/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.ArrayList;
import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// �����ַ�����handler
// map(hello + $this + world)
public class ConcateAttrHandler extends AttrHandler {
	// �����Ľ��ǰ����Ҫ��ӵ��ַ���
	private List<AttrHandler> handlers;
	
	// ��ʼ��
	public ConcateAttrHandler(List<AttrHandler> handlers) {
		this.handlers = handlers;
	}
	public ConcateAttrHandler(int initCap) {
		this.handlers = new ArrayList<>(initCap);
	}
	public ConcateAttrHandler() {
		this(Constants.CONCATE_HANDLER_DEFAULT_CAP);
	}
	
	// ���handler
	public void addHandler(AttrHandler handler) {
		this.handlers.add(handler);
	}
	
	@Override
	public String handle0(String result) {
		StringBuilder sb = new StringBuilder();
		for(AttrHandler handler : handlers) {
			String res = handler.handle(result);
			if(Constants.RESULT_PROXY.equals(res) ) {
				sb.append(result);
			} else {
				sb.append(res);
			}
		}
		
		return sb.toString();
	}

	@Override
	public String name() {
		return Constants.CONCATE;
	}
	
}
