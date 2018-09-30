package Client;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JComboBox;

public class ActionJPanel{
	
	
	public JPanel board;
	public JTextArea status;
	public JTextArea log;
	public JButton exitButton;
	public JButton addCharConfirmButton;
	public JButton passTurn;
	public JButton raiseVoteNoButton;
	public JButton raiseVoteYesButton;
	public JButton raiseVoteSetStartButton;
	public JButton raiseVoteSetEndButton;
	public JButton raiseVoteConfirmButton;
	public JButton voteYesButton;
	public JButton voteNoButton;
	public JComboBox comboBox;
	public GridBagConstraints gbc_action;
	
	Game game = Game.getInstance();
	
	private JPanel[] actionJPanels = new JPanel[5];
	public static final int NO_ACTION = 0;
	public static final int ADD_CHAR = 1;
	public static final int RAISE_VOTE_OR_NOT = 2;
	public static final int RAISE_VOTE = 3;
	public static final int VOTE = 4;
	
	public class WaitJPanel extends JPanel{
		public WaitJPanel() {
			setLayout(new BorderLayout(0, 0));
			
			JTextField txtWaitingForOther = new JTextField();
			txtWaitingForOther.setEditable(false);
			txtWaitingForOther.setText("Waiting for other players...");
			txtWaitingForOther.setHorizontalAlignment(JTextField.CENTER);
			add(txtWaitingForOther,BorderLayout.CENTER);
		}
	}
	
	public class AddCharJPanel extends JPanel{
		public AddCharJPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			String[] chars = new String[26];
			for (int i=0;i<26;i++) {
				chars[i] = ""+(char) (i+65); 
			}
			comboBox = new JComboBox(chars);
			add(comboBox);
			addCharConfirmButton = new JButton("Confirm");
			add(addCharConfirmButton);
			passTurn = new JButton("Pass");
			add(passTurn);
		}
	}
	
	public class RaiseVoteOrNotJPanel extends JPanel{
		public RaiseVoteOrNotJPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			raiseVoteYesButton = new JButton("Yes");
			add(raiseVoteYesButton);
			
			raiseVoteNoButton = new JButton("No");
			add(raiseVoteNoButton);
			
		}
	}	
	
	public class RaiseVoteJPanel extends JPanel{
		public RaiseVoteJPanel(){
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			raiseVoteSetStartButton = new JButton("AsStart");
			add(raiseVoteSetStartButton);
			raiseVoteSetEndButton = new JButton("AsEnd");
			add(raiseVoteSetEndButton);
			raiseVoteConfirmButton = new JButton("Confirm");
			add(raiseVoteConfirmButton);
		}
	}
	
	public class VoteJPanel extends JPanel{
		private JTextField voteInfo;
		public VoteJPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
	
			voteYesButton = new JButton("Yes");
			add(voteYesButton);
			
			voteNoButton = new JButton("No");
			add(voteNoButton);
		}
		
		public void setVoteInfo(String str) {
			voteInfo.setText(str);
		}
	}
	
	public ActionJPanel() {
		status = new JTextArea();
		log = new JTextArea();
		exitButton = new JButton("Exit");
		board = new OperationJPanel();
		
		actionJPanels[NO_ACTION] = new WaitJPanel();
		actionJPanels[ADD_CHAR] = new AddCharJPanel();
		actionJPanels[RAISE_VOTE_OR_NOT] = new RaiseVoteOrNotJPanel();
		actionJPanels[RAISE_VOTE] = new RaiseVoteJPanel();
		actionJPanels[VOTE] = new VoteJPanel();
		addActionListener();
	}
	
	private void addActionListener(){
//  	public JButton exitButton;
//		public JButton addCharConfirmButton;
//		public JButton passTurn;
//		public JButton raiseVoteNoButton;
//		public JButton raiseVoteYesButton;
//		public JButton raiseVoteSetStartButton;
//		public JButton raiseVoteSetEndButton;
//		public JButton raiseVoteConfirmButton;
//		public JButton voteYesButton;
//		public JButton voteNoButton;
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("exit");			
//				game.exit();
			}
		});
		
		passTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = (String) comboBox.getSelectedItem();
				
				game.passPlayer();
				System.out.println("Player pass.");
			}
		});
		
		raiseVoteYesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.raiseVoteYes();
				System.out.println("Vote yes.");
			}
		});
		
		
		
		raiseVoteNoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.raiseVoteNo();;
				System.out.println("Vote no.");
			}
		});
		
		raiseVoteSetStartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.selectStart();
			}
		});
		
		raiseVoteSetEndButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.selectEnd();
			}
		});
		
		raiseVoteConfirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.raiseVote();
			}
		});
		
			
		addCharConfirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = (String) comboBox.getSelectedItem();
				
				game.putCharacter(temp.charAt(0));
				System.out.println(comboBox.getSelectedItem()+"changed");
			}
		});
		
		
		
		voteNoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				
				System.out.println("selected vote no");
				game.vote("0");
			}
		});
		
		voteYesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				
				System.out.println("selected vote yes");
				game.vote("1");
			}
		});
	}
	
    private static void createAndShowGui() {
    	ActionJPanel mainPanel = new ActionJPanel();

        JFrame frame = new JFrame("JPanelGrid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel.actionJPanels[4]);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	System.out.println(Double.MIN_VALUE);
        SwingUtilities.invokeLater(() -> {
            createAndShowGui();
        });
    }

	public JPanel getJPanel(int pnum) {
		return actionJPanels[pnum];
	}
	
	public class OperationJPanel extends JPanel {
		/**
		 * Create the panel.
		 */
		public OperationJPanel() {
			setBounds(0, 0, 350, 840);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{10,140, 140, 10};
			gridBagLayout.rowHeights = new int[]{10, 400, 10, 100, 10, 20, 10, 280, 10};
			gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			setLayout(gridBagLayout);

			
			
			JScrollPane scroll = new JScrollPane(status); 
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
			GridBagConstraints gbc_state_1 = new GridBagConstraints();
			gbc_state_1.fill = GridBagConstraints.BOTH;
			gbc_state_1.insets = new Insets(0, 0, 5, 5);
			gbc_state_1.gridwidth = 2;
			gbc_state_1.gridx = 1;
			gbc_state_1.gridy = 1;
			add(scroll, gbc_state_1);
			

			GridBagConstraints gbc_log_1 = new GridBagConstraints();
			gbc_log_1.fill = GridBagConstraints.BOTH;
			gbc_log_1.insets = new Insets(0, 0, 5, 5);
			gbc_log_1.gridwidth = 2;
			gbc_log_1.gridx = 1;
			gbc_log_1.gridy = 3;
			add(log, gbc_log_1);

			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
			gbc_btnNewButton.gridwidth = 2;
			gbc_btnNewButton.gridx = 1;
			gbc_btnNewButton.gridy = 5;
			add(exitButton, gbc_btnNewButton);
			
			JPanel action = new WaitJPanel();
			gbc_action = new GridBagConstraints();
			gbc_action.fill = GridBagConstraints.CENTER;
			gbc_action.insets = new Insets(0, 0, 5, 5);
			gbc_log_1.anchor = GridBagConstraints.CENTER;
			gbc_action.gridwidth = 2;
			gbc_action.gridx = 1;
			gbc_action.gridy = 7;	
			add(action, gbc_action);
			
		}
		

	}

}
