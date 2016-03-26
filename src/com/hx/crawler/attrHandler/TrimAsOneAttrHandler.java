/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// ���ַ����Ķ���ո�ϲ�Ϊһ����handler
// map(trimAsOne), map(trimAsOne('abc') )
public class TrimAsOneAttrHandler extends NoneOrOneStringArgsAttrHandler {
	
	// ��ʼ��
	public TrimAsOneAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}
	public TrimAsOneAttrHandler(String str) {
		super(str);
	}
	
	@Override
	protected String gotResult(String str, String result) {
		return Tools.trimSpacesAsOne(str);
	}

	@Override
	public String name() {
		return Constants.TRIM_AS_ONE;
	}
	
}
