package cz.zweistein.gw2.app.wvwexplorer.componentmodel;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import cz.zweistein.gw2.api.dto.WvWMap;
import cz.zweistein.gw2.api.dto.WvWMatchDetail;
import cz.zweistein.gw2.api.dto.WvWObjective;
import cz.zweistein.gw2.app.wvwexplorer.WvWExplorerModel;

public class WvWTreeModel implements TreeModel, IUpdateable {
	
	private WvWExplorerModel model;
	
	private List<TreeModelListener> treeModelListeners;

	public WvWTreeModel(WvWExplorerModel model) {
		this.treeModelListeners = new LinkedList<TreeModelListener>();
		this.model = model;
		this.model.setWvWTreeListener(this);
	}

	@Override
	public Object getRoot() {
		return model.getMatchDetails();
	}

	@Override
	public Object getChild(Object parent, int index) {
	
		if (parent instanceof WvWMatchDetail)  {
			return ((WvWMatchDetail)parent).getMaps().get(index);
		} else if (parent instanceof WvWMap) {
			return ((WvWMap)parent).getObjectives().get(index);
		} else {
			return null;
		}
		
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof WvWMatchDetail)  {
			return ((WvWMatchDetail)parent).getMaps().size();
		} else if (parent instanceof WvWMap) {
			return ((WvWMap)parent).getObjectives().size();
		} else {
			return 0;
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		return node instanceof WvWObjective;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof WvWMatchDetail)  {
			return ((WvWMatchDetail)parent).getMaps().indexOf(child);
		} else if (parent instanceof WvWMap) {
			return ((WvWMap)parent).getObjectives().indexOf(child);
		} else {
			return 0;
		}
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListeners.remove(l);
	}

	public void fireChange() {
		for(TreeModelListener listener: this.treeModelListeners) {
			TreeModelEvent event = new TreeModelEvent(this, new TreePath(getRoot()));
			listener.treeStructureChanged(event);
		}
	}

	@Override
	public void fireChange(List<TreePath> changedPaths) {
		for(TreeModelListener listener: this.treeModelListeners) {
			for (TreePath treePath : changedPaths) {
				TreeModelEvent event = new TreeModelEvent(this, treePath);
				listener.treeStructureChanged(event);
			}
		}
	}

}
