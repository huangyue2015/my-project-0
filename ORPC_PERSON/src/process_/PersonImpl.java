package process_;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interface_.IPerson;
import jms.JmsHandler;
import po.Person;

public class PersonImpl extends UnicastRemoteObject implements IPerson
{
	private static final long serialVersionUID = 1L;
	
	public PersonImpl() throws RemoteException
	{
	}
	
	@Override
	public void updatePerson(Person person) throws RemoteException
	{
		System.out.println("PERSON:  姓名:"+person.getName()+"\n年龄:"+person.getAge());
		new Thread(new JmsHandler<Person>("PersonService", person)).start();
	}

}
