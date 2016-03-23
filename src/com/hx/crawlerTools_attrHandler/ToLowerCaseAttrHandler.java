/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// ��ȡ�������ַ�����Сд��ʽ��Handler
// map(toLowerCase )
public class ToLowerCaseAttrHandler extends AttrHandler {
	// ��ʼ��
	public ToLowerCaseAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return result.toLowerCase();
	}

	@Override
	public String name() {
		return Constants.TO_LOWERCASE;
	}
	
}
