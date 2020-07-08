
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
//import java.io.PrintStream;
import java.net.MalformedURLException;
import javax.swing.*;
import javax.swing.Timer;
import java.io.*;
import java.net.URL;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 *
 * Created on Apr 29, 2010, 8:45:38 PM
 */
/**
 *
 * @author bare bones
 */
public class Main extends javax.swing.JFrame
{
  private static boolean computer = false, compVsComp = false, delimHit, doubleJumping = false, fromSelected = false, isSetUp = false, move = true, named1 = false, named2 = false, quit = false, turnOver = false, winner = false;
  private static char mark, notMark, mark1 = 'b', mark2 = 'r', where, EMPTY = '*';
  public static Color color, color1 = Color.getHSBColor(0, 0, (float)0.1), color2 = Color.getHSBColor((float)0.59166666666666666666666666666667, (float).58, (float).74), notColor, colorX = color2;
  private static Dimension screen = new Dimension(Toolkit.getDefaultToolkit().getScreenSize()), dim = new Dimension();
  public static final int SIZE = /*Integer.parseInt(JOptionPane.showInputDialog(null, "size?", */ 8/*))*/, WIN_WIDTH = 640, WIN_HEIGHT = 640;
  private static int turns, to1, to2, from1, from2, pieces1, pieces2, piece, notPiece, p1wins, p2wins, progress, numOfPlayers = 2, screenWidth = (int)screen.width, screenHeight = (int)screen.height, width = WIN_WIDTH / 2, height = WIN_HEIGHT / 2;
  private static int[][] priority;
//  private static javax.swing.JToggleButton[][] boardButtons = new javax.swing.JToggleButton()[SIZE][SIZE];
  private static Main main;
  private static PrintStream printer;
  private static Rectangle placementRect = new Rectangle((screenWidth / 2) - windowWidth(), (screenHeight / 2) - windowHeight(), 0,0);
  public static String console = trans("Please set up your game.", "Bitte richten Sie ihr Spiel ein.", "Por favor, configure su juego."), error, header = trans("Please set up the game in the \"Options\" tab", "richten sie das spiel unter dem register \"Optionen\" ein", "Por favor, configurar el juego en la ficha de \"Options\""), language, name, name1 = System.getProperty("user.name"), name2 = "Player 2", notName, /*square = "", readme = readme(),*/ siteName = "http://supuh.wikia.com/wiki/BHC/", spareStr = "", turnOutput = "<html><center>" + header + "</center></html>";
  public static final String TITLE = GameProps.getProp("title"), VERSION = GameProps.getProp("version"), SAVEFILE = "CheckersSave.dll", ERROR_FILE = "errors.txt";
  public static String[] arguments;
  private static Timer timer2 = new Timer(50, new java.awt.event.ActionListener()
                                         {
                                           public void actionPerformed(java.awt.event.ActionEvent evt)
                                           {
//                                             System.out.println(dim.toString());
                                             if (dim.getWidth() < WIN_WIDTH || dim.getHeight() < WIN_HEIGHT)
                                             {
                                               if (dim.getWidth() < WIN_WIDTH)
                                               {
                                                 dim = new Dimension((int)(WIN_WIDTH - width), (int)dim.getHeight());
                                                 width = (int)(width / 2);
                                               }
                                               else
                                               {
                                                 dim = new Dimension((int)dim.getWidth(), (int)(WIN_HEIGHT - height));
                                                 height = (int)(height / 2);
                                               }
                                               main.setSize(dim);
                                               main.repaint();
                                             }
                                             else
                                             {
                                               stopTimer2();
                                               main.setMinimumSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
                                             }
                                           }
                                         });
  public static char[][] board = {{(char)(mark1 - 32), EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY},
                                  {EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY, mark1},
                                  {mark1, EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY},
                                  {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                                  {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                                  {EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2},
                                  {mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY},
                                  {EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2}};
  public static Color[][] boardColor = {{color1, colorX, color1, colorX, color1, colorX, color1, colorX},
                                        {colorX, color1, colorX, color1, colorX, color1, colorX, color1},
                                        {color1, colorX, color1, colorX, color1, colorX, color1, colorX},
                                        {colorX, colorX, colorX, colorX, colorX, colorX, colorX, colorX},
                                        {colorX, colorX, colorX, colorX, colorX, colorX, colorX, colorX},
                                        {colorX, color2, colorX, color2, colorX, color2, colorX, color2},
                                        {color2, colorX, color2, colorX, color2, colorX, color2, colorX},
                                        {colorX, color2, colorX, color2, colorX, color2, colorX, color2}};
  public static final int BOARD_WIDTH = 256, BOARD_HEIGHT = 256, rows = 8, columns = 8;
  public static java.awt.Color p1Color = new java.awt.Color(255, 000, 000), p2Color = new java.awt.Color(000, 000, 255), noColor = new java.awt.Color(
  255, 255, 255), tempColor1 = color1, tempColor2 = color2;
  public static java.awt.Color[][] colorBoard;

  private static void setup()
  {/*TC*/
    System.out.println("BEGIN setup()");
//    JOptionPane.showMessageDialog(null, "1", "setup", 1);
    if (move)
    {
      color = color1;
      mark = mark1;
      name = name1;
      notName = name2;
      notColor = color2;
      notMark = mark2;
      notPiece = pieces2;
      piece = pieces1;
    }
    else
    {
      color = color2;
      mark = mark2;
      name = name2;
      notName = name1;
      notColor = color1;
      notMark = mark1;
      notPiece = pieces1;
      piece = pieces2;
    }
//    JOptionPane.showMessageDialog(null, "2", "setup", 1);
    System.err.println("numOfPlayers set to " + numOfPlayers);
//    JOptionPane.showMessageDialog(null, "3", "setup", 1);
    if (numOfPlayers == 1)
    {
      name2 = (compVsComp ? "Computer 2" : trans("the computer", "der computer", "la computadora"));
      color2 = Color.RED;
      mark2 = 'r';
    }
//    JOptionPane.showMessageDialog(null, "4", "setup", 1);
    if (numOfPlayers == 0)
    {
      name1 = "Computer 1";
      color1 = Color.BLACK;
      mark1 = 'b';
    }
//    JOptionPane.showMessageDialog(null, "5", "setup", 1);
    char[][] newBoard = {{(char)(mark1 - 32), EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY},
                         {EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY, mark1},
                         {mark1, EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY},
                         {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                         {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                         {EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2},
                         {mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY},
                         {EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2}};
//    JOptionPane.showMessageDialog(null, "6", "setup", 1);
    Color[][] newBoardColor = {{color1, colorX, color1, colorX, color1, colorX, color1, colorX},
                               {colorX, color1, colorX, color1, colorX, color1, colorX, color1},
                               {color1, colorX, color1, colorX, color1, colorX, color1, colorX},
                               {colorX, colorX, colorX, colorX, colorX, colorX, colorX, colorX},
                               {colorX, colorX, colorX, colorX, colorX, colorX, colorX, colorX},
                               {colorX, color2, colorX, color2, colorX, color2, colorX, color2},
                               {color2, colorX, color2, colorX, color2, colorX, color2, colorX},
                               {colorX, color2, colorX, color2, colorX, color2, colorX, color2}};
//    JOptionPane.showMessageDialog(null, "7", "setup", 1);
    board = newBoard;
    boardColor = newBoardColor;
//    JOptionPane.showMessageDialog(null, "8", "setup", 1);
    main = new Main();
//    JOptionPane.showMessageDialog(null, "9", "setup", 1);
    /*main.*/refreshBoard();
//    JOptionPane.showMessageDialog(null, "10", "setup", 1);
    System.out.println("[" + screen.width + ", " + screen.height + "]");
    /*TC*/    System.out.println("END setup()");
  }

  public static void setP1Color(Color input)
  {
    tempColor1 = input;
    color1DisplayPanel.setBackground(tempColor1);
    color1DisplayPanel.repaint();
    System.err.println("Player 1 picked the color " + tempColor1.toString());
  }

  public static void setP2Color(Color input)
  {
    tempColor2 = input;
    color2DisplayPanel.setBackground(tempColor2);
    color2DisplayPanel.repaint();
//    tempColor2 = new Color();
    System.err.println("Player 2 picked the color " + tempColor2.toString());
  }

  private static String location(int f1, int f2)
  {/*TC*/ System.err.println("location(" + ((char)(f2 + 65) + "-" + (f1 + 1)) + ")");
    return (char)(f2 + 65) + "-" + (f1 + 1);
  }

  /** Creates new form Main */
  public Main()
  {
    initComponents();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        readmeFrame = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        readmeHeader = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jEditorPane3 = new javax.swing.JEditorPane();
        readmeContent = new javax.swing.JPanel();
        readmeContentTabs = new javax.swing.JTabbedPane();
        readmeHowToRunTab = new javax.swing.JScrollPane();
        jEditorPane4 = new javax.swing.JEditorPane();
        readmeHowToPlayTab = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        readmeVersionChangesTab = new javax.swing.JScrollPane();
        readmeTheJRETab = new javax.swing.JTabbedPane();
        JREInformattionTab = new javax.swing.JPanel();
        JREInstallationTab = new javax.swing.JTabbedPane();
        JREInstallationOracleTab = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        JREInstallationOracleStep1Tab = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep1Label2 = new javax.swing.JLabel();
        copyOracleJREInstallerPageURLButton = new javax.swing.JButton();
        JREInstallationSupuhWikiStep2Tab1 = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep1Label3 = new javax.swing.JLabel();
        JREInstallationSupuhWikiStep3Tab1 = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep3Label1 = new javax.swing.JLabel();
        JREInstallationSupuhWikiStep4Tab1 = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep4Label1 = new javax.swing.JLabel();
        JREInstallationSupuhWikiStep5Tab1 = new javax.swing.JScrollPane();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        JREInstallationSupuhWikiTab = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        JREInstallationSupuhWikiStep1Tab = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep1Label4 = new javax.swing.JLabel();
        copySupuhWikiJREInstallerPageURLButton = new javax.swing.JButton();
        JREInstallationSupuhWikiStep2Tab = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep1Label1 = new javax.swing.JLabel();
        JREInstallationSupuhWikiStep3Tab = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep3Label = new javax.swing.JLabel();
        JREInstallationSupuhWikiStep4Tab = new javax.swing.JScrollPane();
        JREInstallationSupuhWikiStep4Label = new javax.swing.JLabel();
        JREInstallationSupuhWikiStep5Tab = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        readmeContactTab = new javax.swing.JScrollPane();
        aboutFrame = new javax.swing.JFrame();
        aboutLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        testBoardFrame = new javax.swing.JFrame();
        testBoardPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        optionTab = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        applyOptionsButton = new javax.swing.JButton();
        name2InputPanel = new javax.swing.JPanel();
        name2Input = new javax.swing.JTextField();
        name1InputPanel = new javax.swing.JPanel();
        name1Input = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        name1ColorChangerButton = new javax.swing.JButton();
        color1DisplayPanel = new javax.swing.JPanel();
        color2DisplayPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        numOfPlayersSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        gameTab = new javax.swing.JPanel();
        boardPanel = new javax.swing.JPanel();
        A1 = new javax.swing.JButton();
        B1 = new javax.swing.JButton();
        C1 = new javax.swing.JButton();
        D1 = new javax.swing.JButton();
        E1 = new javax.swing.JButton();
        F1 = new javax.swing.JButton();
        G1 = new javax.swing.JButton();
        H1 = new javax.swing.JButton();
        A2 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        C2 = new javax.swing.JButton();
        D2 = new javax.swing.JButton();
        E2 = new javax.swing.JButton();
        F2 = new javax.swing.JButton();
        G2 = new javax.swing.JButton();
        H2 = new javax.swing.JButton();
        A3 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        C3 = new javax.swing.JButton();
        D3 = new javax.swing.JButton();
        E3 = new javax.swing.JButton();
        F3 = new javax.swing.JButton();
        G3 = new javax.swing.JButton();
        H3 = new javax.swing.JButton();
        A4 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        C4 = new javax.swing.JButton();
        D4 = new javax.swing.JButton();
        E4 = new javax.swing.JButton();
        F4 = new javax.swing.JButton();
        G4 = new javax.swing.JButton();
        H4 = new javax.swing.JButton();
        A5 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        C5 = new javax.swing.JButton();
        D5 = new javax.swing.JButton();
        E5 = new javax.swing.JButton();
        F5 = new javax.swing.JButton();
        G5 = new javax.swing.JButton();
        H5 = new javax.swing.JButton();
        A6 = new javax.swing.JButton();
        B6 = new javax.swing.JButton();
        C6 = new javax.swing.JButton();
        D6 = new javax.swing.JButton();
        E6 = new javax.swing.JButton();
        F6 = new javax.swing.JButton();
        G6 = new javax.swing.JButton();
        H6 = new javax.swing.JButton();
        A7 = new javax.swing.JButton();
        B7 = new javax.swing.JButton();
        C7 = new javax.swing.JButton();
        D7 = new javax.swing.JButton();
        E7 = new javax.swing.JButton();
        F7 = new javax.swing.JButton();
        G7 = new javax.swing.JButton();
        H7 = new javax.swing.JButton();
        A8 = new javax.swing.JButton();
        B8 = new javax.swing.JButton();
        C8 = new javax.swing.JButton();
        D8 = new javax.swing.JButton();
        E8 = new javax.swing.JButton();
        F8 = new javax.swing.JButton();
        G8 = new javax.swing.JButton();
        H8 = new javax.swing.JButton();
        colLabelA = new javax.swing.JLabel();
        colLabelB = new javax.swing.JLabel();
        colLabelC = new javax.swing.JLabel();
        colLabelD = new javax.swing.JLabel();
        colLabelE = new javax.swing.JLabel();
        colLabelF = new javax.swing.JLabel();
        colLabelG = new javax.swing.JLabel();
        colLabelH = new javax.swing.JLabel();
        rowLabel1 = new javax.swing.JLabel();
        rowLabel2 = new javax.swing.JLabel();
        rowLabel3 = new javax.swing.JLabel();
        rowLabel4 = new javax.swing.JLabel();
        rowLabel5 = new javax.swing.JLabel();
        rowLabel6 = new javax.swing.JLabel();
        rowLabel7 = new javax.swing.JLabel();
        rowLabel8 = new javax.swing.JLabel();
        player1NameLabel = new javax.swing.JLabel();
        player2NameLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        forfeitButton = new javax.swing.JButton();
        turnOutputLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        scoreboardTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        consolePane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleOutput = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        forfeitMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        quitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        supuhWikiMenuItem = new javax.swing.JMenuItem();
        readmeMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        readmeFrame.setTitle("Readme for " + TITLE + ", version " + VERSION);
        readmeFrame.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        readmeFrame.setLocationByPlatform(true);
        readmeFrame.setMinimumSize(new java.awt.Dimension(600, 480));

        jPanel5.setOpaque(false);

        readmeHeader.setOpaque(false);
        readmeHeader.setPreferredSize(new java.awt.Dimension(600, 186));

        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane6.setOpaque(false);
        jScrollPane6.setPreferredSize(new java.awt.Dimension(116, 22));

        jEditorPane3.setEditable(false);
        jEditorPane3.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        jEditorPane3.setText(readme());
        jEditorPane3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jEditorPane3.setOpaque(false);
        jScrollPane6.setViewportView(jEditorPane3);

        javax.swing.GroupLayout readmeHeaderLayout = new javax.swing.GroupLayout(readmeHeader);
        readmeHeader.setLayout(readmeHeaderLayout);
        readmeHeaderLayout.setHorizontalGroup(
            readmeHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        readmeHeaderLayout.setVerticalGroup(
            readmeHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
        );

        readmeContent.setOpaque(false);

        readmeHowToRunTab.setOpaque(false);

        jEditorPane4.setEditable(false);
        jEditorPane4.setOpaque(false);
        readmeHowToRunTab.setViewportView(jEditorPane4);

        readmeContentTabs.addTab("How to Run", readmeHowToRunTab);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        readmeHowToPlayTab.addTab("Setting Up", jPanel6);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Rules", jPanel8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Input", jPanel9);

        readmeHowToPlayTab.addTab("Making a Move", jTabbedPane2);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        readmeHowToPlayTab.addTab("Winning the Game", jPanel7);

        readmeContentTabs.addTab("How to Play", readmeHowToPlayTab);
        readmeHowToPlayTab.getAccessibleContext().setAccessibleName("Making a Move");

        readmeVersionChangesTab.setOpaque(false);
        readmeContentTabs.addTab("Version Changes", readmeVersionChangesTab);

        JREInformattionTab.setOpaque(false);

        javax.swing.GroupLayout JREInformattionTabLayout = new javax.swing.GroupLayout(JREInformattionTab);
        JREInformattionTab.setLayout(JREInformattionTabLayout);
        JREInformattionTabLayout.setHorizontalGroup(
            JREInformattionTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        JREInformattionTabLayout.setVerticalGroup(
            JREInformattionTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        readmeTheJRETab.addTab("Information", JREInformattionTab);

        JREInstallationOracleTab.setOpaque(false);

        jTabbedPane4.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        JREInstallationOracleStep1Tab.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Go to http://tinyurl.com/DLJavaJRE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N

        JREInstallationSupuhWikiStep1Label2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep1.png"))); // NOI18N
        jScrollPane5.setViewportView(JREInstallationSupuhWikiStep1Label2);

        copyOracleJREInstallerPageURLButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        copyOracleJREInstallerPageURLButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editcopy.png"))); // NOI18N
        copyOracleJREInstallerPageURLButton.setText("Copy this URL");
        copyOracleJREInstallerPageURLButton.setToolTipText("Copy the url to this webpage to the clipboard");
        copyOracleJREInstallerPageURLButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        copyOracleJREInstallerPageURLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyOracleJREInstallerPageURLButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JREInstallationOracleStep1TabLayout = new javax.swing.GroupLayout(JREInstallationOracleStep1Tab);
        JREInstallationOracleStep1Tab.setLayout(JREInstallationOracleStep1TabLayout);
        JREInstallationOracleStep1TabLayout.setHorizontalGroup(
            JREInstallationOracleStep1TabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
            .addComponent(copyOracleJREInstallerPageURLButton, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        );
        JREInstallationOracleStep1TabLayout.setVerticalGroup(
            JREInstallationOracleStep1TabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JREInstallationOracleStep1TabLayout.createSequentialGroup()
                .addComponent(copyOracleJREInstallerPageURLButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("tab6", JREInstallationOracleStep1Tab);

        JREInstallationSupuhWikiStep2Tab1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Click the link with the name of your Operating System.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep2Tab1.setOpaque(false);

        JREInstallationSupuhWikiStep1Label3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep2.png"))); // NOI18N
        JREInstallationSupuhWikiStep2Tab1.setViewportView(JREInstallationSupuhWikiStep1Label3);

        jTabbedPane4.addTab("Step 2", JREInstallationSupuhWikiStep2Tab1);

        JREInstallationSupuhWikiStep3Tab1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Download the File", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep3Tab1.setOpaque(false);

        JREInstallationSupuhWikiStep3Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep3.png"))); // NOI18N
        JREInstallationSupuhWikiStep3Tab1.setViewportView(JREInstallationSupuhWikiStep3Label1);

        jTabbedPane4.addTab("Step 3", JREInstallationSupuhWikiStep3Tab1);

        JREInstallationSupuhWikiStep4Tab1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Run and install the files", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep4Tab1.setOpaque(false);

        JREInstallationSupuhWikiStep4Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep4.png"))); // NOI18N
        JREInstallationSupuhWikiStep4Tab1.setViewportView(JREInstallationSupuhWikiStep4Label1);

        jTabbedPane4.addTab("Step 4", JREInstallationSupuhWikiStep4Tab1);

        JREInstallationSupuhWikiStep5Tab1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "You may now run any .class file! Have fun!", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep5Tab1.setOpaque(false);

        jButton2.setText("<html><span style=\"font-size:18pt;\">This program does not require a JRE.<br>It should help you, though :3</span></html>");
        jButton2.setToolTipText("<html>Clicking this button doesn't do ANYTHING.<br>Fun to click, though :3</html>");
        JREInstallationSupuhWikiStep5Tab1.setViewportView(jButton2);

        jTabbedPane4.addTab("Step 5", JREInstallationSupuhWikiStep5Tab1);

        jScrollPane4.setOpaque(false);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1) Go to http://tinyurl.com/DLJavaJRE", "2) Select your operating system from the drop-down menu labelled \"Platform:\"", "3) Check the checkbox saying that you \"agree to the  Java SE Runtime Environment 6u18 with JavaFX 1 License Agreement .\"", "4) DO NOT FILL IN ANY INFORMATION OR REGISTER", "5) Click the red \"Continue »\" button", "    * If you selected \"Linux\", download the file called \"jre-6u18-linux-i586.bin\", which should be around 20 MB.", "    * If you selected \"Linux Intel Itanium\", download the file called \"jre-6u18-linux-ia64.bin\", which should be around 20 MB.", "    * If you selected \"Linux x64\", download the file called \"jre-6u18-linux-x64.bin\", which should be around 20 MB.", "    * If you selected \"Solaris SPARC\", download the file called \"jre-6u18-solaris-sparc.sh\", which should be around 25 MB.", "    * If you selected \"Solaris x64\", download the file called \"jre-6u18-solaris-x64.sh\", which should be around 7 MB.", "    * If you selected \"Solaris x86\", download the file called \"jre-6u18-solaris-1586.sh\", which should be around 19 MB.", "    * If you selected \"Windows\", download the file called \"jre-6u18-windows-i586.exe\", which should be around 15 MB.", "    * If you selected \"Windows Intel Itanium\", download the file called \"jre-6u18-windows-ia64.exe\", which should be around 16 MB.", "    * If you selected \"Windows x64\", download the file called     \"jre-6u18-windows-x64.exe\", which should be around 16 MB.", "6) Run this file like a normal program, and it will    automatically install the Java RE for you.", "7) You may now play Checkers. Have fun! </html>" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jList2.setDropMode(javax.swing.DropMode.ON_OR_INSERT);
        jList2.setOpaque(false);
        jScrollPane4.setViewportView(jList2);

        javax.swing.GroupLayout JREInstallationOracleTabLayout = new javax.swing.GroupLayout(JREInstallationOracleTab);
        JREInstallationOracleTab.setLayout(JREInstallationOracleTabLayout);
        JREInstallationOracleTabLayout.setHorizontalGroup(
            JREInstallationOracleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JREInstallationOracleTabLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
        );
        JREInstallationOracleTabLayout.setVerticalGroup(
            JREInstallationOracleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
            .addGroup(JREInstallationOracleTabLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        JREInstallationTab.addTab("", new javax.swing.ImageIcon(getClass().getResource("/129x16px-Oracle_logo_svg.png")), JREInstallationOracleTab, "How to Download from Oracle"); // NOI18N

        JREInstallationSupuhWikiTab.setOpaque(false);

        jTabbedPane3.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        JREInstallationSupuhWikiStep1Tab.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Go to http://supuh.wikia.com/wiki/JRE_Installers", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N

        JREInstallationSupuhWikiStep1Label4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep1.png"))); // NOI18N
        jScrollPane7.setViewportView(JREInstallationSupuhWikiStep1Label4);

        copySupuhWikiJREInstallerPageURLButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        copySupuhWikiJREInstallerPageURLButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editcopy.png"))); // NOI18N
        copySupuhWikiJREInstallerPageURLButton.setText("Copy this URL");
        copySupuhWikiJREInstallerPageURLButton.setToolTipText("Copy the url to this webpage to the clipboard");
        copySupuhWikiJREInstallerPageURLButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        copySupuhWikiJREInstallerPageURLButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copySupuhWikiJREInstallerPageURLButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JREInstallationSupuhWikiStep1TabLayout = new javax.swing.GroupLayout(JREInstallationSupuhWikiStep1Tab);
        JREInstallationSupuhWikiStep1Tab.setLayout(JREInstallationSupuhWikiStep1TabLayout);
        JREInstallationSupuhWikiStep1TabLayout.setHorizontalGroup(
            JREInstallationSupuhWikiStep1TabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
            .addComponent(copySupuhWikiJREInstallerPageURLButton, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );
        JREInstallationSupuhWikiStep1TabLayout.setVerticalGroup(
            JREInstallationSupuhWikiStep1TabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JREInstallationSupuhWikiStep1TabLayout.createSequentialGroup()
                .addComponent(copySupuhWikiJREInstallerPageURLButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Step 1", JREInstallationSupuhWikiStep1Tab);

        JREInstallationSupuhWikiStep2Tab.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Click the link with the name of your Operating System.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep2Tab.setOpaque(false);

        JREInstallationSupuhWikiStep1Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep2.png"))); // NOI18N
        JREInstallationSupuhWikiStep2Tab.setViewportView(JREInstallationSupuhWikiStep1Label1);

        jTabbedPane3.addTab("Step 2", JREInstallationSupuhWikiStep2Tab);

        JREInstallationSupuhWikiStep3Tab.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Download the File", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep3Tab.setOpaque(false);

        JREInstallationSupuhWikiStep3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep3.png"))); // NOI18N
        JREInstallationSupuhWikiStep3Tab.setViewportView(JREInstallationSupuhWikiStep3Label);

        jTabbedPane3.addTab("Step 3", JREInstallationSupuhWikiStep3Tab);

        JREInstallationSupuhWikiStep4Tab.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Run and install the files", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep4Tab.setOpaque(false);

        JREInstallationSupuhWikiStep4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/JREInstallationSupuhWikiStep4.png"))); // NOI18N
        JREInstallationSupuhWikiStep4Tab.setViewportView(JREInstallationSupuhWikiStep4Label);

        jTabbedPane3.addTab("Step 4", JREInstallationSupuhWikiStep4Tab);

        JREInstallationSupuhWikiStep5Tab.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "You may now run any .class file! Have fun!", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Semibold", 0, 18))); // NOI18N
        JREInstallationSupuhWikiStep5Tab.setOpaque(false);

        jButton1.setText("<html><span style=\"font-size:18pt;\">This program does not require a JRE.<br>It should help you, though :3</span></html>");
        jButton1.setToolTipText("<html>Clicking this button doesn't do ANYTHING.<br>Fun to click, though :3</html>");
        JREInstallationSupuhWikiStep5Tab.setViewportView(jButton1);

        jTabbedPane3.addTab("Step 5", JREInstallationSupuhWikiStep5Tab);

        javax.swing.GroupLayout JREInstallationSupuhWikiTabLayout = new javax.swing.GroupLayout(JREInstallationSupuhWikiTab);
        JREInstallationSupuhWikiTab.setLayout(JREInstallationSupuhWikiTabLayout);
        JREInstallationSupuhWikiTabLayout.setHorizontalGroup(
            JREInstallationSupuhWikiTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
        );
        JREInstallationSupuhWikiTabLayout.setVerticalGroup(
            JREInstallationSupuhWikiTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );

        JREInstallationTab.addTab("", new javax.swing.ImageIcon(getClass().getResource("/SupuhWiki - 66x16.png")), JREInstallationSupuhWikiTab, "How to Download from SupuhWiki"); // NOI18N

        readmeTheJRETab.addTab("Installation", JREInstallationTab);

        readmeContentTabs.addTab("The JRE", readmeTheJRETab);

        readmeContactTab.setOpaque(false);
        readmeContentTabs.addTab("CONTACT", readmeContactTab);

        javax.swing.GroupLayout readmeContentLayout = new javax.swing.GroupLayout(readmeContent);
        readmeContent.setLayout(readmeContentLayout);
        readmeContentLayout.setHorizontalGroup(
            readmeContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(readmeContentTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        readmeContentLayout.setVerticalGroup(
            readmeContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(readmeContentTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(readmeHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(readmeContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(readmeHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(readmeContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout readmeFrameLayout = new javax.swing.GroupLayout(readmeFrame.getContentPane());
        readmeFrame.getContentPane().setLayout(readmeFrameLayout);
        readmeFrameLayout.setHorizontalGroup(
            readmeFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        readmeFrameLayout.setVerticalGroup(
            readmeFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        aboutFrame.setTitle(TITLE + " - About");
        aboutFrame.setAlwaysOnTop(true);
        aboutFrame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        aboutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        aboutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BHC logo.png"))); // NOI18N
        aboutLabel.setText("<html>Version <b>" + VERSION + "</b><br>Copyright  Blue Husky Gaming ©2010</html>");
        aboutLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        aboutLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        aboutLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout aboutFrameLayout = new javax.swing.GroupLayout(aboutFrame.getContentPane());
        aboutFrame.getContentPane().setLayout(aboutFrameLayout);
        aboutFrameLayout.setHorizontalGroup(
            aboutFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aboutLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
        );
        aboutFrameLayout.setVerticalGroup(
            aboutFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aboutLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout testBoardPanelLayout = new javax.swing.GroupLayout(testBoardPanel);
        testBoardPanel.setLayout(testBoardPanelLayout);
        testBoardPanelLayout.setHorizontalGroup(
            testBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        testBoardPanelLayout.setVerticalGroup(
            testBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout testBoardFrameLayout = new javax.swing.GroupLayout(testBoardFrame.getContentPane());
        testBoardFrame.getContentPane().setLayout(testBoardFrameLayout);
        testBoardFrameLayout.setHorizontalGroup(
            testBoardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(testBoardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(testBoardFrameLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(testBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        testBoardFrameLayout.setVerticalGroup(
            testBoardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(testBoardFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(testBoardFrameLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(testBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(TITLE + " - " + header);
        setMinimumSize(new java.awt.Dimension(640, 640));
        setName("Blue Husky's Checkers"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(128, 128));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(128, 128));

        optionTab.setOpaque(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        applyOptionsButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        applyOptionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/apply32.png"))); // NOI18N
        applyOptionsButton.setText("<html><center>Apply changes and start a new game</center></html>");
        applyOptionsButton.setToolTipText("Clicking this button will start a new game with applied settings");
        applyOptionsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        applyOptionsButton.setFocusable(false);
        applyOptionsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        applyOptionsButton.setIconTextGap(5);
        applyOptionsButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        applyOptionsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        applyOptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyOptionsButtonActionPerformed(evt);
            }
        });

        name2InputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Player 2 name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N
        name2InputPanel.setOpaque(false);

        name2Input.setText(name2);

        javax.swing.GroupLayout name2InputPanelLayout = new javax.swing.GroupLayout(name2InputPanel);
        name2InputPanel.setLayout(name2InputPanelLayout);
        name2InputPanelLayout.setHorizontalGroup(
            name2InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(name2Input, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
        );
        name2InputPanelLayout.setVerticalGroup(
            name2InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(name2InputPanelLayout.createSequentialGroup()
                .addComponent(name2Input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        name1InputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Player 1 name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 11))); // NOI18N
        name1InputPanel.setOpaque(false);

        name1Input.setText(name1);

        javax.swing.GroupLayout name1InputPanelLayout = new javax.swing.GroupLayout(name1InputPanel);
        name1InputPanel.setLayout(name1InputPanelLayout);
        name1InputPanelLayout.setHorizontalGroup(
            name1InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(name1Input, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
        );
        name1InputPanelLayout.setVerticalGroup(
            name1InputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(name1Input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);

        name1ColorChangerButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        name1ColorChangerButton.setText("Choose Colors");
        name1ColorChangerButton.setToolTipText("Choose a precise color for each player");
        name1ColorChangerButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        name1ColorChangerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name1ColorChangerButtonActionPerformed(evt);
            }
        });

        color1DisplayPanel.setBackground(color1);
        color1DisplayPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        color1DisplayPanel.setToolTipText(name1 + "'s color");

        javax.swing.GroupLayout color1DisplayPanelLayout = new javax.swing.GroupLayout(color1DisplayPanel);
        color1DisplayPanel.setLayout(color1DisplayPanelLayout);
        color1DisplayPanelLayout.setHorizontalGroup(
            color1DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        color1DisplayPanelLayout.setVerticalGroup(
            color1DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        color2DisplayPanel.setBackground(color2);
        color2DisplayPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        color2DisplayPanel.setToolTipText(name2 + "'s color");

        javax.swing.GroupLayout color2DisplayPanelLayout = new javax.swing.GroupLayout(color2DisplayPanel);
        color2DisplayPanel.setLayout(color2DisplayPanelLayout);
        color2DisplayPanelLayout.setHorizontalGroup(
            color2DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );
        color2DisplayPanelLayout.setVerticalGroup(
            color2DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(color1DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(color2DisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(name1ColorChangerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(name1ColorChangerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(color2DisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(color1DisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setToolTipText(/*"slide to pick the number of human players"*/ "Your version of this program is not capable of this operation, yet");
        jPanel2.setEnabled(false);
        jPanel2.setOpaque(false);

        numOfPlayersSlider.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        numOfPlayersSlider.setMajorTickSpacing(1);
        numOfPlayersSlider.setMaximum(2);
        numOfPlayersSlider.setPaintLabels(true);
        numOfPlayersSlider.setPaintTicks(true);
        numOfPlayersSlider.setSnapToTicks(true);
        numOfPlayersSlider.setToolTipText("slide to pick the number of human players");
        numOfPlayersSlider.setValue(numOfPlayers);
        numOfPlayersSlider.setEnabled(false);
        numOfPlayersSlider.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Number of Players");
        jLabel1.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(numOfPlayersSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(numOfPlayersSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(name2InputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(name1InputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(applyOptionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name1InputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name2InputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyOptionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
        );

        jPanel2.getAccessibleContext().setAccessibleName("numOfPlayersSlider");

        jLabel2.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BHC logo.png"))); // NOI18N

        javax.swing.GroupLayout optionTabLayout = new javax.swing.GroupLayout(optionTab);
        optionTab.setLayout(optionTabLayout);
        optionTabLayout.setHorizontalGroup(
            optionTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionTabLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        optionTabLayout.setVerticalGroup(
            optionTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Options", new javax.swing.ImageIcon(getClass().getResource("/configure.png")), optionTab, "Set options for the game"); // NOI18N

        gameTab.setEnabled(isSetUp);
        gameTab.setOpaque(false);

        boardPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boardPanel.setOpaque(false);
        boardPanel.setLayout(new java.awt.GridBagLayout());

        A1.setBackground(boardColor(0,0));
        A1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        A1.setForeground(new java.awt.Color(51, 51, 51));
        A1.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(0,0) ? "/none32.png" : (isKing(0,0) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        A1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        A1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A1.setMaximumSize(boardPieceDimension());
        A1.setMinimumSize(boardPieceDimension());
        A1.setPreferredSize(boardPieceDimension());
        A1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        boardPanel.add(A1, gridBagConstraints);

        B1.setBackground(new java.awt.Color(26, 26, 26));
        B1.setEnabled(false);
        B1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B1.setMaximumSize(boardPieceDimension());
        B1.setMinimumSize(boardPieceDimension());
        B1.setPreferredSize(boardPieceDimension());
        B1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        boardPanel.add(B1, gridBagConstraints);

        C1.setBackground(boardColor(0,2));
        C1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        C1.setForeground(new java.awt.Color(51, 51, 51));
        C1.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(0,2) ? "/none32.png" : (isKing(0,2) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        C1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        C1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C1.setMaximumSize(boardPieceDimension());
        C1.setMinimumSize(boardPieceDimension());
        C1.setPreferredSize(boardPieceDimension());
        C1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        boardPanel.add(C1, gridBagConstraints);

        D1.setBackground(new java.awt.Color(26, 26, 26));
        D1.setEnabled(false);
        D1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D1.setMaximumSize(boardPieceDimension());
        D1.setMinimumSize(boardPieceDimension());
        D1.setPreferredSize(boardPieceDimension());
        D1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        boardPanel.add(D1, gridBagConstraints);

        E1.setBackground(boardColor(0,4));
        E1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        E1.setForeground(new java.awt.Color(51, 51, 51));
        E1.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(0,4) ? "/none32.png" : (isKing(0,4) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        E1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        E1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E1.setMaximumSize(boardPieceDimension());
        E1.setMinimumSize(boardPieceDimension());
        E1.setPreferredSize(boardPieceDimension());
        E1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        boardPanel.add(E1, gridBagConstraints);

        F1.setBackground(new java.awt.Color(26, 26, 26));
        F1.setEnabled(false);
        F1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F1.setMaximumSize(boardPieceDimension());
        F1.setMinimumSize(boardPieceDimension());
        F1.setPreferredSize(boardPieceDimension());
        F1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        boardPanel.add(F1, gridBagConstraints);

        G1.setBackground(boardColor(0,6));
        G1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        G1.setForeground(new java.awt.Color(51, 51, 51));
        G1.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(0,6) ? "/none32.png" : (isKing(0,6) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        G1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        G1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G1.setMaximumSize(boardPieceDimension());
        G1.setMinimumSize(boardPieceDimension());
        G1.setPreferredSize(boardPieceDimension());
        G1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        boardPanel.add(G1, gridBagConstraints);

        H1.setBackground(new java.awt.Color(26, 26, 26));
        H1.setEnabled(false);
        H1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H1.setMaximumSize(boardPieceDimension());
        H1.setMinimumSize(boardPieceDimension());
        H1.setPreferredSize(boardPieceDimension());
        H1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        boardPanel.add(H1, gridBagConstraints);

        A2.setBackground(new java.awt.Color(26, 26, 26));
        A2.setEnabled(false);
        A2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A2.setMaximumSize(boardPieceDimension());
        A2.setMinimumSize(boardPieceDimension());
        A2.setPreferredSize(boardPieceDimension());
        A2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        boardPanel.add(A2, gridBagConstraints);

        B2.setBackground(boardColor(1,1));
        B2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        B2.setForeground(new java.awt.Color(51, 51, 51));
        B2.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(1,1) ? "/none32.png" : (isKing(1,1) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        B2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        B2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B2.setMaximumSize(boardPieceDimension());
        B2.setMinimumSize(boardPieceDimension());
        B2.setPreferredSize(boardPieceDimension());
        B2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        boardPanel.add(B2, gridBagConstraints);

        C2.setBackground(new java.awt.Color(26, 26, 26));
        C2.setEnabled(false);
        C2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C2.setMaximumSize(boardPieceDimension());
        C2.setMinimumSize(boardPieceDimension());
        C2.setPreferredSize(boardPieceDimension());
        C2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        boardPanel.add(C2, gridBagConstraints);

        D2.setBackground(boardColor(1,3));
        D2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        D2.setForeground(new java.awt.Color(51, 51, 51));
        D2.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(1,3) ? "/none32.png" : (isKing(1,3) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        D2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        D2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D2.setMaximumSize(boardPieceDimension());
        D2.setMinimumSize(boardPieceDimension());
        D2.setPreferredSize(boardPieceDimension());
        D2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        boardPanel.add(D2, gridBagConstraints);

        E2.setBackground(new java.awt.Color(26, 26, 26));
        E2.setEnabled(false);
        E2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E2.setMaximumSize(boardPieceDimension());
        E2.setMinimumSize(boardPieceDimension());
        E2.setPreferredSize(boardPieceDimension());
        E2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        boardPanel.add(E2, gridBagConstraints);

        F2.setBackground(boardColor(1,5));
        F2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        F2.setForeground(new java.awt.Color(51, 51, 51));
        F2.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(1,5) ? "/none32.png" : (isKing(1,5) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        F2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        F2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F2.setMaximumSize(boardPieceDimension());
        F2.setMinimumSize(boardPieceDimension());
        F2.setPreferredSize(boardPieceDimension());
        F2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        boardPanel.add(F2, gridBagConstraints);

        G2.setBackground(new java.awt.Color(26, 26, 26));
        G2.setEnabled(false);
        G2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G2.setMaximumSize(boardPieceDimension());
        G2.setMinimumSize(boardPieceDimension());
        G2.setPreferredSize(boardPieceDimension());
        G2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        boardPanel.add(G2, gridBagConstraints);

        H2.setBackground(boardColor(1,7));
        H2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        H2.setForeground(new java.awt.Color(51, 51, 51));
        H2.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(1,7) ? "/none32.png" : (isKing(1,7) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        H2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        H2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H2.setMaximumSize(boardPieceDimension());
        H2.setMinimumSize(boardPieceDimension());
        H2.setPreferredSize(boardPieceDimension());
        H2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        boardPanel.add(H2, gridBagConstraints);

        A3.setBackground(boardColor(2,0));
        A3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        A3.setForeground(new java.awt.Color(51, 51, 51));
        A3.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(2,0) ? "/none32.png" : (isKing(2,0) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        A3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        A3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A3.setMaximumSize(boardPieceDimension());
        A3.setMinimumSize(boardPieceDimension());
        A3.setPreferredSize(boardPieceDimension());
        A3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        boardPanel.add(A3, gridBagConstraints);

        B3.setBackground(new java.awt.Color(26, 26, 26));
        B3.setEnabled(false);
        B3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B3.setMaximumSize(boardPieceDimension());
        B3.setMinimumSize(boardPieceDimension());
        B3.setPreferredSize(boardPieceDimension());
        B3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        boardPanel.add(B3, gridBagConstraints);

        C3.setBackground(boardColor(2,2));
        C3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        C3.setForeground(new java.awt.Color(51, 51, 51));
        C3.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(2,2) ? "/none32.png" : (isKing(2,2) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        C3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        C3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C3.setMaximumSize(boardPieceDimension());
        C3.setMinimumSize(boardPieceDimension());
        C3.setPreferredSize(boardPieceDimension());
        C3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        boardPanel.add(C3, gridBagConstraints);

        D3.setBackground(new java.awt.Color(26, 26, 26));
        D3.setEnabled(false);
        D3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D3.setMaximumSize(boardPieceDimension());
        D3.setMinimumSize(boardPieceDimension());
        D3.setPreferredSize(boardPieceDimension());
        D3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        boardPanel.add(D3, gridBagConstraints);

        E3.setBackground(boardColor(2,4));
        E3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        E3.setForeground(new java.awt.Color(51, 51, 51));
        E3.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(2,4) ? "/none32.png" : (isKing(2,4) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        E3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        E3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E3.setMaximumSize(boardPieceDimension());
        E3.setMinimumSize(boardPieceDimension());
        E3.setPreferredSize(boardPieceDimension());
        E3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        boardPanel.add(E3, gridBagConstraints);

        F3.setBackground(new java.awt.Color(26, 26, 26));
        F3.setEnabled(false);
        F3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F3.setMaximumSize(boardPieceDimension());
        F3.setMinimumSize(boardPieceDimension());
        F3.setPreferredSize(boardPieceDimension());
        F3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        boardPanel.add(F3, gridBagConstraints);

        G3.setBackground(boardColor(2,6));
        G3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        G3.setForeground(new java.awt.Color(51, 51, 51));
        G3.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(2,6) ? "/none32.png" : (isKing(2,6) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        G3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        G3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G3.setMaximumSize(boardPieceDimension());
        G3.setMinimumSize(boardPieceDimension());
        G3.setPreferredSize(boardPieceDimension());
        G3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        boardPanel.add(G3, gridBagConstraints);

        H3.setBackground(new java.awt.Color(26, 26, 26));
        H3.setEnabled(false);
        H3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H3.setMaximumSize(boardPieceDimension());
        H3.setMinimumSize(boardPieceDimension());
        H3.setPreferredSize(boardPieceDimension());
        H3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        boardPanel.add(H3, gridBagConstraints);

        A4.setBackground(new java.awt.Color(26, 26, 26));
        A4.setEnabled(false);
        A4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A4.setMaximumSize(boardPieceDimension());
        A4.setMinimumSize(boardPieceDimension());
        A4.setPreferredSize(boardPieceDimension());
        A4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        boardPanel.add(A4, gridBagConstraints);

        B4.setBackground(boardColor(3,1));
        B4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        B4.setForeground(new java.awt.Color(51, 51, 51));
        B4.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(3,1) ? "/none32.png" : (isKing(3,1) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        B4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        B4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B4.setMaximumSize(boardPieceDimension());
        B4.setMinimumSize(boardPieceDimension());
        B4.setPreferredSize(boardPieceDimension());
        B4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        boardPanel.add(B4, gridBagConstraints);

        C4.setBackground(new java.awt.Color(26, 26, 26));
        C4.setEnabled(false);
        C4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C4.setMaximumSize(boardPieceDimension());
        C4.setMinimumSize(boardPieceDimension());
        C4.setPreferredSize(boardPieceDimension());
        C4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        boardPanel.add(C4, gridBagConstraints);

        D4.setBackground(boardColor(3,3));
        D4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        D4.setForeground(new java.awt.Color(51, 51, 51));
        D4.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(3,3) ? "/none32.png" : (isKing(3,3) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        D4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        D4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D4.setMaximumSize(boardPieceDimension());
        D4.setMinimumSize(boardPieceDimension());
        D4.setPreferredSize(boardPieceDimension());
        D4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        boardPanel.add(D4, gridBagConstraints);

        E4.setBackground(new java.awt.Color(26, 26, 26));
        E4.setEnabled(false);
        E4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E4.setMaximumSize(boardPieceDimension());
        E4.setMinimumSize(boardPieceDimension());
        E4.setPreferredSize(boardPieceDimension());
        E4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        boardPanel.add(E4, gridBagConstraints);

        F4.setBackground(boardColor(3,5));
        F4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        F4.setForeground(new java.awt.Color(51, 51, 51));
        F4.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(3,5) ? "/none32.png" : (isKing(3,5) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        F4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        F4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F4.setMaximumSize(boardPieceDimension());
        F4.setMinimumSize(boardPieceDimension());
        F4.setPreferredSize(boardPieceDimension());
        F4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        boardPanel.add(F4, gridBagConstraints);

        G4.setBackground(new java.awt.Color(26, 26, 26));
        G4.setEnabled(false);
        G4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G4.setMaximumSize(boardPieceDimension());
        G4.setMinimumSize(boardPieceDimension());
        G4.setPreferredSize(boardPieceDimension());
        G4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        boardPanel.add(G4, gridBagConstraints);

        H4.setBackground(boardColor(3,7));
        H4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        H4.setForeground(new java.awt.Color(51, 51, 51));
        H4.setIcon(new javax.swing.ImageIcon(getClass().getResource(!isOccupied(3,7) ? "/none32.png" : (isKing(3,7) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
        H4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        H4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H4.setMaximumSize(boardPieceDimension());
        H4.setMinimumSize(boardPieceDimension());
        H4.setPreferredSize(boardPieceDimension());
        H4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 5;
        boardPanel.add(H4, gridBagConstraints);

        A5.setBackground(boardColor(2,0));
        A5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        A5.setForeground(new java.awt.Color(51, 51, 51));
        A5.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(4,0) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        A5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        A5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A5.setMaximumSize(boardPieceDimension());
        A5.setMinimumSize(boardPieceDimension());
        A5.setPreferredSize(boardPieceDimension());
        A5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        boardPanel.add(A5, gridBagConstraints);

        B5.setBackground(new java.awt.Color(26, 26, 26));
        B5.setEnabled(false);
        B5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B5.setMaximumSize(boardPieceDimension());
        B5.setMinimumSize(boardPieceDimension());
        B5.setPreferredSize(boardPieceDimension());
        B5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        boardPanel.add(B5, gridBagConstraints);

        C5.setBackground(boardColor(4,2));
        C5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        C5.setForeground(new java.awt.Color(51, 51, 51));
        C5.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(4,2) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        C5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        C5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C5.setMaximumSize(boardPieceDimension());
        C5.setMinimumSize(boardPieceDimension());
        C5.setPreferredSize(boardPieceDimension());
        C5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        boardPanel.add(C5, gridBagConstraints);

        D5.setBackground(new java.awt.Color(26, 26, 26));
        D5.setEnabled(false);
        D5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D5.setMaximumSize(boardPieceDimension());
        D5.setMinimumSize(boardPieceDimension());
        D5.setPreferredSize(boardPieceDimension());
        D5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        boardPanel.add(D5, gridBagConstraints);

        E5.setBackground(boardColor(4,4));
        E5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        E5.setForeground(new java.awt.Color(51, 51, 51));
        E5.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(4,4) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        E5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        E5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E5.setMaximumSize(boardPieceDimension());
        E5.setMinimumSize(boardPieceDimension());
        E5.setPreferredSize(boardPieceDimension());
        E5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        boardPanel.add(E5, gridBagConstraints);

        F5.setBackground(new java.awt.Color(26, 26, 26));
        F5.setEnabled(false);
        F5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F5.setMaximumSize(boardPieceDimension());
        F5.setMinimumSize(boardPieceDimension());
        F5.setPreferredSize(boardPieceDimension());
        F5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        boardPanel.add(F5, gridBagConstraints);

        G5.setBackground(boardColor(4,6));
        G5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        G5.setForeground(new java.awt.Color(51, 51, 51));
        G5.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(4,6) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        G5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        G5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G5.setMaximumSize(boardPieceDimension());
        G5.setMinimumSize(boardPieceDimension());
        G5.setPreferredSize(boardPieceDimension());
        G5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        boardPanel.add(G5, gridBagConstraints);

        H5.setBackground(new java.awt.Color(26, 26, 26));
        H5.setEnabled(false);
        H5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H5.setMaximumSize(boardPieceDimension());
        H5.setMinimumSize(boardPieceDimension());
        H5.setPreferredSize(boardPieceDimension());
        H5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        boardPanel.add(H5, gridBagConstraints);

        A6.setBackground(new java.awt.Color(26, 26, 26));
        A6.setEnabled(false);
        A6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A6.setMaximumSize(boardPieceDimension());
        A6.setMinimumSize(boardPieceDimension());
        A6.setPreferredSize(boardPieceDimension());
        A6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        boardPanel.add(A6, gridBagConstraints);

        B6.setBackground(boardColor(5,1));
        B6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        B6.setForeground(new java.awt.Color(51, 51, 51));
        B6.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(5,1) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        B6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        B6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B6.setMaximumSize(boardPieceDimension());
        B6.setMinimumSize(boardPieceDimension());
        B6.setPreferredSize(boardPieceDimension());
        B6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        boardPanel.add(B6, gridBagConstraints);

        C6.setBackground(new java.awt.Color(26, 26, 26));
        C6.setEnabled(false);
        C6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C6.setMaximumSize(boardPieceDimension());
        C6.setMinimumSize(boardPieceDimension());
        C6.setPreferredSize(boardPieceDimension());
        C6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        boardPanel.add(C6, gridBagConstraints);

        D6.setBackground(boardColor(5,3));
        D6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        D6.setForeground(new java.awt.Color(51, 51, 51));
        D6.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(5,3) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        D6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        D6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D6.setMaximumSize(boardPieceDimension());
        D6.setMinimumSize(boardPieceDimension());
        D6.setPreferredSize(boardPieceDimension());
        D6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        boardPanel.add(D6, gridBagConstraints);

        E6.setBackground(new java.awt.Color(26, 26, 26));
        E6.setEnabled(false);
        E6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E6.setMaximumSize(boardPieceDimension());
        E6.setMinimumSize(boardPieceDimension());
        E6.setPreferredSize(boardPieceDimension());
        E6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        boardPanel.add(E6, gridBagConstraints);

        F6.setBackground(boardColor(5,5));
        F6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        F6.setForeground(new java.awt.Color(51, 51, 51));
        F6.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(5,7) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        F6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        F6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F6.setMaximumSize(boardPieceDimension());
        F6.setMinimumSize(boardPieceDimension());
        F6.setPreferredSize(boardPieceDimension());
        F6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        boardPanel.add(F6, gridBagConstraints);

        G6.setBackground(new java.awt.Color(26, 26, 26));
        G6.setEnabled(false);
        G6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G6.setMaximumSize(boardPieceDimension());
        G6.setMinimumSize(boardPieceDimension());
        G6.setPreferredSize(boardPieceDimension());
        G6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        boardPanel.add(G6, gridBagConstraints);

        H6.setBackground(boardColor(5,7));
        H6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        H6.setForeground(new java.awt.Color(51, 51, 51));
        H6.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(5,7) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        H6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        H6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H6.setMaximumSize(boardPieceDimension());
        H6.setMinimumSize(boardPieceDimension());
        H6.setPreferredSize(boardPieceDimension());
        H6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 7;
        boardPanel.add(H6, gridBagConstraints);

        A7.setBackground(boardColor(6,0));
        A7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        A7.setForeground(new java.awt.Color(51, 51, 51));
        A7.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(6,0) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        A7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        A7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A7.setMaximumSize(boardPieceDimension());
        A7.setMinimumSize(boardPieceDimension());
        A7.setPreferredSize(boardPieceDimension());
        A7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        boardPanel.add(A7, gridBagConstraints);

        B7.setBackground(new java.awt.Color(26, 26, 26));
        B7.setEnabled(false);
        B7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B7.setMaximumSize(boardPieceDimension());
        B7.setMinimumSize(boardPieceDimension());
        B7.setPreferredSize(boardPieceDimension());
        B7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        boardPanel.add(B7, gridBagConstraints);

        C7.setBackground(boardColor(6,2));
        C7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        C7.setForeground(new java.awt.Color(51, 51, 51));
        C7.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(6,2) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        C7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        C7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C7.setMaximumSize(boardPieceDimension());
        C7.setMinimumSize(boardPieceDimension());
        C7.setPreferredSize(boardPieceDimension());
        C7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        boardPanel.add(C7, gridBagConstraints);

        D7.setBackground(new java.awt.Color(26, 26, 26));
        D7.setEnabled(false);
        D7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D7.setMaximumSize(boardPieceDimension());
        D7.setMinimumSize(boardPieceDimension());
        D7.setPreferredSize(boardPieceDimension());
        D7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        boardPanel.add(D7, gridBagConstraints);

        E7.setBackground(boardColor(6,4));
        E7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        E7.setForeground(new java.awt.Color(51, 51, 51));
        E7.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(6,4) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        E7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        E7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E7.setMaximumSize(boardPieceDimension());
        E7.setMinimumSize(boardPieceDimension());
        E7.setPreferredSize(boardPieceDimension());
        E7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        boardPanel.add(E7, gridBagConstraints);

        F7.setBackground(new java.awt.Color(26, 26, 26));
        F7.setEnabled(false);
        F7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F7.setMaximumSize(boardPieceDimension());
        F7.setMinimumSize(boardPieceDimension());
        F7.setPreferredSize(boardPieceDimension());
        F7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        boardPanel.add(F7, gridBagConstraints);

        G7.setBackground(boardColor(6,6));
        G7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        G7.setForeground(new java.awt.Color(51, 51, 51));
        G7.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(6,6) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        G7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        G7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G7.setMaximumSize(boardPieceDimension());
        G7.setMinimumSize(boardPieceDimension());
        G7.setPreferredSize(boardPieceDimension());
        G7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        boardPanel.add(G7, gridBagConstraints);

        H7.setBackground(new java.awt.Color(26, 26, 26));
        H7.setEnabled(false);
        H7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H7.setMaximumSize(boardPieceDimension());
        H7.setMinimumSize(boardPieceDimension());
        H7.setPreferredSize(boardPieceDimension());
        H7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        boardPanel.add(H7, gridBagConstraints);

        A8.setBackground(new java.awt.Color(26, 26, 26));
        A8.setEnabled(false);
        A8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        A8.setMaximumSize(boardPieceDimension());
        A8.setMinimumSize(boardPieceDimension());
        A8.setPreferredSize(boardPieceDimension());
        A8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        boardPanel.add(A8, gridBagConstraints);

        B8.setBackground(boardColor(7,1));
        B8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        B8.setForeground(new java.awt.Color(51, 51, 51));
        B8.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(7,1) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        B8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        B8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        B8.setMaximumSize(boardPieceDimension());
        B8.setMinimumSize(boardPieceDimension());
        B8.setPreferredSize(boardPieceDimension());
        B8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        boardPanel.add(B8, gridBagConstraints);

        C8.setBackground(new java.awt.Color(26, 26, 26));
        C8.setEnabled(false);
        C8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        C8.setMaximumSize(boardPieceDimension());
        C8.setMinimumSize(boardPieceDimension());
        C8.setPreferredSize(boardPieceDimension());
        C8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                C8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        boardPanel.add(C8, gridBagConstraints);

        D8.setBackground(boardColor(7,3));
        D8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        D8.setForeground(new java.awt.Color(51, 51, 51));
        D8.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(7,3) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        D8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        D8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        D8.setMaximumSize(boardPieceDimension());
        D8.setMinimumSize(boardPieceDimension());
        D8.setPreferredSize(boardPieceDimension());
        D8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                D8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        boardPanel.add(D8, gridBagConstraints);

        E8.setBackground(new java.awt.Color(26, 26, 26));
        E8.setEnabled(false);
        E8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        E8.setMaximumSize(boardPieceDimension());
        E8.setMinimumSize(boardPieceDimension());
        E8.setPreferredSize(boardPieceDimension());
        E8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                E8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        boardPanel.add(E8, gridBagConstraints);

        F8.setBackground(boardColor(7,5));
        F8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        F8.setForeground(new java.awt.Color(51, 51, 51));
        F8.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(7,5) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        F8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        F8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        F8.setMaximumSize(boardPieceDimension());
        F8.setMinimumSize(boardPieceDimension());
        F8.setPreferredSize(boardPieceDimension());
        F8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                F8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        boardPanel.add(F8, gridBagConstraints);

        G8.setBackground(new java.awt.Color(26, 26, 26));
        G8.setEnabled(false);
        G8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        G8.setMaximumSize(boardPieceDimension());
        G8.setMinimumSize(boardPieceDimension());
        G8.setPreferredSize(boardPieceDimension());
        G8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                G8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        boardPanel.add(G8, gridBagConstraints);

        H8.setBackground(boardColor(7,7));
        H8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        H8.setForeground(new java.awt.Color(51, 51, 51));
        H8.setIcon(new javax.swing.ImageIcon(getClass().getResource(isKing(7,7) ? "/Piece background KING.png" : "/Piece background PAWN.png")));
        H8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        H8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        H8.setMaximumSize(boardPieceDimension());
        H8.setMinimumSize(boardPieceDimension());
        H8.setPreferredSize(boardPieceDimension());
        H8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                H8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 9;
        boardPanel.add(H8, gridBagConstraints);

        colLabelA.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelA.setText("A");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelA, gridBagConstraints);

        colLabelB.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelB.setText("B");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelB, gridBagConstraints);

        colLabelC.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelC.setText("C");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelC, gridBagConstraints);

        colLabelD.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelD.setText("D");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelD, gridBagConstraints);

        colLabelE.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelE.setText("E");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelE, gridBagConstraints);

        colLabelF.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelF.setText("F");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelF, gridBagConstraints);

        colLabelG.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelG.setText("G");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelG, gridBagConstraints);

        colLabelH.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        colLabelH.setText("H");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        boardPanel.add(colLabelH, gridBagConstraints);

        rowLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel1.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        boardPanel.add(rowLabel1, gridBagConstraints);

        rowLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel2.setText("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        boardPanel.add(rowLabel2, gridBagConstraints);

        rowLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel3.setText("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        boardPanel.add(rowLabel3, gridBagConstraints);

        rowLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel4.setText("4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        boardPanel.add(rowLabel4, gridBagConstraints);

        rowLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel5.setText("6");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        boardPanel.add(rowLabel5, gridBagConstraints);

        rowLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel6.setText("5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        boardPanel.add(rowLabel6, gridBagConstraints);

        rowLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel7.setText("7");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        boardPanel.add(rowLabel7, gridBagConstraints);

        rowLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        rowLabel8.setText("8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        boardPanel.add(rowLabel8, gridBagConstraints);

        player1NameLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        player1NameLabel.setText(name2);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        boardPanel.add(player1NameLabel, gridBagConstraints);
        player1NameLabel.getAccessibleContext().setAccessibleName("player 2 name");

        player2NameLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        player2NameLabel.setText(name1);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        boardPanel.add(player2NameLabel, gridBagConstraints);
        player2NameLabel.getAccessibleContext().setAccessibleName("player 1 name");

        jPanel3.setOpaque(false);

        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancel.png"))); // NOI18N
        cancelButton.setText("<html> <center>CANCEL<br> MOVE</center> </html>");
        cancelButton.setToolTipText("deselects the piece you have selected");
        cancelButton.setAutoscrolls(true);
        cancelButton.setEnabled(isSetUp);
        cancelButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        forfeitButton.setBackground(new java.awt.Color(255, 0, 0));
        forfeitButton.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        forfeitButton.setForeground(new java.awt.Color(102, 0, 0));
        forfeitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/undo.png"))); // NOI18N
        forfeitButton.setText("FORFEIT");
        forfeitButton.setToolTipText("You lose and your opponent wins instantly");
        forfeitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        forfeitButton.setEnabled(isSetUp);
        forfeitButton.setFocusable(false);
        forfeitButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        forfeitButton.setMaximumSize(new java.awt.Dimension(47, 16));
        forfeitButton.setMinimumSize(new java.awt.Dimension(47, 16));
        forfeitButton.setPreferredSize(new java.awt.Dimension(47, 16));
        forfeitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forfeitButtonActionPerformed(evt);
            }
        });

        turnOutputLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        turnOutputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turnOutputLabel.setLabelFor(turnOutputLabel);
        turnOutputLabel.setText(turnOutput);
        turnOutputLabel.setToolTipText("This is where the secondary output is");
        turnOutputLabel.setAutoscrolls(true);
        turnOutputLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        turnOutputLabel.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(turnOutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(forfeitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forfeitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(turnOutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
        );

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout gameTabLayout = new javax.swing.GroupLayout(gameTab);
        gameTab.setLayout(gameTabLayout);
        gameTabLayout.setHorizontalGroup(
            gameTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameTabLayout.createSequentialGroup()
                .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        gameTabLayout.setVerticalGroup(
            gameTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
            .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Game", new javax.swing.ImageIcon(getClass().getResource("/BHC icon 16.png")), gameTab, "Make game operations"); // NOI18N

        scoreboardTab.setToolTipText("View the scoreboard");
        scoreboardTab.setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Player Name", "Wins", "Losses"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout scoreboardTabLayout = new javax.swing.GroupLayout(scoreboardTab);
        scoreboardTab.setLayout(scoreboardTabLayout);
        scoreboardTabLayout.setHorizontalGroup(
            scoreboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );
        scoreboardTabLayout.setVerticalGroup(
            scoreboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Scoreboard", new javax.swing.ImageIcon(getClass().getResource("/txt.png")), scoreboardTab); // NOI18N

        consolePane.setEnabled(false);
        consolePane.setOpaque(false);

        jScrollPane1.setEnabled(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.setVerifyInputWhenFocusTarget(false);

        consoleOutput.setColumns(1);
        consoleOutput.setEditable(false);
        consoleOutput.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        consoleOutput.setRows(5);
        consoleOutput.setText(console);
        consoleOutput.setOpaque(false);
        jScrollPane1.setViewportView(consoleOutput);

        javax.swing.GroupLayout consolePaneLayout = new javax.swing.GroupLayout(consolePane);
        consolePane.setLayout(consolePaneLayout);
        consolePaneLayout.setHorizontalGroup(
            consolePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        consolePaneLayout.setVerticalGroup(
            consolePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        gameMenu.setMnemonic(KeyEvent.VK_G);
        gameMenu.setText("Game");
        gameMenu.setDelay(0);
        gameMenu.setPreferredSize(new java.awt.Dimension(39, 19));

        forfeitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/undo.png"))); // NOI18N
        forfeitMenuItem.setText("Forfeit");
        forfeitMenuItem.setPreferredSize(new java.awt.Dimension(100, 22));
        gameMenu.add(forfeitMenuItem);
        gameMenu.add(jSeparator1);

        quitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        quitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exit.png"))); // NOI18N
        quitMenuItem.setText("Exit");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(quitMenuItem);

        jMenuBar1.add(gameMenu);

        helpMenu.setMnemonic(KeyEvent.VK_F1);
        helpMenu.setText("Help");

        supuhWikiMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/network.png"))); // NOI18N
        supuhWikiMenuItem.setText("<html><a href=\"http://supuh.wikia.com/\"><u>The " + TITLE + " page on SupuhWiki</u></a></html>");
        supuhWikiMenuItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        supuhWikiMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supuhWikiMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(supuhWikiMenuItem);

        readmeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        readmeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N
        readmeMenuItem.setText("Readme");
        readmeMenuItem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        readmeMenuItem.setPreferredSize(new java.awt.Dimension(113, 22));
        readmeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readmeMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(readmeMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addComponent(consolePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consolePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        getAccessibleContext().setAccessibleName("null");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
      cancelMove();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void forfeitButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_forfeitButtonActionPerformed
    {//GEN-HEADEREND:event_forfeitButtonActionPerformed
      /**
      Quits the game when pressed
       */
      forfeit();
      applyOptionsButtonActionPerformed(evt);
      consoleOutput.setText(console);
    }//GEN-LAST:event_forfeitButtonActionPerformed
  public static Color boardColor(int x, int y)
  {
    if (board[x][y] == mark1)
    {
      return color1;
    }
    if (board[x][y] == mark2)
    {
      return color2;
    }
    return colorX;
  }// <editor-fold defaultstate="collapsed" desc="Board Buttons">
    private void A1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A1ActionPerformed
    {//GEN-HEADEREND:event_A1ActionPerformed
      select(0, 0);
}//GEN-LAST:event_A1ActionPerformed

    private void B1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B1ActionPerformed
    {//GEN-HEADEREND:event_B1ActionPerformed
      select(0, 1);
    }//GEN-LAST:event_B1ActionPerformed

    private void C1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C1ActionPerformed
    {//GEN-HEADEREND:event_C1ActionPerformed
      select(0, 2);
    }//GEN-LAST:event_C1ActionPerformed

    private void D1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D1ActionPerformed
    {//GEN-HEADEREND:event_D1ActionPerformed
      select(0, 3);
    }//GEN-LAST:event_D1ActionPerformed

    private void E1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E1ActionPerformed
    {//GEN-HEADEREND:event_E1ActionPerformed
      select(0, 4);
    }//GEN-LAST:event_E1ActionPerformed

    private void F1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F1ActionPerformed
    {//GEN-HEADEREND:event_F1ActionPerformed
      select(0, 5);
    }//GEN-LAST:event_F1ActionPerformed

    private void H1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H1ActionPerformed
    {//GEN-HEADEREND:event_H1ActionPerformed
      select(0, 7);
    }//GEN-LAST:event_H1ActionPerformed

    private void G1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G1ActionPerformed
    {//GEN-HEADEREND:event_G1ActionPerformed
      select(0, 6);
    }//GEN-LAST:event_G1ActionPerformed

    private void A2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A2ActionPerformed
    {//GEN-HEADEREND:event_A2ActionPerformed
      select(1, 0);
    }//GEN-LAST:event_A2ActionPerformed

    private void B2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B2ActionPerformed
    {//GEN-HEADEREND:event_B2ActionPerformed
      select(1, 1);
    }//GEN-LAST:event_B2ActionPerformed

    private void C2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C2ActionPerformed
    {//GEN-HEADEREND:event_C2ActionPerformed
      select(1, 2);
    }//GEN-LAST:event_C2ActionPerformed

    private void D2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D2ActionPerformed
    {//GEN-HEADEREND:event_D2ActionPerformed
      select(1, 3);
    }//GEN-LAST:event_D2ActionPerformed

    private void F2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F2ActionPerformed
    {//GEN-HEADEREND:event_F2ActionPerformed
      select(1, 5);
    }//GEN-LAST:event_F2ActionPerformed

    private void G2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G2ActionPerformed
    {//GEN-HEADEREND:event_G2ActionPerformed
      select(1, 6);
    }//GEN-LAST:event_G2ActionPerformed

    private void H2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H2ActionPerformed
    {//GEN-HEADEREND:event_H2ActionPerformed
      select(1, 7);
    }//GEN-LAST:event_H2ActionPerformed

    private void E2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E2ActionPerformed
    {//GEN-HEADEREND:event_E2ActionPerformed
      select(1, 4);
    }//GEN-LAST:event_E2ActionPerformed

    private void A3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A3ActionPerformed
    {//GEN-HEADEREND:event_A3ActionPerformed
      select(2, 0);
    }//GEN-LAST:event_A3ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B3ActionPerformed
    {//GEN-HEADEREND:event_B3ActionPerformed
      select(2, 1);
    }//GEN-LAST:event_B3ActionPerformed

    private void C3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C3ActionPerformed
    {//GEN-HEADEREND:event_C3ActionPerformed
      select(2, 2);
    }//GEN-LAST:event_C3ActionPerformed

    private void D3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D3ActionPerformed
    {//GEN-HEADEREND:event_D3ActionPerformed
      select(2, 3);
    }//GEN-LAST:event_D3ActionPerformed

    private void E3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E3ActionPerformed
    {//GEN-HEADEREND:event_E3ActionPerformed
      select(2, 4);
    }//GEN-LAST:event_E3ActionPerformed

    private void F3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F3ActionPerformed
    {//GEN-HEADEREND:event_F3ActionPerformed
      select(2, 5);
    }//GEN-LAST:event_F3ActionPerformed

    private void G3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G3ActionPerformed
    {//GEN-HEADEREND:event_G3ActionPerformed
      select(2, 6);
    }//GEN-LAST:event_G3ActionPerformed

    private void H3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H3ActionPerformed
    {//GEN-HEADEREND:event_H3ActionPerformed
      select(2, 7);
    }//GEN-LAST:event_H3ActionPerformed

    private void A4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A4ActionPerformed
    {//GEN-HEADEREND:event_A4ActionPerformed
      select(3, 0);
    }//GEN-LAST:event_A4ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B4ActionPerformed
    {//GEN-HEADEREND:event_B4ActionPerformed
      select(3, 1);
    }//GEN-LAST:event_B4ActionPerformed

    private void C4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C4ActionPerformed
    {//GEN-HEADEREND:event_C4ActionPerformed
      select(3, 2);
    }//GEN-LAST:event_C4ActionPerformed

    private void D4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D4ActionPerformed
    {//GEN-HEADEREND:event_D4ActionPerformed
      select(3, 3);
    }//GEN-LAST:event_D4ActionPerformed

    private void F4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F4ActionPerformed
    {//GEN-HEADEREND:event_F4ActionPerformed
      select(3, 5);
    }//GEN-LAST:event_F4ActionPerformed

    private void G4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G4ActionPerformed
    {//GEN-HEADEREND:event_G4ActionPerformed
      select(3, 6);
    }//GEN-LAST:event_G4ActionPerformed

    private void H4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H4ActionPerformed
    {//GEN-HEADEREND:event_H4ActionPerformed
      select(3, 7);
    }//GEN-LAST:event_H4ActionPerformed

    private void E4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E4ActionPerformed
    {//GEN-HEADEREND:event_E4ActionPerformed
      select(3, 4);
    }//GEN-LAST:event_E4ActionPerformed

    private void A5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A5ActionPerformed
    {//GEN-HEADEREND:event_A5ActionPerformed
      select(4, 0);
    }//GEN-LAST:event_A5ActionPerformed

    private void B5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B5ActionPerformed
    {//GEN-HEADEREND:event_B5ActionPerformed
      select(4, 1);
    }//GEN-LAST:event_B5ActionPerformed

    private void C5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C5ActionPerformed
    {//GEN-HEADEREND:event_C5ActionPerformed
      select(4, 2);
    }//GEN-LAST:event_C5ActionPerformed

    private void D5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D5ActionPerformed
    {//GEN-HEADEREND:event_D5ActionPerformed
      select(4, 3);
    }//GEN-LAST:event_D5ActionPerformed

    private void E5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E5ActionPerformed
    {//GEN-HEADEREND:event_E5ActionPerformed
      select(4, 4);
    }//GEN-LAST:event_E5ActionPerformed

    private void F5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F5ActionPerformed
    {//GEN-HEADEREND:event_F5ActionPerformed
      select(4, 5);
    }//GEN-LAST:event_F5ActionPerformed

    private void G5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G5ActionPerformed
    {//GEN-HEADEREND:event_G5ActionPerformed
      select(4, 6);
    }//GEN-LAST:event_G5ActionPerformed

    private void H5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H5ActionPerformed
    {//GEN-HEADEREND:event_H5ActionPerformed
      select(4, 7);
    }//GEN-LAST:event_H5ActionPerformed

    private void supuhWikiMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_supuhWikiMenuItemActionPerformed
    {//GEN-HEADEREND:event_supuhWikiMenuItemActionPerformed
      final JEditorPane finalpane = new JEditorPane();
      try
      {
        finalpane.setPage(new URL(siteName));
      }
      catch (Exception e)
      {
        System.err.println("Exception caught in supuhWikiMenuItemActionPerformed(java.awt.event.ActionEvent)\n\r"
                           + e.getMessage() + "\n\r" + e.getCause() + "\n\r" + e.getLocalizedMessage());
      }
    }//GEN-LAST:event_supuhWikiMenuItemActionPerformed

    private void name1ColorChangerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_name1ColorChangerButtonActionPerformed
    {//GEN-HEADEREND:event_name1ColorChangerButtonActionPerformed
      ColorChooser.main(arguments);
      name1ColorChangerButton.repaint();
}//GEN-LAST:event_name1ColorChangerButtonActionPerformed

    private void applyOptionsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_applyOptionsButtonActionPerformed
    {//GEN-HEADEREND:event_applyOptionsButtonActionPerformed
//      timer2.stop();
      System.out.println(boardDimension() + "\n" + boardPieceDimension());
      numOfPlayers = numOfPlayersSlider.getValue();
      name1 = name1Input.getText();
      name2 = name2Input.getText();
      color1 = tempColor1;
      color1DisplayPanel.setBackground(color1);
      color1DisplayPanel.setToolTipText(name1 + "'s color");
      //      ColorChooser.setColorButton1Background(color1);
      color1DisplayPanel.repaint();
      color2 = tempColor2;
      color2DisplayPanel.setBackground(color2);
      color2DisplayPanel.setToolTipText(name2 + "'s color");
      //      ColorChooser.setColorButton2Background(color2);
      color2DisplayPanel.repaint();
      console += "\n\rClick a square to select a piece";
      consoleOutput.setText(console);
      isSetUp = true;
      cancelButton.setEnabled(isSetUp);
      forfeitButton.setEnabled(isSetUp);
      setTurn();
      turnOutput(name + "'s turn");
      setTitle(TITLE + " - " + name + "'s turn");

      player1NameLabel.setText(name1);
      player2NameLabel.setText(name2);
      player1NameLabel.repaint();
      player2NameLabel.repaint();

      char[][] newBoard  = {{(char)(mark1 - 32), EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY},
                            {EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY, mark1},
                            {mark1, EMPTY, mark1, EMPTY, mark1, EMPTY, mark1, EMPTY},
                            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                            {EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2},
                            {mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY},
                            {EMPTY, mark2, EMPTY, mark2, EMPTY, mark2, EMPTY, mark2}};
      board = newBoard;

      Color[][] newBoardColor = {{color1, colorX, color1, colorX, color1, colorX, color1, colorX},
                                 {colorX, color1, colorX, color1, colorX, color1, colorX, color1},
                                 {color1, colorX, color1, colorX, color1, colorX, color1, colorX},
                                 {colorX, colorX, colorX, colorX, colorX, colorX, colorX, colorX},
                                 {colorX, colorX, colorX, colorX, colorX, colorX, colorX, colorX},
                                 {colorX, color2, colorX, color2, colorX, color2, colorX, color2},
                                 {color2, colorX, color2, colorX, color2, colorX, color2, colorX},
                                 {colorX, color2, colorX, color2, colorX, color2, colorX, color2}};
      boardColor = newBoardColor;

      refreshBoard();
      turnOutputLabel.repaint();
      jTabbedPane1.setSelectedIndex(jTabbedPane1.indexOfComponent(gameTab));
}//GEN-LAST:event_applyOptionsButtonActionPerformed

    private void readmeMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_readmeMenuItemActionPerformed
    {//GEN-HEADEREND:event_readmeMenuItemActionPerformed
      readmeFrame.setVisible(true);
    }//GEN-LAST:event_readmeMenuItemActionPerformed

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitMenuItemActionPerformed
    {//GEN-HEADEREND:event_quitMenuItemActionPerformed
      quit();
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void A6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A6ActionPerformed
    {//GEN-HEADEREND:event_A6ActionPerformed
      select(5, 0);
    }//GEN-LAST:event_A6ActionPerformed

    private void B6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B6ActionPerformed
    {//GEN-HEADEREND:event_B6ActionPerformed
      select(5, 1);
    }//GEN-LAST:event_B6ActionPerformed

    private void C6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C6ActionPerformed
    {//GEN-HEADEREND:event_C6ActionPerformed
      select(5, 2);
    }//GEN-LAST:event_C6ActionPerformed

    private void D6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D6ActionPerformed
    {//GEN-HEADEREND:event_D6ActionPerformed
      select(5, 3);
    }//GEN-LAST:event_D6ActionPerformed

    private void E6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E6ActionPerformed
    {//GEN-HEADEREND:event_E6ActionPerformed
      select(5, 4);
    }//GEN-LAST:event_E6ActionPerformed

    private void F6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F6ActionPerformed
    {//GEN-HEADEREND:event_F6ActionPerformed
      select(5, 5);
    }//GEN-LAST:event_F6ActionPerformed

    private void G6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G6ActionPerformed
    {//GEN-HEADEREND:event_G6ActionPerformed
      select(5, 6);
    }//GEN-LAST:event_G6ActionPerformed

    private void H6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H6ActionPerformed
    {//GEN-HEADEREND:event_H6ActionPerformed
      select(5, 7);
    }//GEN-LAST:event_H6ActionPerformed

    private void A7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A7ActionPerformed
    {//GEN-HEADEREND:event_A7ActionPerformed
      select(6, 0);
    }//GEN-LAST:event_A7ActionPerformed

    private void B7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B7ActionPerformed
    {//GEN-HEADEREND:event_B7ActionPerformed
      select(6, 1);
    }//GEN-LAST:event_B7ActionPerformed

    private void C7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C7ActionPerformed
    {//GEN-HEADEREND:event_C7ActionPerformed
      select(6, 2);
    }//GEN-LAST:event_C7ActionPerformed

    private void D7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D7ActionPerformed
    {//GEN-HEADEREND:event_D7ActionPerformed
      select(6, 3);
    }//GEN-LAST:event_D7ActionPerformed

    private void E7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E7ActionPerformed
    {//GEN-HEADEREND:event_E7ActionPerformed
      select(6, 4);
    }//GEN-LAST:event_E7ActionPerformed

    private void F7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F7ActionPerformed
    {//GEN-HEADEREND:event_F7ActionPerformed
      select(6, 5);
    }//GEN-LAST:event_F7ActionPerformed

    private void G7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G7ActionPerformed
    {//GEN-HEADEREND:event_G7ActionPerformed
      select(6, 6);
    }//GEN-LAST:event_G7ActionPerformed

    private void H7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H7ActionPerformed
    {//GEN-HEADEREND:event_H7ActionPerformed
      select(6, 7);
    }//GEN-LAST:event_H7ActionPerformed

    private void A8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_A8ActionPerformed
    {//GEN-HEADEREND:event_A8ActionPerformed
      select(7, 0);
    }//GEN-LAST:event_A8ActionPerformed

    private void B8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_B8ActionPerformed
    {//GEN-HEADEREND:event_B8ActionPerformed
      select(7, 1);
    }//GEN-LAST:event_B8ActionPerformed

    private void C8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_C8ActionPerformed
    {//GEN-HEADEREND:event_C8ActionPerformed
      select(7, 2);
    }//GEN-LAST:event_C8ActionPerformed

    private void D8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_D8ActionPerformed
    {//GEN-HEADEREND:event_D8ActionPerformed
      select(7, 3);
    }//GEN-LAST:event_D8ActionPerformed

    private void E8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_E8ActionPerformed
    {//GEN-HEADEREND:event_E8ActionPerformed
      select(7, 4);
    }//GEN-LAST:event_E8ActionPerformed

    private void F8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_F8ActionPerformed
    {//GEN-HEADEREND:event_F8ActionPerformed
      select(7, 5);
    }//GEN-LAST:event_F8ActionPerformed

    private void G8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_G8ActionPerformed
    {//GEN-HEADEREND:event_G8ActionPerformed
      select(7, 6);
    }//GEN-LAST:event_G8ActionPerformed

    private void H8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_H8ActionPerformed
    {//GEN-HEADEREND:event_H8ActionPerformed
      select(7, 7);
    }//GEN-LAST:event_H8ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
      quit();
    }//GEN-LAST:event_formWindowClosing

  @SuppressWarnings("static-access")
    private void copySupuhWikiJREInstallerPageURLButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copySupuhWikiJREInstallerPageURLButtonActionPerformed
    {//GEN-HEADEREND:event_copySupuhWikiJREInstallerPageURLButtonActionPerformed
      Copier.copy("http://supuh.wikia.com/wiki/JRE_Installers");
      /*final JOptionPane message = new*/JOptionPane/*();*/
      
//      javax.swing.Timer timer = new javax.swing.Timer(5000, new java.awt.event.ActionListener() {
//        public void actionPerformed(java.awt.event.ActionEvent evt) {
//          message.showMessageDialog(null, "Blah");
////          message.;
//          message.dispose();
//        }
//      });
//      timer.start();
//      timer.setRepeats(false);
      /*message*/.showMessageDialog(readmeFrame, "Copied to clopboard", TITLE, 2, new javax.swing.ImageIcon(getClass().getResource("/apply32.png")));
//      timer.stop();
    }//GEN-LAST:event_copySupuhWikiJREInstallerPageURLButtonActionPerformed

  private void copyOracleJREInstallerPageURLButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copyOracleJREInstallerPageURLButtonActionPerformed
  {//GEN-HEADEREND:event_copyOracleJREInstallerPageURLButtonActionPerformed
    Copier.copy("http://tinyurl.com/DLJavaJRE/");
    JOptionPane.showMessageDialog(readmeFrame, "Copied to clopboard", TITLE, 2, new javax.swing.ImageIcon(getClass().getResource("/apply32.png")));
  }//GEN-LAST:event_copyOracleJREInstallerPageURLButtonActionPerformed

  private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_aboutMenuItemActionPerformed
  {//GEN-HEADEREND:event_aboutMenuItemActionPerformed
    slideOpen(aboutFrame, 400, 460);
  }//GEN-LAST:event_aboutMenuItemActionPerformed
  // </editor-fold>

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) throws FileNotFoundException, MalformedURLException
  {
//    JOptionPane.showMessageDialog(null, "1", "main", 1);
    final Loader loader = new Loader();
    loader.setVisible(true);
//    JOptionPane.showMessageDialog(null, "2", "main", 1);
//    final Timer timer = new Timer(1, new java.awt.event.ActionListener()
//    {
//      private boolean repeat = true;
//      public void actionPerformed(java.awt.event.ActionEvent evt)
//      {
//        if (progress != Loader.jProgressBar1.getMaximum())
//        {
//          if (progress != Loader.jProgressBar1.getMinimum())
//          {
//            if (repeat)
//              progress++;
//            else
//              progress--;
//          }
//          else
//          {
//            repeat = !repeat;
//            progress++;
//          }
//
//        }
//        else
//        {
//          repeat = !repeat;
//          progress--;
//        }
//        Loader.jProgressBar1.setValue(progress);
//        Loader.jProgressBar1.repaint();
//      }
//    });
    Loader.jProgressBar1.setIndeterminate(true);
//    JOptionPane.showMessageDialog(null, "3", "main", 1);
//    timer.start();
//    JOptionPane.showMessageDialog(null, "Welcome to " + TITLE + ".\nPress OK to load program. This may take a bit.", TITLE + " " + VERSION, -1);
//      System.setOut(new PrintStream(console));
//    arguments = args;
//    JOptionPane.showMessageDialog(null, "4", "main", 1);
    setup();
//    JOptionPane.showMessageDialog(null, "5", "main", 1);
//    Action act = new Action()
//    {
//      public Object getValue(String key)
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//
//      public void putValue(String key, Object value)
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//
//      public void setEnabled(boolean b)
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//
//      public boolean isEnabled()
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//
//      public void addPropertyChangeListener(PropertyChangeListener listener)
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//
//      public void removePropertyChangeListener(PropertyChangeListener listener)
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//
//      public void actionPerformed(ActionEvent e)
//      {
//        throw new UnsupportedOperationException("Not supported yet.");
//      }
//    };
//    JOptionPane.showMessageDialog(null, "6", "main", 1);
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
//        JOptionPane.showMessageDialog(null, "7", "main", 1);
        main = new Main();
//        JOptionPane.showMessageDialog(null, "8", "main", 1);
        loader.setVisible(false);
//        JOptionPane.showMessageDialog(null, "9", "main", 1);
//        timer.stop();
//        JOptionPane.showMessageDialog(null, "10", "main", 1);
        final int width = main.getWidth(), height = main.getHeight();
//        JOptionPane.showMessageDialog(null, "11", "main", 1);
        dim = new Dimension(1,1);
//        JOptionPane.showMessageDialog(null, "12", "main", 1);
        main.setMinimumSize(dim);
//        JOptionPane.showMessageDialog(null, "13", "main", 1);
        main.setSize(dim);
//        JOptionPane.showMessageDialog(null, "14", "main", 1);
//        refreshBoard();
//        JOptionPane.showMessageDialog(null, "15", "main", 1);
        main.setVisible(true);
//        JOptionPane.showMessageDialog(null, "16", "main", 1);
        System.err.println("Expanding window...");
//        JOptionPane.showMessageDialog(null, "17", "main", 1);
        slideOpen(main, WIN_WIDTH, WIN_HEIGHT);
//        timer2.start();
//        JOptionPane.showMessageDialog(null, "18", "main", 1);
      }
    });
  }

  public static void quit()
  {
//    timer2.stop();
    if (JOptionPane.showConfirmDialog(jTabbedPane1, "Are you sure you want to quit?\nYour progress will NOT be saved", "Quit? - " + TITLE, 0, 0) == 0)
    {
      main.setResizable(true);
      main.setMinimumSize(new Dimension(0,0));
      main.setEnabled(false);
      dim = new Dimension(main.getWidth(), main.getHeight());
      Timer timer = new Timer(50, new java.awt.event.ActionListener()
      {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
          if ((int)dim.getWidth() >= 1)
          {
            if (dim.getWidth() > 112)
              dim = new Dimension((int)(dim.getWidth() / 1.25), (int)dim.getHeight());
            else
              dim = new Dimension((int)dim.getWidth(), (int)(dim.getHeight() / 1.25));
            main.setTitle(dim.getWidth() + "," + dim.getHeight());
            main.setSize(dim);
            main.repaint();


            if (dim.getHeight() <= 2)
              System.exit(0);
          }
        }
      });
      timer.start();
    }
  }
// <editor-fold defaultstate="collapsed" desc="Generated Variables">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton A1;
    public static javax.swing.JButton A2;
    public static javax.swing.JButton A3;
    public static javax.swing.JButton A4;
    public static javax.swing.JButton A5;
    public static javax.swing.JButton A6;
    public static javax.swing.JButton A7;
    public static javax.swing.JButton A8;
    public static javax.swing.JButton B1;
    public static javax.swing.JButton B2;
    public static javax.swing.JButton B3;
    public static javax.swing.JButton B4;
    public static javax.swing.JButton B5;
    public static javax.swing.JButton B6;
    public static javax.swing.JButton B7;
    public static javax.swing.JButton B8;
    public static javax.swing.JButton C1;
    public static javax.swing.JButton C2;
    public static javax.swing.JButton C3;
    public static javax.swing.JButton C4;
    public static javax.swing.JButton C5;
    public static javax.swing.JButton C6;
    public static javax.swing.JButton C7;
    public static javax.swing.JButton C8;
    public static javax.swing.JButton D1;
    public static javax.swing.JButton D2;
    public static javax.swing.JButton D3;
    public static javax.swing.JButton D4;
    public static javax.swing.JButton D5;
    public static javax.swing.JButton D6;
    public static javax.swing.JButton D7;
    public static javax.swing.JButton D8;
    public static javax.swing.JButton E1;
    public static javax.swing.JButton E2;
    public static javax.swing.JButton E3;
    public static javax.swing.JButton E4;
    public static javax.swing.JButton E5;
    public static javax.swing.JButton E6;
    public static javax.swing.JButton E7;
    public static javax.swing.JButton E8;
    public static javax.swing.JButton F1;
    public static javax.swing.JButton F2;
    public static javax.swing.JButton F3;
    public static javax.swing.JButton F4;
    public static javax.swing.JButton F5;
    public static javax.swing.JButton F6;
    public static javax.swing.JButton F7;
    public static javax.swing.JButton F8;
    public static javax.swing.JButton G1;
    public static javax.swing.JButton G2;
    public static javax.swing.JButton G3;
    public static javax.swing.JButton G4;
    public static javax.swing.JButton G5;
    public static javax.swing.JButton G6;
    public static javax.swing.JButton G7;
    public static javax.swing.JButton G8;
    public static javax.swing.JButton H1;
    public static javax.swing.JButton H2;
    public static javax.swing.JButton H3;
    public static javax.swing.JButton H4;
    public static javax.swing.JButton H5;
    public static javax.swing.JButton H6;
    public static javax.swing.JButton H7;
    public static javax.swing.JButton H8;
    private javax.swing.JPanel JREInformattionTab;
    private javax.swing.JPanel JREInstallationOracleStep1Tab;
    private javax.swing.JPanel JREInstallationOracleTab;
    private javax.swing.JLabel JREInstallationSupuhWikiStep1Label1;
    private javax.swing.JLabel JREInstallationSupuhWikiStep1Label2;
    private javax.swing.JLabel JREInstallationSupuhWikiStep1Label3;
    private javax.swing.JLabel JREInstallationSupuhWikiStep1Label4;
    private javax.swing.JPanel JREInstallationSupuhWikiStep1Tab;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep2Tab;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep2Tab1;
    private javax.swing.JLabel JREInstallationSupuhWikiStep3Label;
    private javax.swing.JLabel JREInstallationSupuhWikiStep3Label1;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep3Tab;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep3Tab1;
    private javax.swing.JLabel JREInstallationSupuhWikiStep4Label;
    private javax.swing.JLabel JREInstallationSupuhWikiStep4Label1;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep4Tab;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep4Tab1;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep5Tab;
    private javax.swing.JScrollPane JREInstallationSupuhWikiStep5Tab1;
    private javax.swing.JPanel JREInstallationSupuhWikiTab;
    private javax.swing.JTabbedPane JREInstallationTab;
    private static javax.swing.JFrame aboutFrame;
    private javax.swing.JLabel aboutLabel;
    private javax.swing.JMenuItem aboutMenuItem;
    private static javax.swing.JButton applyOptionsButton;
    private static javax.swing.JPanel boardPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel colLabelA;
    private javax.swing.JLabel colLabelB;
    private javax.swing.JLabel colLabelC;
    private javax.swing.JLabel colLabelD;
    private javax.swing.JLabel colLabelE;
    private javax.swing.JLabel colLabelF;
    private javax.swing.JLabel colLabelG;
    private javax.swing.JLabel colLabelH;
    private static javax.swing.JPanel color1DisplayPanel;
    private static javax.swing.JPanel color2DisplayPanel;
    private javax.swing.JTextArea consoleOutput;
    private static javax.swing.JPanel consolePane;
    private javax.swing.JButton copyOracleJREInstallerPageURLButton;
    private javax.swing.JButton copySupuhWikiJREInstallerPageURLButton;
    private javax.swing.JButton forfeitButton;
    private javax.swing.JMenuItem forfeitMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JPanel gameTab;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JEditorPane jEditorPane3;
    private javax.swing.JEditorPane jEditorPane4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private static javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton name1ColorChangerButton;
    private javax.swing.JTextField name1Input;
    private javax.swing.JPanel name1InputPanel;
    private javax.swing.JTextField name2Input;
    private javax.swing.JPanel name2InputPanel;
    private static javax.swing.JSlider numOfPlayersSlider;
    private javax.swing.JPanel optionTab;
    private javax.swing.JLabel player1NameLabel;
    private javax.swing.JLabel player2NameLabel;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JScrollPane readmeContactTab;
    private javax.swing.JPanel readmeContent;
    private javax.swing.JTabbedPane readmeContentTabs;
    private javax.swing.JFrame readmeFrame;
    private javax.swing.JPanel readmeHeader;
    private javax.swing.JTabbedPane readmeHowToPlayTab;
    private javax.swing.JScrollPane readmeHowToRunTab;
    private javax.swing.JMenuItem readmeMenuItem;
    private javax.swing.JTabbedPane readmeTheJRETab;
    private javax.swing.JScrollPane readmeVersionChangesTab;
    private javax.swing.JLabel rowLabel1;
    private javax.swing.JLabel rowLabel2;
    private javax.swing.JLabel rowLabel3;
    private javax.swing.JLabel rowLabel4;
    private javax.swing.JLabel rowLabel5;
    private javax.swing.JLabel rowLabel6;
    private javax.swing.JLabel rowLabel7;
    private javax.swing.JLabel rowLabel8;
    private javax.swing.JPanel scoreboardTab;
    private static javax.swing.JMenuItem supuhWikiMenuItem;
    private javax.swing.JFrame testBoardFrame;
    private javax.swing.JPanel testBoardPanel;
    private static javax.swing.JLabel turnOutputLabel;
    // End of variables declaration//GEN-END:variables
// </editor-fold>

  private static void refreshBoard()
  {
    /*TC*/System.out.println("BEGIN refreshBoard()");
    A1.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(0,0) ? "/none32.png" : (isKing(0,0) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    A1.setBackground(boardColor[0][0]);
    A1.setEnabled(isSetUp && clickable(0,0));
    A1.repaint();

    C1.setBackground(boardColor[0][2]);
    C1.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(0,2) ? "/none32.png" : (isKing(0,2) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    C1.setEnabled(isSetUp && clickable(0,2));
    C1.repaint();

    E1.setBackground(boardColor[0][4]);
    E1.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(0,4) ? "/none32.png" : (isKing(0,4) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    E1.setEnabled(isSetUp && clickable(0,4));
    E1.repaint();

    G1.setBackground(boardColor[0][6]);
    G1.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(0,6) ? "/none32.png" : (isKing(0,6) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    G1.setEnabled(isSetUp && clickable(0,6));
    G1.repaint();

    B2.setBackground(boardColor[1][1]);
    B2.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(1,1) ? "/none32.png" : (isKing(1,1) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    B2.setEnabled(isSetUp && clickable(1,1));
    B2.repaint();

    D2.setBackground(boardColor[1][3]);
    D2.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(1,3) ? "/none32.png" : (isKing(1,3) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    D2.setEnabled(isSetUp && clickable(1,3));
    D2.repaint();

    F2.setBackground(boardColor[1][5]);
    F2.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(1,5) ? "/none32.png" : (isKing(1,5) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    F2.setEnabled(isSetUp && clickable(1,5));
    F2.repaint();

    H2.setBackground(boardColor[1][7]);
    H2.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(1,7) ? "/none32.png" : (isKing(1,7) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    H2.setEnabled(isSetUp && clickable(1,7));
    H2.repaint();

    A3.setBackground(boardColor[2][0]);
    A3.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(2,0) ? "/none32.png" : (isKing(2,0) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    A3.setEnabled(isSetUp && clickable(2,0));
    A3.repaint();

    C3.setBackground(boardColor[2][2]);
    C3.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(2,2) ? "/none32.png" : (isKing(2,2) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    C3.setEnabled(isSetUp && clickable(2,2));
    C3.repaint();

    E3.setBackground(boardColor[2][4]);
    E3.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(2,4) ? "/none32.png" : (isKing(2,4) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    E3.setEnabled(isSetUp && clickable(2,4));
    E3.repaint();

    G3.setBackground(boardColor[2][6]);
    G3.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(2,6) ? "/none32.png" : (isKing(2,6) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    G3.setEnabled(isSetUp && clickable(2,6));
    G3.repaint();

    B4.setBackground(boardColor[3][1]);
    B4.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(3,1) ? "/none32.png" : (isKing(3,1) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    B4.setEnabled(isSetUp && clickable(3,1));
    B4.repaint();

    D4.setBackground(boardColor[3][3]);
    D4.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(3,3) ? "/none32.png" : (isKing(3,3) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    D4.setEnabled(isSetUp && clickable(3,3));
    D4.repaint();

    F4.setBackground(boardColor[3][5]);
    F4.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(3,5) ? "/none32.png" : (isKing(3,5) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    F4.setEnabled(isSetUp && clickable(3,5));
    F4.repaint();

    H4.setBackground(boardColor[3][7]);
    H4.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(3,7) ? "/none32.png" : (isKing(3,7) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    H4.setEnabled(isSetUp && clickable(3,7));
    H4.repaint();

    A5.setBackground(boardColor[4][0]);
    A5.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(4,0) ? "/none32.png" : (isKing(4,0) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    A5.setEnabled(isSetUp && clickable(4,0));
    A5.repaint();

    C5.setBackground(boardColor[4][2]);
    C5.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(4,2) ? "/none32.png" : (isKing(4,2) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    C5.setEnabled(isSetUp && clickable(4,2));
    C5.repaint();

    E5.setBackground(boardColor[4][4]);
    E5.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(4,4) ? "/none32.png" : (isKing(4,4) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    E5.setEnabled(isSetUp && clickable(4,4));
    E5.repaint();

    G5.setBackground(boardColor[4][6]);
    G5.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(4,6) ? "/none32.png" : (isKing(4,6) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    G5.setEnabled(isSetUp && clickable(4,6));
    G5.repaint();

    B6.setBackground(boardColor[5][1]);
    B6.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(5,1) ? "/none32.png" : (isKing(5,1) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    B6.setEnabled(isSetUp && clickable(5,1));
    B6.repaint();

    D6.setBackground(boardColor[5][3]);
    D6.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(5,3) ? "/none32.png" : (isKing(5,3) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    D6.setEnabled(isSetUp && clickable(5,3));
    D6.repaint();

    F6.setBackground(boardColor[5][5]);
    F6.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(5,5) ? "/none32.png" : (isKing(5,5) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    F6.setEnabled(isSetUp && clickable(5,5));
    F6.repaint();

    H6.setBackground(boardColor[5][7]);
    H6.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(5,7) ? "/none32.png" : (isKing(5,7) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    H6.setEnabled(isSetUp && clickable(5,7));
    H6.repaint();

    A7.setBackground(boardColor[6][0]);
    A7.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(6,0) ? "/none32.png" : (isKing(6,0) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    A7.setEnabled(isSetUp && clickable(6,0));
    A7.repaint();

    C7.setBackground(boardColor[6][2]);
    C7.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(6,2) ? "/none32.png" : (isKing(6,2) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    C7.setEnabled(isSetUp && clickable(6,2));
    C7.repaint();

    E7.setBackground(boardColor[6][4]);
    E7.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(6,4) ? "/none32.png" : (isKing(6,4) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    E7.setEnabled(isSetUp && clickable(6,4));
    E7.repaint();

    G7.setBackground(boardColor[6][6]);
    G7.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(6,6) ? "/none32.png" : (isKing(6,6) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    G7.setEnabled(isSetUp && clickable(6,6));
    G7.repaint();

    B8.setBackground(boardColor[7][1]);
    B8.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(7,1) ? "/none32.png" : (isKing(7,1) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    B8.setEnabled(isSetUp && clickable(7,1));
    B8.repaint();

    D8.setBackground(boardColor[7][3]);
    D8.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(7,3) ? "/none32.png" : (isKing(7,3) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    D8.setEnabled(isSetUp && clickable(7,3));
    D8.repaint();

    F8.setBackground(boardColor[7][5]);
    F8.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(7,5) ? "/none32.png" : (isKing(7,5) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    F8.setEnabled(isSetUp && clickable(7,5));
    F8.repaint();

    H8.setBackground(boardColor[7][7]);
    H8.setIcon(new javax.swing.ImageIcon(main.getClass().getResource(!isOccupied(7,7) ? "/none32.png" : (isKing(7,7) ? "/Piece background KING.png" : "/Piece background PAWN.png"))));
    H8.setEnabled(isSetUp && clickable(7,7));
    H8.repaint();

    turnOutputLabel.repaint();

//    pack();
//    repaint();
    /*TC*/    System.out.println("END refreshBoard()");
  }

  private static boolean isKing(int f1, int f2)
  {/*TC*/
    System.out.println("BEGIN isKing()");
    if (board[f1][f2] >= 'A' && board[f1][f2] <= 'Z')
    {/*TC*/
      System.out.println("END isKing(true)");
      return true;
    }
    /*TC*/    System.out.println("END isKing(false)");
    return false;
  }

  private static boolean isOccupied(int f1, int f2)
  {/*TC*/
    System.out.println("BEGIN isOccupied(" + location(f1, f2) + ")");
    if (board[f1][f2] != EMPTY || boardColor[f1][f2] != colorX)
    {
      /*TC*/      System.out.println("END isOccupied(true)");
      return true;
    }
    /*TC*/    System.out.println("END isOccupied(false)");
    return false;
  }

  private void select(int x, int y)
  {/*TC*/
    System.out.println("BEGIN select(" + x + ", " + y + ")");
    System.out.println(boardPanel.getHeight() + ", " + boardPanel.getWidth());
    setTurn();
    if (!fromSelected)
    {
      if (isOccupied(x, y))
      {
        if(isMyPiece(x,y))
        {
          from1 = x;
          from2 = y;
          fromSelected = true;
          output(trans("Moving from ", "Bewegen von ", "Moviendo a ") + location(x, y));
        }
        else
          output(trans("You must select your own piece.", "", ""));
      }
      else
      {
        output(trans("There is no piece to move at ", "Es gibt keinen bewegbaren Stein auf ",
                     "No hay ninguna pieza para mover en ") + location(x, y));
      }
    }
    else
    {
      {
        if (!isOccupied(x, y))
        {
          to1 = x;
          to2 = y;
          output(location(from1, from2) + " moved to " + location(to1, to2));
          fromSelected = false;
          tryMove(to1, to2);
        }
        else
        {
          output("There is already a piece at " + location(x, y) + ".");
        }
      }
    }
    refreshBoard();
    /*TC*/    System.out.println("END select()");
  }

  private void cancelMove()
  {
    fromSelected = false;
    output("move canceled");
  }

  private void output(String string)
  {
    console += "\n\r" + string;
    consoleOutput.setText(console);
  }

  private void tryMove(int x, int y)
  {
    if (!fromSelected)
    {
      if (!isOccupied(to1, to2))
      {
        board[to1][to2] = board[from1][from2];
        board[from1][from2] = EMPTY;
        boardColor[to1][to2] = boardColor[from1][from2];
        boardColor[from1][from2] = colorX;

        switchTurn();
        refreshBoard();
      }
      else
      {
        output(trans("There is already a piece there.", "", ""));
      }
    }
    refreshBoard();
  }

  private void switchTurn()
  {
    move = !move;
    if (move)
    {
      color = color1;
      mark = mark1;
      name = name1;
      notName = name2;
      notColor = color2;
      notMark = mark2;
      notPiece = pieces2;
      piece = pieces1;
    }
    else
    {
      color = color2;
      mark = mark2;
      name = name2;
      notName = name1;
      notColor = color1;
      notMark = mark1;
      notPiece = pieces1;
      piece = pieces2;
    }

    turnOutput(name + "'s turn");

  }
  private void setTurn()
  {
    if (move)
    {
      color = color1;
      mark = mark1;
      name = name1;
      notName = name2;
      notColor = color2;
      notMark = mark2;
      notPiece = pieces2;
      piece = pieces1;
    }
    else
    {
      color = color2;
      mark = mark2;
      name = name2;
      notName = name1;
      notColor = color1;
      notMark = mark1;
      notPiece = pieces1;
      piece = pieces2;
    }
  }

  private void turnOutput(String string)
  {
    turnOutputLabel.setText("<html><center>" + string + "</center></html>");
    turnOutputLabel.repaint();
  }

  public static int boardWidth()
  {
    return boardPanel.getWidth();
  }

  public static int boardHeight()
  {
    return boardPanel.getHeight();
  }

  public static Dimension boardDimension()
  {
    return new java.awt.Dimension(boardHeight(), boardWidth());
  }

  public static int boardPieceWidth()
  {
    return boardPanel.getWidth() / 9;
  }

  public static int boardPieceHeight()
  {
    return boardPanel.getHeight() / 9;
  }

  public static Dimension boardPieceDimension()
  {
    return new java.awt.Dimension(40, 40);
  }

  private void setDim(JButton A1)
  {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private static void stopTimer2()
  {
    timer2.stop();
    System.err.println("Window successfully expanded.");
  }

  public static String readme()
  {
    if (language.equals("de"))
    {
      return  "   ╔╗ ╔╗ ╔╗╔╦══╗\n" +
              "   ║╚═╣║ ║║║║╔╗║\n" +
              "   ║╔╗║║ ║║║║╚╝║\n" +
              "   ║╚╝║╚═╣╚╝║══╣\n" +
              "  ╔╬╦╦╬╦╦╩═╦╬╦╦╣╔╗ ╔╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦═══════╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╗\n" +
              "  ║╚╝║║║║══╣╠╝║╚╝║ ╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣ACHTUNG╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣\n" +
              "  ║╔╗║║║╠═╗║ ═╬╗╔╝ ╠╬╬╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩═══════╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╬╬╣\n" +
              "  ║║║║╚╝╠═╝║╠╗║║║  ╠╬╣ Informationen in dieser README-Datei sind möglicherweise ╠╬╣\n" +
              "╔═╩╬╩╩╦═╩╦═╩╬╩╬╬╩═╗╠╬╣   veraltet, Sie finden die aktuellste README-Datei auf   ╠╬╣\n" +
              "║╔═╣╔╗║  ╠╗╔╣ ║║╔═╝╠╬╣ http://supuh.wikia.com/wiki/Blue_Husky's_Checkers/Readme ╠╬╣\n" +
              "║║╔╣╚╝║║║║║║║║║║║╔╗╠╬╬╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╬╬╣\n" +
              "║╚╝║╔╗║║║╠╝╚╣║ ║╚╝║╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣\n" +
              "╚══╩╝╚╩╩╩╩══╩╩═╩══╝╚╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╝\n</html>";
    }
    else if (language.equals("es"))
    {
      return  "   ╔╗ ╔╗ ╔╗╔╦══╗\n" +
              "   ║╚═╣║ ║║║║╔╗║\n" +
              "   ║╔╗║║ ║║║║╚╝║\n" +
              "   ║╚╝║╚═╣╚╝║══╣\n" +
              "  ╔╬╦╦╬╦╦╩═╦╬╦╦╣╔╗ ╔╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦════════╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╗\n" +
              "  ║╚╝║║║║══╣╠╝║╚╝║ ╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣ATENCIÓN╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣\n" +
              "  ║╔╗║║║╠═╗║ ═╬╗╔╝ ╠╬╬╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩════════╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╬╬╣\n" +
              "  ║║║║╚╝╠═╝║╠╗║║║  ╠╬╣ Información en este archivo Léame puede ser obsoleta. Por╠╬╣\n" +
              "╔═╩╬╩╩╦═╩╦═╩╬╩╬╬╩═╗╠╬╣         favor vea el Léame siempre actualizado en        ╠╬╣\n" +
              "║╔═╣╔╗║  ╠╗╔╣ ║║╔═╝╠╬╣ http://supuh.wikia.com/wiki/Blue_Husky's_Checkers/Readme ╠╬╣\n" +
              "║║╔╣╚╝║║║║║║║║║║║╔╗╠╬╬╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╬╬╣\n" +
              "║╚╝║╔╗║║║╠╝╚╣║ ║╚╝║╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣\n" +
              "╚══╩╝╚╩╩╩╩══╩╩═╩══╝╚╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╝\n</html>";
    }
    else
    {
      return  "   ╔╗ ╔╗ ╔╗╔╦══╗\n" +
              "   ║╚═╣║ ║║║║╔╗║\n" +
              "   ║╔╗║║ ║║║║╚╝║\n" +
              "   ║╚╝║╚═╣╚╝║══╣\n" +
              "  ╔╬╦╦╬╦╦╩═╦╬╦╦╣╔╗ ╔╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦═════════╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╗\n" +
              "  ║╚╝║║║║══╣╠╝║╚╝║ ╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣ATTENTION╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣\n" +
              "  ║╔╗║║║╠═╗║ ═╬╗╔╝ ╠╬╬╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩═════════╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╬╬╣\n" +
              "  ║║║║╚╝╠═╝║╠╗║║║  ╠╬╣ Information in this readme may be out-of-date. Please see╠╬╣\n" +
              "╔═╩╬╩╩╦═╩╦═╩╬╩╬╬╩═╗╠╬╣              the always-up-to-date readme at             ╠╬╣\n" +
              "║╔═╣╔╗║  ╠╗╔╣ ║║╔═╝╠╬╣ http://supuh.wikia.com/wiki/Blue_Husky's_Checkers/Readme ╠╬╣\n" +
              "║║╔╣╚╝║║║║║║║║║║║╔╗╠╬╬╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╦╬╬╣\n" +
              "║╚╝║╔╗║║║╠╝╚╣║ ║╚╝║╠╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╬╣\n" +
              "╚══╩╝╚╩╩╩╩══╩╩═╩══╝╚╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╩╝\n</html>";
    }
  }

  public static String trans(String english, String german, String spanish)
  {
    language = System.getProperty("user.language");
    if (language.equals("es"))
    {
      return spanish;
    }
    else if (language.equals("de"))
    {
      return german;
    }
    else
    {
      return english;
    }
  }

  private void forfeit()
  {
    if ((JOptionPane.showConfirmDialog(this, trans("Are you sure to want to forfeit?",
                                                   "sind Sie sicher, dass Sie aufgeben wollen?",
                                                   "¿Estás seguro de querer perder?"), "Forfeit? - " + TITLE, 0, 0)) == 0)
    {
      quit = true;
      console += "\n\r" + name + trans(" has forfeit.", " hat aufgegeben.", " tiene pérdida.");
      
    }
  }

  private static int windowHeight()
  {
    return 512;
  }

  private static int windowWidth()
  {
    return 680;
  }

  private static boolean isMyPiece(int x, int y)
  {
    return (board[x][y] == mark || board[x][y] == (char)(mark - 32));
  }

  private static boolean clickable(int x, int y)
  {
    return (isMyPiece(x,y) || !isOccupied(x,y));
  }

  private static void slideOpen(final JFrame frame, final int WIDTH, final int HEIGHT)
  {
//    double w = 16;//WIDTH / 32;
//    double h = 16;//HEIGHT / 32;
    frame.setMinimumSize(new Dimension(0,0));
    frame.setSize(new Dimension(0,0));
    dim = frame.getSize();
    timer2 = new Timer(50, new java.awt.event.ActionListener()
    {
      int w = WIDTH, h = HEIGHT;
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        System.out.println("WIDTH - w, HEIGHT - h = " + WIDTH + " - " + w + ", " + HEIGHT + " - " + h);

        if (dim.getWidth() < WIDTH || dim.getHeight() < HEIGHT)
        {
          dim = new Dimension(WIDTH - w, HEIGHT - h);

          if (dim.getWidth() < WIDTH)
          {
            w = (int)(w / 1.25);
          }
          else
          {
            h = (int)(h / 1.25);
          }
          frame.setSize(dim);
          frame.repaint();
        }
        else
        {
          stopTimer2();
          frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        }
//        System.out.println("WIDTH: " + WIDTH + ", HEIGHT: " + HEIGHT + ", w: " + w + ", h: " + h + ", frame.getSize().toString(): " + frame.getSize().toString() + ", dim:" + dim + ", timer2.isRunning(): " + timer2.isRunning());
      }
    });
    frame.setVisible(true);
    timer2.start();
  }
  /** <editor-fold defaultstate="collapsed" desc="Stupid freakin' icon code I never finished">

  public static Image gameIcon = new Image()
  {
  @Override
  public int getWidth(ImageObserver observer)
  {
  return 16;
  }

  @Override
  public int getHeight(ImageObserver observer)
  {
  return 16;
  }

  @Override
  public ImageProducer getSource()
  {
  return new ImageProducer() {

  public void addConsumer(ImageConsumer ic)
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }

  public boolean isConsumer(ImageConsumer ic)
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }

  public void removeConsumer(ImageConsumer ic)
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }

  public void startProduction(ImageConsumer ic)
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }

  public void requestTopDownLeftRightResend(ImageConsumer ic)
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }
  };
  }

  @Override
  public Graphics getGraphics()
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Object getProperty(String name, ImageObserver observer)
  {
  throw new UnsupportedOperationException("Not supported yet.");
  }
  };
   */
  //</editor-fold>
}
