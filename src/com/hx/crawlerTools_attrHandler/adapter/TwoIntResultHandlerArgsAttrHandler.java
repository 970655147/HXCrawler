/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.ConstantsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.TwoIntArgsAttrHandler;

// 截取字符串的handler
// map(trim)
public class TwoIntResultHandlerArgsAttrHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	private TwoIntArgsAttrHandler handler;
	private AttrHandler start;
	private AttrHandler end;
	
	// 初始化
	// 6个方法
	// (arg01, arg02)	([arg01], [arg02])
	// (arg01, [arg02])	([arg01], arg02)
	// (arg01)			([arg01])
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, AttrHandler start, AttrHandler end) {
		this.handler = handler;
		this.start = start;
		this.end = end;
	}
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, int start, int end) {
		this(handler, new ConstantsAttrHandler(String.valueOf(start)),
				new ConstantsAttrHandler(String.valueOf(end)) );
	}
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, AttrHandler start, int end) {
		this(handler, start, new ConstantsAttrHandler(String.valueOf(end)) );
	}
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, int start, AttrHandler end) {
		this(handler, new ConstantsAttrHandler(String.valueOf(start)), end);
	}
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, AttrHandler start) {
		this(handler, start, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED) );
	}
	public TwoIntResultHandlerArgsAttrHandler(TwoIntArgsAttrHandler handler, int start) {
		this(handler, new ConstantsAttrHandler(String.valueOf(start)) );
	}
	
	@Override
	public String handle0(String result) {
		handler.setArgs(Integer.parseInt(start.handle(result)), Integer.parseInt(end.handle(result)) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
}
