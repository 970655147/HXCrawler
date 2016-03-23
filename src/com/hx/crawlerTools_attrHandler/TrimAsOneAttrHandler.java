/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// ���ַ����Ķ���ո�ϲ�Ϊһ����handler
// map(trimAsOne)
public class TrimAsOneAttrHandler extends AttrHandler {
	// ��ʼ��
	public TrimAsOneAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return Tools.trimSpacesAsOne(result);
	}

	@Override
	public String name() {
		return Constants.TRIM_AS_ONE;
	}
	
}
