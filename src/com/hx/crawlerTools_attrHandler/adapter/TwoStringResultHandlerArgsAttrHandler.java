/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.ConstantsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.TwoStringArgsAttrHandler;

// 构造两个字符串参数的AttrHandler的Handler适配器
// 作为适配器内部使用
public class TwoStringResultHandlerArgsAttrHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	private TwoStringArgsAttrHandler handler;
	private AttrHandler arg01;
	private AttrHandler arg02;
	
	// 初始化
	public TwoStringResultHandlerArgsAttrHandler(TwoStringArgsAttrHandler handler, AttrHandler arg01, AttrHandler arg02) {
		this.handler = handler;
		this.arg01 = arg01;
		this.arg02 = arg02;
	}
	public TwoStringResultHandlerArgsAttrHandler(TwoStringArgsAttrHandler handler, AttrHandler arg01) {
		this(handler, arg01, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
	}
	public TwoStringResultHandlerArgsAttrHandler(TwoStringArgsAttrHandler handler, String arg01) {
		this(handler, new ConstantsAttrHandler(arg01) );
	}
	public TwoStringResultHandlerArgsAttrHandler(TwoStringArgsAttrHandler handler,  AttrHandler arg01, String arg02) {
		this(handler, arg01, new ConstantsAttrHandler(arg02) );
	}
	public TwoStringResultHandlerArgsAttrHandler(TwoStringArgsAttrHandler handler,  String arg01, AttrHandler arg02) {
		this(handler, new ConstantsAttrHandler(arg01), arg02 );
	}
	public TwoStringResultHandlerArgsAttrHandler(TwoStringArgsAttrHandler handler,  String arg01, String arg02) {
		this(handler, new ConstantsAttrHandler(arg01), new ConstantsAttrHandler(arg02) );
	}
	
	@Override
	public String handle0(String result) {
		handler.setArgs(arg01.handle(result), arg02.handle(result) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
	@Override
	public String toString() {
		return new JSONObject().element("handler", handler.toString() ).element("arg01", arg01.toString() ).element("arg02", arg02.toString() ).toString();
	}
}
