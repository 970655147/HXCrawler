/**
 * file name : CuttingOutAndAttrHandler.java
 * created at : 6:39:00 PM Mar 24, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import java.util.List;

import com.hx.crawler.attrHandler.adapter.interf.MultiArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// 短路与
// and(left, right), left && right
public class CuttingOutOrAttrHandler<T extends AttrHandler> extends MultiArgsAttrHandler<T> {
	// 初始化
	public CuttingOutOrAttrHandler(List<T> handlers) {
		super(handlers);
	}
	public CuttingOutOrAttrHandler(int initCap) {
		super(initCap);
	}
	public CuttingOutOrAttrHandler() {
		this(Constants.CUTTING_DOOR_HANDLER_DEFAULT_CAP);
	}
	
	@Override
	protected String handle0(String result) {
		for(AttrHandler handler : handlers) {
			if(Constants.TRUE.equals(handler.handle(result)) ) {
				return Constants.TRUE;
			}
		}
		
		return Constants.FALSE;
	}
	
	@Override
	public String name() {
		return Constants.CUTTING_OUT_OR;
	}
	
}
