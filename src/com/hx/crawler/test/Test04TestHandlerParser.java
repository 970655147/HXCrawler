/**
 * file name : Test04TestHandlerParser.java
 * created at : 3:10:59 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.test;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.interf.HandlerParser;
import com.hx.crawler.util.Log;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser;

public class Test04TestHandlerParser {
	
	// 测试HandlerParser相关方法
	public static void main(String []args) {
		
//		String str = "map(hello + $this).map(equals($this, sdf) )";
//		String str = "map(trim() ).map(subString($this, indexOf($this, length()))).map(a + $this + b)";
//		String str = "map(((trim())) )";
//		String str = "map(((trim().subString())) )";
//		String str = "map(trim().trim() )";
//		String str = "map(trim().subString(1, 3).trimSpaces().length().equals(abc) )";
//		String str = "map(subString(indexOf(this, abc), indexOf(bb) ) )";
//		String str = "map(subString(indexOf('this', 'abc'), indexOf(bb) ) )";
//		String str = "map('a' + 'b' + c + 'd' + e.length )";
//		String str = "map(indexOf('abc', 3) )";
//		String str = "map(abc + trim() + 'sdf' )";
//		String str = "map('abc ' + 'this one is very good' + $this + ' sdf' )";
//		String str = "map((toUpperCase.toLowerCase.subString(1, 3) + 'cape').matches('\\w+') )";
//		String str = "map(($this + ' --sdf-- ' + '$this sd').toUpperCase  )";
//		String str = "map(trim.replace('\\w+', 'x') )";
//		String str = "map(trim.matches('\\w+') )";
//		String str = "map(concate(abc, 'dsf----', $this) )";
//		String str = "map(subString(1, indexOf('st'), 4) )";
//		String str = "sd(subString(1, indexOf('st'), 4) )";
//		String str = "map(subString(trimSpaces().indexOf('|'), trim().indexOf('|') ) )";
//		String str = "map(trimAsOne() )";
		String str = "map(equals(12) )";
		
//		String res = "       te-----             ---|st    ";
//		String res = "te--|st";
		String res = "te-   -|st";
		HandlerParser parser = new StandardHandlerParser();
		AttrHandler handler = parser.handlerParse(str);
		Log.log(handler.handle(res) );
		Log.log(res.substring(Tools.trimAllSpaces(res).indexOf('|'), res.trim().indexOf('|')) );
		
	}
	
}
