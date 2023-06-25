package com.example.expensetrackerApi.Service;


import com.example.expensetrackerApi.entity.User;
import com.example.expensetrackerApi.entity.UserModel;

import lombok.NoArgsConstructor;


public interface UserService {

	User createUser(UserModel user);
	User getUser();
	User getLoggedInUser();
	void updateUser(User user);
	void deleteUser();
}
