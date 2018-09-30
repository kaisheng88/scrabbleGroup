package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String address = args[0];
		String address = "127.0.0.1";
		
		Integer port = 4444;
		
//		try {
//			port = Integer.parseInt(args[0]);
//		} catch (Exception e) {
//			System.out.println("Bad port number");
//			System.exit(0);
//		}
		
		try{
			
			Socket socket = new Socket(address, port);
			
			//DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			//DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			MessageSender sender = new MessageSender(outputStream);
			Client client = new Client(inputStream, sender);
			client.start();

			Game game = Game.getInstance();
			game.setSender(sender);
			GameGUI gameGUI = new GameGUI();  
			game.SetGUI(gameGUI);
		    
			// call gui main
			
		}
		catch (IOException e) {
			
			System.out.println("Something wrong with IO");
			
		}
	}

}
