/**
 * file name : Values.java
 * created at : 8:03:17 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import java.util.ArrayList;
import java.util.List;

// values 元素
public class Values extends EndPoint {
	
	// values的子元素
	private List<EndPoint> childs;
	
	// 初始化
	public Values(String name, String xpath, EndPoint parent) {
		super(EndPoint.VALUES, name, xpath, parent);
		childs = new ArrayList<>();
	}

	// 添加子元素
	public void addChild(EndPoint endPoint) {
		childs.add(endPoint);
	}
	
	// 获取子元素
	public EndPoint getChild(int idx) {
		return childs.get(idx);
	}
	
	// for debug ...
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append(super.toString() );
		sb.append(", values : [  ");
		for(EndPoint ep : childs) {
			sb.append(ep.toString() );
			sb.append(", ");
		}
		sb.replace(sb.length() - 2, sb.length(), " ]");
		sb.append(" }");
		
		return sb.toString();
	}

	// values 的孩子个数为childs的结点数目[不用递归]
	public int childSize() {
		return childs.size();
	}
	
	// 获取attr [values 不支持此操作]
	public String getAttribute() {
		throw new RuntimeException("unsupported operation exception ...");
	}

}
