package com.common.server;

import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.common.entity.PR;
import com.common.util.Cache;

public class SMSHandler {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PR pr;
		try {
			pr = new SMSHandler().sendOneSMS("18665351397", "您的短信验证码为: 7401");
			System.out.println(pr.getResultstate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public PR sendOneSMS(String mobile,String ...contents) throws Exception
	{
		if(Cache.mobileSendLockMap.containsKey(mobile))
		{
			if(Cache.mobileSendLockMap.get(mobile) >= 10)
				throw new Exception("当天发送短信的次数已完");
			Cache.mobileSendLockMap.put(mobile, Cache.mobileSendLockMap.get(mobile)+1);
		}
		else
		{
			HashMap<String, Object> result = null;

			CCPRestSDK restAPI = new CCPRestSDK();
			restAPI.init(DevelopersAccount.REST_ADDRESS, DevelopersAccount.REST_PORT);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
			restAPI.setAccount(DevelopersAccount.ACCOUNT_SID, DevelopersAccount.AUTH_TOKEN);// 初始化主帐号和主帐号TOKEN
			restAPI.setAppId(DevelopersAccount.AppID);// 初始化应用ID
			result = restAPI.sendTemplateSMS(mobile,"1" ,contents);

			System.out.println("SDKTestSendTemplateSMS result=" + result);
			if("000000".equals(result.get("statusCode"))){
				//正常返回输出data包体信息（map）
				HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for(String key:keySet){
					Object object = data.get(key);
					System.out.println(key +" = "+object);
				}
			}else{
				//异常返回输出错误码和错误信息
				System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
				return new PR(0, "", result.get("statusMsg").toString());
			}
			Cache.mobileSendLockMap.put(mobile, 1);
		}
		return new PR(1, "", "");
		
	}
}
