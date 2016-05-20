/**
 * file name : StringOneOrTwoIntAttrHandler.java
 * created at : 3:18:39 AM May 21, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;

public abstract class StringOneOrTwoIntAttrHandler extends StringTwoIntAttrHandler {

	// ������û�ָ���Ĳ���
	protected String target;
	protected int from;
	protected int to;
	
	// ��ʼ��
	public StringOneOrTwoIntAttrHandler(String target, int from, int to) {
		setArgs(target, from, to);
	}
	
	// ���ò���
	@Override
	public void setArgs(String target, int from, int to) {
		this.target = target;
		this.from = from;
		this.to = to;
	}

	// ��дhandle0, �����߼�[�������߼�������gotResult��]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, from, to, result);
	}
	
	// ����ҵ��
	protected abstract String gotResult(String target, int from, int to, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("name", name() ).element("target", target).element("from", from).element("to", to)
				.toString();
	}
	
}
