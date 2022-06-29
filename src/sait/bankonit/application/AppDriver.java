package sait.bankonit.application;

import sait.bankonit.gui.LoginWindow;

/**
 * Application driver
 * 
 * @author Andrew Wahlers(775340)
 * @version
 */
public class AppDriver {

	/**
	 * Entry point for program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/* Uncomment for versions 1.0 - 3.0 */
		// long cardNumber = 4444111122223333L;
		// short pin = 4444;
		//
		// Account account = BankManagerBroker.getInstance().login(cardNumber, pin);
		// AccountWindow accountWindow = new AccountWindow(account);
		// accountWindow.setVisible(true);
		// System.out.println(account.toString());
		//

		/* Uncomment for version 4.0 */
		LoginWindow login = new LoginWindow();
		login.setVisible(true);

	}

}
