/**
 * file name : Tools.java
 * created at : 6:58:34 PM Jul 25, 2015
 * created by 970655147
 */

package com.hx.crawler.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.xml.sax.InputSource;

import com.hx.crawler.attrHandler.StandardHandlerParser;
import com.hx.crawler.attrHandler.StandardHandlerParser.Types;
import com.hx.crawler.attrHandler.interf.HandlerParser;
import com.hx.crawler.attrHandler.operation.CompositeOperationAttrHandler;
import com.hx.crawler.attrHandler.operation.interf.OperationAttrHandler;
import com.hx.crawler.crawler.HtmlCrawler;
import com.hx.crawler.crawler.SingleUrlTask;
import com.hx.crawler.crawler.interf.Crawler;
import com.hx.crawler.crawler.interf.ScriptParameter;
import com.hx.crawler.util.LogPattern.LogPatternChain;
import com.hx.crawler.util.LogPattern.LogPatternType;
import com.hx.crawler.xpathParser.XPathParser;
import com.hx.crawler.xpathParser.interf.ResultJudger;

// 工具类
public class Tools {
	
	// 常量
	public static final Random ran = new Random();
	public static final Character SLASH = Constants.SLASH;
	public static final Character INV_SLASH = Constants.INV_SLASH;
	public static final Character DOT = Constants.DOT;
	public static final Character COMMA = Constants.COMMA;
	public static final Character COLON = Constants.COLON;	
	public static final Character SPACE = Constants.SPACE;
	public static final Character TAB = Constants.TAB;
	public static final Character CR = Constants.CR;
	public static final Character LF = Constants.LF;
	public static final Character QUESTION = Constants.QUESTION;
	public static final Character QUOTE = Constants.QUOTE;
	public static final Character SINGLE_QUOTE = Constants.SINGLE_QUOTE;
	public static final String CRLF = Constants.CRLF;
	public static final String EMPTY_STR = Constants.EMPTY_STR;
	public static final String NULL = Constants.NULL;
	public static final String TRUE = Constants.TRUE;
	public static final String FALSE = Constants.FALSE;
	
	// 业务相关常量
	public static final String TASK = "task";
	public static final String SITE = "site";
	public static final String NAME = "name";
	public static final String URL = "url";
	public static final BigDecimal BIGDEC_ZERO = new BigDecimal("0.0");
	public static final Integer INTE_ZERO = new Integer("0");
	public static final String PAGE_NO = "pageNo";
	public static final String KEY_WORD = "keyWord";
	public static final String DEBUG_ENABLE = "debugEnable";
	public static final String FETCHED_RESULT = "fetchedResult";
	public static final String SPENT = "spent";
	public static final String DEPTH = "depth";
	public static final String REQUEST = "request";
	public static final String RESPONE = "response";
	public static final String REASON = "reason";
	public static final String MSG = "message";
	public static final String EXT = "ext";
	public static final String BUCKET = "bucket";
	// add at 2016.03.21
	public static final String BRAND = "brand";
	public static final String UPC = "universalProductCode";
	public static final String MPN = "manufacturePartNumber";
	public static final String LEVEL = "level";
	public static final String VALUE = "value";
	public static final String DESCRIPTION = "description";
	public static final String LIST = "list";
	public static final String REBATE = "rebate";
	public static final String FINAL = "final";
	public static final String PRICE = "price";
	
	// http相关常量
	public static final String COOKIE_STR = "Cookie";
	public static final String RESP_COOKIE_STR = "Set-Cookie";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String CONNECTION = "Connection";
	public static final String HOST = "Host";
	public static final String REFERER = "Referer";
	public static final String USER_AGENT = "User-Agent";
	public static final String DATE = "Date";
	public static final String SERVER = "Server";
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
	
	// parse 相关常量
	public static final String PARSE_METHOD_NAME = "parse";
	public static final boolean IS_PARSE_METHOD_STATIC = true;
	public static final Class[] PARSE_METHOD_PARAMTYPES = new Class[]{ScriptParameter.class };
	
	// 后缀相关
	public final static String HTML = ".html";
	public final static String JAVA = ".java";
	public final static String SCALA = ".scala";
	public final static String PYTHON = ".py";
	// add at 2016.05.13
	public final static String C_HEADER = ".h";
	public final static String C_SOURCE = ".c";
	public final static String CPP = ".cpp";
	public final static String PHP = ".php";
	public final static String TXT = ".txt";
	public final static String PNG = ".png";
	public final static String JPG = ".jpg";
	public final static String JPEG = ".jpeg";
	public final static String JS = ".js";
	public final static String MAP = ".map";
	public final static String ZIP = ".zip";
	public final static String IDX = ".idx";
	public final static String FIV = ".fiv";
	public final static String MP4 = ".mp4";
	public final static String GP3 = ".3gp";
	public final static String RMVB = ".rmvb";
	public final static String RM = ".rm";
	public final static String AVI = ".avi";
	public final static String LOG = ".log";
	// add at 2016.05.13
	public final static String CLASS = ".class";
	public final static String DOC = ".doc";
	public final static String DOCX = ".docx";
	public final static String XLS = ".xls";
	public final static String XLSX = ".xlsx";
	public final static String PPT = ".ppt";
	public final static String PPTX = ".pptx";
	
	// 编码相关		add at 2016.04.16
	public final static String ASCII = "ascii";
	public final static String ISO_8859_1 = "iso-8859-1";
	public final static String UTF_8 = "utf-8";
	public final static String UTF_16 = "utf-16";
	public final static String GBK = "gbk";
	public final static String GB2312 = "gb2312";
	
	// 字节的表示相关
	public final static String BYTE = "byte";
	public final static String KB = "kb";
	public final static String MB = "mb";
	public final static String GB = "gb";
	public final static String TB = "tb";
	public final static String PB = "pb";
	public final static String EB = "eb";
	public final static String ZB = "zb";
	public final static String YB = "yb";
	
	// 打印日志相关 [add at 2016.03.17]
	public final static long LOG_ON_SAVE = 1 ;
	public final static long LOG_ON_APPEND = LOG_ON_SAVE << 1 ;
	public final static long LOG_ON_DELETE = LOG_ON_APPEND << 1 ;
	public final static long LOG_ON_COPY = LOG_ON_DELETE << 1 ;
	public final static long LOG_ON_DOWNLOAD = LOG_ON_COPY << 1 ;
	public final static long LOG_ON_AWAIT_TASK_END = LOG_ON_DOWNLOAD << 1 ;
	public final static long LOG_ON_FLUSH_BUFFER = LOG_ON_AWAIT_TASK_END << 1 ;
	public final static long LOG_ON_ALL = LOG_ON_SAVE | LOG_ON_APPEND | LOG_ON_DELETE | LOG_ON_COPY 
								| LOG_ON_DOWNLOAD | LOG_ON_AWAIT_TASK_END | LOG_ON_FLUSH_BUFFER;
	public final static long LOG_ON_NONE = ~LOG_ON_ALL;
	public static long LOG_ON_MINE_CONF = LOG_ON_ALL;
	
	// --------------------------- 可配置变量 --------------------------------------
	// 线程池相关
	public static int CHECK_INTERVAL = Constants.CHECK_INTERVAL;
	public static int N_THREADS = Constants.N_THREADS;
	public static ThreadPoolExecutor threadPool = Constants.threadPool;
	
	// 临时文件相关
	public static String TMP_NAME = Constants.TMP_NAME;
	public static String TMP_DIR = Constants.TMP_DIR;
	public static AtomicInteger TMP_IDX = Constants.TMP_IDX;
	public static String SUFFIX = Constants.SUFFIX;
	public static int BUFF_SIZE_ON_TRANS_STREAM = Constants.BUFF_SIZE_ON_TRANS_STREAM;
	public static String DEFAULT_CHARSET = Constants.DEFAULT_CHARSET;
	public static boolean WRITE_ASYNC = Constants.WRITE_ASYNC;
	
	// 文件名后面可能出现的其他符号
	static Set<Character> mayBeFileNameSeps = Constants.mayBeFileNameSeps;
	// 如果字符串为一下字符串, 将其视为空字符串
	static Set<String> emptyStrCondition = Constants.emptyStrCondition;
	// ----------------- 属性结束 -----------------------
	
	// 初始化
	static {
		threadPool = Tools.newFixedThreadPool(N_THREADS);
	}
	
	// --------------------------- 配置可配置变量的接口 ----------------------------------------
	public static void setTmpIdx(int idx) {
		TMP_IDX.set(idx);
	}
	public static void setTmpDir(String tmpDir) {
    	Tools.assert0(tmpDir != null, "'tmpDir' can't be null ");
		TMP_DIR = tmpDir;
	}
	public static void setTmpName(String tmpName) {
    	Tools.assert0(tmpName != null, "'tmpName' can't be null ");
		TMP_NAME = tmpName;
	}
	public static void setSuffix(String suffix) {
    	Tools.assert0(suffix != null, "'suffix' can't be null ");
		SUFFIX = suffix;
	}
	// 配置defaultCharSet
	public static void setDefaultCharSet(String defaultCharSet) {
    	Tools.assert0(defaultCharSet != null, "'defaultCharSet' can't be null ");
		DEFAULT_CHARSET = defaultCharSet;
	}
	public static void setLogOnMine(long logOnMine) {
		LOG_ON_MINE_CONF = logOnMine;
	}
	public static void setBuffSize(int buffSize) {
    	Tools.assert0(buffSize > 0, "buffSize must > 0 ");
		BUFF_SIZE_ON_TRANS_STREAM = buffSize;
	}
    // 配置checkInterval
    public static void setCheckInterval(int checkInterval) {
    	Tools.assert0(checkInterval > 0, "checkInterval must > 0 ");
    	CHECK_INTERVAL = checkInterval;
    }
    // 配置线程池中线程的个数
    public static void setNThread(int nThread) {
    	Tools.assert0(nThread > 0, "nThread must > 0 ");
    	if(isThreadPoolRunning(threadPool) ) {
    		Log.err("the threadPool is running NOW, please try again later !");
    		return ;
    	}
    	
    	N_THREADS = nThread;
    	threadPool = newFixedThreadPool(N_THREADS);
    }
    
	// ---------------临时文件相关---------------
	// 获取临时路径的下一个路径[返回文件路径]
	public static String getNextTmpPath() {
		return TMP_DIR + "\\" + getNextTmpName() + SUFFIX;
	}
	public static String getNextTmpPath(String suffix) {
		return TMP_DIR + "\\" + getNextTmpName() + suffix;
	}
	public static String getTmpPath(int idx) {
		return TMP_DIR + "\\" + TMP_NAME + idx + SUFFIX;
	}
	public static String getTmpPath(int idx, String suffix) {
		return TMP_DIR + "\\" + TMP_NAME + idx + suffix;
	}
	public static String getTmpPath(String name) {
		return TMP_DIR + "\\" + name + SUFFIX;
	}
	public static String getTmpPath(String name, String suffix) {
		return TMP_DIR + "\\" + name + suffix;
	}
	public static String getNextTmpDir() {
		return TMP_DIR + "\\" + getNextTmpName();
	}
	public static String getTmpDir(int idx) {
		return TMP_DIR + "\\" + TMP_NAME + idx;
	}
	public static String getTmpDir(String name) {
		return TMP_DIR + "\\" + name;
	}
	public static String getFilePath(String dir, String file) {
		Tools.assert0(dir != null, "'dir' can't be null ");
		Tools.assert0(file != null, "'file' can't be null ");
		return Tools.removeIfEndsWith(dir, "/") + Tools.addIfNotStartsWith(file, "/");
	}
	
