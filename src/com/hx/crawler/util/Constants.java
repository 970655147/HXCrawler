/**
 * file name : Constants.java
 * created at : 8:06:27 PM Jul 24, 2015
 * created by 970655147
 */

package com.hx.crawler.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hx.crawler.attrHandler.DoNothingAttrHandler;
import com.hx.crawler.attrHandler.StandardHandlerParser;
import com.hx.crawler.attrHandler.StandardHandlerParser.Types;
import com.hx.crawler.attrHandler.operation.MapOperationAttrHandler;
import com.hx.crawler.attrHandler.operation.interf.OperationAttrHandler;
import com.hx.crawler.test.Test01TestXpathParser;
import com.hx.crawler.util.LogPattern.ConstantsLogPattern;
import com.hx.crawler.util.LogPattern.DateLogPattern;
import com.hx.crawler.util.LogPattern.ExceptionLogPattern;
import com.hx.crawler.util.LogPattern.HandlerLogPattern;
import com.hx.crawler.util.LogPattern.IncIndexLogPattern;
import com.hx.crawler.util.LogPattern.LogPatternChain;
import com.hx.crawler.util.LogPattern.ModeLogPattern;
import com.hx.crawler.util.LogPattern.MsgLogPattern;
import com.hx.crawler.util.LogPattern.OneStringVariableLogPattern;
import com.hx.crawler.util.LogPattern.ResultLogPattern;
import com.hx.crawler.util.LogPattern.SpentLogPattern;
import com.hx.crawler.util.LogPattern.StackTraceLogPattern;
import com.hx.crawler.util.LogPattern.TaskNameLogPattern;
import com.hx.crawler.util.LogPattern.ThreadLogPattern;
import com.hx.crawler.util.LogPattern.UrlLogPattern;
import com.hx.crawler.xpathParser.AttributeHandler;
import com.hx.crawler.xpathParser.ValuesHandler;
import com.hx.crawler.xpathParser.interf.EndPointHandler;

// 常量
public class Constants {
	
	// the key may occur in 'xpathTemplate'
	public final static String NAME = "name";
	public final static String XPATH = "xpath";
	public final static String VALUES = "values";
	public final static String ATTRIBUTE = "attribute";
	public final static String HANDLER = "handler";
	
	// handler actionMode
	// '+' represents 'add' handler based on 'parentNode'
	// '-' represents 'override handler', discard all handler of 'parentNode'
	public final static String HANDLER_ADDED = "+";
	public final static String HANDLER_OVERRIDE = "-";
	
	// the special value may be 'AttributeNode''s value
	// ':idex', repsresents got current 'resultNode' index in 'parentNode'[ValueNode]
	// 'text', respresents got 'selectedNode''s text[not include subTag, and subTag's text]
	// 'innertext', respresents got 'selectedNode''s innerText[not include subTag, include subTag's text]
	// 'innerhtml', respresents got 'selectedNode''s innerHTML[include subTag, include subTag's text]
	// 'outerhtml', based on 'innerhtml' and add 'currentTag' outside
	// 'attrName', represents got 'selectedNode''s 'attr(attrName)'[got defined 'attr']
	public final static String INDEX = ":index";
	public final static String TEXT = "text";
	public final static String INNER_TEXT = "innertext";
	public final static String INNER_HTML = "innerhtml";
	public final static String OUTER_HTML = "outerhtml";
	
	// Constants
	public static final String EMPTY_STR = "";
	public static final String CRLF = "\r\n";
	public final static String NULL = "null";
	public final static String DEFAULT_VALUE = NULL;
	public final static String ATTR_NOT_SUPPORT = "AttributeNotSupported";
	public final static String ARRAY_ATTR = "JSONArrayAttribute";
	
	// Constants
	public final static Character SLASH = '\\';
	public final static Character INV_SLASH = '/';
	public static final Character QUESTION = '?';
	public static final Character DOT = '.';
	public static final Character COMMA = ',';
	public static final Character COLON = ':';	
	public static final Character SPACE = ' ';
	public static final Character TAB = '\t';
	public static final Character CR = '\r';
	public static final Character LF = '\n';
	public static final Character QUOTE = '\"';
	public static final Character SINGLE_QUOTE = '\'';
	
	// 'rootNode' int result of 'XpathIndexString'
	public final static String ROOT = "#root";
	
	// the 'attr' maybe a 'hyperLink'
	public final static Set<String> links = new HashSet<>();
	static {
		links.add("href");
		links.add("src");
	}
	
