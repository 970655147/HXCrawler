/**
 * file name : OneOrTwoStringIntArgsAttrHandler.java
 * created at : 2:02:45 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import com.hx.crawler.attrHandler.interf.AttrHandler;

// �����ַ���, һ��int��AttrHandler
public abstract class TwoStringIntArgsAttrHandler extends AttrHandler {

	// ������������
	public abstract void setArgs(String arg01, String arg02, int arg03);
	
}
