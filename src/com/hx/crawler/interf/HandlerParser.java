/**
 * file name : HandlerParser.java
 * created at : 1:04:16 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.interf;

import com.hx.crawlerTools_attrHandler.StandardHandlerParser.Types;
import com.hx.crawlerTools_attrHandler.operation.interf.OperationAttrHandler;

// 解析handler, 将"handle"属性, 解析为AttrHandler链, 进行处理
public abstract class HandlerParser {

	public AttrHandler handlerParse(String handlerStr, String handlerType) {
		return handlerParse(handlerStr, handlerType, null);
	}
	
	// 核心需要重写的业务方法
	public abstract OperationAttrHandler handlerParse(String handlerStr, String handlerType, Types lastOperationReturn);
	
}
