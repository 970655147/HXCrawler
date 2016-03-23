/**
 * file name : Test03TestTools.java
 * created at : 12:59:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.test;

import com.hx.crawler.util.Log;
import com.hx.crawler.util.Tools;

public class Test03TestTools {

	// 测试Tools相关方法
	public static void main(String []args) {
		
		String str = " lkdjfg	 	 lkdfg	lksdf 	lskdfsldf";
		Log.log(Tools.trimAllSpaces(str) );
		
	}
	
}
