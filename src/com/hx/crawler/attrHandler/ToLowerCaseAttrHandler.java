/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.NoneOrOneStringArgsAttrHandler;
import com.hx.crawler.attrHandler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// ��ȡ�������ַ�����Сд��ʽ��Handler
// map(toLowerCase ), map(toLowerCase('abc') )
public class ToLowerCaseAttrHandler extends NoneOrOneStringArgsAttrHandler {
	// ��ʼ��
	public ToLowerCaseAttrHandler(String str) {
		super(str);
	}
	public ToLowerCaseAttrHandler() {
		this(Constants.HANDLER_UNDEFINED);
	}

	@Override
	protected String gotResult(String str, String result) {
		return str.toLowerCase();
	}

	@Override
	public String name() {
		return Constants.TO_LOWERCASE;
	}
	
}
