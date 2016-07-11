package com.control;

import javax.servlet.http.HttpServletRequest;

import com.pojo.User;
import com.web.Serialization;

public class UserControl implements UserInterface
{
	@Override
	public void addUser(HttpServletRequest req) 
	{
		User person = new User(Serialization.toEntityBean(req));
		System.out.println(person.getUsername()+ person.getNickname() + person.getPassword());
	}
	
}
