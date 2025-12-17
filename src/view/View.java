package view;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * View Class that extends JFrame to make the GUI
 * @author Robert Gabriel Macalintal
 *
 */
public class View extends JFrame {
	
	/**
	 * Serial Number
	 */
	private static final long serialVersionUID = 1L;
	
	private int counterTA = 0;

	// Define new JFrame
	private JFrame frame = new JFrame("BATTLESHIP");
	
	// Define Main UI Panel + Color
	private JPanel userInterface = new JPanel();
	private Color uiBGColor = new Color(128, 128, 128);
	
	// Define Inner Panels
	private JPanel titlePanel = new JPanel();
	private JPanel textAreaPanel = new JPanel();
	private JPanel gridAreaPanel = new JPanel();
	
	private JPanel mainGrid = new JPanel();
	private JPanel gridPanelRight = new JPanel();
	private JPanel gridPanelControls = new JPanel();
	private JPanel indicatorPanel = new JPanel();
	
	private JPanel statusPanel = new JPanel();
	
	// Define Colors
	private Color lightGreen = new Color(204, 221, 221);
	private Color darkGreen = new Color(85, 153, 136);
	
	// Define Buttons
	private JButton swapButton = new JButton("Swap");
	private JButton flipButton = new JButton("Flip");
	
	private JButton carrier5 = new JButton("Carr-5");
	private JButton cruiser4 = new JButton("Crui-4");
	private JButton destroyer3 = new JButton("Dest-3");
	private JButton frigate3 = new JButton("MFri-3");
	private JButton sub2 = new JButton("Subm-2");
	
	// Define Grid Buttons 2-D Array
	private JButton[][] gridButtons = new JButton[10][10];
	
	// Define Text Field
	private final JTextField textField = new JTextField();
	
	// Define Labels
	private JLabel battleshipTitle = new JLabel("BATTLESHIP");
	
	private JLabel playerSide = new JLabel("Player's Side");
	private JLabel enemySide = new JLabel("Enemy's Side");
	
	private JLabel vert = new JLabel("|V|");
	private JLabel shipType = new JLabel("N/A");
	
	private JTextArea textArea = new JTextArea(25,0);
	
	private JLabel phaseLabel = new JLabel(" Place Your Ships");
	private JLabel gameStatus = new JLabel("<html>Phase 1<br>"
			+ "Click on a square to place ship.<br>"
			+ "<br>"
			+ "Your Ships:<br>"
			+ "Carrier: 0/5<br>"
			+ "Cruiser: 0/4<br>"
			+ "Destroyer: 0/3<br>"
			+ "Missile Frigate: 0/3<br>"
			+ "Submarine: 0/2<br>"
			+ "<br>"
			+ "Enemy Ships:<br>"
			+ "Carrier: 5/5<br>"
			+ "Cruiser: 4/4<br>"
			+ "Destroyer: 3/3<br>"
			+ "Missile Frigate: 3/3<br>"
			+ "Submarine: 2/2</html>");
	
	// Define Scroller
	private JScrollPane textScroller;
	
	// Define Menu Items
	private JMenu fileMenu = new JMenu("File");
	private JMenu langMenu = new JMenu("Language");
	private JMenu helpMenu = new JMenu("Help");
	private JMenu networkMenu = new JMenu("Network");
	private JMenuItem newOption = new JMenuItem("New");
	private JMenuItem aboutOption = new JMenuItem("About");
	private JMenuItem devModeOption = new JMenuItem("Dev Mode");
	private JMenuItem exitOption = new JMenuItem("Exit");
	private JMenuItem englishLang = new JMenuItem("English");
	private JMenuItem tagalogLang = new JMenuItem("Tagalog");
	private JMenuItem hostGameOption = new JMenuItem("Host a Game");
	private JMenuItem joinGameOption = new JMenuItem("Join a Game");
	private JMenuItem singlePlayerOption = new JMenuItem("Single Player");
	
	//Define GridBag Layout Constraints
	private GridBagConstraints constraints = new GridBagConstraints();
	
	//Add Button Icons
	private ImageIcon[] iconArray = new ImageIcon[6];
	private ImageIcon bowNorth = new ImageIcon("src/view/icons/Assets/bow_north.png");
	private ImageIcon bowEast = new ImageIcon("src/view/icons/Assets/bow_east.png");
	private ImageIcon bowSouth = new ImageIcon("src/view/icons/Assets/bow_south.png");
	private ImageIcon bowWest = new ImageIcon("src/view/icons/Assets/bow_west.png");
	private ImageIcon midH = new ImageIcon("src/view/icons/Assets/midhull_horiz.png");
	private ImageIcon midV = new ImageIcon("src/view/icons/Assets/midhull_vert.png");
	
	private ImageIcon hit = new ImageIcon("src/view/icons/Assets/hit.png");
	private ImageIcon miss = new ImageIcon("src/view/icons/Assets/miss.png");
	private ImageIcon empty = new ImageIcon("src/view/icons/Assets/empty.png");
	
	private int[] playerShips = {0, 0, 0, 0, 0};
	private int[] enemyShips = {5, 4, 3, 3, 2};
	
