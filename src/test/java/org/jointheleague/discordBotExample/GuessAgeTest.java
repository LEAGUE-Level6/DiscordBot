package org.jointheleague.discordBotExample;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.BinaryTranslator;
import org.jointheleague.modules.GoFish;
import org.jointheleague.modules.GuessAge;
import org.jointheleague.modules.pojo.Age;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import net.aksingh.owmjapis.api.APIException;



class GuessAgeTest {
	
	GuessAge guess;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Mock
	private MessageCreateEvent testEvent;
	@Mock
	private TextChannel textChannel;
	
	@Mock
	private MessageAuthor author;
	
	@Mock
	GuessAge mockGuess;
	
	@BeforeEach 
	void setUp() { 
        MockitoAnnotations.openMocks(this);
        guess = new GuessAge(null);
        System.setOut(new PrintStream(outContent));


	}
	
	@Test
    void itShouldHaveACommand() {
        //Given

        //When
        String command = GuessAge.getCommand();
        //Then
        assertNotEquals("", command);
        assertNotEquals("!command", command);
        assertNotNull(command);
    }
	@Test
    void itShouldHandleMessagesWithCommand()  {
        //Given
        HelpEmbed helpEmbed = new HelpEmbed(GuessAge.getCommand(), "test");
        when(testEvent.getMessageContent()).thenReturn(GuessAge.getCommand());
        when(testEvent.getChannel()).thenReturn(textChannel);
        when(testEvent.getMessageAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(869256021326049280L);
        
        //When
        try {
			guess.handle(testEvent);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }
	
	@Test
	void shouldGuessAge() {
		//given
		 String name = "Grace";
		 int expectedAge = 63;
		 
		 //when
		 int actualAge = guess.getAgeWithName(name);
		//then
		 assertEquals(expectedAge, actualAge);
	}
	
}
