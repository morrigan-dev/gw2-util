package cz.zweistein.gw2.app.wvwexplorer;

import java.rmi.RemoteException;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cz.zweistein.gw2.app.wvwexplorer.gui.WvWExplorerGui;

public class WvWExplorer {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		final WvWExplorerModel model = new WvWExplorerModel();
		final WvWExplorerGui gui = new WvWExplorerGui(model);

		try {
			MidiSynth synth = new MidiSynth();
			model.setSound(synth);
		} catch (MidiUnavailableException e1) {
			e1.printStackTrace();
		}

		gui.setStatusIdle();

		while (true) {

			if (model.isInitialized()) {
				model.update();
			} else {
				try {
					model.init();
				} catch (final RemoteException e) {
					gui.setStatusError(e);
				}
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				break;
			}

		}

	}

}
