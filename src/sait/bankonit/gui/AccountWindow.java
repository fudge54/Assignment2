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
	private Account account;
	private BankManager test = new BankManager();
	private char dorw = 'X';
	JRadioButton deposit;
	JRadioButton withdraw;
	JTextField input;
	JButton submit;
	JButton signOut;
	ArrayList<Transaction> transactions;
	double amount;

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

		deposit = new JRadioButton("Deposit");
		panel.add(deposit, BorderLayout.NORTH);
		withdraw = new JRadioButton("Withdraw");
		panel.add(withdraw, BorderLayout.NORTH);
		ButtonGroup transactionType = new ButtonGroup();
		transactionType.add(withdraw);
		transactionType.add(deposit);

		JLabel amount = new JLabel("Amount:");
		panel.add(amount, BorderLayout.NORTH);
		input = new JTextField(20);
		panel.add(input, BorderLayout.NORTH);
		submit = new JButton("Submit");
		panel.add(submit, BorderLayout.NORTH);
		signOut = new JButton("Sign Out");
		panel.add(signOut, BorderLayout.SOUTH);

		ActionListener listener = new MyActionListener();

		submit.addActionListener(listener);
		withdraw.addActionListener(listener);
		deposit.addActionListener(listener);
		signOut.addActionListener(listener);

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

	private class MyActionListener implements ActionListener {
		@Override

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == deposit) {
				dorw = 'D';
			} else if (e.getSource() == withdraw) {
				dorw = 'W';
			}
			if (e.getSource() == input) {
				amount = Double.parseDouble(input.getText());
			}

			if (e.getSource() == submit) {

				if (dorw == 'D') {

					transactions.add(account.getCardNumber(), dorw, amount, date);
				}

			}

		}

	}

}
