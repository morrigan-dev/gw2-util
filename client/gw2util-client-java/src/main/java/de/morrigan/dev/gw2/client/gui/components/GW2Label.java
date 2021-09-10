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
	private int leftX;
	private int leftY;
	private int rightX;
	private int rightY;

	private Color leftColor;
	private Color rightColor;

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
		w += this.leftX + this.rightX;
		w += iconWidth;

		int h = fm.getHeight();
		h += this.leftY + this.rightY;

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
		if (this.rightColor != null) {
			this.rightColor = new Color(this.rightColor.getRed(), this.rightColor.getGreen(),
					this.rightColor.getBlue(), foreground.getAlpha());
		}
		if (this.leftColor != null) {
			this.leftColor = new Color(this.leftColor.getRed(), this.leftColor.getGreen(),
					this.leftColor.getBlue(), foreground.getAlpha());
		}

		int x = iconWidth;
		if (this.margin != null) {
			x += this.margin.left;
			h += this.margin.top;
		}

		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			int w = fm.charWidth(ch) + this.tracking;

			g.setColor(this.leftColor);
			g.drawString("" + chars[i], x - this.leftX, h - this.leftY);

			g.setColor(this.rightColor);
			g.drawString("" + chars[i], x + this.rightX, h + this.rightY);

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
		this.leftX = x;
		this.leftY = y;
		this.leftColor = color;
	}

	public void setMargin(Insets margin) {
		this.margin = margin;
	}

	public void setRightShadow(int x, int y, Color color) {
		LOG.debug("x: {}, y: {}, color: {}", x, y, color);
		this.rightX = x;
		this.rightY = y;
		this.rightColor = color;
	}

	@Override
	public void setText(String text) {
		LOG.debug("text: {}", text);
		super.setText(text);
		Main.getInstance().repaint();
	}
}
