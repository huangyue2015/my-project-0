package com.common.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.common.util.Cache;
import com.common.util.Globle;

public class InitCache extends AbstractInitSystem{
	
	
	
	@Override
	public void init()
	{
		/**
		 * 刷新验证码缓存
		 */
		Globle.excute(new MobileSendLockMap());
	}
	
	class MobileSendLockMap implements Runnable
	{
		@Override
		public void run() {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
			String[] ss = simpleDateFormat.format(date).split(":");
			int h = 24 - Integer.valueOf(ss[0]);
			int m = 60 - Integer.valueOf(ss[1]);
			long sss = h* 60 *60 * 1000 + m * 60 * 1000;
			
			while (true) {
				try 
				{
					if(logger.isInfoEnabled())
						logger.info("系统将于"+sss+"秒后刷新手机验证码限制缓存");
					Thread.sleep(sss);
					Cache.mobileSendLockMap.clear();
					sss = 24 * 60 * 60 * 1000 ;
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
		
	}
}
