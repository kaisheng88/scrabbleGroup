package Client;

import java.awt.Desktop.Action;
import java.util.Set;

import org.json.simple.JSONObject;

import com.sun.media.jfxmedia.control.VideoDataBuffer;


public class Game {
	
	
	private int selectRow, selectColumn;
	private int startRow, startColumn;
	private int endRow, endColumn;
	
	private char table[][];
	private char c;
	private JSONObject gameMessage;
	private MessageSender sender;
	private int score = 0;
	private static Game instance;
	private GameGUI gameGUI;
	private int playerScore = 0;
	
	public void SetGUI(GameGUI GUI) {
		this.gameGUI = GUI;
	}
		
	public void setSender(MessageSender sender) {
		this.sender = sender;
	}
	
	private Game() {
		
		table = new char[20][20];
	}
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	public void selectTile(int row, int column) {
		
		this.selectRow = row;
		this.selectColumn = column;
		gameGUI.updateStatus("column="+column+"	row="+row);
		System.out.printf("changed:%d,%d\n",row,column);
	}
	
	public void selectStart(){
		this.startColumn = this.selectColumn;
		this.startRow = this.selectRow;
	}
	
	public void selectEnd(){
		this.endColumn = this.selectColumn;
		this.endRow = this.selectRow;
	}
	

	
	
	public void raiseVoteYes(){
		gameGUI.changePanel(ActionJPanel.RAISE_VOTE);
	}
	
	public void raiseVoteNo(){
		//only for single player
		gameGUI.changePanel(ActionJPanel.ADD_CHAR);
		gameMessage = new JSONObject();
		gameMessage.put("command_name", "raise_No");
		sender.sendMessage(gameMessage);
	}
	
	public boolean putCharacter(char c) {
		
		if (table[selectRow][selectColumn] == 0) {
			table[selectRow][selectColumn] = c;
			this.c = c;
			gameMessage = new JSONObject();
			gameMessage.put("command_name", "addChar");
			gameMessage.put("row", String.valueOf(selectRow));
			gameMessage.put("column", String.valueOf(selectColumn));
			gameMessage.put("char", Character.toString(c));
			//don't have to update first, since server will do a broadcast to 
			//all players to updateGrid
			//gameGUI.updateGrid(selectRow, selectColumn, c);
			sender.sendMessage(gameMessage);
			gameGUI.changePanel(ActionJPanel.RAISE_VOTE_OR_NOT);			
			System.out.println("client added char");
			score++;
						
			return true; // success
		} else {
			return false; // fail
		}
	}
	
	public void backToGamePlaying(){
		gameGUI.changePanel(ActionJPanel.ADD_CHAR);	
	}
	
	public void vote(String v){
		JSONObject msg = new JSONObject();
		// call gui
		msg.put("command_name", "vote");
		msg.put("selection", v);
		sender.sendMessage(msg);
		gameGUI.changePanel(ActionJPanel.NO_ACTION);
	}
	
	public JSONObject receiveInvitation() {
		
		JSONObject msg = new JSONObject();
		// call gui
		msg.put("command_name", "accept_invitation");
		return msg;
	}
	
	public void sendInvitation() {
		
		JSONObject msg = new JSONObject();
		msg.put("command_name", "initialize_game");
		sender.sendMessage(msg);
	}
	
	public void startGame() {
		
		// call gui
		// open gui
	}
	
	public void updateGrid(int row, int column, char c) {
		
		// call gui
		gameGUI.updateGrid(row, column, c);
	}
	
	public JSONObject vote(int startRow, int startColumn, int endRow, int endColumn) {
		
		int vote;
		JSONObject msg = new JSONObject();
		msg.put("command_name", "vote");
		// call gui
		// get vote result
		return msg;
	}
	
	public void gameOver() {
		
		// call gui
		//table = new char[20][20];
		System.exit(0);
	}
	
	public void updateScore(int[] scores) {
		
		// call gui
	}
	
	public void play() {
		
		// call gui
		// row, column, char
		// whether raise a vote
		
		gameGUI.changePanel(ActionJPanel.ADD_CHAR);
	}
	
	public void exit() {
		this.gameGUI.closeWindow();
		JSONObject msg = new JSONObject();
		msg.put("command_name", "exit");
		sender.sendMessage(msg);
	}
	
	public void passPlayer(){
		JSONObject msg = new JSONObject();
		msg.put("command_name", "pass");
		sender.sendMessage(msg);
	}
	
	public void voteForWord(String word){
		gameGUI.updateStatus("Vote for "+word);
		gameGUI.changePanel(ActionJPanel.VOTE);
	}
	
	public void raiseVote(){
		String word = checkRaise();
		if (word == null){
			gameGUI.updateStatus("Error in selection");
		} else {
			JSONObject msg = new JSONObject();

			msg.put("command_name", "raise_Vote");
			msg.put("word", word);
			sender.sendMessage(msg);
			gameGUI.changePanel(ActionJPanel.NO_ACTION);
		}
	}

	public String checkRaise() {
		
		System.out.println("Raise vote");
		String msg = null;
		int score = 0;
		
		if (startRow == endRow) {
			
			if (selectRow != startRow || selectColumn < startColumn || selectColumn > endColumn) {
				msg = null;
			} else {
				
				String word = "";
				for (int i = startColumn; i <= endColumn; ++i) {
					
					if (table[startRow][i] == 0) {
						return null;
					} else {
						word += Character.toString(table[startRow][i]);
					}
				}
				msg = word;
				score = word.length();
			}
			
		} else if (startColumn == endColumn) {
			
			if (selectColumn != startColumn || selectRow < startRow || selectRow > endRow) {
				msg = null;
			} else {
				
				String word = "";
				for (int i = startRow; i <= endRow; ++i) {
					
					if (table[i][startColumn] == 0) {
						return null;
					} else {
						word += Character.toString(table[i][startColumn]);
					}
				}
				msg = word;
				score = word.length();
			}
			
		} else {
			msg = null;
		}
		
		return msg;
	}
}
