/**
 * file name : Test04TestHandlerParser.java
 * created at : 3:10:59 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.test;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.interf.HandlerParser;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Log;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser;

public class Test04TestHandlerParser {
	
	// ����HandlerParser��ط���
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
//		String str = "map((not(!!!equals('abc'))) )";
//		String str = "map((equals('abc', 'abc')) )";
//		String str = "map(trimAll(' sdf sdf  d') + '..' )";
//		String str = "map(eq(2, 2) )";
					// afalse
//		String str = "map( a + b == a + b )";
//		String str = "map( (a + b) == (a + b) )";
//		String str = "map(length('add') == 2 )";
//		String str = "map(2 != 2)";
//		String str = "map(and(true, true) )";
//		String str = "map(tr + true && true )";
//		String str = "map(sf + 3 > 2 + sdf )";
//		String str = "map((false || true && true || false) && true && contains(c) )";
//		String str = "map(false ? true : false )";
//		String str = "map((3 >= 3) ? 'abc' + abc > a : 'sdf' )";
//		String str = "filter(true ).map(sdf)";
//		String str = "map(add(18, 5, 7) ) ";
//		String str = "map(add(toInt('2'), 6, 9) ) ";
		String str = "map(length>15?(subString(0,12)+'...'):$this ) ";
		
//		String res = "       te-----             ---|st    ";
//		String res = "te--|st";
		String res = "te-   abslkdjflskfjgf -|st";
		HandlerParser parser = new StandardHandlerParser();
		AttrHandler handler = parser.handlerParse(str, Constants.MAP_HANDLER);
		Log.log(handler.handle(res) );
//		Log.log(res.substring(Tools.trimAllSpaces(res).indexOf('|'), res.trim().indexOf('|')) );
		
	}
	
}
