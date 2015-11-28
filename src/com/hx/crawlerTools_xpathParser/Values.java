/**
 * file name : Values.java
 * created at : 8:03:17 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import java.util.ArrayList;
import java.util.List;

// values Ԫ��
public class Values extends EndPoint {
	
	// values����Ԫ��
	private List<EndPoint> childs;
	
	// ��ʼ��
	public Values(String name, String xpath, EndPoint parent) {
		super(EndPoint.VALUES, name, xpath, parent);
		childs = new ArrayList<>();
	}

	// �����Ԫ��
	public void addChild(EndPoint endPoint) {
		childs.add(endPoint);
	}
	
	// ��ȡ��Ԫ��
	public EndPoint getChild(int idx) {
		return childs.get(idx);
	}
	
	// for debug ...
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append(super.toString() );
		sb.append(", values : [  ");
		for(EndPoint ep : childs) {
			sb.append(ep.toString() );
			sb.append(", ");
		}
		sb.replace(sb.length() - 2, sb.length(), " ]");
		sb.append(" }");
		
		return sb.toString();
	}

	// values �ĺ��Ӹ���Ϊchilds�Ľ����Ŀ[���õݹ�]
	public int childSize() {
		return childs.size();
	}
	
	// ��ȡattr [values ��֧�ִ˲���]
	public String getAttribute() {
		throw new RuntimeException("unsupported operation exception ...");
	}

}
