/**
 * file name : GreaterThanAttrHandler.java
 * created at : 5:19:32 PM Mar 24, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneOrTwoStringArgsAttrHandler;

//判断给定的字符串是否和expect匹配的handler
//gt(2), gt(2, 1), 2 > 1
public class LessThanAttrHandler extends OneOrTwoStringArgsAttrHandler {
	
	// 初始化
	public LessThanAttrHandler(String arg01, String arg02) {
		super(arg01, arg02);
	}
	public LessThanAttrHandler(String arg01) {
		this(arg01, Constants.HANDLER_UNDEFINED);
	}
	public LessThanAttrHandler() {
		this(Constants.HANDLER_UNDEFINED, Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String arg01, String arg02, String result) {
		Tools.assert0(arg01 != null, "error while calc the 'matches(String)', pattern be initialized illegal ! ");
		Tools.assert0(arg02 != null, "error while calc the 'matches(String)', pattern be initialized illegal ! ");
		int intArg01 = 0, intArg02 = 0;
		boolean valid = true;
		try {
			intArg01 = Integer.parseInt(arg01);
			intArg02 = Integer.parseInt(arg02);
		} catch(Exception e) {
			valid = false;
		}
		if(valid) {
			return String.valueOf(intArg01 < intArg02);
		}
		
		return String.valueOf(arg01.compareTo(arg02) < 0 );
	}
	
	@Override
	public String name() {
		return Constants.GREATER_THAN;
	}
}
