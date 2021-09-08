package cz.zweistein.gw2.app.wvwexplorer.componentmodel;

import java.util.List;
import java.util.Map.Entry;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.tree.TreePath;

import cz.zweistein.gw2.app.wvwexplorer.WvWExplorerModel;

public class WorldNamesComboBoxModel extends AbstractListModel implements ComboBoxModel, IUpdateable {
	private static final long serialVersionUID = 6731546977451638084L;

	public static class WorldNameEntry {
		private Entry<Long, String> entry;
		
		public WorldNameEntry(Entry<Long, String> entry) {
			super();
			this.entry = entry;
		}

		public Entry<Long, String> getEntry() {
			return entry;
		}

		@Override
		public String toString() {
			return entry.getValue();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			} else {
				return entry.equals(((WorldNameEntry)obj).getEntry());
			}
		}
		
	}
	
	private WvWExplorerModel model;
	
	private WorldNameEntry selectedItem;

	public WorldNamesComboBoxModel(WvWExplorerModel model) {
		this.model = model;
		model.setWorldNamesListener(this);
	}

	@Override
	public int getSize() {
		return model.getWorldNames().entrySet().size();
	}
	
	public void fireChange() {
		for (ListDataListener listener : getListDataListeners()) {
			listener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize()));
		}
	}

	@Override
	public Object getElementAt(int index) {
		@SuppressWarnings("unchecked")
		Entry<Long, String>[] entries = model.getWorldNames().entrySet().toArray(new Entry[model.getWorldNames().entrySet().size()]);
		
		return new WorldNameEntry(entries[index]);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = (WorldNameEntry) anItem;
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				model.changeWorld(selectedItem.getEntry().getKey());
			}
		});
		
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void fireChange(List<TreePath> changedPaths) {
		fireChange();
	}

}
