package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;
import org.omg.CORBA.portable.InputStream;

public class Name extends CustomMessageCreateListener {
	private static final String COMMAND = "!Name";
	public Name(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		 
	}
	
}