	// {'attribute' : AttributeHandler, ... }
	public final static Map<String, EndPointHandler> endpointToHandler = new HashMap<>();
	static {
		endpointToHandler.put(ATTRIBUTE, new AttributeHandler() );
		endpointToHandler.put(VALUES, new ValuesHandler() );
		// 不能向下面 这样写, 因为是EndPoint.ATTRIBUTE的初始化导致了Constants.class的加载, 而执行当前staticBlock的时候, Endpoint.ATTRIBUTE 以及EndPoint.VALUES在初始化阶段初始化的null值		--2016.02.02
//		endpointToHandler.put(EndPoint.ATTRIBUTE, new ValuesHandler() );
	}
	
	// Constants related on 'attrHandler'				// add at 2016.03.22
	public final static String HANDLER_PREFIX = "map";
	public final static String RESULT_PROXY = "$this";
	public final static boolean RECOGNIZE_RESULT_PROXY = true;
	public final static String ANONY_OPERAND_NAME = "anonymousOperand";
	public final static String EMPTY_OPERAND_NAME = "emptyOperand";
	public final static int COMPOSITE_HANDLER_DEFAULT_CAP = 2;
	public final static int CONCATE_HANDLER_DEFAULT_CAP = 2;
	public final static int CUTTING_DOOR_HANDLER_DEFAULT_CAP = 2;
	public final static int CALC_HANDLER_DEFAULT_CAP = 2;
	public final static int MULTI_ARG_HANDLER_DEFAULT_CAP = 2;
	public final static int OPERAND_DEFAULT_CAP = 2;
	// some 'sugar' symbol
		// 'abc' + 'def', 2 > 3, true && true, 3>5 ? truePart : falsePart
	public final static String STRING_CONCATE = "+";
	public final static String STRING_NOT = "!";
	public final static String GT = ">";
	public final static String LT = "<";
	public final static String EQ = "=";
	public final static String AND = "&";
	public final static String OR = "|";
	public final static String COND_EXP_COND = "?";
	public final static String COND_EXP_BRANCH = ":";
	
	public final static String PARAM_SEP = ",";
	public final static String SUB_HANDLER_CALL = ".";
//	public final static String QUOTE = "'";
	public final static String QUOTED_STRING = "quotedString";
	public final static String TRUE = Boolean.TRUE.toString();
	public final static String FALSE = Boolean.FALSE.toString();
	public final static String HANDLER_UNDEFINED = "-1";
	public final static int TARGET_UNDEFINED = Integer.parseInt(HANDLER_UNDEFINED);
	
	// each 'functionName'
	public final static String CONCATE = "concate";
	public final static String REPLACE = "replace";
	public final static String TRIM = "trim";
	public final static String TRIM_ALL = "trimAll";
	public final static String TRIM_AS_ONE = "trimAsOne";
	public final static String SUB_STRING = "subString";
	public final static String TO_UPPERCASE = "toUpperCase";
	public final static String TO_LOWERCASE = "toLowerCase";
	public final static String CONSTANTS = "constants";
	public final static String DO_NOTHING = "doNothing";
	
	public final static String INDEX_OF = "indexOf";
	public final static String LAST_INDEX_OF = "lastIndexOf";
	public final static String LENGTH = "length";
	
	public final static String EQUALS = "equals";
	public final static String MATCHES = "matches";
	public final static String CONTAINS = "contains";
	public final static String NOT = "not";
	
	public final static String COMPOSITE = "composite";
	
	public final static String GREATER_THAN = "gt";
	public final static String GREATER_EQUALS_THAN = "get";
	public final static String LESS_THAN = "lt";
	public final static String LESS_EQUALS_THAN = "let";
	public final static String _EQUALS = "eq";
	public final static String NOT_EQUALS = "neq";
	
	public final static String CUTTING_OUT_AND = "and";
	public final static String CUTTING_OUT_OR = "or";
	
	public final static String COND_EXP = "condExp";
	
	public final static String ADD = "add";
	public final static String SUB = "sub";
	public final static String MUL = "mul";
	public final static String DIV = "div";
	public final static String MOD = "mod";
	
	public final static String TO_INT = "toInt";
	public final static String TO_BOOLEAN = "toBool";
	public final static String TO_STRING = "toString";

	// Constants related on 'operation'
	public final static String OPERATION_COMPOSITE = "operationComposite";
	public final static String MAP_OPERATION = "map";
	public final static String FILTER_OPERATION = "filter";
	public final static String ASSERT_OPERATION = "assert";
	
