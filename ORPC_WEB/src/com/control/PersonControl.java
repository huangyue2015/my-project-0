package com.control;

import javax.servlet.http.HttpServletRequest;

import com.util.Serialization;

import interface_.PersonService;
import po.Person;

public class PersonControl
{
	@SuppressWarnings("unchecked")
	public Person updatePerson(HttpServletRequest req)
	{
		try
		{
			Person person = new Person(Serialization.toEntityBean(req));
			
			PersonService.newInstance().updatePerson(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new Person("张三", 18);
	}

}
