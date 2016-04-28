package interface_;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.Person;

public interface IDev extends Remote
{
	void updateDev(Person person) throws RemoteException;
	void updateDevPersonMsg(Person person) throws RemoteException;
}
