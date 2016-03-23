/**
 * file name : Constants.java
 * created at : 8:06:27 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawler.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hx.crawler.interf.EndPointHandler;
import com.hx.crawlerTools_xpathParser.AttributeHandler;
import com.hx.crawlerTools_xpathParser.ValuesHandler;

// 常量
public class Constants {
	
	// indexString中可以出现的键
	public final static String NAME = "name";
	public final static String XPATH = "xpath";
	public final static String VALUES = "values";
	public final static String ATTRIBUTE = "attribute";
	public final static String HANDLER = "handler";
	
	// handler处理相关, + 表示在父节点的基础上面增加当前handler数据, - 表示重写父节点的attrHandler
	public final static String HANDLER_ADDED = "+";
	public final static String HANDLER_OVERRIDE = "-";
	
	// 各种属性[attribute]
	public final static String INDEX = ":index";
	public final static String TEXT = "text";
	public final static String INNER_TEXT = "innertext";
	public final static String INNER_HTML = "innerhtml";
	public final static String OUTER_HTML = "outerhtml";
	
	// 其他值类型的常量
	public final static String NULL = "null";
	public final static String DEFAULT_VALUE = NULL;
	public final static String ATTR_NOT_SUPPORT = "AttributeNotSupported";
	public final static String ARRAY_ATTR = "JSONArrayAttribute";
	
	// root元素
	public final static String ROOT = "#root";
	
	// 常量
	public final static Character SLASH = '\\';
	public final static Character INV_SLASH = '/';

	// 存在连接的属性
	public final static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// 各个结点 以及其对应的handler的映射
	public final static Map<String, EndPointHandler> endpointToHandler = new HashMap<>();
	static {
		endpointToHandler.put(ATTRIBUTE, new AttributeHandler() );
		endpointToHandler.put(VALUES, new ValuesHandler() );
		// 不能向下面 这样写, 因为是EndPoint.ATTRIBUTE的初始化导致了Constants.class的加载, 而执行当前staticBlock的时候, Endpoint.ATTRIBUTE 以及EndPoint.VALUES在初始化阶段初始化的null值		--2016.02.02
//		endpointToHandler.put(EndPoint.ATTRIBUTE, new ValuesHandler() );
	}
	
	// 属性处理相关的常量			// add at 2016.03.22
	public final static String HANDLER_PREFIX = "map";
	public final static String RESULT_PROXY = "$this";
	public final static boolean RECOGNIZE_RESULT_PROXY = true;
	public final static String ANONY_OPERAND_NAME = "anonymousOperand";
	public final static String EMPTY_OPERAND_NAME = "emptyOperand";
	public final static int COMPOSITE_HANDLER_DEFAULT_CAP = 2;
	public final static int CONCATE_HANDLER_DEFAULT_CAP = 2;
	public final static int OPERAND_DEFAULT_CAP = 2;
	public final static String STRING_CONCATE = "+";
	public final static String PARAM_SEP = ",";
	public final static String SUB_HANDLER_CALL = ".";
	public final static String QUOTE = "'";
	public final static String QUOTED_STRING = "quotedString";
	public final static String TRUE = Boolean.TRUE.toString();
	public final static String FALSE = Boolean.FALSE.toString();
	public final static String HANDLER_UNDEFINED = "-1";
	public final static int TARGET_UNDEFINED = -1;
	
	// 各个可用的方法的标识符
	public final static String CONCATE = "concate";
	public final static String REPLACE = "replace";
	public final static String TRIM = "trim";
	public final static String TRIM_ALL = "trimAll";
	public final static String TRIM_AS_ONE = "trimAsOne";
	public final static String SUB_STRING = "subString";
	public final static String TO_UPPERCASE = "toUpperCase";
	public final static String TO_LOWERCASE = "toLowerCase";
	public final static String CONSTANTS = "constants";
	public final static String DO_NOTHING = "doNothing";
	
	public final static String INDEX_OF = "indexOf";
	public final static String LAST_INDEX_OF = "lastIndexOf";
	public final static String LENGTH = "length";
	
	public final static String EQUALS = "equals";
	public final static String MATCHES = "matches";
	public final static String CONTAINS = "contains";
	
	public final static String COMPOSITE = "composite";
	
	// HandlerParser的相关分隔符
	// 需要跳过的符号对, 括号对
	public final static Set<String> handlerParserSeps = new HashSet<>();
	public final static Map<String, String> escapeMap = new HashMap<>();
	public final static Map<Character, Character> escapeCharMap = new HashMap<>();
	public final static Map<String, String> bracketPair = new HashMap<>();
	static {
		handlerParserSeps.add("(");
		handlerParserSeps.add(")");
		handlerParserSeps.add("+");
		handlerParserSeps.add(",");
		handlerParserSeps.add(".");
		handlerParserSeps.add("'");
		
		escapeMap.put("\"", "\"");
		escapeMap.put("'", "'");
		escapeCharMap.put('\'', '\'');
		escapeCharMap.put('"', '"');
		
		bracketPair.put("(", ")");
		bracketPair.put("{", "}");
	}

	
}
