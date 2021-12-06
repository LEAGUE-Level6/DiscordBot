package org.jointheleague.discordBotExample;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.modules.BinaryTranslator;
import org.jointheleague.modules.GoFish;
import org.jointheleague.modules.pojo.HelpEmbed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.aksingh.owmjapis.api.APIException;

class BinaryTranslatorTest {
	
	BinaryTranslator bTranslate = new BinaryTranslator(null);
	 private final String testChannelName = "test";
	 private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	 private final PrintStream originalOut = System.out;
	@Mock
	private MessageCreateEvent testEvent;
	
	@Mock
	private TextChannel textChannel;
	
	@Mock
	private MessageAuthor author;
	
	@Mock
	private Random rand;
	
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
        HelpEmbed helpEmbed = new HelpEmbed(BinaryTranslator.getCommand(), "test");
        when(testEvent.getMessageContent()).thenReturn(BinaryTranslator.getCommand());
        when(testEvent.getChannel()).thenReturn(textChannel);
        when(testEvent.getMessageAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(869256021326049280L);
        
        //When
        try {
			bTranslate.handle(testEvent);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Then
        verify(textChannel, times(1)).sendMessage(anyString());
    }
	
	
	
	@Test
	void shouldConvertToBinary() {
		//given
		String decimal = "4";
		String expectedBinary = "00000100";
		String command = BinaryTranslator.getCommand() + "to 4";
		when(testEvent.getMessageContent()).thenReturn(command);
		//when
		String actualBinary = bTranslate.decimalToBinary(decimal);
		
		//then
		assertEquals(expectedBinary, actualBinary);
	}
	 
	@Test
	void shouldConvertToDecimal() {
		//given
		String binary ="100";
		int expectedDecimal = 4;
		String command = BinaryTranslator.getCommand() + "from 100"
				+ "";
		when(testEvent.getMessageContent()).thenReturn(command);
		//when
		int actualDecimal = bTranslate.binaryToDecimal(binary);
		//then
		assertEquals(expectedDecimal, actualDecimal);
	}
	
	@Test
	void shouldSendBinaryValue() {
		//given
		int decimal = 4;
		String expectedBinary = "00000100";
		String command = BinaryTranslator.getCommand() + " to 4"
				+ "";
		when(testEvent.getMessageContent()).thenReturn(command);
		when(testEvent.getChannel()).thenReturn(textChannel);
        when(testEvent.getMessageAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(869256021326049280L);
        
        //when
        try {
			bTranslate.handle(testEvent);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
        
        verify(textChannel, times(1)).sendMessage("Here's your binary number " + expectedBinary);
        
	} 
	
	@Test
	void shouldSendDecimalValue() {
		//given
		String binary = "100";
		int expectedDec = 4;
		String command = BinaryTranslator.getCommand() + " from 100";
		when(testEvent.getMessageContent()).thenReturn(command);
		when(testEvent.getChannel()).thenReturn(textChannel);
        when(testEvent.getMessageAuthor()).thenReturn(author);
        when(author.getId()).thenReturn(869256021326049280L);
        
		//when
        try {
			bTranslate.handle(testEvent);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		} 
        
        verify(textChannel, times(1)).sendMessage("Here's your decimal number " + expectedDec);
        
		
	} 
	

	 
	
}
