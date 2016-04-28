package interface_;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import comment.Reslove;
import po.Person;
import rmi.RmiServer;
import system.DynamicFactory;

public class PersonService implements IPerson
{
	protected static final String interfacename = "PersonService";
	
	@Reslove(classPath = "process_.PersonImpl")
	public IPerson person;

	@Override
	public void updatePerson(Person p) throws RemoteException
	{
		try
		{
			if(RmiServer.isLocal(interfacename))
				person.updatePerson(p);
			else
			{
				person = new RmiServer<IPerson>(interfacename).startLookup();
				person.updatePerson(p);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public PersonService(IPerson person)
	{
		this.person = person;
	}
	
	public static PersonService  newInstance() throws RemoteException, MalformedURLException
	{
		PersonService ps = new PersonService(null);
		DynamicFactory<PersonService> dfFactory = new DynamicFactory<PersonService>(ps, IPerson.class);
		ps =  dfFactory.getBean();
		return ps;
	}
}
