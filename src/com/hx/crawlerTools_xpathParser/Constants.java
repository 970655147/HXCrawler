/**
 * file name : Constants.java
 * created at : 8:06:27 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 常量
public class Constants {
	
	// indexString中可以出现的键
	public static String NAME = "name";
	public static String XPATH = "xpath";
	public static String VALUES = "values";
	public static String ATTRIBUTE = "attribute";
	
	// 各种属性[attribute]
	public static String INDEX = ":index";
	public static String TEXT = "text";
	public static String INNER_TEXT = "innertext";
	public static String INNER_HTML = "innerhtml";
	public static String OUTER_HTML = "outerhtml";
	
	// 其他值类型的常量
	public static final String NULL = "null";
	public static final String DEFAULT_VALUE = NULL;
	public static final String ATTR_NOT_SUPPORT = "AttributeNotSupported";
	public static final String ARRAY_ATTR = "JSONArrayAttribute";
	
	// root元素
	public static String ROOT = "#root";
	
	// 常量
	public static Character SLASH = '\\';
	public static Character INV_SLASH = '/';

	// 存在连接的属性
	public static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// 各个结点 以及其对应的handler的映射
	public static Map<String, EndPointHandler> endpointToHandler = new HashMap<>();
	static {
		endpointToHandler.put(ATTRIBUTE, new AttributeHandler() );
		endpointToHandler.put(VALUES, new ValuesHandler() );
		// 不能向下面 这样写, 因为是EndPoint.ATTRIBUTE的初始化导致了Constants.class的加载, 而执行当前staticBlock的时候, Endpoint.ATTRIBUTE 以及EndPoint.VALUES在初始化阶段初始化的null值		--2016.02.02
//		endpointToHandler.put(EndPoint.ATTRIBUTE, new ValuesHandler() );
	}
	
}
