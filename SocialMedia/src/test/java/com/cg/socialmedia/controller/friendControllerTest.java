package com.cg.socialmedia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.socialmedia.entity.Auser;
import com.cg.socialmedia.entity.Friends;
import com.cg.socialmedia.entity.Status;
import com.cg.socialmedia.repository.AuserRepository;
import com.cg.socialmedia.repository.FriendRepository;
import com.cg.socialmedia.service.FriendsService;

@SpringBootTest
public class friendControllerTest {
    @Autowired
    private FriendRepository repo;
	@Autowired
	private FriendsService service;
	@Autowired
	private Friends friend;
	@Autowired
	private Auser user;
	@Autowired
	private Auser user2;
	@Autowired
	private AuserRepository userrepo;
	@Autowired
	private FriendsController con;
	
	@Test
	public void addDetailsTest() {
		Long aa=(long) 100;
		Long bb=(long)200;
		Long ff=(long)123;
		user.setUserId(aa);
		user.setUsername("rahulreddy");
		user.setEmailId("rahulreddy@gmail");
		Auser u1=userrepo.save(user);
		user2.setUserId(bb);
		user2.setEmailId("googlereddy@gmail.com");
		user2.setUsername("google23");
		Auser u2=userrepo.save(user2);
		friend.setFriendid(ff);
		friend.setActiveid(user.getUserId());
		friend.setStatus(Status.REQUEST);
		friend.setUser(u1);
		friend.setUserid2(u2);
		Friends f2=repo.save(friend);
		assertEquals(f2.getUser(),u1);
		assertEquals(f2.getUserid2(),u2);
		assertEquals(f2.getActiveid(),u1.getUserId());		
	}
	@Test
	public void userById() {
		Long aa=(long) 100;
		Long bb=(long)200;
		Long ff=(long)123;
		user.setUserId(aa);
		user.setUsername("rahul");
		user.setEmailId("rahul@gmail");
		Auser u1=userrepo.save(user);
		user2.setUserId(bb);
		user2.setEmailId("google@gmail.com");
		user2.setUsername("google");
		Auser u2=userrepo.save(user2);
		friend.setFriendid(ff);
		friend.setActiveid(user.getUserId());
		friend.setStatus(Status.REQUEST);
		friend.setUser(u1);
		friend.setUserid2(u2);
		Friends f2=repo.save(friend);
	  Friends fri=repo.findbyuserid(u1.getUserId(), u2.getUserId());
	  assertEquals(fri.getActiveid(),f2.getActiveid()); 
	}
	@Test
	public void deleteRequest() {
		Long aa=(long) 100;
		Long bb=(long)200;
		Long ff=(long)123;
		user.setUserId(aa);
		user.setUsername("rahul33");
		user.setEmailId("rahul33@gmail.com");
		Auser u1=userrepo.save(user);
		user.setUserId(bb);
		user2.setEmailId("google22@11.com");
		user2.setUsername("google233");
		Auser u2=userrepo.save(user2);
		friend.setFriendid(ff);
		friend.setActiveid(user.getUserId());
		friend.setStatus(Status.REQUEST);
		friend.setUser(u1);
		friend.setUserid2(u2);
		Boolean  f1=con.DeleteRequest(u1.getUserId(), u2.getUserId());
		assertEquals(f1,true);	
	}

@Test
public void Accept() {
	Long aa=(long) 100;
	Long bb=(long)200;
	Long ff=(long)123;
	user.setUserId(aa);
	user.setUsername("rahul");
	user.setEmailId("rahul@gmail");
	Auser u1=userrepo.save(user);
	user2.setUserId(bb);
	user2.setEmailId("google@gmail.com");
	user2.setUsername("google");
	Auser u2=userrepo.save(user2);
	friend.setFriendid(ff);
	friend.setActiveid(user.getUserId());
	friend.setStatus(Status.REQUEST);
	friend.setUser(u1);
	friend.setUserid2(u2);
	Friends f2=repo.save(friend);
  Friends fri=repo.findbyuserid(u1.getUserId(), u2.getUserId());
  assertEquals(fri.getActiveid(),f2.getActiveid()); 
}
}
