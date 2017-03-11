import java.util.*;
import org.json.*;
//source: http://theoryapp.com/parse-json-in-java/

public class PassableModule implements Passable{
	public String name;
	public String type;
	public String id;
	public String mainServerID;
	public ArrayList parameterData;

	public PassableModule(){
		name = "";
		type = "";
		id = "";
		mainServerID = "";
		parameterData = new ArrayList();
	}

	public PassableModule(String input){
		JSONObject parsedInput = new JSONObject(input);
		name = (String)parsedInput.get("name");
		type = (String)parsedInput.get("type");
		id = (String)parsedInput.get("id");
		mainServerID = (String)parsedInput.get("mainServerID");
		JSONArray parametersArray = (JSONArray)parsedInput.get("parameterData");
		parameterData = new ArrayList();
		for(int i = 0; i < parametersArray.length(); ++i){
			parameterData.add(parametersArray.get(i));
		}
	}

	public String toJSON(){
		JSONObject returnJSONObj = new JSONObject();
		returnJSONObj.put("name", name.toLowerCase());
		returnJSONObj.put("type", type.toLowerCase());
		returnJSONObj.put("id", id);
		returnJSONObj.put("mainServerID", mainServerID);
		returnJSONObj.put("parameterData", parameterData);
		return returnJSONObj.toString();
	}

	// example usage
	public static void main(String[] args) {
			PassableModule myModule = new PassableModule();
			myModule.name = "Front Door Sensor";
			myModule.type = "sensormodule";
			myModule.id = "12345abcde";
			myModule.mainServerID = "123.456.789:8080";
			myModule.parameterData.add(0);
			myModule.parameterData.add("test string");
			myModule.parameterData.add('c');
			myModule.parameterData.add(50);
			System.out.println(myModule.toJSON());

			PassableModule myModule2 = new PassableModule(myModule.toJSON());
			myModule.parameterData.add("a new value");
			System.out.println(myModule.toJSON());
	}
}
