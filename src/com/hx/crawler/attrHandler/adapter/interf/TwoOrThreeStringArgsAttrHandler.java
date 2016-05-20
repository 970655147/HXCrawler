/**
 * file name : TwoOrThreeStringArgsAttrHandler.java
 * created at : 2:49:50 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;

public abstract class TwoOrThreeStringArgsAttrHandler extends ThreeStringArgsAttrHandler {
	// 传入的用户指定的参数
	protected String target;
	protected String pattern;
	protected String replacement;
	
	// 初始化
	public TwoOrThreeStringArgsAttrHandler(String target, String pattern, String replacement) {
		setArgs(target, pattern, replacement);
	}
	
	// 配置参数
	@Override
	public void setArgs(String target, String pattern, String replacement) {
		this.target = target;
		this.pattern = pattern;
		this.replacement = replacement;
	}
	// 重写handle0, 处理逻辑[将核心逻辑留在了gotResult中]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, pattern, replacement, result);
	}
	
	// 核心业务
	protected abstract String gotResult(String target, String start, String end, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("name", name() ).element("target", target).element("pattern", pattern).element("replacement", replacement)
				.toString();
	}
}
