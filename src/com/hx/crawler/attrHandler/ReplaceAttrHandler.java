/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.TwoStringArgsAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// ���������ַ����滻ΪĿ���ַ���
// map(replace(src, tar) )
public class ReplaceAttrHandler extends TwoStringArgsAttrHandler {
	// �滻��ģʽ, �滻�Ľ��
	private String regex;
	private String replacement;
	
	// ��ʼ��
	public ReplaceAttrHandler(String regex, String replacement) {
		setArgs(regex, replacement);
	}
	public ReplaceAttrHandler() {
		this(Constants.HANDLER_UNDEFINED, Constants.HANDLER_UNDEFINED);
	}

	@Override
	public String handle0(String result) {
		Tools.assert0(! Constants.HANDLER_UNDEFINED.equals(regex), "error while calc the 'replace(String, String)', 'regex' be initialized illegal ! ");
		Tools.assert0(! Constants.HANDLER_UNDEFINED.equals(replacement), "error while calc the 'matches(String, String)', 'replacement' be initialized illegal ! ");
		return result.replaceAll(regex, replacement);
	}

	@Override
	public String name() {
		return Constants.REPLACE;
	}
	
	@Override
	public void setArgs(String arg1, String arg2) {
		this.regex = arg1;
		this.replacement = arg2;
	}
	
}
