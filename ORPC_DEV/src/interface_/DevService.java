package interface_;

import java.rmi.RemoteException;

import comment.Reslove;
import po.Person;
import rmi.RmiServer;
import system.DynamicFactory;

public class DevService implements IDev
{
	protected static final String interfacename = "DevService";
	
	public DevService(IDev dev)
	{
		this.dev = dev;
	}
	
	@Reslove(classPath = "process_.DevImpl")
	public IDev dev;
	
	@Override
	public void updateDev(Person person) throws RemoteException
	{
		try
		{
			if(RmiServer.isLocal(interfacename))
				dev.updateDev(person);
			else
				new RmiServer<IDev>(interfacename).startLookup().updateDev(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateDevPersonMsg(Person person) throws RemoteException
	{
		try
		{
			if(RmiServer.isLocal(interfacename))
				dev.updateDevPersonMsg(person);
			else
				new RmiServer<IDev>(interfacename).startLookup().updateDevPersonMsg(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static DevService  newInstance()
	{
		DynamicFactory<DevService> dfFactory = new DynamicFactory<DevService>(new DevService(null), IDev.class);
		return dfFactory.getBean();
	}
}
