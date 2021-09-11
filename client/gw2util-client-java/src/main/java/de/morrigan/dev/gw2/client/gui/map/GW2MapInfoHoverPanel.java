package de.morrigan.dev.gw2.client.gui.map;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.components.GW2Label;
import de.morrigan.dev.gw2.client.gui.map.waypoints.HoverInfo;
import de.morrigan.dev.gw2.client.gui.map.waypoints.WPGW2Resource;
import de.morrigan.dev.gw2.dto.common.enums.WPSubType;
import de.morrigan.dev.gw2.resources.FontConstants;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.utils.resources.FontManager;

public class GW2MapInfoHoverPanel extends GW2HoverPanel {

  /** FontManager der verschiedene Schriftarten OS unabhängig bereitstellt */
  private static final FontManager FONT_MANAGER = FontManager.getInstance();

  private class ResRow {

    public WPSubType wpSubType;
    public ImageIcon resIcon;
    public String resLabel;
    public int amount;

    public ResRow(WPSubType wpSubType, ImageIcon resIcon, String resLabel, int amount) {
      super();
      this.wpSubType = wpSubType;
      this.resIcon = resIcon;
      this.resLabel = resLabel;
      this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      ResRow other = (ResRow) obj;
      if (!getOuterType().equals(other.getOuterType())) {
        return false;
      }
      if (this.resLabel == null) {
        if (other.resLabel != null) {
          return false;
        }
      } else if (!this.resLabel.equals(other.resLabel)) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + getOuterType().hashCode();
      result = (prime * result) + ((this.resLabel == null) ? 0 : this.resLabel.hashCode());
      return result;
    }

    private GW2MapInfoHoverPanel getOuterType() {
      return GW2MapInfoHoverPanel.this;
    }
  }

  private static final long serialVersionUID = 1L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(GW2MapInfoHoverPanel.class);

  private JPanel pnlOre;
  private JPanel pnlWood;
  private JPanel pnlPlant;

  public GW2MapInfoHoverPanel() {
    super();
  }

  @Override
  public void configureGUI() {
    super.configureGUI();
    this.pnlOre.setLayout(new GridBagLayout());
    this.pnlOre.setOpaque(false);
    this.pnlWood.setLayout(new GridBagLayout());
    this.pnlWood.setOpaque(false);
    this.pnlPlant.setLayout(new GridBagLayout());
    this.pnlPlant.setOpaque(false);
    this.lblInfo.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f, Font.BOLD).get());
  }

  @Override
  public void createGUI() {
    super.createGUI();
    this.pnlOre = new JPanel();
    this.pnlWood = new JPanel();
    this.pnlPlant = new JPanel();
  }

  @Override
  public void layoutGUI() {
    super.layoutGUI();
    GridBagConstraints gbc = new GridBagConstraints();
    GCUtil.configGC(gbc, 0, 1, GCUtil.NORTHWEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.ALL_INSETS);
    this.pnlContent.add(this.pnlOre, gbc);
    GCUtil.configGC(gbc, 1, 1, GCUtil.NORTHWEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.ALL_INSETS);
    this.pnlContent.add(this.pnlWood, gbc);
    GCUtil.configGC(gbc, 2, 1, GCUtil.NORTHWEST, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.ALL_INSETS);
    this.pnlContent.add(this.pnlPlant, gbc);
  }

  public void setData(String mapName, Map<WPSubType, Integer> resourceAmount) {
    LOG.debug("mapName: {}, resourceAmount: {}", mapName, resourceAmount);
    setInfo(RESOURCE_MANAGER.getLabel("resourceOverview") + " (" + mapName + ")");

    GridBagConstraints gbc = new GridBagConstraints();
    List<WPSubType> wpSubTypes = new ArrayList<>(resourceAmount.keySet());
    Collections.sort(wpSubTypes);

    List<ResRow> resRows = new ArrayList<>();
    for (WPSubType wpSubType : wpSubTypes) {
      int amount = resourceAmount.get(wpSubType);
      if (amount > 0) {
        HoverInfo info = WPGW2Resource.getHoverInfoBySubType(wpSubType, -1, -1);
        List<ImageIcon> resourceIcon = info.resourceIcon;
        List<String> resourceLabel = info.resourceLabel;
        for (int j = 0; j < resourceIcon.size(); j++) {
          ResRow resRow = new ResRow(wpSubType, resourceIcon.get(j), resourceLabel.get(j), amount);
          int index = resRows.indexOf(resRow);
          if (index == -1) {
            resRows.add(resRow);
          } else {
            resRows.get(index).amount += amount;
          }
        }
      }
    }

    int oreY = 0;
    int woodY = 0;
    int plantY = 0;
    this.pnlOre.removeAll();
    this.pnlWood.removeAll();
    this.pnlPlant.removeAll();
    for (ResRow resRow : resRows) {
      JLabel resIcon = new JLabel();
      GW2Label resInfo = new GW2Label();
      resIcon.setIcon(resRow.resIcon);
      resInfo.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f).get());
      StringBuilder sb = new StringBuilder(resRow.resLabel);
      sb.append(" (").append(resRow.amount).append("x)");
      resInfo.setText(sb.toString());

      switch (resRow.wpSubType.getWpType()) {
        case ORE:
          GCUtil.configGC(gbc, 0, 0 + oreY, GCUtil.NORTHWEST, GCUtil.NONE, 0.0, 0.0, 1, 1,
              InsetConstants.NO_INSETS);
          this.pnlOre.add(resIcon, gbc);
          GCUtil.configGC(gbc, 1, 0 + oreY, GCUtil.NORTHWEST, GCUtil.HORI, 0.0, 0.0, 1, 1,
              InsetConstants.LEFT_INSETS);
          this.pnlOre.add(resInfo, gbc);
          oreY++;
        break;
        case WOOD:
          GCUtil.configGC(gbc, 0, 0 + woodY, GCUtil.NORTHWEST, GCUtil.NONE, 0.0, 0.0, 1, 1,
              InsetConstants.NO_INSETS);
          this.pnlWood.add(resIcon, gbc);
          GCUtil.configGC(gbc, 1, 0 + woodY, GCUtil.NORTHWEST, GCUtil.HORI, 0.0, 0.0, 1, 1,
              InsetConstants.LEFT_INSETS);
          this.pnlWood.add(resInfo, gbc);
          woodY++;
        break;
        case PLANT:
          GCUtil.configGC(gbc, 0, 0 + plantY, GCUtil.NORTHWEST, GCUtil.NONE, 0.0, 0.0, 1, 1,
              InsetConstants.NO_INSETS);
          this.pnlPlant.add(resIcon, gbc);
          GCUtil.configGC(gbc, 1, 0 + plantY, GCUtil.NORTHWEST, GCUtil.HORI, 0.0, 0.0, 1, 1,
              InsetConstants.LEFT_INSETS);
          this.pnlPlant.add(resInfo, gbc);
          plantY++;
        break;

        default:
        break;
      }
    }

    pack();
  }
}
