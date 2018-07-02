import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestProgramCapitalAndLargestCity
{
	String webServicesURL="http://services.groupkt.com/state/get/USA/all";
	
	/*
	Verify that when we enter a State abbrevation, the corresponding capital city and largest city is
	returned
	*/
	@Test
    public void testOne() 
	{
		ProgramCapitalAndLargestCity programObject = new ProgramCapitalAndLargestCity();
		String userInput="AK";
		String expectedCapitalCity="Juneau";
		String exptedLargestCity="Anchorage";
		String rawResponse = programObject.getRawResponse(webServicesURL);
		String actualCapitalCity = programObject.getCapitalCity(rawResponse,userInput);
		Assert.assertEquals("Expected capital city was not equal to actual capital city",expectedCapitalCity,actualCapitalCity);
		String actualLargestCity = programObject.getLargestCity(rawResponse,userInput);
		Assert.assertEquals("Expected largest city was not equal to actual largest city",exptedLargestCity,actualLargestCity);
	}
	
	/*
	Verify that when we enter a State full name, the corresponding capital city and largest city is
	returned
	*/
	@Test
    public void testTwo() 
	{
		ProgramCapitalAndLargestCity programObject = new ProgramCapitalAndLargestCity();
		String userInput="Alaska";
		String expectedCapitalCity="Juneau";
		String exptedLargestCity="Anchorage";
		String rawResponse = programObject.getRawResponse(webServicesURL);
		String actualCapitalCity = programObject.getCapitalCity(rawResponse,userInput);
		Assert.assertEquals("Expected capital city was not equal to actual capital city",expectedCapitalCity,actualCapitalCity);
		String actualLargestCity = programObject.getLargestCity(rawResponse,userInput);
		Assert.assertEquals("Expected largest city was not equal to actual largest city",exptedLargestCity,actualLargestCity);
	}
	
	/*
	We enter a State abbrevation. This State does Not have a largest_city record. The appropriate message 
	"largest_city is Not present for this record" should be displayed
	*/
	@Test
    public void testThree() throws InterruptedException
	{
		String expectedMessage = "largest_city is Not present for this record";
		ProgramCapitalAndLargestCity programObject = new ProgramCapitalAndLargestCity();
		String userInput="VI";
		String rawResponse = programObject.getRawResponse(webServicesURL);
		//Create a new stream to hold the output
		 ByteArrayOutputStream baosObj = new ByteArrayOutputStream();
		 PrintStream newPsObj = new PrintStream(baosObj);
		 PrintStream oldPsObj = System.out;
		 System.setOut(newPsObj);
		//Calling the largest city function
		String actualLargestCity = programObject.getLargestCity(rawResponse,userInput);
		//Polling for 5seconds for the message
		boolean isExpectedMsgPresent = false;
		for(int count=0; count<=4; count++)
		{
		 String textInCurrentStream = baosObj.toString();
		 if( textInCurrentStream.contains(expectedMessage) )
		 {
			 isExpectedMsgPresent = true;
			 break;
		 }
		 Thread.sleep(1000);
		}
		Assert.assertTrue("Expted msg is not present int console->"+expectedMessage,isExpectedMsgPresent);
		//Reset the earlier output stream
		System.out.flush();
		System.setOut(oldPsObj);	
	}
	
	
	/*
	We enter a State full name. This State does Not have a largest_city record. The appropriate message 
	"largest_city is Not present for this record" should be displayed
    */
	@Test
    public void testFour() throws InterruptedException
	{
		String expectedMessage = "largest_city is Not present for this record";
		ProgramCapitalAndLargestCity programObject = new ProgramCapitalAndLargestCity();
		String userInput="U.S. Virgin Islands";
		String rawResponse = programObject.getRawResponse(webServicesURL);
		//Create a new stream to hold the output
		 ByteArrayOutputStream baosObj = new ByteArrayOutputStream();
		 PrintStream newPsObj = new PrintStream(baosObj);
		 PrintStream oldPsObj = System.out;
		 System.setOut(newPsObj);
		//Calling the largest city function
		String actualLargestCity = programObject.getLargestCity(rawResponse,userInput);
		//Polling for 5seconds for the message
		boolean isExpectedMsgPresent = false;
		for(int count=0; count<=4; count++)
		{
		 String textInCurrentStream = baosObj.toString();
		 if( textInCurrentStream.contains(expectedMessage) )
		 {
			 isExpectedMsgPresent = true;
			 break;
		 }
		 Thread.sleep(1000);
		}
		Assert.assertTrue("Expted msg is not present int console->"+expectedMessage,isExpectedMsgPresent);
		//Reset the earlier output stream
		System.out.flush();
		System.setOut(oldPsObj);	
	}
	
		
		
	/*
	We enter a non-existing US state named "noSuchState". The program should return null for both the functions
	*/
	@Test
    public void testFive() 
	{
		String userInput="noSuchState";
		ProgramCapitalAndLargestCity programObject = new ProgramCapitalAndLargestCity();
		String rawResponse = programObject.getRawResponse(webServicesURL);
		String actualCapitalCity = programObject.getCapitalCity(rawResponse,userInput);
		Assert.assertEquals("Expected capital city should return null",null,actualCapitalCity);
		String actualLargestCity = programObject.getLargestCity(rawResponse,userInput);
		Assert.assertEquals("Expected largest city should return null",null,actualLargestCity);
	}
	
	
}