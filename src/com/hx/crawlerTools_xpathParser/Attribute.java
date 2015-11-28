/**
 * file name : Attribute.java
 * created at : 7:57:53 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

// attribute结点
public class Attribute extends EndPoint {
	
	// attribute[text, innertext, innerhtml, outerhtml]
	private String attr;
	
	// 初始化
	public Attribute(String name, String xpath, String attr, EndPoint parent) {
		super(EndPoint.ATTRIBUTE, name, xpath, parent);
		this.attr = attr;
	}

	// 添加子元素[attribute 不支持添加子节点]
	public void addChild(EndPoint endPoint) {
		throw new RuntimeException("unsupported operation exception ...");
	}
	
	// 获取子元素[attribute 没有子元素]
	public EndPoint getChild(int idx) {
		throw new RuntimeException("unsupported operation exception ...");
	}
	
	// for debug ...
	public String toString() {
		return "{ " + super.toString() + ", attribute : " + attr + " }";
	}

	// Attribute结点的孩子个数为0
	public int childSize() {
		return 0;
	}
	
	// 获取attr
	public String getAttribute() {
		return attr;
	}

}