	// 校验不通过即时返回的消息, 
	// default 'OperationHandler'[map(doNothing) ]
	public final static String immediateOperationMsg;
	public final static OperationAttrHandler defaultOperationAttrHandler = new MapOperationAttrHandler(new DoNothingAttrHandler(), Types.String);
	
	// <handlerType -> operation> {'handler' : [map, filter, assert] }
	// the 'Operation' that need immediateReturn
	// the seprator that used while 'WordsSeprate'
	// the symbolPair that need to be escape while 'WordsSeprate', like 'log(abc);', should not be seprate
	// the symbolPair represents every 'bracketPair'['(' & ')', '{' & '}', '[' & ']' ]
	public final static Map<String, List<String>> handlerTypeToHandleOperations = new HashMap<>();
	public final static Set<String> immediateOperation = new HashSet<>();
	public final static Set<String> handlerParserSeps = new HashSet<>();
	public final static Map<String, String> escapeMap = new HashMap<>();
	public final static Map<Character, Character> escapeCharMap = new HashMap<>();
	public final static Map<String, String> bracketPair = new HashMap<>();
	static {
		handlerTypeToHandleOperations.put(HANDLER, Arrays.asList(MAP_OPERATION, FILTER_OPERATION, ASSERT_OPERATION) );
		
		immediateOperation.add(FILTER_OPERATION);
		immediateOperation.add(ASSERT_OPERATION);
		
		immediateOperationMsg = immediateOperation.toString();
		
		handlerParserSeps.add("(");
		handlerParserSeps.add(")");
		handlerParserSeps.add("+");
		handlerParserSeps.add(",");
		handlerParserSeps.add(".");
		handlerParserSeps.add("'");
		handlerParserSeps.add("!");
		handlerParserSeps.add(">");
		handlerParserSeps.add("<");
		handlerParserSeps.add("=");
		handlerParserSeps.add("&");
		handlerParserSeps.add("|");
		handlerParserSeps.add("?");
		handlerParserSeps.add(":");
		
		escapeMap.put("\"", "\"");
		escapeMap.put("'", "'");
		escapeCharMap.put('\'', '\'');
		escapeCharMap.put('"', '"');
		
		bracketPair.put("(", ")");
		bracketPair.put("{", "}");
	}

	// update at 2016.04.21
	// ------------------------ 业务相关常量[从Log, Tools中提取] ---------------------------------
	// 默认的常量配置
		// Log相关
	public final static String DEFAULT_HORIZON_LINES = "-----------------------------------";
	public final static String DEFAULT_HORIZON_STARTS = "***********************************";
	public final static String DEFAULT_GOT_THERE = "get there...";	
	public final static String DEFAULT_GOT_NOTHING = "got nothing ~";	
	
	public final static OutputStream[] defaultOutStreams = new OutputStream[] {
																System.out,
																System.err		
															};
	public final static boolean[] defaultOutToLogFile = new boolean[] {
																false,
																false
															};
	public final static String[] defaultLogBufNames = new String[] {
																"_Log.log",
																"_Log.err"
															};
	public final static String[] defaultLogFiles = new String[] {
																"C:\\Users\\970655147\\Desktop\\tmp\\out.log",
																"C:\\Users\\970655147\\Desktop\\tmp\\out.log"
															};
	public final static OutputStream nullOutputStream = new NullOutputStream();
	public final static String defaultDateFormat = "yyyy-MM-dd hh:mm:ss:SSS";
	public final static String defaultLogPattern = ">>>> [${idx }] [${date }] - [${mode }] => `${msg }`  >>>>";
	public final static String defaultTaskBeforeLogPattern = "URL : '${url }' \r\n --------------------- [ '${taskName }' start ... ] --------------------------";
	public final static String defaultTaskAfterLogPattern = "FetchedResult : '${result }' \r\n --------------------- [ '${taskName }' end ... ] -------------------------- \r\n spent '${spent }' ms ... ";
	public final static String defaultTaskExceptionLogPattern = "Exception : '${exception }' \r\n while fetch : '${taskName }', url : '${url }' ";
	public final static LogPatternChain justPrintMsgLogPattern = new LogPatternChain().addLogPattern(new MsgLogPattern(Constants.DEFAULT_VALUE) );
	
	public final static String _DEFAULT_SEP_WHILE_CRLF = " ";
	public final static String _DEFAULT_SEP_WHILE_NO_CRLF = ", ";
	public final static String _DEFAULT_SEP_WHILE_TWO_DIMEN = " ";
	public final static String _DEFAULT_MAP_KV_SEP = " -> ";
	public final static boolean DEFAULT_OUTPUT_APPEND_CRLF = true;
	public final static boolean DEFAULT_ERRPUT_APPEND_CRLF = true;
	public final static boolean DEFAULT_OUTPUT_APPEND_CRLF_FOR_CONTAINER = false;
	public final static boolean DEFAULT_ERRPUT_APPEND_CRLF_FOR_CONTAINER = false;
	
