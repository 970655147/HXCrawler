/**
 * file name : OneOrTwoStringIntArgsAttrHandler.java
 * created at : 2:04:25 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;

public abstract class OneOrTwoStringIntArgsAttrHandler extends TwoStringIntArgsAttrHandler {
	// 传入的用户指定的参数
	protected String target;
	protected String idxStr;
	protected int from;
	
	// 初始化
	public OneOrTwoStringIntArgsAttrHandler(String target, String idxStr, int from) {
		setArgs(target, idxStr, from);
	}
	
	// 配置参数
	@Override
	public void setArgs(String target, String idxStr, int from) {
		this.target = target;
		this.idxStr = idxStr;
		this.from = from;
	}
	// 重写handle0, 处理逻辑[将核心逻辑留在了gotResult中]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, idxStr, from, result);
	}
	
	// 核心业务
	protected abstract String gotResult(String target, String idxStr, int from, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("target", target).element("idxStr", idxStr).element("from", from)
				.toString();
	}
}
