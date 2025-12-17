package model;

//import java.io.File;
import java.util.Random;

public class Model {
	
	/*
	 * Declare Fields
	 */
	private int playerTurn;
	private int enemyTurn;
	
	private int type = -1;
	
	private boolean gridSwap = false;
	private boolean devMode = false;

	private boolean myTurn;
	private boolean vert = true;
	
	private boolean host = true;
	private boolean network = false;

	private int gameState;
	private boolean enemyReady;
	private String currentLanguage = "English";
	
	private int[][] playerGrid = new int[10][10];
	private int[][] enemyGrid = new int[10][10];
	
	private int[] playerShips = new int[5];
	private int[] enemyShips = new int[5];
	
//	private File savedGame = new File("save.txt");
	
	/*
	 * Generated Getters and Setters
	 */
	public int getPlayerTurn() {
		return playerTurn;
	}
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}
	public int getEnemyTurn() {
		return enemyTurn;
	}
	public void setEnemyTurn(int enemyTurn) {
		this.enemyTurn = enemyTurn;
	}
	public int getGameState() {
		return gameState;
	}
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	public int[][] getPlayerGrid() {
		return playerGrid;
	}
	public void setPlayerGrid(int[][] playerGrid) {
		this.playerGrid = playerGrid;
	}
	public int[][] getEnemyGrid() {
		return enemyGrid;
	}
	public void setEnemyGrid(int[][] enemyGrid) {
		this.enemyGrid = enemyGrid;
	}
	public boolean isMyTurn() {
		return myTurn;
	}
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	public boolean isVert() {
		return vert;
	}
	public void setVert(boolean vert) {
		this.vert = vert;
	}
	public boolean isDevModeOn() {
		return devMode;
	}
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	public int[] getPlayerShips() {
		return playerShips;
	}
	public void setPlayerShips(int type) {
		this.playerShips[type] = type + 1;
	}
	public int[] getEnemyShips() {
		return enemyShips;
	}
	public void setEnemyShips(int type) {
		this.enemyShips[type] = type + 1;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isGridSwapOn() {
		return gridSwap;
	}
	public void setGridSwap(boolean gridSwap) {
		this.gridSwap = gridSwap;
	}
	public String getCurrentLanguage() {
		return currentLanguage;
	}
	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}
	public boolean isNetworkOn() {
		return network;
	}
	public void setNetworkOn(boolean network) {
		this.network = network;
	}
	public boolean isHost() {
		return host;
	}
	public void setHost(boolean host) {
		this.host = host;
	}
	public boolean isEnemyReady() {
		return enemyReady;
	}
	public void setEnemyReady(boolean enemyReady) {
		this.enemyReady = enemyReady;
	}
	
