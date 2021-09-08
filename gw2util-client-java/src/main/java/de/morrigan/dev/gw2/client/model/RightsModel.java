package de.morrigan.dev.gw2.client.model;

import java.awt.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Vector;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.utils.annotations.RightCheck;
import de.morrigan.dev.swing.models.AbstractModel;
import de.morrigan.dev.utils.BitUtil;

/**
 * Dieses Model verwaltet alle Rechte, die der angemeldete Benutzer besitzt. Geladen werden diese Rechte vom Server nach
 * der Anmeldung. Dieses Rechte Model wird benötigt, um in den einzelnen Views die GUI entsprechend der Rechte, die der
 * Benutzer besitzt zu gestalten.
 * <p>
 * Alle Views, die eine Rechteprüfung ermöglichen sollen, müssen sich als Observer an diesem Model anmelden.
 * 
 * @author morrigan
 */
public class RightsModel extends AbstractModel {

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(RightsModel.class);

  private static final RightsModel INSTANCE = new RightsModel();

  public static final long RIGHTS_CHANGED = BitUtil.setLongBit(0);

  public static RightsModel getInstance() {
    return INSTANCE;
  }

  /** Beinhaltet alle Rechte, die der angemeldete Benutzer besitzt */
  private Vector<String> rights = new Vector<>();

  private RightsModel() {
    super();
  }

  public boolean hasRight(final String... rightsToCheck) {
    Validate.notNull(rightsToCheck, "Der Parameter (rightsToCheck) darf nicht null sein!");
    LOG.debug("rightsToCheck: {}", Arrays.asList(rightsToCheck));

    boolean hasAllRights = true;
    for (final String rightKey : rightsToCheck) {
      if (!this.rights.contains(rightKey)) {
        hasAllRights = false;
        break;
      }
    }

    return hasAllRights;
  }

  public void resetRights() {
    this.rights.clear();

    if (LOG.isInfoEnabled()) {
      LOG.info("Alle Rechte entfernt.");
    }
    if (!isChanging()) {
      syncViews(RIGHTS_CHANGED);
    }
  }

  public void setRights(Vector<String> rights) {
    LOG.debug("rights: {}", rights);
    this.rights.clear();
    this.rights.addAll(rights);

    if (LOG.isInfoEnabled()) {
      LOG.info(rights.size() + " Rechte hinzugefügt.");
    }
    if (!isChanging()) {
      syncViews(RIGHTS_CHANGED);
    }
  }

  public void updateViewByRights(Object viewToCheck) {
    Field[] fields = viewToCheck.getClass().getDeclaredFields();
    for (Field field : fields) {
      for (Annotation anno : field.getDeclaredAnnotations()) {
        if (anno.annotationType().equals(RightCheck.class)) {
          RightCheck annotatedRights = (RightCheck) anno;
          field.setAccessible(true);
          switch (annotatedRights.changeType()) {
            case VISIBILITY:
              updateField(viewToCheck, field, annotatedRights, "setVisible");
            break;
            case CHANGEABILITY:
              updateField(viewToCheck, field, annotatedRights, "setEditable");
            break;

            default:
            break;
          }
        }
      }
    }

  }

  private void updateField(Object viewToCheck, Field field, RightCheck rights, String methodName) {
    RightsModel rightsModel = RightsModel.getInstance();
    boolean methodFound = false;
    Class<?> classToCheck = field.getType();
    while (!methodFound && (classToCheck != null)) {
      try {
        Class<?>[] types = new Class<?>[] {
            boolean.class
        };
        Method method = classToCheck.getDeclaredMethod(methodName, types);

        Component o = (Component) field.get(viewToCheck);
        method.setAccessible(true);
        method.invoke(o, rightsModel.hasRight(rights.rightKeys()));
        methodFound = true;
      } catch (NoSuchMethodException e) {
        LOG.info("keine {} in {} vorhanden.", methodName, classToCheck.getSimpleName());
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        LOG.error(e.getMessage(), e);
      }
      classToCheck = classToCheck.getSuperclass();
    }
  }
}
