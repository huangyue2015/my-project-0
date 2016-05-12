package com.zookeeper.demo1;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Test
{

	public static void main(String[] args)
	{
		try
		{
			new Test().doZookeeper();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (KeeperException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doZookeeper() throws IOException, KeeperException, InterruptedException
	{
		ZooKeeper zk = new ZooKeeper("192.168.153.131:4181", 500000, new Watcher(){

			//监控所有被触发的条件
			@Override
			public void process(WatchedEvent event)
			{
				System.out.println("do someThing ......");
			}
		});
		
		zk.delete("/root", -1);
		
		//创建一个节点root，数据是mydata,不进行ACL权限控制，节点为永久性的(即客户端shutdown了也不会消失)
		zk.create("/root", "mydqtq".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		//在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的
		zk.create("/root/childone","childone".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		
		//取得/root节点下的子节点名称,返回List<String>
		List<String> childNames = zk.getChildren("/root", true);
		sysOut(childNames);
		
		//取得/root/childone节点下的数据,返回byte[]
		System.out.println(new String(zk.getData("/root/childone", true, null)));
		
		//修改节点/root/childone下的数据，第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
		zk.setData("/root/childone","childonemodify".getBytes(), -1);
		
		//删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
		zk.delete("/root/childone", -1);
		      
		//关闭session
		zk.close();
	}
	
	public void sysOut(List<String> lists)
	{
		for(String s : lists)
			System.out.println(s);
	}
}
