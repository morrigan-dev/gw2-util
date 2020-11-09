package de.morrigan.dev.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseableTabbedPane extends JPanel {

	private class CloseButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public CloseButton() {
			super();

			final int size = 18;
			setPreferredSize(new Dimension(size, size));
			setUI(new BasicButtonUI());
			setContentAreaFilled(false);
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			addMouseListener(BTN_MOUSE_LISTENER);
			setRolloverEnabled(true);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			final int tabIndex = CloseableTabbedPane.this.tpnTabPane.indexOfTabComponent(CloseableTabbedPane.this);
			if (tabIndex != -1) {
				CloseableTabbedPane.this.tpnTabPane.remove(tabIndex);
			}
		}

		@Override
		public void updateUI() {}

		@Override
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			final Graphics2D g2 = (Graphics2D) g.create();
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.RED);
			}
			final int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g2.dispose();
		}
	}

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(CloseableTabbedPane.class);

	private static final MouseListener BTN_MOUSE_LISTENER = new MouseAdapter() {

		@Override
		public void mouseEntered(final MouseEvent e) {
			final Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				final AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			final Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				final AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};

	private static final long serialVersionUID = 1L;

	private final JTabbedPane tpnTabPane;
	private final JLabel lblTabTitle;

	public CloseableTabbedPane(final JTabbedPane tpnTabPane) {
		super();
		LOG.debug("tpnTabPane: {}", tpnTabPane);

		this.tpnTabPane = tpnTabPane;

		this.lblTabTitle = new JLabel() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getText() {
				final int tabIndex = tpnTabPane.indexOfTabComponent(CloseableTabbedPane.this);
				if (tabIndex != -1) {
					return tpnTabPane.getTitleAt(tabIndex);
				}
				return null;
			}
		};
		add(this.lblTabTitle);

		final JButton btnClose = new CloseButton();
		add(btnClose);

		setOpaque(false);
	}
}
