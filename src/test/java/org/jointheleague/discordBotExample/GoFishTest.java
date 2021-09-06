package org.jointheleague.discordBotExample;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.jointheleague.modules.GoFish;
import org.jointheleague.modules.pojo.HelpEmbed;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.aksingh.owmjapis.api.APIException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class GoFishTest {
	
    private final String testChannelName = "test";
	private final GoFish goFish = new GoFish(testChannelName);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

	ArrayList<Integer> testCards = new ArrayList<Integer>();
	
	@Mock
	private MessageCreateEvent testEvent;
	
	@Mock
	private TextChannel textChannel;
	
	@Mock
	private MessageAuthor author;
	
	@Mock
	private GoFish mockFish;
	
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));
    }
	
	@AfterEach
    public void itShouldNotPrintToSystemOut() {
        String expected = "";
        String actual = outContent.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }
	
	@Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = GoFish.getCommand();

        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }
	
	@Test
    void itShouldHandleMessagesWithCommand()  {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(GoFish.getCommand(), "test");
        when(testEvent.getMessageContent()).thenReturn(GoFish.getCommand());
        when(testEvent.getChannel()).thenReturn(textChannel);
        when(testEvent.getMessageAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(869256021326049280L);
        
        //When
        try {
			goFish.handle(testEvent);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }
	/* Do I need this test?
	@Test
    void itShouldNotHandleMessagesWithoutCommand() {
        //Given
        String command = "";
        when(testEvent.getMessageContent()).thenReturn(command);
        when(testEvent.getMessageAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(869256021326049280L);
        //When
        try {
			goFish.handle(testEvent);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Then
        verify(textChannel, never()).sendMessage();
    }
*/
    @Test
    void itShouldHaveAHelpEmbed() {
        //Given

        //When
        HelpEmbed actualHelpEmbed = goFish.getHelpEmbed();

        //Then
        assertNotNull(actualHelpEmbed);
    }

    @Test
    void itShouldHaveTheCommandAsTheTitleOfTheHelpEmbed() {
        //Given

        //When
        String helpEmbedTitle = goFish.getHelpEmbed().getTitle();
        String command = GoFish.getCommand();

        //Then
        assertEquals(command, helpEmbedTitle);
    }
	/*
	@Test
	void shouldPickFirstPlayer() {
		when(testEvent.getMessageContent()).thenReturn(GoFish.getCommand());
	    when(testEvent.getMessageAuthor()).thenReturn(author);
	    when(author.getId()).thenReturn(869256021326049280L);
		goFish.pickFirstPlayer(testEvent);
		assertNotNull(goFish.firstPlayer);
	}
	*/
	@Test
	void shouldCreateDeck() {
		goFish.createDeck();
		assertEquals(goFish.getDeckSize(), 52);
	}
	
	@Test
	void shouldShuffleDeck() {
		ArrayList<Integer> newCards = goFish.shuffleDeck(testCards);
		assertNotEquals(testCards, newCards);
	}
	
	@Test
	void shouldDealCards() {
		goFish.shuffleDeck(testCards);
		goFish.dealCards();
		int playerAmount = goFish.getPlayerCardsSize();
		int botAmount = goFish.getBotCardsSize();
		assertEquals(playerAmount, 7);
		assertEquals(playerAmount, botAmount);
	}
	
	@Test
	void shouldSearchDeckAndFindCards() {
		ArrayList<Integer> deckToSearch = goFish.shuffleDeck(testCards);
		ArrayList<Integer> deckToAdd = new ArrayList<Integer>();
		int cardToSearch = 4;
		goFish.searchDeck(deckToSearch, cardToSearch, deckToAdd, testEvent);
		boolean found = goFish.found;
		assertEquals(found, true);
	}
	
	@Test
	void shouldSearchDeckAndFindNothing() {
		ArrayList<Integer> deckToSearch = goFish.shuffleDeck(testCards);
		ArrayList<Integer> deckToAdd = new ArrayList<Integer>();
		int cardToSearch = 100;
		goFish.searchDeck(deckToSearch, cardToSearch, deckToAdd, testEvent);
		boolean found = goFish.found;
		assertEquals(found, false);
	}
	
	@Test
	void shouldSayPlayerWins() {
		
     

	}
	
	@Test 
	void shouldSayBotWins(){
		
	}
	

}
