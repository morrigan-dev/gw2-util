package de.morrigan.dev.gw2.client.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.AbstractLayoutCache;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.morrigan.dev.gw2.client.gui.components.FilterModel.TreeObject;
import de.morrigan.dev.gw2.resources.FontConstants;
import de.morrigan.dev.gw2.resources.ImageConstants;
import de.morrigan.dev.gw2.resources.ResourceManager;
import de.morrigan.dev.gw2.utils.observer.IObservable;
import de.morrigan.dev.gw2.utils.observer.IObserver;
import de.morrigan.dev.swing.GCUtil;
import de.morrigan.dev.swing.InsetConstants;
import de.morrigan.dev.utils.resources.FontManager;

public class FilterPanel extends JPanel implements IObserver {

  /** FontManager der verschiedene Schriftarten OS unabhängig bereitstellt */
  private static final FontManager FONT_MANAGER = FontManager.getInstance();

  private class CustomTreeUI extends BasicTreeUI {

    @Override
    protected AbstractLayoutCache.NodeDimensions createNodeDimensions() {
      return new NodeDimensionsHandler() {

        @Override
        public Rectangle getNodeDimensions(Object value, int row, int depth, boolean expanded, Rectangle size) {
          Rectangle dimensions = super.getNodeDimensions(value, row, depth, expanded, size);
          dimensions.width = LIST_WIDTH - getRowX(row, depth);
          return dimensions;
        }
      };
    }

    @Override
    protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
      // do nothing.
    }

