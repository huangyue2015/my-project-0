package system.bean.pool;

public class DatabaseParameter
{
    private String driverClassName = "org.postgresql.Driver";
    private String ip              = "localhost";
    private int    port            = 5432;
    private String dbName          = "postgres";
    private String username        = "postgres";
    private String password        = "longrise";
    private Integer maxStatementPerConnection = 10;
    
    //////////
    private int initConnection = 3;
    private String poolName = "p";
    //////////
    public DatabaseParameter()
    {
    }
    
    public DatabaseParameter( String dbName )
    {
        this.dbName = dbName;
    }

    public DatabaseParameter( String ip , int port , String dbName , String username , String password )
    {
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }
    
    public DatabaseParameter( String ip , int port , String dbName , String username , String password, Integer maxStatementPerConnection )
    {
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.maxStatementPerConnection = maxStatementPerConnection;
    }

    
    
    public DatabaseParameter(String driverClassName, String ip, int port, String dbName, String username, String password, Integer maxStatementPerConnection, int initConnection)
	{
		this.driverClassName = driverClassName;
		this.ip = ip;
		this.port = port;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
		this.maxStatementPerConnection = maxStatementPerConnection;
		this.initConnection = initConnection;
	}

    
    
    
	public DatabaseParameter(String driverClassName, String ip, int port, String dbName, String username, String password, Integer maxStatementPerConnection, int initConnection, String poolName)
	{
		this.driverClassName = driverClassName;
		this.ip = ip;
		this.port = port;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
		this.maxStatementPerConnection = maxStatementPerConnection;
		this.initConnection = initConnection;
		this.poolName = poolName;
	}

	
	
	public String getPoolName()
	{
		return poolName;
	}

	public void setPoolName(String poolName)
	{
		this.poolName = poolName;
	}

	public int getInitConnection()
	{
		return initConnection;
	}

	public void setInitConnection(int initConnection)
	{
		this.initConnection = initConnection;
	}

	public String getConnectionUrl()
    {
        return "jdbc:postgresql://" + ip + ":" + port + "/" + dbName;
    }
    /**
     * @return the driverClassName
     */
    public String getDriverClassName ()
    {
        return driverClassName;
    }

    /**
     * @param driverClassName the driverClassName to set
     */
    public void setDriverClassName ( String driverClassName )
    {
        this.driverClassName = driverClassName;
    }

    /**
     * @return the ip
     */
    public String getIp ()
    {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp ( String ip )
    {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort ()
    {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort ( int port )
    {
        this.port = port;
    }

    /**
     * @return the dbName
     */
    public String getDbName ()
    {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName ( String dbName )
    {
        this.dbName = dbName;
    }

    /**
     * @return the username
     */
    public String getUsername ()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername ( String username )
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword ()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword ( String password )
    {
        this.password = password;
    }

    public Integer getMaxStatementPerConnection ()
    {
        return maxStatementPerConnection;
    }

    public void setMaxStatementPerConnection ( Integer maxStatementPerConnection )
    {
        this.maxStatementPerConnection = maxStatementPerConnection;
    }

}
