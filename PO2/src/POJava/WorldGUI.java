package POJava;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class WorldGUI implements ActionListener, KeyListener {
    private Toolkit toolkit ;
    private Dimension dimension;
    private JFrame jFrame;
    private JMenu menu;
    private JMenuItem newGame, load, save, exit;
    private BoardGraphics boardGraphics = null;
    private GraphicsLog graphicsLog = null;
    private Marks marks = null;
    private JPanel mainPanel;
    private final int BREAK;
    private World world;

    public WorldGUI(String title) {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        BREAK = dimension.height / 100;


        jFrame = new JFrame(title);
        jFrame.setBounds((dimension.width - 800) / 2, (dimension.height - 600) / 2, 800, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New game");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(newGame);
        menu.add(load);
        menu.add(save);
        menu.add(exit);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setLayout(null);


        jFrame.addKeyListener(this);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            Log.ClearLogs();
            int sizeX = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Width", "10"));
            int sizeY = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "height", "10"));
            world = new World(sizeX, sizeY, this);
            world.GenerateWorld();
            if (boardGraphics != null)
                mainPanel.remove(boardGraphics);
            if (graphicsLog != null)
                mainPanel.remove(graphicsLog);
            if (marks != null)
                mainPanel.remove(marks);
            startGame();
        }
        if (e.getSource() == load) {
            Log.ClearLogs();
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "File name", "test");
            world = World.Load(nameOfFile);
            world.setWorldGUI(this);
            boardGraphics = new BoardGraphics(world);
            graphicsLog = new GraphicsLog();
            marks = new Marks();
            if (boardGraphics != null)
                mainPanel.remove(boardGraphics);
            if (graphicsLog != null)
                mainPanel.remove(graphicsLog);
            if (marks != null)
                mainPanel.remove(marks);
            startGame();
        }
        if (e.getSource() == save) {
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "File name", "test");
            world.Save(nameOfFile);
            Log.AddComment("World has been created");
            graphicsLog.RefreshLog();
        }
        if (e.getSource() == exit) {
            jFrame.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (world != null && world.getPause()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

            } else if (world.getHumanAlive()) {
                if (keyCode == KeyEvent.VK_UP) {
                    world.getHuman().setDirection(Organism.Direction.UP);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    world.getHuman().setDirection(Organism.Direction.DOWN);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    world.getHuman().setDirection(Organism.Direction.LEFT);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    world.getHuman().setDirection(Organism.Direction.RIGHT);
                } else if (keyCode == KeyEvent.VK_P) {
                    if (world.getHuman().GetCooldown() == 0) {
                        world.getHuman().UseAbility();
                        Log.AddComment("Alzur's Shield activated");

                    } else if (world.getHuman().GetCooldown() <= 5) {
                        Log.AddComment("Ability on cooldown for "
                                + world.getHuman().GetCooldown() + " more rounds");
                        graphicsLog.RefreshLog();
                        return;
                    }
                } else {
                    Log.AddComment("\nUnknown symbol, try again");
                    graphicsLog.RefreshLog();
                    return;
                }
            } else if (!world.getHumanAlive() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P)) {
                Log.AddComment("Human is dead, you can't control it anymore");
                graphicsLog.RefreshLog();
                return;
            } else {
                Log.AddComment("\nUnknown symbol, try again");
                graphicsLog.RefreshLog();
                return;
            }
            Log.ClearLogs();
            world.setPause(false);
            world.ExecuteRound();
            RefreshWorld();
            world.setPause(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class BoardGraphics extends JPanel {
        private final int sizeX;
        private final int sizeY;
        private BoardField[][] boardField;
        private World WORLD;

        public BoardGraphics(World world) {
            super();
            setBounds(mainPanel.getX() + BREAK, mainPanel.getY() + BREAK,
                    mainPanel.getHeight() * 5 / 6 - BREAK, mainPanel.getHeight() * 5 / 6 - BREAK);
            WORLD = world;
            this.sizeX = world.getSizeX();
            this.sizeY = world.getSizeY();

            boardField = new BoardField[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    boardField[i][j] = new BoardField(j, i);
                    boardField[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof BoardField) {
                                BoardField tmpPole = (BoardField) e.getSource();
                                if (tmpPole.isEmpty) {
                                    OrganismList organismList= new OrganismList
                                            (tmpPole.getX() + jFrame.getX(),
                                                    tmpPole.getY() + jFrame.getY(),
                                                    new Point(tmpPole.getPozX(), tmpPole.getPozY()));
                                }
                            }
                        }
                    });
                }
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(boardField[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }

        private class BoardField extends JButton {
            private boolean isEmpty;
            private Color color;
            private final int pozX;
            private final int pozY;

            public BoardField(int X, int Y) {
                super();
                color = Color.WHITE;
                setBackground(color);
                isEmpty = true;
                pozX = X;
                pozY = Y;
            }

            public boolean isEmpty() {
                return isEmpty;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }


            public Color getColor() {
                return color;
            }

            public void setKolor(Color kolor) {
                this.color = kolor;
                setBackground(kolor);
            }

            public int getPozX() {
                return pozX;
            }

            public int getPozY() {
                return pozY;
            }
        }

        public void odswiezPlansze() {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organism tmpOrganizm = world.getBoard()[i][j];
                    if (tmpOrganizm != null) {
                        boardField[i][j].setEmpty(false);
                        boardField[i][j].setEnabled(false);
                        boardField[i][j].setKolor(tmpOrganizm.getColor());
                    } else {
                        boardField[i][j].setEmpty(true);
                        boardField[i][j].setEnabled(true);
                        boardField[i][j].setKolor(Color.WHITE);
                    }
                }
            }
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public BoardField[][] getBoardField() {
            return boardField;
        }
    }

    private class GraphicsLog extends JPanel {
        private String text;
        private final String instriction = "Jakub Janeczek\nArrows - human movement\n" +
                "P - special ability\nEnter - next round\n";
        private JTextArea textArea;

        public GraphicsLog() {
            super();
            setBounds(boardGraphics.getX() + boardGraphics.getWidth() + BREAK,
                    mainPanel.getY() + BREAK,
                    mainPanel.getWidth() - boardGraphics.getWidth() - BREAK * 3,
                    mainPanel.getHeight() * 5 / 6 - BREAK);
            text = Log.getLog();
            textArea = new JTextArea(text);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void RefreshLog() {
            text = instriction + Log.getLog();
            textArea.setText(text);
        }
    }

    private class OrganismList extends JFrame {
        private String[] listaOrganizmow;
        private Organism.OrganismType[] typOrganizmuList;
        private JList jList;

        public OrganismList(int x, int y, Point point) {
            super("List of organisms");
            setBounds(x, y, 100, 300);
            listaOrganizmow = new String[]{"Pineborscht", "Guarana", "Dandelion", "Grass",
                    "Belladonna", "Antelope", "Fox", "Sheep", "Wolf", "Turtle"};
            typOrganizmuList = new Organism.OrganismType[]{Organism.OrganismType.PINEBORSCHT,
                    Organism.OrganismType.GUARANA, Organism.OrganismType.DANDELION, Organism.OrganismType.GRASS,
                    Organism.OrganismType.BELLADONNA, Organism.OrganismType.ANTYLOPE,
                    Organism.OrganismType.FOX,
                    Organism.OrganismType.SHEEP, Organism.OrganismType.WOLF,
                    Organism.OrganismType.TURTLE
            };

            jList = new JList(listaOrganizmow);
            jList.setVisibleRowCount(listaOrganizmow.length);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Organism tmpOrganism = OrganismsGenerator.CreateNewOrganism
                            (typOrganizmuList[jList.getSelectedIndex()], world, point);
                    world.AddOrganism(tmpOrganism);
                    Log.AddComment("Created new organism " + tmpOrganism.OrganismTypeToString());
                    RefreshWorld();
                    dispose();

                }
            });

            JScrollPane sp = new JScrollPane(jList);
            add(sp);

            setVisible(true);
        }
    }

    private class Marks extends JPanel {
        private final int TYPES_NUMBER = 11;
        private JButton[] jButtons;

        public Marks() {
            super();
            setBounds(mainPanel.getX() + BREAK, mainPanel.getHeight() * 5 / 6 + BREAK,
                    mainPanel.getWidth() - BREAK * 2,
                    mainPanel.getHeight() * 1 / 6 - 2 * BREAK);
            setBackground(Color.WHITE);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            jButtons = new JButton[TYPES_NUMBER];
            jButtons[0] = new JButton("Pineborscht");
            jButtons[0].setBackground(new Color(204, 0, 204));

            jButtons[1] = new JButton("Guarana");
            jButtons[1].setBackground(Color.RED);

            jButtons[2] = new JButton("Dandelion");
            jButtons[2].setBackground(Color.YELLOW);

            jButtons[3] = new JButton("Grass");
            jButtons[3].setBackground(Color.GREEN);

            jButtons[4] = new JButton("Belladonna");
            jButtons[4].setBackground(new Color(25, 0, 51));

            jButtons[5] = new JButton("Antelope");
            jButtons[5].setBackground(new Color(153, 76, 0));

            jButtons[6] = new JButton("Human");
            jButtons[6].setBackground(Color.BLUE);

            jButtons[7] = new JButton("Fox");
            jButtons[7].setBackground(new Color(255, 128, 0));

            jButtons[8] = new JButton("Sheep");
            jButtons[8].setBackground(new Color(255, 153, 204));

            jButtons[9] = new JButton("Wolf");
            jButtons[9].setBackground(new Color(64, 64, 64));

            jButtons[10] = new JButton("Turtle");
            jButtons[10].setBackground(new Color(0, 102, 0));

            for (int i = 0; i < TYPES_NUMBER; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }

        }
    }

    private void startGame() {
        boardGraphics = new BoardGraphics(world);
        mainPanel.add(boardGraphics);

        graphicsLog = new GraphicsLog();
        mainPanel.add(graphicsLog);

        marks = new Marks();
        mainPanel.add(marks);

        RefreshWorld();
    }

    public void RefreshWorld() {
        boardGraphics.odswiezPlansze();
        graphicsLog.RefreshLog();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public World getWorld() {
        return world;
    }

    public BoardGraphics getBoardGraphics() {
        return boardGraphics;
    }

    public GraphicsLog getGraphicsLog() {
        return graphicsLog;
    }
}