    @Override
    protected void paintVerticalPartOfLeg(Graphics g, Rectangle clipBounds, Insets insets, TreePath path) {
      // do nothing.
    }
  }

  private class FilterRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Color getBackground() {
      return new Color(0, 0, 0, 0);
    }

    @Override
    public Color getBackgroundNonSelectionColor() {
      return new Color(0, 0, 0, 0);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, final boolean sel, boolean expanded,
        final boolean leaf, int row, boolean hasFocus) {
      final GW2Label lblRenderer = new GW2Label() {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g) {
          if (sel) {
            int width = getWidth();
            int height = getHeight();
            Paint paint;
            if (leaf) {
              paint = new GradientPaint(0, 0, new Color(111, 100, 70), width, height, new Color(111, 100,
                  70, 0));
            } else {
              paint = new GradientPaint(19, 0, new Color(111, 100, 70), width, height, new Color(111,
                  100, 70, 0));
            }

            ((Graphics2D) g).setPaint(paint);
            ((Graphics2D) g).fillRect(0, 0, width, height);
          }
          boolean opaque = isOpaque();
          setOpaque(false);
          super.paintComponent(g);
          setOpaque(opaque);
        }
      };
      TreeObject treeObject = (TreeObject) ((DefaultMutableTreeNode) value).getUserObject();
      lblRenderer.setIcon(treeObject.icon);
      lblRenderer.setText(treeObject.label);
      lblRenderer.setForeground(Color.WHITE);
      lblRenderer.setFont(FONT_MANAGER.getFont(FontConstants.MENOMONIA, 14f).get());
      if (sel) {
        lblRenderer.setOpaque(true);
        lblRenderer.setBackground(new Color(111, 100, 70));
      } else {
        lblRenderer.setOpaque(false);
      }
      return lblRenderer;
    }
  }

  private static final long serialVersionUID = 1L;

  /** Logger für Debugausgaben */
  private static final Logger LOG = LoggerFactory.getLogger(FilterPanel.class);

  /** Handel auf den LabelManager */
  private static final ResourceManager RESOURCE_MANAGER = ResourceManager.getInstance();

  /** Handel auf den ImageManager */
  private static final ImageConstants IMAGE_MANAGER = ImageConstants.getInstance();

  private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(50, 20);

  private static final int LIST_WIDTH = 250;

  private GW2MenuBar header;
  private JPanel pnlContent;

  // Labels
  private GW2Label lblAvailableElements;
  private GW2Label lblSelectedElements;
  // Buttons
  private JLabel btnMoveAllLeft;
  private JLabel btnMoveLeft;
  private JLabel btnMoveAllRight;
  private JLabel btnMoveRight;
  // Lists
  private JScrollPane scAvailable;
  private JTree lstAvailableElements;
  private JScrollPane scSelected;
  private JTree lstSelectedElements;
  // Panels
  private JPanel pnlMoveButtons;

  private FilterModel model = FilterModel.getInstance();

  public FilterPanel() {
    super();

    createGUI();
    layoutGUI();
    configureGUI();
    configureListener();
    updateLanguage();

    this.model.addObserver(this);
  }

  @Override
  public void update(IObservable obs, long updateFlag) {
    LOG.debug("obs: {}, updateFlag: {}", obs, updateFlag);
    if (obs instanceof FilterModel) {
      expandAll();
    }
  }

  protected void layoutGUI() {

    setLayout(new GridBagLayout());
    this.pnlContent.setLayout(new GridBagLayout());

    final GridBagConstraints gbc = new GridBagConstraints();

    // Movebuttons
    GCUtil.configGC(gbc, 0, 0, GCUtil.WEST, GCUtil.VERT, 0.0, 0.5, 1, 1, InsetConstants.NO_INSETS);
    this.pnlMoveButtons.add(new JLabel(), gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlMoveButtons.add(this.btnMoveAllRight, gbc);
    GCUtil.configGC(gbc, 0, 2, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlMoveButtons.add(this.btnMoveRight, gbc);
    GCUtil.configGC(gbc, 0, 3, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlMoveButtons.add(this.btnMoveLeft, gbc);
    GCUtil.configGC(gbc, 0, 4, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlMoveButtons.add(this.btnMoveAllLeft, gbc);
    GCUtil.configGC(gbc, 0, 5, GCUtil.WEST, GCUtil.VERT, 0.0, 0.5, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlMoveButtons.add(new JLabel(), gbc);

    this.scAvailable = new JScrollPane();
    this.scAvailable.setViewportView(this.lstAvailableElements);
    this.scAvailable.setPreferredSize(new Dimension(LIST_WIDTH, 10));
    this.scAvailable.setOpaque(false);
    this.scAvailable.getViewport().setOpaque(false);
    this.scAvailable.setBorder(null);
    this.scAvailable.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

    this.scSelected = new JScrollPane();
    this.scSelected.setViewportView(this.lstSelectedElements);
    this.scSelected.setPreferredSize(new Dimension(LIST_WIDTH, 10));
    this.scSelected.setOpaque(false);
    this.scSelected.getViewport().setOpaque(false);
    this.scSelected.setBorder(null);
    this.scSelected.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

    // Verfügbar
    GCUtil.configGC(gbc, 0, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlContent.add(this.lblAvailableElements, gbc);
    GCUtil.configGC(gbc, 0, 2, GCUtil.WEST, GCUtil.VERT, 0.0, 1.0, 1, 1, InsetConstants.TOP_INSETS);
    this.pnlContent.add(this.scAvailable, gbc);
    // Movebuttons
    GCUtil.configGC(gbc, 1, 2, GCUtil.WEST, GCUtil.VERT, 0.0, 1.0, 1, 1, InsetConstants.LT_INSETS);
    this.pnlContent.add(this.pnlMoveButtons, gbc);
    // Selektiert
    GCUtil.configGC(gbc, 2, 1, GCUtil.WEST, GCUtil.NONE, 0.0, 0.0, 1, 1, InsetConstants.LT_INSETS);
    this.pnlContent.add(this.lblSelectedElements, gbc);
    GCUtil.configGC(gbc, 2, 2, GCUtil.WEST, GCUtil.VERT, 0.0, 1.0, 1, 1, InsetConstants.LT_INSETS);
    this.pnlContent.add(this.scSelected, gbc);

    GCUtil.configGC(gbc, 0, 0, GCUtil.NORTH, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.header, gbc);
    GCUtil.configGC(gbc, 0, 1, GCUtil.NORTH, GCUtil.HORI, 1.0, 0.0, 1, 1, InsetConstants.NO_INSETS);
    add(this.pnlContent, gbc);
  }

  private void configureGUI() {
    setOpaque(false);
    this.pnlContent.setOpaque(false);
    this.pnlMoveButtons.setOpaque(false);

    this.btnMoveAllLeft.setPreferredSize(DEFAULT_BUTTON_SIZE);
    this.btnMoveLeft.setPreferredSize(DEFAULT_BUTTON_SIZE);
    this.btnMoveRight.setPreferredSize(DEFAULT_BUTTON_SIZE);
    this.btnMoveAllRight.setPreferredSize(DEFAULT_BUTTON_SIZE);

    Color bg = new Color(0, 0, 0, 0);
    this.btnMoveAllLeft.setBackground(bg);
    this.btnMoveLeft.setBackground(bg);
    this.btnMoveRight.setBackground(bg);
    this.btnMoveAllRight.setBackground(bg);

    FilterRenderer defaultTreeCellRenderer = new FilterRenderer();
    this.lstAvailableElements.setModel(this.model.getAvailableElementsModel());
    this.lstAvailableElements.setCellRenderer(defaultTreeCellRenderer);
    this.lstAvailableElements.setRootVisible(false);
    this.lstAvailableElements.setOpaque(false);
    this.lstAvailableElements.setUI(new CustomTreeUI());

    this.lstSelectedElements.setModel(this.model.getSelectedElementsModel());
    this.lstSelectedElements.setCellRenderer(defaultTreeCellRenderer);
    this.lstSelectedElements.setRootVisible(false);
    this.lstSelectedElements.setOpaque(false);
    this.lstSelectedElements.setUI(new CustomTreeUI());

    this.header.getContent().add(this.pnlContent);
    this.header.setExpanded(false);

    expandAll();
  }

  private void configureListener() {
    this.lstAvailableElements.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
          moveRight();
        }
      }
    });
    this.lstAvailableElements.addKeyListener(new KeyAdapter() {

      @Override
      public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
          int[] selectionRows = FilterPanel.this.lstAvailableElements.getSelectionRows();
          if (selectionRows.length > 0) {
            moveRight();
            FilterPanel.this.lstAvailableElements.setSelectionInterval(selectionRows[0], selectionRows[0]);
          }
        }
      }

    });
    this.lstSelectedElements.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
          moveLeft();
        }
      }
    });
    this.lstSelectedElements.addKeyListener(new KeyAdapter() {

      @Override
      public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
          int[] selectionRows = FilterPanel.this.lstSelectedElements.getSelectionRows();
          if (selectionRows.length > 0) {
            moveLeft();
            FilterPanel.this.lstSelectedElements.setSelectionInterval(selectionRows[0], selectionRows[0]);
          }
        }
      }
    });
    this.btnMoveRight.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        moveRight();
      }
    });
    this.btnMoveAllRight.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        moveAllRight();
      }
    });

    this.btnMoveLeft.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        moveLeft();
      }
    });

    this.btnMoveAllLeft.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        moveAllLeft();
      }
    });

  }

  private void createGUI() {
    this.header = new GW2MenuBar();
    this.pnlContent = new JPanel();

    this.lblAvailableElements = new GW2Label();
    this.lblSelectedElements = new GW2Label();

    this.pnlMoveButtons = new JPanel();
    this.pnlMoveButtons.setLayout(new GridBagLayout());

    // Movebuttons
    this.btnMoveAllLeft = new JLabel();
    this.btnMoveLeft = new JLabel();
    this.btnMoveRight = new JLabel();
    this.btnMoveAllRight = new JLabel();

    this.lstAvailableElements = new JTree();
    this.lstSelectedElements = new JTree();
  }

  private void expandAll() {
    int rowCount = this.lstAvailableElements.getRowCount();
    for (int i = rowCount; i >= 0; i--) {
      this.lstAvailableElements.expandRow(i);
    }
    rowCount = this.lstSelectedElements.getRowCount();
    for (int i = rowCount; i >= 0; i--) {
      this.lstSelectedElements.expandRow(i);
    }
  }

  private void moveAllLeft() {
    List<DefaultMutableTreeNode> selectedNodes = new ArrayList<>();
    this.model.fillSelectedLeafs(selectedNodes, (DefaultMutableTreeNode) this.lstSelectedElements.getModel()
        .getRoot());
    this.model.removeItems(selectedNodes);
  }

  private void moveAllRight() {
    List<DefaultMutableTreeNode> selectedNodes = new ArrayList<>();
    this.model.fillSelectedLeafs(selectedNodes, (DefaultMutableTreeNode) this.lstAvailableElements.getModel()
        .getRoot());
    this.model.addItems(selectedNodes);
  }

  private void moveLeft() {
    TreePath[] selectionPaths = FilterPanel.this.lstSelectedElements.getSelectionModel().getSelectionPaths();
    List<DefaultMutableTreeNode> selectedNodes = new ArrayList<>();
    for (TreePath treePath : selectionPaths) {
      DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath.getLastPathComponent();
      this.model.fillSelectedLeafs(selectedNodes, selectedItem);
    }
    this.model.removeItems(selectedNodes);
  }

  private void moveRight() {
    TreePath[] selectionPaths = FilterPanel.this.lstAvailableElements.getSelectionModel().getSelectionPaths();
    List<DefaultMutableTreeNode> selectedNodes = new ArrayList<>();
    for (TreePath treePath : selectionPaths) {
      DefaultMutableTreeNode selectedItem = (DefaultMutableTreeNode) treePath.getLastPathComponent();
      this.model.fillSelectedLeafs(selectedNodes, selectedItem);
    }
    this.model.addItems(selectedNodes);
  }

  private void updateLanguage() {
    this.header.setHeadertext(RESOURCE_MANAGER.getLabel("filter"));

    this.lblAvailableElements.setText(RESOURCE_MANAGER.getLabel("availableElements"));
    this.lblSelectedElements.setText(RESOURCE_MANAGER.getLabel("selectedElements"));

    int iconSize = 25;
    this.btnMoveAllLeft.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.ARROWS_LEFT_ICON, iconSize, iconSize));
    this.btnMoveLeft.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.ARROW_LEFT_ICON, iconSize, iconSize));
    this.btnMoveAllRight.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.ARROWS_RIGHT_ICON, iconSize, iconSize));
    this.btnMoveRight.setIcon(IMAGE_MANAGER.getImageIcon(ImageConstants.ARROW_RIGHT_ICON, iconSize, iconSize));
  }
}
