package Client;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class OperationJPanel extends JPanel {
	private JTextArea status;
	private JTextArea log;
	private JButton exitButton;
	
	/**
	 * Create the panel.
	 */
	public OperationJPanel(JPanel actionJPanel) {
		setBounds(0, 0, 350, 840);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10,140, 140, 10};
		gridBagLayout.rowHeights = new int[]{10, 400, 10, 100, 10, 280, 10, 20, 10};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);

		
		status = new JTextArea();
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
		
		log = new JTextArea();
		GridBagConstraints gbc_log_1 = new GridBagConstraints();
		gbc_log_1.fill = GridBagConstraints.BOTH;
		gbc_log_1.insets = new Insets(0, 0, 5, 5);
		gbc_log_1.gridwidth = 2;
		gbc_log_1.gridx = 1;
		gbc_log_1.gridy = 3;
		add(log, gbc_log_1);
		
		JPanel action = actionJPanel;
		GridBagConstraints gbc_action = new GridBagConstraints();
		gbc_action.fill = GridBagConstraints.CENTER;
		gbc_action.insets = new Insets(0, 0, 5, 5);
		gbc_log_1.anchor = GridBagConstraints.CENTER;
		gbc_action.gridwidth = 2;
		gbc_action.gridx = 1;
		gbc_action.gridy = 5;	
		add(action, gbc_action);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("exit");				
				Game game = Game.getInstance();
				game.exit();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 6;
		add(exitButton, gbc_btnNewButton);
		
		
	}
	
	public void updateLog(String str) {
		log.append(str+"\n");
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public void updateStatus(String str) {
		status.setText(str);
	}
	
    private static void createAndShowGui() {
    	OperationJPanel mainPanel = new OperationJPanel(new ActionJPanel().getJPanel(0));

        JFrame frame = new JFrame("JPanelGrid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	System.out.println(new String("a"));
        SwingUtilities.invokeLater(() -> {
            createAndShowGui();
        });
    }
}
