package de.morrigan.dev.swing.models;

import java.math.BigDecimal;

import de.morrigan.dev.gw2.utils.exceptions.AbstractException;
import de.morrigan.dev.gw2.utils.observer.AObservable;
import de.morrigan.dev.utils.resources.LanguageManager;

/**
 * Dieses abstrakte Model stellt Hilfsmethoden für das Observer-Pattern zur Verfügung. Jedes Model muss von dieser
 * Klasse abgeleitet sein.
 *
 * @author morrigan
 */
public abstract class AbstractModel extends AObservable {

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  protected static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  /** Gibt an, ob das Model initialisiert wurde */
  private boolean initialized;

  public AbstractModel() {
    super();

    this.initialized = false;
  }

  /**
   * Diese Methode initialisiert alle Werte im Model veranlasst zum Schluss, dass die registrierten Observer ihre GUI
   * aktualisieren.
   * 
   * @throws AbstractException
   */
  public void initialize() throws AbstractException {}

  public boolean isInitialized() {
    return this.initialized;
  }

  public void setInitialized(final boolean initialized) {
    this.initialized = initialized;
  }

  protected final boolean checkBigDecimalChanged(final BigDecimal oldDec, final BigDecimal newDec) {
    boolean result;
    result = ((oldDec == null) && (newDec != null));
    result |= (oldDec != null) && (newDec == null);
    result |= ((oldDec != null) && (newDec != null) && (newDec.compareTo(oldDec) != 0));
    return result;
  }

  /**
   * Prüft, ob zwei Objekte ungleich sind.
   * 
   * @param oldObj Das alte Objekt
   * @param newObj Das neue Objekt, das mit dem alten verglichen werden soll
   * @return true, falls die Inhalte der Objekte voneinander abweichen
   */
  protected final boolean checkValueChanged(final Object oldObj, final Object newObj) {
    return (((oldObj != null) && (newObj == null)) || ((newObj != null) && !newObj.equals(oldObj)));
  }

  /**
   * Diese Methode setzt alle Werte im Model zurück und veranlasst danach, dass die registrierten Observer ihre GUI
   * aktualisieren.
   * 
   * @throws AbstractException
   */
  protected void reset() throws AbstractException {}

  /**
   * Diese Methode benachrichtigt alle Observer, dass sich die Daten geändert haben und nun ihre GUI aktualisieren
   * müssen.
   */
  protected final void syncViews() {
    setChanged();
    notifyObservers(this);
    clearChanged();
  }

  /**
   * Diese Methode benachrichtigt alle Observer, dass sich die Daten geändert haben und nun ihre GUI aktualisieren
   * müssen. über den Parameter kann gesteuert werden, welche GUI Elemente aktualisiert werden sollen. Üblicherweise
   * wird dazu eine Enumeration im ViewModel erstellt und einer der Werte mit übergeben. In den entsprechenden Forms
   * muss auf diese Enumeration dann geprüft werden.
   * 
   * @param updateObject Option zur Steuerung der zu aktualisierenden GUI Elemente
   */
  protected final void syncViews(final long updateObject) {
    setChanged();
    notifyObservers(this, updateObject);
    clearChanged();
  }
}
