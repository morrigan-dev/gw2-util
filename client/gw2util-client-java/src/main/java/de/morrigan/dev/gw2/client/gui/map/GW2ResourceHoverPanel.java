package de.morrigan.dev.gw2.client.gui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.resources.FontConstants;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.utils.resources.FontManager;

public class GW2ResourceHoverPanel extends GW2HoverPanel {

  /** automatisch generierte serialVersionUID */
  private static final long serialVersionUID = 4099306553622494465L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(GW2ResourceHoverPanel.class);

  /** FontManager der verschiedene Schriftarten OS unabhängig bereitstellt */
  private static final FontManager FONT_MANAGER = FontManager.getInstance();

  private JPanel pnlResources;
  private JLabel lblGatheringIcon;
  private GW2Label lblGatheringInfo;
  private GW2Label lblCreateInfo;

  public GW2ResourceHoverPanel() {
    super();
  }

  @Override
  public void configureGUI() {
    super.configureGUI();
    this.pnlResources.setLayout(new GridBagLayout());
    this.pnlResources.setOpaque(false);
    this.lblInfo.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f, Font.BOLD).get());
    this.lblGatheringInfo.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f).get());
    this.lblCreateInfo.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f).get());
    this.lblCreateInfo.setForeground(Color.LIGHT_GRAY);
  }

  @Override
  public void createGUI() {
    super.createGUI();
    this.pnlResources = new JPanel();
    this.lblGatheringIcon = new JLabel();
    this.lblGatheringInfo = new GW2Label();
    this.lblCreateInfo = new GW2Label();
  }

  @Override
  public void layoutGUI() {
    super.layoutGUI();
    GridBagConstraints gbc = new GridBagConstraints();
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 2, 1, InsetConstants.LEFT_INSETS);
    this.pnlContent.add(this.pnlResources, gbc);
    GCUtil.configGC(gbc, 0, 2, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlContent.add(this.lblGatheringIcon, gbc);
    GCUtil.configGC(gbc, 1, 2, GCUtil.WEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
    this.pnlContent.add(this.lblGatheringInfo, gbc);
    GCUtil.configGC(gbc, 0, 3, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 2, 1, InsetConstants.ALL_INSETS);
    this.pnlContent.add(this.lblCreateInfo, gbc);
  }

  public void setData(String info, List<ImageIcon> resIcons, List<String> resInfos, ImageIcon gatheringIcon,
      String gatheringInfo, String createInfo) {
    LOG.debug("info: {}, resIcons: {}, resInfos: {}, gatheringIcon: {}, gatheringInfo: {}, createInfo: {}", info,
        resIcons, resInfos, gatheringIcon, gatheringInfo, createInfo);
    setInfo(info);
    this.lblGatheringIcon.setIcon(gatheringIcon);
    this.lblGatheringInfo.setText(gatheringInfo);
    this.lblCreateInfo.setText(createInfo);

    this.pnlResources.removeAll();
    GridBagConstraints gbc = new GridBagConstraints();
    for (int i = 0; i < resIcons.size(); i++) {
      JLabel resIcon = new JLabel();
      GW2Label resInfo = new GW2Label();
      resIcon.setIcon(resIcons.get(i));
      resInfo.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f).get());
      resInfo.setText(resInfos.get(i));

      GCUtil.configGC(gbc, 0, 0 + i, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
      this.pnlResources.add(resIcon, gbc);
      GCUtil.configGC(gbc, 1, 0 + i, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LEFT_INSETS);
      this.pnlResources.add(resInfo, gbc);
    }
    pack();
  }
}
