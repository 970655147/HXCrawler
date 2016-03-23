/**
 * file name : ConcateAttrHandler.java
 * created at : 1:09:46 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import java.util.regex.Pattern;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.OneStringArgsAttrHandler;

// �жϸ������ַ����Ƿ��expectƥ���handler
// map(matches(pat) )
public class MatchesAttrHandler extends OneStringArgsAttrHandler {
	// �������õ��ĳ���ֵ
		// ��ʱ��ʹ����򵥵ķ���������
		// ���� ���˾��ô˴�Ӧ���л���
	private String pattern;
	
	// ��ʼ��
	public MatchesAttrHandler(String pattern) {
		setArgs(pattern);
	}
	public MatchesAttrHandler() {
		this(null);
	}

	@Override
	public String handle0(String result) {
		Tools.assert0(pattern != null, "error while calc the 'matches(String)', pattern be initialized illegal ! ");
		return String.valueOf(Pattern.compile(pattern).matcher(result).matches() );
	}

	@Override
	public String name() {
		return Constants.MATCHES;
	}
	
	@Override
	public void setArgs(String arg) {
		this.pattern = arg;
	}
	
}