	// 常量配置[首选配置文件中的配置, 否则为默认配置]
	static String HORIZON_LINES = Constants.DEFAULT_HORIZON_LINES;
	static String HORIZON_STARTS = Constants.DEFAULT_HORIZON_STARTS;
	static String GOT_THERE = Constants.DEFAULT_GOT_THERE;
	static String GOT_NOTHING = Constants.DEFAULT_GOT_NOTHING;
	
	// 增加一个输出模式[fatal] : 1. 增加索引, LOG_MODES_STR, LOG_MODES
	// 						2. 增加outStreams, outToLogFiles, logBuffNames, logFIles
	//						3. 增加Log.fatal()系列方法
	//						4. 更新Log.dispath
	//						5. 测试
	public static final int OUT_IDX = 0;
	public static final int ERR_IDX = OUT_IDX + 1;
	static final JSONArray LOG_MODES_STR = new JSONArray()
											.element("Constants.OUT_IDX").element("Constants.ERR_IDX");
	public final static String[] LOG_MODES = {"LOG", "ERROR" };
	// updated at 2015.05.05
	static OutputStream[] outStreams = Arrays.copyOf(defaultOutStreams, defaultOutStreams.length);
	static boolean[] outToLogFile = Arrays.copyOf(defaultOutToLogFile, defaultOutToLogFile.length);
	static String[] logBufNames = Arrays.copyOf(defaultLogBufNames, defaultLogBufNames.length);
	static String[] logFiles = Arrays.copyOf(defaultLogFiles, defaultLogFiles.length);
	public static DateFormat dateFormat = new SimpleDateFormat(Constants.defaultDateFormat );
	static boolean usePattern = Boolean.parseBoolean(Constants.DEFAULT_USE_PATTERN);
	static String logPattern = Constants.defaultLogPattern;	
	static LogPatternChain logPatternChain = justPrintMsgLogPattern;	
	
	static String DEFAULT_SEP_WHILE_CRLF = Constants._DEFAULT_SEP_WHILE_CRLF;
	static String DEFAULT_SEP_WHILE_NO_CRLF = Constants._DEFAULT_SEP_WHILE_NO_CRLF;
	static String DEFAULT_SEP_WHILE_TWO_DIMEN = Constants._DEFAULT_SEP_WHILE_TWO_DIMEN;
	static String DEFAULT_MAP_KV_SEP = Constants._DEFAULT_MAP_KV_SEP;
	
	static boolean OUTPUT_APPEND_CRLF = Constants.DEFAULT_OUTPUT_APPEND_CRLF;
	static boolean ERRPUT_APPEND_CRLF = Constants.DEFAULT_ERRPUT_APPEND_CRLF;
	static boolean OUTPUT_APPEND_CRLF_FOR_CONTAINER = Constants.DEFAULT_OUTPUT_APPEND_CRLF_FOR_CONTAINER;
	static boolean ERRPUT_APPEND_CRLF_FOR_CONTAINER = Constants.DEFAULT_ERRPUT_APPEND_CRLF_FOR_CONTAINER;
	
	// 日志格式相关
	public final static String VAR_START = "${";
	public final static String VAR_END = "}";
	public final static String LBRACKET = "(";
	public final static String RBRACKET = ")";
	
	// fixed pattern
	// add at 2016.04.21
	public final static String LOG_PATTERN_CHAIN = "logPatternChain";
	public final static String LOG_PATTERN_CONSTANTS = CONSTANTS;
	public final static String LOG_PATTERN_DATE = "date";
	public final static String LOG_PATTERN_IDX = "idx";
	
	// controllable [variable]
	// add at 2016.04.22
	public final static String LOG_PATTERN_MODE = "mode";
	public final static String LOG_PATTERN_MSG = "msg";
	public final static String LOG_PATTERN_HANDLER = HANDLER;
	// add at 2016.04.29
	public final static String LOG_PATTERN_THREAD = "thread";
	public final static String LOG_PATTERN_STACK_TRACE = "stackTrace";
	
	// add at 2016.04.23
	public final static String LOG_PATTERN_TASK_NAME = "taskName";
	public final static String LOG_PATTERN_URL = "url";
	public final static String LOG_PATTERN_RESULT = "result";
	public final static String LOG_PATTERN_SPENT = "spent";
	public final static String LOG_PATTERN_EXCEPTION = "exception";
	
