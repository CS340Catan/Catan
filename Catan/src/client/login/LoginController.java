package client.login;

import client.base.*;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;
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
import shared.data.PlayerInfo;
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
		ClientModel.getNotifier().addObserver(this);
		server = ServerProxy.getSingleton();
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
			if (canLogin(signInCredentials) && server.login(signInCredentials)) {
				/*
				 * If the login succeeded, throw a success statement and execute
				 * loginAction.
				 */
				UserPlayerInfo.getSingleton().setName(signInUsername);
				String outputStr = "Welcome, " + signInUsername + ".\n";
				String title = "Welcome to Catan!";

				messageView.setTitle(title);
				messageView.setMessage(outputStr);
				messageView.setController(this);
				messageView.showModal();

				getLoginView().closeModal();
				loginAction.execute();
			} else {
				/*
				 * If the login does not succeed, throw an error message stating
				 * invalid login credentials.
				 */
				String outputStr = "The inputed Username/Password was invalid. Try again.";
				String title = "Invalid User Login";

				messageView.setTitle(title);
				messageView.setMessage(outputStr);
				messageView.setController(this);
				messageView.showModal();
			}
		} catch (ServerResponseException e) {
			/*
			 * If the server throws an exception, i.e. the user is not connected
			 * to the server.
			 */
			String outputStr = "The inputed Username/Password was invalid. Try again.";
			String title = "Invalid User Login";

			messageView.setTitle(title);
			messageView.setMessage(outputStr);
			messageView.setController(this);
			messageView.showModal();

			e.printStackTrace();
		}

	}

	@Override
	public void register() {
		/*
		 * Grab register credentials from the LoginView and create a user
		 * credentials object.
		 */
		String registerUsername = this.getLoginView().getRegisterUsername();
		String registerPassword = this.getLoginView().getRegisterPassword();
		String registerPasswordRepeat = this.getLoginView()
				.getRegisterPasswordRepeat();
		UserCredentials registerCredentials = new UserCredentials(
				registerUsername, registerPassword);

		try {
			if (canRegister(registerCredentials, registerPasswordRepeat)
					&& server.Register(registerCredentials)) {
				/*
				 * If the register succeeded, throw a success statement and
				 * execute loginAction.
				 */
				String outputStr = "Thank you " + registerUsername
						+ " for registering for Catan.\n";
				String title = "Welcome to Catan!";

				UserPlayerInfo.getSingleton().setName(registerUsername);

				messageView.setTitle(title);
				messageView.setMessage(outputStr);
				messageView.setController(this);
				messageView.showModal();

				getLoginView().closeModal();
				loginAction.execute();
			} else {
				/*
				 * If the login does not succeed, throw an error message stating
				 * invalid register credentials.
				 */
				String outputStr = "The inputed username & password was invalid. Try again.";
				String title = "Invalid User Registration";

				messageView.setTitle(title);
				messageView.setMessage(outputStr);
				messageView.setController(this);
				messageView.showModal();
			}
		} catch (ServerResponseException e) {
			/*
			 * If the server throws an exception, i.e. the user is not connected
			 * to the server.
			 */
			String outputStr = "There already exists a user name: "
					+ registerCredentials.getUsername() + ".";
			String title = "Invalid User Registration";

			messageView.setTitle(title);
			messageView.setMessage(outputStr);
			messageView.setController(this);
			messageView.showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
	}

	private boolean canLogin(UserCredentials loginCredentials) {
		/*
		 * Check that all strings inside of loginCredentials are not null
		 */
		if (loginCredentials.getUsername() == null)
			return false;
		if (loginCredentials.getPassword() == null)
			return false;
		return true;
	}

	private boolean canRegister(UserCredentials registerCredentials,
			String passwordRepeat) {
		/*
		 * Registering a new user should reject the registration if the username
		 * is fewer than 3 or greater than seven characters, if the password is
		 * less than 5 characters long or is not made of allowed characters
		 * (alphanumerics, underscores, hyphens) or if the password verification
		 * entry doesn�t match the original.
		 */

		/*
		 * Check if the user name is not null and the user name length is
		 * greater than 3 but less than 7.
		 */
		if (registerCredentials.getUsername() == null)
			return false;
		if (registerCredentials.getUsername().length() < 3
				|| registerCredentials.getUsername().length() > 7)
			return false;
		/*
		 * Check if the password is not null, is equal to the repeated inputed
		 * password, the length of the password is greater than 5 and the
		 * password only contains valid characters (alphanumerics, underscore,
		 * and hyphens).
		 */
		if (registerCredentials.getPassword() == null)
			return false;
		if (!registerCredentials.getPassword().equals(passwordRepeat))
			return false;
		if (registerCredentials.getPassword().length() < 5)
			return false;
		if (!registerCredentials.getPassword().matches("[a-zA-Z0-9_-]+"))
			return false;

		return true;
	}
}