	// 获取临时文件的下一个索引[生成文件名称]
	private static String getNextTmpName() {
		return TMP_NAME + (TMP_IDX.getAndIncrement() );
	}
	
	// ----------------- 文件操作相关方法 -----------------------
	// 判断是否需要打印日志
	public static boolean isLog(long logFlags, long logMask) {
		return ((logFlags & logMask) != 0);
	}
	// 将html字符串保存到指定的文件中
	// add 'isAsync' at 2016.04.16
	public static void save(String html, File targetFile, String charset, boolean isAsync, long logFlags) throws IOException {
		Tools.assert0(html != null, "'html' can't be null ");
		Tools.assert0(targetFile != null, "'targetFile' can't be null ");
		Tools.assert0(targetFile != null, "'targetFile' can't be null ");
		
		write(html, targetFile, charset, isAsync, false);
		if(isLog(logFlags, LOG_ON_APPEND) ) {
			Log.log("append content to \" " + targetFile.getAbsolutePath() + " \" success ...");
		}
	}
	public static void save(String html, String nextTmpName, String charset, boolean isAsync, long logFlags) throws IOException {
		save(html, new File(nextTmpName), charset, isAsync, logFlags );
	}
	public static void save(String html, String nextTmpName, boolean isAsync, long logFlags) throws IOException {
		save(html, new File(nextTmpName), isAsync, logFlags );
	}
	public static void save(String html, File nextTmpFile, boolean isAsync, long logFlags) throws IOException {
		save(html, nextTmpFile, DEFAULT_CHARSET, isAsync, logFlags);
	}
	public static void save(String html, String nextTmpName, long logFlags) throws IOException {
		save(html, new File(nextTmpName), logFlags );
	}
	public static void save(String html, File nextTmpFile, long logFlags) throws IOException {
		save(html, nextTmpFile, DEFAULT_CHARSET, WRITE_ASYNC, logFlags);
	}
	public static void save(String html, String nextTmpName, String charset, long logFlags) throws IOException {
		save(html, new File(nextTmpName), charset, logFlags );
	}
	public static void save(String html, File nextTmpFile, String charset, long logFlags) throws IOException {
		save(html, nextTmpFile, charset, WRITE_ASYNC, logFlags );
	}
	
	public static void append(String html, File nextTmpFile, String charset, boolean isAsync, long logFlags) throws IOException {
		write(html, nextTmpFile, charset, isAsync, true);
		if(isLog(logFlags, LOG_ON_APPEND) ) {
			Log.log("append content to \" " + nextTmpFile.getAbsolutePath() + " \" success ...");
		}
	}
	public static void append(String html, String nextTmpName, String charset, boolean isAsync, long logFlags) throws IOException {
		append(html, new File(nextTmpName), charset, isAsync, logFlags );
	}
	public static void append(String html, String nextTmpName, boolean isAsync, long logFlags) throws IOException {
		append(html, new File(nextTmpName), isAsync, logFlags );
	}
	public static void append(String html, File nextTmpFile, boolean isAsync, long logFlags) throws IOException {
		append(html, nextTmpFile, DEFAULT_CHARSET, isAsync, logFlags);
	}
	public static void append(String html, String nextTmpName, long logFlags) throws IOException {
		append(html, new File(nextTmpName), logFlags );
	}
	public static void append(String html, File nextTmpFile, long logFlags) throws IOException {
		append(html, nextTmpFile, DEFAULT_CHARSET, WRITE_ASYNC, logFlags);
	}
	public static void append(String html, String nextTmpName, String charset, long logFlags) throws IOException {
		append(html, new File(nextTmpName), charset, logFlags );
	}
	public static void append(String html, File nextTmpFile, String charset, long logFlags) throws IOException {
		append(html, nextTmpFile, charset, WRITE_ASYNC, logFlags );
	}
	
	public static void save(String html, String nextTmpName, boolean isAsync) throws IOException {
		save(html, nextTmpName, isAsync, LOG_ON_MINE_CONF);
	}
	public static void save(String html, String nextTmpName, String charset, boolean isAsync) throws IOException {
		save(html, nextTmpName, charset, isAsync, LOG_ON_MINE_CONF );
	}
	public static void save(String html, File nextTmpFile, boolean isAsync) throws IOException {
		save(html, nextTmpFile, isAsync, LOG_ON_MINE_CONF);
	}
	public static void save(String html, File nextTmpFile, String charset, boolean isAsync) throws IOException {
		save(html, nextTmpFile, charset, isAsync, LOG_ON_MINE_CONF);
	}
	public static void save(String html, String nextTmpName) throws IOException {
		save(html, nextTmpName, WRITE_ASYNC );
	}
	public static void save(String html, String nextTmpName, String charset) throws IOException {
		save(html, nextTmpName, charset, WRITE_ASYNC );
	}
	public static void save(String html, File nextTmpFile) throws IOException {
		save(html, nextTmpFile, WRITE_ASYNC);
	}
	public static void save(String html, File nextTmpFile, String charset) throws IOException {
		save(html, nextTmpFile, charset, WRITE_ASYNC);
	}
	
	public static void append(String html, String nextTmpName, boolean isAsync) throws IOException {
		append(html, nextTmpName, isAsync, LOG_ON_MINE_CONF);
	}
	public static void append(String html, String nextTmpName, String charset, boolean isAsync) throws IOException {
		append(html, nextTmpName, charset, isAsync, LOG_ON_MINE_CONF );
	}
	public static void append(String html, File nextTmpFile, boolean isAsync) throws IOException {
		append(html, nextTmpFile, isAsync, LOG_ON_MINE_CONF);
	}
	public static void append(String html, File nextTmpFile, String charset, boolean isAsync) throws IOException {
		append(html, nextTmpFile, charset, isAsync, LOG_ON_MINE_CONF);
	}
	public static void append(String html, String nextTmpName) throws IOException {
		append(html, nextTmpName, WRITE_ASYNC );
	}
	public static void append(String html, String nextTmpName, String charset) throws IOException {
		append(html, nextTmpName, charset, WRITE_ASYNC );
	}
	public static void append(String html, File nextTmpFile) throws IOException {
		append(html, nextTmpFile, WRITE_ASYNC);
	}
	public static void append(String html, File nextTmpFile, String charset) throws IOException {
		append(html, nextTmpFile, charset, WRITE_ASYNC);
	}
	
	// 1. could use 'tryWithResource' replace 'tryFinally'
	// 2. update 'BufferedOutputStream' with 'FileOutputStream' cause there need not 'Buffer'
	// at 2016.04.16
	public static void write(final String html, final File nextTmpFile, final String charset,  boolean isAsync, final boolean isAppend) throws IOException {
		Runnable writeTask = (new Runnable() {
			@Override
			public void run() {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(nextTmpFile, isAppend);
					fos.write(html.getBytes(charset) );
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if(fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		if(! isAsync) {
			writeTask.run();
		} else {
			execute(writeTask);
		}
	}
	public static void write(final String html, final File nextTmpFile, final String charset, final boolean isAppend) throws IOException {
		write(html, nextTmpFile, charset, isAppend, WRITE_ASYNC);
	}
	
	// 移除指定的文件
	public static void delete(String path, long logFlags) {
		Tools.assert0(path != null, "'path' can't be null ");
		
		File file = new File(path);
		if(file.exists() ) {
			boolean isSucc = file.delete();
			if(isLog(logFlags, LOG_ON_DELETE) ) {
				if(isSucc) {
					Log.log("delete \" " + path + " \" success ...");
				} else {
					Log.log("delete \" " + path + " \" failed, maybe inuse ...");
				}
			}
		} else {
			if(isLog(logFlags, LOG_ON_DELETE) ) {
				Log.log("\" " + path + " \" is not exists ...");
			}
		}
	}
	public static void delete(String path) {
		delete(path, LOG_ON_MINE_CONF);
	}
	
    // 复制指定的文件
    public static void copy(String src, String dst, long logFlags) throws IOException {
    	Tools.assert0(src != null, "'src' can't be null ");
    	Tools.assert0(dst != null, "'dst' can't be null ");
    	
        File srcFile = new File(src);
        File dstFile = new File(dst);
        if(srcFile.isDirectory() ) {
        	if(isLog(logFlags, LOG_ON_COPY) ) {
        		Log.log("srcFile \" " + src + " \" can't be folder ...");
        	}
            return ;
        }
        if(! srcFile.exists() ) {
        	if(isLog(logFlags, LOG_ON_COPY) ) {
        		Log.log("srcFile \" " + src + " \" do not exists ...");
        	}
            return ;
        }
        if(dstFile.exists() ) {
        	if(isLog(logFlags, LOG_ON_COPY) ) {
        		Log.log("dstFile \" " + dst + " \" does exists, please remove it first [make sure it is not important] ...");
        	}
            return ;
        }

        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(dstFile);
        copy(fis, fos);
        if(isLog(logFlags, LOG_ON_COPY) ) {
        	Log.log("copy file \" " + src + " \" -> \" " + dst + " \" success ...");
        }
    }
    public static void copy(String src, String dst) throws IOException {
    	copy(src, dst, LOG_ON_MINE_CONF);
    }

	// 获取给定的输入流中的字符内容
	public static String getContent(InputStream is, String charset) throws IOException {
		Tools.assert0(is != null, "'inputStream' can't be null ");
		Tools.assert0(charset != null, "'charset' can't be null ");
		
		StringBuilder sb = new StringBuilder(is.available() );
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(is, charset) );
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line );
				sb.append(Tools.CRLF);
			}
		} finally {
			if(br != null) {
				br.close();
			}
		}
		
		return sb.toString();
	}
	public static String getContent(InputStream is) throws IOException {
		return getContent(is, DEFAULT_CHARSET);
	}
	public static String getContent(String path, String charset) throws IOException {
		return getContent(new File(path), charset);
	}
	public static String getContent(File file, String charset) throws IOException {
		return getContent(new FileInputStream(file), charset);
	}
	public static String getContent(String path) throws IOException {
		return getContent(new File(path), DEFAULT_CHARSET);
	}
	public static String getContent(File file) throws IOException {
		return getContent(file, DEFAULT_CHARSET);
	}
	