	// default 'VariableValue'[${var }], and supported two 'mode'
	public final static String DEFAULT_VAR_VALUE = "varNotFound";
	
	// Log.formatLogInfo(LogPatternChain, String[]) ->  Log.formatLogInfo(LogPatternChain, Map<String, String>)
//	// not concern 'ConreteValue', just ensure '*_idx' unique
//		// String mode = args[MODEL_IDX]
//	public final static int MODE_IDX = 0;
//	public final static int MSG_IDX = MODE_IDX + 1;
//	public final static int LOG_PATTERN_HANDLER_IDX = MSG_IDX + 1;
//	public final static int TASK_NAME_IDX = LOG_PATTERN_HANDLER_IDX + 1;
//	public final static int URL_IDX = TASK_NAME_IDX + 1;
//	public final static int RESULT_IDX = URL_IDX + 1;
//	public final static int SPENT_IDX = RESULT_IDX + 1;
//	public final static int EXCEPTION_IDX = SPENT_IDX + 1;
//	public final static int MAX_LOG_PATTERN_IDX = EXCEPTION_IDX + 1;
	
	// 'logPattern''s seprators 
	public final static Set<String> logPatternSeps = new HashSet<>();
	static {
		logPatternSeps.add(VAR_START);
		logPatternSeps.add(VAR_END);
		logPatternSeps.add(LBRACKET);
		logPatternSeps.add(RBRACKET);
	}
	
		// Tools相关
	// 默认的相关配置变量
	private final static String DEFAULT_TMP_NAME = "tmp";
	private final static String DEFAULT_TMP_DIR = "C:\\Users\\970655147\\Desktop\\tmp";
	private final static String DEFAULT_WRITE_ASYNC = "false";
	private final static String DEFAULT_USE_PATTERN = "false";
	private final static int DEFAULT_BUFF_SIZE_ON_TRANS_STREAM = 2048;
	private final static String DEFAULT_SUFFIX = ".html";
	// 取得默认编码??
	public final static String _DEFAULT_CHARSET = Charset.defaultCharset().name();
	
	private final static int DEFAULT_CHECK_INTERVAL = 3 * 1000;
	private final static int DEFAULT_N_THREADS = 10;
	
	// 线程池相关
	static int CHECK_INTERVAL = Constants.DEFAULT_CHECK_INTERVAL;
	static int N_THREADS = Constants.DEFAULT_N_THREADS;
	static ThreadPoolExecutor threadPool = null;
	static boolean WRITE_ASYNC = Boolean.parseBoolean(Constants.DEFAULT_WRITE_ASYNC);
	
	// 临时文件相关
	static String TMP_NAME = Constants.DEFAULT_TMP_NAME;
	static String TMP_DIR = Constants.DEFAULT_TMP_DIR;
	static AtomicInteger TMP_IDX = new AtomicInteger();
	static String SUFFIX = Constants.DEFAULT_SUFFIX;
	static String DEFAULT_CHARSET = Constants._DEFAULT_CHARSET;
	static int BUFF_SIZE_ON_TRANS_STREAM = Constants.DEFAULT_BUFF_SIZE_ON_TRANS_STREAM;
	
	static Set<String> emptyStrCondition = new HashSet<>();
	static Set<Character> mayBeFileNameSeps = new HashSet<>();
	static {
		emptyStrCondition.add(EMPTY_STR);
		mayBeFileNameSeps.add(QUESTION);
	}
	
	// 打印任务日志相关
	static String taskBeforeLogPattern = Constants.defaultTaskBeforeLogPattern;
	static String taskAfterLogPattern = Constants.defaultTaskAfterLogPattern;
	static String taskExceptionLogPattern = Constants.defaultTaskExceptionLogPattern;
	static LogPatternChain taskBeforeLogPatternChain = null;
	static LogPatternChain taskAfterLogPatternChain = null;
	static LogPatternChain taskExceptionLogPatternChain = null;
	
