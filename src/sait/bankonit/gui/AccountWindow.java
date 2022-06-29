package sait.bankonit.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ca.bankonit.exceptions.InvalidAccountException;
import ca.bankonit.manager.BankManager;
import ca.bankonit.models.Account;
import ca.bankonit.models.Transaction;

/**
 * Renders the account window.
 * 
 * @author Nick Hamnett
 * @version Aug 3, 2021
 */
public class AccountWindow extends JFrame {

	private Account account; // Declares Account object globally.
	private BankManager bankManager = new BankManager(); // Creates BankManager object globally.
	private char dorw = 'X'; // Creates char globally.
	private JRadioButton deposit; // Declares JRadioButton
	private JRadioButton withdraw;
	private JTextField input;
	private JButton submit;
	private JButton signOut;
	private ArrayList<Transaction> transactions;
	private JPanel cPanel;
	private double amount = 0;
	private ActionListener listener;
	private JTextArea textBox;
	private JLabel balance;
	private JPanel bPanel;

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

		listener = new MyActionListener();

		JPanel topPanel = createTopPanel();
		JPanel centerPanel = createCenterPanel();
		JPanel bottomPanel = createBottomPanel();

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
		JPanel panel = new JPanel();
		String cardNumber = String.valueOf(account.getCardNumber());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		String bal = calcBal();
		JLabel cardNum = new JLabel("Card #" + cardNumber);
		cardNum.setFont(new Font("Serif", Font.BOLD, 20));
		balance = new JLabel(bal);

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
		cPanel = new JPanel();
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		textBox = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textBox);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		textBox.setEditable(false);
		cPanel.add(scrollPane);

		populateTransactions();
		return cPanel;
	}

	/**
	 * Creates and displays bottom panel.
	 * 
	 * @return bottom panel of window.
	 */
	private JPanel createBottomPanel() {
		bPanel = new JPanel();
		JLabel type = new JLabel("Type: ");

		bPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
		bPanel.add(type, BorderLayout.NORTH);

		deposit = new JRadioButton("Deposit");
		deposit.addActionListener(listener);
		bPanel.add(deposit, BorderLayout.NORTH);

		withdraw = new JRadioButton("Withdraw");
		withdraw.addActionListener(listener);
		bPanel.add(withdraw, BorderLayout.NORTH);

		ButtonGroup transactionType = new ButtonGroup();
		transactionType.add(withdraw);
		transactionType.add(deposit);

		JLabel amount = new JLabel("Amount:");
		bPanel.add(amount, BorderLayout.NORTH);

		input = new JTextField(20);
		bPanel.add(input, BorderLayout.NORTH);

		submit = new JButton("Submit");
		submit.addActionListener(listener);
		submit.revalidate();
		bPanel.add(submit, BorderLayout.NORTH);

		signOut = new JButton("Sign Out");
		signOut.addActionListener(listener);
		bPanel.add(signOut, BorderLayout.SOUTH);

		return bPanel;
	}

	/**
	 * Clears and re-populates transactions as well as updates balance.
	 */
	private void populateTransactions() {

		// createTopPanel();
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
		String balance = "";
		double bal = 0;

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

			if (e.getSource() == deposit) {
				dorw = 'D';
			} else if (e.getSource() == withdraw) {
				dorw = 'W';
			}
			// if (e.getSource() == input) {

			// }

			if (e.getSource() == submit) {

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
			input.setText("");
			populateTransactions();
			bankManager.persist();

			if (e.getSource() == signOut) {
				JOptionPane.showMessageDialog(AccountWindow.this, "Goodbye");

				AccountWindow.this.dispose();

			}
		}

	}

}
