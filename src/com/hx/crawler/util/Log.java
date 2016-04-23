/**
 * file name : Log.java
 * created at : 8:10:53 PM Apr 22, 2015
 * created by 970655147
 */

package com.hx.crawler.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import com.hx.crawler.util.LogPattern.LogPatternChain;
import com.hx.crawler.util.LogPattern.LogPatternType;

// 打印数据相关的类
public class Log {
	
	// --------------------------- 可配置变量 --------------------------------------
	// 以及输出流, 错误流, 以及默认是否换行
	public static String HORIZON_LINES = Constants.HORIZON_LINES;
	public static String HORIZON_STARTS = Constants.HORIZON_STARTS;
	public static String GOT_THERE = Constants.GOT_THERE;
	
	public static OutputStream outStream = Constants.outStream;
	public static OutputStream errStream = Constants.errStream;
	private static boolean outToLogFile = Constants.outToLogFile;
	private static boolean errToLogFile = Constants.errToLogFile;
	public final static String logBufName = Constants.logBufName;
	private static String logFile = Constants.logFile;
	public static LogPatternChain logPatternChain = Constants.logPatternChain;
	
	public static String DEFAULT_SEP_WHILE_CRLF = Constants.DEFAULT_SEP_WHILE_CRLF;
	public static String DEFAULT_SEP_WHILE_NO_CRLF = Constants.DEFAULT_SEP_WHILE_NO_CRLF;
	public static String DEFAULT_SEP_WHILE_TWO_DIMEN = Constants.DEFAULT_SEP_WHILE_TWO_DIMEN;
	public static String DEFAULT_MAP_KV_SEP = Constants.DEFAULT_MAP_KV_SEP;
	
	public static boolean OUTPUT_APPEND_CRLF = Constants.OUTPUT_APPEND_CRLF;
	public static boolean ERRPUT_APPEND_CRLF = Constants.ERRPUT_APPEND_CRLF;
	public static boolean OUTPUT_APPEND_CRLF_FOR_CONTAINER = Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER;
	public static boolean ERRPUT_APPEND_CRLF_FOR_CONTAINER = Constants.ERRPUT_APPEND_CRLF_FOR_CONTAINER;
	// --------------------------- 置于最后 ----------------------------------------
	
