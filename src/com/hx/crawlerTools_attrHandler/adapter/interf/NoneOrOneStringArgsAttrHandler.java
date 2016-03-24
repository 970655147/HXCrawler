/**
 * file name : TwoIntArgsAttrHandler.java
 * created at : 10:51:01 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter.interf;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;


// 零个或者一个字符串参数的AttrHander参数的AttrHandler
public abstract class NoneOrOneStringArgsAttrHandler extends OneStringArgsAttrHandler {
	// 传入的用户指定的参数
	protected String arg;
	
	// 初始化
	public NoneOrOneStringArgsAttrHandler(String str) {
		setArgs(str);
	}
	
	// 配置参数
	public void setArgs(String arg) {
		this.arg = arg;
	}
	// 重写handle0, 处理逻辑[将核心逻辑留在了gotResult中]
	public String handle0(String result) {
		if(! Constants.HANDLER_UNDEFINED.equals(arg) ) {
			return gotResult(arg, result);
		}
		
		return gotResult(result, result);
	}
	
	// 核心业务
	protected abstract String gotResult(String arg, String result);
	
}
