package de.morrigan.dev.swing.interfaces.dialog;

import de.morrigan.dev.swing.enums.DialogMessage;

public interface IPasswordDialogListener {

	boolean actionPerformed(String username, String password, DialogMessage msg);
}