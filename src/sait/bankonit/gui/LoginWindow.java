package sait.bankonit.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ca.bankonit.manager.BankManagerBroker;
import ca.bankonit.models.Account;

/**
 * Renders the login window
 * 
 * @author Andrew Wahlers(775340)
 * @version June 30/22
 */
public class LoginWindow extends JFrame {
	private JTextField cardNum; // Card Number input field
	private JPasswordField pin; // Pin input field
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JLabel bankOn;
	private JButton login;
	private ActionListener listener;

	/**
	 * Initializes the login window.
	 */
	public LoginWindow() {
		super("Bank On It Login");
		// Set window size to 500x150
		this.setSize(500, 150);

		// Cause process to exit when X is clicked.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// bring in action listener
		listener = new MyActionListener();

		// Create the panels for our log in window
		JPanel northPanel = createNorthPanel();
		JPanel centerPanel = createCenterPanel();
		JPanel southPanel = createSouthPanel();

		// Add the panels to our login window
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);

		// Center login window in screen
		this.setLocationRelativeTo(null);
	}

	/**
	 * Creates a JPanel with a label for Bank On It Login.
	 * 
	 * @return The top panel for the window
	 */
	private JPanel createNorthPanel() {
		northPanel = new JPanel();
		bankOn = new JLabel("Bank On It Login");
		bankOn.setFont(new Font("Serif", Font.BOLD, 24));
		northPanel.add(bankOn);
		return northPanel;
	}

	/**
	 * Creates the center panel with fields for users to enter both card number and
	 * pin for accessing their bank account
	 * 
	 * @return center panel
	 */
	private JPanel createCenterPanel() {

		//
		centerPanel = new JPanel();
		JLabel cardLabel = new JLabel("Card Number: ");
		JLabel pinLabel = new JLabel("PIN: ");

		cardNum = new JTextField(20);
		pin = new JPasswordField(6);

		centerPanel.add(cardLabel);
		centerPanel.add(cardNum);
		centerPanel.add(pinLabel);
		centerPanel.add(pin);

		return centerPanel;

	}

	private JPanel createSouthPanel() {
		southPanel = new JPanel();
		login = new JButton("Login");
		login.addActionListener(listener);
		southPanel.add(login);
		return southPanel;

	}

	private class MyActionListener implements ActionListener {
		@Override

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == login) {

				try {
					long card = Long.parseLong(cardNum.getText());
					short pinNum = Short.parseShort(new String(pin.getPassword()));
					Account account = BankManagerBroker.getInstance().login(card, pinNum);
					if (account == null) {
						JOptionPane.showMessageDialog(centerPanel, "Incorrect card number or pin.");
					} else {
						AccountWindow accountWindow = new AccountWindow(account);
						accountWindow.setVisible(true);
					}

				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(centerPanel, "Invalid input detected, Please try again.");
				} // catch (InvalidAccountException e1) {
					// JOptionPane.showMessageDialog(centerPanel, "Invalid account.");
					// }
			}
		}
	}
}
