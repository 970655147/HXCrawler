/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// ��ȡ�������ַ����ĳ��ȵ�handler
// map(length )	or map(length() )
public class LengthAttrHandler extends AttrHandler {
	// ��ʼ��
	public LengthAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return String.valueOf(result.length() );
	}

	@Override
	public String name() {
		return Constants.TO_UPPERCASE;
	}
	
}
