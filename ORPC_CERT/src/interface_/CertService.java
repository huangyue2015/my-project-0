package interface_;

import java.rmi.RemoteException;

import comment.Reslove;
import po.Person;
import rmi.RmiServer;
import system.DynamicFactory;

public class CertService implements ICert
{
	protected static final String interfacename = "CertService";
	
	public CertService(ICert cert)
	{
		this.cert = cert;
	}
	
	@Reslove(classPath = "process_.CertImpl")
	public ICert cert;
	
	@Override
	public void updteCertMesssage(Person person) throws RemoteException
	{
		try
		{
			if(RmiServer.isLocal(interfacename))
				cert.updteCertMesssage(person);
			else
				new RmiServer<ICert>(interfacename).startLookup().updteCertMesssage(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void uodateCertPerssonMsg(Person person) throws RemoteException
	{
		try
		{
			if(RmiServer.isLocal(interfacename))
				cert.uodateCertPerssonMsg(person);
			else
				new RmiServer<ICert>(interfacename).startLookup().uodateCertPerssonMsg(person);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static CertService  newInstance()
	{
		DynamicFactory<CertService> dfFactory = new DynamicFactory<CertService>(new CertService(null), ICert.class);
		return  dfFactory.getBean();
	}
}
