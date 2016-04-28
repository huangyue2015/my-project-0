package com.util;

public class Correspond<Name,Value>
{
	private Name name;
	private Value value;
	
	public Correspond(Name name, Value value)
	{
		this.name = name;
		this.value = value;
	}

	public Name getName()
	{
		return name;
	}

	public Value getValue()
	{
		return value;
	}

	public void setName(Name name)
	{
		this.name = name;
	}

	public void setValue(Value value)
	{
		this.value = value;
	}
	
	
}
