package org.jointheleague.discord_bot_example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author keithgroves
 *
 */
public class Launcher {
	public static void main(String[] args) {
		//FileInputStream serviceAccount;
		//try {
			//serviceAccount = new FileInputStream("src/main/java/org/jointheleague/modules/key.json");
			//FirebaseOptions options = new FirebaseOptions.Builder()
					  //.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  //.setDatabaseUrl("https://leaguelinks-9cafa.firebaseio.com")
					  //.build();
					
					//FirebaseApp.initializeApp(options);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		
		if (args.length == 0) {
			args = new String[] { "default" };
		}
		new Launcher().launch(args);
	}

	public void launch(String[] args) {
		Map<String, BotInfo> map = Utilities.loadBotsFromJson();
		for (String name : args) {
			BotInfo n = map.get(name);
			new Bot(n.getToken(), n.getChannel()).connect();
		}
	}
}
