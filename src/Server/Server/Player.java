package Server;

public class Player {
	
	private String playerName;
	private int playerId;
	private int playerScore;
	
	
	public Player(String name, int id){
		this.playerName = name == "" ? "anonymous" + id : name;
		this.playerId = id;
		this.playerScore = 0;
	}
	
	public String getPlayerName(){
		return this.playerName;
	}
	
	public int getPlayerId(){
		return this.playerId;
	}
	
	public int getPlayerScore(){
		return this.playerScore;
	}
	
	public int addPlayerScore(int score){
		return this.playerScore += score;
	}
}
