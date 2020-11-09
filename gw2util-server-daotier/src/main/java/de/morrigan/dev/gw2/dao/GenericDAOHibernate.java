package de.morrigan.dev.gw2.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.dao.interfaces.IGenericDAO;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.interfaces.IActiveStateEntity;
import de.morrigan.dev.gw2.entity.interfaces.IAuditEntity;
import de.morrigan.dev.gw2.entity.interfaces.IEntity;
import de.morrigan.dev.gw2.utils.enums.ActiveState;
import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

/**
 * Diese abstrakte Klasse stellt die Oberklasse aller DAOs dar und bietet verschiedene Methoden, die von allen DAOs
 * benutzt werden können.
 * 
 * @author morrigan
 * @param <T> Die Klasse der Entity, für dieses DAO.
 * @param <U> Der Datentyp des Primärschlüssels.
 */
@PersistenceContext(name = "MainPersistenceUnit")
public abstract class GenericDAOHibernate<T, U extends Serializable> implements IGenericDAO<T, U> {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GenericDAOHibernate.class);

	/** Der Entity Manager für die Ausführung von Queries */
	@PersistenceContext
	protected EntityManager entityManager;

	/** Die Klasse des Entity-Objekts, zu dem dieses DAO gehört. */
	private final Class<T> persistentClass;

	/**
	 * Erzeugt eine neue Instanz und übergibt die Klasse des Entity-Objekts.
	 * 
	 * @param persistentClass die Klasse des Entity-Objekts. (not null)
	 * @throws IllegalArgumentException falls der Parameter null ist.
	 */
	public GenericDAOHibernate(final Class<T> persistentClass) {
		super();
		Validate.notNull(persistentClass, "Der Parameter (persistentClass) darf nicht null sein!");
		this.persistentClass = persistentClass;
	}

	@Override
	public void delete(final T entity) throws PersistenceException {
		LOG.debug("entity: {}", entity);
		if (entity == null) {
			return;
		}
		try {
			this.entityManager.remove(entity);
		} catch (final Exception e) {
			throw new PersistenceException(AbstractException.DATABASE_DELETE_FAILED_EXCEPTION, "", e);
		}
	}

	@Override
	public void deleteDetached(final U id) throws PersistenceException {
		delete(this.entityManager.find(getPersistentClass(), id));
	}

	@Override
	public List<T> findAll() throws PersistenceException {
		final StringBuilder query = new StringBuilder();
		query.append("SELECT result FROM ");
		query.append(getPersistentClass().getSimpleName());
		query.append(" AS result");

		final Query q = this.entityManager.createQuery(query.toString());
		return getResultList(q);
	}

	@Override
	public T findById(final U id) throws PersistenceException {
		LOG.debug("id: {}", id);
		try {
			if (id == null) {
				return null;
			}
			return this.entityManager.find(getPersistentClass(), id);
		} catch (final IllegalArgumentException e) {
			throw new PersistenceException(AbstractException.DATABASE_NO_ENTITY_OR_INVALID_PKEY, "", e);
		}
	}

	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	@Override
	public T markAsDeleted(final T entity) throws PersistenceException {
		LOG.debug("entity: {}", entity);
		if (entity == null) {
			return null;
		}
		T result = null;
		if (entity instanceof IActiveStateEntity) {
			final IActiveStateEntity statusEntity = (IActiveStateEntity) entity;
			statusEntity.setActiveState(ActiveState.DELETED);
			result = save(entity);
		} else {
			LOG.warn(
					"Die Entity {} implementiert nicht das IActiveStateEntity Interface. Der Status kann somit nicht aktualisiert werden.",
					entity.getClass());
		}
		LOG.debug("result: {}", result);
		return result;
	}

	@Override
	public T merge(final T entity) throws PersistenceException {
		LOG.debug("entity: {}", entity);
		if (entity == null) {
			return null;
		}
		try {
			return this.entityManager.merge(entity);
		} catch (final Exception e) {
			throw new PersistenceException(AbstractException.DATABASE_MERGE_FAILED_EXCEPTION, "", e);
		}
	}

	public T persist(final T entity) throws PersistenceException {
		LOG.debug("entity: {}", entity);
		if (entity == null) {
			return null;
		}
		try {
			this.entityManager.persist(entity);
			this.entityManager.flush();
			this.entityManager.refresh(entity);
			return entity;
		} catch (EntityExistsException e) {
			throw new PersistenceException(AbstractException.DATABASE_ALREADY_EXIST_EXCEPTION, "", e);
		} catch (Exception e) {
			throw new PersistenceException(AbstractException.DATABASE_PERSIST_FAILED_EXCEPTION, "", e);
		}
	}

	@Override
	public T save(final T entity) throws PersistenceException {
		LOG.debug("entity: {}", entity);
		T savedEntity = null;
		if (entity == null) {
			return null;
		}
		// Es werden nur die Entities gespeichert, die von IEntity abgeleitet sind.
		if (entity instanceof IEntity) {
			savedEntity = saveNormalEntity(entity);
			LOG.debug("saved entity: {}", savedEntity);
		} else {
			LOG.warn("Die Entity {} implementiert nicht das Interface IEntity!", entity.getClass());
		}
		return savedEntity;
	}

	@Override
	public <S extends IAuditEntity> void setAuditInformation(final S auditEntity, User executingUser,
			boolean setCreateData) {
		Validate.notNull(executingUser, "Der Parameter (executingUser) darf nicht null sein!");
		LOG.debug("auditEntity: {}, executingUser: {}, setCreateData: {}", auditEntity, executingUser, setCreateData);
		if (auditEntity == null) {
			return;
		}
		Date currentDate = new Date();
		if (setCreateData) {
			auditEntity.setCreateDate(currentDate);
			auditEntity.setCreateUser(executingUser);
		}
		auditEntity.setUpdateDate(currentDate);
		auditEntity.setUpdateUser(executingUser);
	}

	protected <S> List<S> getGenericResultList(final Class<?> resultType, final Query query)
			throws PersistenceException {
		Validate.notNull(query, "Der Parameter (query) darf nicht null sein!");
		LOG.debug("resultType: {}, query: {}", resultType, query);
		List<S> result = new ArrayList<>();
		try {
			result = checkResult(resultType, query.getResultList());
		} catch (final IllegalStateException e) {
			throw new PersistenceException(AbstractException.UNEXPECTED_EXCEPTION, "", e);
		} catch (final PersistenceException e) {
			throw new PersistenceException(AbstractException.DATABASE_UNEXPECTED_EXCEPTION, "", e);
		}

		LOG.debug("result: {}", result);
		return result;
	}

	/**
	 * Ermittelt zu einer übergebenen Query ein Ergebnis in Form eines eindeutigen Objekts. Eventuell auftretende Fehler
	 * werden gefangen und in entsprechende {@link PersistenceException} umgewandelt. Der Rückgabewert kann generisch
	 * angegeben werden, sodass nicht nur der zu dem jeweiligen DAO gehörende Typ erzeugt werden kann.
	 * 
	 * @param query Die auszuführende Query (not null)
	 * @return das Ergebnis der Query
	 * @throws PersistenceException falls ein Fehler bei der Ausführung der Query auftritt.
	 * @throws IllegalArgumentException falls der übergebene Parameter null ist.
	 * @see {@link Query#getSingleResult()}
	 */
	@SuppressWarnings("unchecked")
	protected <S> S getGenericSingleResult(final Query query) throws PersistenceException {
		Validate.notNull(query, "Der Parameter (query) darf nicht null sein!");
		LOG.debug("query: {}", query);
		S result = null;
		try {
			result = (S) query.getSingleResult();
		} catch (final NoResultException e) {
			throw new de.morrigan.dev.gw2.exception.NoResultException("", e);
		} catch (final NonUniqueResultException e) {
			throw new PersistenceException(AbstractException.DATABASE_NON_UNIQUE_EXCEPTION, "Query: " + query.toString(), e);
		} catch (final IllegalStateException e) {
			throw new PersistenceException(AbstractException.UNEXPECTED_EXCEPTION, "Query: " + query.toString(), e);
		} catch (final javax.persistence.PersistenceException e) {
			throw new PersistenceException(AbstractException.DATABASE_UNEXPECTED_EXCEPTION, "Query: " + query.toString(), e);
		}
		LOG.debug("result: {}", result);
		return result;
	}

	/**
	 * Liefert eine Auflistung an Parametern für eine SQL IN Abfrage.
	 * 
	 * @param enumeration eine beliebige Enumeration. (not null)
	 * @return eine Auflistung der übergebenen Enums für eine SQL IN Abfrage.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	protected String getINParameter(Enum<?>[] enumeration) {
		Validate.notNull(enumeration, "Der Parameter (enumeration) darf nicht null sein!");
		LOG.debug("enumeration: {}", (Object) enumeration);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < enumeration.length; i++) {
			result.append("'");
			result.append(enumeration[i].name());
			result.append("'");
			if (i < (enumeration.length - 1)) {
				result.append(",");
			}
		}
		return result.toString();
	}

	/**
	 * Diese Methode verweist lediglich auf {@link GenericDAOHibernate#getGenericResultList(Query)}, hat aber einen festen
	 * Rückgabewert List<T>.
	 * 
	 * @return Das Ergebnis der Abfrage in einer Liste. (not null)
	 */
	protected List<T> getResultList(final Query query) throws PersistenceException {
		return getGenericResultList(this.persistentClass, query);
	}

	/**
	 * Diese Methode verweist lediglich auf {@link GenericDAOHibernate#getGenericSingleResult(Query)}, hat aber einen
	 * festen Rückgabewert T.
	 */
	protected T getSingleResult(final Query query) throws PersistenceException {
		return getGenericSingleResult(query);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <S> List<S> checkResult(final Class<?> resultType, final List result) throws PersistenceException {
		LOG.debug("result: {}", result);
		if (!result.isEmpty()) {
			try {
				resultType.cast(result.get(0));
			} catch (final ClassCastException e) {
				throw new PersistenceException(AbstractException.TYPE_CHECK_EXCEPTION,
						"Es befindet sich ein Element mit dem falschen Typ in der Liste!", e);
			}
		}
		return result;
	}

	/**
	 * Diese Methode speicherte einen "normalen" Datensatz ab, der keine weiteren Zusatzeigenschaften wie Historisierung
	 * besitzt.
	 * 
	 * @param entity Der zu speichernde Datensatz.
	 * @return Den gespeicherten Datensatz.
	 * @throws PersistenceException
	 * @author morrigan
	 */
	private T saveNormalEntity(final T entity) throws PersistenceException {
		LOG.debug("entity: {}", entity);
		T savedEntity = null;
		final IEntity idEntity = (IEntity) entity;
		if (idEntity.getId() == 0L) {
			savedEntity = persist(entity);
		} else {
			savedEntity = merge(entity);
		}
		return savedEntity;
	}
}