    // ------------ 初始化了所有的静态成员, 在进行更新配置 --------------------
	// 初始化相关配置
	static {
		boolean isException = false;
		Properties props = new Properties();
		try {
//			InputStream config = new FileInputStream(new File("./src/config.conf") );
			// 前者为true, 后者为false
//			Log.log(Main.class.getClass().getClassLoader() == null);
//			Log.log(new Main().getClass().getClassLoader() == null);
			InputStream config = new Test01TestXpathParser().getClass().getClassLoader().getResourceAsStream("config.conf");
			props.load(new InputStreamReader(config, DEFAULT_CHARSET) );
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			props = null;
			isException = true;
			System.err.println("config file is not exist ...");
		} catch (IOException e) {
//			e.printStackTrace();
			props = null;
			isException = true;
			System.err.println("IO Exception ...");
		} catch (NullPointerException e) {
//			e.printStackTrace();
			props = null;
			isException = true;
			System.err.println("config file is not exist ...");
		}
		
		if(! isException) {
			initToolsByConfigFile(props);
			initLogByConfigFile(props);
		}
		JSONObject newProps = JSONObject.fromObject(props);
		taskBeforeLogPatternChain = initLogPattern(taskBeforeLogPattern, newProps);
		taskAfterLogPatternChain = initLogPattern(taskAfterLogPattern, newProps);
		taskExceptionLogPatternChain = initLogPattern(taskExceptionLogPattern, newProps);
		if(usePattern) {
			logPatternChain = initLogPattern(logPattern, newProps);
		}
		
		props = null;
	}
	// 使用配置文件进行初始化	
		// 静态块, 初始化 可配置的数据
		// tmpName, tmpDir, buffSize, suffix, checkInterval, nThreads
		// emptyCondition, mayBeFileNameSeps
	private static void initToolsByConfigFile(Properties props) {
		TMP_NAME = props.getProperty("tmpName", DEFAULT_TMP_NAME);
		TMP_DIR = props.getProperty("tmpDir", DEFAULT_TMP_DIR);
		BUFF_SIZE_ON_TRANS_STREAM = Integer.parseInt(props.getProperty("buffSizeOnTransStream", String.valueOf(BUFF_SIZE_ON_TRANS_STREAM)) );
		SUFFIX = props.getProperty("suffix", DEFAULT_SUFFIX);
		DEFAULT_CHARSET = props.getProperty("defaultCharSet", _DEFAULT_CHARSET);
		// check for the 'charset'
			Charset.forName(DEFAULT_CHARSET);
		WRITE_ASYNC = Boolean.parseBoolean(props.getProperty("writeAsync", DEFAULT_WRITE_ASYNC) );
		CHECK_INTERVAL = Integer.parseInt(props.getProperty("checkInterval", String.valueOf(DEFAULT_CHECK_INTERVAL)) );
		N_THREADS = Integer.parseInt(props.getProperty("nThreads", String.valueOf(DEFAULT_N_THREADS)) );
		
		String[] emptyConds = props.getProperty("emptyStrCondition", Constants.EMPTY_STR).split(";");
		for(String emptyCon : emptyConds) {
			emptyStrCondition.add(emptyCon.trim() );
		}
		String[] fileNameSeps = props.getProperty("mayBeFileNameSeps", Constants.EMPTY_STR).split(";");
		for(String sep : fileNameSeps) {
			if(! isEmpty0(sep)) {
				mayBeFileNameSeps.add(sep.charAt(0) );
			}
		}
		
		taskBeforeLogPattern = props.getProperty("taskBeforeLogPattern", taskBeforeLogPattern);
		taskAfterLogPattern = props.getProperty("taskAfterLogPattern", taskAfterLogPattern);
		taskExceptionLogPattern = props.getProperty("taskExceptionLogPattern", taskExceptionLogPattern);
	}
	static boolean isEmpty0(String str) {
		return (str == null) || emptyStrCondition.contains(str.trim());
	}
	
