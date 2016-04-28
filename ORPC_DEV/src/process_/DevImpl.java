package process_;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interface_.IDev;
import po.Person;

public class DevImpl extends UnicastRemoteObject implements IDev
{

	private static final long serialVersionUID = 1L;

	public DevImpl() throws RemoteException
	{
	}

	@Override
	public void updateDev(Person person) throws RemoteException
	{
		System.out.println("DEV:  姓名:"+person.getName()+"\n年龄:"+person.getAge());
	}

	@Override
	public void updateDevPersonMsg(Person person) throws RemoteException
	{
		System.out.println("DEV正在更新dev表中的person信息 :  姓名:"+person.getName()+"\n年龄:"+person.getAge());
	}

}
