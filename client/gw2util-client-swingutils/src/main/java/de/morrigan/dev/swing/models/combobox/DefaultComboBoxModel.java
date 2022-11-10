package de.morrigan.dev.swing.models.combobox;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

import de.morrigan.dev.gw2.utils.model.interfaces.IComboBoxItem;
import de.morrigan.dev.utils.resources.LanguageManager;

public class DefaultComboBoxModel<T> extends AbstractListModel implements MutableComboBoxModel {

  private static final long serialVersionUID = 1L;

  /** Stellt Beschriftungen, Nachrichten und Fehlerbeschreibungen bereit */
  protected static final LanguageManager LANGUAGES = LanguageManager.getInstance();

  /** Liste mit den einzelnen Entities */
  private List<T> dataList = new ArrayList<>();

  /** Aktuell ausgewähltes Element */
  private T selectedItem;

  public DefaultComboBoxModel() {
    super();
  }

  @Override
  public void addElement(final Object anObject) {
    if (anObject instanceof IComboBoxItem) {
      @SuppressWarnings("unchecked")
      final T elementToAdd = (T) anObject;
      this.dataList.add(elementToAdd);
      fireIntervalAdded(this, this.dataList.size() - 1, this.dataList.size() - 1);
    }
  }

  public boolean contains(final T element) {
    return this.dataList.contains(element);
  }

  @Override
  public Object getElementAt(final int index) {
    if ((index >= 0) && (index < this.dataList.size())) {
      return this.dataList.get(index);
    } else {
      return null;
    }
  }

  public T getSelectedElement() {
    return this.selectedItem;
  }

  @Override
  public Object getSelectedItem() {
    return this.selectedItem;
  }

  @Override
  public int getSize() {
    return this.dataList.size();
  }

  @Override
  public void insertElementAt(final Object anObject, final int index) {
    if (anObject instanceof IComboBoxItem) {
      @SuppressWarnings("unchecked")
      final T elementToInsert = (T) anObject;
      this.dataList.add(elementToInsert);
      fireIntervalAdded(this, index, index);
    } else {
      throw new IllegalArgumentException(anObject.getClass() + " implementiert nicht das Interface "
          + IComboBoxItem.class);
    }
  }

  public boolean isEmpty() {
    return this.dataList.isEmpty();
  }

  public void removeAllElements() {
    this.dataList.clear();
  }

  @Override
  public void removeElement(final Object anObject) {
    if (anObject instanceof IComboBoxItem) {
      @SuppressWarnings("unchecked")
      final T elementToRemove = (T) anObject;
      final int index = this.dataList.indexOf(elementToRemove);
      this.dataList.remove(elementToRemove);
      fireIntervalRemoved(this, index, index);
    } else {
      throw new IllegalArgumentException(anObject.getClass() + " implementiert nicht das Interface "
          + IComboBoxItem.class);
    }
  }

  @Override
  public void removeElementAt(final int index) {
    this.dataList.remove(index);
    fireIntervalRemoved(this, index, index);
  }

  public void setDataList(final List<T> dataList) {
    this.dataList = dataList;
    fireContentsChanged(this, 0, dataList.size());
  }

  @Override
  @SuppressWarnings("unchecked")
  public void setSelectedItem(final Object anItem) {
    if (((this.selectedItem != null) && !this.selectedItem.equals(anItem))
        || ((this.selectedItem == null) && (anItem != null))) {
      if (anItem == null) {
        this.selectedItem = null;
        fireContentsChanged(this, -1, -1);
        return;
      }
      if (anItem instanceof IComboBoxItem) {
        this.selectedItem = (T) anItem;
        fireContentsChanged(this, -1, -1);
      } else {
        throw new IllegalArgumentException(anItem.getClass() + " implementiert nicht das Interface "
            + IComboBoxItem.class);
      }
    }
  }
}
