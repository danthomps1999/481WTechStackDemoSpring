package SpringFuntionsDemo;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {
	public static ArrayList<String> userNames = new ArrayList<String>();

	static {
		userNames.add("Testing T. Testerston the Third");
	}

	public ArrayList<String> getUserNames() {
		return userNames;
	}

	public void addUserName(String userName) {
		userNames.add(userName);
	}
}
