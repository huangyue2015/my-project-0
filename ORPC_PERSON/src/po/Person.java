package po;

import java.io.Serializable;
import system.bean.EntityBean;


public class Person implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@comment.EntityBean
	private String name;
	
	@comment.EntityBean
	private int age;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public Person(String name, int age)
	{
		this.name = name;
		this.age = age;
	}
	public Person()
	{
	}
	
	public  Person(EntityBean<String,Object> map)
	{
		this.name = map.getString("name");
		this.age = map.getInt("age");
	}
}