	// 获取文件的所有的行, 存储在一个结果的List, 文件过大, 慎用此方法
	public static List<String> getContentWithList(File file, String charset) throws IOException {
		Tools.assert0(file != null, "'file' can't be null ");
		Tools.assert0(charset != null, "'charset' can't be null ");
		
		List<String> lines = new LinkedList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset) );
			String line = null;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			if(br != null) {
				br.close();
			}
		}
		
		return lines;
	}
	public static List<String> getContentWithList(File file) throws IOException {
		return getContentWithList(file, DEFAULT_CHARSET);
	}
	public static List<String> getContentWithList(String file, String charset) throws IOException {
		return getContentWithList(new File(file), charset );
	}
	public static List<String> getContentWithList(String file) throws IOException {
		return getContentWithList(new File(file) );
	}
	
	// ----------------- 业务方法 -----------------------
	// 所有的数字的Character
	static Set<Character> nums = new HashSet<>();
	static {
		for(char i='0'; i<='9'; i++) {
			nums.add(i);
		}
	}
	
	// 处理价格, 也可以用于处理提取字符串中的BigDecimal的情况
	public static BigDecimal dealPrice(String str) {
		if(isEmpty(str)) {
			return BIGDEC_ZERO;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if(nums.contains(ch) || (ch == '.') ) {
				sb.append(ch);
			}
		}
		if(sb.length() == 0) {
			return BIGDEC_ZERO;
		} else {
			return new BigDecimal(sb.toString());
		}
	}
	
	// 处理页数, 也可以用于处理提取字符串中的整数的情况
	public static Integer dealPageNum(String str) {
		if(isEmpty(str)) {
			return INTE_ZERO;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if(nums.contains(ch) ) {
				sb.append(ch);
			}
		}
		if(sb.length() == 0) {
			return INTE_ZERO;
		} else {
			return new Integer(sb.toString());
		}
	}
	
	// 匹配siteUrl的regex
	static Pattern siteUrlPattern = Pattern.compile("^(\\w{3,5}://\\w+(\\.\\w+)+?/)(.*)");
	
	// 获取站点的首页url
	// http://www.baidu.com/tieba/java/page01.jsp  =>  http://www.baidu.com/
	public static String getSiteUrl(String url) {
		Tools.assert0(! isEmpty(url), "'url' can't be null ");
		Matcher matcher = siteUrlPattern.matcher(url);
		if(matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
	
	// 将绝对/ 相对的url转换为绝对的url
	// 转换 /path & ./path
	public static String transformUrl(String siteUrl, String relativePath) {
		Tools.assert0(! isEmpty(siteUrl), "'siteUrl' can't be null ");
		Tools.assert0(! isEmpty(relativePath), "'relativePath' can't be null ");
		
		if(relativePath.startsWith("/") ) {
			return getSiteUrl(siteUrl) + removeIfStartsWith(relativePath, "/");
		} else if (relativePath.startsWith("./") ) {
			return siteUrl.substring(0, siteUrl.lastIndexOf("/")+1 ) + Tools.removeIfStartsWith(relativePath, "./");
		} else {
			return relativePath;
		}
	}

	// 如果给定的字符串以startsWith, 则移除startsWith
	public static String removeIfStartsWith(String str, String startsWith) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(startsWith != null, "'startsWith' can't be null ");
		
		if(str.startsWith(startsWith) ) {
			return str.substring(startsWith.length() );
		}
		
		return str;
	}
	public static String removeIfEndsWith(String str, String endsWith) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(endsWith != null, "'endsWith' can't be null ");
		
		if(str.endsWith(endsWith) ) {
			return str.substring(0, str.length() - endsWith.length());
		}
		
		return str;
	}
	public static String addIfNotStartsWith(String str, String startsWith) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(startsWith != null, "'startsWith' can't be null ");
		
		if(! str.startsWith(startsWith) ) {
			return startsWith + str;
		}
		
		return str;
	}
	public static String addIfNotEndsWith(String str, String endsWith) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(endsWith != null, "'endsWith' can't be null ");
		
		if(! str.endsWith(endsWith) ) {
			return str + endsWith;
		}
		
		return str;
	}
	
	// 判断字符串是否为空[null, "", "null"]
	public static boolean isEmpty(String str) {
		return (str == null) || emptyStrCondition.contains(str.trim());
	}
	public static <T> boolean isEmpty(Collection<T> arr) {
		return (arr == null) || (arr.size() == 0);
	}
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return (map == null) || (map.size() == 0);
	}
	
	// 获取str中以start 和end之间的字符串
	public static String getStrInRange(String str, String start, String end) {
		return getStrInRange(str, start, end, false, false);
	}
	public static String getStrInRangeInclude(String str, String start, String end) {
		return getStrInRange(str, start, end, true, true);
	}
	public static String getStrInRangeWithStart(String str, String start) {
		return getStrInRangeWithStart(str, start, false);
	}
	public static String getStrInRangeWithStartInclude(String str, String start) {
		return getStrInRangeWithStart(str, start, true);
	}
	public static String getStrInRangeWithEnd(String str, String end) {
		return getStrInRangeWithEnd(str, end, false);
	}
	public static String getStrInRangeWithEndInclude(String str, String end) {
		return getStrInRangeWithEnd(str, end, true);
	}
	public static String getStrInRange(String str, String start, String end, boolean includeStart, boolean includeEnd) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(start != null, "'start' can't be null ");
		Tools.assert0(end != null, "'end' can't be null ");
		
		int startIdx = str.indexOf(start);
		if(startIdx == -1) {
			return Tools.EMPTY_STR;
		}
		
		int endIdx = str.indexOf(end, startIdx + start.length());
		if(endIdx == -1) {
			return Tools.EMPTY_STR;
		}
		
		if(! includeStart) {
			startIdx += start.length();
		}
		if(includeEnd) {
			endIdx += end.length();
		}
		
		return str.substring(startIdx, endIdx);
	}
	public static String getStrInRangeWithStart(String str, String start, boolean include) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(start != null, "'start' can't be null ");
		
		int idx = str.indexOf(start);
		if(idx != -1) {
			if(! include) {
				idx += start.length();
			}
			return str.substring(idx);
		}
		
		return Tools.EMPTY_STR;
	}
	public static String getStrInRangeWithEnd(String str, String end, boolean include) {
		Tools.assert0(str != null, "'str' can't be null ");
		Tools.assert0(end != null, "'end' can't be null ");
		
		int idx = str.indexOf(end);
		if(idx != -1) {
			if(include) {
				idx += end.length();
			}
			return str.substring(0, idx);
		}
		
		return Tools.EMPTY_STR;
	}
	
	// 整合这三类方法, 之前的实现有点冗余			--2015.12.17
//	public static String getStrInRange(String str, String start, String end) {
////		int startIdx = str.indexOf(start);
////		if(startIdx == -1) {
////			return Tools.EMPTY_STR;
////		}
////		
////		int endIdx = str.indexOf(end, startIdx + start.length());
////		if(endIdx == -1) {
////			return Tools.EMPTY_STR;
////		}
////		
////		return str.substring(startIdx + start.length(), endIdx);
//		return getStrInRange0(str, start, end, false, false);
//	}
	
	// ----------------- crawler 处理相关 -----------------------
	// 获取处理过之后的文档
	public static void getPreparedDoc(String url, String file) throws Exception {
		Tools.assert0(url != null, "'url' can't be null ");
		String html = HtmlCrawler.newInstance().getPage(url).getContent();
		getPreparedDoc(url, html, file);
	}
	public static void getPreparedDoc(String url, String html, String file) throws Exception {
		Tools.assert0(html != null, "'html' can't be null ");
		Tools.assert0(file != null, "'file' can't be null ");
		Tools.save(StringEscapeUtils.unescapeHtml(Tools.normalize(html) ), file);
	}
	
	public final static com.hx.crawler.xpathParser.interf.Parser xpathParser = new XPathParser();
	// 通过xpath 获取结果
	public static JSONArray getResultByXPath(String html, String url, String xpath) throws Exception {
		Tools.assert0(html != null, "'html' can't be null ");
		Tools.assert0(xpath != null, "'xpath' can't be null ");
		return xpathParser.parse(Tools.normalize(html), url, xpath);
	}
	public static JSONArray getResultByXPathes(String html, String url, String[] xpathes, ResultJudger judger) throws Exception {
		Tools.assert0(html != null, "'html' can't be null ");
		Tools.assert0(xpathes != null, "'xpathes' can't be null ");
		Tools.assert0(judger != null, "'judger' can't be null ");
		
		for(int i=0; i<xpathes.length; i++) {			
			JSONArray res = getResultByXPath(html, url, xpathes[i]);
			if(! judger.isResultNull(i, res)) {
				return res;
			}
		}
		
		return null;
	}
	
	// 格式化html [去掉, 添加 不规范的标签]
	// 思路 : 使用tagSoup规范化xml
		// 注意 : 需要重定向ContentHandler的输出
		// 需要去掉xml声明, 需要去掉html标签的xmlns的属性
	public static String normalize(String html) throws Exception {
		Tools.assert0(html != null, "'html' can't be null ");
//		StringReader xmlReader = new StringReader("");
		StringReader sr = new StringReader(html);
		Parser parser = new Parser();		//实例化Parse
		
//		// 输出格式化之后的xml
		XMLWriter writer = new XMLWriter();	//实例化XMLWriter，即SAX内容处理器
		parser.setContentHandler(writer);	//设置内容处理器
		StringWriter strWriter = new StringWriter();
		writer.setOutput(strWriter);
		
		parser.parse(new InputSource(sr));	//解析
		
//		Scanner scan = new PYXScanner();
//		scan.scan(xmlReader, parser);	//通过xmlReader读取解析后的结果
		
//		char[] buff = new char[1024];
//		StringBuilder sb = new StringBuilder();
//		while(xmlReader.read(buff) != -1) {
//		    sb.append(buff);		//打印解析后的结构良好的HTML文档
//		} 
		
		// 获取结构良好的html, 并去掉xml声明
		String res = strWriter.toString();
		res = res.substring(res.indexOf("\n") + 1);
		
		// 就是因为多了这个xmlns的声明, 导致了我的xPath读取数据有问题。。。, 或者说 只能通过/*[@XX='XXX'] 来读取数据
		// 并且读取到的数据 还有一个xmlns的声明...						--2015.07.31
//		String html = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><body><strong><span id=\"ctl01_ContentPlaceHolder1_lblPriceTitle\">ProductPricing</span>:</strong></body></html>";
		// 所以 这里对res进行统一的处理, 去掉html标签的xmlns的声明
		String needReplace = "xmlns=\"http://www.w3.org/1999/xhtml\"";
		return res.replace(needReplace, Tools.EMPTY_STR);
	}
	
	// 创建一个ScriptParameter
	public static SingleUrlTask newSingleUrlTask(Crawler<HttpResponse, Header, String, NameValuePair, String, String> crawler, String url, Map<String, Object>param) {
		SingleUrlTask res = new SingleUrlTask();
		res.setCrawler(crawler);
		res.setUrl(url);
		res.setParam(param);
		crawler.setScriptParameter(res);
		res.setTaskGroupId(110);
		res.setTaskId(120);
		
		return res;
	}
	
	// 通过xpath获取真实的xpath
	// 可以改写为JSONArray.toString() 实现 [思路来自'duncen'[newEgg同事] ]
	public static String getRealXPathByXPathObj(String xpath) {
		Tools.assert0(xpath != null, "'xpath' can't be null ");
		return new JSONArray().element(xpath).toString();
	}
	public static String getRealXPathByXPathObj(String... xpathes) {
		JSONArray res = new JSONArray();
		for(String xpath : xpathes) {
			res.add(xpath);
		}
		
		return res.toString();
	}
