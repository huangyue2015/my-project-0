package interface_;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.Person;

public interface IPerson extends Remote
{
	void updatePerson(Person person) throws RemoteException;
}
