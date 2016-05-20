/**
 * file name : TwoOrThreeStringArgsAttrHandler.java
 * created at : 2:49:50 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;

public abstract class TwoOrThreeStringArgsAttrHandler extends ThreeStringArgsAttrHandler {
	// ������û�ָ���Ĳ���
	protected String target;
	protected String pattern;
	protected String replacement;
	
	// ��ʼ��
	public TwoOrThreeStringArgsAttrHandler(String target, String pattern, String replacement) {
		setArgs(target, pattern, replacement);
	}
	
	// ���ò���
	@Override
	public void setArgs(String target, String pattern, String replacement) {
		this.target = target;
		this.pattern = pattern;
		this.replacement = replacement;
	}
	// ��дhandle0, �����߼�[�������߼�������gotResult��]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, pattern, replacement, result);
	}
	
	// ����ҵ��
	protected abstract String gotResult(String target, String start, String end, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("name", name() ).element("target", target).element("pattern", pattern).element("replacement", replacement)
				.toString();
	}
}
