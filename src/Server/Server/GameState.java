package Server;

public class GameState {
	
	private char table[][];

	public GameState(int playerNum) {
		table = new char[20][20];
	}
	
	/*
	public void updateTable(int row, int column, char c) {
		
		table[row][column] = c;
	}
	*/
	public char[][] returnTable(){
		return table;
	}
}
