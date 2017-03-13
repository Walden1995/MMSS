import java.util.*;
import org.json.*;
//source: http://theoryapp.com/parse-json-in-java/

public class PassableUser implements Passable{
	public String name;
	public String type;
	public String id;
	public ArrayList logs;
	public ArrayList notifications;
	public boolean isBeingListened;

	public PassableUser(){
		name = "";
		type = "";
		id = "";
		logs = new ArrayList();
		notifications = new ArrayList();
		isBeingListened = false;
	}

	private Object safelyGet(JSONObject json, String member, Object defaultValue){
		try{
			return json.get(member);
		}catch(Exception e){
			return defaultValue;
		}
	}

	public PassableUser(String input) throws Exception{
		PassableUser defaultUser = new PassableUser();
		JSONObject parsedInput = new JSONObject(input);
		id = (String) safelyGet(parsedInput, "id", defaultUser.id);
		if(id.equals("")){ //at the very minimum, ID must be valid
			throw new Exception("Error: Problem trying to get ID");
		}
		name = (String) safelyGet(parsedInput, "name", defaultUser.name);
		type = (String) safelyGet(parsedInput, "type", defaultUser.type);
		isBeingListened = (boolean) safelyGet(parsedInput, "isBeingListened", defaultUser.isBeingListened);
		
		JSONArray logsInput = (JSONArray) safelyGet(parsedInput, "logs", defaultUser.logs);
		logs = new ArrayList();
		for(int i = 0; i < logsInput.length(); ++i){
			logs.add(logsInput.get(i));
		}

		JSONArray notificationsInput = (JSONArray) safelyGet(parsedInput, "notifications", defaultUser.notifications);
		notifications = new ArrayList();
		for(int i = 0; i < notificationsInput.length(); ++i){
			notifications.add(notificationsInput.get(i));
		}
	}

	public String toJSON(){
		JSONObject returnJSONObj = new JSONObject();
		returnJSONObj.put("name", name.toLowerCase());
		returnJSONObj.put("type", type.toLowerCase());
		returnJSONObj.put("id", id);
		returnJSONObj.put("logs", logs);
		returnJSONObj.put("notifications", notifications);
		returnJSONObj.put("isBeingListened", isBeingListened);
		return returnJSONObj.toString();
	}

	public boolean isBeingListened(){
		return isBeingListened;
	}

	// example usage
	public static void main(String[] args) {
		PassableUser myUser = new PassableUser();
		myUser.name = "John Doe";
		myUser.type = "guardian";
		myUser.id = "12345abcde";
		myUser.logs.add("log 1");
		myUser.notifications.add("note 1");
		System.out.println(myUser.toJSON());
		try{
			PassableUser myUser2 = new PassableUser(myUser.toJSON());
			myUser2.logs.add("log 2");
			myUser2.notifications.add("note 2");
			System.out.println(myUser2.toJSON());
		}catch(Exception e){
			System.out.println("ID '" + myUser.id + "' is invalid");
		}
			
	}
}
