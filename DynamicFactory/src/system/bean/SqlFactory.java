package system.bean;

public class SqlFactory
{
	private SqlFactory()
	{
		
	}
	
	public static String getInsertSql(String[] names,Object[] values,String tableName)
	{
		return String.format("insert into %s%s values%s",tableName, arrayToString(names),arrayToString(values,true));
	}
	
	private static String arrayToString(Object[] arges,boolean flag)
	{
		if(arges == null)
			return null;
		StringBuffer stringBuffer = new StringBuffer("(");
		for(int i = 0; i < arges.length; i++ )
		{
			if(flag && arges[i].getClass().getSimpleName().equals("String"))
				stringBuffer.append(String.format("'%s'", arges[i]));
			else
				stringBuffer.append(arges[i]);
			if(i != arges.length - 1)
				stringBuffer.append(",");
		}
		stringBuffer.append(")");
		return stringBuffer.toString();
	}
	
	private static String arrayToString(Object[] arges)
	{
		if(arges == null)
			return null;
		StringBuffer stringBuffer = new StringBuffer("(");
		for(int i = 0; i < arges.length; i++ )
		{
			stringBuffer.append(arges[i]);
			System.out.println(arges[i].getClass().getSimpleName());
			if(i != arges.length - 1)
				stringBuffer.append(",");
		}
		stringBuffer.append(")");
		return stringBuffer.toString();
	}
	
	public static void main(String[] arge)
	{
		System.out.println(getInsertSql(new String[]{"name","age"}, new Object[]{"张三",12}, "person"));
	}
}
