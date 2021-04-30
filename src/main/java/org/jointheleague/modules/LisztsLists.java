package org.jointheleague.modules;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;

import net.aksingh.owmjapis.api.APIException;

public class LisztsLists extends CustomMessageCreateListener {
	private static final String command = "!lisztlist";
	public LisztsLists(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if(event.getMessageContent().contains("!lisztlist")) {
			int r = new Random().nextInt(7);
			if(r == 0) {
				event.getChannel().sendMessage("Liszt’s father was strict about him practicing with a metronome, which might be one reason Liszt was well-known for his ability to keep absolute tempo.");
			}
			if(r == 1) {
				event.getChannel().sendMessage("In addition to his father knowing the Classical greats, Liszt himself studied with Carl Czerny as a boy. Carl Czerny was one of Beethoven’s best students, and was a renowned piano teacher.");
			}
			if(r == 2) {
				event.getChannel().sendMessage("Liszt was Elvis before Elvis was Elvis. They called it “Lisztomania” – women would faint and go into a frenzy when he performed, so much so that local doctors thought it was an epidemic of mental illness.");
			}
			if(r == 3) {
				event.getChannel().sendMessage("Liszt’s fame allowed him to fill concert halls with his solo performances, and he elevated the piano as a solo instrument in the concert hall as well.");
			}
			if(r == 4) {
				event.getChannel().sendMessage("Because of his motivation, drive and hard work, Liszt built up an unparalleled technical prowess at the piano. ");
			}
			if(r == 5) {
				event.getChannel().sendMessage("Liszt was such an intense piano player – loud enough to fill a recital hall on his own – that he would break piano strings while playing.");
			}
			if(r == 6) {
				event.getChannel().sendMessage("In addition to being virtuosic and loud, Liszt created an entire genre of music – the symphonic poem.");
			}
			if(r == 7) {
				event.getChannel().sendMessage("Liszt was known for being generous to friends and family.");
			}


		}
		
		
	}

}
