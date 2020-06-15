package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class GetTime extends CustomMessageCreateListener{
	public static final String COMMAND = "!time";
	public GetTime(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
			Clock clock = Clock.system(ZoneOffset.ofHours(-2));
			System.out.println(clock.instant());
		};
	}
