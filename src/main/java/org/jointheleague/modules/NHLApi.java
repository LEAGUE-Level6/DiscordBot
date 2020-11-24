package org.jointheleague.modules;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.Example;
import org.jointheleague.modules.pojo.Person;
import org.jointheleague.modules.pojo.Position;
import org.jointheleague.modules.pojo.Roster;
import org.jointheleague.modules.pojo.Roster_;
import org.jointheleague.modules.pojo.Team;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;
import org.jointheleague.modules.pojo.apiExample.Article;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class NHLApi extends CustomMessageCreateListener {

	private final String apiKey = "";

	private final Gson gson = new Gson();

	private final String COMMAND = "!NHL";

	int NJD = 1;
	int NYI = 2;
	int NYR = 3;
	int PHI = 4;
	int PIT = 5;
	int BOS = 6;
	int BUF = 7;
	int MTL = 8;
	int OTT = 9;
	int TOR = 10;
	int CAR = 12;
	int FLA = 13;
	int TBL = 14;
	int WSH = 15;
	int CHI = 16;
	int DET = 17;
	int NSH = 18;
	int STL = 19;
	int CGY = 20;
	int COL = 21;
	int EDM = 22;
	int VAN = 23;
	int ANA = 24;
	int DAL = 25;
	int LAK = 26;
	int SJS = 28;
	int CBJ = 29;
	int MIN = 30;
	int WPG = 52;
	int ARI = 53;
	int VGK = 54;

	// int teamKey = 0;

	public NHLApi(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		if (event.getMessageContent().contains(COMMAND)) {

			// remove the command so we are only left with the search term
			String msg = event.getMessageContent().replaceAll(" ", "").replace("!NHL", "").replace("Roster", "");
			 event.getChannel().sendMessage("\n"+"test");
			if (msg.equals("")) {
				event.getChannel().sendMessage("Please put the teams abbreviation after the command");
			} else {
				 event.getChannel().sendMessage(" 	second test");
				String message = getTeam(msg);
				System.out.println(message);
				 event.getChannel().sendMessage(" 	third test");
				event.getChannel().sendMessage(message);
			}

		}
	}

	public String getTeam(String team) {
		// https://statsapi.web.nhl.com/api/v1/teams/16/stats
		int teamKey = 0;
		if (team.contains("NJD")) {
			teamKey = NJD;
		} else if (team.contains("NYI")) {
			teamKey = NYI;
		} else if (team.contains("NYR")) {
			teamKey = NYR;
		} else if (team.contains("PHI")) {
			teamKey = PHI;
		} else if (team.contains("PIT")) {
			teamKey = PIT;
		} else if (team.contains("BOS")) {
			teamKey = BOS;
		} else if (team.contains("BUF")) {
			teamKey = BUF;
		} else if (team.contains("MTL")) {
			teamKey = MTL;
		} else if (team.contains("OTT")) {
			teamKey = OTT;
		} else if (team.contains("TOR")) {
			teamKey = TOR;
		} else if (team.contains("CAR")) {
			teamKey = CAR;
		} else if (team.contains("FLA")) {
			teamKey = FLA;
		} else if (team.contains("TBL")) {
			teamKey = TBL;
		} else if (team.contains("WSH")) {
			teamKey = WSH;
		} else if (team.contains("CHI")) {
			teamKey = CHI;
		} else if (team.contains("DET")) {
			teamKey = DET;
		} else if (team.contains("NSH")) {
			teamKey = NSH;
		} else if (team.contains("STL")) {
			teamKey = STL;
		} else if (team.contains("CGY")) {
			teamKey = CGY;
		} else if (team.contains("COL")) {
			teamKey = COL;
		} else if (team.contains("EDM")) {
			teamKey = EDM;
		} else if (team.contains("VAN")) {
			teamKey = VAN;
		} else if (team.contains("ANA")) {
			teamKey = ANA;
		} else if (team.contains("DAL")) {
			teamKey = DAL;
		} else if (team.contains("LAK")) {
			teamKey = LAK;
		} else if (team.contains("SJS")) {
			teamKey = SJS;
		} else if (team.contains("CBJ")) {
			teamKey = CBJ;
		} else if (team.contains("MIN")) {
			teamKey = MIN;
		} else if (team.contains("WPG")) {
			teamKey = WPG;
		} else if (team.contains("ARI")) {
			teamKey = ARI;
		} else if (team.contains("VGK")) {
			teamKey = VGK;
		}
		// https://statsapi.web.nhl.com/api/v1/t/16?expand=team.roster
		String requestURL = "http://statsapi.web.nhl.com/api/v1/teams/" + teamKey + "/?expand=team.roster";
		System.out.println(requestURL);
		try {

			// the following code will probably be the same for your feature
			URL url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			JsonReader repoReader = Json.createReader(con.getInputStream());
			JsonObject userJSON = ((JsonObject) repoReader.read());
			con.disconnect();

			// turn the json response into a java object
			// you will need to create a java class that represents the response in
			// org.jointheleague.modules.pojo
			// you can use a tools like Postman and jsonschema2pojo.com to help with that

			// you can use postman to make the request and receive a response, then take
			// that and put it right into jsonschema2pojo.com
			// If using jsonschema2pojo.com, select Target Langue = java, Source Type =
			// JSON, Annotation Style = Gson
			Example example = gson.fromJson(userJSON.toString(), Example.class);

			// get the first article (these are just java objects now)
			List<Team> teams = example.getTeams();

			// get the title of the article
			Roster roster = new Roster();
			List<Roster_> nRoster_ = new ArrayList<Roster_>();
			// get the content of the article
			String message = "The roster for " + team + ": " + "\n";
			for (int i = 0; i < teams.size(); i++) {
				Roster r = teams.get(i).getRoster();
				nRoster_ = (r.getRoster());

				Person person = null;
				Position position = null;
				String jerseyNum = null;
				String fullName = null;
				String positionAbbrev = null;
				String p = null;
				String p1 = null;

				if (nRoster_ != null) {
					for (int k = 0; k < nRoster_.size(); k++) {
						Roster_ newRoster_ = nRoster_.get(k);
						person = newRoster_.getPerson();
						position = newRoster_.getPosition();
						jerseyNum = newRoster_.getJerseyNumber();
						fullName = person.getFullName();
						positionAbbrev = position.getAbbreviation();
						p = position.getType();
						p1 = position.getName();
						message += "Jersey Number: " + jerseyNum + " Name: " + fullName + " position: " + positionAbbrev
								+ p + p1 + "\n";

					}
				} else {
					System.out.println("roster_ is null");
				}

			}
			// send the message
			return message;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(requestURL);

		return requestURL;

	}

}
