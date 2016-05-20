/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter;

import net.sf.json.JSONObject;

import com.hx.crawler.attrHandler.ConstantsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.TwoIntArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 构造两个int参数的AttrHandler的Handler适配器
// map(trim)
public class TwoIntResultHandlerArgsAttrHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	private TwoIntArgsAttrHandler handler;
	private AttrHandler arg01;
	private AttrHandler arg02;
	
	// 初始化
	// 6个方法
	// (arg01, arg02)	([arg01], [arg02])
	// (arg01, [arg02])	([arg01], arg02)
	// (arg01)			([arg01])
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, AttrHandler start, AttrHandler end) {
		this.handler = handler;
		this.arg01 = start;
		this.arg02 = end;
	}
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, int start, int end) {
		this(handler, new ConstantsAttrHandler(String.valueOf(start)),
				new ConstantsAttrHandler(String.valueOf(end)) );
	}
	
	@Override
	public String handle0(String result) {
		handler.setArgs(Integer.parseInt(arg01.handle(result)), Integer.parseInt(arg02.handle(result)) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("handler", handler.toString() ).element("arg01", arg01.toString() ).element("arg02", arg02.toString() )
				.toString();
	}
}