//	public static String getRealXPathByXPathObj(String xpath) {
//		return "[" + xpath + "]";
//	}
//	public static String getRealXPathByXPathObj(String... xpathes) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("[");
//		sb.append(xpathes[0]);
//		for(int i=1; i<xpathes.length; i++) {
//			sb.append(", \r\n");
//			sb.append(xpathes[i]);
//		}
//		sb.append("]");
//		
//		return sb.toString();
//	}
	
	// 利用反射调用指定的class的methodName方法
	public static void parse(String className, String url, Map<String, Object> params) throws Exception {
		parse(className, url, params, PARSE_METHOD_NAME, IS_PARSE_METHOD_STATIC, PARSE_METHOD_PARAMTYPES);
	}
	public static void parse(String className, String url, Map<String, Object> params, String methodName, boolean isStaticMethod, Class[] methodParamTypes) throws Exception {
		Tools.assert0(className != null, "'className' can't be null ");
		Tools.assert0(url != null, "'url' can't be null ");
		Tools.assert0(methodName != null, "'methodName' can't be null ");
		
		Class clazz = Class.forName(className);
		Method method = clazz.getMethod(methodName, methodParamTypes);
		SingleUrlTask singleUrlTask = newSingleUrlTask(HtmlCrawler.newInstance(), url, params);
		
		try {
			if(isStaticMethod) {
				method.invoke(null, singleUrlTask);
			} else {
				method.invoke(clazz.newInstance(), singleUrlTask);
			}
		} catch(Exception e) {
			logErrorMsg(singleUrlTask, e);
		}
	}
	public static void parseAsync(final String className, final String url, final Map<String, Object> params) throws Exception {
		parseAsync(className, url, params, PARSE_METHOD_NAME, IS_PARSE_METHOD_STATIC, PARSE_METHOD_PARAMTYPES);
	}
	public static void parseAsync(final String className, final String url, final Map<String, Object> params, final String methodName, final boolean isStaticMethod, final Class[] methodParamTypes) throws Exception {
		execute(new Runnable() {
			public void run() {
				try {
					parse(className, url, params, methodName, isStaticMethod, methodParamTypes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public static void execute(Runnable runnable) {
		threadPool.execute(runnable);
	}
	
	// 为nextStageParams添加category
	public static void addNameUrlSite(JSONObject category, JSONObject nextStageParams) {
		Tools.assert0(! isEmpty(category), "'category' can't be null ");
		nextStageParams.put(Tools.NAME, category.getString(NAME) );
		nextStageParams.put(Tools.URL, category.getString(URL) );
		nextStageParams.put(Tools.SITE, nextStageParams.getString(SITE) + "." + category.getString(NAME) );
	}
	
	// 通过产品的数目, 以及每一页显示的产品的数目, 计算页数
	public static int calcPageNums(int productNum, int numPerPage) {
		return ((productNum-1) / numPerPage) + 1;
	}
	
	// 空格类字符
	static Set<Character> spaces = new HashSet<>();
	static {
		spaces.add(SPACE);
		spaces.add(TAB);
		spaces.add(CR);
		spaces.add(LF);
	}
	
	// 将字符串的多个连续的空格转换为一个空格
	// 思路 : 如果str为null  直接返回null
	// 将str中多个相邻的空格替换为一个空格[SPACE]
	// 如果结果的字符串长度为1 并且该字符为空格, 则直接返回空字符串
	// 否则  去掉前后的空格, 返回之间的子字符串
	// 可以直接使用正则进行处理		// str.replaceAll("\\s+", " ");
	public static String trimSpacesAsOne(String str) {
		if(isEmpty(str) ) {
			return EMPTY_STR;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			if(spaces.contains(str.charAt(i)) ) {
				sb.append(SPACE);
				int nextI = i+1;
				while((nextI < str.length() ) && spaces.contains(str.charAt(nextI)) ) nextI++ ;
				i = nextI - 1;
				continue ;
			}
			sb.append(str.charAt(i) );
		}
		
		if((sb.length() == 0) || ((sb.length() == 1) && spaces.contains(sb.charAt(0))) ) {
			return EMPTY_STR;
		} else {
			int start = 0, end = sb.length();
			if(spaces.contains(sb.charAt(start)) ) {
				start ++;
			}
			if(spaces.contains(sb.charAt(end-1)) ) {
				end --;
			}
			
			return sb.substring(start, end);
		}
	}
	public static String[] trimSpacesAsOne(String[] arr) {
		Tools.assert0(arr != null, "'arr' can't be null ");
		for(int i=0; i<arr.length; i++) {
			arr[i] = trimSpacesAsOne(arr[i]);
		}
		
		return arr;
	}
	public static List<String> trimSpacesAsOne(List<String> arr) {
		Tools.assert0(arr != null, "'arr' can't be null ");
		for(int i=0; i<arr.size(); i++) {
			arr.set(i, trimSpacesAsOne(arr.get(i)) );
		}
		
		return arr;
	}

	public static String trimAllSpaces(String str, Map<Character, Character> escapeMap) {
		if(isEmpty(str) ) {
			return EMPTY_STR;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			Character ch = str.charAt(i);
			if((escapeMap != null ) && escapeMap.containsKey(ch) ) {
				int prevI = i;
				i = str.indexOf(escapeMap.get(ch), i+1);
				if(i >= 0) {
					sb.append(str.substring(prevI, i+1) );
				} else {
					sb.append(str.substring(prevI) );
					break ;
				}
				continue ;
			}
			if(spaces.contains(str.charAt(i)) ) {
				int nextI = i+1;
				while((nextI < str.length() ) && spaces.contains(str.charAt(nextI)) ) nextI++ ;
				i = nextI - 1;
				continue ;
			}
			sb.append(str.charAt(i) );
		}
		return sb.toString();
	}
	public static String trimAllSpaces(String str) {
		return trimAllSpaces(str, null);
	}
	public static String[] trimAllSpaces(String[] arr, Map<Character, Character> escapeMap) {
		Tools.assert0(arr != null, "'arr' can't be null ");
		for(int i=0; i<arr.length; i++) {
			arr[i] = trimAllSpaces(arr[i], escapeMap);
		}
		
		return arr;
	}
	public static String[] trimAllSpaces(String[] arr) {
		return trimAllSpaces(arr, null);
	}
	public static List<String> trimAllSpaces(List<String> arr, Map<Character, Character> escapeMap) {
		Tools.assert0(arr != null, "'arr' can't be null ");
		for(int i=0; i<arr.size(); i++) {
			arr.set(i, trimAllSpaces(arr.get(i), escapeMap) );
		}
		
		return arr;
	}
	public static List<String> trimAllSpaces(List<String> arr) {
		return trimAllSpaces(arr, null);
	}
	
	// 去掉掉obj中所有的字符串类的值的相邻的多个空格
	// 思路 : 如果obj是空对象  则直接返回
	// 否则 遍历各个kv数据, 如果值为String  则去掉其多余的空格, 然后在更新obj中对应key的值
		// 否则如果 值为JSONObject, 递归
		// 否则如果 值为JSONArray, trimSpaces(JSONArray )
	public static void trimSpaces(JSONObject obj) {
		if(isEmpty(obj) ) {
			return ;
		}
		
		JSONArray names = obj.names();
		Iterator<?> it = names.iterator();
		while(it.hasNext()) {
			String key = String.valueOf(it.next() );
			Object val = obj.get(key );
			if(val instanceof String) {
				obj.put(key, trimSpacesAsOne((String) val));
			} else if(val instanceof JSONObject) {
				trimSpaces((JSONObject) val);
			} else if(val instanceof JSONArray) {
				trimSpaces((JSONArray) val);
			}
		}
	}

	// 去掉掉arr中所有的字符串类的值的相邻的多个空格
	// 思路 : 如果arr是空对象  则直接返回
	// 否则 遍历各个数据, 如果值为String  则去掉其多余的空格, 然后在更新obj中对应key的值
		// 否则如果 值为JSONObject, trimSpaces(JSONObject )
		// 否则如果 值为JSONArray, 递归
	public static void trimSpaces(JSONArray arr) {
		if(isEmpty(arr) ) {
			return ;
		}
		
		for(int i=0; i<arr.size(); i++ ) {
			Object val = arr.get(i);
			if(val instanceof String) {
				arr.set(i, trimSpacesAsOne((String) val));
			} else if(val instanceof JSONObject) {
				trimSpaces((JSONObject) val);
			} else if(val instanceof JSONArray) {
				trimSpaces((JSONArray) val);
			}
		}
	}
	
	// 确保arr中的每一个JSONObject都存在指定的key, 否则  则删除该条目
	// val.toString可以确保值为null的情形
	public static void removeIfNull(JSONArray arr, String key) {
		if(isEmpty(arr) ) {
			return ;
		}
		
		Iterator<?> it = arr.iterator();
		while(it.hasNext()) {
			Object obj = it.next();
			if(obj instanceof JSONObject) {
				if(! ((JSONObject) obj).containsKey(key)) {
					it.remove();
				} else {
					Object val = ((JSONObject) obj).get(key);
					// 1. "{'key' : '' }"
					// 2. "{'key' : null }"
					if(isEmpty(val.toString())) {
						it.remove();
					}
				}
			}
			
		}
	}
	
	// 去掉掉obj中所有的字符串类的值的相邻的多个空格
	// 思路 : 如果obj是空对象  则直接返回
	// 否则 遍历各个kv数据, 如果值为String  如果只为空  则去掉当前kv对
		// 否则如果 值为JSONObject, 递归,  如果该方法之后, val为空, 则移除val对应的条目
		// 否则如果 值为JSONArray, removeIfNull(JSONArray ),  如果该方法之后, val为空, 则移除val对应的条目
	public static void removeIfNull(JSONObject obj) {
		if(isEmpty(obj) ) {
			return ;
		}
		
		Iterator<?> it = obj.names().iterator();
		while(it.hasNext()) {
			String key = String.valueOf(it.next() );
			Object val = obj.get(key );
			if(val instanceof String) {
				if(isEmpty((String) val)) {
					obj.remove(key);
				}
			} else if(val instanceof JSONObject) {
				// 防止 "{'price' : null } "的情形
				if(isEmpty(val.toString()) ) {
					obj.remove(key);
				} else {
					removeIfNull((JSONObject) val);
					if(((JSONObject) val).isEmpty() ) {
						obj.remove(key);
					}
				}
			} else if(val instanceof JSONArray) {
				removeIfNull((JSONArray) val);
				if(((JSONArray) val).isEmpty() ) {
					obj.remove(key);
				}
			}
		}
	}
	
	// 去掉掉arr中所有的字符串类的值的相邻的多个空格
	// 思路 : 如果arr是空对象  则直接返回
	// 否则 遍历各个数据, 如果值为String  则去掉其多余的空格, 然后在更新obj中对应key的值
		// 否则如果 值为JSONObject, removeIfNull(JSONObject ), 如果该方法之后, val为空, 则移除val对应的条目
	// 注意 : 因为这里对JSONArray进行了删除操作, 所以这里使用了Iterator
	public static void removeIfNull(JSONArray arr) {
		if(isEmpty(arr) ) {
			return ;
		}
		
		Iterator<?> it = arr.iterator();
		while(it.hasNext() ) {
			Object val = it.next();
			if(val instanceof String) {
				if(isEmpty((String) val)) {
					it.remove();
				}
			} else if(val instanceof JSONObject) {
				// 防止 "{'price' : null } "的情形
				if(isEmpty(val.toString()) ) {
					it.remove();
				} else {
					removeIfNull((JSONObject) val);
					if(((JSONObject) val).isEmpty() ) {
						it.remove();
					}
				}
			}
		}
	}
	
	// 从spec中获取需要的数据
	// 注意 : 必需确保spec中每一个对象为JSONObject, name为spec的数据中需要检测的值, value为spec的数据中需要获取的值, getInSpec存放获取数据的键的(key[src源对象] -> key[dst目标对象])映射
//	[											
//	...												{
//    {													...
//        "value":" #F3F07AAR#ABA",			=>			"model":"#F3F07AAR#ABA"	
//        "name":"Model"								...
//    }												}
//	...	
//	]
	public static void getNeededFrom(JSONArray spec, JSONObject product, String name, String value, Map<String, String> getInSpec) {
		if((isEmpty(spec)) || (isEmpty(product)) || isEmpty(getInSpec) ) {
			return ;
		}
		
		Iterator<?> it = spec.iterator();
		while(it.hasNext()) {
			JSONObject val = (JSONObject) it.next();
			String key = val.getString(name);
			if(getInSpec.containsKey(key) ) {
				product.put(getInSpec.get(key), val.get(value));
			}
		}
	}

	// 过滤掉不需要的字符
	public static String filter(String str, Set<Character> needBeFiltered) {
		if(isEmpty(str) || isEmpty(needBeFiltered) ) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder(str.length());
		for(int i=0; i<str.length(); i++) {
			if(! needBeFiltered.contains(str.charAt(i)) ) {
				sb.append(str.charAt(i));
			}
		}
		
		return trimSpacesAsOne(sb.toString() );
	}
	public static JSONObject filter(JSONObject obj, Set<String> needBeFiltered) {
		if(isEmpty(obj) || isEmpty(needBeFiltered) ) {
			return null;
		}
		
		for(String filter : needBeFiltered) {
			obj.remove(filter);
		}
		return obj;
	}
	
	// 向sb中添加str
	public static void append(StringBuilder sb, String str, boolean isClean) {
		Tools.assert0(sb != null, "'sb' can't be null ");
		if(isClean) {
			sb.setLength(0);
		}
		sb.append(str);
	}
	public static void append(StringBuilder sb, String str) {
		append(sb, str, false);
	}
	public static void appendCRLF(StringBuilder sb, String str, boolean isClean) {
		Tools.assert0(sb != null, "'sb' can't be null ");
		if(isClean) {
			sb.setLength(0);
		}
		append(sb, str);
	}
	public static void appendCRLF(StringBuilder sb, String str) {
		appendCRLF(sb, str + CRLF, false);
	}
	
	// 获取taskName
	public static String getTaskName(ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask) {
		Tools.assert0(singleUrlTask != null, "'singleUrlTask' can't be null ");
		return "crawl " + singleUrlTask.getParam().get(Tools.TASK) + " from " + singleUrlTask.getParam().get(Tools.SITE);
	}
	
	// 获取键值对类型的数据对, 添加到headers中
	public static void addHeaders(File configFile, Map<String, String> headers, String sep) throws IOException {
		Tools.assert0(configFile != null, "'configFile' can't be null ");
		Tools.assert0(headers != null, "'headers' can't be null ");
		Tools.assert0(sep != null, "'sep' can't be null ");
		
		List<String> lines = Tools.getContentWithList(configFile);
		for(String line : lines) {
			int idx = line.indexOf(sep);
			if(idx > 0) {
				headers.put(line.substring(0, idx), line.substring(idx + 1));
			}
		}
	}
	
	// 解码含有unicode字符串的字符串
	// 遍历一次字符串, 寻找出匹配"\\uxxxx"的字符串, 然后将其解码为字符[unicode -> char]
	// 对于其他的字符不作处理
	public static String unicodeDecode(String str) {
		Tools.assert0(str != null, "'str' can't be null ");
		
		StringBuilder sb = new StringBuilder(str.length() );
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if(ch == SLASH) {
				char nextCh = str.charAt(i + 1);
				if(nextCh == 'u') {
					boolean isUnicode = true;
					for(int j=0; j<4; j++) {
						// '+2' escape '\\u'
						if(! isHexChar(str.charAt(i + j + 2)) ) {
							isUnicode = false;
							break ;
						}
					}
					
					// 如果"\\u"之后的四个字符可以表示为十六进制的数字, 则将其解码, 并更新i, continue
					if(isUnicode) {
						char decoded = Character.valueOf((char) Integer.valueOf(str.substring(i+2, i+6), 16).intValue());
						sb.append(decoded);
						i += 5;
						continue ;
					}
				}
			} 
			
			sb.append(ch);
		}
		
		return sb.toString();
	}
	// 判断给定的字符是否可表示十六进制[0-9, a-f, A-F]
	public static boolean isHexChar(char ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
	}
	
	// 从指定的url上面下载图片  保存到指定的路径下面 [也适用于下载其他的二进制数据]
	public static void downloadFrom(String urlStr, String path, long logFlags) throws IOException {
		Tools.assert0(urlStr != null, "'urlStr' can't be null ");
		Tools.assert0(path != null, "'path' can't be null ");
		
		URL url = new URL(urlStr);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(new File(path));
		copy(is,  os);
		
		if(isLog(logFlags, LOG_ON_DOWNLOAD) ) {
			Log.log("download file \"" + path + "\" succcess ...");
		}
	}
	public static void downloadFrom(String urlStr, String path) throws IOException {
		downloadFrom(urlStr, path, LOG_ON_MINE_CONF);
	}
	
	// 将输入流中的数据 复制到输出流
	public static void copy(InputStream is, OutputStream os, boolean isCloseStream) {
		Tools.assert0(is != null, "'inputStream' can't be null ");
		Tools.assert0(os != null, "'outputStream' can't be null ");
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			int len = 0;
			byte[] buf = new byte[BUFF_SIZE_ON_TRANS_STREAM];
			while((len = bis.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(isCloseStream) {
				if(bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void copy(InputStream is, OutputStream os) {
		copy(is, os, true);
	}
	
	// 获取指定路径的文件的文件, 通过sep分割的文件名     获取文件名
	// 解析? 的位置, 是为了防止一下情况
	public static String getFileName(String path, char sep) {
		Tools.assert0(path != null, "'path' can't be null ");
		int start = path.lastIndexOf(sep) + 1;
		
//		http://webd.home.news.cn/1.gif?z=1&_wdxid=01002005057000300000000001110
		int end = getSymAfterFileName(path, start+1);
		if(end != -1) {
			return path.substring(start, end);
		} else {
			return path.substring(start);
		}
	}
	// 获取文件名后面的可能出现的符合的最近的索引
	private static int getSymAfterFileName(String path, int start) {
		int min = -1;
		for(int i=start; i<path.length(); i++) {
			if(mayBeFileNameSeps.contains(path.charAt(i)) ) {
				min = i;
				break ;
			}
		}
		
		return min;
	}
	
	// ------------ 线程池相关 --------------------
	// awaitTermination 线程池
//	public static void awaitTermination(long timout, TimeUnit unit) {
//		try {
//			threadPool.awaitTermination(timout, unit);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	// 添加一个任务
	public static void addTask(Runnable run) {
		threadPool.execute(run);
	}
    // 新建一个线程池
    public static ThreadPoolExecutor newFixedThreadPool(int nThread) {
    	Tools.assert0(nThread > 0, "nThread must > 0 ");
    	return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThread);
    }
    
	// shutdown 线程池
	public static void awaitShutdown(ThreadPoolExecutor threadPool, int checkInterval, long logFlags) {
		awaitTasksEnd(threadPool, checkInterval, true, logFlags);
	}
	public static void awaitShutdown(ThreadPoolExecutor threadPool, int checkInterval) {
		awaitTasksEnd(threadPool, checkInterval, true, LOG_ON_MINE_CONF);
	}
	public static void awaitShutdown() {
		awaitShutdown(threadPool, CHECK_INTERVAL, LOG_ON_MINE_CONF);
	}
	
    // 等待 线程池中任务结束 [并不关闭线程池]
    public static void awaitTasksEnd(ThreadPoolExecutor threadPool, int checkInterval, long logFlags) {
    	awaitTasksEnd(threadPool, checkInterval, false, logFlags);
    }
    public static void awaitTasksEnd(ThreadPoolExecutor threadPool, int checkInterval) {
    	awaitTasksEnd(threadPool, checkInterval, false, LOG_ON_MINE_CONF);
    }
    public static void awaitTasksEnd() {
    	awaitTasksEnd(threadPool, CHECK_INTERVAL, false, LOG_ON_MINE_CONF);
    }
    public static void awaitTasksEnd(ThreadPoolExecutor threadPool, int checkInterval, boolean isShutdown, long logFlags) {
    	Tools.assert0(threadPool != null, "'threadPool' can't be null ");
    	Tools.assert0(checkInterval > 0, "'checkInterval' must > 0 ");
    	
        while (! threadPool.isShutdown() ) {
        	int taskInQueue = threadPool.getQueue().size();
        	int activeTaskCount = threadPool.getActiveCount();
            if((taskInQueue == 0) && (activeTaskCount == 0) ) {
            	if(isShutdown) {
            		if(isLog(logFlags, LOG_ON_AWAIT_TASK_END) ) {
            			Log.log("threadPool is shuttingDown !");
            		}
            		threadPool.shutdown();
            	}
                break ;
            } else {
            	if(isLog(logFlags, LOG_ON_AWAIT_TASK_END) ) {
            		Log.log("task in queue : " + taskInQueue + ", active task count : " + activeTaskCount + ", at : " + new Date().toString() + " !");
            	}
                Tools.sleep(checkInterval);
            }
        }
    }
    public static void awaitTasksEnd(ThreadPoolExecutor threadPool, int checkInterval, boolean isShutdown) {
    	awaitTasksEnd(threadPool, checkInterval, isShutdown, LOG_ON_MINE_CONF);
    }
    // 判断给定的线程池是否还有任务在运行
    public static boolean isThreadPoolRunning(ThreadPoolExecutor threadPool) {
    	Tools.assert0(threadPool != null, "'threadPool' can't be null ");
    	int taskInQueue = threadPool.getQueue().size();
    	int activeTaskCount = threadPool.getActiveCount();
    	return ((taskInQueue != 0) || (activeTaskCount != 0) );
    }

	// 让当前线程休息ms
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// ------------ 日志相关 --------------------
	// 相关的logPattern
	public static LogPatternChain taskBeforeLogPatternChain = Constants.taskBeforeLogPatternChain;
	public static LogPatternChain taskAfterLogPatternChain = Constants.taskAfterLogPatternChain;
	public static LogPatternChain taskExceptionLogPatternChain = Constants.taskExceptionLogPatternChain;
	// 打印任务的日志信息
	public static void logBeforeTask(ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask, boolean debugEnable) {
		Tools.assert0(singleUrlTask != null, "'singleUrlTask' can't be null ");
		if(debugEnable ) {
			String info = Constants.formatLogInfo(taskBeforeLogPatternChain, new JSONObject()
			.element(LogPatternType.URL.typeKey(), singleUrlTask.getUrl()).element(LogPatternType.TASK_NAME.typeKey(), Tools.getTaskName(singleUrlTask))
			.element(LogPatternType.MODE.typeKey(), Constants.LOG_MODES[Constants.OUT_IDX])
			);
			Log.log(info );
		}
	}
	public static void logBeforeTask(ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask, String debugEnable) {
		logBeforeTask(singleUrlTask, Boolean.parseBoolean(debugEnable) );
	}
	public static void logAfterTask(ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask, String fetchedResult, String spent, boolean debugEnable) {
		Tools.assert0(singleUrlTask != null, "'singleUrlTask' can't be null ");
		if(debugEnable ) {
			String info = Constants.formatLogInfo(taskAfterLogPatternChain, new JSONObject()
							.element(LogPatternType.RESULT.typeKey(), fetchedResult).element(LogPatternType.TASK_NAME.typeKey(), Tools.getTaskName(singleUrlTask))
							.element(LogPatternType.SPENT.typeKey(), spent).element(LogPatternType.MODE.typeKey(), Constants.LOG_MODES[Constants.OUT_IDX])
							);
		    Log.log(info );
		}
	}
	public static void logAfterTask(ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask, String fetchedResult, String spent, String debugEnable) {
		logAfterTask(singleUrlTask, fetchedResult, spent, Boolean.parseBoolean(debugEnable) );
	}
	public static void logErrorMsg(ScriptParameter<?, ?, ?, ?, ?, ?> singleUrlTask, Exception e) {
		Tools.assert0(singleUrlTask != null, "'singleUrlTask' can't be null ");
		String info = Constants.formatLogInfo(taskExceptionLogPatternChain, new JSONObject()
						.element(LogPatternType.EXCEPTION.typeKey(), e.getClass().getName() + " : " + e.getMessage() )
						.element(LogPatternType.TASK_NAME.typeKey(), Tools.getTaskName(singleUrlTask))
						.element(LogPatternType.URL.typeKey(), singleUrlTask.getUrl()).element(LogPatternType.MODE.typeKey(), Constants.LOG_MODES[Constants.ERR_IDX])
						);
		Log.err(info );
	}
	
	// 获取现在的毫秒数, 以及根据start获取开销的时间
	public static long now() {
		return System.currentTimeMillis();
	}
	public static String nowStr() {
		return String.valueOf(now() );
	}
	public static long spent(long start) {
		return now() - start;
	}
	public static String spentStr(long start) {
		return String.valueOf(spent(start) );
	}
	
	// ------------ 进制转换相关 --------------------
    // 根据长度, 获取长度的字符串表示
	public static String getLengthString(long length, String dimen) {
		if(equalsIgnoreCase(Tools.BYTE, dimen)) {
		  return length + " " + Tools.BYTE;
		} else if(equalsIgnoreCase(Tools.KB, dimen) ) {
			return Tools.getKBytesByBytes(length) + " " + Tools.KB;
		} else if(equalsIgnoreCase(Tools.MB, dimen) ) {
			return Tools.getMBytesByBytes(length) + " " + Tools.MB;
		} else if(equalsIgnoreCase(Tools.GB, dimen) ) {
			return Tools.getGBytesByBytes(length) + " " + Tools.GB;
		} else if(equalsIgnoreCase(Tools.TB, dimen) ) {
			return Tools.getTBytesByBytes(length) + " " + Tools.TB;
		} else if(equalsIgnoreCase(Tools.PB, dimen) ) {
			return Tools.getPBytesByBytes(length) + " " + Tools.PB;
		} else if(equalsIgnoreCase(Tools.EB, dimen) ) {
			return Tools.getEBytesByBytes(length) + " " + Tools.EB;
		} else if(equalsIgnoreCase(Tools.ZB, dimen) ) {
			return Tools.getZBytesByBytes(length) + " " + Tools.ZB;
		} else if(equalsIgnoreCase(Tools.YB, dimen) ) {
			return Tools.getYBytesByBytes(length) + " " + Tools.YB;
		} else {
			return length + " " + Tools.BYTE;
		}
	}
	
	// 根据字节数, 获取千字节数, 兆字节数, 吉字节数, 踢字节数
	public static long getKBytesByBytes(long bytes) {
		return bytes >> 10;
	}
	public static long getMBytesByBytes(long bytes) {
		return bytes >> 20;
	}
	public static long getGBytesByBytes(long bytes) {
		return bytes >> 30;
	}
	public static long getTBytesByBytes(long bytes) {
		return bytes >> 40;
	}
	public static long getPBytesByBytes(long bytes) {
		return bytes >> 50;
	}
	public static long getEBytesByBytes(long bytes) {
		return bytes >> 60;
	}
	public static long getZBytesByBytes(long bytes) {
		return bytes >> 70;
	}
	public static long getYBytesByBytes(long bytes) {
		return bytes >> 80;
	}
	
	// ------------ 缓冲相关 ------- 2016.03.16 -------------
	// 存放缓冲信息
	static class BuffInfo {
		// 输出路径, 刷出数据的阈值, 缓冲大小, StringBuffer
		public String outputPath;
		public String charset;
		public int threshold;
		public int buffSize;
		public StringBuffer sb;
		
		// 初始化
		public BuffInfo(String outputPath, String charset, int threshold, BuffSizeEstimator buffSizeEstimator) {
			this.outputPath = outputPath;
			this.charset = charset;
			this.threshold = threshold;
			this.buffSize = buffSizeEstimator.getBuffSize(threshold);
			this.sb = new StringBuffer(buffSize);
		}
	}
	// 根据buff阈值获取buffSize的接口
	static interface BuffSizeEstimator {
		public int getBuffSize(int threshold);
	}
	
	// 存放各个buffer, 以及buffer的默认刷出阈值大小
	// 默认的BuffSizeEstimator
	private static Map<String, BuffInfo> bufferToBuffInfo = new HashMap<>(); 
	public static int defaultBuffThreshold = 128 << 10;
	public static BuffSizeEstimator defaultBuffSizeEstimator = new BuffSizeEstimator() {
		public int getBuffSize(int threshold) {
			return threshold + (threshold >> 3);
		}
	};
	
	// 获取所有的缓冲区的key的集合
	public static Set<String> buffNames() {
		return new HashSet<>(bufferToBuffInfo.keySet() );
	}
	// 创建一个缓冲区
	public static void createAnBuffer(String bufName, String outputPath, String charset, BuffSizeEstimator buffSizeEstimator, int threshold) {
		Tools.assert0(bufName != null, "'bufName' can't be null ");
		Tools.assert0(outputPath != null, "'outputPath' can't be null ");
		Tools.assert0(charset != null, "'charset' can't be null ");
		Tools.assert0(buffSizeEstimator != null, "'buffSizeEstimator' can't be null ");
		Tools.assert0(threshold > 0, "'threshold' must > 0 ");
		
		if(bufExists(bufName) ) {
			throw new RuntimeException("the buffInfo with key : " + bufName + " is already exists !");
		}
		
		BuffInfo buffInfo = new BuffInfo(outputPath, charset, threshold, buffSizeEstimator);
		bufferToBuffInfo.put(bufName, buffInfo);
	}
	public static void createAnBuffer(String bufName, String outputPath, String charset) {
		createAnBuffer(bufName, outputPath, charset, defaultBuffSizeEstimator, defaultBuffThreshold);
	}
	public static void createAnBuffer(String bufName, String outputPath) {
		createAnBuffer(bufName, outputPath, DEFAULT_CHARSET);
	}
	public static void createAnBufferIfNotExists(String bufName, String outputPath, String charset, BuffSizeEstimator buffSizeEstimator, int threshold) {
		if(! bufExists(bufName) ) {
			BuffInfo buffInfo = new BuffInfo(outputPath, charset, threshold, buffSizeEstimator);
			bufferToBuffInfo.put(bufName, buffInfo);
		}
	}
	public static void createAnBufferIfNotExists(String bufName, String outputPath, String charset) {
		createAnBufferIfNotExists(bufName, outputPath, charset, defaultBuffSizeEstimator, defaultBuffThreshold);
	}
	public static void createAnBufferIfNotExists(String bufName, String outputPath) {
		createAnBufferIfNotExists(bufName, outputPath, DEFAULT_CHARSET);
	}
	public static void closeAnBuffer(String bufName) throws IOException {
		flushBuffer(bufName, true);
	}
	public static void closeAllBuffer() throws IOException {
		for(String bufName : buffNames() ) {
			closeAnBuffer(bufName);
		}
	}
	// 判断给定的bufName的buffer是否存在
	public static boolean bufExists(String buffName) {
		return getBuffInfo(buffName) != null;
	}
	public static BuffInfo getBuffInfo(String buffName) {
		return bufferToBuffInfo.get(buffName);
	}
	
	// 向给定的缓冲区中添加数据 并检测buffer中的数据是否超过了阈值
	public static void appendBuffer(String bufName, String content, long logFlags) throws IOException {
		Tools.assert0(bufName != null, "'bufName' can't be null ");
		if(! bufExists(bufName)) {
			throw new RuntimeException("have no buffInfo with key : " + bufName + ", please createAnBuffer first !");
		}
		
		BuffInfo buffInfo = bufferToBuffInfo.get(bufName);
		buffInfo.sb.append(content);
		if(buffInfo.sb.length() > buffInfo.threshold) {
			synchronized(buffInfo.sb) {
				if(buffInfo.sb.length() > buffInfo.threshold) {
					flushBuffer(buffInfo.sb, buffInfo.outputPath, buffInfo.charset, logFlags);
				}
			}
		}
	}
	public static void appendBuffer(String bufName, String content) throws IOException {
		appendBuffer(bufName, content, LOG_ON_MINE_CONF);
	}
	public static void appendBufferCRLF(String bufName, String content, long logFlags) throws IOException {
		appendBuffer(bufName, content + CRLF);
	}
	public static void appendBufferCRLF(String bufName, String content) throws IOException {
		appendBufferCRLF(bufName, content, LOG_ON_MINE_CONF);
	}
	
	// 刷出缓存的数据
	public static void flushBuffer(String bufName, boolean isLastBatch, long logFlags) throws IOException {
		Tools.assert0(bufName != null, "'bufName' can't be null ");
		if(! bufExists(bufName)) {
			throw new RuntimeException("have no buffInfo with key : " + bufName + ", please createAnBuffer first !");
		}
		
		BuffInfo buffInfo = bufferToBuffInfo.get(bufName);
		if(buffInfo.sb.length() > 0) {
			synchronized (buffInfo.sb) {
				if(buffInfo.sb.length() > 0) {
					// judge if 'buf' exists in case of 'MultiThreadConcurrent'
					if(bufExists(bufName) ) {
						flushBuffer(buffInfo.sb, buffInfo.outputPath, buffInfo.charset, logFlags);
					} else {
						Log.log("the buffer : '" + bufName + "' already be removed !");
					}
				}
			}
		}
		if(isLastBatch) {
			bufferToBuffInfo.remove(bufName);
		}
	}
	public static void flushBuffer(String bufName, long logFlags) throws IOException {
		flushBuffer(bufName, false, logFlags);
	}
	public static void flushBuffer(String bufName, boolean isLastBatch) throws IOException {
		flushBuffer(bufName, isLastBatch, LOG_ON_MINE_CONF);
	}
	public static void flushBuffer(String bufName) throws IOException {
		flushBuffer(bufName, LOG_ON_MINE_CONF);
	}
	// update the step 'flushDataToPath' into 'threadPoolExecutor'		at 2016.04.16
	public static void flushBuffer(final StringBuffer sb, final String path, final String charset, long logFlags) throws IOException {
		Tools.assert0(sb != null, "'sb' can't be null ");
		Tools.assert0(path != null, "'path' can't be null ");
		Tools.assert0(charset != null, "'charset' can't be null ");
		
		// move 'nextThree' a head incase of 'buff.sb.length > buff.threshold', got an circle, but can't clear 'buff.sb'		at 2016.04.23
		long kbLength = getKBytesByBytes(sb.length() );
		String content = sb.toString();
		sb.setLength(0);
		
		if(! threadPool.isShutdown() ) {
			Tools.append(content, path, charset, true);
		} else {
			Tools.append(content, path, charset, false);
		}
		  
		if(isLog(logFlags, LOG_ON_FLUSH_BUFFER) ) {
			Log.log("flush buffer at : " + new Date().toString() + ", size : " + kbLength + " kb" );
		}
	}
	public static void flushBuffer(StringBuffer sb, String path, String charset) throws IOException {
		flushBuffer(sb, path, charset, LOG_ON_MINE_CONF);
	}
	public static void flushBuffer(StringBuffer sb, String path) throws IOException {
		flushBuffer(sb, path, DEFAULT_CHARSET);
	}
	
	// ------------ assert相关 ------- 2016.03.22 -------------
	// 工具方法
	// 确保boo为true, 否则 抛出异常
	public static void assert0(String msg) {
		assert0(false, msg);
	}
	public static void assert0(boolean boo, String msg) {
		if(msg == null) {
			Log.err("'msg' can't be null ");
			return ;
		}
		if(! boo) {
			throw new RuntimeException("assert0Exception : " + msg);
		}
	}
	// add at 2016.05.02
	public static void assert0(Exception e) {
		assert0(false, e);
	}
	public static void assert0(boolean boo, Exception e) {
		Tools.assert0(e != null, "'e' can't be null ");
		if(! boo) {
			throw new RuntimeException(e);
		}
	}
	// 确保val 和expected相同, 否则 抛出异常
	public static void assert0(int val, int expect, String errorMsg) {
		assert0(val, expect, true, errorMsg);
	}
	public static void assert0(int val, int expect, boolean isEquals, String errorMsg) {
		if(isEquals ^ (val == expect)) {
			String symbol = null;
			if(isEquals) {
				symbol = "!=";
			} else {
				symbol = "==";
			}
			assert0("assert0Exception : " + val + " " + symbol + ", expected : " + expect + ", MSG : " + errorMsg);
		}
	}
	public static <T> void assert0(T val, T expect, String errorMsg) {
		assert0(val, expect, true, errorMsg);
	}
	public static <T> void assert0(T val, T expect, boolean isEquals, String errorMsg) {
		if(val == null) {
			if(expect != null) {
				assert0("assert0Exception : " + val + " == null, expected : " + expect + ", MSG : " + errorMsg);
			}
		}
		if(isEquals ^ (val.equals(expect)) ) {
			String symbol = null;
			if(isEquals) {
				symbol = "!=";
			} else {
				symbol = "==";
			}
			assert0("assert0Exception : " + String.valueOf(val) + " " + symbol + " " + String.valueOf(expect) + ", expected : " + String.valueOf(expect) + ", MSG : " + errorMsg );
		}
	}
	
	// ------------ handlerParser相关 ------- 2016.03.23 -------------
	// 处理handlerParse相关的业务
	public final static HandlerParser handlerParser = new StandardHandlerParser();
	
	// 将给定的字符串解析为AttrHandler
	public static OperationAttrHandler handlerParse(String handlerStr, String handlerType, Types lastOperationReturn) {
		Tools.assert0(handlerStr != null, "'handlerStr' can't be null ");
		Tools.assert0(handlerType != null, "'handlerType' can't be null ");
		return handlerParser.handlerParse(handlerStr, handlerType, lastOperationReturn);
	}
	public static OperationAttrHandler handlerParse(String handlerStr, String handlerType) {
		return handlerParse(handlerStr, handlerType, null);
	}
	
	// 合并两个Handler
	public static OperationAttrHandler combineHandler(OperationAttrHandler mainHandler, OperationAttrHandler attachHander) {
		Tools.assert0(mainHandler != null, "'mainHandler' can't be null ");
		Tools.assert0(attachHander != null, "'attachHander' can't be null ");
		
		Tools.assert0(! mainHandler.operationReturn().isFinal, "the first handler's returnType is final, can't concate 'AttrHandler' anymore ! please check it ! ");
		CompositeOperationAttrHandler<OperationAttrHandler> attrHandler = new CompositeOperationAttrHandler<>();
		attrHandler.addHandler(mainHandler);
		attrHandler.addHandler(attachHander);
		return attrHandler;
	}
//	// 合并两个返回值为逻辑值的Handler, isAnd表示使用"&&"连接 还是"||"连接
//	public static AttrHandler combineCuttingOutHandler(OperationAttrHandler mainHandler, OperationAttrHandler attachHander, boolean isAnd) {
//		Tools.assert0(Types.Boolean == mainHandler.operationReturn(), "combineCuttingOutHandler need '(Boolean, Boolean)' as parameter ! please check it ! ");
//		Tools.assert0(Types.Boolean == attachHander.operationReturn(), "combineCuttingOutHandler need '(Boolean, Boolean)' as parameter ! please check it ! ");
//		if(isAnd) {
//			return new CuttingOutAndAttrHandler<AttrHandler>(Arrays.asList(mainHandler, attachHander) );
//		} else {
//			return new CuttingOutOrAttrHandler<AttrHandler>(Arrays.asList(mainHandler, attachHander) );
//		}
//	}
	// 获取给定的attrHander中最后一个有效的AttrHandler[非Composite]
//	public static AttrHandler lastWorkedHandler(AttrHandler attrHandler) {
//		if(attrHandler instanceof CompositeAttrHandler) {
//			CompositeAttrHandler compositeHandler = ((CompositeAttrHandler) attrHandler);
//			return lastWorkedHandler(compositeHandler.handler(compositeHandler.handlers().size() ) );
//		}
//		
//		return attrHandler;
//	}
//	public static OperationAttrHandler lastWorkedHandler(OperationAttrHandler attrHandler) {
//		if(attrHandler instanceof CompositeOperationAttrHandler) {
//			CompositeOperationAttrHandler<?> compositeHandler = ((CompositeOperationAttrHandler<?>) attrHandler);
//			return lastWorkedHandler(compositeHandler.handler(compositeHandler.handlers().size() ) );
//		}
//		
//		return attrHandler;
//	}
//	public static AttrHandler removeIfLastWorkedHandlerIsFilter(OperationAttrHandler attrHandler) {
//		if(attrHandler instanceof CompositeOperationAttrHandler) {
//			CompositeOperationAttrHandler<OperationAttrHandler> compositeHandler = ((CompositeOperationAttrHandler<OperationAttrHandler>) attrHandler);
//			OperationAttrHandler lastAttrHandler = compositeHandler.handler(compositeHandler.handlers().size() - 1);
//			if(lastAttrHandler instanceof CompositeOperationAttrHandler) {
//				return removeIfLastWorkedHandlerIsFilter(lastAttrHandler);
//			}
//			if(Constants.FILTER_OPERATION.equals(lastAttrHandler.operationType()) ) {
//				return compositeHandler.removeHandler(compositeHandler.handlers().size() - 1);
//			}
//		}
//		
//		return null;
//	}
	
	// ------------ 将数据复制到剪切板 ------- 2016.04.07 -------------
	// windows剪切板 和内存交互数据
	public static void copyStringToClipBoard(String str) {
//		Clipboard clipboard = System.getToolkit().getSystemClipboard();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ss = new StringSelection(str);
		clipboard.setContents(ss, null);
	}
	public static void copyImgToClipBoard(RenderedImage img) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //得到系统剪贴板
      ImageTransferable selection = new ImageTransferable(img);  //图像通道
      clipboard.setContents(selection, null);
    }
	public static void copyFilesToClipBoard(List<File> files) {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); 
      FileTransferable selection = new FileTransferable(files );  
      clipboard.setContents(selection, null);
    }	
	public static String getStringFromClipBoard(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.stringFlavor;
		String res = Tools.EMPTY_STR;
		
		if(clipboard.isDataFlavorAvailable(flavor)){//是否符合剪贴板的数据类型
			try {
				res = clipboard.getData(flavor).toString();
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}
	public static RenderedImage getImgFromClipBoard() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		DataFlavor flavor = DataFlavor.imageFlavor;  
		RenderedImage img = null;
		      
		if (clipboard.isDataFlavorAvailable(flavor)) {
		   try {
		  	 img = (RenderedImage) clipboard.getData(flavor);
		   } catch (UnsupportedFlavorException e) {
		  	 e.printStackTrace();
		   } catch (IOException e) {
		      e.printStackTrace();
		   }
		}
	  
		return img;
   }
   public static List<File> getFilesFromClipBoard() {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      DataFlavor flavor = DataFlavor.javaFileListFlavor;  
      List<File> files = null;
      
      if (clipboard.isDataFlavorAvailable(flavor)) {
         try {
        	 files = (List<File>) clipboard.getData(flavor);
         } catch (UnsupportedFlavorException e) {
        	 e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      return files;
   }
	
	// ------------ asList / Set / Map ------- 2016.04.24 -------------
   public static <T> List<T> asList(T... eles) {
	   return new ArrayList0<>(eles);
   }
   public static <T> List<T> asLinkedList(T... eles) {
	   return new LinkedList0<>(eles);
   }
   public static <T> void asList(List<T> ls, T... eles) {
	   for(T ele : eles) {
		   ls.add(ele);
	   }
   }
   public static <T> Set<T> asSet(T... eles) {
	   return new HashSet0<>(eles);
   }
   public static <T> Set<T> asSortedSet(T... eles) {
	   return new TreeSet0<>(eles);
   }
   public static <T> void asSet(Set<T> set, T... eles) {
	   for(T ele : eles) {
		   set.add(ele);
	   }
   }
   public static <K, V> Map<K, V> asMap(K[] keys, V[] vals) {
	   return new HashMap0<>(keys, vals);
   }
   public static <K, V> Map<K, V> asSortedMap(K[] keys, V[] vals) {
	   return new TreeMap0<>(keys, vals);
   }
   public static <K, V> void asMap(Map<K, V> map, K[] keys, V[] vals) {
	   for(int i=0; i<keys.length; i++) {
		   map.put(keys[i], vals[i]);
	   }
   }
   
   // 辅助数据结构
   static class ArrayList0<E> extends ArrayList<E> {
	   public ArrayList0(E[] array) {
           if (array == null)	return ;
            for(E ele : array) {
            	add(ele);
            }
	   }
   }
   static class LinkedList0<E> extends LinkedList<E> {
	   public LinkedList0(E[] array) {
            if (array == null)	return ;
            for(E ele : array) {
            	add(ele);
            }
	   }
   }
   static class HashSet0<E> extends HashSet<E> {
	   public HashSet0(E[] array) {
           if (array == null)	return ;
            for(E ele : array) {
            	add(ele);
            }
	   }
   }
   static class TreeSet0<E> extends TreeSet<E> {
	   public TreeSet0(E[] array) {
           if (array == null)	return ;
            for(E ele : array) {
            	add(ele);
            }
	   }
   }
   static class HashMap0<K, V> extends HashMap<K, V> {
	   public HashMap0(K[] keys, V[] vals) {
            if ((keys == null) || (vals == null) )	return ;
            Tools.assert0(keys.length == vals.length, "keys's length must 'eq' vals's length !");
            for(int i=0; i<keys.length; i++) {
            	put(keys[i], vals[i]);
            }
	   }
   }
   static class TreeMap0<K, V> extends TreeMap<K, V> {
	   public TreeMap0(K[] keys, V[] vals) {
           if ((keys == null) || (vals == null) )	return ;
           Tools.assert0(keys.length == vals.length, "keys's length must 'eq' vals's length !");
           for(int i=0; i<keys.length; i++) {
        	   put(keys[i], vals[i]);
           }
	   }
   }
   
   // add at 2016.05.07
   // 默认的索引
   public static int GET_INFO_FROM_JSON_DEFAULT_IDX = 0;
   // 获取给定的JSONObject的给定的索引的数据
   public static String getString(JSONObject obj, int idx, String[] idxes) {
       return obj.getString(idxes[getIdx(idx, idxes)] );
   }
   public static String optString(JSONObject obj, int idx, String[] idxes) {
       return obj.optString(idxes[getIdx(idx, idxes)] );
   }
   public static String optString(JSONObject obj, int idx, String[] idxes, String defaultValue) {
       return obj.optString(idxes[getIdx(idx, idxes)], defaultValue);
   }
   public static long getLong(JSONObject obj, int idx, String[] idxes) {
       return obj.getLong(idxes[getIdx(idx, idxes)] );
   }
   public static long optLong(JSONObject obj, int idx, String[] idxes) {
       return obj.optLong(idxes[getIdx(idx, idxes)] );
   }
   public static long optLong(JSONObject obj, int idx, String[] idxes, long defaultValue) {
       return obj.optLong(idxes[getIdx(idx, idxes)] , defaultValue);
   }
   public static int getInt(JSONObject obj, int idx, String[] idxes) {
	   return obj.getInt(idxes[getIdx(idx, idxes)] );
   }
   public static int optInt(JSONObject obj, int idx, String[] idxes) {
	   return obj.optInt(idxes[getIdx(idx, idxes)] );
   }
   public static int optInt(JSONObject obj, int idx, String[] idxes, int defaultValue) {
	   return obj.optInt(idxes[getIdx(idx, idxes)] , defaultValue);
   }
   public static JSONObject getJSONObject(JSONObject obj, int idx, String[] idxes) {
       return obj.getJSONObject(idxes[getIdx(idx, idxes)] );
   }
   public static JSONObject optJSONObject(JSONObject obj, int idx, String[] idxes) {
       return obj.optJSONObject(idxes[getIdx(idx, idxes)] );
   }
   public static JSONArray getJSONOArray(JSONObject obj, int idx, String[] idxes) {
	   return obj.getJSONArray(idxes[getIdx(idx, idxes)] );
   }
   public static JSONArray optJSONArray(JSONObject obj, int idx, String[] idxes) {
	   return obj.optJSONArray(idxes[getIdx(idx, idxes)] );
   }
   
   public static String getString(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.getString(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static String optString(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.optString(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static String optString(JSONObject obj, int idx, int defaultIdx, String[] idxes, String defaultValue) {
	   return obj.optString(idxes[getIdx(idx, idxes, defaultIdx)], defaultValue);
   }
   public static long getLong(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.getLong(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static long optLong(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.optLong(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static long optLong(JSONObject obj, int idx, int defaultIdx, String[] idxes, long defaultValue) {
	   return obj.optLong(idxes[getIdx(idx, idxes, defaultIdx)] , defaultValue);
   }
   public static int getInt(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.getInt(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static int optInt(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.optInt(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static int optInt(JSONObject obj, int idx, int defaultIdx, String[] idxes, int defaultValue) {
	   return obj.optInt(idxes[getIdx(idx, idxes, defaultIdx)] , defaultValue);
   }
   public static JSONObject getJSONObject(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.getJSONObject(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static JSONObject optJSONObject(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.optJSONObject(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static JSONArray getJSONOArray(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.getJSONArray(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   public static JSONArray optJSONArray(JSONObject obj, int idx, int defaultIdx, String[] idxes) {
	   return obj.optJSONArray(idxes[getIdx(idx, idxes, defaultIdx)] );
   }
   
   public static int getIdx(int idx, String[] idxes) {
	   return getIdx(idx, idxes.length);
   }
   public static int getIdx(int idx, Collection<String> idxes) {
	   return getIdx(idx, idxes.size());
   }
   public static int getIdx(int idx, int maxSize) {
	   return getIdx(idx, maxSize, GET_INFO_FROM_JSON_DEFAULT_IDX);
   }
   public static int getIdx(int idx, String[] idxes, int defaultIdx) {
	   return getIdx(idx, idxes.length, defaultIdx);
   }
   public static int getIdx(int idx, Collection<String> idxes, int defaultIdx) {
	   return getIdx(idx, idxes.size(), defaultIdx);
   }
   public static int getIdx(int idx, int maxSize, int defaultIdx) {
	   return (idx >= maxSize) ? defaultIdx : idx;
   }
   
   // add at 2016.05.17
   // 查询字符串的分隔符
   static String PARAM_KV_SEP = "=";
   static String PARAM_PARAM_SEP = "&";
   // 增加封装get请求的查询字符串
   public static String encapQueryString(Map<String, String> params) {
	   return encapQueryString0(params, PARAM_KV_SEP, PARAM_PARAM_SEP);
   }
	// cookie相关分隔符
	public static String COOKIE_KV_SEP = "=";
	public static String COOKIE_COOKIE_SEP = ";";
	// 通过cookies获取cookie的字符串表示
	public static String getCookieStr(Map<String, String> cookies) {
		return encapQueryString0(cookies, COOKIE_KV_SEP, COOKIE_COOKIE_SEP);
	}
	// 通过cookie格式的字符串 获取各个cookie [这里 直接使用split, 避免出现错误]
	public static Map<String, String> getCookiesByCookieStr(String cookiesStr) {
		String[] cookies = cookiesStr.split(COOKIE_COOKIE_SEP);
		Map<String, String> res = new HashMap<>(cookies.length );
		for(int i=0; i<cookies.length; i++) {
			String[] kvPair = cookies[i].split(COOKIE_KV_SEP);
			Tools.assert0(kvPair.length > 1, "error cookieString : '" + cookiesStr + "', around : '" + cookies[i] + "'");
			res.put(kvPair[0], kvPair[1] );
		}
		
		return res;
	}
	private static String encapQueryString0(Map<String, String> params, String KVSep, String paramsSep) {
		Tools.assert0(params != null, "'params' can't be null ");
		Tools.assert0(KVSep != null, "'KVSep' can't be null ");
		Tools.assert0(paramsSep != null, "'paramsSep' can't be null ");
		
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry : params.entrySet() ) {
			sb.append(entry.getKey() );	sb.append(KVSep);
			sb.append(entry.getValue());	sb.append(paramsSep);
		}
		if(sb.length() > paramsSep.length() ) {
			sb.delete(sb.length()-paramsSep.length(), sb.length() );
		}
		
		return sb.toString();
	}
	
    // add at 2016.05.18
	public static boolean STD_CASE_TO_UPPERCASE = false;
	// 获取标准的大写 或者小写
	public static String getStdCase(String str) {
		return getStdCase(str, STD_CASE_TO_UPPERCASE);
	}
	public static String getStdCase(String str, boolean isUpperCase) {
		Tools.assert0(str != null, "'str' can't be null ");
		if(isUpperCase) {
			return str.toUpperCase();
		} else {
			return str.toLowerCase();
		}
	}
	// 判断str01 和str02是否相同[忽略大小写]
	public static boolean equalsIgnoreCase(String str01, String str02) {
		return getStdCase(str01).equals(getStdCase(str02) );
	}
	// 如果给定的字符串的首字母是大写的话, 将其转换为小写
	public static String lowerCaseFirstChar(String str) {
		Tools.assert0(((str != null) || (str.length() == 0) ), "'str' is null ");
		if(str.length() == 1) {
			return str.toLowerCase();
		}
		if(Character.isUpperCase(str.charAt(0)) ) {
			return Character.toLowerCase(str.charAt(0)) + str.substring(1);
		}
		
		return str;
	}
	public static String upperCaseFirstChar(String str) {
		Tools.assert0(((str != null) || (str.length() == 0) ), "'str' is null ");
		if(str.length() == 1) {
			return str.toUpperCase();
		}
		if(Character.isLowerCase(str.charAt(0)) ) {
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		}
		
		return str;
	}
	// 获取给定的异常的信息
	public static String errorMsg(Exception e) {
		Tools.assert0(e != null, "'e' can't be null ");
		return e.getClass().getName() + " -> " + e.getMessage();
	}
	
   // ------------ 待续 --------------------

	
}
