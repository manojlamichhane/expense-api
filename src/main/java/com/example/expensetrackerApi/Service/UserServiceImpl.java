package com.example.expensetrackerApi.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.expensetrackerApi.entity.User;
import com.example.expensetrackerApi.entity.UserModel;
import com.example.expensetrackerApi.exception.ItemAlreadyExistsException;
import com.example.expensetrackerApi.exception.ResourceNotFoundException;
import com.example.expensetrackerApi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User createUser(UserModel user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new ItemAlreadyExistsException("User is already registered");
		}
		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		return userRepository.save(newUser);
	}

	@Override
	public User getUser() {
		
		User newUser = getLoggedInUser();
		return userRepository.findById(newUser.getId()).orElseThrow(()->new ResourceNotFoundException("User not found"));		

//		Optional<User> newUser = userRepository.findById(id);
//		if(newUser.isPresent()) {
//			return newUser.get();
//		}
//		throw new ResourceNotFoundException("User not found");
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		User newUser = getLoggedInUser();
		newUser.setName(user.getName( )== null?newUser.getName():user.getName());
		newUser.setEmail(user.getEmail() == null?newUser.getEmail():user.getEmail());
		newUser.setPassword(user.getPassword() == null?newUser.getPassword():user.getPassword());
		newUser.setAge(user.getAge()==null?newUser.getAge():user.getAge());
		userRepository.save(newUser);
	}

	@Override
	public void deleteUser() {
		User newuser =  getLoggedInUser() ;
		userRepository.delete(newuser);
		
	}

	@Override
	public User getLoggedInUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
        return	userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		// TODO Auto-generated method stub
	}

}
