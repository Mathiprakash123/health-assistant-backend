package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.UserRepository;
import com.example.demo.modal.UserRegister;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public String addUser(UserRegister userInfo) {
        UserRegister user = new UserRegister();
        user.setFirstname(userInfo.getFirstname());
        user.setPassword(userInfo.getPassword());
        user.setEmail(userInfo.getEmail());
        user.setAge(userInfo.getAge());
        user.setHeight(userInfo.getHeight());
        user.setWeight(userInfo.getWeight());

        repo.save(user);
        return "Registered";
    }
    public List<UserRegister> viewAllInfo() {
    	return repo.findAll();
    }
	public String checkUser(String email, String password) {
		
		UserRegister user = repo.findAllByEmail(email);
		if(user!=null && user.getPassword().equals(password)){
			return "Login successful";
		}
		
		return "Login failed";
	}
	

    public UserRegister getUserProfileByEmail(String email) {
        UserRegister user= repo.findAllByEmail(email);
        return user;
    }
}
