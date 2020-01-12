package org.jointheleague.modules;

import org.javacord.api.event.message.MessageCreateEvent;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAImage;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import net.aksingh.owmjapis.api.APIException;

public class WolframAlpha extends CustomMessageCreateListener{
	private static String appId = "6HQJ4A-PK99X3UT2A";
	private static WAEngine engine = new WAEngine();
	static {
		engine.setAppID(appId);
		//engine.addFormat("plaintext");
		engine.addFormat("image");
	}
	static WAQueryResult createQuery(String userInput) {
		WAQuery query = engine.createQuery();
		query.setInput(userInput);
		try {
			WAQueryResult result = engine.performQuery(query);
			return result;
		} catch (WAException e) {
			e.printStackTrace();
		}
		return null;		
	}
	public WolframAlpha(String channelName){
		super(channelName);
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		if(event.getMessageContent().startsWith("!alpha")) {
			String query = event.getMessageContent().substring(6);
			WAQueryResult user = createQuery(query);
			for (WAPod pod : user.getPods()) {
                if (!pod.isError()) {
                    System.out.println(pod.getTitle());
                    String title = "**" + pod.getTitle() + "**";
                    
                    event.getChannel().sendMessage(title);
                    System.out.println("------------");
                    for (WASubpod subpod : pod.getSubpods()) {
                        for (Object element : subpod.getContents()) {
                            if (element instanceof WAPlainText) {
                    
                            	event.getChannel().sendMessage(((WAPlainText) element).getText());
                                System.out.println(((WAPlainText) element).getText());
                                System.out.println("");
                            }else if(element instanceof WAImage) {
                            	WAImage image = (WAImage)element;
                            	System.out.println(image.getURL());
                            	event.getChannel().sendMessage(image.getURL());
                            }else {
                            	System.out.println(element.getClass());
                            }
                        }
                    }
                    System.out.println("");
                }
            }
		}else {
			
		}
	}
}
