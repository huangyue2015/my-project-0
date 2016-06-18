package xml.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import json.pojo.Order;
import json.pojo.OrderFactory;

public class TestOrder
{
	private  ObjectMapper mapper = new ObjectMapper();
	
	private String encode2JSON(Order order) throws JsonProcessingException
	{
		String json = mapper.writeValueAsString(order);
		return json;
	}
	
	private Order decode2Order(String json) throws JsonParseException, JsonMappingException, IOException
	{
		Order order = mapper.readValue(json, Order.class);
		return order;
	}
	
	public static void main(String[] args) throws IOException
	{
		//序列化
		TestOrder t = new TestOrder();
		Order order = OrderFactory.create(123);
		long l1 = System.currentTimeMillis();
		String s = t.encode2JSON(order);
		long r = System.currentTimeMillis()-l1;
		System.out.println( r +"	" +s);
		
		//反序列化
		long l2 = System.currentTimeMillis();
		Order ord = t.decode2Order(s);
		long r1 = System.currentTimeMillis()-l2;
		System.out.println(r1+" 	"+ord.getOrderNumber()+"--"+ ord.getCustomer().getFirstName());
	}
}
