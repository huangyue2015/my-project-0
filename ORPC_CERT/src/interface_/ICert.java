package interface_;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.Person;

public interface ICert extends Remote
{
	void updteCertMesssage(Person person) throws RemoteException;
	void uodateCertPerssonMsg(Person person) throws RemoteException;
}
