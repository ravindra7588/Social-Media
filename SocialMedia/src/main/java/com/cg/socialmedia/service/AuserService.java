package com.cg.socialmedia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.cg.socialmedia.entity.Auser;
import com.cg.socialmedia.entity.UserProfile;
import com.cg.socialmedia.exception.ResourceNotFoundException;
import com.cg.socialmedia.exception.UserAlreadyExistsException;
import com.cg.socialmedia.exception.UserNotFoundException;
import com.cg.socialmedia.repository.AuserRepository;

@Service
public class AuserService{
	
	@Autowired
	private AuserRepository userRepo;
	

	public AuserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(AuserRepository userRepo) {
		this.userRepo = userRepo;
	}


	public Auser registerUser(Auser user) throws UserAlreadyExistsException{
		return userRepo.save(user);
	}

	public Auser updatePassword(Auser user) throws UserNotFoundException {
		return userRepo.save(user);
	}

	public Auser findByEmailId(String emailId) throws UserNotFoundException {
		return userRepo.findByEmailId(emailId);
	}

	//method to view all records of user
		public List<Auser> viewAllUsers() throws ResourceNotFoundException
		{
			List<Auser> user= userRepo.findAll();
			if(user==null || user.isEmpty()) {
				throw new ResourceNotFoundException("not found");
			}
			else {
				return user;
			}
		}
		
		
		//method to delete user record by id
		public Integer deleteUserById(long id) throws ResourceNotFoundException{
			Auser user=userRepo.findUserById(id) ;
			if(user==null) {
				throw new ResourceNotFoundException("not found");
			}
			else {
				userRepo.deleteById(id);
				return 1;
			}
		}
		
		public List<Auser> findAll() throws ResourceNotFoundException{
			List<Auser> userList=userRepo.findAll();
			if(userList==null || userList.isEmpty()) {
				throw new ResourceNotFoundException("not found");
			}
			else {
				return userList;
			}
		}

}