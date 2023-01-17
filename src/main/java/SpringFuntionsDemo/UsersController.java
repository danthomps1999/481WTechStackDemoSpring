package SpringFuntionsDemo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/userNames")
public class UsersController {
	@Autowired
	private UsersDAO usersDAO;

	@GetMapping(path = "/", produces = "application/json")
	public ArrayList<String> getUserNames() {
		return usersDAO.getUserNames();
	}

	@PostMapping(path = "/", consumes = "application/json", produces = "application.json")
	public void addUserName(@RequestBody String string) {
		usersDAO.addUserName(string);
	}
}