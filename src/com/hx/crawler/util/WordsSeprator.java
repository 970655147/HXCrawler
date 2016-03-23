/**
 * file name : WordSeprator.java
 * created at : 3:43:13 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

// �ָ��ַ���
public class WordsSeprator implements Iterator<String> {

	// �������ַ���, �ָ�������һ��λ�õ�ӳ��, ��Ҫ�����ķ��Ŷ�
	// ��ǰ������
	private String str;
	private Map<String, Integer> sepToPos;
	private Map<String, String> escapeMap;
	private int idx;
	// ����next��ֵ
	private String next;
	private String lastNext;
	private int lastNextIdx;
	private String last;
	// �Ƿ��ȡ�ָ���, ��ǰ�Ƿ�Ӧ�÷��طָ���
	private boolean gotSep;
		private boolean nowSep;
		private String lastSep;
	
	// ��ʼ��
	public WordsSeprator(String str, Map<String, Integer> sepToPos, Map<String, String> escapeMap, boolean gotSep) {
		this.str = str;
		this.sepToPos = sepToPos;
		this.escapeMap = escapeMap;
		this.gotSep = gotSep;
		this.nowSep = false;
		this.lastSep = null;
		freshAll();
	}

	@Override
	public boolean hasNext() {
		if(next != null) {
			return true;
		}
		
			// true 				&& 	true
		if((idx >= str.length()) && (! (nowSep && (lastSep != null))) ) {
			return false;
		}
		if(gotSep) {
			boolean isNowSep = nowSep;
			nowSep = ! nowSep;
			if(isNowSep) {
				next = lastSep;
				return hasNext();
			}
		}

		String sep = minSep();
		int pos = getPosBySep(sep);
		String res = null;
		lastNextIdx = idx;
		if(pos < 0) {
			res = str.substring(idx);
			idx = str.length();
			lastSep = null;
		} else {
			fresh(sep);
			res = str.substring(idx, pos);
			idx = pos + sep.length();
			lastSep = sep;
		}
		if(Tools.isEmpty(res) ) {
			return hasNext();
		}
		next = res;
		return hasNext();
	}
	
	@Override
	public String next() {
		last = lastNext;
		if(! hasNext() ) {
			return null;
		}
		
		lastNext = next;
		String res = next;
		next = null;
		return res;
	}
	public String seekLastNext() {
		if(! hasNext())	;
		return lastNext;
	}
	public int lastNextPos() {
		return lastNextIdx;
	}
	public String seek() {
		if(! hasNext() ) {
			return null;
		}
		return next;
	}
	public String last() {
		if(! hasNext())	;
		return last;
	}
	public String rest() {
		if(! hasNext())	;
		return str.substring(idx - lastNext.length() );
	}
	public String rest(int pos) {
		if((pos < 0) || (pos >= str.length()) ) {
			return null;
		}
		return str.substring(pos);
	}

	@Override
	public void remove() {
		throw new RuntimeException("unsupportedOperation !");
	}
	
	// fresh���еķָ��� / �����ķָ���
	private void freshAll() {
		for(Entry<String, Integer> entry : sepToPos.entrySet() ) {
			fresh(entry.getKey() );
		}
	}
	private void fresh(String sep) {
		Integer pos = sepToPos.get(sep);
		if(pos != null ) {
			if(pos >= 0) {
				sepToPos.put(sep, indexOf(str, sep, pos+1) );
			}
		}
	}
	private Integer indexOf(String str, String sep, int start) {
		int idx = start;
		whileLoop:
		while(idx < str.length() ) {
			for(Entry<String, String> entry : escapeMap.entrySet() ) {
				if(str.startsWith(entry.getKey(), idx) ) {
					idx = str.indexOf(entry.getValue(), idx+entry.getKey().length() );
					if(idx < 0) {
						break whileLoop;
					}
					idx += entry.getValue().length();
					continue whileLoop;
				}
			}
			if(str.startsWith(sep, idx) ) {
				return idx;
			}
			idx ++;
		}
		
		return -1;
	}

	// ��ȡ������������ķָ���
	private String minSep() {
		int minPos = Integer.MAX_VALUE;
		String minSep = null;
		for(Entry<String, Integer> entry : sepToPos.entrySet() ) {
			if((entry.getValue() >= 0) && (entry.getValue() < minPos) ) {
				minPos = entry.getValue();
				minSep = entry.getKey();
			}
		}
		return minSep;
	}
	private int minPos() {
		String minSep = minSep();
		return getPosBySep(minSep);
	}
	private int getPosBySep(String sep) {
		return (sep == null) ? -1 : sepToPos.get(sep);
	}
		
}