	// 使用properties初始化log相关的常量配置
		// 使用配置文件进行初始化		add at 2016.04.15
	private static void initLogByConfigFile(Properties props) {
		if(props != null) {
			HORIZON_LINES = props.getProperty("horizonLines", DEFAULT_HORIZON_LINES);
			HORIZON_STARTS = props.getProperty("horizonStars", DEFAULT_HORIZON_STARTS);
			GOT_THERE = props.getProperty("gotThere", DEFAULT_GOT_THERE);
			GOT_NOTHING = props.getProperty("gotNothing", DEFAULT_GOT_NOTHING);
			
			String outToConsole = props.getProperty("outToConsole", Constants.TRUE);
			if(! outToConsole.equals(Constants.TRUE) ) {
				outStreams[OUT_IDX] = null;
			}
			String errToConsole = props.getProperty("errToConsole", Constants.TRUE);
			if(! errToConsole.equals(Constants.TRUE) ) {
				outStreams[ERR_IDX] = null;
			}
			outToLogFile[OUT_IDX] = Boolean.parseBoolean(props.getProperty("outToLogFile", String.valueOf(outToLogFile[OUT_IDX])) );
			outToLogFile[ERR_IDX] = Boolean.parseBoolean(props.getProperty("errToLogFile", String.valueOf(outToLogFile[ERR_IDX])) );
			logFiles[OUT_IDX] = props.getProperty("outLogFilePath", logFiles[OUT_IDX]); 
			logFiles[ERR_IDX] = props.getProperty("errLogFilePath", logFiles[ERR_IDX]); 
			dateFormat = new SimpleDateFormat(props.getProperty("dateFormat", Constants.defaultDateFormat) );
			usePattern = Boolean.parseBoolean(props.getProperty("usePattern", Constants.TRUE) );
			logPattern = props.getProperty("logPattern", logPattern);
			
			DEFAULT_SEP_WHILE_CRLF = props.getProperty("defaultSepWhileCRLF", _DEFAULT_SEP_WHILE_CRLF);
			DEFAULT_SEP_WHILE_NO_CRLF = props.getProperty("defaultSepWhileNotCRLF", _DEFAULT_SEP_WHILE_NO_CRLF);
			DEFAULT_SEP_WHILE_TWO_DIMEN = props.getProperty("defaultSepWhileTwoDimen", _DEFAULT_SEP_WHILE_TWO_DIMEN);
			DEFAULT_MAP_KV_SEP = props.getProperty("defaultMapKVSep", _DEFAULT_MAP_KV_SEP);
			
			OUTPUT_APPEND_CRLF = props.getProperty("defaultOutputAppendCrlf", Constants.TRUE).equals(Constants.TRUE);
			ERRPUT_APPEND_CRLF = props.getProperty("defaultErrputAppendCrlf", Constants.TRUE).equals(Constants.TRUE);
			OUTPUT_APPEND_CRLF_FOR_CONTAINER = props.getProperty("defaultOutputAppendCrlfForContainer", Constants.FALSE).equals(Constants.TRUE);
			ERRPUT_APPEND_CRLF_FOR_CONTAINER = props.getProperty("defaultErrputAppendCrlfForContainer", Constants.FALSE).equals(Constants.TRUE);
		}
	}	
	
