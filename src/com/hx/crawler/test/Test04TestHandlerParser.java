/**
 * file name : Test04TestHandlerParser.java
 * created at : 3:10:59 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.test;

import com.hx.crawler.attrHandler.StandardHandlerParser;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.attrHandler.interf.HandlerParser;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Log;

public class Test04TestHandlerParser {
	
	// 测试HandlerParser相关方法
	public static void main(String []args) {
		
//		String str = "map(hello + $this).map(equals($this, sdf) )";
//		String str = "map(trim() ).map(subString($this, indexOf($this, length()))).map(a + $this + b)";
//		String str = "map(((trim())) )";
//		String str = "map(((trim().subString())) )";
//		String str = "map(trim().trim() )";
//		String str = "map(trim().subString(1, 3).trimAsOne().length().equals(abc) )";
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
//		String str = "map(length>15?(subString(0,12)+'...'):$this ) ";
//		String str = "map(toBool('bac') )";
//		String str = "map(toString(toInt('7')) )";
//		String str = "map(getStrIn('abs', 'sk', true, false))";
//		String str = "map(getStrIn('abs', 'sk', true, false))";
//		String str = "map(getStrIn('abs' + '', 'sk' + 'fj', true, false))";
//		String str = "map(getStrIn('absdddsk', 'abs', 'sk', true, false))";
//		String str = "map(indexOf('b', 8) )";
//		String str = "map(replace('abcabsabs', 'abs', 'XXX'))";
//		String str = "map(subString('abdff1', 3))";
//		String str = "map(trim )";
//		String str = "map(trim(1, 2) )";
//		String str = "map(trim(34435, 2, 1) )";
//		String str = "map(trimAsOne(1, 2) )";
		String str = "map(trimAsOne('  3443  5  ', 1, 2) )";
//		String str = "map(trimAll('  3443  5  ', 1, 2) )";
		
//		String res = "       te-----             ---|st    ";
//		String res = "te--|st";
		String res = "te-   abslkdjflskfjgf -|st ";
		HandlerParser parser = new StandardHandlerParser();
		AttrHandler handler = parser.handlerParse(str, Constants.HANDLER);
		Log.log(handler.handle(res) );
//		Log.log(res.substring(Tools.trimAllSpaces(res).indexOf('|'), res.trim().indexOf('|')) );
		
	}
	
}
