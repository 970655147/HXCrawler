/**
 * file name : ThreeStringTwoBooleanResultHandler.java
 * created at : 11:58:49 PM May 20, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter;

import net.sf.json.JSONObject;

import com.hx.crawler.attrHandler.ConstantsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.StringIntArgsAttrHandler;
import com.hx.crawler.attrHandler.adapter.interf.ThreeStringTwoBooleanArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

public class ThreeStringTwoBooleanResultHandler extends AttrHandler {
	// 处理数据的handler, 获取参数的handler
	private ThreeStringTwoBooleanArgsAttrHandler handler;
	private AttrHandler target;
	private AttrHandler start;
	private AttrHandler end;
	private AttrHandler includeStart;
	private AttrHandler includeEnd;

	// 初始化
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, AttrHandler target, AttrHandler start, AttrHandler end, AttrHandler includeStart, AttrHandler includeEnd) {
		this.handler = handler;
		this.target = target;
		this.start = start;
		this.end = end;
		this.includeStart = includeStart;
		this.includeEnd = includeEnd;
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, String target, String start, String end, boolean includeStart, boolean includeEnd) {
		this(handler, new ConstantsAttrHandler(target),
				new ConstantsAttrHandler(start),
				new ConstantsAttrHandler(end),
				new ConstantsAttrHandler(String.valueOf(includeStart)),
				new ConstantsAttrHandler(String.valueOf(includeEnd))
		);
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, AttrHandler start, AttrHandler end, AttrHandler includeStart, AttrHandler includeEnd) {
		this.handler = handler;
		this.target = new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED);
		this.start = start;
		this.end = end;
		this.includeStart = includeStart;
		this.includeEnd = includeEnd;
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, String start, String end, boolean includeStart, boolean includeEnd) {
		this(handler,
				new ConstantsAttrHandler(start),
				new ConstantsAttrHandler(end),
				new ConstantsAttrHandler(String.valueOf(includeStart)),
				new ConstantsAttrHandler(String.valueOf(includeEnd))
				);
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, AttrHandler target, AttrHandler start, AttrHandler end) {
		this(handler, target, start, end, new ConstantsAttrHandler("false"), new ConstantsAttrHandler("false") );
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, String target, String start, String end) {
		this(handler, target, start, end, false, false );
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, AttrHandler start, AttrHandler end) {
		this(handler, new ConstantsAttrHandler(Constants.HANDLER_UNDEFINED), start, end, new ConstantsAttrHandler("false"), new ConstantsAttrHandler("false") );
	}
	public ThreeStringTwoBooleanResultHandler(ThreeStringTwoBooleanArgsAttrHandler handler, String start, String end) {
		this(handler, Constants.HANDLER_UNDEFINED, start, end, false, false );
	}
	
	@Override
	public String handle0(String result) {
		handler.setArgs(target.handle(result), start.handle(result), end.handle(result), Boolean.parseBoolean(includeStart.handle(result)), Boolean.parseBoolean(includeEnd.handle(result)) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("handler", handler.toString() ).element("target", target.toString() ).element("start", start.toString() ).element("end", end.toString() )
				.element("includeStart", includeStart).element("includeEnd", includeEnd)
				.toString();
	}
	
}
