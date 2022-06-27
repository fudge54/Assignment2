package sait.bankonit.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

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
	private Account account;
	private BankManager test = new BankManager();

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

		JPanel topPanel = createTopPanel();
		JPanel centerPanel = createCenterPanel();
		JPanel bottomPanel = createBottomPanel();

		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

	}

	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		String cardNumber = String.valueOf(account.getCardNumber());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		String bal = calcBal();
		JLabel cardNum = new JLabel("Card #" + cardNumber);
		cardNum.setFont(new Font("Serif", Font.BOLD, 20));
		JLabel balance = new JLabel(bal);

		((JComponent) panel.add(cardNum)).setAlignmentX(CENTER_ALIGNMENT);
		((JComponent) panel.add(balance)).setAlignmentX(CENTER_ALIGNMENT);
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JTextArea textBox = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textBox);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		textBox.setEditable(false);
		panel.add(scrollPane);

		ArrayList<Transaction> transactions;
		try {

			transactions = test.getTransactionsForAccount(account);
			for (int i = 0; i < transactions.size(); i++) {
				textBox.append(transactions.get(i).toString());
				textBox.append("\r\n");
			}
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went wrong.");
		}

		return panel;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		JLabel type = new JLabel("Type: ");
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
		panel.add(type, BorderLayout.NORTH);

		JRadioButton deposit = new JRadioButton("Deposit");
		panel.add(deposit, BorderLayout.NORTH);
		JRadioButton withdraw = new JRadioButton("Withdraw");
		panel.add(withdraw, BorderLayout.NORTH);
		ButtonGroup transactionType = new ButtonGroup();
		transactionType.add(withdraw);
		transactionType.add(deposit);

		JLabel amount = new JLabel("Amount:");
		panel.add(amount, BorderLayout.NORTH);
		JTextField input = new JTextField(20);
		panel.add(input, BorderLayout.NORTH);
		JButton submit = new JButton("Submit");
		panel.add(submit, BorderLayout.NORTH);
		JButton signOut = new JButton("Sign Out");
		panel.add(signOut, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Clears and re-populates transactions as well as updates balance.
	 */
	private void populateTransactions() {
		createCenterPanel();
		createTopPanel();
	}

	private String calcBal() {
		String balance = "";
		double bal = 0;
		ArrayList<Transaction> transactions;

		try {

			transactions = test.getTransactionsForAccount(account);
			Transaction oneT;

			for (int i = 0; i < transactions.size(); i++) {
				oneT = transactions.get(i);
				if (oneT.getTransactionType() == 'D') {
					bal -= oneT.getAmount();
				} else {
					bal += oneT.getAmount();
				}
			}
			if (bal >= 0) {
				balance = String.format("Balance: $%.2f", bal);
			} else {
				balance = String.format("Balance: $-%.2f", bal);
			}

		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return balance;
	}
}
