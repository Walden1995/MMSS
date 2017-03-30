import java.util.*;
import org.json.*;
//source: http://theoryapp.com/parse-json-in-java/

public class PassableNotification implements Passable{
	public boolean success;
	public String message;
	public String time;
	public ArrayList<PassablePageInfo> data;

	public PassableNotification(){
		success = false;
		message = "No message received or invalid response from server";
		time = "1970-01-01 00:00:00";
		data = new ArrayList<PassablePageInfo>();
	}

	private Object safelyGet(JSONObject json, String member, Object defaultValue){
		try{
			return json.get(member);
		}catch(Exception e){
			return defaultValue;
		}
	}

	public PassableNotification(String input) throws Exception{
		PassableNotification defaultNotification = new PassableNotification();
		JSONObject parsedInput = new JSONObject(input);
		success = (boolean) safelyGet(parsedInput, "success", defaultNotification.success);
		if(success != true && success != false){//shouldn't happen
			throw new Exception("Error: Problem trying to get success boolean");
		}
		message = (String) safelyGet(parsedInput, "message", defaultNotification.message);
		time = (String) safelyGet(parsedInput, "time", defaultNotification.time);
		JSONArray dataArray = (JSONArray) safelyGet(parsedInput, "data", defaultNotification.data);
		data = new ArrayList<PassablePageInfo>();
		for(int i = 0; i < dataArray.length(); ++i){
			JSONObject curObject = (JSONObject) dataArray.get(i);
			data.add(new PassablePageInfo(curObject));
		}
	}

	//Exception shouldn't happen unless one or more of the values are invalid
	public String toJSON() throws Exception{
		JSONObject returnJSONObj = new JSONObject();
		returnJSONObj.put("success", success);
		returnJSONObj.put("message", message);
		returnJSONObj.put("time", time);
		JSONArray tempArray = new JSONArray();
		for(int i = 0; i < data.size(); ++i){
			PassablePageInfo curPage = (PassablePageInfo) data.get(i);
			tempArray.put(curPage.toJSONObject());
		}
		returnJSONObj.put("data",tempArray);
		return returnJSONObj.toString();
	}

	public boolean isBeingListened(){
		return true;
	}

	// example usage
	public static void main(String[] args) {
		PassableNotification myResponse = new PassableNotification();
		myResponse.data.add(new PassablePageInfo("myType", "myID"));
		try{
			System.out.println(myResponse.toJSON());
			PassableNotification myResponse2 = new PassableNotification(myResponse.toJSON());
			myResponse2.success = true;
			myResponse2.message = "A whole new message";
			myResponse2.data.add(new PassablePageInfo("newType","newID"));
			System.out.println(myResponse2.toJSON());
		}catch(Exception e){
			System.out.println(e);
			System.out.println("Response is invalid");
		}
			
	}
}

class PassablePageInfo implements Passable{
	public String type;
	public String id;

	public PassablePageInfo(){
		type = "none";
		id = "";
	}

	public PassablePageInfo(String inType, String inID){
		type = inType;
		id = inID;
	}

	private Object safelyGet(JSONObject json, String member, Object defaultValue){
		try{
			return json.get(member);
		}catch(Exception e){
			return defaultValue;
		}
	}

	public PassablePageInfo(String input) throws Exception{
		PassablePageInfo defaultPageInfo = new PassablePageInfo();
		JSONObject parsedInput = new JSONObject(input);
		type = (String) safelyGet(parsedInput, "type", defaultPageInfo.type);
		if(type.length() == 0){
			throw new Exception("Error: No type specified");
		}
		id = (String) safelyGet(parsedInput, "id", defaultPageInfo.id);
		if(id.length() == 0){
			throw new Exception("Error: ID is of length 0");
		}
	}

	public PassablePageInfo(JSONObject parsedInput) throws Exception{
		PassablePageInfo defaultPageInfo = new PassablePageInfo();
		type = (String) safelyGet(parsedInput, "type", defaultPageInfo.type);
		if(type.length() == 0){
			throw new Exception("Error: No type specified");
		}
		id = (String) safelyGet(parsedInput, "id", defaultPageInfo.id);
		if(id.length() == 0){
			throw new Exception("Error: ID is of length 0");
		}
	}

	//Exception shouldn't happen unless one or more of the values are invalid
	public String toJSON() throws Exception{
		return toJSONObject().toString();
	}

	public JSONObject toJSONObject() throws Exception{
		JSONObject returnJSONObj = new JSONObject();
		returnJSONObj.put("type", type);
		returnJSONObj.put("id", id);
		return returnJSONObj;
	}

	public boolean isBeingListened(){
		return true;
	}

	// example usage
	public static void main(String[] args) {
		PassablePageInfo myPage = new PassablePageInfo();
		myPage.id = "123";
		myPage.type = "module";
		try{
			System.out.println(myPage.toJSON());
			PassablePageInfo myPage2 = new PassablePageInfo(myPage.toJSON());
			myPage2.id = "456";
			myPage2.type = "user";
			System.out.println(myPage2.toJSON());
		}catch(Exception e){
			System.out.println("Respone is invalid");
		}
			
	}
}