package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class ScrabbleServer {
	
public static ClientHandler clientHandler;
	
	public static void main(String[] args) {
		
		ServerSocket listeningSocket = null;
		int count = 0;
		
		try {
					
			Map<Character, Integer> tileBag = getTileBag("./tileBag.json");
									
			TileBag tb = new TileBag(tileBag);
			
			//needs changing later on
			listeningSocket = new ServerSocket(4444);
 
			while (true) {
				
				clientHandler = new ClientHandler(listeningSocket.accept(), tb, count++); //This method will block until a connection request is received
				new Thread(clientHandler).start();				
				
			}
		}catch (SocketException ex) {
			ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if(listeningSocket != null) {
				try {
					listeningSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static Map<Character, Integer> getTileBag(String filePath){
		//continue tomorrow
		return new HashMap<Character, Integer>();
	}
	
	

}
