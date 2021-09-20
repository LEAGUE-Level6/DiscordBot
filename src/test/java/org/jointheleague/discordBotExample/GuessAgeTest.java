package org.jointheleague.discordBotExample;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.GoFish;
import org.jointheleague.modules.GuessAge;
import org.jointheleague.modules.pojo.Age;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;



class GuessAgeTest {
	
	GuessAge guess;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Mock
	private MessageCreateEvent testEvent;
   
	
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
