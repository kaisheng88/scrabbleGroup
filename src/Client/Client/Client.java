package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Client extends Thread {
	
	private String address;
	private int port;
	private Socket socket;
	private JSONParser parser;
	private Game game;
	private BufferedReader inputStream;
	MessageSender sender;

	public Client(BufferedReader inputStream, MessageSender sender) {
		
		System.out.println("Client instant..");
		this.inputStream = inputStream;
		this.sender = sender;
		parser = new JSONParser();
		game = Game.getInstance();
	}
		
	private void write(JSONObject json) {
		System.out.println("client test write");
		System.out.println(json.toString());
		sender.sendMessage(json);
	}
	
	
	public void initializeGame() {
		System.out.println("Client initialize game");
		JSONObject msg = new JSONObject();
		msg.put("command_name", "initialize_game");
		write(msg);
	}
	
	public void parseCommand(String input) {
		
		System.out.println("Client parse command: " +input);
		try {
			JSONObject json = (JSONObject) parser.parse(input);
			if (json.containsKey("invite_game")) {
				
				// game hub invitation
				JSONObject msg = game.receiveInvitation();
				write(msg);
				
			} else if (json.containsKey("game_start")) {
				
				// start game
				game.startGame();
				
			} else if (json.containsKey("your_turn")) {
				
				// play game
				game.play();
				
			} else if (json.containsKey("game_over")) {
				
				// game over
				game.gameOver();
				
			} else if (json.containsKey("scores")) {
				
				// return scores
				//int[] scores = (int[]) json.get("scores");
				//game.updateScore(scores);
			} else if (json.containsKey("raiseVoteWord")) {
				String word = (String) json.get("raiseVoteWord");
				game.voteForWord(word);
				
			} else if (json.containsKey("update_status")) {
				
				// update grid
				int row = (int) json.get("row");
				int column = (int) json.get("column");
				char c = (char) json.get("char");
				game.updateGrid(row, column, c);
				
				
			}else if (json.containsKey("updateBoard")) {			
				System.out.println("client received update board");
				String row = (String)json.get("row");
				String column = (String)json.get("column");
				int rowNum =  Integer.valueOf(row);
				int columnNum = Integer.valueOf(column);
				String s = (String) json.get("char");
				char c =  s.charAt(0);
				game.updateGrid(rowNum, columnNum, c);				
			}
		} catch (ParseException e) {
			System.out.println("Parsing failed");
		}
	}
	
	@Override
	public void run(){

		try {
			String clientMsg;
			while ((clientMsg = inputStream.readLine()) != null) {
				System.out.println("Received: "+clientMsg);
				parseCommand(clientMsg);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
