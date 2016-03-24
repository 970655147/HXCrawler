/**
 * file name : CuttingOutAndAttrHandler.java
 * created at : 6:39:00 PM Mar 24, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.MultiArgsAttrHandler;

// 短路与
// and(left, right), left && right
public class CuttingOutAndAttrHandler extends MultiArgsAttrHandler {
	// 初始化
	public CuttingOutAndAttrHandler(List<AttrHandler> handlers) {
		super(handlers);
	}
	public CuttingOutAndAttrHandler(int initCap) {
		super(initCap);
	}
	public CuttingOutAndAttrHandler() {
		super(Constants.CUTTING_DOOR_HANDLER_DEFAULT_CAP);
	}
	
	@Override
	protected String handle0(String result) {
		for(AttrHandler handler : handlers) {
			if(Constants.FALSE.equals(handler.handle(result)) ) {
				return Constants.FALSE;
			}
		}
		
		return Constants.TRUE;
	}
	
	@Override
	public String name() {
		return Constants.CUTTING_OUT_AND;
	}
	
}