	private CustomDialog networkDialog = new CustomDialog(frame);
	
	public View() {
		/*
		 * Set Border Layout
		 */
		setLayout(new BorderLayout());
		
		/*
		 *  Set close condition on Frame
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		 * Set Menu Bar
		 */
		JMenuBar menuBar = new JMenuBar();
		
		/*
		 * Set Action Command for Components that need it
		 */
		swapButton.setActionCommand("Swap");
		flipButton.setActionCommand("Flip");
		fileMenu.setActionCommand("File");
		langMenu.setActionCommand("Language");
		helpMenu.setActionCommand("Help");
		networkMenu.setActionCommand("Network");
		newOption.setActionCommand("New");
		aboutOption.setActionCommand("About");
		devModeOption.setActionCommand("Dev Mode");
		exitOption.setActionCommand("Exit");
		englishLang.setActionCommand("English");
		tagalogLang.setActionCommand("Tagalog");
		hostGameOption.setActionCommand("Host a Game");
		joinGameOption.setActionCommand("Join a Game");
		singlePlayerOption.setActionCommand("Single Player");
		
		textField.setActionCommand("Enter");
		
		/*
		 *  Set Inner Panel Layouts
		 */
		titlePanel.setLayout(new GridBagLayout());
		textAreaPanel.setLayout(new BorderLayout());
		gridAreaPanel.setLayout(new BorderLayout());
		
		mainGrid.setLayout(new GridLayout(10, 10, 5, 5));
		gridPanelRight.setLayout(new BorderLayout());
		
		indicatorPanel.setLayout(new FlowLayout());
		gridPanelControls.setLayout(new FlowLayout());
		statusPanel.setLayout(new BorderLayout());
		
		/*
		 *  Set font styles for components that need it
		 */
		battleshipTitle.setFont(new Font("SanSerif", Font.BOLD, 60));
		playerSide.setFont(new Font("SanSerif", Font.BOLD, 33));
		enemySide.setFont(new Font("SanSerif", Font.BOLD, 33));
		phaseLabel.setFont(new Font("SanSerif", Font.BOLD, 30));
		gameStatus.setFont(new Font("SanSerif", Font.PLAIN, 18));
		textArea.setFont(new Font("SanSerif", Font.PLAIN, 15));
		
		/*
		 *  Set border for components that need it
		 */
		gameStatus.setBorder(BorderFactory.createLineBorder(lightGreen, 10));
		textArea.setBorder(BorderFactory.createLineBorder(lightGreen, 5));
		textField.setBorder(BorderFactory.createLineBorder(lightGreen, 5));
		gridAreaPanel.setBorder(BorderFactory.createLineBorder(darkGreen, 1));
		statusPanel.setBorder(BorderFactory.createLineBorder(darkGreen, 10));
		indicatorPanel.setBorder(BorderFactory.createLineBorder(darkGreen, 10));
		gridPanelControls.setBorder(BorderFactory.createLineBorder(darkGreen, 10));
		
		/*
		 *  Set Grid labels
		 */
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridButtons[i][j] = new JButton();
				gridButtons[i][j].setActionCommand(i + "" + j);
				gridButtons[i][j].setBackground(Color.white);
			}
		}
		
		/*
		 * Define Image Icon Array
		 */
		iconArray[0] = bowNorth;
		iconArray[1] = bowEast;
		iconArray[2] = bowSouth;
		iconArray[3] = bowWest;
		iconArray[4] = midH;
		iconArray[5] = midV;
		
		/* 
		 * Set alignment for components that need it
		 */
		battleshipTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		playerSide.setAlignmentX(Component.CENTER_ALIGNMENT);
		enemySide.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		phaseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		textArea.setAlignmentY(Component.TOP_ALIGNMENT);
		
		/* 
		 * NOTE: Panel sizes are scaled by 2/3 from original plan for visibility reasons
		 * 		Font was also changed to be more universal
		 */
		
		/*
		 *  Set UI Size and Color
		 */
		userInterface.setPreferredSize(new Dimension(1280, 720));
		userInterface.setBackground(uiBGColor);
		
		/*
		 *  Set Inner Panels Sizes
		 */
		titlePanel.setPreferredSize(new Dimension(1270, 134));
		textAreaPanel.setPreferredSize(new Dimension(320, 570));
		gridAreaPanel.setPreferredSize(new Dimension(945, 570));
		
		mainGrid.setPreferredSize(new Dimension(570, 570));
		gridPanelRight.setPreferredSize(new Dimension(365, 570));
		
		indicatorPanel.setPreferredSize(new Dimension(275, 70));
		gridPanelControls.setPreferredSize(new Dimension(50, 520));
		statusPanel.setPreferredSize(new Dimension(250, 520));
		
		textArea.setPreferredSize(new Dimension(300, 540));
		textField.setPreferredSize(new Dimension(300, 40));
		
		/*
		 *  Set Inner Panels Colors
		 */
		titlePanel.setBackground(lightGreen);
		textAreaPanel.setBackground(lightGreen);
		gridAreaPanel.setBackground(darkGreen);
		
		statusPanel.setBackground(lightGreen);
		
		textArea.setBackground(lightGreen);
		
		/*
		 * Set components to transparent if needed
		 */
		mainGrid.setOpaque(false);
		gridPanelRight.setOpaque(false);
		indicatorPanel.setOpaque(false);
		gridPanelControls.setOpaque(false);
		
		/*
		 *  Add Text to Title Panel
		 */
		constraints.weightx = 10;
		constraints.anchor = GridBagConstraints.CENTER;
		titlePanel.add(battleshipTitle, constraints);
		
		/*
		 *  Add components to Main UI
		 */
		userInterface.add(titlePanel, BorderLayout.NORTH);
		userInterface.add(textAreaPanel, BorderLayout.WEST);
		userInterface.add(gridAreaPanel, BorderLayout.EAST);
		
		/*
		 *  Add components to Grid Area Panel
		 */
		gridAreaPanel.add(mainGrid, BorderLayout.WEST);
		gridAreaPanel.add(gridPanelRight, BorderLayout.EAST);
		
		/*
		 *  Add components to Grid Panel
		 */
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mainGrid.add(gridButtons[i][j]);
				gridButtons[i][j].setPreferredSize(new Dimension(30, 30));
			}
		}
		
		/*
		 *  Initialize Grid Visibility
		 */
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridButtons[i][j].setVisible(true);
				gridButtons[i][j].setEnabled(true);
				
			}
		}
		
		/*
		 * Initialize Player Side Text Visibility
		 */
		playerSide.setVisible(true);
		enemySide.setVisible(false);
	
		/*
		 *  Add components to Text Area Panel
		 */
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		appendToTA("Initializing game boards...\n\n"
				+ "Note when placing ships:\n"
				+ "Vertical placements: top to bottom\n"
				+ "Horizontal placements: left to right\n\n"
				+ "And don't forget to choose your ship type!\n\n");
		
		textScroller = new JScrollPane(textArea);
		
		textAreaPanel.add(textScroller, BorderLayout.CENTER);
		textAreaPanel.add(textField, BorderLayout.SOUTH);
		
		/*
		 *  Add components to Grid's Right side Panel
		 */
		gridPanelRight.add(indicatorPanel, BorderLayout.NORTH);
		gridPanelRight.add(statusPanel, BorderLayout.CENTER);
		gridPanelRight.add(gridPanelControls, BorderLayout.WEST);
		
		gridPanelControls.add(swapButton);
		gridPanelControls.add(flipButton);
		
		gridPanelControls.add(vert);
		
		gridPanelControls.add(carrier5);
		gridPanelControls.add(cruiser4);
		gridPanelControls.add(destroyer3);
		gridPanelControls.add(frigate3);
		gridPanelControls.add(sub2);
		
		gridPanelControls.add(shipType);
		
		indicatorPanel.add(playerSide);
		indicatorPanel.add(enemySide);
		
		statusPanel.add(phaseLabel, BorderLayout.NORTH);
		statusPanel.add(gameStatus, BorderLayout.CENTER);
		
		/*
		 * Add Menu Bar
		 */
		setJMenuBar(menuBar);
		
		/*
		 * Set Mnemonics for Menu options
		 */
		fileMenu.setMnemonic('F');
		helpMenu.setMnemonic('H');
		langMenu.setMnemonic('L');
		
		/*
		 * Add Menu Items
		 */
		newOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		
		exitOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		
		englishLang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		
		tagalogLang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		
		aboutOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
		
		devModeOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
		
		hostGameOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		
		joinGameOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_DOWN_MASK));
		
		singlePlayerOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		
		/*
		 * Add Menu Items to appropriate Menu Options
		 */
		menuBar.add(buildMenu(fileMenu, new Object[] {newOption, null, exitOption}));
		menuBar.add(buildMenu(langMenu, new Object[] {englishLang, tagalogLang}));
		menuBar.add(buildMenu(helpMenu, new Object[] {aboutOption, null, devModeOption}));
		menuBar.add(buildMenu(networkMenu, new Object[] {hostGameOption, joinGameOption, null, singlePlayerOption}));
		
		/*
		 *  Add the Main UI
		 */
		frame.add(userInterface, BorderLayout.CENTER);
		
		/*
		 * Set UI conditions then Pack
		 */
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
	}
	
	/**
	 * Adds listener to components that need it
	 * @param cl - listener to be added
	 */
	public void addContListener(ActionListener cl) {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				gridButtons[i][j].addActionListener(cl);
			}
		}
		
		swapButton.addActionListener(cl);
		flipButton.addActionListener(cl);
		
		carrier5.addActionListener(cl);
		cruiser4.addActionListener(cl);
		destroyer3.addActionListener(cl);
		frigate3.addActionListener(cl);
		sub2.addActionListener(cl);
		
		newOption.addActionListener(cl);
		exitOption.addActionListener(cl);
		englishLang.addActionListener(cl);
		tagalogLang.addActionListener(cl);
		aboutOption.addActionListener(cl);
		devModeOption.addActionListener(cl);
		hostGameOption.addActionListener(cl);
		joinGameOption.addActionListener(cl);
		singlePlayerOption.addActionListener(cl);
		
		textField.addActionListener(cl);
		
	}
	
	/**
	 * Swaps grid view
	 * @param gridSwap - boolean indicating player side or enemy side
	 * @param grid - grid values to be evaluated
	 * @param devMode - checks if developer mode is on or not
	 * @param gameState - checks the phase of the game
	 */
	public void swapGrid(boolean gridSwap, int[][] grid, boolean devMode, int gameState) {
		
		boolean player = true;
		
		// Swap Side Labels depending on Swap Boolean
		if (gridSwap == false) {
			player = true;
			playerSide.setVisible(true);
			enemySide.setVisible(false);
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					gridButtons[i][j].setBackground(Color.WHITE);
					gridButtons[i][j].setText("");
					gridButtons[i][j].setIcon(null);
					hitMissVisible(grid[i][j], i, j);
					if (grid[i][j] > 0) {
						gridButtons[i][j].setText("" + grid[i][j]);
					}
				}
			}
