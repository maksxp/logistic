package ua.viasat.logistic.service;

import ua.viasat.logistic.model.User;

import java.util.List;

public interface UserService {
	 User findUserByEmail(String email);
	 User findUserById(int id);
	 void saveUser(User user);
	 void editUserType(String userType, int userId);
	 void editUserPassword(String password, int userId);
	 List<User> listAllUsers();
}
