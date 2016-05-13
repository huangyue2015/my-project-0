package com.hbase.canal.pool.db.bo;

/**
 * 这是外部可以配置的连接池属性 可以允许外部配置，拥有默认值
 * 
 */
public class DBBean
{
	//数据库类型
	private String	type = "PostgreSQL";
	//默认数据库名称
	private String	db = "test";
	private String	uri = "localhost";
	private String	port = "5432";
	private String	uid ="postgres";
	private String	pwd = "123456";
	private int		minpool = 3;
	private int		maxpool = 300;
	private int		initpool = 5;
	//数据库连接池的默认名称
	private String	poolName = "pool";

	public String getPoolName()
	{
		return poolName;
	}

	public void setPoolName(String poolName)
	{
		this.poolName = poolName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDb()
	{
		return db;
	}

	public void setDb(String db)
	{
		this.db = db;
	}

	public String getUri()
	{
		return uri;
	}

	public void setUri(String uri)
	{
		this.uri = uri;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public int getMinpool()
	{
		return minpool;
	}

	public void setMinpool(int minpool)
	{
		this.minpool = minpool;
	}

	public int getMaxpool()
	{
		return maxpool;
	}

	public void setMaxpool(int maxpool)
	{
		this.maxpool = maxpool;
	}

	public int getInitpool()
	{
		return initpool;
	}

	public void setInitpool(int initpool)
	{
		this.initpool = initpool;
	}

	public DBBean(String type, String db, String uri, String port, String uid, String pwd, int minpool, int maxpool, int initpool, String poolname)
	{
		this.type = type;
		this.db = db;
		this.uri = uri;
		this.port = port;
		this.uid = uid;
		this.pwd = pwd;
		this.minpool = minpool;
		this.maxpool = maxpool;
		this.initpool = initpool;
		this.poolName = poolname;
	}

	public DBBean(String db)
	{
		this.db = db;
	}
}
