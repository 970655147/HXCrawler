/**
 * file name : EndPointHandler.java
 * created at : 2:02:38 PM Feb 2, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import org.dom4j.Element;

// �����������Endpoint��ʱ��, �������߼�
public abstract class EndPointHandler {

	public abstract void handle(Element root, Element currentEle, String url, JSONArray res, int idx, EndPoint child, JSON curObj);
	
}
