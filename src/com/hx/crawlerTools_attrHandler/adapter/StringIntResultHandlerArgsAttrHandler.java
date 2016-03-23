/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.ConstantsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.StringIntArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.TwoIntArgsAttrHandler;

// 截取字符串的handler
// map(trim)
public class StringIntResultHandlerArgsAttrHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	private StringIntArgsAttrHandler handler;
	private AttrHandler arg01;
	private AttrHandler arg02;
	
	// 初始化
	public StringIntResultHandlerArgsAttrHandler(StringIntArgsAttrHandler handler, AttrHandler arg01, AttrHandler arg02) {
		this.handler = handler;
		this.arg01 = arg01;
		this.arg02 = arg02;
	}
	public StringIntResultHandlerArgsAttrHandler(StringIntArgsAttrHandler handler, String arg01, int arg02) {
		this(handler, new ConstantsAttrHandler(String.valueOf(arg01)),
				new ConstantsAttrHandler(String.valueOf(arg02)) );
	}
	public StringIntResultHandlerArgsAttrHandler(StringIntArgsAttrHandler handler, AttrHandler arg01, int arg02) {
		this(handler, arg01, new ConstantsAttrHandler(String.valueOf(arg02)) );
	}
	public StringIntResultHandlerArgsAttrHandler(StringIntArgsAttrHandler handler, String arg01, AttrHandler arg02) {
		this(handler, new ConstantsAttrHandler(String.valueOf(arg01)), arg02);
	}
	public StringIntResultHandlerArgsAttrHandler(StringIntArgsAttrHandler handler, AttrHandler arg01) {
		this(handler, arg01, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
	}
	public StringIntResultHandlerArgsAttrHandler(StringIntArgsAttrHandler handler, String arg01) {
		this(handler, new ConstantsAttrHandler(String.valueOf(arg01)) );
	}

	@Override
	public String handle0(String result) {
		handler.setArgs(arg01.handle(result), Integer.parseInt(arg02.handle(result)) );
		return handler.handle(result);
	}


	@Override
	public String name() {
		return handler.name();
	}
	
}
