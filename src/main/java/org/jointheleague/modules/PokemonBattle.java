package org.jointheleague.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.jointheleague.modules.pojo.Pokemon.PokemonWrapper;
import org.jointheleague.modules.pojo.Pokemon.types.DoubleDamageTo;
import org.jointheleague.modules.pojo.Pokemon.types.HalfDamageTo;
import org.jointheleague.modules.pojo.Pokemon.types.NoDamageTo;
import org.jointheleague.modules.pojo.Pokemon.types.PokemonType;
import org.jointheleague.modules.pojo.apiExample.ApiExampleWrapper;

import com.google.gson.Gson;

import net.aksingh.owmjapis.api.APIException;

public class PokemonBattle extends CustomMessageCreateListener {
	
//private final String apiKey = "59ac01326c584ac0a069a29798794bec";
private static final String COMMAND = "!pokemon";
private static final String COMMAND2="use";
private final Gson gson = new Gson();
List<String> movesList=new ArrayList<String>();
boolean comma;
boolean hasMove;
boolean battleOver=true;
String chars;
String move;
String pokemon1;
int pokemon1Health;
String pokemon2;
int pokemon2Health;
int progress;
Random rand=new Random();
List<String> randomList=new ArrayList<String>();
List<String> randomList2=new ArrayList<String>();
int random;
int players;
int turn=1;
double power1;
double power2;
String type1;
String type2;
int speed1;
int speed2;
double damageMultiplier;
//Random hpMultiplier=new Random();
//int hpMultiplierInt;
String[] pokemon1Types=new String[2];
String[] pokemon2Types=new String[2];

public PokemonBattle (String channelName){
super(channelName);
helpEmbed=new HelpEmbed("COMMAND", "");
}

@Override
public void handle(MessageCreateEvent event) throws APIException {
	// TODO Auto-generated method stub
	if(event.getMessageContent().contains(COMMAND)&&battleOver==true) {
		//remove the command so we are only left with the search term
		String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND, "");

		if (msg.equals("")) {
			event.getChannel().sendMessage("Please put a word after the command");
		} else if(players==0) {
			players=1;
			pokemon1=msg;
			//hpMultiplierInt=hpMultiplier.nextInt(2);
			//hpMultiplierInt+=2;
			pokemon1Health=getHP(pokemon1);
			speed1=getSpeed(pokemon1);
			System.out.println(pokemon1Health);
			System.out.println("Speed="+speed1);
			PokemonWrapper moves=getMoves(msg);
			pokemon1Types=getTypePokemon(pokemon1);
			//moves.getMoves().get(0);
			for(int i=0; i<moves.getMoves().size(); i++) {
			//event.getChannel().sendMessage(moves.getMoves().get(i).toString());
			comma=false;
			progress=0;
			move="";
			chars="";
			while(!comma) {
				chars+=moves.getMoves().get(i).toString().charAt(12+progress);
			if(chars.equals(",")) {
				comma=true;
			}
			else {
				move+=chars;
				chars="";
				progress++;
			}
			}
			//System.out.println(move);
			try {
				double j=getMovePower(move);
				movesList.add(move);
			}
			catch (Exception e) {
				System.out.println("no move power");
			}
			}
			for(int i=0; i<4; i++) {
			random=rand.nextInt(movesList.size());
			randomList.add(movesList.get(random));
			System.out.println(movesList.get(random));
			movesList.remove(random);
			}
			event.getChannel().sendMessage("You selected "+msg+". Your moves are "+randomList.get(0)+", "+randomList.get(1)+", "+randomList.get(2)+" and "+randomList.get(3)+ ". Waiting for second player.");
		}
		else if(players==1) {
			players=2;
			pokemon2=msg;
			//hpMultiplierInt=hpMultiplier.nextInt(2);
			//hpMultiplierInt+=2;
			pokemon2Health=getHP(pokemon2);
			int speed2=getSpeed(pokemon2);
			System.out.println(pokemon2Health);
			System.out.println("Speed="+speed2);
			PokemonWrapper moves=getMoves(msg);
			pokemon2Types=getTypePokemon(pokemon2);
			//pokemon1Types=getTypePokemon(pokemon1);
			//moves.getMoves().get(0);
			movesList.clear();
			for(int i=0; i<moves.getMoves().size(); i++) {
			//event.getChannel().sendMessage(moves.getMoves().get(i).toString());
			comma=false;
			progress=0;
			move="";
			chars="";
			while(!comma) {
				chars+=moves.getMoves().get(i).toString().charAt(12+progress);
			if(chars.equals(",")) {
				comma=true;
			}
			else {
				move+=chars;
				chars="";
				progress++;
			}
			}
			try {
				double j=getMovePower(move);
				movesList.add(move);
			}
			catch (Exception e) {
				System.out.println("no move power");
			}
			}
			for(int i=0; i<4; i++) {
				random=rand.nextInt(movesList.size());
				randomList2.add(movesList.get(random));
				System.out.println(movesList.get(random));
				movesList.remove(random);
				}
				event.getChannel().sendMessage("You selected "+msg+". Your moves are "+randomList2.get(0)+", "+randomList2.get(1)+", "+randomList2.get(2)+" and "+randomList2.get(3)+ ". The battle has begun.");
				System.out.println("Speed="+speed2);
				if(speed1>speed2) {
					turn=1;
				}
				else if(speed1<speed2) {
					turn=2;
				}
				else {
					turn=1;
				}
		}
	}
	else if(event.getMessageContent().toLowerCase().contains(COMMAND2) && players==2) {
		battleOver=false;
		String msg = event.getMessageContent().replaceAll(" ", "").replace(COMMAND2, "").toLowerCase();
		if (msg.equals("")) {
			event.getChannel().sendMessage("Please put a word after the command");
		}
		else if(msg.contains(pokemon1) && turn==1) {
			hasMove=false;
			System.out.println(msg);
				String msg2=msg.replace(pokemon1, "");
				System.out.println(msg2);
				for(int i=0; i<randomList.size(); i++) {
					if(randomList.get(i).equals(msg2)) {
						hasMove=true;
					}
				}
				if(hasMove==true) {
				turn=2;
				power1=getMovePower(msg2);
				type1=getMoveType(msg2);
				damageMultiplier=getDamageRelations(type1);
				power1=power1*=damageMultiplier;
				pokemon2Health-=power1;
				event.getChannel().sendMessage(pokemon1+" did "+power1+" damage to "+pokemon2+". "+pokemon2+" now has "+pokemon2Health+" health left.");
				if(pokemon2Health<=0) {
					event.getChannel().sendMessage(pokemon1+" has won the battle.");
					battleOver=true;
					players=0;
					movesList.clear();
					randomList.clear();
					randomList2.clear();
				}
				}
				else {
					event.getChannel().sendMessage("Use a real move, or a move that your pokemon knows");	
				}
		}
		else if(msg.contains(pokemon2) && turn==2) {
			hasMove=false;
				String msg2=msg.replace(pokemon2, "");
				for(int i=0; i<randomList2.size(); i++) {
					if(randomList2.get(i).equals(msg2)) {
						hasMove=true;
					}
				}
				if(hasMove==true) {
					turn=1;
				power2=getMovePower(msg2);
				type2=getMoveType(msg2);
				damageMultiplier=getDamageRelations(type2);
				power2=power2*=damageMultiplier;
				pokemon1Health-=power2;
				event.getChannel().sendMessage(pokemon2+" did "+power2+" damage to "+pokemon1+". "+pokemon1+" now has "+pokemon1Health+" health left.");
				if(pokemon1Health<=0) {
					event.getChannel().sendMessage(pokemon2+" has won the battle.");
					battleOver=true;
					players=0;
					movesList.clear();
					randomList.clear();
					randomList2.clear();
				}
				}
				else {
					event.getChannel().sendMessage("Use a real move, or a move that your pokemon knows");	
				}
		}
		else if(msg.contains(pokemon1) && turn==2) {
			event.getChannel().sendMessage("It is not "+pokemon1+"'s turn.");
		}
		else if(msg.contains(pokemon2) && turn==1) {
			event.getChannel().sendMessage("It is not "+pokemon2+"'s turn.");
		}
		}
	}
