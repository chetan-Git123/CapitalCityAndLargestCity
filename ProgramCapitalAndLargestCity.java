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

   String userInput = new ProgramCapitalAndLargestCity().takeUserInput();
   System.out.println("User input is->" + userInput);
   if (userInput.equals("Q")) {
    System.out.println("Exiting the program as per user request");
    break;
   }

   //Get the raw response
   String rawResponse = new ProgramCapitalAndLargestCity().getRawResponse(webServicesURL);
   boolean IsResponseStringEmpty = "".equals(rawResponse);
   if (IsResponseStringEmpty == true) {
    System.out.println("Response from the webservice is empyt. We are exiting the program");
    break;
   }

   //Get the largest city
   String largestCity = new ProgramCapitalAndLargestCity().getLargestCity(rawResponse, userInput);
   System.out.println("Largest city is->" + largestCity);

   //Get the capital
   String capitalCity = new ProgramCapitalAndLargestCity().getCapitalCity(rawResponse, userInput);
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


 public static String getLargestCity(String rawResponse, String userInput) {
  //Convert the response to JSON
  String largestCity = null;
  try {
   JSONParser jsonParserObject = new JSONParser();
   JSONObject jsonObjRawResponse = (JSONObject) jsonParserObject.parse(rawResponse);
   JSONObject jsonObjRestResponse = (JSONObject) jsonObjRawResponse.get("RestResponse");
   JSONArray individualRecords = (JSONArray) jsonObjRestResponse.get("result");
   for (int count = 0; count <= individualRecords.size() - 1; count++) {
    JSONObject indiviudalRecord = (JSONObject) individualRecords.get(count);
    Object indiviudalRecordAbbrevation = indiviudalRecord.get("abbr");
    if (indiviudalRecordAbbrevation.toString().equals(userInput)) {
     if (indiviudalRecord.containsKey("largest_city")) {
      largestCity = indiviudalRecord.get("largest_city").toString();
     } else {
      System.out.println("largest_city is Not present for this record");
     }
     break;
    }
    Object indiviudalRecordFullStateName = indiviudalRecord.get("name");
    if (indiviudalRecordFullStateName.toString().equals(userInput)) {

     if (indiviudalRecord.containsKey("largest_city")) {
      largestCity = indiviudalRecord.get("largest_city").toString();
     } else {
      System.out.println("largest_city is Not present for this record");
     }
     break;
    }

   }
  } catch (ParseException ex) {
   System.out.println("ParseException  happened");
   System.out.println(ex.getMessage());
  }

  return largestCity;
 }


 public String getCapitalCity(String rawResponse, String userInput) {
  //Convert the response to JSON
  String capitalCity = null;
  try {
   JSONParser jsonParserObject = new JSONParser();
   JSONObject jsonObjRawResponse = (JSONObject) jsonParserObject.parse(rawResponse);
   JSONObject jsonObjRestResponse = (JSONObject) jsonObjRawResponse.get("RestResponse");
   JSONArray individualRecords = (JSONArray) jsonObjRestResponse.get("result");
   for (int count = 0; count <= individualRecords.size() - 1; count++) {
    JSONObject indiviudalRecord = (JSONObject) individualRecords.get(count);
    Object indiviudalRecordAbbrevation = indiviudalRecord.get("abbr");
    if (indiviudalRecordAbbrevation.toString().equals(userInput)) {
     capitalCity = indiviudalRecord.get("capital").toString();
     break;
    }
    Object indiviudalRecordFullStateName = indiviudalRecord.get("name");
    if (indiviudalRecordFullStateName.toString().equals(userInput)) {
     capitalCity = indiviudalRecord.get("capital").toString();
     break;
    }

   }
  } catch (ParseException ex) {
   System.out.println("ParseException  happened");
   System.out.println(ex.getMessage());
  }

  return capitalCity;
 }




}