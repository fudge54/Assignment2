package sait.bankonit.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import ca.bankonit.models.Account;

/**
 * Renders the account window.
 * 
 * @author Nick Hamnett
 * @version Aug 3, 2021
 */
public class AccountWindow extends JFrame {
	private Account account;

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
		JLabel cardNum = new JLabel(cardNumber);
		JLabel balance = new JLabel("test");

		((JComponent) panel.add(cardNum)).setAlignmentX(CENTER_ALIGNMENT);
		((JComponent) panel.add(balance)).setAlignmentX(CENTER_ALIGNMENT);
		return panel;
	}

	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		return panel;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();

		return panel;
	}

	/**
	 * Clears and re-populates transactions as well as updates balance.
	 */
	private void populateTransactions() {

	}
}
