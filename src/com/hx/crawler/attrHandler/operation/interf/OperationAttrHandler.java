/**
 * file name : OperationAttrHandler.java
 * created at : 12:02:34 PM Mar 26, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.operation.interf;

import com.hx.crawler.attrHandler.StandardHandlerParser.Types;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Log;
import com.hx.crawler.util.Tools;

// ����AttrHandler[map, filter, ..]
public abstract class OperationAttrHandler extends AttrHandler {
	// ��ǰAttrHandler�Ĳ���, ����ֵ, beFiltered assertFalse�ı�־λ, ��Ҫ���ص���Ϣ
	protected String operationType;
	protected Types operationReturn;
	protected int returnFlag;
	protected String returnMsg;
	
	// ��filter��[��1λΪ1] ����assert�ж�ʧ��[��2λΪ1]�ı�־λ
	// ..0001,	..0010
	public final static int BE_FILTERED = 1;
	public final static int ASSERT_FALSE = BE_FILTERED << 1;
	
	// ��ʼ��
	public OperationAttrHandler() {
//		beFiltered(false);
//		assertFalse(false);
		cleanImmediateReturnFlag();
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
		return (this.returnFlag & BE_FILTERED) != 0;
	}
	public void beFiltered(boolean beFiltered) {
		// set 'BE_FILTERED' as true
		if(beFiltered) {
			this.returnFlag |= BE_FILTERED;
		// set 'BE_FILTERED' as false
		} else {
			this.returnFlag &= (~ BE_FILTERED);
		}
	}
	public boolean assertFalse() {
		return (this.returnFlag & ASSERT_FALSE) != 0;
	}
	public void assertFalse(boolean assertFalse) {
		// set 'ASSERT_FALSE' as true
		if(assertFalse) {
			this.returnFlag |= ASSERT_FALSE;
		} else {
		// set 'ASSERT_FALSE' as false
			this.returnFlag &= (~ ASSERT_FALSE);
		}
	}
	protected void returnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String returnMsg() {
		return returnMsg;
	}
	
	// �ж��Ƿ���Ҫ��ʱ����
	public boolean immediateReturn() {
//		return beFiltered() || assertFalse();
		return returnFlag != 0;
	}
	public void cleanImmediateReturnFlag() {
		returnFlag = 0;
	}
	protected void updateImmediateReturnFlag(OperationAttrHandler operationHandler) {
		if(operationHandler.beFiltered() ) {
			beFiltered(operationHandler.beFiltered() );
		} else if(operationHandler.assertFalse() ) {
			assertFalse(operationHandler.assertFalse() );
		}
	}
	public void handleImmediateReturn() {
		if(beFiltered() ) {
			System.out.println(returnMsg);
		} else if(assertFalse() ) {
			Tools.assert0(returnMsg);
		}
	}
	
}
