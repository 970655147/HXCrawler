/**
 * file name : MapOperationAttrHandler.java
 * created at : 12:28:38 PM Mar 26, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.operation;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser.Types;
import com.hx.crawlerTools_attrHandler.operation.interf.OperationAttrHandler;

// mapHandler
public class MapOperationAttrHandler extends OperationAttrHandler {
	// ��ϵĺ���ҵ���handler
	private AttrHandler handler;
	
	// ��ʼ��
	public MapOperationAttrHandler(AttrHandler handler, Types operationReturn ) {
		super();
		this.handler = handler;
		operationType(Constants.MAP_OPERATION);
		operationReturn(operationReturn);
	}

	@Override
	protected String handle0(String result) {
		return handler.handle(result);
	}

	@Override
	public String name() {
		return Constants.MAP_OPERATION;
	}

}
