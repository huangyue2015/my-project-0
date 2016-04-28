import interface_.DevService;
import interface_.IDev;
import rmi.RmiServer;

public class StartDevService extends DevService
{

	public StartDevService(IDev dev)
	{
		super(dev);
	}

	public static void main(String[] args)
	{
		new RmiServer<IDev>(DevService.newInstance().dev, interfacename).startServer();
	}

}
