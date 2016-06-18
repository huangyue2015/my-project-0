package netty.serialization;

import java.io.Serializable;

public class SubCribeReq implements Serializable
{

	/**
	 * 默认的序列号ID
	 */
	private static final long serialVersionUID = 1L;

	private int  subReqID;
	
	private String userName;
	
	private String producName;
	
	private String phoneNumber;
	
	private String address;

	public int getSubReqID()
	{
		return subReqID;
	}

	public void setSubReqID(int subReqID)
	{
		this.subReqID = subReqID;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getProducName()
	{
		return producName;
	}

	public void setProducName(String producName)
	{
		this.producName = producName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	@Override
	public String toString()
	{
		return String.format("SubCribeReq[subReqID=%s,userName=%s,producName=%s,phoneNumber=%s,address=%s]",
				subReqID,userName,producName,phoneNumber,address);
	}
}






















