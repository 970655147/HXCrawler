/**
 * file name : TwoIntArgsAttrHandler.java
 * created at : 10:51:01 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;


// 零个或者一个字符串参数的AttrHander参数的AttrHandler
public abstract class TwoOrThreeStringTwoBooleanArgsAttrHandler extends ThreeStringTwoBooleanArgsAttrHandler {
	// 传入的用户指定的参数
	protected String target;
	protected String start;
	protected String end;
	protected boolean includeStart;
	protected boolean includeEnd;
	
	// 初始化
	public TwoOrThreeStringTwoBooleanArgsAttrHandler(String target, String start, String end, boolean includeStart, boolean includeEnd) {
		setArgs(target, start, end, includeStart, includeEnd);
	}
	
	// 配置参数
	@Override
	public void setArgs(String target, String start, String end, boolean includeStart, boolean includeEnd) {
		this.target = target;
		this.start = start;
		this.end = end;
		this.includeStart = includeStart;
		this.includeEnd = includeEnd;
	}
	// 重写handle0, 处理逻辑[将核心逻辑留在了gotResult中]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, start, end, includeStart, includeEnd, result);
	}
	
	// 核心业务
	protected abstract String gotResult(String target, String start, String end, boolean includeStart, boolean includeEnd, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("name", name() ).element("target", target).element("start", start).element("end", end)
				.element("includeStart", includeStart).element("includeEnd", includeEnd)
				.toString();
	}
}
