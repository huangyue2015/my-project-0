import interface_.CertService;
import interface_.ICert;
import rmi.RmiServer;

public class StartCertService extends CertService
{

	public StartCertService(ICert cert)
	{
		super(cert);
	}

	public static void main(String[] args)
	{
		new RmiServer<ICert>(CertService.newInstance().cert, interfacename).startServer();
	}

}
