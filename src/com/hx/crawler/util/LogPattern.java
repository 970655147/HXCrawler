/**
 * file name : LogPattern.java
 * created at : 11:37:47 PM Apr 21, 2016
 * created by 970655147
 */

package com.hx.crawler.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.hx.crawler.attrHandler.operation.interf.OperationAttrHandler;

// ��־ģʽ�Ľӿ�, ��������
public interface LogPattern {
	// ��ȡ��ǰLogPattern��Ӧ��pattern
	// ��ȡ��ǰPattern������
	public String pattern();
	public LogPatternType type();
	
	// LogPattern�ĸ�������
	static enum LogPatternType {
		// LogPattern��[ͨ����˵�������LogPattern]
		// Log.log���LogPattern
		// Tools.logBefore / logAfter��ص�LogPattern
		PATTERN_CHAIN(Constants.LOG_PATTERN_CHAIN),
		DATE(Constants.LOG_PATTERN_DATE), CONSTANTS(Constants.LOG_PATTERN_CONSTANTS), MODE(Constants.LOG_PATTERN_MODE), 
			MSG(Constants.LOG_PATTERN_MSG), INC_IDX(Constants.LOG_PATTERN_IDX), HANDLER(Constants.LOG_PATTERN_HANDLER),
		URL(Constants.LOG_PATTERN_URL), TASK_NAME(Constants.LOG_PATTERN_TASK_NAME), RESULT(Constants.LOG_PATTERN_RESULT), 
			SPENT(Constants.LOG_PATTERN_SPENT), EXCEPTION(Constants.LOG_PATTERN_EXCEPTION);
		
		// typeKey
		private String typeKey;
		private LogPatternType(String typeKey) {
			this.typeKey = typeKey;
		}
		
		// ��ȡtypeKey
		public String typeKey() {
			return typeKey;
		}
	}
	// ����һ���ɱ����[String]��LogPattern
	static abstract class OneStringVariableLogPattern implements LogPattern {
		protected String arg;
		public OneStringVariableLogPattern(String arg) {
			setArg(arg);
		}
		// ���ò���, �Լ�Ĭ�ϵ�patternʵ��
		public void setArg(String arg) {
			this.arg = arg;
		}
		@Override
		public String pattern() {
			return arg;
		}
	}
	
	// LogPattern��
	static class LogPatternChain implements LogPattern {
		private List<LogPattern> chain = new ArrayList<>();
		private String result = null;
		public String pattern() {
			if(result != null) {
				return result;
			}
			
			StringBuilder sb = new StringBuilder();
			for(LogPattern logPat : chain) {
				sb.append(logPat.pattern() );
			}
			result = sb.toString();
			return result;
		}
		public LogPatternType type() {
			return LogPatternType.PATTERN_CHAIN;
		}
		public LogPatternChain addLogPattern(LogPattern logPattern) {
			this.chain.add(logPattern);
			return this;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public List<LogPattern> getChain() {
			return this.chain;
		}
	}
	
	// date, constants, mode, msg, idx, handler ��ص�LogPattern
	static class DateLogPattern implements LogPattern {
		private DateFormat dateFormat;
		public DateLogPattern(String dateFormat) {
			this.dateFormat = new SimpleDateFormat(dateFormat);
		}
		public String pattern() {
			return dateFormat.format(new Date() );
		}
		public LogPatternType type() {
			return LogPatternType.DATE;
		}
	}
	static class ConstantsLogPattern implements LogPattern {
		protected String res;
		public ConstantsLogPattern(String res) {
			this.res = res;
		}
		public String pattern() {
			return res;
		}
		public LogPatternType type() {
			return LogPatternType.CONSTANTS;
		}
	}
	static class ModeLogPattern extends OneStringVariableLogPattern {
		public ModeLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.MODE;
		}
	}
	static class MsgLogPattern extends OneStringVariableLogPattern {
		public MsgLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.MSG;
		}
	}
	static class IncIndexLogPattern implements LogPattern {
		private AtomicLong var;
		private int inc;
		public IncIndexLogPattern(int initVal, int inc) {
			var = new AtomicLong(initVal);
			this.inc = inc;
		}
		public String pattern() {
			return String.valueOf(var.getAndAdd(inc) );
		}
		public LogPatternType type() {
			return LogPatternType.INC_IDX;
		}
	}
	static class HandlerLogPattern extends OneStringVariableLogPattern {
		private OperationAttrHandler attrHandler;
		public HandlerLogPattern(OperationAttrHandler attrHandler, String arg) {
			super(arg);
			this.attrHandler = attrHandler;
		}
		public String pattern() {
			attrHandler.cleanImmediateReturnFlag();
			String res = attrHandler.handle(arg);
			if(attrHandler.immediateReturn() ) {
				attrHandler.handleImmediateReturn();
				res = Constants.EMPTY_STR;
			}
			
			return res;
		}
		public LogPatternType type() {
			return LogPatternType.HANDLER;
		}
	}
	
	// taskName, url, result, spent, exception ��ص�LogPattern
	static class TaskNameLogPattern extends OneStringVariableLogPattern {
		public TaskNameLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.TASK_NAME;
		}
	}
	static class UrlLogPattern extends OneStringVariableLogPattern {
		public UrlLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.URL;
		}
	}
	static class ResultLogPattern extends OneStringVariableLogPattern {
		public ResultLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.RESULT;
		}
	}
	static class SpentLogPattern extends OneStringVariableLogPattern {
		public SpentLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.SPENT;
		}
	}
	static class ExceptionLogPattern extends OneStringVariableLogPattern {
		public ExceptionLogPattern(String mode) {
			super(mode);
		}
		public LogPatternType type() {
			return LogPatternType.EXCEPTION;
		}
	}
	
}
