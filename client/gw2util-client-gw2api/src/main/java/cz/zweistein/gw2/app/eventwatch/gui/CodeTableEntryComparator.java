package cz.zweistein.gw2.app.eventwatch.gui;

import java.io.Serializable;
import java.util.Comparator;

public class CodeTableEntryComparator implements Comparator<CodeTableItem>, Serializable {
	private static final long serialVersionUID = -6420422470040154374L;

	@Override
	public int compare(CodeTableItem arg0, CodeTableItem arg1) {
		return arg0.toString().compareTo(arg1.toString());
	}

}
