/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 返回res的AttrHandler
// 适配常量
public class ConstantsAttrHandler extends AttrHandler {
	// 处理结果得到的常量值
	private String res;
	
	// 初始化
	public ConstantsAttrHandler(String res) {
		this.res = res;
		if(Constants.escapeCharMap.containsKey(res.charAt(0)) && Constants.escapeCharMap.containsKey(res.charAt(res.length()-1)) ) {
			this.res = this.res.substring(1, this.res.length()-1 );
		}
	}

	@Override
	public String handle0(String result) {
		if(Constants.RECOGNIZE_RESULT_PROXY) {
			if(Constants.RESULT_PROXY.equals(res) ) {
				return result;
			}
		}
		
		return res;
	}

	@Override
	public String name() {
		return Constants.CONSTANTS;
	}

	@Override
	public String toString() {
		return new JSONObject().element("name", name() ).element("operands", res).toString();
	}
}
