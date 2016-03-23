/**
 * file name : IndexAttrHandler.java
 * created at : 1:12:29 PM Mar 22, 2016
 * created by 970655147
 */

package com.hx.crawlerTools_attrHandler;

import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;
import com.hx.crawlerTools_attrHandler.adapter.interf.StringIntArgsAttrHandler;

// lastIndexOf������AttrHandler
// map(lastIndexOf(hello[, 2]) )
public class LastIndexAttrHandler extends StringIntArgsAttrHandler {
	// ������Ŀ���ַ���, ��ʼ��ǰ������λ��
	private String idxStr;
	private int to;
	
	// ��ʼ��
	public LastIndexAttrHandler(String idxStr, int to) {
		setArgs(idxStr, to);
	}
	public LastIndexAttrHandler(String idxStr) {
		this(idxStr, Constants.TARGET_UNDEFINED);
	}
	public LastIndexAttrHandler() {
		this(null, Constants.TARGET_UNDEFINED);
	}
	
	@Override
	public String handle0(String result) {
		Tools.assert0(idxStr != null, "error while calc the 'lastIndexOf(String[, Int])', 'idxString' be initialized illegal ! ");
		if(Constants.TARGET_UNDEFINED == to) {
			return String.valueOf(result.lastIndexOf(idxStr) );
		}
		
		return String.valueOf(result.lastIndexOf(idxStr, to) );
	}
	
	@Override
	public String name() {
		return Constants.LAST_INDEX_OF;
	}
	
	@Override
	public void setArgs(String arg1, int arg2) {
		this.idxStr = arg1;
		this.to = arg2;
	}
	
}
