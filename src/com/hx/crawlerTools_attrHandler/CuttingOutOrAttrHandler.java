/**
 * file name : CuttingOutAndAttrHandler.java
 * created at : 6:39:00 PM Mar 24, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser.Types;
import com.hx.crawlerTools_attrHandler.adapter.interf.MultiArgsAttrHandler;

// ��·��
// and(left, right), left && right
public class CuttingOutOrAttrHandler extends MultiArgsAttrHandler {
	// ��ʼ��
	public CuttingOutOrAttrHandler(List<AttrHandler> handlers) {
		super(handlers);
		operationReturn(Types.Boolean);
	}
	public CuttingOutOrAttrHandler(int initCap) {
		super(initCap);
		operationReturn(Types.Boolean);
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
