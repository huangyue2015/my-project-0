package system.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityBean<K, V> extends HashMap<K, V>
{
	private static final long serialVersionUID = 1L;

	public int getInt(K k)
	{
		Object o = this.get(k);
		if(o != null)
			return Integer.valueOf(o.toString());
		else
			return 0;
	} 
	
	public String getString(K k)
	{
		Object o = this.get(k);
		if(o != null)
			return (String) o;
		else
			return null;
	}
	
//	public boolean insert()
//	{
//		
//	}
//	
	public boolean insert(Object o)
	{
		try
		{
			Map<String,Object> map = reflecx(o);
			if(map != null && map.size() > 0)
			{
				for(Iterator<String> i = map.keySet().iterator(); i.hasNext();)
				{
					String key = i.next();
					Object value = map.get(key);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public  Map<String,Object> reflecx(Object o) throws IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		Map<String,Object> map = new HashMap<>();
		Class<?> c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields)
		{
			comment.EntityBean eb = f.getAnnotation(comment.EntityBean.class);
			if (eb != null)
			{
			    f.setAccessible(true);
			    if(f.get(o) != null)
			    	map.put(f.getName(), f.get(o));
			}
		}
		return map;
	}
}
