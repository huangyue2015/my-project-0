package com.hbase.canal.core;

public abstract class Meta
{
	abstract String getTableName();
	abstract String[] getMetaData();
	
	static class Qualification extends Meta
	{
		public static String[] METADATA = {"id","cardtype","cardno","cardno2","personname","birthday","sex",
				"edulevel","nations","accounttype","gettype","qualificationtype","qualificationstate",
				"qualificationno","examtime","providetime","avaidate","printflag","printtime","orgno",
				"companyno","orgsyscode","areaid","provideorgno","printedno","qurl","remark1","remark2",
				"cyzg","workingareaid","domicile","xiangzhen","cid","belongorgno","creator","createtime",
				"updater","updatetime","piccardfile","cloudappid","publishtype","dinvalidtype","jobtitle","orgname"};
		
		public static String tableName = "qualification";
		
		@Override
		public String getTableName()
		{
			return tableName;
		}

		@Override
		String[] getMetaData()
		{
			return METADATA;
		}
	}
}
