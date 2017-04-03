package ua.viasat.logistic.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.viasat.logistic.model.Role;
import ua.viasat.logistic.model.User;
import ua.viasat.logistic.repository.RoleRepository;
import ua.viasat.logistic.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	@Override
	public User findUserById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public void saveUser(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByRole(user.getUserType());
        user.setEnabled(true);
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	@Override
	public void editUserPassword(String password,int userId){
		User user = userRepository.findById(userId);
		user.setPassword(password);
	}
	@Override
	public void editUserType (String userType,int userId){
		User user = userRepository.findById(userId);
		user.setUserType(userType);
		Role userRole = roleRepository.findByRole(user.getUserType());
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
	}

	@Override
	public List<User> listAllUsers(){
		return userRepository.findAll();
	}

}
