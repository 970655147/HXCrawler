/**
 * file name : HandlerParser.java
 * created at : 1:04:16 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.interf;

import java.util.List;

// ����handler, ��"handle"����, ����ΪAttrHandler��, ���д���
public abstract class HandlerParser {

	public abstract AttrHandler handlerParse(String handler);
	
}