	// ------------ 格式化日期相关 ------- 2016.04.21 -------------
	// 根据给定的logPattern获取打印日志所需的LogPatternChain
	public static LogPatternChain initLogPattern(String logPattern, Map<String, String> props) {
		LogPatternChain logPatternChain = new LogPatternChain();
		WordsSeprator sep = new WordsSeprator(logPattern, Constants.logPatternSeps, null, true);
		while(sep.hasNext() ) {
			String next = sep.next();
			switch (next) {
				case Constants.VAR_START:
					Tools.assert0(sep.hasNext(), "unExpected end of 'logPattern'! ");
					String varName = sep.next().trim();
					switch (varName) {
						case Constants.LOG_PATTERN_DATE:
							logPatternChain.addLogPattern(new DateLogPattern(Constants.dateFormat) );
							break;
						case Constants.LOG_PATTERN_MODE:
							logPatternChain.addLogPattern(new ModeLogPattern(Constants.LOG_MODES[Constants.OUT_IDX]) );	
							break;
						case Constants.LOG_PATTERN_MSG:
							logPatternChain.addLogPattern(new MsgLogPattern(Constants.DEFAULT_VAR_VALUE) );	
							break;
						case Constants.LOG_PATTERN_IDX:
							// ${idx }
							if(! LBRACKET.equals(sep.seek()) ) {
								logPatternChain.addLogPattern(new IncIndexLogPattern(0, 1) );
								break ;
							}
							// ${idx() }
							sep.next();
							if(RBRACKET.equals(sep.seek()) ) {
								logPatternChain.addLogPattern(new IncIndexLogPattern(0, 1) );
								sep.next();
								break ;
							}
							// ${idx(2) } or $idx(2, 4)
							String initValOrAndInc = sep.next();
							int commaIdx = initValOrAndInc.indexOf(",");
							int inc = 1;
							int initVal = 0;
							// 'Integer.parseInt' may got 'NumberFormatException'
							if(commaIdx >= 0) {
								inc = Integer.parseInt(initValOrAndInc.substring(commaIdx + 1).trim() );
								initVal = Integer.parseInt(initValOrAndInc.substring(0, commaIdx).trim() );
							} else {
								initVal = Integer.parseInt(initValOrAndInc.trim() );
							}
							logPatternChain.addLogPattern(new IncIndexLogPattern(initVal, inc) );
							Tools.assert0(RBRACKET.equals(sep.next()), "expect a ')', but got an : '" + sep.seekLastNext() + "' !" );
							break;
						case LOG_PATTERN_HANDLER :
							Tools.assert0(LBRACKET.equals(sep.next()), "expect a '(', but go an : '" + sep.seekLastNext() + "' !");
							int stackCnt = 1;
							StringBuilder sb = new StringBuilder(sep.length() - sep.lastNextPos() );
							while(sep.hasNext() ) {
								String partHandlerStr = sep.next();
								if(LBRACKET.equals(partHandlerStr) ) {
									stackCnt ++;
								}
								if(RBRACKET.equals(partHandlerStr) ) {
									stackCnt --;
								}
								if(stackCnt == 0) {
									break ;
								}
								sb.append(partHandlerStr);
							}
							Tools.assert0(RBRACKET.equals(sep.seekLastNext()), "expect 'handler()' endsWith ')', but got an : '" + sep.seekLastNext() + "' !");
							String handlerStr = sb.toString();
							OperationAttrHandler operationHandler = new StandardHandlerParser().handlerParse(handlerStr, Constants.HANDLER);
							logPatternChain.addLogPattern(new HandlerLogPattern(operationHandler, Constants.DEFAULT_VAR_VALUE) );
							break ;
						case Constants.LOG_PATTERN_THREAD:
							logPatternChain.addLogPattern(new ThreadLogPattern() );
							break;
						case Constants.LOG_PATTERN_STACK_TRACE:
							logPatternChain.addLogPattern(new StackTraceLogPattern() );
							break;
						case Constants.LOG_PATTERN_TASK_NAME:
							logPatternChain.addLogPattern(new TaskNameLogPattern(Constants.DEFAULT_VAR_VALUE) );	
							break;
						case Constants.LOG_PATTERN_URL:
							logPatternChain.addLogPattern(new UrlLogPattern(Constants.DEFAULT_VAR_VALUE) );	
							break;
						case Constants.LOG_PATTERN_RESULT:
							logPatternChain.addLogPattern(new ResultLogPattern(Constants.DEFAULT_VAR_VALUE) );	
							break;
						case Constants.LOG_PATTERN_SPENT:
							logPatternChain.addLogPattern(new SpentLogPattern(Constants.DEFAULT_VAR_VALUE) );	
							break;
						case Constants.LOG_PATTERN_EXCEPTION:
							logPatternChain.addLogPattern(new ExceptionLogPattern(Constants.DEFAULT_VAR_VALUE) );	
							break;										
						default:
							String constantsValue = (props == null) ? DEFAULT_VAR_VALUE : (props.get(varName) != null) ? props.get(varName) : DEFAULT_VAR_VALUE;
							logPatternChain.addLogPattern(new ConstantsLogPattern(constantsValue) );
							break;
					}
					Tools.assert0(Constants.VAR_END.equals(sep.next() ), "expect an '" + Constants.VAR_END + "', but got an '" + sep.seekLastNext() + "' ! ");
					break;
				default:
					logPatternChain.addLogPattern(new ConstantsLogPattern(next) );
					break;
			}
		}
		
		return logPatternChain;
	}
	// 格式化日期相关
	public static String formatLogInfo(LogPatternChain logPatternChain, JSONObject argsMap) {
		if(logPatternChain == null) {
			return argsMap.optString(Constants.LOG_PATTERN_MSG );
		}
		
		logPatternChain.setResult(null );
		for(LogPattern logPattern : logPatternChain.getChain() ) {
			switch (logPattern.type() ) {
				// use 'Mode' instedof 'LogPatternType.Mode'
				// from : http://caohongxing7604.blog.163.com/blog/static/32016974200991412040387/
				case MODE:
				case MSG:
				case HANDLER :
				case TASK_NAME :
				case URL :
				case RESULT :
				case SPENT :
				case EXCEPTION :
					((OneStringVariableLogPattern) logPattern).setArg(argsMap.optString(logPattern.type().typeKey(), Constants.DEFAULT_VAR_VALUE) );
					break ;
				case PATTERN_CHAIN :
					LogPatternChain subLogPatternChain = (LogPatternChain) logPattern;
					subLogPatternChain.setResult(formatLogInfo(subLogPatternChain, argsMap) );
					break ;
				default:
					break;
			}
		}
		
		return logPatternChain.pattern();
	}
	public static String formatLogInfo(LogPatternChain logPatternChain, AbstractMap<String, String> argsMap) {
		return formatLogInfo(logPatternChain, JSONObject.fromObject(argsMap) );
	}
	
	
}
