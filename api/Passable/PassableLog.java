import java.util.*;
import org.json.*;
//source: http://theoryapp.com/parse-json-in-java/

public class PassableLog implements Passable{
	public String id;
	public ArrayList parameterData;
	public ServerDate time;
	public String message;
	public String type;

	public PassableLog(){
		id = "";
		parameterData = new ArrayList();
		time = new ServerDate("1970-01-01 00:00:00");
		message = "No log message was input into this field.";
		type = "none";
	}

	private Object safelyGet(JSONObject json, String member, Object defaultValue){
		try{
			return json.get(member);
		}catch(Exception e){
			return defaultValue;
		}
	}

	public PassableLog(String input) throws Exception{
		PassableLog defaultUser = new PassableLog();
		JSONObject parsedInput = new JSONObject(input);
		id = (String) safelyGet(parsedInput, "id", defaultUser.id);
		if(id.equals("")){ //at the very minimum, ID must be valid
			throw new Exception("Error: Problem trying to get ID");
		}

		message = (String) safelyGet(parsedInput, "message", defaultUser.message);
		type = (String) safelyGet(parsedInput, "type", defaultUser.type);
		
		
		String date = (String) safelyGet(parsedInput, "time", "");
		if(date.length() > 0)
			time = new ServerDate(date);
		else
			throw new Exception("Error: Problem trying to get time data");

		JSONArray dataArray = (JSONArray) safelyGet(parsedInput, "parameterData", new JSONArray());
		parameterData = new ArrayList();
		for(int i = 0; i < dataArray.length(); ++i){
			parameterData.add(dataArray.get(i));
		}
	}

	//Exception shouldn't happen unless one or more of the values are invalid
	public String toJSON() throws Exception{
		JSONObject returnJSONObj = new JSONObject();
		returnJSONObj.put("id", id);
		returnJSONObj.put("time", time.toString());
		returnJSONObj.put("type", type.toLowerCase());
		returnJSONObj.put("message", message);
		JSONArray paramJSON = new JSONArray();
		for(int i = 0; i < parameterData.size(); ++i){
			paramJSON.put(parameterData.get(i));
		}
		returnJSONObj.put("parameterData", paramJSON);
		return returnJSONObj.toString();
	}

	public boolean isBeingListened(){
		return true;
	}

	// example usage
	public static void main(String[] args) {
		PassableLog logEntry = new PassableLog();
		logEntry.id = "12345abcde";
		logEntry.type = "module";
		logEntry.message = "I am the first log entry";
		logEntry.parameterData.add("param 1");
		try{
			System.out.println(logEntry.toJSON());
			PassableLog logEntry2 = new PassableLog(logEntry.toJSON());
			logEntry2.parameterData.add("param 2");
			logEntry2.message = "I am the second log entry";
			logEntry2.time = new ServerDate();
			System.out.println(logEntry2.toJSON());
		}catch(Exception e){
			System.out.println("Problem with log entry.");
		}
			
	}
}
