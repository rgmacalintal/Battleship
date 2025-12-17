package controller;

import view.View;
import model.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/**
 * Controller Class that instantiates a View and Model
 * @author Robert Gabriel Macalintal
 *
 */
public class Controller {
	
	private View GameView;
	private Model GameModel;
	private Server GameServer;
	
	/**
	 * Controller Constructor
	 */
	public Controller() {
		GameView = new View();
		GameModel = new Model();
	}
	
	/**
	 * Starts the game through the Model, adds listeners to buttons,
	 * and sets the View visible and not resizable.
	 */
	public void start() {
		
		GameModel.startGame();
		GameView.addContListener(new ControllerListener());
		GameView.setVisible(true);
		
	}
	
	/**
	 * Starts the game through the Model, adds listeners to buttons,
	 * and sets the View visible and not resizable.
	 */
	public void startNetworkGame() {
		
		GameModel.startNetworkGame();
		GameView.setResizable(false);
		GameView.setVisible(true);
		
	}
	
	/**
	 * A class within the Controller that implements ActionListener.
	 * This class becomes the listener for all interactions in View.
	 * @author Robert Gabriel Macalintal
	 *
	 */
	public class ControllerListener implements ActionListener, Runnable {
		
		@Override
		public void run() { }

		@Override
		public void actionPerformed(ActionEvent e) {
			
			this.run();

			Random rand1 = new Random();
			Random rand2 = new Random();
			
			int randomInt1 = rand1.nextInt(10);
			int randomInt2 = rand2.nextInt(10);
			
			boolean tempBool = true;
			int tempInt = 0;
			
			String arg = e.getActionCommand();
			
			// File Menu
			if (arg.equals("Exit")) {
				if (GameModel.isNetworkOn()) {
					GameServer.sendMsg("DisC");
				}
				System.exit(0);
			} else if (arg.equals("New")) {
				if(GameModel.isNetworkOn()) {
					GameServer.sendMsg("DisC");
					GameModel.setNetworkOn(false);
				}
				
				GameModel.startGame();
				GameView.resetButtons();
				GameView.swapGrid(false, GameModel.getPlayerGrid(), false, GameModel.getGameState());
				GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
				
			// Network Menu
			} else if (arg.equals("Join a Game")) {
				
				GameView.displayNetworkDialog();
				
				try {
					tempInt = Integer.parseInt(GameView.getPort());
				} catch (NumberFormatException nfe) {
					tempInt = 0;
				}
				
				if (tempInt < 10000 || tempInt > 65535) {
					GameView.appendToTA("Invalid Port Number " + GameView.getPort() + "\n");
				} else {
					
					GameView.appendToTA("Connecting with server on " + GameView.getAddress() + " at port " + GameView.getPort() + "\n");
					
					try {
						GameServer = new Server(false, tempInt, GameView.getAddress(), GameModel, GameView);
					} catch (IOException ioe) {
						GameView.appendToTA("Failed to connect to IP: " + GameView.getAddress() + " Port: " + GameView.getPort() + "\n");
					}
					
					GameModel.setNetworkOn(true);
					GameServer.setPortNumber(tempInt);
					
					GameModel.setHost(false);
					startNetworkGame();
					
					GameView.resetButtons();
					GameView.swapGrid(false, GameModel.getPlayerGrid(), false, GameModel.getGameState());
					GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
					
				}
				
			} else if (arg.equals("Host a Game")) {
				
				GameView.appendToTA("Starting Server Thread\n");
				GameView.appendToTA("Please enter network information...\n");
				
				GameView.displayNetworkDialog();
				
				try {
					tempInt = Integer.parseInt(GameView.getPort());
				} catch (NumberFormatException nfe) {
					tempInt = 0;
				}
				
				if (tempInt < 10000 || tempInt > 65535) {
					GameView.appendToTA("Invalid Port Number " + GameView.getPort());
				} else {
					
					try {
						GameServer = new Server(true, tempInt, "", GameModel, GameView);
					} catch (IOException ioe) {
						GameView.appendToTA("Failed to establish a server\n");
					}
					
					GameModel.setNetworkOn(true);
					GameServer.setPortNumber(tempInt);
					
					GameModel.setHost(true);
					startNetworkGame();

					GameView.resetButtons();
					GameView.swapGrid(false, GameModel.getPlayerGrid(), false, GameModel.getGameState());
					GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
					
				}
				
			} else if (arg.equals("Single Player")) {
				GameModel.setNetworkOn(false);
				GameModel.startGame();
				GameView.resetButtons();
				GameView.swapGrid(false, GameModel.getPlayerGrid(), false, GameModel.getGameState());
				GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
				GameView.addContListener(new ControllerListener());
				
				if (GameModel.isNetworkOn()) {
					GameServer.sendMsg("DisC");
				}
				
			// Text Field
			} else if (arg.equals("Enter")){
				if (GameModel.isNetworkOn()) {
					GameServer.sendMsg("Opponent: " + GameView.getFieldStr());
				}
				GameView.appendToTA("You: " + GameView.getFieldStr() + "\n");
				GameView.removeFieldStr();
			}
			
			// Swap and Flip Buttons
			else if (arg.equals("Swap")) {
				GameModel.setGridSwap(!GameModel.isGridSwapOn());
				
				if (!GameModel.isGridSwapOn()) {
					GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getPlayerGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
				} else {
					GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getEnemyGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
				}
				
			} else if (arg.equals("Flip")) {
				GameModel.setVert(!GameModel.isVert());
				GameView.flipShip(GameModel.isVert());
			}
			
			// About, Help, and Language Menus
			else if (arg.equals("About")) {
				GameView.displayAbout();
			} else if (arg.equals("Dev Mode")) {
				
				if (GameModel.isNetworkOn()) {
					GameServer.sendMsg("Opponent: IS TRYING TO CHEAT LOL");
				} else {
					GameModel.setDevMode(!GameModel.isDevModeOn());
					if (GameModel.isGridSwapOn()) {
						GameView.devModeVisible(GameModel.isDevModeOn(), GameModel.getEnemyGrid(), false);
					} else {
						GameView.devModeVisible(GameModel.isDevModeOn(), GameModel.getPlayerGrid(), true);
					}
				}
				
			} else if (arg.equals("English") || arg.equals("Tagalog")) {
				GameView.changeLanguage(arg, GameModel.getGameState());
				GameModel.setCurrentLanguage(arg);
			}
			
			// Ship Type Buttons
			else if (arg.equals("Carr-5")) {
				GameModel.setType(0);
				GameView.changeShipType(e);
			} else if (arg.equals("Crui-4")) {
				GameModel.setType(1);
				GameView.changeShipType(e);
			} else if (arg.equals("Dest-3")) {
				GameModel.setType(2);
				GameView.changeShipType(e);
			} else if (arg.equals("MFri-3")) {
				GameModel.setType(3);
				GameView.changeShipType(e);
			} else if (arg.equals("Subm-2")) {
				GameModel.setType(4);
				GameView.changeShipType(e);
			}
			
			// Grid Check
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (arg.equals(i + "" + j) && GameModel.getGameState() == 0) {
						
						if (!GameModel.placeShips(i, j, GameModel.getPlayerGrid())) {
							GameView.displayInvalidPlacement();
						} else {
							
							tempInt = GameModel.getType();
							
							GameModel.setPlayerShips(tempInt);
							GameView.disableTypeButtons(tempInt);
							GameModel.setType(-1);
							
							for (int k = 0; k < 5; k++) {
								if (GameModel.getPlayerShips()[k] == 0) {
									tempBool = false;
								}
							}
							
							if (GameModel.isNetworkOn()) {
								
								if (tempBool) {
									GameServer.sendMsg("Ready");
								}
								
								if (GameModel.isVert()) {
									GameServer.sendMsg("Place:" + i + ":" + j + ":" + tempInt + ":v");
								} else {
									GameServer.sendMsg("Place:" + i + ":" + j + ":" + tempInt + ":h");
								}
								
								if (tempBool && GameModel.isEnemyReady()) {
									GameModel.setGameState(1);
									GameView.changePhase();
									GameView.appendToTA("\nYou may now Attack!\n\nThe host will start\n");
								}
								
							} else {
								
								if (tempBool) {
									GameModel.setGameState(1);
									GameView.changePhase();
								}
								
							}
							
							GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
							
							if (!GameModel.isGridSwapOn()) {
								GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getPlayerGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
							} else {
								GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getEnemyGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
							}
							
						}
						
					} else if (arg.equals(i + "" + j) && GameModel.getGameState() == 1 && GameModel.isEnemyReady()) {
						
						if (GameModel.isMyTurn()) {
							
							// Player Turn
							tempInt = GameModel.getEnemyGrid()[i][j];
							if (tempInt > 0) {
								GameView.reduceShips(true, tempInt);
							}
							GameView.hitMissVisible(GameModel.hitOrMiss(i, j), i, j);
							GameModel.setPlayerTurn(GameModel.getPlayerTurn() + 1);
							GameModel.setMyTurn(!GameModel.isMyTurn());
							
							if (tempInt > 0) {
								GameView.appendToTA("\nYou attacked position " + i + " " + j + ": Hit\n");
								tempInt = -1;
							} else if (tempInt == 0) {
								GameView.appendToTA("\nYou attacked position " + i + " " + j + ": Miss\n");
								tempInt = -2;
							}
							
							// Enemy Turn
							if (!GameModel.isNetworkOn()) {
								tempBool = true;
								do {
									randomInt1 = rand1.nextInt(10);
									randomInt2 = rand2.nextInt(10);
									tempInt = GameModel.getPlayerGrid()[randomInt1][randomInt2];
									if (!(GameModel.hitOrMiss(randomInt1, randomInt2) == 0)) {
										tempBool = false;
									}
								} while (tempBool);
								
								if (tempInt > 0) {
									GameView.reduceShips(false, tempInt);
								}
								GameModel.setEnemyTurn(GameModel.getEnemyTurn() + 1);
								GameModel.setMyTurn(!GameModel.isMyTurn());
								
								if (tempInt > 0) {
									GameView.appendToTA("\nEnemy attacked position " + randomInt1 + " " + randomInt2 + ": Hit\n");
								} else if (tempInt == 0) {
									GameView.appendToTA("\nEnemy attacked position " + randomInt1 + " " + randomInt2 + ": Miss\n");
								}
								
							// Send Attack Msg if Network is active
							} else {
								GameServer.sendMsg("Attack:" + i + ":" + j + ":" + tempInt + ":");
							}
							
							// Display changes
							GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
							if (!GameModel.isGridSwapOn()) {
								GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getPlayerGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
							} else {
								GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getEnemyGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
							}
							
						} else {
							GameView.appendToTA("Not your turn yet\n");
						}
						
						if (GameModel.isGridEmpty(GameModel.getPlayerGrid())) {
							GameView.displayLoss();
						} else if (GameModel.isGridEmpty(GameModel.getEnemyGrid())) {
							GameView.displayWin();
						}
						
					} // end grid if statement
					
				} // end grid check inner for loop
				
			} // end grid check outer for loop
			
		} // end actionperformed
		
	} // end controllerlistener class
	
} // end controller class
