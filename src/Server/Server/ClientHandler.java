package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientHandler implements Runnable{
	
	private Socket clientSocket;
	private TileBag tileBag;
	private Player player;
	private int playerId; 
	
	public ClientHandler(Socket cs, TileBag tb, int id){
		this.clientSocket = cs;
		this.tileBag = tb;
		this.playerId = id;
	}
	
	public void run() {
		try {
		// TODO Auto-generated method stub
		//Get the input/output streams for reading/writing data from/to the socket
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
		System.out.println("client handler");
		JSONParser parser = new JSONParser();
		String clientMsg = null;
		
		try {
		while(true) {
			clientMsg = in.readLine();
			System.out.println(clientMsg);
			if (clientMsg == null) break;
			JSONObject command = (JSONObject) parser.parse(clientMsg);
			//to be continued
			
			out.flush();
			System.out.println("Response sent");
		}}
		catch(SocketException e) {
			System.out.println("closed...");
			out.write("Error: System error");
			out.flush();
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.write("Error: System error");
			out.flush();
		}
		clientSocket.close();
	} catch (SocketException ex) {
		ex.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	} 
		
	}

}