public PokemonWrapper getMoves(String pokemon) {
			
			//create the request URL (can be found in the documentation)
			String requestURL = "https://pokeapi.co/api/v2/pokemon/" +
			          pokemon+"/";
			 
			try {
				
				//the following code will probably be the same for your feature
				URL url = new URL(requestURL);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestProperty("User-Agent", "Jake");
				//System.out.println("open");
				con.setRequestMethod("GET");
				JsonReader repoReader = Json.createReader(con.getInputStream());
				//System.out.println("getinput");
			    JsonObject userJSON = ((JsonObject) repoReader.read());
			    con.disconnect();
			    
			    PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
			    return pokemonWrapper;
			}
			catch (MalformedURLException e) {
				System.out.println("url");
				e.printStackTrace();
			} catch (ProtocolException e) {
				System.out.println("prot");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("io");
				e.printStackTrace();
			}
			return null;
}
/*public PokemonWrapper getMovesUrl(String url1){
	url1.replaceFirst("%7D", "");
	String requestURL = url1;
	
	try {
		
		//the following code will probably be the same for your feature
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Jake");
		System.out.println("open");
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		System.out.println("getinput");
	    JsonObject userJSON = ((JsonObject) repoReader.read());
	    con.disconnect();
	    
	    PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
	    return pokemonWrapper;
	}
	catch (MalformedURLException e) {
		System.out.println("url");
		e.printStackTrace();
	} catch (ProtocolException e) {
		System.out.println("prot");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io");
		e.printStackTrace();
	}
	return null;
	
}*/
public int getHP(String pokemon) {
	String requestURL = "https://pokeapi.co/api/v2/pokemon/" +
	          pokemon+"/";
	try {
		
		//the following code will probably be the same for your feature
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Jake");
		//System.out.println("open");
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		//System.out.println("getinput");
	    JsonObject userJSON = ((JsonObject) repoReader.read());
	    con.disconnect();
	   // int hp=userJSON.getInt("hp");
	    JsonArray hp=userJSON.getJsonArray("stats");
	   System.out.println(hp.get(0));
	   int hpint=hp.get(0).asJsonObject().getInt("base_stat");
	   // PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
	    return hpint*3;
	}
	catch (MalformedURLException e) {
		System.out.println("url");
		e.printStackTrace();
	} catch (ProtocolException e) {
		System.out.println("prot");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io");
		e.printStackTrace();
	}
	return 0;
}
public String[] getTypePokemon(String pokemon) {
	String requestURL = "https://pokeapi.co/api/v2/pokemon/" +
	          pokemon+"/";
	JsonArray pokemonTypes;
	JsonObject pokemonType1;
	String pokemonType1Name;
	JsonObject pokemonType2;
	String pokemonType2Name;
	String[] pokemonTypesArray=new String[2];
		
	try {
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Jake");
		//System.out.println("open");
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		//System.out.println("getinput");
	    JsonObject userJSON = ((JsonObject) repoReader.read());
	    con.disconnect();
	    pokemonTypes=userJSON.getJsonArray("types");
	   pokemonType1=pokemonTypes.get(0).asJsonObject().getJsonObject("type");
	   pokemonType1Name=pokemonType1.getString("name");
	   System.out.println(pokemonType1Name);
	   pokemonTypesArray[0]=pokemonType1Name;
		try {
	   pokemonType2=pokemonTypes.get(1).asJsonObject().getJsonObject("type");
	   pokemonType2Name=pokemonType2.getString("name");
	   System.out.println(pokemonType2Name);
	   pokemonTypesArray[1]=pokemonType2Name;
	   }
	   catch (IndexOutOfBoundsException i) {
		   System.out.println("outOfBounds");
	   
	 //  pokemonTypesArray[1]=pokemonType2;
	   }
	// PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
	    return pokemonTypesArray;
	}
	catch (MalformedURLException e) {
		System.out.println("url");
		e.printStackTrace();
	} catch (ProtocolException e) {
		System.out.println("prot");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io");
		e.printStackTrace();
	}
	return null;
}
public double getMovePower(String move) {
	String requestURL = "https://pokeapi.co/api/v2/move/" +
	          move+"/";
	try {
		
		//the following code will probably be the same for your feature
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Jake");
		//System.out.println("open");
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		//System.out.println("getinput");
	    JsonObject userJSON = ((JsonObject) repoReader.read());
	    con.disconnect();
	    double power=userJSON.getInt("power");
	  //  System.out.println(power);
	   // PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
	    return power;
	}
	catch (MalformedURLException e) {
		System.out.println("url");
		e.printStackTrace();
	} catch (ProtocolException e) {
		System.out.println("prot");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io");
		e.printStackTrace();
	}
	return 0;
}
public String getMoveType(String move) {
	String requestURL = "https://pokeapi.co/api/v2/move/" +
	          move+"/";
	try {
	URL url = new URL(requestURL);
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	con.setRequestProperty("User-Agent", "Jake");
	//System.out.println("open");
	con.setRequestMethod("GET");
	JsonReader repoReader = Json.createReader(con.getInputStream());
	//System.out.println("getinput");
    JsonObject userJSON = ((JsonObject) repoReader.read());
    con.disconnect();
    JsonObject moveType=userJSON.getJsonObject("type");
    String moveTypeName=moveType.getString("name");
    System.out.println(moveTypeName);
// PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
    
    return moveTypeName;
}
catch (MalformedURLException e) {
	System.out.println("url");
	e.printStackTrace();
} catch (ProtocolException e) {
	System.out.println("prot");
	e.printStackTrace();
} catch (IOException e) {
	System.out.println("io");
	e.printStackTrace();
}
	return null;
}
public double getDamageRelations(String moveType) {
	String requestURL = "https://pokeapi.co/api/v2/type/" +
	          moveType+"/";
	try {
	URL url = new URL(requestURL);
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	con.setRequestProperty("User-Agent", "Jake");
	//System.out.println("open");
	con.setRequestMethod("GET");
	JsonReader repoReader = Json.createReader(con.getInputStream());
	//System.out.println("getinput");
    JsonObject userJSON = ((JsonObject) repoReader.read());
    con.disconnect();
    damageMultiplier=1;
    PokemonType pokemonType=gson.fromJson(userJSON.toString(), PokemonType.class);
    //JsonArray damageRelations=userJSON.getJsonArray("damage_relations");
   // JsonArray doubleDamage=damageRelations.getJsonArray(1);
    List<DoubleDamageTo> doubleDamageToTypes = pokemonType.getDamageRelations().getDoubleDamageTo();
   // for(int i=0; i<doubleDamageToTypes.size(); i++) {
    //	JsonObject doubleDamageTo=doubleDamage.get(i).asJsonObject().getJsonObject("name");
   // 	doubleDamageToTypes.add(doubleDamageTo);
   // }
    //JsonArray halfDamage=damageRelations.getJsonArray(3);
    //JsonArray halfDamageToTypes = null;
    List<HalfDamageTo> halfDamageToTypes = pokemonType.getDamageRelations().getHalfDamageTo();
    //for(int i=0; i<halfDamage.size(); i++) {
    //	JsonObject halfDamageTo=halfDamage.get(i).asJsonObject().getJsonObject("name");
    //	halfDamageToTypes.add(halfDamageTo);
  //  }
   // JsonArray noDamage=damageRelations.getJsonArray(5);
   // JsonArray noDamageToTypes = null;
    List<NoDamageTo> noDamageToTypes = pokemonType.getDamageRelations().getNoDamageTo();
    //for(int i=0; i<noDamage.size(); i++) {
    //	JsonObject noDamageTo=noDamage.get(i).asJsonObject().getJsonObject("name");
    //	noDamageToTypes.add(noDamageTo);
    //}
    
    
   if(turn==2) {
	   //if pokemon 1 attacks
    	for(int i=0; i<doubleDamageToTypes.size(); i++) {
    	if(pokemon2Types[0].equals(doubleDamageToTypes.get(i).getName())) {
    		damageMultiplier*=2;
    	}
    	}
    	for(int i=0; i<halfDamageToTypes.size(); i++) {
    		if(pokemon2Types[0].equals(halfDamageToTypes.get(i).getName())) {
    			damageMultiplier*=.5;
    		}
    	}
    	for(int i=0; i<noDamageToTypes.size(); i++) {
    		if(pokemon2Types[0].equals(noDamageToTypes.get(i).getName())) {
    			damageMultiplier*=0;
    		}
    	}
    	try {
    	for(int i=0; i<doubleDamageToTypes.size(); i++) {
        	if(pokemon2Types[1].equals(doubleDamageToTypes.get(i).getName())) {
        		damageMultiplier*=2;
        	}
        	}
        	for(int i=0; i<halfDamageToTypes.size(); i++) {
        		if(pokemon2Types[1].equals(halfDamageToTypes.get(i).getName())) {
        			damageMultiplier*=.5;
        		}
        	}
        	for(int i=0; i<noDamageToTypes.size(); i++) {
        		if(pokemon2Types[1].equals(noDamageToTypes.get(i).getName())) {
        			damageMultiplier*=0;
        		}
        	}
    	}
    	catch(Exception e) {
    		
    	}
   }
   else if(turn==1) {
	   //if pokemon 2 attacks
	   for(int i=0; i<doubleDamageToTypes.size(); i++) {
	    	if(pokemon1Types[0].equals(doubleDamageToTypes.get(i).getName())) {
	    		damageMultiplier*=2;
	    	}
	    	}
	    	for(int i=0; i<halfDamageToTypes.size(); i++) {
	    		if(pokemon1Types[0].equals(halfDamageToTypes.get(i).getName())) {
	    			damageMultiplier*=.5;
	    		}
	    	}
	    	for(int i=0; i<noDamageToTypes.size(); i++) {
	    		if(pokemon1Types[0].equals(noDamageToTypes.get(i).getName())) {
	    			damageMultiplier*=0;
	    		}
	    	}
	    	try {
	    	for(int i=0; i<doubleDamageToTypes.size(); i++) {
	        	if(pokemon1Types[1].equals(doubleDamageToTypes.get(i).getName())) {
	        		damageMultiplier*=2;
	        	}
	        	}
	        	for(int i=0; i<halfDamageToTypes.size(); i++) {
	        		if(pokemon1Types[1].equals(halfDamageToTypes.get(i).getName())) {
	        			damageMultiplier*=.5;
	        		}
	        	}
	        	for(int i=0; i<noDamageToTypes.size(); i++) {
	        		if(pokemon1Types[1].equals(noDamageToTypes.get(i).getName())) {
	        			damageMultiplier*=0;
	        		}
	        	}
	    	}
	    	catch(Exception e) {
	    		
	    	}
   }
// PokemonWrapper pokemonWrapper=gson.fromJson(userJSON.toString(), PokemonWrapper.class);
    System.out.println("damageMultiplier="+damageMultiplier);
    return damageMultiplier;
}
catch (MalformedURLException e) {
	System.out.println("url");
	e.printStackTrace();
} catch (ProtocolException e) {
	System.out.println("prot");
	e.printStackTrace();
} catch (IOException e) {
	System.out.println("io");
	e.printStackTrace();
}
	return 0;
}
public int getSpeed(String pokemon) {
	String requestURL = "https://pokeapi.co/api/v2/pokemon/" +
	          pokemon+"/";
	try {
		
		//the following code will probably be the same for your feature
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Jake");
		//System.out.println("open");
		con.setRequestMethod("GET");
		JsonReader repoReader = Json.createReader(con.getInputStream());
		//System.out.println("getinput");
	    JsonObject userJSON = ((JsonObject) repoReader.read());
	    con.disconnect();
	    JsonArray stats=userJSON.getJsonArray("stats");
	   System.out.println(stats.get(0));
	   int speedInt=stats.get(5).asJsonObject().getInt("base_stat");
	    return speedInt;
	}
	catch (MalformedURLException e) {
		System.out.println("url");
		e.printStackTrace();
	} catch (ProtocolException e) {
		System.out.println("prot");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("io");
		e.printStackTrace();
	}
	return 0;
}
}
