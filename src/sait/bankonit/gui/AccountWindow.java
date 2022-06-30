package sait.bankonit.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import ca.bankonit.exceptions.InvalidAccountException;
import ca.bankonit.manager.BankManager;
import ca.bankonit.models.Account;
import ca.bankonit.models.Transaction;

/**
 * Renders the account window. Builds JFrames to fill the window with user
 * account information as well as the ability to make deposits and withdrawls
 * with the south JPanel. Information is saved and logout button sends user back
 * to the login window.
 * 
 * @author Daniel Bourdage-Gorny , Andrew Wahlers
 * @version 6/28/22
 */
public class AccountWindow extends JFrame {

	// fields
	private Account account;
	/** Account object to hold Account info. */
	private BankManager bankManager = new BankManager();
	/** Creates super class object. */
	private char dorw = 'X';
	/** Char Variable Deposit or Withdrawal */
	private JRadioButton deposit;
	/** Radio Button to choose deposit option */
	private JRadioButton withdraw;
	/** Radio Button to choose withdraw option. */
	private JTextField input;
	/** Text box for user input. */
	private JButton submit;
	/** Button to submit transaction. */
	private JButton signOut;
	/** Button to logout of account */
	private ArrayList<Transaction> transactions;
	/** Array list containing transactions of account */
	private JPanel cPanel;
	/** The center panel of the window */
	private double amount = 0;
	/** Variable to hold transaction amount. */
	private ActionListener listener;
	/** Action listener to handle buttons. */
	private JTextArea textBox;
	/** Text Area for center panel. */
	private JLabel balance;
	/** Label to display balance amount. */
	private JPanel bPanel;

	/** Bottom panel of the window. */

	/**
	 * Initializes the account window
	 * 
	 * @param account Account to manage
	 */
	public AccountWindow(Account account) {
		super("Bank On It Account");

		// Store account as field.
		this.account = account;

		// Set size to 600x500
		this.setSize(600, 500);

		// set termination process
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create action listener for frame.
		listener = new MyActionListener();

		// Call methods to create panels.
		JPanel topPanel = createTopPanel();
		JPanel centerPanel = createCenterPanel();
		JPanel bottomPanel = createBottomPanel();

		// Organize panels in the frame.
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

	}

	/**
	 * Creates and displays top panel.
	 * 
	 * @return top panel of window.
	 */
	private JPanel createTopPanel() {

		// fields
		String cardNumber = String.valueOf(account.getCardNumber());
		String bal = calcBal();
		balance = new JLabel(bal);

		// Create panel with layout.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// populate panel with correct data
		JLabel cardNum = new JLabel("Card #" + cardNumber);
		cardNum.setFont(new Font("Serif", Font.BOLD, 20));

		((JComponent) panel.add(cardNum)).setAlignmentX(CENTER_ALIGNMENT);
		((JComponent) panel.add(balance)).setAlignmentX(CENTER_ALIGNMENT);
		return panel;
	}

	/**
	 * Creates and displays center panel.
	 * 
	 * @return center panel of window.
	 */
	private JPanel createCenterPanel() {

		// Create panel with layout.
		cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		textBox = new JTextArea();

		// Add scrollpane to textArea
		JScrollPane scrollPane = new JScrollPane(textBox);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		textBox.setEditable(false);
		cPanel.add(scrollPane);

		// Calls method to populate panel with data.
		populateTransactions();
		return cPanel;
	}

	/**
	 * Creates and displays bottom panel.
	 * 
	 * @return bottom panel of window.
	 */
	private JPanel createBottomPanel() {

		// Create panel with layout add the following sections to north side of panel.
		bPanel = new JPanel();
		JLabel type = new JLabel("Type: ");

		bPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
		bPanel.add(type, BorderLayout.NORTH);

		// Create deposit button
		deposit = new JRadioButton("Deposit");
		deposit.addActionListener(listener);
		bPanel.add(deposit, BorderLayout.NORTH);

		// Create withdraw button
		withdraw = new JRadioButton("Withdraw");
		withdraw.addActionListener(listener);
		bPanel.add(withdraw, BorderLayout.NORTH);

		// Create button group for withdraw/deposit.
		ButtonGroup transactionType = new ButtonGroup();
		transactionType.add(withdraw);
		transactionType.add(deposit);

		JLabel amount = new JLabel("Amount:");
		bPanel.add(amount, BorderLayout.NORTH);

		// Create input textbox for amount.
		input = new JTextField(20);
		bPanel.add(input, BorderLayout.NORTH);

		// Create submit button
		submit = new JButton("Submit");
		submit.addActionListener(listener);
		submit.revalidate();
		bPanel.add(submit, BorderLayout.NORTH);

		// Create sign out button on south side of panel.
		signOut = new JButton("Sign Out");
		signOut.addActionListener(listener);
		bPanel.add(signOut, BorderLayout.SOUTH);

		return bPanel;
	}

	/**
	 * Clears and re-populates transactions as well as updates balance and populates
	 * middle panel with data.
	 */
	private void populateTransactions() {

		// Populate middle panel with data
		try {
			textBox.setText("");
			transactions = bankManager.getTransactionsForAccount(account);
			for (int i = 0; i < transactions.size(); i++) {
				textBox.append(transactions.get(i).toString());
				textBox.append("\r\n");
			}
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went wrong.");
		}
		balance.setText(calcBal());

	}

	/**
	 * Calculate and display balance to the user.
	 * 
	 * @return Balance of account.
	 */
	private String calcBal() {

		// fields
		String balance = "";
		double bal = 0;

		// try catch to calculate balance and deal with all exceptions
		try {

			transactions = bankManager.getTransactionsForAccount(account);
			Transaction oneT;

			for (int i = 0; i < transactions.size(); i++) {
				oneT = transactions.get(i);
				if (oneT.getTransactionType() == 'D') {
					bal += oneT.getAmount();
				} else {
					bal -= oneT.getAmount();
				}
			}

			// format balance as string
			balance = String.format("Balance: $%.2f", bal);

		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return balance;
	}

	/**
	 * Uses ActionListener to manage user input.
	 * 
	 * @author Daniel Bourdage-Gorny, Andrew Wahlers
	 *
	 */
	private class MyActionListener implements ActionListener {
		@Override

		public void actionPerformed(ActionEvent e) {

			// if else loop to check whether deposit or withdrawal
			if (e.getSource() == deposit) {
				dorw = 'D';
			} else if (e.getSource() == withdraw) {
				dorw = 'W';
			}
			// if (e.getSource() == input) {

			// }

			// check to detect user submission
			if (e.getSource() == submit) {

				// If statement to Call super class method to add
				// either withdrawal or deposit and deal with exceptions
				try {
					amount = Double.parseDouble(input.getText());
					if (dorw == 'D') {
						bankManager.deposit(account, amount);
					} else if (dorw == 'W') {
						bankManager.withdraw(account, amount);
					}
				} catch (InvalidAccountException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(bPanel, "Amount input is not a valid Double.");
				}
			}

			// Refresh data in window with newly added user data
			input.setText("");
			populateTransactions();
			bankManager.persist();

			// Display message on exit to the user
			if (e.getSource() == signOut) {
				JOptionPane.showMessageDialog(AccountWindow.this, "Goodbye");

				AccountWindow.this.dispose();

			}
		}

	}

}
