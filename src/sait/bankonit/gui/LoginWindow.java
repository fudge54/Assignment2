package sait.bankonit.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ca.bankonit.manager.BankManagerBroker;
import ca.bankonit.models.Account;

/**
 * Renders the login window, Build JFrames to fill the window for the login
 * interface Users can input their banking information and click login which
 * will pull up their account information (if information is valid) Calls
 * AccountWindow.java with account information
 * 
 * @author Andrew Wahlers(775340) Daniel Bourdage-Gorny
 * @version June 30/22
 */
public class LoginWindow extends JFrame {
	private JTextField cardNum; // Card Number input field
	private JPasswordField pin; // Pin input field
	private JPanel northPanel; // North Panel with logo
	private JPanel centerPanel; // Center panel with input boxes
	private JPanel southPanel; // South Panel with login button
	private JLabel bankOn; // Logo label
	private JButton login; // Login button
	private ActionListener listener; // Action listener

	/**
	 * Initializes the login window. Sets the size of a window and calls for
	 * creation of panels within the login panel.
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
		// create a new panel object
		northPanel = new JPanel();
		// add the Bank on it logo to the panel
		bankOn = new JLabel("Bank On It Login");
		// Make the font bigger and bolder
		bankOn.setFont(new Font("Serif", Font.BOLD, 24));
		// add the label to the panel
		northPanel.add(bankOn);
		// return the newly built north panel
		return northPanel;
	}

	/**
	 * Creates the center panel with fields for users to enter both card number and
	 * pin for accessing their bank account
	 * 
	 * @return center panel
	 */
	private JPanel createCenterPanel() {

		// create a JPanel
		centerPanel = new JPanel();
		// create labels to go in front of each of the fields
		JLabel cardLabel = new JLabel("Card Number: ");
		JLabel pinLabel = new JLabel("PIN: ");

		// card number text field with 20 spaces of field displayed
		cardNum = new JTextField(20);
		// create pin password field with 6 spaces
		pin = new JPasswordField(6);

		// add each panel in order of wanted appearance
		centerPanel.add(cardLabel);
		centerPanel.add(cardNum);
		centerPanel.add(pinLabel);
		centerPanel.add(pin);

		// return the newly built panel
		return centerPanel;

	}

	/**
	 * Creates a JPanel for the southern part of the window. Adds a log in button
	 * with a listener User can click the log in button after entering their info
	 * 
	 * @return South panel with log in button
	 */
	private JPanel createSouthPanel() {
		// Create panel
		southPanel = new JPanel();
		// create button with Login label
		login = new JButton("Login");
		// add button to action listener
		login.addActionListener(listener);
		// add button to the panel
		southPanel.add(login);
		return southPanel;

	}

	/**
	 * Action Listener for the log in button takes the input card number and pin and
	 * creates an account with matching information Then checks if that account has
	 * correct info and if correct information calls to create a new account window
	 * with valid credentials.
	 * 
	 * If input is incorrect user will see an invalid message Class description:
	 *
	 * @author Andrew Wahlers (775340)
	 *
	 */
	private class MyActionListener implements ActionListener {
		@Override

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == login) {

				try {
					// get card number from the text field
					long card = Long.parseLong(cardNum.getText());
					// get pin number from the password field
					short pinNum = Short.parseShort(new String(pin.getPassword()));
					// create account with the info
					Account account = BankManagerBroker.getInstance().login(card, pinNum);
					// check if account is invalid
					if (account == null) {
						JOptionPane.showMessageDialog(centerPanel, "Incorrect card number or pin.");
					} else {
						// create account window with users information from account
						AccountWindow accountWindow = new AccountWindow(account);
						// show user the window
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
