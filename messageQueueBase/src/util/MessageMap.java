package util;

public class MessageMap
{
	private String name;
	private String classPath;
	
	
	
	public MessageMap()
	{
		
	}

	

	
	public MessageMap(String name, String classPath)
	{
		super();
		this.name = name;
		this.classPath = classPath;
	}




	public String getName()
	{
		return name;
	}




	public void setName(String name)
	{
		this.name = name;
	}




	public String getClassPath()
	{
		return classPath;
	}

	public void setClassPath(String classPath)
	{
		this.classPath = classPath;
	}
	
	
}