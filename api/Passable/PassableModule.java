import java.util.*;

public class PassableModule implements Passable{
	public String name;
	public String type;
	public String id;
	public String hubServerID;
	public String mainServerID;
	public ArrayList parameterData;

	public PassableModule(){}

	public String toJSON(){
		String message = "{\"name\": \"" + name.toLowerCase() + "\",";
		message += "\"type\": \"" + type.toLowerCase() + "\",";
		message += "\"id\": \"" + id + "\",";
		message += "\"hubServerID\": \"" + hubServerID + "\",";
		message += "\"mainServerID\": \"" + mainServerID + "\",";
		message += "\"parameterData\": " + parameterData;
		message += "}";
		return message;
	}

	// example usage
	public static void main(String[] args) {
			PassableModule myModule = new PassableModule();
			myModule.name = "Front Door Sensor";
			myModule.type = "sensormodule";
			myModule.id = "12345abcde";
			myModule.hubServerID = "10.0.0.5:3030";
			myModule.mainServerID = "123.456.789:8080";
			myModule.parameterData = new ArrayList();
			myModule.parameterData.add(0);

			System.out.println(myModule.toJSON());

			myModule.parameterData.add("test string");
			System.out.println(myModule.toJSON());
	}
}
