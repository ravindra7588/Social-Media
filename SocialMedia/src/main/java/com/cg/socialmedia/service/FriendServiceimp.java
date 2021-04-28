package com.cg.socialmedia.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.socialmedia.entity.Friends;
import com.cg.socialmedia.exception.FriendException;
import com.cg.socialmedia.repository.FriendRepository;





@Service
@Transactional
public class FriendServiceimp implements FriendsService {
	@Autowired
	private FriendRepository fridao;
   //Add friends
	@Override
	public Friends add(Friends friends) {
		// TODO Auto-generated method stub
		
		
		
		return fridao.save(friends);
	}
   
 
	//find by user id
	@Override
	public Friends FindByuserid(Long user, Long user1)throws FriendException{
	        Friends fri=fridao.findbyuserid(user, user1);
	        if(fri == null ) {
	        	throw new FriendException("no found friend request");
	        }
	        else {
	        return fri;
	        }
	}
//AcceptRequest
	@Override
	public Integer AcceptRequest(Long user, Long user1){
		return fridao.acceptRequest(user, user1);
		
	}
//list Request
	@SuppressWarnings("null")
	@Override
	public List<Friends> ListRequest(Long user) throws  FriendException {
		// TODO Auto-generated method stub
		List<Friends> fri=fridao.RequestList(user);
		if(fri==null && fri.isEmpty()) {
			throw new FriendException("NO FRIEND ADDED");
		}
		else {
		return fridao.RequestList(user);
		}
	}
//delete
	@Override
	public void deleteRequest(Friends friend) throws  FriendException {
		// TODO Auto-generated method stub
		fridao.delete(friend);
	}
//List Friends
	@Override
	public List<Friends> listFriend(Long user) throws  FriendException  {
		// TODO Auto-generated method stub
		return fridao.friendList(user);
	}

   
	

	
	
}
