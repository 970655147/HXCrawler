/**
 * file name : EndPoint.java
 * created at : 7:42:13 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

// 一个EndPint[终端结点 : "values" & "attribute"]
public abstract class EndPoint {
	
	// 常量
	public static String VALUES = Constants.VALUES;
	public static String ATTRIBUTE = Constants.ATTRIBUTE;
	
	// 类型, 名称, xpath
	// 注意 : 对于values来说, xpath必需存在
		// attribute来说, xpath不必须
	private String type;
	private String name;
	private String xpath;
	private EndPoint parent;
	
	// 初始化
	public EndPoint(String type, String name, String xpath, EndPoint parent) {
		this.type = type;
		this.name = name;
		this.parent = parent;
		this.xpath = xpath;
		
//		if(xpath != null) {
//			this.xpath = Tools.getXPath(this, xpath);
//		}
	}

	// 添加孩子, 孩子的个数
	public abstract void addChild(EndPoint endPoint);
	public abstract EndPoint getChild(int idx);
	public abstract int childSize();
	public abstract String getAttribute();
	
	// for debug ...
	public String toString() {
		return "name : " + name + ", xpath : " + xpath;
	}
	
	// getter
	public EndPoint getParent() {
		return parent;
	}
	public String getXPath() {
		return xpath;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	
	// 通过传入的xpath, 获取真实的xpath
	// 如果xpath以 / 开头[/, //], 则直接返回xpath
	// 否则如果以.开头[./]  拼接上parent结点的xpath
	// 否则  表示xpath不存在, 或者不合理, 返回ep的父节点的xpath
	protected static String getXPath(EndPoint ep, String xpath) {
		xpath = xpath.trim();
		if(xpath.startsWith("/") ) {
			return xpath;
		} else if(xpath.startsWith(".")) {
			StringBuilder sb = new StringBuilder(ep.getParent().getXPath().length() + xpath.length() + 1);
			sb.append(ep.getParent().getXPath() );
			makeSureNotEndsWithInvSlash(sb);
			sb.append(xpath, 1, xpath.length());
			return sb.toString();
		}
		
		return ep.getParent().getXPath();
	}

	// 确保xpath 不以"/" 结尾
	protected static void makeSureNotEndsWithInvSlash(StringBuilder xPath) {
		if(xPath.charAt(xPath.length()-1) == Constants.INV_SLASH) {
			xPath.deleteCharAt(xPath.length() - 1);
		}
	}
	
}
