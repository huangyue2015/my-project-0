package json.pojo;

import java.util.List;

public class Customer
{
	private long customerNumber;
	private String firstName;
	private String laseName;
	private List<String> middleNames;
	public long getCustomerNumber()
	{
		return customerNumber;
	}
	public void setCustomerNumber(long customerNumber)
	{
		this.customerNumber = customerNumber;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLaseName()
	{
		return laseName;
	}
	public void setLaseName(String laseName)
	{
		this.laseName = laseName;
	}
	public List<String> getMiddleNames()
	{
		return middleNames;
	}
	public void setMiddleNames(List<String> middleNames)
	{
		this.middleNames = middleNames;
	}
	
	
}
