/**
 * file name : HandlerParser.java
 * created at : 1:04:16 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.interf;

import com.hx.crawler.attrHandler.StandardHandlerParser.Types;
import com.hx.crawler.attrHandler.operation.interf.OperationAttrHandler;

// ����handler, ��"handle"����, ����ΪAttrHandler��, ���д���
public abstract class HandlerParser {

	public AttrHandler handlerParse(String handlerStr, String handlerType) {
		return handlerParse(handlerStr, handlerType, null);
	}
	
	// ������Ҫ��д��ҵ�񷽷�
	public abstract OperationAttrHandler handlerParse(String handlerStr, String handlerType, Types lastOperationReturn);
	
}