/**
 * file name : GetStrInRangeHandler.java
 * created at : 11:49:20 PM May 20, 2016
 * created by 970655147
 */

package com.hx.crawler.attrHandler;

import com.hx.crawler.attrHandler.adapter.interf.TwoOrThreeStringTwoBooleanArgsAttrHandler;
import com.hx.crawler.util.Constants;
import com.hx.crawler.util.Tools;

// getStrIn('hello', 'e', 'o')
public class GetStrInRangeHandler extends TwoOrThreeStringTwoBooleanArgsAttrHandler  {

	// ≥ı ºªØ
	public GetStrInRangeHandler(String target, String start, String end, boolean includeStart, boolean includeEnd) {
		super(target, start, end, includeStart, includeEnd);
	}
	public GetStrInRangeHandler(String start, String end, boolean includeStart, boolean includeEnd) {
		this(Constants.HANDLER_UNDEFINED, start, end, includeStart, includeEnd);
	}
	public GetStrInRangeHandler(String target, String start, String end) {
		this(target, start, end, false, false);
	}
	public GetStrInRangeHandler(String start, String end) {
		this(Constants.HANDLER_UNDEFINED, start, end, false, false);
	}
	public GetStrInRangeHandler() {
		this(Constants.HANDLER_UNDEFINED, Constants.HANDLER_UNDEFINED, Constants.HANDLER_UNDEFINED, false, false);
	}

	@Override
	protected String gotResult(String target, String start, String end, boolean includeStart, boolean includeEnd, String result) {
		return Tools.getStrInRange(target, start, end, includeStart, includeEnd);
	}

	@Override
	public String name() {
		return Constants.GET_STR_IN_RANGE;
	}

}
