/**
 * file name : HandlerParser.java
 * created at : 1:04:16 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.interf;

import java.util.List;

// 解析handler, 将"handle"属性, 解析为AttrHandler链, 进行处理
public abstract class HandlerParser {

	public abstract AttrHandler handlerParse(String handler);
	
}
