/**
 * file name : ResultJudger.java
 * created at : 5:11:13 PM Oct 25, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import net.sf.json.JSONArray;

// ����жϽӿ�
public interface ResultJudger {

	// �жϸ����Ľ���Ƿ�Ϊ��
	public boolean isResultNull(int idx, JSONArray fetchedData);
	
}
