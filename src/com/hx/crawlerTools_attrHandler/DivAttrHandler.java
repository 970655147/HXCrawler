/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.List;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawlerTools_attrHandler.adapter.interf.MultiArgsAttrHandler;

// ���������ַ����滻ΪĿ���ַ���
// map(replace(src, tar) )
public class DivAttrHandler extends MultiArgsAttrHandler<AttrHandler> {
	
	// ��ʼ��
	public DivAttrHandler(List<AttrHandler> handlerChain) {
		super(handlerChain);
	}
	public DivAttrHandler(int initCap) {
		super(initCap);
	}
	public DivAttrHandler() {
		super(Constants.CALC_HANDLER_DEFAULT_CAP);
	}

	@Override
	public String handle0(String result) {
		int res = Integer.parseInt(handlers.get(0).handle(result) );
		for(int i=1; i<handlers.size(); i++) {
			res /= Integer.parseInt(handlers.get(i).handle(result) );
		}
		
		return String.valueOf(res);
	}

	@Override
	public String name() {
		return Constants.DIV;
	}
}
