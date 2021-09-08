package cz.zweistein.gw2.app.eventwatch;

import java.rmi.RemoteException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cz.zweistein.gw2.app.eventwatch.gui.EventDialog;

public class EventWatcher {

	public static void main(String[] args) throws RemoteException {

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

		EventWatcherModel model = new EventWatcherModel();
		EventDialog ed = new EventDialog(model );

		while (true) {
			
			if (model.getInitialized()) {
				model.update();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}

		}
	}

}
