package com.util;

import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import system.bean.EntityBean;

@SuppressWarnings("rawtypes")
public class Serialization
{

	public static void main(String[] args)
	{

	}

	public static EntityBean toEntityBean(HttpServletRequest req)
	{
		return getMap(req);
	}

	public static EntityBean getMap(HttpServletRequest request)
	{
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		EntityBean returnMap = new EntityBean();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext())
		{
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj)
			{
				value = "";
			}
			else if (valueObj instanceof String[])
			{
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++)
				{
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			}
			else
			{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