//	/*
//	 * 
//	 */
//	public void saveGame() {
//		
//	}
//	
//	/*
//	 * 
//	 */
//	public void loadGame() {
//		
//	}
	
	/**
	 * Resets game state to the beginning of the game
	 */
	public void startGame() {
		initializeGrids();
		
		gridSwap = false;
		devMode = false;
		enemyReady = true;
		network = false;
		
//		Random rand = new Random();
//		int turnRand = rand.nextInt(100);
//		
//		if (turnRand % 2 == 0) myTurn = true;
//		else myTurn = false;
		myTurn = true;
		
		gameState = 0;
		
		playerShips[0] = 0;
		playerShips[1] = 0;
		playerShips[2] = 0;
		playerShips[3] = 0;
		playerShips[4] = 0;
		
		// Initialize Enemy Ships
		Random rand1 = new Random();
		Random rand2 = new Random();
		
		int randomI = 0;
		int randomJ = 0;
		
		enemyShips[0] = 5;
		enemyShips[1] = 4;
		enemyShips[2] = 3;
		enemyShips[3] = 3;
		enemyShips[4] = 2;
		
		for (int i = 0; i < 5; i++) {
			do {
				type = i;
				vert = !vert;
				randomI = rand1.nextInt(10);
				randomJ = rand2.nextInt(10);
			} while(!placeShips(randomI, randomJ, enemyGrid));
		}
		
		// Reinitiallize type and vert for player
		type = -1;
		vert = true;
	}
	
	/**
	 * Starts a game with another player
	 */
	public void startNetworkGame() {
		initializeGrids();
		
		gridSwap = false;
		devMode = false;
		enemyReady = false;
		network = true;
		
//		Random rand = new Random();
//		int turnRand = rand.nextInt(100);
//		
//		if (turnRand % 2 == 0) myTurn = true;
//		else myTurn = false;
		if (host) {
			myTurn = true;
		} else {
			myTurn = false;
		}
		
		gameState = 0;
		
		playerShips[0] = 0;
		playerShips[1] = 0;
		playerShips[2] = 0;
		playerShips[3] = 0;
		playerShips[4] = 0;
		
		enemyShips[0] = 0;
		enemyShips[1] = 0;
		enemyShips[2] = 0;
		enemyShips[3] = 0;
		enemyShips[4] = 0;
		
		type = -1;
		vert = true;
	}
	
	/**
	 * Places a ship depending on the position and grid it is given
	 * @param ii - x-coord initial position
	 * @param jj - y-coord initial position
	 * @param grid - grid of the user placing ships
	 * @return boolean that tells the user if ship placement is valid
	 */
	public boolean placeShips(int ii, int jj, int[][] grid) {
		boolean valid = true;
		int length = 5;
		
		if (type == 0) {
			length = 5;
		} else if (type == 1) {
			length = 4;
		} else if (type == 2) {
			length = 3;
		} else if (type == 3) {
			length = 3;
		} else if (type == 4) {
			length = 2;
		} else {
			return false;
		}
		
		if (vert) {
			
			if (ii + length > 10) return false;
			
			for (int i0 = ii; i0 < ii + length; i0++) {
				if (grid[i0][jj] > 0) {
					valid = false;
					return valid;
				} else if (i0 == (ii + length - 1) && valid == true) {
					for (int i1 = ii; i1 < ii + length; i1++) {
						grid[i1][jj] = type + 1;
					}
				}
			}
			
		} else if (!vert) {
			
			if (jj + length > 10) return false;
			
			for (int j0 = jj; j0 < jj + length; j0++) {
				if (grid[ii][j0] > 0) {
					valid = false;
					return valid;
				} else if (j0 == (jj + length - 1) && valid == true) {
					for (int j1 = jj; j1 < jj + length; j1++) {
						grid[ii][j1] = type + 1;
					}
				}
			}
			
		}
		
		return valid;
	}
	
	/**
	 * Checks if an attack is a hit or a miss
	 * @param i - x-coord position
	 * @param j - y-coord position
	 * @return an integer indicating if the attack was a hit, miss, or invalid (0)
	 */
	public int hitOrMiss(int i, int j) {
		int gridValue = 0;
		if (enemyGrid[i][j] == 0 && myTurn) {
			
			enemyGrid[i][j] = -2;
			gridValue = -2;
			
		} else if (enemyGrid[i][j] > 0 && myTurn) {
			
			if (enemyGrid[i][j] == 1) {
				enemyShips[0]--;
			} else if (enemyGrid[i][j] == 2) {
				enemyShips[1]--;
			} else if (enemyGrid[i][j] == 3) {
				enemyShips[2]--;
			} else if (enemyGrid[i][j] == 4) {
				enemyShips[3]--;
			} else if (enemyGrid[i][j] == 5) {
				enemyShips[4]--;
			}
			
			enemyGrid[i][j] = -1;
			gridValue = -1;
			
		} else if (playerGrid[i][j] == 0 && !myTurn) {
			
			playerGrid[i][j] = -2;
			gridValue = -2;
			
		} else if (playerGrid[i][j] > 0 && !myTurn) {
			
			if (playerGrid[i][j] == 1) {
				playerShips[0]--;
			} else if (playerGrid[i][j] == 2) {
				playerShips[1]--;
			} else if (playerGrid[i][j] == 3) {
				playerShips[2]--;
			} else if (playerGrid[i][j] == 4) {
				playerShips[3]--;
			} else if (playerGrid[i][j] == 5) {
				playerShips[4]--;
			}
			
			playerGrid[i][j] = -1;
			gridValue = -1;
			
		}
		
		return gridValue;
	}
	
	/**
	 * Checks if a given grid is empty
	 * @param grid
	 * @return a boolean that states whether a grid is empty or not
	 */
	public boolean isGridEmpty(int[][] grid) {
		boolean empty = true;
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (grid[i][j] > 0) {
					return false;
				}
			}
		}
		
		return empty;
	}
	
	/**
	 * Initializes game grids
	 */
	public void initializeGrids() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerGrid[i][j] = 0;
				enemyGrid[i][j] = 0;
			}
		}
	}

}
