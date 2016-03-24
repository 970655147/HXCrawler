/**
 * file name : AttrHandler.java
 * created at : 1:03:05 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.interf;

import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.StandardHandlerParser.Types;

// handler��صĳ���
public abstract class AttrHandler {

	// ��ǰAttrHandler�Ĳ���
	protected String operationType;
	protected Types operationReturn;
	
	// ��ʼ��
	public AttrHandler() {
		
	}
	
	// ����ҵ��[ģ�巽��]
	public String handle(String result) {
		Tools.assert0(result != null, "result can't be null !");
		String res = handle0(result);
		
		return res;
	}
	
	// sett & getter
	public void operationType(String operationType) {
		this.operationType = operationType;
	}
	public String operationType() {
		return this.operationType;
	}
	public Types operationReturn() {
		return operationReturn;
	}
	public void operationReturn(Types operationReturn) {
		this.operationReturn = operationReturn;
	}

	// ���Ĵ����������ַ���, ��ȡhandler������
	protected abstract String handle0(String result);
	public abstract String name();
	
}