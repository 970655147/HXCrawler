/**
 * file name : OperationAttrHandler.java
 * created at : 12:02:34 PM Mar 26, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.operation.interf;

import com.hx.crawler.attrHandler.StandardHandlerParser.Types;
import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Log;
import com.hx.crawler.util.Tools;

// 操作AttrHandler[map, filter, ..]
public abstract class OperationAttrHandler extends AttrHandler {
	// 当前AttrHandler的操作
	protected String operationType;
	protected Types operationReturn;
	protected boolean beFiltered;
	protected boolean assertTrue;
	protected String returnMsg;
	
	// 初始化
	public OperationAttrHandler() {
		beFiltered(false);
		assertTrue(true);
	}
	
	// sett & getter
	protected void operationType(String operationType) {
		this.operationType = operationType;
	}
	public String operationType() {
		return this.operationType;
	}
	public Types operationReturn() {
		return operationReturn;
	}
	protected void operationReturn(Types operationReturn) {
		this.operationReturn = operationReturn;
	}
	public boolean beFiltered() {
		return beFiltered;
	}
	public void beFiltered(boolean beFiltered) {
		this.beFiltered = beFiltered;
	}
	public boolean assertTrue() {
		return assertTrue;
	}
	public void assertTrue(boolean assertTrue) {
		this.assertTrue = assertTrue;
	}
	protected void returnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String returnMsg() {
		return returnMsg;
	}
	
	// 判断是否需要即时返回
	public boolean immediateReturn() {
		return beFiltered() || (! assertTrue());
	}
	public void cleanImmediateReturnFlag() {
		if(beFiltered() ) {
			beFiltered(false );
		} else if(! assertTrue() ) {
			assertTrue(true );
		}
	}
	protected void updateImmediateReturnFlag(OperationAttrHandler operationHandler) {
		if(operationHandler.beFiltered() ) {
			beFiltered(operationHandler.beFiltered() );
		} else if(! operationHandler.assertTrue() ) {
			assertTrue(operationHandler.assertTrue() );
		}
	}
	public void handleImmediateReturn() {
		if(beFiltered() ) {
			Log.log(returnMsg);
		} else if(! assertTrue() ) {
			Tools.assert0(returnMsg);
		}
	}
	
}
