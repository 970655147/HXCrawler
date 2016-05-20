/**
 * file name : TwoIntArgsAttrHandler.java
 * created at : 10:51:01 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler.adapter.interf;

import net.sf.json.JSONObject;

import com.hx.crawler.util.Constants;


// �������һ���ַ���������AttrHander������AttrHandler
public abstract class TwoOrThreeStringTwoBooleanArgsAttrHandler extends ThreeStringTwoBooleanArgsAttrHandler {
	// ������û�ָ���Ĳ���
	protected String target;
	protected String start;
	protected String end;
	protected boolean includeStart;
	protected boolean includeEnd;
	
	// ��ʼ��
	public TwoOrThreeStringTwoBooleanArgsAttrHandler(String target, String start, String end, boolean includeStart, boolean includeEnd) {
		setArgs(target, start, end, includeStart, includeEnd);
	}
	
	// ���ò���
	@Override
	public void setArgs(String target, String start, String end, boolean includeStart, boolean includeEnd) {
		this.target = target;
		this.start = start;
		this.end = end;
		this.includeStart = includeStart;
		this.includeEnd = includeEnd;
	}
	// ��дhandle0, �����߼�[�������߼�������gotResult��]
	public String handle0(String result) {
		if(Constants.HANDLER_UNDEFINED.equals(target) ) {
			target = result;
		}

		return gotResult(target, start, end, includeStart, includeEnd, result);
	}
	
	// ����ҵ��
	protected abstract String gotResult(String target, String start, String end, boolean includeStart, boolean includeEnd, String result);
	
	@Override
	public String toString() {
		return new JSONObject()
				.element("name", name() ).element("target", target).element("start", start).element("end", end)
				.element("includeStart", includeStart).element("includeEnd", includeEnd)
				.toString();
	}
}