//		} else if (gridSwap == false && devMode == true){
//			player = true;
//			playerSide.setVisible(true);
//			enemySide.setVisible(false);
//			
//			devModeVisible(devMode, grid);
		} else if (gridSwap == true && devMode == false){
			player = false;
			playerSide.setVisible(false);
			enemySide.setVisible(true);
			
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					gridButtons[i][j].setBackground(Color.WHITE);
					gridButtons[i][j].setText("");
					gridButtons[i][j].setIcon(null);
					hitMissVisible(grid[i][j], i, j);
				}
			}
		} else if (gridSwap == true && devMode == true){
			player = false;
			playerSide.setVisible(false);
			enemySide.setVisible(true);
			
			devModeVisible(devMode, grid, false);
		}
		
		enableGrid(grid, gameState, player);
	}
	
	/**
	 * Shows the user whether ship placement will be vertical or horizontal
	 * @param isVert - boolean indicating if placement will be vertical
	 */
	public void flipShip(boolean isVert) {
		if (isVert) {
			vert.setText("|V|");
		} else {
			vert.setText("-H-");
		}
	}
	
	/**
	 * Changes ship type for placement based on button clicked
	 * @param e - ActionEvent telling the function which button was pressed
	 */
	public void changeShipType(ActionEvent e) {
		String arg = e.getActionCommand();
		
		if (arg.equals("Carr-5")) {
			shipType.setText("Carr");
		} else if (arg.equals("Crui-4")) {
			shipType.setText("Crui");
		} else if (arg.equals("Dest-3")) {
			shipType.setText("Dest");
		} else if (arg.equals("MFri-3")) {
			shipType.setText("M.Fri");
		} else if (arg.equals("Subm-2")) {
			shipType.setText("Subm");
		}
	}
	
	/**
	 * Changes GUI Language based on what user wants
	 * @param arg - language chosen
	 * @param gameState - current game phase
	 */
	public void changeLanguage(String arg, int gameState) {
		
		if (gameState == 0) {
			
			if (arg.equals("English")) {
				
				// Translate all components
				fileMenu.setText("File");
				helpMenu.setText("Help");
				langMenu.setText("Language");
				
				newOption.setText("New");
//				saveOption.setText("Save");
//				loadOption.setText("Load");
				exitOption.setText("Exit");
				englishLang.setText("English");
				tagalogLang.setText("Tagalog");
				aboutOption.setText("About");
				hostGameOption.setText("Host a Game");
				joinGameOption.setText("Join a Game");
				singlePlayerOption.setText("Single Player");
				
				swapButton.setText("Swap");
				flipButton.setText("Flip");
				
				battleshipTitle.setText("BATTLESHIP");
				
				playerSide.setText("Player's Side");
				enemySide.setText("Enemy's Side");
				
				phaseLabel.setText(" Place Your Ships");
				gameStatus.setText("<html>Phase 1<br>"
						+ "Click on a square to place ship.<br>"
						+ "<br>"
						+ "Your Ships:<br>"
						+ "Carrier: " + playerShips[0] + "/5<br>"
						+ "Cruiser: " + playerShips[1] + "/4<br>"
						+ "Destroyer: " + playerShips[2] + "/3<br>"
						+ "Missile Frigate: " + playerShips[3] + "/3<br>"
						+ "Submarine: " + playerShips[4] + "/2<br>"
						+ "<br>"
						+ "Enemy Ships:<br>"
						+ "Carrier: " + enemyShips[0] + "/5<br>"
						+ "Cruiser: " + enemyShips[1] + "/4<br>"
						+ "Destroyer: " + enemyShips[2] + "/3<br>"
						+ "Missile Frigate: " + enemyShips[3] + "/3<br>"
						+ "Submarine: " + enemyShips[4] + "/2</html>");
				
			} else if (arg.equals("Tagalog")) {

				// Translate all components
				fileMenu.setText("File");
				helpMenu.setText("Tulong");
				langMenu.setText("Wika");
				
				newOption.setText("Bago");
//				saveOption.setText("I-Save ang Laro");
//				loadOption.setText("I-Load ang Laro");
				exitOption.setText("Umalis");
				englishLang.setText("Ingles");
				tagalogLang.setText("Tagalog");
				aboutOption.setText("Tungkol sa Laro");
				hostGameOption.setText("Maghost ng Laro");
				joinGameOption.setText("Sumali sa Laro");
				singlePlayerOption.setText("Maglaro Mag-isa");
				
				swapButton.setText("Ilipat");
				flipButton.setText("Iikot");
				
				playerSide.setText("Ang Iyong Side");
				enemySide.setText("Side ng Kalaban");
				
				phaseLabel.setText(" Maglagay ng Barko");
				gameStatus.setText("<html>Phase 1<br>"
						+ "I-click ang square para maglagay.<br>"
						+ "<br>"
						+ "Mga Barko Mo:<br>"
						+ "Carrier: " + playerShips[0] + "/5<br>"
						+ "Cruiser: " + playerShips[1] + "/4<br>"
						+ "Destroyer: " + playerShips[2] + "/3<br>"
						+ "Missile Frigate: " + playerShips[3] + "/3<br>"
						+ "Submarine: " + playerShips[4] + "/2<br>"
						+ "<br>"
						+ "Mga Barko ng Kalaban:<br>"
						+ "Carrier: " + enemyShips[0] + "/5<br>"
						+ "Cruiser: " + enemyShips[1] + "/4<br>"
						+ "Destroyer: " + enemyShips[2] + "/3<br>"
						+ "Missile Frigate: " + enemyShips[3] + "/3<br>"
						+ "Submarine: " + enemyShips[4] + "/2</html>");
							
			}
			
		} else {
			
			if (arg.equals("English")) {
				
				// Translate all components
				fileMenu.setText("File");
				helpMenu.setText("Help");
				langMenu.setText("Language");
				
				newOption.setText("New");
//				saveOption.setText("Save");
//				loadOption.setText("Load");
				exitOption.setText("Exit");
				englishLang.setText("English");
				tagalogLang.setText("Tagalog");
				aboutOption.setText("About");
				
				swapButton.setText("Swap");
				flipButton.setText("Flip");
				
				battleshipTitle.setText("BATTLESHIP");
				
				playerSide.setText("Player's Side");
				enemySide.setText("Enemy's Side");
				
				phaseLabel.setText(" Attacking Phase");
				gameStatus.setText("<html>Phase 2<br>"
						+ "Click an enemy square to attack.<br>"
						+ "<br>"
						+ "Your Ships:<br>"
						+ "Carrier: " + playerShips[0] + "/5<br>"
						+ "Cruiser: " + playerShips[1] + "/4<br>"
						+ "Destroyer: " + playerShips[2] + "/3<br>"
						+ "Missile Frigate: " + playerShips[3] + "/3<br>"
						+ "Submarine: " + playerShips[4] + "/2<br>"
						+ "<br>"
						+ "Enemy Ships:<br>"
						+ "Carrier: " + enemyShips[0] + "/5<br>"
						+ "Cruiser: " + enemyShips[1] + "/4<br>"
						+ "Destroyer: " + enemyShips[2] + "/3<br>"
						+ "Missile Frigate: " + enemyShips[3] + "/3<br>"
						+ "Submarine: " + enemyShips[4] + "/2</html>");
				
			} else if (arg.equals("Tagalog")) {

				// Translate all components
				fileMenu.setText("File");
				helpMenu.setText("Tulong");
				langMenu.setText("Wika");
				
				newOption.setText("Bago");
//				saveOption.setText("I-Save ang Laro");
//				loadOption.setText("I-Load ang Laro");
				exitOption.setText("Umalis");
				englishLang.setText("Ingles");
				tagalogLang.setText("Tagalog");
				aboutOption.setText("Tungkol sa Laro");
				
				swapButton.setText("Ilipat");
				flipButton.setText("Iikot");
				
				playerSide.setText("Ang Iyong Side");
				enemySide.setText("Side ng Kalaban");
				
				phaseLabel.setText(" Phase ng Pagatake");
				gameStatus.setText("<html>Phase 2<br>"
						+ "I-click ang square para umatake.<br>"
						+ "<br>"
						+ "Mga Barko Mo:<br>"
						+ "Carrier: " + playerShips[0] + "/5<br>"
						+ "Cruiser: " + playerShips[1] + "/4<br>"
						+ "Destroyer: " + playerShips[2] + "/3<br>"
						+ "Missile Frigate: " + playerShips[3] + "/3<br>"
						+ "Submarine: " + playerShips[4] + "/2<br>"
						+ "<br>"
						+ "Mga Barko ng Kalaban:<br>"
						+ "Carrier: " + enemyShips[0] + "/5<br>"
						+ "Cruiser: " + enemyShips[1] + "/4<br>"
						+ "Destroyer: " + enemyShips[2] + "/3<br>"
						+ "Missile Frigate: " + enemyShips[3] + "/3<br>"
						+ "Submarine: " + enemyShips[4] + "/2</html>");
							
			}
		}
		
	}
	
	/**
	 * Changes the GUI text based on the phase of the game once all ships have been placed
	 */
	public void changePhase() {
		if (swapButton.getText().equals("Swap")) {
			phaseLabel.setText(" Attacking Phase");
			gameStatus.setText("<html>Phase 2<br>"
					+ "Click an enemy square to attack.<br>"
					+ "<br>"
					+ "Your Ships:<br>"
					+ "Carrier: " + playerShips[0] + "/5<br>"
					+ "Cruiser: " + playerShips[1] + "/4<br>"
					+ "Destroyer: " + playerShips[2] + "/3<br>"
					+ "Missile Frigate: " + playerShips[3] + "/3<br>"
					+ "Submarine: " + playerShips[4] + "/2<br>"
					+ "<br>"
					+ "Enemy Ships:<br>"
					+ "Carrier: " + enemyShips[0] + "/5<br>"
					+ "Cruiser: " + enemyShips[1] + "/4<br>"
					+ "Destroyer: " + enemyShips[2] + "/3<br>"
					+ "Missile Frigate: " + enemyShips[3] + "/3<br>"
					+ "Submarine: " + enemyShips[4] + "/2</html>");
		} else if (swapButton.getText().equals("Ilipat")) {
			phaseLabel.setText(" Phase ng Pagatake");
			gameStatus.setText("<html>Phase 2<br>"
					+ "I-click ang square para umatake.<br>"
					+ "<br>"
					+ "Mga Barko Mo:<br>"
					+ "Carrier: " + playerShips[0] + "/5<br>"
					+ "Cruiser: " + playerShips[1] + "/4<br>"
					+ "Destroyer: " + playerShips[2] + "/3<br>"
					+ "Missile Frigate: " + playerShips[3] + "/3<br>"
					+ "Submarine: " + playerShips[4] + "/2<br>"
					+ "<br>"
					+ "Mga Barko ng Kalaban:<br>"
					+ "Carrier: " + enemyShips[0] + "/5<br>"
					+ "Cruiser: " + enemyShips[1] + "/4<br>"
					+ "Destroyer: " + enemyShips[2] + "/3<br>"
					+ "Missile Frigate: " + enemyShips[3] + "/3<br>"
					+ "Submarine: " + enemyShips[4] + "/2</html>");
		}
	}
	
	/**
	 * Displays hit or miss icons/removes them if not needed
	 * @param hm - hit miss value
	 * @param i - x-coord
	 * @param j - y-coord
	 */
	public void hitMissVisible(int hm, int i, int j) {
		
		if (hm == -1) {
			gridButtons[i][j].setIcon(hit);
			gridButtons[i][j].setDisabledIcon(hit);
			gridButtons[i][j].setBackground(Color.RED);
			gridButtons[i][j].setText("");
			gridButtons[i][j].setEnabled(false);
		} else if (hm == -2) {
			gridButtons[i][j].setIcon(miss);
			gridButtons[i][j].setDisabledIcon(miss);
			gridButtons[i][j].setBackground(Color.BLUE);
			gridButtons[i][j].setText("");
			gridButtons[i][j].setEnabled(false);
		} else if (hm == 0) {
			gridButtons[i][j].setBackground(Color.WHITE);
			gridButtons[i][j].setIcon(null);
			gridButtons[i][j].setText("");
		} else {
			gridButtons[i][j].setBackground(Color.WHITE);
		}
		
	}
	
	/**
	 * Keeps track of ship lengths
	 * @param player - boolean indicating if player made the move
	 * @param type - the ship type that has been hit
	 */
	public void reduceShips(boolean player, int type) {
		if (player) {
			enemyShips[type - 1]--;
		} else if (!player) {
			playerShips[type - 1]--;
		}
	}
	
	/**
	 * Changes grid visibility based on developer mode
	 * @param dm - indicates if dev mode is currently on
	 * @param grid - grid values given to the function
	 * @param player - boolean indicating if it is player side or not
	 */
	public void devModeVisible(boolean dm, int[][] grid, boolean player) {
		
		if (player) return;
		
		if (dm == true) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					gridButtons[i][j].setBackground(Color.WHITE);
					if (grid[i][j] > 0) {
						gridButtons[i][j].setIcon(null);
						gridButtons[i][j].setText("" + grid[i][j]);
					} else if (grid[i][j] == 0) {
						gridButtons[i][j].setIcon(null);
						gridButtons[i][j].setText("");
					} else if (grid[i][j] < 0) {
						gridButtons[i][j].setText("");
						hitMissVisible(grid[i][j], i, j);
					}
				}
			}
			
		} else if (dm == false) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					gridButtons[i][j].setBackground(Color.WHITE);
					gridButtons[i][j].setText("");
					hitMissVisible(grid[i][j], i, j);
				}
			}
			
		}
	}
	
	/**
	 * Enables/Disables buttons based on game state
	 * @param grid 
	 * @param gameState
	 * @param player
	 */
	public void enableGrid(int[][] grid, int gameState, boolean player) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (gameState == 1 && player == true) {
					gridButtons[i][j].setEnabled(false);
				} else if (gameState == 0 && player == false) {
					gridButtons[i][j].setEnabled(false);
				} else if (grid[i][j] == 0 && gameState == 0 && player == true) {
					gridButtons[i][j].setEnabled(true);
				} else if (grid[i][j] != 0 && gameState == 0 && player == true) {
					gridButtons[i][j].setEnabled(false);
				} else if (grid[i][j] >= 0 && gameState == 1 && player == false) {
					gridButtons[i][j].setEnabled(true);
				} else if (grid[i][j] < 0 && gameState == 1 && player == false) {
					gridButtons[i][j].setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * Disables type buttons based on which on needs to be disabled
	 * @param type - tells the function which button needs to be disabled
	 */
	public void disableTypeButtons(int type) {
		shipType.setText("N/A");
		if (type == 0) {
			carrier5.setEnabled(false);
			playerShips[type] = 5;
		} else if (type == 1) {
			cruiser4.setEnabled(false);
			playerShips[type] = 4;
		} else if (type == 2) {
			destroyer3.setEnabled(false);
			playerShips[type] = 3;
		} else if (type == 3) {
			frigate3.setEnabled(false);
			playerShips[type] = 3;
		} else if (type == 4) {
			sub2.setEnabled(false);
			playerShips[type] = 2;
		}
		
		boolean allDisabled = true;
		if (carrier5.isEnabled()) {
			allDisabled = false;
		} else if (cruiser4.isEnabled()) {
			allDisabled = false;
		} else if (destroyer3.isEnabled()) {
			allDisabled = false;
		} else if (frigate3.isEnabled()) {
			allDisabled = false;
		} else if (sub2.isEnabled()) {
			allDisabled = false;
		}
		
		if (allDisabled) {
			flipButton.setEnabled(false);
			vert.setText("N/A");
		}
	}
	
	public void resetButtons() {
		textArea.setText("Initializing game boards...\n\n"
				+ "Note when placing ships:\n"
				+ "Vertical placements: top to bottom\n"
				+ "Horizontal placements: left to right\n\n"
				+ "And don't forget to choose your ship type!\n\n");
		textArea.setRows(25);
		
		shipType.setText("N/A");
		vert.setText("|V|");
		flipButton.setEnabled(true);
		carrier5.setEnabled(true);
		cruiser4.setEnabled(true);
		destroyer3.setEnabled(true);
		frigate3.setEnabled(true);
		sub2.setEnabled(true);
		
		playerShips[0] = 0;
		playerShips[1] = 0;
		playerShips[2] = 0;
		playerShips[3] = 0;
		playerShips[4] = 0;
		
		enemyShips[0] = 5;
		enemyShips[1] = 4;
		enemyShips[2] = 3;
		enemyShips[3] = 3;
		enemyShips[4] = 2;
	}
	
	/**
	 * Displays a dialog box telling the user about the game
	 */
	public void displayAbout() {
		
		JDialog about;
		JLabel aboutLabel;
		
		if (swapButton.getText().equals("Swap")) {
			about = new JDialog(frame, "About");
			aboutLabel = new JLabel("<html>BATTLESHIP<br>"
					+ "Made by: Robert Gabriel Macalintal - 041096069<br>"
					+ "Algonquin College - CET-CS 2024</html>");
		} else {
			about = new JDialog(frame, "Tungkol sa Laro");
			aboutLabel = new JLabel("<html>BATTLESHIP<br>"
					+ "Ginawa ni: Robert Gabriel Macalintal - 041096069<br>"
					+ "Algonquin College - CET-CS 2024</html>");
		}
		
		aboutLabel.setAlignmentX(CENTER_ALIGNMENT);
		aboutLabel.setAlignmentY(CENTER_ALIGNMENT);
		aboutLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 5));
		aboutLabel.setBackground(new Color(255, 255, 255));
		
		about.add(aboutLabel);
		about.setSize(new Dimension(350, 100));
		about.setResizable(false);
		about.setVisible(true);
	}
	
	/**
	 * Displays a dialog box telling the user that the ship placement chosen was invalid
	 */
	public void displayInvalidPlacement() {
		
		JDialog invalid;
		JLabel invalidLabel;
		
		if (swapButton.getText().equals("Swap")) {
			invalid = new JDialog(frame, "Invalid Placement");
			invalidLabel = new JLabel("<html>Invalid Ship Placement<br>"
					+ "Please try again.</html>");
		} else {
			invalid = new JDialog(frame, "Bawal Dito");
			invalidLabel = new JLabel("<html>Bawal ilagay ang barko dito<br>"
					+ "Subukan ulit.</html>");
		}
		
		invalidLabel.setAlignmentX(CENTER_ALIGNMENT);
		invalidLabel.setAlignmentY(CENTER_ALIGNMENT);
		invalidLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 5));
		invalidLabel.setBackground(new Color(255, 255, 255));
		
		invalid.add(invalidLabel);
		invalid.setSize(new Dimension(350, 100));
		invalid.setResizable(false);
		invalid.setVisible(true);
	}
	
	/**
	 * Displays a dialog box telling the user that they have won
	 */
	public void displayWin() {
		
		JDialog win;
		JLabel winLabel;
		
		if (swapButton.getText().equals("Swap")) {
			win = new JDialog(frame, "Congratulations!");
			winLabel = new JLabel("<html>YOU HAVE WON!<br>"
					+ "Thank you for playing.</html>");
		} else {
			win = new JDialog(frame, "Congratulations");
			winLabel = new JLabel("<html>NANALO KA!<br>"
					+ "Salamat sa paglalaro.</html>");
		}
		
		winLabel.setAlignmentX(CENTER_ALIGNMENT);
		winLabel.setAlignmentY(CENTER_ALIGNMENT);
		winLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 5));
		winLabel.setBackground(new Color(255, 255, 255));
		
		win.add(winLabel);
		win.setSize(new Dimension(350, 100));
		win.setResizable(false);
		win.setVisible(true);
	}
	
	/**
	 * Displays a dialog box telling the user that they have lost
	 */
	public void displayLoss() {
		
		JDialog loss;
		JLabel lossLabel;
		
		if (swapButton.getText().equals("Swap")) {
			loss = new JDialog(frame, "Thank you for playing");
			lossLabel = new JLabel("<html>You have lost...<br>"
					+ "Thank you for playing.</html>");
		} else {
			loss = new JDialog(frame, "Salamat");
			lossLabel = new JLabel("<html>Ikaw ay natalo...<br>"
					+ "Salamat sa paglalaro.</html>");
		}
		
		lossLabel.setAlignmentX(CENTER_ALIGNMENT);
		lossLabel.setAlignmentY(CENTER_ALIGNMENT);
		lossLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 5));
		lossLabel.setBackground(new Color(255, 255, 255));
		
		loss.add(lossLabel);
		loss.setSize(new Dimension(350, 100));
		loss.setResizable(false);
		loss.setVisible(true);
	}
	
	/**
	 * Appends string to textArea
	 * @param str - string to be appended
	 */
	public void appendToTA(String str) {
		textArea.setRows(textArea.getLineCount() + 3);
		
		textArea.append(str);
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
	}
	
	/**
	 * Gets Text Field Text
	 */
	public String getFieldStr() {
		return textField.getText();
	}
	
	/**
	 * Removes Text Field Text
	 */
	public void removeFieldStr() {
		textField.setText("");
	}
	
	/**
	 * Displays network dialog box to connect to a game
	 */
	public void displayNetworkDialog() {
		networkDialog.setVisible(true);
	}
	
	/**
	 * Gets IP Address from Dialog Box
	 */
	public String getAddress() {
		return networkDialog.getAddress();
	}
	
	/**
	 * Gets Port Number from Dialog Box
	 */
	public String getPort() {
		return networkDialog.getPort();
	}

