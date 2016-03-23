/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.interf.AttrHandler;
import com.hx.crawler.util.Constants;

// ȥ���ַ���ǰ��ո��handler
// map(trim)
public class TrimAttrHandler extends AttrHandler {
	// ��ʼ��
	public TrimAttrHandler() {
	}

	@Override
	public String handle0(String result) {
		return result.trim();
	}

	@Override
	public String name() {
		return Constants.TRIM;
	}
	
}
