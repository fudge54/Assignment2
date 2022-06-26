package sait.bankonit.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Renders the login window
 * 
 * @author Nick Hamnett
 * @version Aug 3, 2021
 */
public class LoginWindow extends JFrame {

	/**
	 * Initializes the login window.
	 */
	public LoginWindow() {
		super("Bank On It Login");
		// test hey daniel
		// Set window size to 500x150
		this.setSize(500, 150);

		// Cause process to exit when X is clicked.
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Center login window in screen
		this.setLocationRelativeTo(null);
	}
}
