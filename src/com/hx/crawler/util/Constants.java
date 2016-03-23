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

// ����
public class Constants {
	
	// indexString�п��Գ��ֵļ�
	public final static String NAME = "name";
	public final static String XPATH = "xpath";
	public final static String VALUES = "values";
	public final static String ATTRIBUTE = "attribute";
	public final static String HANDLER = "handler";
	
	// handler�������, + ��ʾ�ڸ��ڵ�Ļ����������ӵ�ǰhandler����, - ��ʾ��д���ڵ��attrHandler
	public final static String HANDLER_ADDED = "+";
	public final static String HANDLER_OVERRIDE = "-";
	
	// ��������[attribute]
	public final static String INDEX = ":index";
	public final static String TEXT = "text";
	public final static String INNER_TEXT = "innertext";
	public final static String INNER_HTML = "innerhtml";
	public final static String OUTER_HTML = "outerhtml";
	
	// ����ֵ���͵ĳ���
	public final static String NULL = "null";
	public final static String DEFAULT_VALUE = NULL;
	public final static String ATTR_NOT_SUPPORT = "AttributeNotSupported";
	public final static String ARRAY_ATTR = "JSONArrayAttribute";
	
	// rootԪ��
	public final static String ROOT = "#root";
	
	// ����
	public final static Character SLASH = '\\';
	public final static Character INV_SLASH = '/';

	// �������ӵ�����
	public final static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// ������� �Լ����Ӧ��handler��ӳ��
	public final static Map<String, EndPointHandler> endpointToHandler = new HashMap<>();
	static {
		endpointToHandler.put(ATTRIBUTE, new AttributeHandler() );
		endpointToHandler.put(VALUES, new ValuesHandler() );
		// ���������� ����д, ��Ϊ��EndPoint.ATTRIBUTE�ĳ�ʼ��������Constants.class�ļ���, ��ִ�е�ǰstaticBlock��ʱ��, Endpoint.ATTRIBUTE �Լ�EndPoint.VALUES�ڳ�ʼ���׶γ�ʼ����nullֵ		--2016.02.02
//		endpointToHandler.put(EndPoint.ATTRIBUTE, new ValuesHandler() );
	}
	
	// ���Դ�����صĳ���			// add at 2016.03.22
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
	
	// �������õķ����ı�ʶ��
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
	
	// HandlerParser����طָ���
	// ��Ҫ�����ķ��Ŷ�, ���Ŷ�
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
