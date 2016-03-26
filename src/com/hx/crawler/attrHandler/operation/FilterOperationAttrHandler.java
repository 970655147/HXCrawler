/**
 * file name : MapOperationAttrHandler.java
 * created at : 12:28:38 PM Mar 26, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.operation;

import com.hx.crawler.attrHandler.StandardHandlerParser.Types;
import com.hx.crawler.attrHandler.operation.interf.OperationAttrHandler;
import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// filterHandler
// 注意 ： 请将结果原样返回
public class FilterOperationAttrHandler extends OperationAttrHandler {
	// 组合的核心业务的handler
	private AttrHandler handler;
	
	// 初始化
	public FilterOperationAttrHandler(AttrHandler handler, Types operationReturn ) {
		super();
		this.handler = handler;
		operationType(Constants.FILTER_OPERATION);
		operationReturn(operationReturn);
	}

	// 注意 ： 不能改变结果
	@Override
	protected String handle0(String result) {
		boolean bool = Boolean.parseBoolean(handler.handle(result) );
		beFiltered(bool);
		if(beFiltered() ) {
			returnMsg("beFiltered while handle : '" + result + "', filterHandler : " + handler.toString() );
			return "beFiltered";
		}
		return result;
	}

	@Override
	public String name() {
		return Constants.FILTER_OPERATION;
	}

}
