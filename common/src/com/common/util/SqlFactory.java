package com.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.entity.POJO;

public class SqlFactory<T extends POJO> {


	public String getInsertSql(T t) throws Exception
	{
		String result = null;
		String tableName = t.getClass().getSimpleName();
		Map<String,String> map = t.getMap();
		if(map.isEmpty())
			throw new Exception("没有需要插入的数据");
		if(StringUtil.isNullOrEmpty(map.get("id")))
			map.put("id", StringUtil.getUUID());
		Pair<String, String> pair = getPairFormMap(map);
		String fields = "("+ pair.getKey() + ")";
		String values = pair.getValue().replaceAll(",", "','");
		values = "('"+ values + "')";
		result = String.format("insert into %s%s values%s", tableName,fields,values);
		return result;
	} 
	
	public String getSelectSql(T t) throws Exception
	{
		String tableName = t.getClass().getSimpleName();
		StringBuilder result = new StringBuilder("UPDATE "+tableName+" SET  ");
		Map<String,String> map = t.getMap();
		if(map.isEmpty())
			throw new Exception("没有需要更新的数据");
		if(StringUtil.isNullOrEmpty(map.get("id")))
			throw new Exception("更新的数据是主键不能为空");
		
		List<String> list = new ArrayList<>();
		for(Entry<String, String> entry : map.entrySet())
		{
			String value = entry.getValue();
			String key = entry.getKey();
			if(StringUtil.isNotNullOrEmpty(value))
			{
				if(!"id".equals(key))
				{
					String s = String.format("%s=%s", key,"'"+value+"'");
					list.add(s);
				}
			}
		}
		result.append(StringUtil.getStringByList(list));
		result.append(String.format("	where id = '%s' ", map.get("id")));
		System.out.println(result.toString());
		return result.toString();
	}
	
	
	
	private Pair<String, String> getPairFormMap(Map<String,String> map)
	{
		List<String> keys = new ArrayList<>();
		List<String> values = new ArrayList<>();
		
		for(Entry<String, String> entry : map.entrySet())
		{
			String value = entry.getValue();
			String key = entry.getKey();
			if(StringUtil.isNotNullOrEmpty(value))
			{
				keys.add(key);
				values.add(value);
			}
		}
		
		String key = StringUtil.getStringByList(keys);
		String value = StringUtil.getStringByList(values);
		return new Pair<String, String>(key, value);
	}
}
