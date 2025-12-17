package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import model.Model;
import view.View;

public class Server implements Runnable {
	
	/**
	 * Declaring Fields for Server Class
	 */
	Socket sock;
	ServerSocket serverSock;
	
	Model GameModel;
	View GameView;
	
	BufferedReader in;
	PrintWriter out;
	
	InputStream inStream;
	OutputStream outStream;

	private static int PORT = 10000;
	private int portNumber = PORT;
	
	private static String ADDRESS = "127.0.0.1";
	private String ipAddress = ADDRESS;
	
	/**
	 * Sets the port number for the server based on user input
	 * @param portNumber - port number to be used by the server socket
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	/**
	 * Sends a message to the other player based on String given
	 * @param msg - the message to be sent
	 */
	public void sendMsg(String msg)
	{
		this.out.println(msg);
	}
	
	/**
	 * Server Constructor
	 * @param host - Checks if user is host or not
	 * @param portNumber - Port number to be used
	 * @param ipAddress - host IP Address
	 * @param GameModel - Model in MVC structure
	 * @param GameView - View in MVC structure
	 * @throws IOException
	 * @throws SocketException
	 */
	public Server(boolean host, int portNumber, String ipAddress, Model GameModel, View GameView) throws IOException, SocketException {
		
		this.GameModel = GameModel;
		this.GameView = GameView;
		
		if (host) {
				
			this.ipAddress = InetAddress.getLocalHost().getHostAddress();
			serverSock = new ServerSocket(portNumber);
			sock = serverSock.accept();
			
			outStream = sock.getOutputStream();
			out = new PrintWriter(outStream, true /* autoFlush */);
			
			GameView.appendToTA("\nOpponent Connected\n");
			
		} else {
			
			if (ipAddress.equals("")) {
				this.ipAddress = ADDRESS;
			} else {
				this.ipAddress = ipAddress;
			}
			this.portNumber = portNumber;
				
	        this.sock = new Socket(this.ipAddress, this.portNumber);
	        
	        this.outStream = sock.getOutputStream();
	        this.out = new PrintWriter(outStream, true /* autoFlush */);
	        
	        GameView.appendToTA("Connected to Port " + portNumber + "\n");
		        
		}
		
		Thread handler = new Thread(this);
		handler.start();
		
	}
	
	/**
	 * Run the Server Thread
	 */
	@Override
	public void run() {
		
		String inputLine;
		String[] data;
        String arg1;
        String arg2;
        String arg3;
        String arg4;
        String msgType;
        
        int tempInt = 0;
        boolean tempBool = true;
        
        boolean done = false;
		
		try {
			
			this.inStream = sock.getInputStream();
			
			this.in = new BufferedReader(new InputStreamReader(inStream));         
	        
            while ((inputLine = in.readLine()) != null & !done) {
            	data = inputLine.split(":", 5);
            	msgType = data[0];
            	
            	switch (msgType) {
            	case "Place":
            		arg1 = data[1];
            		arg2 = data[2];
            		arg3 = data[3];
            		arg4 = data[4];
            		
            		tempBool = GameModel.isVert();
            		
            		if (arg4.equals("v")) {
            			GameModel.setVert(true);
            		} else {
            			GameModel.setVert(false);
            		}
            		
            		GameModel.setType(Integer.parseInt(arg3));
            		GameModel.placeShips(Integer.parseInt(arg1), Integer.parseInt(arg2), GameModel.getEnemyGrid());
            		
            		GameModel.setType(-1);
            		GameModel.setVert(tempBool);
            		tempBool = true;
            		
            		// Display changes
					GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
					if (!GameModel.isGridSwapOn()) {
						GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getPlayerGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
					} else {
						GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getEnemyGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
					}
            		
            		break;
            	case "Attack":
            		arg1 = data[1];
            		arg2 = data[2];
            		
            		tempInt = GameModel.getPlayerGrid()[Integer.parseInt(arg1)][Integer.parseInt(arg2)];
            		
            		GameModel.hitOrMiss(Integer.parseInt(arg1), Integer.parseInt(arg2));
            		
            		if (tempInt > 0) {
						GameView.reduceShips(false, tempInt);
					}
					GameModel.setEnemyTurn(GameModel.getEnemyTurn() + 1);
					GameModel.setMyTurn(!GameModel.isMyTurn());
					
					if (tempInt > 0) {
						GameView.appendToTA("\nEnemy attacked position " + Integer.parseInt(arg1) + " " + Integer.parseInt(arg2) + ": Hit\n");
					} else if (tempInt == 0) {
						GameView.appendToTA("\nEnemy attacked position " + Integer.parseInt(arg1) + " " + Integer.parseInt(arg2) + ": Miss\n");
					}
					
					if (GameModel.isGridEmpty(GameModel.getPlayerGrid())) {
						GameView.displayLoss();
						done = true;
					}
					
					// Display changes
					GameView.changeLanguage(GameModel.getCurrentLanguage(), GameModel.getGameState());
					if (!GameModel.isGridSwapOn()) {
						GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getPlayerGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
					} else {
						GameView.swapGrid(GameModel.isGridSwapOn(), GameModel.getEnemyGrid(), GameModel.isDevModeOn(), GameModel.getGameState());
					}
            		
            		break;
            	case "Ready":
            		
            		GameModel.setEnemyReady(true);
            		GameView.appendToTA("\nThe Opponent is ready\n");
            		
            		for (int k = 0; k < 5; k++) {
						if (GameModel.getPlayerShips()[k] == 0) {
							tempBool = false;
						}
					}
            		
            		if (tempBool) {
						GameModel.setGameState(1);
						GameView.changePhase();
						GameView.appendToTA("\nYou may now Attack!\n\nThe host will start\n");
					}
            		
            		break;
            	case "Opponent":
            		GameView.appendToTA(inputLine + "\n");
            		break;
            	case "DisC":
            		done = true;
            		sock.close();
            		GameView.appendToTA("\nOpponent Disconnected\n");
					GameModel.setNetworkOn(false);
					GameModel.startGame();
            		break;
            	default:
            		break;
            	}
            	
            }
            
			sock.close();
			
		} catch (IOException ioe) {
			
			try {
				sock.close();
				GameView.appendToTA("Disconnected to socket\n");
				GameModel.setNetworkOn(false);
				GameModel.startGame();
			} catch (IOException e) { }
			
		}
		
	}
	
}