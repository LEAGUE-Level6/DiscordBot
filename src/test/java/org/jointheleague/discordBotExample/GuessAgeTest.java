package org.jointheleague.discordBotExample;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;



import org.jointheleague.modules.GuessAge;
import org.jointheleague.modules.pojo.Age;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;



class GuessAgeTest {
	
	GuessAge guess;
	
	
   
	
	@BeforeEach
	void setUp() {
        MockitoAnnotations.openMocks(this);
        guess = new GuessAge(null);


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
