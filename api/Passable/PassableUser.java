

public class PassableUser implements Passable{
	public String name;
	public String type;
	public String id;

	public PassableUser(){}

	public String toJSON(){
		//TODO
		return "PassableUser.toJSON() not implemented yet";
	}

	// example usage
	public static void main(String[] args) {
			PassableUser myUser = new PassableUser();
			myUser.name = "John Doe";
			myUser.type = "guardian";
			myUser.id = "12345abcde";

			System.out.println(myUser.toJSON());
	}
}
