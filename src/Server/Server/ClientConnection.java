package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientConnection extends Thread {

	private BufferedReader inputStream;
	private BufferedWriter outputStream;
	private GameHub hub;
	Player player;

	public ClientConnection(Socket clientSocket, int id) {
		BufferedReader in;
		
		try {
			inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
			outputStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
			
			//inputStream = new DataInputStream(clientSocket.getInputStream());
			//outputStream = new DataOutputStream(clientSocket.getOutputStream());
			hub = GameHub.getInstance();
			//setting as anonymous
			//outputStream.write("aaaaaaaaaaaaaaaaaaa");
			this.player = new Player("", id);
			//this.id = id;
			System.out.println("Thread " + id +" online");
			
		} catch (Exception e) {
			
			System.out.println("Socket initialization fail");
		}
	}

	@Override
	public void run() {

		try {
			System.out.println("server run");
			JSONParser parser = new JSONParser();
			
			while (true) {
				
//				if (inputStream.available() > 0) {
//					String s = inputStream.readUTF();
//							System.out.println(s);
//					JSONObject command = (JSONObject) parser.parse(s);
//					parseCommand(command);
//				}
				String clientMsg = inputStream.readLine();
				System.out.println(clientMsg);
				if (clientMsg == null) break;
				JSONObject command = (JSONObject) parser.parse(clientMsg);
				//to be continued
				parseCommand(command);
				//outputStream.flush();
				System.out.println("Response sent");
			
			}

			//clientSocket.close();

		} catch (IOException | ParseException e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
			System.out.println("Thread run into failure");
			System.out.println("Lose connection to client");
			
		} finally {
			
			System.out.println("Thread number " + this.player.getPlayerName() + " closed");
			
		}
	}
	
	public synchronized void write(JSONObject json) {
		
		try {
			System.out.println("send "+json.toJSONString());
			outputStream.write(json.toJSONString()+"\n");
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void broadcast(JSONObject msg){
		System.out.println("send "+msg.toJSONString());
		List<ClientConnection> pool = hub.getPool();
		for (ClientConnection client: pool) {
			client.write(msg);
		}
	}
	
	private void parseCommand(JSONObject command) {
		
		System.out.println("server parse command " +command.get("command_name"));
		
		if (command.get("command_name").equals("accept_invitation")) {
			
			// if someone accepts invitation,
			// check whether everyone agrees to start a game
			int start = hub.acceptInvitation();
			if (start == 1) {
				JSONObject msg = new JSONObject();
	    		msg.put("game_start", 1);
	    		System.out.println("server game start");
				broadcast(msg);
				hub.askForAction();
			}
			
		} else if (command.get("command_name").equals("initialize_game")) {
			System.out.println("server init game");
			// if someone wants to start a game
			// broadcast invitation to every member
			JSONObject msg = new JSONObject();
    		msg.put("invite_game", 1);
			broadcast(msg);
			
		}else if (command.get("command_name").equals("raise_Vote")) {
			System.out.println("raise vote");
			String word = (String)command.get("word");
			JSONObject msg = new JSONObject();
    		msg.put("raiseVoteWord", word);
    		hub.recordScore(word.length());
			broadcast(msg);
		} 
		
		else if (command.get("command_name").equals("pass")) {
			
			if (hub.checkPass() == 1){
				JSONObject msg = new JSONObject();
	    		msg.put("game_over", "1");
	    		broadcast(msg);
			}
			else{
				hub.nextPlayer();	
			}	
		} else if (command.get("command_name").equals("raise_No")) {						
			hub.nextPlayer();			
			
		} else if (command.get("command_name").equals("vote")) {
			System.out.println("server vote");
			String res = (String) command.get("selection");
			int[] score = hub.countVote(Integer.valueOf(res));
			if (score != null) {
				hub.nextPlayer();
				JSONObject msg = new JSONObject();
	    		msg.put("scores", 1);
	    		broadcast(msg);
	    		hub.askForAction();
			}
			
		} else if (command.get("command_name").equals("exit")) {
			System.out.println("server exit");
			JSONObject msg = new JSONObject();
    		msg.put("game_over", 1);
    		broadcast(msg);
    		hub.reset();
		}
		else if (command.get("command_name").equals("addChar")) {
			System.out.println("Server recieved add char");
			System.out.println(command.get("row").toString());
			System.out.println(command.get("column"));
			//String temp = command.get("row").toString();
			//int i = Integer.valueOf(temp);
			//int row = (int) command.get("row");
			//int column = (int) command.get("column");
			//char c = (char) command.get("char");
			//might not need to update the gameState in serve 
			//just broadcast the change to the rest of the members
			//correct me if I'm wrong..
			//hub.updateTable(row, column, c);
			JSONObject msg = new JSONObject();
			msg.put("updateBoard", "1");
    		msg.put("row", command.get("row"));
    		msg.put("column", command.get("column"));
    		msg.put("char", command.get("char"));
    		System.out.println("Server to broadcast add char");
    		write(msg);
    		
    		broadcast(msg);
			
		}
	}
}