/**
 * file name : EndPoint.java
 * created at : 7:42:13 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawler.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// 一个EndPint[终端结点 : "values" & "attribute"]
public abstract class EndPoint {
	
	// 常量
	public static String VALUES = Constants.VALUES;
	public static String ATTRIBUTE = Constants.ATTRIBUTE;
	
	// 类型, 名称, xpath
	// 注意 : 对于values来说, xpath必需存在
		// attribute来说, xpath不必须
	protected String type;
	protected String name;
	protected String xpath;
	protected EndPoint parent;
	// mapHandler, filterHanlder, 以及是否被filter的标志位
	protected AttrHandler mapHandler;
	protected AttrHandler filterHandler;
		protected boolean beFiltered;
	
	// 初始化
	public EndPoint(String type, String name, String xpath, String handlerStr, EndPoint parent) {
		this.type = type;
		this.name = name;
		this.parent = parent;
		this.xpath = xpath;
		this.beFiltered = false;
		
		if(handlerStr != null) {
			if(handlerStr.startsWith(Constants.HANDLER_ADDED) ) {
				if(parent.mapHandler == null) {
					this.mapHandler = Tools.handlerParse(handlerStr.substring(1) );
					this.filterHandler = Tools.removeIfLastWorkedHandlerIsFilter(this.mapHandler);
				} else {
					this.mapHandler = Tools.combineHandler(parent.mapHandler, Tools.handlerParse(handlerStr.substring(1)) );
					AttrHandler filterHandler0 = Tools.removeIfLastWorkedHandlerIsFilter(this.mapHandler);
					this.filterHandler = (filterHandler0 != null) ? filterHandler0 : this.filterHandler;
				}
			} else if(handlerStr.startsWith(Constants.HANDLER_OVERRIDE) ) {
				this.mapHandler = Tools.handlerParse(handlerStr.substring(1));
				this.filterHandler = Tools.removeIfLastWorkedHandlerIsFilter(this.mapHandler);
			} else {
				Tools.assert0("the handler should startWith : [" + Constants.HANDLER_ADDED + ", " + Constants.HANDLER_OVERRIDE + "], around : " + this.toString() );
			}
		} else {
			// default inhert from parent
			if(parent != null) {
				this.mapHandler = parent.mapHandler;
				this.filterHandler = parent.filterHandler;
			}
		}
		
//		if(xpath != null) {
//			this.xpath = Tools.getXPath(this, xpath);
//		}
	}

	// 添加孩子, 获取, 孩子的个数
	public abstract void addChild(EndPoint endPoint);
	public abstract EndPoint getChild(int idx);
	public abstract int childSize();
	public abstract String getAttribute();
	
	// for debug ...
	public String toString() {
		return new JSONObject().element("name", name).element("xpath", xpath).toString();
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
	public AttrHandler getMapHandler() {
		return mapHandler;
	}
	public AttrHandler getFilterHandler() {
		return filterHandler;
	}
	public void setBeFiltered(boolean beFiltered) {
		this.beFiltered = beFiltered;
	}
	public boolean beFiltered() {
		return this.beFiltered;
	}
	
	// 通过传入的xpath, 获取真实的xpath
	// 如果xpath以 / 开头[/, //], 则直接返回xpath
	// 否则如果以.开头[./]  拼接上parent结点的xpath
	// 否则  表示xpath不存在, 或者不合理, 返回ep的父节点的xpath
//	protected static String getXPath(EndPoint ep, String xpath) {
//		xpath = xpath.trim();
//		if(xpath.startsWith("/") ) {
//			return xpath;
//		} else if(xpath.startsWith(".")) {
//			StringBuilder sb = new StringBuilder(ep.getParent().getXPath().length() + xpath.length() + 1);
//			sb.append(ep.getParent().getXPath() );
//			makeSureNotEndsWithInvSlash(sb);
//			sb.append(xpath, 1, xpath.length());
//			return sb.toString();
//		}
//		
//		return ep.getParent().getXPath();
//	}

//	// 确保xpath 不以"/" 结尾
//	protected static void makeSureNotEndsWithInvSlash(StringBuilder xPath) {
//		if(xPath.charAt(xPath.length()-1) == Constants.INV_SLASH) {
//			xPath.deleteCharAt(xPath.length() - 1);
//		}
//	}
	
}
