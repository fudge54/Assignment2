package sait.bankonit.gui;

import javax.swing.JFrame;

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
	}

	/**
	 * Clears and re-populates transactions as well as updates balance.
	 */
	private void populateTransactions() {

	}
}
