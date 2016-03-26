/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneBooleanArgsAttrHandler;

// ��������[boo]ת��Ϊ[!boo]��handler
// map(not('abc') )
public class NotAttrHandler extends OneBooleanArgsAttrHandler {
	// �������õ��ĳ���ֵ
	private boolean boo;
	
	// ��ʼ��
	public NotAttrHandler(boolean  boo) {
		this.boo =  boo;
	}
	public NotAttrHandler() {
		this(false);
	}
	
	@Override
	public String handle0(String result) {
		if(boo) {
			return Constants.FALSE;
		} else {
			return Constants.TRUE;
		}
	}

	@Override
	public String name() {
		return Constants.NOT;
	}

	@Override
	public void setArgs(boolean arg) {
		this.boo = arg;
	}
	
}
