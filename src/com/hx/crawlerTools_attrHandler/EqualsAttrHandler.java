/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// �жϸ������ַ����Ƿ��expectƥ���handler
// map(equals('abc') )
public class EqualsAttrHandler extends OneStringArgsAttrHandler {
	// �������õ��ĳ���ֵ
	private String expect;
	
	// ��ʼ��
	public EqualsAttrHandler(String expect) {
		this.expect = expect;
	}
	public EqualsAttrHandler() {
		this(null);
	}
	
	@Override
	public String handle0(String result) {
		if(expect == null) {
			if(result == null) {
				return Constants.TRUE;
			} else {
				return Constants.FALSE;
			}
		}
		return String.valueOf(expect.equals(result) );
	}

	@Override
	public String name() {
		return Constants.EQUALS;
	}

	@Override
	public void setArgs(String arg) {
		this.expect = arg;
	}
	
}
