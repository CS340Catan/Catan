package client.login;

import client.base.*;
import client.misc.*;
import client.model.ClientModel;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import shared.communication.UserCredentials;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;

/**
 * Implementation for the login controller
 */
@SuppressWarnings("unused")
public class LoginController extends Controller implements ILoginController,
		Observer {

	private IMessageView messageView;
	private IAction loginAction;
	private IServer server;

	/**
	 * LoginController constructor
	 * 
	 * @param view
	 *            Login view
	 * @param messageView
	 *            Message view (used to display error messages that occur during
	 *            the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);

		this.messageView = messageView;
		ClientModel.getSingleton().addObserver(this);
	}

	public ILoginView getLoginView() {

		return (ILoginView) super.getView();
	}

	public IMessageView getMessageView() {

		return messageView;
	}

	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value
	 *            The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {

		loginAction = value;
	}

	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {

		return loginAction;
	}

	@Override
	public void start() {

		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		/*
		 * Grab sign in credentials from the LoginView and create a user
		 * credentials object.
		 */
		String signInUsername = this.getLoginView().getLoginUsername();
		String signInPassword = this.getLoginView().getLoginPassword();
		UserCredentials signInCredentials = new UserCredentials(signInUsername,
				signInPassword);

		try {
			if (canLogin(signInCredentials) && server.Login(signInCredentials)) {
				/*
				 * If the login succeeded, throw a success statement and execute
				 * loginAction.
				 */
				String outputStr = "Welcome, " + signInUsername + ".\n";
				JOptionPane.showMessageDialog(null, outputStr,
						"Welcome to Catan!", JOptionPane.PLAIN_MESSAGE);

				getLoginView().closeModal();
				loginAction.execute();
			} else {
				/*
				 * If the login does not succeed, throw an error message stating
				 * invalid login credentials.
				 */
				String outputStr = "The inputed Username/Password were invalid. Try again.";
				JOptionPane.showMessageDialog(null, outputStr,
						"Invalid User Login", JOptionPane.ERROR_MESSAGE);
			}
		} catch (ServerResponseException e) {
			/*
			 * If the server throws an exception, i.e. the user is not connected
			 * to the server.
			 */
			String outputStr = "Could not reach the server. Please try again later.";
			JOptionPane.showMessageDialog(null, outputStr,
					"Invalid User Login", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void register() {
		/*
		 * Grab register credentials from the LoginView and create a user
		 * credentials object.
		 */
		String registerUsername = this.getLoginView().getLoginUsername();
		String registerPassword = this.getLoginView().getLoginPassword();
		UserCredentials registerCredentials = new UserCredentials(
				registerUsername, registerPassword);

		try {
			if (canRegister(registerCredentials)
					&& server.Register(registerCredentials)) {
				/*
				 * If the register succeeded, throw a success statement and
				 * execute loginAction.
				 */
				String outputStr = "Thank you " + registerUsername
						+ " for registering for Catan.\n";
				JOptionPane.showMessageDialog(null, outputStr,
						"Welcome to Catan!", JOptionPane.PLAIN_MESSAGE);

				getLoginView().closeModal();
				loginAction.execute();
			} else {
				/*
				 * If the login does not succeed, throw an error message stating
				 * invalid register credentials.
				 */
				String outputStr = "The inputed Username/Password were invalid. Try again.";
				JOptionPane.showMessageDialog(null, outputStr,
						"Invalid User Register", JOptionPane.ERROR_MESSAGE);
			}
		} catch (ServerResponseException e) {
			/*
			 * If the server throws an exception, i.e. the user is not connected
			 * to the server.
			 */
			String outputStr = "Could not reach the server. Please try again later.";
			JOptionPane.showMessageDialog(null, outputStr,
					"Invalid User Register", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		/*
		 * TODO Check to see if there needs to be anything updated (probably
		 * nothing).
		 */

	}

	private boolean canLogin(UserCredentials loginCredentials) {
		/*
		 * Check that username is not null and password not null
		 */
		if (loginCredentials.getUsername() == null)
			return false;
		if (loginCredentials.getPassword() == null)
			return false;
		return true;
	}

	private boolean canRegister(UserCredentials registerCredentials) {
		/*
		 * Check that username is not null and password is not null.
		 */
		if (registerCredentials.getUsername() == null)
			return false;
		if (registerCredentials.getPassword() == null)
			return false;
		return true;
	}
}
