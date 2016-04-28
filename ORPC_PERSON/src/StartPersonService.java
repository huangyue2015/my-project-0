import java.net.MalformedURLException;
import java.rmi.RemoteException;

import interface_.IPerson;
import interface_.PersonService;
import rmi.RmiServer;

public class StartPersonService extends PersonService
{

	public StartPersonService(IPerson person)
	{
		super(person);
	}

	public static void main(String[] args)
	{
		PersonService ps;
		try
		{
			ps = PersonService.newInstance();
			new RmiServer<IPerson>(ps.person,interfacename).startServer();
		}
		catch (RemoteException | MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

}
