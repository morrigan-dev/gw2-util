package de.morrigan.dev.gw2.client.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.Main;

public class GW2Label extends JLabel {

	/** automatisch generierte serialVersionUID */
	private static final long serialVersionUID = 7349437811760158363L;
	
	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(GW2Label.class);

	private int tracking;
	private int left_x, left_y, right_x, right_y;

	private Color left_color, right_color;

	private Insets margin;

	public GW2Label() {
		super();
		this.tracking = 0;
		setRightShadow(1, 1, Color.BLACK);
		setFont(Main.getInstance().getMenomonia().deriveFont(14f));
		setForeground(Color.WHITE);
	}

	@Override
	public Dimension getPreferredSize() {
		String text = getText();
		FontMetrics fm = this.getFontMetrics(getFont());

		Icon icon = getIcon();
		int iconWidth = 0;
		if (icon != null) {
			iconWidth = icon.getIconWidth() + 2;
		}

		int w = fm.stringWidth(text);
		w += (text.length() - 1) * this.tracking;
		w += this.left_x + this.right_x;
		w += iconWidth;

		int h = fm.getHeight();
		h += this.left_y + this.right_y;

		if (this.margin != null) {
			w += this.margin.left + this.margin.right;
			h += this.margin.top + this.margin.bottom;
		}
		return new Dimension(w, h);
	}

	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		char[] chars = getText().toCharArray();

		FontMetrics fm = this.getFontMetrics(getFont());
		int h = fm.getAscent();
		g.setFont(getFont());

		if (isOpaque()) {
			Color background = getBackground();
			g.setColor(background);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		Icon icon = getIcon();
		int iconWidth = 0;
		if (icon != null) {
			icon.paintIcon(this, g, 0, 0);
			iconWidth = icon.getIconWidth() + 2;
		}

		Color foreground = getForeground();
		if (this.right_color != null) {
			this.right_color = new Color(this.right_color.getRed(), this.right_color.getGreen(),
					this.right_color.getBlue(), foreground.getAlpha());
		}
		if (this.left_color != null) {
			this.left_color = new Color(this.left_color.getRed(), this.left_color.getGreen(),
					this.left_color.getBlue(), foreground.getAlpha());
		}

		int x = iconWidth;
		if (this.margin != null) {
			x += this.margin.left;
			h += this.margin.top;
		}

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			int w = fm.charWidth(ch) + this.tracking;

			g.setColor(this.left_color);
			g.drawString("" + chars[i], x - this.left_x, h - this.left_y);

			g.setColor(this.right_color);
			g.drawString("" + chars[i], x + this.right_x, h + this.right_y);

			g.setColor(foreground);
			g.drawString("" + chars[i], x, h);

			x += w;
		}
	}

	@Override
	public void setForeground(Color fg) {
		LOG.debug("fg: {}", fg);
		super.setForeground(fg);
		Main.getInstance().repaint();
	}

	public void setLeftShadow(int x, int y, Color color) {
		LOG.debug("x: {}, y: {}, color: {}", x, y, color);
		this.left_x = x;
		this.left_y = y;
		this.left_color = color;
	}

	public void setMargin(Insets margin) {
		this.margin = margin;
	}

	public void setRightShadow(int x, int y, Color color) {
		LOG.debug("x: {}, y: {}, color: {}", x, y, color);
		this.right_x = x;
		this.right_y = y;
		this.right_color = color;
	}

	@Override
	public void setText(String text) {
		LOG.debug("text: {}", text);
		super.setText(text);
		Main.getInstance().repaint();
	}
}
