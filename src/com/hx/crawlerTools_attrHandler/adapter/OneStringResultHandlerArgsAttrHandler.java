/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler.adapter;

import net.sf.json.JSONObject;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawlerTools_attrHandler.ConstantsAttrHandler;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// ����һ���ַ���������AttrHandler��Handler������
// map(trim)
public class OneStringResultHandlerArgsAttrHandler extends AttrHandler {
	// �������ݵ�handler, ��ȡ������handler
	private OneStringArgsAttrHandler handler;
	private AttrHandler arg;
	
	// ��ʼ��
	public OneStringResultHandlerArgsAttrHandler(OneStringArgsAttrHandler handler, AttrHandler arg) {
		this.handler = handler;
		this.arg = arg;
	}
	public OneStringResultHandlerArgsAttrHandler(OneStringArgsAttrHandler handler, String arg) {
		this(handler, new ConstantsAttrHandler(arg) );
	}
	
	@Override
	public String handle0(String result) {
		handler.setArgs(arg.handle(result) );
		return handler.handle(result);
	}

	@Override
	public String name() {
		return handler.name();
	}
	
	@Override
	public String toString() {
		return new JSONObject().element("handler", handler.toString() ).element("arg", arg.toString() ).toString();
	}
}
