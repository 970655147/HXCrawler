/**
 * file name : TwoIntArgsAttrHandler.java
 * created at : 10:51:01 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter.interf;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;


// �������һ���ַ���������AttrHander������AttrHandler
public abstract class OneOrTwoStringArgsAttrHandler extends TwoStringArgsAttrHandler {
	// ������û�ָ���Ĳ���
	protected String arg01;
	protected String arg02;
	
	// ��ʼ��
	public OneOrTwoStringArgsAttrHandler(String arg01, String arg02) {
		setArgs(arg01, arg02);
	}
	
	// ���ò���
	@Override
	public void setArgs(String arg01, String arg02) {
		this.arg01 = arg01;
		this.arg02 = arg02;
	}
	// ��дhandle0, �����߼�[�������߼�������gotResult��]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(arg02) ) {
			arg02 = arg01;
			arg01 = result;
		}
		
		return gotResult(arg01, arg02, result);
	}
	
	// ����ҵ��
	protected abstract String gotResult(String arg01, String arg02, String result);
	
	@Override
	public String toString() {
		return new JSONObject().element("name", name() ).element("operands", new JSONArray().element(arg01.toString()).element(arg02.toString()) ).toString();
	}
}
