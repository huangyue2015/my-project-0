package com.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	
	public boolean insert(Object o)
	{
		return insert(o,o.getClass().getSimpleName());
	}
	
	public boolean insert(Object o,String tableName)
	{
		try
		{
			Map<String,Object> map = reflecx(o);
			if(map != null && map.size() > 0)
			{
				List<String> names = new ArrayList<>();
				List<Object> values = new ArrayList<>();
				for(Iterator<String> i = map.keySet().iterator(); i.hasNext();)
				{
					String key = i.next();
					Object value = map.get(key);
					names.add(key);
					values.add(value);
				}
				String sql = SqlFactory.getInsertSql(names.toArray(new String[names.size()]), values.toArray(new Object[values.size()]), tableName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private  Map<String,Object> reflecx(Object o) throws IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		Map<String,Object> map = new HashMap<>();
		Class<?> c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields)
		{
			com.core.comment.EntityBean eb = f.getAnnotation(com.core.comment.EntityBean.class);
			if (eb != null)
			{
			    f.setAccessible(true);
			    if(f.get(o) != null)
			    	map.put(f.getName(), f.get(o));
			}
		}
		return map;
	}
	
	public Object dealSql(String sql)
	{
		return null;
	}
}
