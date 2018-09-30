package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		Integer port = null;
		
		/*try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Bad port number");
			System.exit(0);
		}*/
		port = 4444;
		
		int count = 0;
		ServerSocket listeningSocket = null;
		try{
			
			listeningSocket = new ServerSocket(port);
			System.out.println(Thread.currentThread().getName() + " listening on port " + port);
			
			while (true) {
				
				Socket clientSocket = listeningSocket.accept();
				System.out.println(Thread.currentThread().getName() + " client connection accepted");
				
				ClientConnection clientConnection = new ClientConnection(clientSocket, count++);
				clientConnection.start();
				
				GameHub.getInstance().addConnection(clientConnection);
			}
		}
		catch (IOException e) {
			
			System.out.println("Something wrong with IO");
			
		}
	}

}
