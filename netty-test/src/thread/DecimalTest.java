package thread;

import java.math.BigDecimal;

public class DecimalTest
{

	public static void main(String[] args)
	{
		BigDecimal big = addTest("1.2","1.2","1.2","1.2","1.2");
		System.out.println(divi(big, 6));
	}

	/**
	 * 加法
	 */
	public static BigDecimal addTest(String ...args)
	{
		BigDecimal count = new BigDecimal(0);
		for(String s : args)
		{
			BigDecimal beBigDecimal = new BigDecimal(s);
			count = count.add(beBigDecimal);
		}
//		System.out.println(count.doubleValue());
		return count;
	}
	
	/**
	 * 除法
	 */
	public static BigDecimal divi(BigDecimal bigDecimal, int count)
	{
		return bigDecimal.divide(bigDecimal,count);
	}
}
