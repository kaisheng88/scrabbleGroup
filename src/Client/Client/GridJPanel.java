package Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.*;



public class GridJPanel extends JPanel{
	private static final int SIZE = 20;
	
    private static final int GAP = 1;
    private static final Color BG = Color.BLACK;
    private static final Dimension BTN_PREF_SIZE = new Dimension(40, 40);
    private Game game;
    public JButton[][] buttons = new JButton[SIZE][SIZE];
    
    public class GridJButton extends JButton implements ActionListener{
    	public int i,j;
    	public GridJButton(int i,int j) {
    		super("");
    		this.i=i;
    		this.j=j;
    	}
		@Override
		public void actionPerformed(ActionEvent e) {
			game.selectTile(i, j);
		}
    }
    
    public GridJPanel() {
    	this.game = Game.getInstance();
        setBackground(BG);
        setLayout(new GridLayout(SIZE+1, SIZE+1, GAP, GAP));
        setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        for (int i = 0; i < buttons.length+1; i++) {
        	String ftext = Integer.toString(i);
        	JTextField temp = new JTextField();
        	temp.setText(ftext);
        	temp.setPreferredSize(BTN_PREF_SIZE);
        	temp.setHorizontalAlignment(JTextField.CENTER);
        	add(temp);
        }
        for (int i = 0; i < buttons.length; i++) {
        	String ftext = Integer.toString(i+1);
        	JTextField temp = new JTextField();
        	temp.setText(ftext);
        	temp.setPreferredSize(BTN_PREF_SIZE);
        	temp.setHorizontalAlignment(JTextField.CENTER);
        	add(temp);
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new GridJButton(i,j);
                buttons[i][j].setPreferredSize(BTN_PREF_SIZE);
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 8));
                buttons[i][j].addActionListener((ActionListener) buttons[i][j]);

                add(buttons[i][j]);
            }
        }
    }

    private static void createAndShowGui() {
//        GridJPanel mainPanel = new GridJPanel(Game.getInstance(sender));
//
//        JFrame frame = new JFrame("JPanelGrid");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(mainPanel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	System.out.println(new String("a"));
        SwingUtilities.invokeLater(() -> {
            createAndShowGui();
        });
    }

}