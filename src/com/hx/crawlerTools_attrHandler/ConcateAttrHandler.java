/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.ArrayList;
import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser.Types;
import com.hx.crawlerTools_attrHandler.adapter.interf.MultiArgsAttrHandler;

// 连接字符串的handler
// map(hello + $this + world)
public class ConcateAttrHandler extends MultiArgsAttrHandler {
	// 初始化
	public ConcateAttrHandler(List<AttrHandler> handlers) {
		super(handlers);
		operationReturn(Types.String);
	}
	public ConcateAttrHandler(int initCap) {
		super(initCap);
		operationReturn(Types.String);
	}
	public ConcateAttrHandler() {
		this(Constants.CONCATE_HANDLER_DEFAULT_CAP);
	}
	
	@Override
	public String handle0(String result) {
		StringBuilder sb = new StringBuilder();
		for(AttrHandler handler : handlers) {
			String res = handler.handle(result);
			if(Constants.RESULT_PROXY.equals(res) ) {
				sb.append(result);
			} else {
				sb.append(res);
			}
		}
		
		return sb.toString();
	}

	@Override
	public String name() {
		return Constants.CONCATE;
	}
	
}
