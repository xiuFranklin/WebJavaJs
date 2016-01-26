package sy.service;

import java.util.List;

import sy.modle.User;


public interface UserServiceI {

	public User getUserById(String id);

	public List<User> getAll();
	
	public List<User> getAllUser();
}
