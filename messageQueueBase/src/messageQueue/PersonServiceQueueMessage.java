package messageQueue;

import java.util.Map;

import coreManage.QueueManage;
import jms.IMessageDeal;
import jms.JmsReceive;
import po.Person;

public class PersonServiceQueueMessage
{
	private static final String serviceName = "PersonService";
	
	public static void main(String[] args)
	{
		new PersonServiceQueueMessage().dealMessage();
	}
	
	public void dealMessage()
	{
		JmsReceive jmsReceive = new JmsReceive(serviceName,new IMessageDeal()
		{
			@Override
			public void addMessage(Object obj)
			{
				System.out.println("添加成功");
				@SuppressWarnings("unchecked")
				Map<String,Person> map = (Map<String, Person>) obj;
				Person p = map.get(serviceName);
				System.out.println(p.getName()+p.getAge());
				new QueueManage.QueueHamdler(serviceName).addQueue(p);
			}
		});
		new Thread(jmsReceive).start();
	}
}
