package com.cg.socialmedia.controller;

import java.util.List;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cg.socialmedia.Myproperties;
import com.cg.socialmedia.entity.Auser;
import com.cg.socialmedia.entity.Friends;
import com.cg.socialmedia.entity.Status;
import com.cg.socialmedia.exception.FriendException;
import com.cg.socialmedia.repository.UserRepository;
import com.cg.socialmedia.service.FriendServiceimp;



@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RestController
public class FriendsController {
private static final Logger Logger=LogManager.getLogger(FriendsController.class);
	
	@Autowired
	Myproperties properties;
	@Autowired
	private FriendServiceimp friendservice;
	
	@Autowired
	private UserRepository repo1;
	
	//Add request
	@ResponseStatus(value = HttpStatus.OK)
		@PostMapping(value="/friend/{name}/{name2}")
		public ResponseEntity<?> Request(@PathVariable String name,@PathVariable String name2,@RequestBody Friends friend) {
		     Auser user=repo1.findByFirstName(name);
		     Auser user2=repo1.findByFirstName(name2);
			Friends fri=friendservice.FindByuserid(user.getUserId(), user2.getUserId());
			Friends fri2=friendservice.FindByuserid(user2.getUserId(), user.getUserId());
			if (fri==null && fri2==null) {
				friend.setStatus(Status.REQUEST);
				friend.setActiveid(user.getUserId());
				friend.setUser(user);
				friend.setUserid2(user2);
			 Friends fr2=friendservice.add(friend);
			 Logger.info("request send to"+name+" "+name2+properties.getLog().getSendrequest());
			 return new ResponseEntity<Friends>(fr2,HttpStatus.OK);
			}
			else {
				Logger.error("request send to"+name+" "+name2+properties.getLog().getNotsendrequest());
				return new ResponseEntity<Boolean>(false,HttpStatus.OK);
			}
			
		}
	//Accept Request
	@PutMapping(value="/friend/{user}/{user2}")
   public Integer acceptRequest (@PathVariable Long user,@PathVariable Long user2) {
		 Logger.info("request Accepted"+user+" "+user2+properties.getLog().getRequestAccepted());
	     return friendservice.AcceptRequest(user, user2);
	   
   }
	
	
	
	
	
	
	
	//friend list
    @GetMapping(value="/friend/{user}")
       public ResponseEntity< ?> friendlist(@PathVariable Long user) throws  FriendException{
         List<Friends>fri=friendservice.listFriend(user);
         if(fri == null && fri.isEmpty()) {
        	 throw new  FriendException("request is not found");
         }
         List<Auser>user2= fri.stream().filter(p->p.getUserid2().getUserId() != user).map(p->p.getUserid2()).collect(Collectors.toList());
         List<Auser>user1= fri.stream().filter(p->p.getUser().getUserId() !=user).map(p->p.getUser()).collect(Collectors.toList());
         user1.addAll(user2);
         if(user1.isEmpty()) {
        	 Logger.error("friend list with user"+user+" "+properties.getLog().getNofriend());
             return new ResponseEntity<String>("NO FRIEND ADDED",HttpStatus.OK);
         }
         Logger.info("friend list user"+user+" "+properties.getLog().getFriend());
            return new ResponseEntity<List<Auser>>(user1,HttpStatus.OK);
        }
	

    
    
    
    
    
	//ListRequests
		@GetMapping(value="friend1/{user}")
		public ResponseEntity<?> ListRequests(@PathVariable Long user) throws  FriendException{
			List<Friends> fri=friendservice.ListRequest(user);
			
			if (fri.isEmpty()) {
				 Logger.error("friend request user"+user+" "+properties.getLog().getNofriendrequest());
			  return new ResponseEntity<String>("NO REQUEST FOUND",HttpStatus.OK);  
			}
			else {
				 Logger.info("friend request "+user+" "+properties.getLog().getFriendrequest());
			return new ResponseEntity<List<Friends>> (fri,HttpStatus.OK);}
		}
		
		
		
		
	//deleteRequest
	@DeleteMapping(value="friend/{user}/{user1}")
	public Boolean DeleteRequest(@PathVariable Long user , @PathVariable Long user1) throws FriendException{
		Friends friend=friendservice.FindByuserid(user, user1);
		
		if (friend == null) {
			Logger.error("delete the friend request"+user+""+user1+" "+properties.getLog().getNotdelete());
			
			return false;
		}
		else {
			  Logger.info("delete the friend request"+user+" "+user1+" "+properties.getLog().getDelete());
	            
			friendservice.deleteRequest(friend);
			return true;
		}
		
		
	}
}
