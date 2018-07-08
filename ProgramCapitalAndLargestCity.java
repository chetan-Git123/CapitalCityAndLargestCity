import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.MalformedURLException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


class ProgramCapitalAndLargestCity {
 public static void main(String args[]) {
  /* Driver Code */
  do {
   //Declacarations
   String webServicesURL = "http://services.groupkt.com/state/get/USA/all";
   String largestCityAttribute = "largest_city";
   String capitalCityAttribute = "capital";

   String userInput = new ProgramCapitalAndLargestCity().takeUserInput();
   System.out.println("User input is->" + userInput);
   if (userInput.equals("Q")) {
    System.out.println("Exiting the program as per user request");
    break;
   }
   
   //Validate user input
   boolean isUserInputValid = new ProgramCapitalAndLargestCity().validateUserInput(userInput );
   if(isUserInputValid==false)
   {
	   System.out.println("User input is invalid.");
	   System.out.println("######\n");
	   continue;
   }

   //Get the raw response
   String rawResponse = new ProgramCapitalAndLargestCity().getRawResponse(webServicesURL);
   boolean IsResponseStringEmpty = "".equals(rawResponse);
   if (IsResponseStringEmpty == true) {
    System.out.println("Response from the webservice is empyt. We are exiting the program");
    break;
   }

   //Get the largest city
   String largestCity = new ProgramCapitalAndLargestCity().getCity(rawResponse, userInput,largestCityAttribute);
   System.out.println("Largest city is->" + largestCity);

   //Get the capital
   String capitalCity = new ProgramCapitalAndLargestCity().getCity(rawResponse, userInput,capitalCityAttribute);
   System.out.println("Capital city is->" + capitalCity);
   System.out.println("######\n");
  }
  while (true);
 }

 public String takeUserInput() {
  //Declarations
  String inputString = "";

  //Takes an input
  System.out.println("Enter a state. You can enter the full name or the abbreviation");
  System.out.println("To exit the pogram type Q");
  BufferedReader buffRdrObject = new BufferedReader(new InputStreamReader(System.in));
  try {
   inputString = buffRdrObject.readLine();
  } catch (IOException ex) {
   System.out.println("IOException happened");
   System.out.println(ex.getMessage());
  }
  return inputString;
 }
 
 public boolean validateUserInput(String userInput)
 {
	 boolean isUserInputValid = true;
	 if(  userInput.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$"))
	 {	 
	 }
	 else
	 {
		 isUserInputValid = false;
	 }
	 
	 return isUserInputValid;
 }

 public String getRawResponse(String webServicesURL) {
  String response = "";
  try {
   URL webServicesURLAsURLObject = new URL(webServicesURL);
   HttpURLConnection httpConnObj = (HttpURLConnection) webServicesURLAsURLObject.openConnection();
   httpConnObj.setRequestMethod("GET");
   httpConnObj.connect();
   int responsecode = httpConnObj.getResponseCode();
   if (responsecode != 200) {
    throw new RuntimeException("HttpResponseCode:" + responsecode);
   }
   Scanner scOBJ = new Scanner(webServicesURLAsURLObject.openStream());
   while (scOBJ.hasNext()) {
    response += scOBJ.nextLine();
   }
   scOBJ.close();
  } catch (MalformedURLException ex) {
   System.out.println("MalformedURLException happened");
   System.out.println(ex.getMessage());
  } catch (IOException ex) {
   System.out.println("IOException happened");
   System.out.println(ex.getMessage());
  } catch (RuntimeException ex) {
   System.out.println("RuntimeException happened");
   System.out.println(ex.getMessage());
  }
  return response;
 }


 public static String getCity(String rawResponse, String userInput, String specificAttribute) {
  //Convert the response to JSON
  String specificCity = null;
  try {
   JSONParser jsonParserObject = new JSONParser();
   JSONObject jsonObjRawResponse = (JSONObject) jsonParserObject.parse(rawResponse);
   JSONObject jsonObjRestResponse = (JSONObject) jsonObjRawResponse.get("RestResponse");
   JSONArray individualRecords = (JSONArray) jsonObjRestResponse.get("result");
   for (int count = 0; count <= individualRecords.size() - 1; count++) {
    JSONObject indiviudalRecord = (JSONObject) individualRecords.get(count);
    Object indiviudalRecordAbbrevation = indiviudalRecord.get("abbr");
    if (indiviudalRecordAbbrevation.toString().equalsIgnoreCase(userInput))
	{
     if (indiviudalRecord.containsKey(specificAttribute)) {
      specificCity = indiviudalRecord.get(specificAttribute).toString();
     } else {
      System.out.println(specificAttribute+" is Not present for this record");
     }
     break;
    }
    Object indiviudalRecordFullStateName = indiviudalRecord.get("name");
    if (indiviudalRecordFullStateName.toString().equalsIgnoreCase(userInput))
	{

     if (indiviudalRecord.containsKey(specificAttribute)) {
      specificCity = indiviudalRecord.get(specificAttribute).toString();
     } else {
      System.out.println(specificAttribute+" is Not present for this record");
     }
     break;
    }

   }
  } catch (ParseException ex) {
   System.out.println("ParseException  happened");
   System.out.println(ex.getMessage());
  }

  return specificCity;
 }





}