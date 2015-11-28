/**
 * file name : EndPoint.java
 * created at : 7:42:13 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

// һ��EndPint[�ն˽�� : "values" & "attribute"]
public abstract class EndPoint {
	
	// ����
	public static String VALUES = Constants.VALUES;
	public static String ATTRIBUTE = Constants.ATTRIBUTE;
	
	// ����, ����, xpath
	// ע�� : ����values��˵, xpath�������
		// attribute��˵, xpath������
	private String type;
	private String name;
	private String xpath;
	private EndPoint parent;
	
	// ��ʼ��
	public EndPoint(String type, String name, String xpath, EndPoint parent) {
		this.type = type;
		this.name = name;
		this.parent = parent;
		this.xpath = xpath;
		
//		if(xpath != null) {
//			this.xpath = Tools.getXPath(this, xpath);
//		}
	}

	// ��Ӻ���, ���ӵĸ���
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
	
	// ͨ�������xpath, ��ȡ��ʵ��xpath
	// ���xpath�� / ��ͷ[/, //], ��ֱ�ӷ���xpath
	// ���������.��ͷ[./]  ƴ����parent����xpath
	// ����  ��ʾxpath������, ���߲�����, ����ep�ĸ��ڵ��xpath
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

	// ȷ��xpath ����"/" ��β
	protected static void makeSureNotEndsWithInvSlash(StringBuilder xPath) {
		if(xPath.charAt(xPath.length()-1) == Constants.INV_SLASH) {
			xPath.deleteCharAt(xPath.length() - 1);
		}
	}
	
}
