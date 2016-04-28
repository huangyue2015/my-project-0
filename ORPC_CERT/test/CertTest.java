import interface_.PersonService;
import po.Person;

public class CertTest
{

	public static void main(String[] args)
	{
		try
		{
			Person ps = new Person();
			ps.setName("张三》》》通过资格来更新人员信息");
			PersonService.newInstance().updatePerson(ps);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
