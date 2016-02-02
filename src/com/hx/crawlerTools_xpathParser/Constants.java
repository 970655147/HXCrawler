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

// ����
public class Constants {
	
	// indexString�п��Գ��ֵļ�
	public static String NAME = "name";
	public static String XPATH = "xpath";
	public static String VALUES = "values";
	public static String ATTRIBUTE = "attribute";
	
	// ��������[attribute]
	public static String INDEX = ":index";
	public static String TEXT = "text";
	public static String INNER_TEXT = "innertext";
	public static String INNER_HTML = "innerhtml";
	public static String OUTER_HTML = "outerhtml";
	
	// ����ֵ���͵ĳ���
	public static final String NULL = "null";
	public static final String DEFAULT_VALUE = NULL;
	public static final String ATTR_NOT_SUPPORT = "AttributeNotSupported";
	public static final String ARRAY_ATTR = "JSONArrayAttribute";
	
	// rootԪ��
	public static String ROOT = "#root";
	
	// ����
	public static Character SLASH = '\\';
	public static Character INV_SLASH = '/';

	// �������ӵ�����
	public static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// ������� �Լ����Ӧ��handler��ӳ��
	public static Map<String, EndPointHandler> endpointToHandler = new HashMap<>();
	static {
		endpointToHandler.put(ATTRIBUTE, new AttributeHandler() );
		endpointToHandler.put(VALUES, new ValuesHandler() );
		// ���������� ����д, ��Ϊ��EndPoint.ATTRIBUTE�ĳ�ʼ��������Constants.class�ļ���, ��ִ�е�ǰstaticBlock��ʱ��, Endpoint.ATTRIBUTE �Լ�EndPoint.VALUES�ڳ�ʼ���׶γ�ʼ����nullֵ		--2016.02.02
//		endpointToHandler.put(EndPoint.ATTRIBUTE, new ValuesHandler() );
	}
	
}
