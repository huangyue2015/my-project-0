package system;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

import comment.Reslove;

public class DynamicFactory<T extends Remote>
{
	private T	t;
	private Class<? extends Remote> c;
	
	/**
	 * 
	 * @param t 注解所在的类的对象
	 * @param c 接口的class对象
	 */
	public DynamicFactory(T t,Class<? extends Remote> c)
	{
		this.c = c;
		this.t = t;
	}
	
	public DynamicFactory()
	{
	}
	
	/**
	 * 解析注解 通过注解来解析真实的对象地址
	 * 
	 * @return
	 */
	private List<keyValue<String,Field>> analysisNote(Class<? extends Object> c)
	{
		List<keyValue<String,Field>> list = new ArrayList<>();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields)
		{
			Reslove re = f.getAnnotation(Reslove.class);
			if (re != null)
			{
				keyValue<String,Field> kv = new keyValue<String, Field>(re.classPath(), f);
				list.add(kv);
			}
		}
		return list;
	}

	public synchronized void invokObj(String classpath,Field file)
	{
		try
		{
			Class<? extends Object> _c = Class.forName(classpath);
			Object o = Proxy.newProxyInstance(c.getClassLoader(), new Class[]{ c }, new ProxyHandler(_c.newInstance()));
			file.setAccessible(true);
			file.set(this.t, o);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public T getBean()
	{
		List<keyValue<String,Field>> ls = analysisNote(t.getClass());
		for(keyValue<String,Field> kv : ls)
		{
			invokObj(kv.getKey(), kv.getValue());
		}
		return this.t;
	}
	
	public class keyValue<K,V>
	{
		private K key;
		private V value;
		
		public keyValue(K key,V value)
		{
			this.key = key;
			this.value = value;
		}

		public K getKey()
		{
			return key;
		}

		public void setKey(K key)
		{
			this.key = key;
		}

		public V getValue()
		{
			return value;
		}

		public void setValue(V value)
		{
			this.value = value;
		}
	}
	
}
