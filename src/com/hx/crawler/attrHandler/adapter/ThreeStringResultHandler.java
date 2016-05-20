/**
 * file name : ThreeStringResultHandler.java
 * created at : 2:52:49 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter;

import net.sf.json.JSONObject;

import com.hx.crawler.attrHandler.ConstantsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.ThreeStringArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

public class ThreeStringResultHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	private ThreeStringArgsAttrHandler handler;
	private AttrHandler target;
	private AttrHandler pattern;
	private AttrHandler replacement;

	// 初始化
	public ThreeStringResultHandler(ThreeStringArgsAttrHandler handler, AttrHandler target, AttrHandler pattern, AttrHandler replacement) {
		this.handler = handler;
		this.target = target;
		this.pattern = pattern;
		this.replacement = replacement;
	}
	public ThreeStringResultHandler(ThreeStringArgsAttrHandler handler, String target, String pattern, String replacement) {
		this(handler, new ConstantsAttrHandler(target),
				new ConstantsAttrHandler(pattern),
				new ConstantsAttrHandler(replacement)
		);
	}
	public ThreeStringResultHandler(ThreeStringArgsAttrHandler handler, AttrHandler pattern, AttrHandler replacement) {
		this(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), pattern, replacement );
	}
	public ThreeStringResultHandler(ThreeStringArgsAttrHandler handler, String pattern, String replacement) {
		this(handler, Constants.HANDLER_UNDEFINED, pattern, replacement);
	}
	
	@Override
	public String handle0(String result) {
		handler.setArgs(target.handle(result), pattern.handle(result), replacement.handle(result) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("handler", handler.toString() ).element("target", target.toString() ).element("pattern", pattern.toString() ).element("replacement", replacement.toString() )
				.toString();
	}
	
}
