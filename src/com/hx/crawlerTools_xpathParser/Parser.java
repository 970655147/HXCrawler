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
	
	// 以root为根节点, 解析xpath字符串[指定的格式]
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

	// 解析target EndPoint
	// 先序遍历ep结点, 一次解析每一个结点的数据, 结果存放于res中
	private static void parse0(Element root, Element currentEle, String url, EndPoint ep, JSONArray res, int idx) {
		JSONObject curObj = new JSONObject();
		for(int i=0; i<ep.childSize(); i++) {
			EndPoint child = ep.getChild(i);
			if(EndPoint.ATTRIBUTE.equals(child.getType()) ) {
				if(child.getXPath() != null ) {
//					List<Element> eles = getResultByXPath(root, currentEle, child.getXPath());
					int idx2 = 0;
					
					// 统一只获取第一个元素的数据
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

	// 通过xpath抓取元素
	private static List<Element> getResultByXPath(Element root, Element currentEle, String xPath) {
		if(xPath.startsWith("/")) {
			return root.selectNodes(xPath);
		} else if(xPath.startsWith(".")) {
			return currentEle.selectNodes(xPath);
		}
		
		return null;
	}
	// 通过xpath抓取第一个元素
	private static Element getSingleResultByXPath(Element root, Element currentEle, String xPath) {
		if(xPath.startsWith("/")) {
			return (Element) root.selectSingleNode(xPath);
		} else if(xPath.startsWith(".")) {
			return (Element) currentEle.selectSingleNode(xPath);
		}
		
		return null;
	}
	
	// 存在连接的属性
	static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// 通过element 和attribute属性 获取其对应的值
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

	// 获取ele的innertext [但是不能解决子标签左右两边的文字的位置关系的问题]
	// 后来使用getStringValue() 方法解决了上面的问题
	private static void getInnerText(Element ele, StringBuilder sb) {
		sb.append(ele.getStringValue());
		
//		sb.append(ele.getText());
//		Iterator<Node> it = ele.selectNodes("./*").iterator();
//		while(it.hasNext()) {
//			getInnerText((Element) it.next(), sb);
//		}
	}

	// 获取ele的innerhtml
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

	// 获取ele的outerhtml
	private static void getOuterHtml(Element ele, StringBuilder sb) {
		sb.append(ele.asXML());
		
//		sb.append(ele.getText());
//		Iterator<Node> it = ele.selectNodes("./*").iterator();
//		while(it.hasNext()) {
//			getInnerText(it.next(), sb);
//		}
	}
	
}
