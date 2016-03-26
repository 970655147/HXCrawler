/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// ��ȡ�������ַ����ĳ��ȵ�handler
// ������������
// map(toInt )	or map(toInt() )  or map(toInt('1') )
public class ToStringAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// ��ʼ��
	public ToStringAttrHandler(String str) {
		super(str);
	}
	public ToStringAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return str;
	}

	@Override
	public String name() {
		return Constants.TO_STRING;
	}
	
}