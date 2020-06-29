package org.jointheleague.modules;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

import java.awt.Color;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GetTime extends CustomMessageCreateListener{
	public static final String COMMAND = "!time";
	public GetTime(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
			String text = event.getMessageContent();
			text = text.trim();
			if(text.equals(COMMAND)) {
				String time = LocalTime.now(ZoneId.of("America/Los_Angeles")).toString();
				time = time.substring(time.indexOf('T')+1, time.indexOf('.'));
				new MessageBuilder()
				.setEmbed(new EmbedBuilder()
						.setTitle("Local Time")
						.setDescription(time)
						.setFooter("You can get the time of anywhere by !time <offset from GMT>"))
				.send(event.getChannel());
			}else if(text.startsWith(COMMAND)) {
				try {
					text = text.substring(text.indexOf(" ")+1);
					ZoneId id = ZoneId.ofOffset("GMT", ZoneOffset.of(text));
					String time = LocalTime.now(id).toString();
					time = time.substring(time.indexOf('T')+1, time.indexOf('.'));
					new MessageBuilder()
					.setEmbed(new EmbedBuilder()
							.setTitle("Time in "+id.getId())
							.setDescription(time)
							.setFooter("You can try offestting by hours and minutes like +00:20 or -10:45"))
					.send(event.getChannel());
				}catch(DateTimeException e){
					new MessageBuilder()
					.setEmbed(new EmbedBuilder()
						.setTitle("Error:")
						.setDescription('"'+e.getMessage()+'"')
						.addField("Troubleshooting", "Maybe you inputted the offset wrong? \n"
								+ "You must have it in one of these formats, with + or - before it: \n"
								+ "h, hh, hh:mm \n"
								+ "**h:mm does not work**")
						.setColor(Color.RED))
					.send(event.getChannel());
				}
				
			}
			
		}
	}