	// 初始化
	static {
		try {
			if(isLogToFile() ) {
				setLogFile(logFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// --------------------------- 配置可配置变量的接口 ----------------------------------------
	public static void setLogFile(String logFile) throws IOException {
		if(Log.logFile != null ) {
			setLogFile0(logFile);
		} else {
			Log.log("Log.logFile is null, maybe not support out log to 'logFile', use 'setOutToLogFile / setErrToLogFile' insted !");
		}
	}
	private static void setLogFile0(String logFile) throws IOException {
		if(logFile == null) {
			if(Tools.bufExists(logBufName) ) {
				Tools.closeAnBuffer(logBufName);
			}
			outToLogFile = false;
			errToLogFile = false;
			Log.logFile = null;
			return ;
		}
		
		if((Log.logFile != null) && (! Log.logFile.equals(logFile)) ) {
			if(Tools.bufExists(logBufName) ) {
				Tools.flushBuffer(logBufName, true);
			}
			Tools.createAnBuffer(logBufName, logFile);
		} else {
			if(! Tools.bufExists(logBufName) ) {
				Tools.createAnBuffer(logBufName, logFile);	
			} else {
				Log.log("specified : 'logFile' is current 'Log.logFile', ignore !");
			}
		}
		Log.logFile = logFile;
	}
	public static void setOutToLogFile(boolean outToLogFile, String logFile) throws IOException {
		Log.outToLogFile = outToLogFile;
		setToLogFile0(outToLogFile, logFile);
	}
	public static void setOutToLogFile(boolean outToLogFile) throws IOException {
		setOutToLogFile(outToLogFile, logFile);
	}
	public static void setErrToLogFile(boolean errToLogFile, String logFile) throws IOException {
		Log.errToLogFile = errToLogFile;
		setToLogFile0(errToLogFile, logFile);
	}
	public static void setErrToLogFile(boolean errToLogFile) throws IOException {
		setErrToLogFile(errToLogFile, logFile);
	}
	private static void setToLogFile0(boolean toLogFile, String logFile) throws IOException {
		if(toLogFile) {
			setLogFile0(logFile);
		}
	}
	public static boolean isLogToFile() {
		return outToLogFile || errToLogFile;
	}
	
	// --------------------------- 业务方法 ----------------------------------------
	// 标准输出
	public static void log(boolean appendCRLF) {
		log(GOT_THERE, appendCRLF);
	}
	public static void log() {
		log(GOT_THERE, Constants.OUTPUT_APPEND_CRLF);
	}
	public static void log(String str, boolean appendCRLF) {
		try {
			StringBuilder sb = new StringBuilder(str.length() + 4);
			sb.append(Constants.formatLogInfo(logPatternChain, new JSONObject().element(LogPatternType.MSG.typeKey(), str).element(LogPatternType.MODE.typeKey(), Constants.MODE_LOG)) );
			if(appendCRLF) {
				sb.append(Constants.CRLF );
			}
			
			String line = sb.toString();
			if(outStream != null) {
				outStream.write(line.getBytes(Tools.DEFAULT_CHARSET) );
			}
			if(outToLogFile) {
				Tools.appendBuffer(logBufName, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void log(String obj) {
		log(obj, Constants.OUTPUT_APPEND_CRLF);
	}
	public static void log(Object obj, boolean appendCRLF) {
		log(obj.toString(), appendCRLF );
	}
	public static void log(Object obj) {
		log(obj.toString(), Constants.OUTPUT_APPEND_CRLF );
	}
	public static void logf(String pattern, Object[] args, boolean appendCRLF) {
		log(String.format(pattern, args), appendCRLF);
	}
	public static void logf(String pattern, Object... args) {
		logf(pattern, args, Constants.OUTPUT_APPEND_CRLF);
	}
	
	// 错误输出
	public static void err(boolean appendCRLF) {
		err(GOT_THERE, appendCRLF);
	}
	public static void err() {
		err(GOT_THERE, ERRPUT_APPEND_CRLF);
	}
	public static void err(String str, boolean appendCRLF) {
		try {
			StringBuilder sb = new StringBuilder(str.length() + 4);
			sb.append(Constants.formatLogInfo(logPatternChain, new JSONObject().element(LogPatternType.MSG.typeKey(), str).element(LogPatternType.MODE.typeKey(), Constants.MODE_ERR)) );
			if(appendCRLF) {
				sb.append(Constants.CRLF );
			}
			
			String line = sb.toString();
			if(errStream != null) {
				errStream.write(line.getBytes(Tools.DEFAULT_CHARSET) );
			}
			if(errToLogFile) {
				Tools.appendBuffer(logBufName, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void err(String obj) {
		err(obj, ERRPUT_APPEND_CRLF);
	}
	public static void err(Object obj, boolean appendCRLF) {
		err(obj.toString(), appendCRLF );
	}
	public static void err(Object obj) {
		err(obj, ERRPUT_APPEND_CRLF );
	}
	public static void errf(String pattern, Object[] args, boolean appendCRLF) {
		err(String.format(pattern, args), appendCRLF);
	}
	public static void errf(String pattern, Object... args) {
		errf(pattern, args, Constants.OUTPUT_APPEND_CRLF);
	}
	
	// 打印迭代器中的元素
	public static <T> void log(Iterator<T> it, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			while(it.hasNext()) {
				Tools.appendCRLF(sb, (it.next().toString() + sep) );
			}
		} else {
			while(it.hasNext()) {
				Tools.append(sb, it.next().toString() + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static <T> void log(Iterator<T> it, String sep, boolean appendCRLF) {
		log(it, sep, true, appendCRLF);
	}
	public static <T> void log(Iterator<T> it, boolean appendCRLF) {
		if(appendCRLF) {
			log(it, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(it, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void log(Iterator<T> it) {
		log(it, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void log(Iterator<T> it, String sep) {
		log(it, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static <T> void err(Iterator<T> it, String sep, boolean appendCRLF) {
		log(it, sep, false, appendCRLF);
	}
	public static <T> void err(Iterator<T> it, boolean appendCRLF) {
		if(appendCRLF) {
			err(it, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(it, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void err(Iterator<T> it) {
		err(it, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void err(Iterator<T> it, String sep) {
		err(it, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
			
	// 打印List
	public static <T> void log(List<T> ls, String sep, boolean appendCRLF) {
		log(ls.iterator(), sep, appendCRLF);
	}
	public static <T> void log(List<T> ls, boolean appendCRLF) {
		log(ls.iterator(), appendCRLF);
	}
	public static <T> void log(List<T> ls) {
		log(ls.iterator(), Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void log(List<T> ls, String sep) {
		log(ls.iterator(), sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static <T> void err(List<T> ls, String sep, boolean appendCRLF) {
		err(ls.iterator(), sep, appendCRLF);
	}
	public static <T> void err(List<T> ls, boolean appendCRLF) {
		err(ls.iterator(), appendCRLF);
	}
	public static <T> void err(List<T> ls) {
		err(ls.iterator(), ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void err(List<T> ls, String sep) {
		err(ls.iterator(), sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	// 打印Set
	public static <T> void log(Set<T> ls, String sep, boolean appendCRLF) {
		log(ls.iterator(), sep, appendCRLF);
	}
	public static <T> void log(Set<T> ls, boolean appendCRLF) {
		log(ls.iterator(), appendCRLF);
	}
	public static <T> void log(Set<T> ls) {
		log(ls.iterator(), Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void log(Set<T> ls, String sep) {
		log(ls.iterator(), sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static <T> void err(Set<T> ls, String sep, boolean appendCRLF) {
		err(ls.iterator(), sep, appendCRLF);
	}
	public static <T> void err(Set<T> ls, boolean appendCRLF) {
		err(ls.iterator(), appendCRLF);
	}
	public static <T> void err(Set<T> ls) {
		err(ls.iterator(), ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void err(Set<T> ls, String sep) {
		err(ls.iterator(), sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	// 打印Map
	public static <K, V> void log(Map<K, V> ls, String kvSep, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(Entry<K, V> entry : ls.entrySet() ) {
				Tools.appendCRLF(sb, entry.getKey() + kvSep + entry.getValue() + sep );
			}
		} else {
			for(Entry<K, V> entry : ls.entrySet() ) {
				Tools.append(sb, entry.getKey() + " -> " + entry.getValue() + sep );
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	public static <K, V> void log(Map<K, V> ls, String sep, boolean isOutStream, boolean appendCRLF) {
		log(ls, DEFAULT_MAP_KV_SEP, sep, isOutStream, appendCRLF);
	}
	
	public static <K, V> void log(Map<K, V> ls, String kvSep, String sep, boolean appendCRLF) {
		log(ls, kvSep, sep, true, appendCRLF);
	}
	public static <K, V> void log(Map<K, V> ls, String kvSep, String sep) {
		log(ls, kvSep, sep, true);
	}
	public static <K, V> void log(Map<K, V> ls, String sep, boolean appendCRLF) {
		log(ls, DEFAULT_MAP_KV_SEP, sep, true, appendCRLF);
	}
	public static <K, V> void log(Map<K, V> ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_MAP_KV_SEP, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_MAP_KV_SEP, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <K, V> void log(Map<K, V> ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <K, V> void log(Map<K, V> ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static <K, V> void err(Map<K, V> ls, String kvSep, String sep, boolean appendCRLF) {
		log(ls, kvSep, sep, false, appendCRLF);
	}
	public static <K, V> void err(Map<K, V> ls, String kvSep, String sep) {
		err(ls, kvSep, sep, true);
	}
	public static <K, V> void err(Map<K, V> ls, String sep, boolean appendCRLF) {
		log(ls, DEFAULT_MAP_KV_SEP, sep, false, appendCRLF);
	}
	public static <K, V> void err(Map<K, V> ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_MAP_KV_SEP, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_MAP_KV_SEP, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <K, V> void err(Map<K, V> ls) {
		err(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <K, V> void err(Map<K, V> ls, String sep) {
		err(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	// 打印int[], long[], double[], char[], boolean[], Object[]
	public static void log(int[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(int obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(int obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(int[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(int[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(int[] ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void log(int[] ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void err(int[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(int[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(int[] ls) {
		err(ls, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void err(int[] ls, String sep) {
		err(ls, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void log(long[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(long obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(long obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(long[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(long[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(long[] ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void log(long[] ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void err(long[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(long[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(long[] ls) {
		err(ls, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void err(long[] ls, String sep) {
		err(ls, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void log(double[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(double obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(double obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(double[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(double[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(double[] ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void log(double[] ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void err(double[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(double[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(double[] ls) {
		err(ls, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void err(double[] ls, String sep) {
		err(ls, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void log(char[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(char obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(char obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(char[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(char[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(char[] ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void log(char[] ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void err(char[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(char[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(char[] ls) {
		err(ls, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void err(char[] ls, String sep) {
		err(ls, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void log(boolean[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(boolean obj : ls) {
				Tools.appendCRLF(sb, (obj + sep) );
			}
		} else {
			for(boolean obj : ls) {
				Tools.append(sb, obj + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(boolean[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static void log(boolean[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void log(boolean[] ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void log(boolean[] ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static void err(boolean[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static void err(boolean[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static void err(boolean[] ls) {
		err(ls, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static void err(boolean[] ls, String sep) {
		err(ls, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static <T> void log(T[] ls, String sep, boolean isOutStream, boolean appendCRLF) {
		StringBuilder sb = new StringBuilder();
		if(appendCRLF) {
			for(Object obj : ls) {
				Tools.appendCRLF(sb, (obj.toString() + sep) );
			}
		} else {
			for(Object obj : ls) {
				Tools.append(sb, obj.toString() + sep);
			}
		}
		// incase of 'it.hasNext == false'
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static <T> void log(T[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, true, appendCRLF);
	}
	public static <T> void log(T[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			log(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			log(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void log(T[] ls) {
		log(ls, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void log(T[] ls, String sep) {
		log(ls, sep, Constants.OUTPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	public static <T> void err(T[] ls, String sep, boolean appendCRLF) {
		log(ls, sep, false, appendCRLF);
	}
	public static <T> void err(T[] ls, boolean appendCRLF) {
		if(appendCRLF) {
			err(ls, DEFAULT_SEP_WHILE_CRLF, appendCRLF);
		} else {
			err(ls, DEFAULT_SEP_WHILE_NO_CRLF, appendCRLF);
		}
	}
	public static <T> void err(T[] ls) {
		err(ls, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	public static <T> void err(T[] ls, String sep) {
		err(ls, sep, ERRPUT_APPEND_CRLF_FOR_CONTAINER);
	}
	
	// 打印int[][], long[][], double[][], boolean[][], Object[][]  格式如下 
	// 1 2 3 
	// 2 1 3
	// 3 2 1
	public static void log(int[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			int[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(int[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(int[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(int[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(int[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}

	public static void log(long[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			long[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(long[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(long[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(long[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(long[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}

	public static void log(double[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			double[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(double[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(double[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(double[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(double[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void log(char[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			char[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(char[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(char[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(char[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(char[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void log(boolean[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			boolean[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static void log(boolean[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static void log(boolean[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static void err(boolean[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static void err(boolean[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static <T> void log(T[][] arr, String sep, boolean isOutStream) {
		for(int i=0; i<arr.length; i++) {
			Object[] row = arr[i];
			log(row, sep, isOutStream, false);
		}
	}
	
	public static <T> void log(T[][] arr, String sep) {
		log(arr, sep, true);
	}
	public static <T> void log(T[][] arr) {
		log(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}
	
	public static <T> void err(T[][] arr, String sep) {
		log(arr, sep, false);
	}
	public static <T> void err(T[][] arr) {
		err(arr, DEFAULT_SEP_WHILE_TWO_DIMEN);
	}

	// 打印一条水平线
	public static void logHorizon(int n, boolean isOutStream) {
		StringBuilder sb = new StringBuilder(n * (HORIZON_LINES.length() + 2) );
		for(int i=0; i<n; i++) {
			Tools.appendCRLF(sb, HORIZON_LINES);
		}
		if(sb.length() > Tools.CRLF.length() ) {
			sb.delete(sb.length()-Tools.CRLF.length(), sb.length() );
		}
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void logHorizon(int n) {
		logHorizon(n, true);
	}
	public static void logHorizon() {
		logHorizon(1);
	}
	
	public static void errHorizon(int n) {
		logHorizon(n, false);
	}
	public static void errHorizon() {
		errHorizon(1);
	}
	
	// 键入一个/ n个回车
	public static void enter() {
		enter(1);
	}
	public static void enter(int n) {
		for(int i=0; i<n; i++ ) {
			log(Tools.EMPTY_STR, true);
		}
	}

	// 按照给定的iterator迭代出来的索引, 打印arr中的元素
	public static void log(int[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(int[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(int[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(int[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(int[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(long[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(long[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(long[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(long[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(long[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(double[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(double[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(double[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(double[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(double[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(char[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(char[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(char[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(char[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(char[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void log(boolean[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	
	public static void log(boolean[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static void log(boolean[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static void err(boolean[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static void err(boolean[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static <T> void log(T[] arr, Iterator<Integer> it, String sep, boolean isOutStream) {
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			Tools.append(sb, String.valueOf(arr[it.next()]) + sep);
		}
		if(sb.length() > sep.length() ) {
			sb.delete(sb.length()-sep.length(), sb.length() );
		}
		sb.append(Tools.CRLF);
		
		if(isOutStream) {
			log(sb.toString() );
		} else {
			err(sb.toString() );
		}
	}
	public static <T> void log(T[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, true);
	}
	public static <T> void log(T[] arr, Iterator<Integer> it) {
		log(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}
	
	public static <T> void err(T[] arr, Iterator<Integer> it, String sep) {
		log(arr, it, sep, false);
	}
	public static <T> void err(T[] arr, Iterator<Integer> it) {
		err(arr, it, DEFAULT_SEP_WHILE_NO_CRLF);
	}

	// 打印两个int, long, double, boolean, Object
	public static void log(int row, int col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(long row, long col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(double row, double col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(char row, char col) {
		log(row + DEFAULT_SEP_WHILE_NO_CRLF + col);
	}
	public static void log(boolean bool01, boolean bool02) {
		log(String.valueOf(bool01) + DEFAULT_SEP_WHILE_NO_CRLF + String.valueOf(bool02) );
	}
	public static <T1, T2> void log(T1 row, T2 col) {
		log(row.toString() + DEFAULT_SEP_WHILE_NO_CRLF + col.toString() );
	}
	
	// 打印自定义的主题
	public static void logForPage(String page) {
		logFor(page, "page", HORIZON_LINES, HORIZON_LINES);
	}
	public static void logForThemes(String theme) {
		logFor(theme, "theme", HORIZON_STARTS, HORIZON_STARTS);
	}
	public static void logForPage(Object page) {
		logFor(page.toString(), "page", HORIZON_LINES, HORIZON_LINES);
	}
	public static void logForThemes(Object theme) {
		logFor(theme.toString(), "theme", HORIZON_STARTS, HORIZON_STARTS);
	}
	public static void logFor(String subject, String subjectKey, String before, String after) {
		log(before + " [ " + subjectKey + " : " + subject + " ] " + after);
	}
	
	// 刷出缓冲区的数据		add at 2016.04.15
	public static void flush() {
		try {
			if(logFile != null) {
				if(Tools.bufExists(logBufName) ) {
					Tools.flushBuffer(logBufName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 	
	// ------------ 待续 --------------------
	
	
	

	
}
