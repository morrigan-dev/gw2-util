package cz.zweistein.gw2.app.wvwexplorer.componentmodel;

import java.util.List;

import javax.swing.tree.TreePath;

public interface IUpdateable {
	
	public void fireChange();

	public void fireChange(List<TreePath> changedPaths);

}
