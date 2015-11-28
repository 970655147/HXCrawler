/**
 * file name : Parser.java
 * created at : 8:49:07 AM Jul 26, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Parser {
	
	// ��rootΪ���ڵ�, ����xpath�ַ���[ָ���ĸ�ʽ]
	public static JSONArray parse(Element root, String url, String xpath) {
		JSONArray res = new JSONArray();
		IndexString idxStr = new IndexString(xpath);
		
		EndPoint rootEp = idxStr.getRoot();
		parse0(root, root, url, rootEp, res, 0);
		
		return res;
	}
	public static JSONArray parse(String html, String url, String xpath) throws Exception {
		SAXReader saxReader = new SAXReader();
		Element root = saxReader.read(new StringReader(html)).getRootElement();
		return parse(root, url, xpath);
	}

	// ����target EndPoint
	// �������ep���, һ�ν���ÿһ����������, ��������res��
	private static void parse0(Element root, Element currentEle, String url, EndPoint ep, JSONArray res, int idx) {
		JSONObject curObj = new JSONObject();
		for(int i=0; i<ep.childSize(); i++) {
			EndPoint child = ep.getChild(i);
			if(EndPoint.ATTRIBUTE.equals(child.getType()) ) {
				if(child.getXPath() != null ) {
//					List<Element> eles = getResultByXPath(root, currentEle, child.getXPath());
					int idx2 = 0;
					
					// ͳһֻ��ȡ��һ��Ԫ�ص�����
//					if(eles.size() == 1) {
//						curObj.element(child.getName(), getValueByAttribute(eles.get(0), child.getAttribute(), idx2) );
//					} else {
//						JSONArray attrVals = new JSONArray();
//						for(Element ele : eles) {
//							attrVals.add(getValueByAttribute(ele, child.getAttribute(), (idx2 ++)) );
//						}
//						curObj.element(child.getName(), attrVals);
//					}
					
					Element ele = getSingleResultByXPath(root, currentEle, child.getXPath());
					curObj.element(child.getName(), getValueByAttribute(ele, child.getAttribute(), url, idx2) );
				} else {
					curObj.element(child.getName(), getValueByAttribute(currentEle, child.getAttribute(), url, idx) );
				}
			} else if(EndPoint.VALUES.equals(child.getType()) ) {
				JSONArray curArr = new JSONArray();
				List<Element> eles = getResultByXPath(root, currentEle, child.getXPath() );
				int idx2 = 0;
				for(Element ele : eles) {
					parse0(root, ele, url, child, curArr, idx2++);
				}
				
				curObj.element(child.getName(), curArr);
			}
		}
		
		res.add(curObj);
	}

	// ͨ��xpathץȡԪ��
	private static List<Element> getResultByXPath(Element root, Element currentEle, String xPath) {
		if(xPath.startsWith("/")) {
			return root.selectNodes(xPath);
		} else if(xPath.startsWith(".")) {
			return currentEle.selectNodes(xPath);
		}
		
		return null;
	}
	// ͨ��xpathץȡ��һ��Ԫ��
	private static Element getSingleResultByXPath(Element root, Element currentEle, String xPath) {
		if(xPath.startsWith("/")) {
			return (Element) root.selectSingleNode(xPath);
		} else if(xPath.startsWith(".")) {
			return (Element) currentEle.selectSingleNode(xPath);
		}
		
		return null;
	}
	
	// �������ӵ�����
	static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// ͨ��element ��attribute���� ��ȡ���Ӧ��ֵ
	private static String getValueByAttribute(Element ele, String attribute, String url, int idx) {
		if(Constants.INDEX.equals(attribute)) {
			return String.valueOf(idx);
		}
		if(ele == null) {
			return Constants.NULL;
		}
		if(Constants.TEXT.equals(attribute)) {
			return ele.getText();
		} else if(Constants.INNER_TEXT.equals(attribute)) {
			StringBuilder sb = new StringBuilder();
			getInnerText(ele, sb);
			return sb.toString();
		} else if(Constants.INNER_HTML.equals(attribute)) {
			StringBuilder sb = new StringBuilder();
			getInnerHtml(ele, sb);
			return sb.toString();
		} else if(Constants.OUTER_HTML.equals(attribute)) {
			StringBuilder sb = new StringBuilder();
			getOuterHtml(ele, sb);
			return sb.toString();
		}
		
		String res = ele.attributeValue(attribute, Constants.ATTR_NOT_SUPPORT);
		if((! res.equals(Constants.ATTR_NOT_SUPPORT)) && links.contains(attribute) ) {
			res = Tools.transformUrl(url, res);
		}
		return res;
	}

	// ��ȡele��innertext [���ǲ��ܽ���ӱ�ǩ�������ߵ����ֵ�λ�ù�ϵ������]
	// ����ʹ��getStringValue() ������������������
	private static void getInnerText(Element ele, StringBuilder sb) {
		sb.append(ele.getStringValue());
		
//		sb.append(ele.getText());
//		Iterator<Node> it = ele.selectNodes("./*").iterator();
//		while(it.hasNext()) {
//			getInnerText((Element) it.next(), sb);
//		}
	}

	// ��ȡele��innerhtml
	private static void getInnerHtml(Element ele, StringBuilder sb) {
		String outerHtml = ele.asXML();
		int start = outerHtml.indexOf(">") + 1;
		int end = outerHtml.lastIndexOf("<");
		sb.append(outerHtml, start, end);
		
//		sb.append(ele.getText());
//		Iterator<Node> it = ele.selectNodes("./*").iterator();
//		while(it.hasNext()) {
//			getInnerText(it.next(), sb);
//		}
	}

	// ��ȡele��outerhtml
	private static void getOuterHtml(Element ele, StringBuilder sb) {
		sb.append(ele.asXML());
		
//		sb.append(ele.getText());
//		Iterator<Node> it = ele.selectNodes("./*").iterator();
//		while(it.hasNext()) {
//			getInnerText(it.next(), sb);
//		}
	}
	
}
