import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StonkAPI {
	
	public StonkAPI() {
		
	}
	
    public String[] stonksInfo(String tick) {
    	
    	String[] info = new String[5];

    	String apiString1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
    	String ticker = tick;
    	String apiString2 = "&apikey=demo";		// Replace demo with your key that you get from alphavantage.co
    	
    	String apiUrl = apiString1 + ticker + apiString2;
    	
        try {

            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));
                
                JSONObject dataObjectMeta = (JSONObject) parse.parse(String.valueOf(dataObject.get("Meta Data")));				// Meta Data information
                JSONObject dataObjectHighLow = (JSONObject) parse.parse(String.valueOf(dataObject.get("Time Series (Daily)")));	// Time series information
                
                String refreshTime = String.valueOf(dataObjectMeta.get("3. Last Refreshed"));  									// get the Last refresh time and store it
                String timeZone = String.valueOf(dataObjectMeta.get("5. Time Zone"));
                
                JSONObject dataObjectInfo = (JSONObject) parse.parse(String.valueOf(dataObjectHighLow.get(refreshTime)));		// get the information from the most recent time
                
                info[0] = refreshTime;																							// Storing all the information we got
                info[1] = timeZone;
                info[2] = String.valueOf(dataObjectInfo.get("2. high"));
                info[3] = String.valueOf(dataObjectInfo.get("3. low"));
                info[4] = String.valueOf(dataObjectInfo.get("4. close"));
                
                System.out.println("Someone requested " + ticker + ", the information returned was " + info[0] + " // " + info[1] + " // " + info[2] + " // " + info[3] + " // " + info[4]);
                
                return info;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }


}
