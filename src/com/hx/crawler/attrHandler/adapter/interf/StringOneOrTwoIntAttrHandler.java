/**
 * file name : StringOneOrTwoIntAttrHandler.java
 * created at : 3:18:39 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;

public abstract class StringOneOrTwoIntAttrHandler extends StringTwoIntAttrHandler {

	// 传入的用户指定的参数
	protected String target;
	protected int from;
	protected int to;
	
	// 初始化
	public StringOneOrTwoIntAttrHandler(String target, int from, int to) {
		setArgs(target, from, to);
	}
	
	// 配置参数
	@Override
	public void setArgs(String target, int from, int to) {
		this.target = target;
		this.from = from;
		this.to = to;
	}

	// 重写handle0, 处理逻辑[将核心逻辑留在了gotResult中]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, from, to, result);
	}
	
	// 核心业务
	protected abstract String gotResult(String target, int from, int to, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("name", name() ).element("target", target).element("from", from).element("to", to)
				.toString();
	}
	
}