//////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Code made by Professor /////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates a menu with menu items. - Taken from SwingMenuDemo.java
	 * 
	 * @author: Professor Daniel Cormier
	 * Modified by: Robert Macalintal
	 * 
	 * @param parent       if the parent is a instance of JMenu it adds items to the
	 *                     menu. if the parent is a string it creates the menu and
	 *                     then adds items to the menu.
	 * @param items        list of references to menu items names (strings). If the
	 *                     references null, a separator is added.
	 * @param eventHandler event handler for the menu items.
	 * @returns a reference to JMenu with optional menu items. null if parent is not
	 *          an instance of String or JMenu, or items is null
	 * 
	 */
	private JMenu buildMenu(Object parent, Object[] items) { // removed Object eventHandler from params
		JMenu m = null;
		if (parent instanceof JMenu)
			m = (JMenu) parent;
		else if (parent instanceof String)
			m = new JMenu((String) parent);
		else
			return null;
		if (items == null)
			return null;
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null)
				m.addSeparator();
			else
				m.add(buildMenuItem(items[i]));
			// replaced m.add(buildMenuItem(items[i]), eventHandler);
		}

		return m;
	}

	/**
	 * Creates a menu item. - Taken from SwingMenuDemo.java
	 * 
	 * @author: Professor Daniel Cormier
	 * Modified by: Robert Macalintal
	 * 
	 * @param parent       if the parent is a instance of JMenuItem it adds an event
	 *                     handler. if the parent is a string it creates the menu
	 *                     and then adds an event handler.
	 * @param eventHandler event handler for the menu items. Must be of type
	 *                     ActionListener
	 * @returns a reference to JMenuItem. null if parent is not an instance of
	 *          String or JMenuItem, or the event handler is an instance of
	 *          ActionListener
	 * 
	 */
	private JMenuItem buildMenuItem(Object item) { // removed Object eventHandler from params
		JMenuItem r = null;
		if (item instanceof String)
			r = new JMenuItem((String) item);
		else if (item instanceof JMenuItem)
			r = (JMenuItem) item;
		else
			return null;

//		if (eventHandler instanceof ActionListener)
//			r.addActionListener((ActionListener) eventHandler);
//		else
//			return null;
		return r;
	}

}
