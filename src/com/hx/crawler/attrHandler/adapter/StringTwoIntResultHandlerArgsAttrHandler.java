/**
 * file name : StringTwoIntResultHandlerArgsAttrHandler.java
 * created at : 3:21:28 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter;

import net.sf.json.JSONObject;

import com.hx.crawler.attrHandler.ConstantsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.StringTwoIntAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

public class StringTwoIntResultHandlerArgsAttrHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	protected StringTwoIntAttrHandler handler;
	protected AttrHandler target;
	protected AttrHandler from;
	protected AttrHandler to;
	
	// 初始化
	public StringTwoIntResultHandlerArgsAttrHandler(StringTwoIntAttrHandler handler, AttrHandler target, AttrHandler from, AttrHandler to) {
		this.handler = handler;
		this.target = target;
		this.from = from;
		this.to = to;
	}
	public StringTwoIntResultHandlerArgsAttrHandler(StringTwoIntAttrHandler handler, String target, int from, int to) {
		this(handler, new ConstantsAttrHandler(target), new ConstantsAttrHandler(from), new ConstantsAttrHandler(to) );
	}
	public StringTwoIntResultHandlerArgsAttrHandler(StringTwoIntAttrHandler handler, AttrHandler from, AttrHandler to) {
		this(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), from, to );
	}
	public StringTwoIntResultHandlerArgsAttrHandler(StringTwoIntAttrHandler handler, int from, int to) {
		this(handler, Constants.HANDLER_UNDEFINED, from, to );
	}

	@Override
	public String handle0(String result) {
		handler.setArgs(target.handle(result), Integer.parseInt(from.handle(result)), Integer.parseInt(to.handle(result)) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("handler", handler.toString() ).element("target", target.toString() ).element("from", from.toString() )
				.element("to", to.toString() )
				.toString();
	}
}
