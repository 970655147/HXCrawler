/**
 * file name : Test03TestTools.java
 * created at : 12:59:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.test;

import net.sf.json.JSONObject;

import com.hx.crawler.crawler.HtmlCrawler;
import com.hx.crawler.crawler.interf.ScriptParameter;
import com.hx.crawler.util.Log;
import com.hx.crawler.util.Tools;

public class Test03TestTools {

	// ����Tools��ط���
	public static void main(String []args) throws Exception {
		
		String str = " lkdjfg	 	 lkdfg	lksdf 	lskdfsldf";
		Log.log(Tools.trimAllSpaces(str) );
		
		Log.log(Tools.DEFAULT_CHARSET);
		
		// ��"gbk"��System.outд�����ݲ��ܹ��õ���ȷ�Ľ��, ԭ������System.outĬ�ϵĽ��������ʹ��'��Ŀ�ı���'ô
		Log.log("�й�");
		
		Tools.save(str, Tools.getTmpPath("abc", Tools.TXT), Tools.GBK, true );
		
		ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask = Tools.newSingleUrlTask(HtmlCrawler.getInstance(), "abc.com", new JSONObject().element("key", "value") );
		String fetchedResult = "fetchedResult";
		long start = 1945l;
		
		Tools.logBeforeTask(singleUrlTask, true);
		Tools.logAfterTask(singleUrlTask, fetchedResult, Tools.spentStr(start), true);
		Tools.logErrorMsg(singleUrlTask, new RuntimeException("ac") );

		Tools.assert0(new Exception("abc") );
		
		Log.log(Tools.encapQueryString(new JSONObject().element("key1", "val1").element("key2", "val2")) );
		
		Tools.awaitShutdown();
		
	}
	
}
