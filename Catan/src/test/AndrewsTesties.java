package test;

import static org.junit.Assert.*;

import org.junit.Test;

import client.model.ClientModel;
import shared.model.MessageLine;
import shared.model.MessageList;

public class AndrewsTesties {

	@Test
	public void test() {
		ClientModel clientModel = new ClientModel();
		MessageLine[] messageLines = new MessageLine[2];
		messageLines[0] = new MessageLine("Hi there", "Sam");
		messageLines[1] = new MessageLine("Hey man", "Gus");
		clientModel.setChat(new MessageList(messageLines));
		assertTrue(clientModel.getChat().getLines()[0].getMessage().equals(
				"Hi there"));

		MessageLine newLine = new MessageLine("This is Andrew", "Andrew");
		clientModel.getChat().addLine(newLine);
		assertTrue(clientModel.getChat().getLines()[2].getMessage().equals(
				"This is Andrew"));

	}

}
