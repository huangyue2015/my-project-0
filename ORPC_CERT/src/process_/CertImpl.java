package process_;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interface_.ICert;
import po.Person;

public class CertImpl extends UnicastRemoteObject implements ICert 
{
	private static final long serialVersionUID = 1L;
	public CertImpl() throws RemoteException
	{
	}

	private static final boolean flag = false;
	@Override
	public void updteCertMesssage(Person person) throws RemoteException
	{
		try
		{
			System.out.println("CERT :  姓名:"+person.getName()+"\n年龄:"+person.getAge());
			//PersonService.newInstance(flag).updatePerson(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void uodateCertPerssonMsg(Person person) throws RemoteException
	{
		System.out.println("CERT正在更新cert表中的person信息 :  姓名:"+person.getName()+"\n年龄:"+person.getAge());
	}
}
