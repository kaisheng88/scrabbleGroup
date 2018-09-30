package Server;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class GameHub {

	private static GameHub instance;
	private static int playerNum = 1;//to be changed later
	private int currPlayer;
	private int scores[] = new int[1];
	private int acceptNum;
	private int passNum;
	private int totalVote;
	private int votes;
	private int score;
	private GameState gameState;
	private boolean gameStart = false;
	private List<ClientConnection> pool;

	private GameHub() {
		currPlayer = 0;
		acceptNum = 0;
		pool = new ArrayList<>();
	}

	public static GameHub getInstance() {

		if (instance == null) {
			instance = new GameHub();
		}
		return instance;
	}

	public void reset() {

		playerNum = 0;
		currPlayer = 0;
		scores = null;
		acceptNum = 0;
		passNum = 0;
		totalVote = 0;
		votes = 0;
		score = 0;
		gameState = null;
	}

	public void addConnection(ClientConnection connection) {
		pool.add(connection);
	}

	public void cutConnection(ClientConnection connection) {
		pool.remove(connection);
	}

	public List<ClientConnection> getPool() {
		return pool;
	}

	public void sendInvitation() {

		playerNum = pool.size();
		scores = new int[playerNum];
	}

	public int acceptInvitation() {

		acceptNum++;
		if (acceptNum == playerNum) {

			gameStart = true;
			passNum = 0;
			gameState = new GameState(playerNum);
			totalVote = 0;
			return 1;
		}
		return 0;
	}

	public int checkPass() {

		// return 1 if game ends
		passNum++;
		if (passNum == playerNum) {

			return 1;
		}
		return 0;
	}

	public void askForAction() {

		JSONObject msg = new JSONObject();
		msg.put("your_turn", 1);
		pool.get(currPlayer).write(msg);
	}

	public boolean checkMsgSource(ClientConnection connection) {

		return pool.get(currPlayer).equals(connection);
	}

	//Not needed to keep track of the game state, just do a broadcast of the 
	//put char to all players
	/*public void updateTable(int row, int column, char c) {

		passNum = 0;
		gameState.updateTable(row, column, c);
	}*/
	
	public GameState getTable(){
		return gameState;
	}

	public void recordScore(int score) {

		// record potential score
		// waits for voting
		this.score = score;
	}

	public int[] countVote(int vote) {

		// count number of players who voted
		// if everybody has voted, return score
		totalVote++;
		votes += vote;
		if (totalVote == playerNum) {
			totalVote = 0;
			votes = 0;
			//if more than votes is equal to half or more than the votes
			if (votes >= totalVote / 2) {
				//might need to use objects instead after monday
				//this.pool.get(currPlayer).player.addPlayerScore(score);
				scores[currPlayer] += score;
				return scores;
			}else{
				return scores;
			}
		}
		return null;
	}

	public void nextPlayer() {

		currPlayer = (currPlayer + 1) % playerNum;
	}
}
