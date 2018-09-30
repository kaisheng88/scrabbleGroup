package Client;
import javax.swing.*;
import javax.xml.transform.Templates;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class GameGUI {

	private JFrame frame;
	private GridJPanel gamePanel = new GridJPanel();
	private ActionJPanel actions = new ActionJPanel(); 
	private int temp = 0;
	//private Game game;

   
	/**
	 * Create the application.
	 */
	public GameGUI() {
		initialize();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(gamePanel,BorderLayout.CENTER);		
		frame.getContentPane().add(actions.board,BorderLayout.EAST);	
		frame.setVisible(true);
		actions.exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				temp++;
				
				changePanel(temp%5);
			}
		});
		changePanel(1);
	}
	
	public void closeWindow(){
		frame.dispose();
	}

	
	public void changePanel(int pnum) {
		actions.board.remove(3);
		actions.board.add(actions.getJPanel(pnum),actions.gbc_action);
		frame.getContentPane().repaint();
		frame.getContentPane().revalidate();
	}
	
	public void updateGrid(int i, int j, char c) {
		gamePanel.buttons[i][j].setText(""+c);
	}
	
	public boolean vote(int si,int sj,int ei,int ej) {
		return true;
	}
	
	public void gameOver() {
		
	}
	
	public void updateScore(int[] scores) {
		
	}
	
	public void updateLog(String str) {
		actions.log.append(str+"\n");
		actions.log.setCaretPosition(actions.log.getDocument().getLength());
	}
	
	public void updateStatus(String str) {
		actions.status.setText(str);
	}
	
	public void play() {
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();        
					window.frame.setVisible(true);
					window.changePanel(4);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
}
