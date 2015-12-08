/**
 * file name : Tools.java
 * created at : 6:58:34 PM Jul 25, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_xpathParser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.xml.sax.InputSource;

import com.hx.crawlerTools_crawler.Crawler;
import com.hx.crawlerTools_crawler.HtmlCrawler;
import com.hx.crawlerTools_crawler.ScriptParameter;
import com.hx.crawlerTools_crawler.SingleUrlTask;

// 工具类
public class Tools {
	
	// 常量
	public static String EMPTY_STR = "";
	public static String NULL = "null";
	public static final Character SLASH = '\\';
	public static final Character INV_SLASH = '/';
	public static final Character DOT = '.';
	public static final Character COMMON = ',';
	public static final Character COLON = ':';	
	public static final Character SPACE = ' ';
	public static final Character TAB = '\t';
	public static final Character CR = '\r';
	public static final Character LF = '\n';
	public static final Character QUESTION = '?';
	public static final String CRLF = "\r\n";
	public static Random ran = new Random();
	public static String DEFAULT_CHARSET = "utf-8";
	
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
	
	// 默认的相关配置变量
	private static String DEFAULT_TMP_NAME = "tmp";
	private static String DEFAULT_TMP_DIR = "C:\\Users\\970655147\\Desktop\\tmp";
	private static int DEFAULT_BUFF_SIZE = 2048;
	private static String DEFAULT_SUFFIX = ".html";
	private static int DEFAULT_CHECK_INTERVAL = 3 * 1000;
	private static int DEFAULT_N_THREADS = 10;
	
	// 线程池相关
	public static int CHECK_INTERVAL = DEFAULT_CHECK_INTERVAL;
	public static int N_THREADS = DEFAULT_N_THREADS;
	public static ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(N_THREADS);
	
	// 临时文件相关
	public static String TMP_NAME = DEFAULT_TMP_NAME;
	public static String TMP_DIR = DEFAULT_TMP_DIR;
	public static AtomicInteger TMP_IDX = new AtomicInteger();
	public static int BUFF_SIZE = DEFAULT_BUFF_SIZE;
	public static String SUFFIX = DEFAULT_SUFFIX;
	
	// 后缀相关
	public static String HTML = ".html";
	public static String JAVA = ".java";
	public static String TXT = ".txt";
	public static String PNG = ".png";
	public static String JPEG = ".jpeg";
	public static String JS = ".js";
	public static String MAP = ".map";
	
	// 字节的表示相关
	public static String BYTE = "byte";
	public static String KB = "kb";
	public static String MB = "mb";
	public static String GB = "gb";
	public static String TB = "tb";
	
	// 文件名后面可能出现的其他符号
	static Set<Character> mayBeFileNameSeps = new HashSet<>();
	static {
		mayBeFileNameSeps.add(QUESTION);
	}
	
	// 如果字符串为一下字符串, 将其视为空字符串
	static Set<String> emptyStrCondition = new HashSet<>();
	static {
		emptyStrCondition.add(EMPTY_STR);
	}
	
	// 静态块, 初始化 可配置的数据
	// tmpName, tmpDir, buffSize, suffix, checkInterval, nThreads
	// emptyCondition, mayBeFileNameSeps
	static {
		boolean isException = false;
		Properties props = new Properties();
		try {
//			InputStream config = new FileInputStream(new File("./src/config.conf") );
			// 前者为true, 后者为false
//			Log.log(Main.class.getClass().getClassLoader() == null);
//			Log.log(new Main().getClass().getClassLoader() == null);
			InputStream config = new Main().getClass().getClassLoader().getResourceAsStream("config.conf");
			props.load(config);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			isException = true;
			Log.err("config file is not exist ...");
		} catch (IOException e) {
//			e.printStackTrace();
			isException = true;
			Log.err("IO Exception ...");
		} catch (NullPointerException e) {
//			e.printStackTrace();
			isException = true;
			Log.err("NullPointer Exception ...");
		}
		
		if(! isException) {
			TMP_NAME = props.getProperty("tmpName", DEFAULT_TMP_NAME);
			TMP_DIR = props.getProperty("tmpDir", DEFAULT_TMP_DIR);
			BUFF_SIZE = Integer.parseInt(props.getProperty("buffSize", String.valueOf(DEFAULT_BUFF_SIZE)) );
			SUFFIX = props.getProperty("suffix", DEFAULT_SUFFIX);
			CHECK_INTERVAL = Integer.parseInt(props.getProperty("checkInterval", String.valueOf(DEFAULT_CHECK_INTERVAL)) );
			N_THREADS = Integer.parseInt(props.getProperty("nThreads", String.valueOf(DEFAULT_N_THREADS)) );
			
			String[] emptyConds = props.getProperty("emptyStrCondition", EMPTY_STR).split(";");
			for(String emptyCon : emptyConds) {
				emptyStrCondition.add(emptyCon.trim() );
			}
			String[] fileNameSeps = props.getProperty("mayBeFileNameSeps", EMPTY_STR).split(";");
			for(String sep : fileNameSeps) {
				if(! Tools.isEmpty(sep)) {
					mayBeFileNameSeps.add(sep.charAt(0) );
				}
			}
		}
		
	}
	// ----------------- 属性结束 -----------------------
	
	
	// ---------------临时文件相关---------------
	// 获取临时路径的下一个路径[返回文件路径]
	public static void setTmpIdx(int idx) {
		TMP_IDX = new AtomicInteger(idx );
	}
	public static String getNextTmpPath() {
		return TMP_DIR + "\\" + getNextTmpName();
	}
	public static String getNextTmpPath(String suffix) {
		return TMP_DIR + "\\" + getNextTmpName(suffix);
	}
	public static String getNextTmpPath(String fileName, String suffix) {
		return TMP_DIR + "\\" + fileName + suffix;
	}
	public static String getTmpPath(int idx) {
		return TMP_DIR + "\\" + TMP_NAME + idx + SUFFIX;
	}
	public static String getTmpPath(int idx, String suffix) {
		return TMP_DIR + "\\" + TMP_NAME + idx + suffix;
	}
	
	// 获取临时文件的下一个索引[生成文件名称]
	private static String getNextTmpName() {
		return TMP_NAME + (TMP_IDX.getAndIncrement() ) + SUFFIX;
	}
	private static String getNextTmpName(String suffix) {
		return TMP_NAME + (TMP_IDX.getAndIncrement() ) + suffix;
	}
	
	// ----------------- 文件操作相关方法 -----------------------
	// 将html字符串保存到指定的文件中
	public static void save(String html, String nextTmpName) throws IOException {
		save(html, new File(nextTmpName) );
	}
	public static void save(String html, File nextTmpFile) throws IOException {
		write0(html, nextTmpFile, DEFAULT_CHARSET, false);
		Log.log("save content to \" " + nextTmpFile.getAbsolutePath() + " \" success ...");
	}	
	public static void append(String html, String nextTmpName) throws IOException {
		append(html, new File(nextTmpName) );
	}
	public static void append(String html, File nextTmpFile) throws IOException {
		write0(html, nextTmpFile, DEFAULT_CHARSET, true);
		Log.log("append content to \" " + nextTmpFile.getAbsolutePath() + " \" success ...");
	}
	private static void write0(String html, File nextTmpFile, String charset, boolean isAppend) throws IOException {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(nextTmpFile, isAppend) );
			bos.write(html.getBytes(charset) );
		} finally {
			if(bos != null) {
				bos.close();
			}
		}
	}
	
	// 刷出缓存的数据
	public static void flushBuffer(StringBuffer sb, String path) throws IOException {
	  Tools.append(sb.toString(), path);
	  Log.log("flush buffer at : " + new Date().toString() + ", size : " + getKBytesByBytes(sb.length() << 1) + " kb" );
	  sb.setLength(0);
	}
	
	// 移除指定的文件
	public static void remove(String path) {
		File file = new File(path);
		if(file.exists() ) {
			boolean isSucc = file.delete();
			if(isSucc) {
				Log.log("delete \" " + path + " \" success ...");
			} else {
				Log.log("delete \" " + path + " \" failed, maybe inuse ...");
			}
		} else {
			Log.log("\" " + path + " \" is not exists ...");
		}
	}
	
    // 复制指定的文件
    public static void copy(String src, String dst) throws IOException {
        File srcFile = new File(src);
        File dstFile = new File(dst);
        if(srcFile.isDirectory() ) {
            Log.log("srcFile \" " + src + " \" can't be folder ...");
            return ;
        }
        if(! srcFile.exists() ) {
            Log.log("srcFile \" " + src + " \" do not exists ...");
            return ;
        }
        if(dstFile.exists() ) {
            Log.log("dstFile \" " + dst + " \" does exists, please remove it first [make sure it is not important] ...");
            return ;
        }

        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(dstFile);
        copy(fis, fos);
        Log.log("copy file \" " + src + " \" -> \" " + dst + " \" success ...");
    }

	// 获取给定的输入流中的字符内容
	public static String getContent(InputStream is, String charset) throws IOException {
		StringBuilder sb = new StringBuilder(is.available() );
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(is, charset) );
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line );
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
		List<String> lines = new LinkedList<>();
		BufferedReader br = null;
		
		try {
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
		Matcher matcher = siteUrlPattern.matcher(url);
		if(matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
	
	// 将绝对/ 相对的url转换为绝对的url
	// 转换 /path & ./path
	public static String transformUrl(String url, String res) {
		if(res.startsWith("/") ) {
			return getSiteUrl(url) + removeIfStartsWith(res, "/");
		} else if (res.startsWith("./") ) {
			return url.substring(0, url.lastIndexOf("/")+1 ) + Tools.removeIfStartsWith(res, "./");
		} else {
			return res;
		}
	}

	// 如果给定的字符串以startsWith, 则移除startsWith
	public static String removeIfStartsWith(String str, String startsWith) {
		if(str.startsWith(startsWith) ) {
			return str.substring(startsWith.length() );
		}
		
		return str;
	}
	// 如果给定的字符串以endsWith, 则移除endsWith
	public static String removeIfEndsWith(String str, String endsWith) {
		if(str.endsWith(endsWith) ) {
			return str.substring(0, str.length() - endsWith.length());
		}
		
		return str;
	}
	
	// 如果不是给定的字符串以startsWith, 则添加startsWith
	public static String appendIfNotStartsWith(String str, String startsWith) {
		if(! str.startsWith(startsWith) ) {
			return startsWith + str;
		}
		
		return str;
	}
	public static String appendIfNotEndsWith(String str, String endsWith) {
		if(str.endsWith(endsWith) ) {
			return str + endsWith;
		}
		
		return str;
	}
	
	// 判断str01 和str02是否相同[忽略大小写]
	public static boolean equalsIgnoreCase(String str01, String str02) {
		return str01.toUpperCase().equals(str02.toUpperCase() );
	}
	
	// 通过cookies获取cookie的字符串表示
	public static String getCookieStr(Map<String, String> cookies) {
		StringBuilder sb = new StringBuilder();
		for(Entry<String, String> entry : cookies.entrySet()) {
			sb.append(entry.getKey() );		sb.append("=");
			sb.append(entry.getValue() );		sb.append(";");
		}
		if(sb.length() != 0) {
			return sb.substring(0, sb.length()-1);
		}
		
		return Tools.EMPTY_STR;
	}
	
	// 判断字符串是否为空[null, "", "null"]
	public static boolean isEmpty(String str) {
		return (str == null) || emptyStrCondition.contains(str.trim());
	}
	
	// 获取str中以start 和end之间的字符串
	public static String getStrInRange(String str, String start, String end) {
		int startIdx = str.indexOf(start);
		if(startIdx == -1) {
			return Tools.EMPTY_STR;
		}
		
		int endIdx = str.indexOf(end, startIdx + start.length());
		if(endIdx == -1) {
			return Tools.EMPTY_STR;
		}
		
		return str.substring(startIdx + start.length(), endIdx);
	}
	public static String getStrInRangeWithStart(String str, String start) {
		int idx = str.indexOf(start);
		if(idx != -1) {
			return str.substring(idx + start.length());
		}
		
		return Tools.EMPTY_STR;
	}
	public static String getStrInRangeWithEnd(String str, String end) {
		int idx = str.indexOf(end);
		if(idx != -1) {
			return str.substring(0, idx);
		}
		
		return Tools.EMPTY_STR;
	}
	public static String getStrInRangeInclude(String str, String start, String end) {
		int startIdx = str.indexOf(start);
		if(startIdx == -1) {
			return Tools.EMPTY_STR;
		}
		
		int endIdx = str.indexOf(end, startIdx + start.length());
		if(endIdx == -1) {
			return Tools.EMPTY_STR;
		}
		
		return str.substring(startIdx, endIdx + end.length() );
	}
	public static String getStrInRangeWithStartInclude(String str, String start) {
		int idx = str.indexOf(start);
		if(idx != -1) {
			return str.substring(idx);
		}
		
		return Tools.EMPTY_STR;
	}
	public static String getStrInRangeWithEndInclude(String str, String end) {
		int idx = str.indexOf(end);
		if(idx != -1) {
			return str.substring(0, idx + end.length());
		}
		
		return Tools.EMPTY_STR;
	}
	
	// ----------------- crawler 处理相关 -----------------------
	// 获取处理过之后的文档
	public static void getPreparedDoc(String url, String file) throws Exception {
		String html = HtmlCrawler.newInstance().getPage(url).getContent();
		getPreparedDoc(url, html, file);
	}
	public static void getPreparedDoc(String url, String html, String file) throws Exception {
		Tools.save(StringEscapeUtils.unescapeHtml(Tools.normalize(html) ), file);
	}
	
	// 通过xpath 获取结果
	public static JSONArray getResultByXPath(String html, String url, String xpath) throws Exception {
		return com.hx.crawlerTools_xpathParser.Parser.parse(Tools.normalize(html), url, xpath);
	}
	public static JSONArray getResultByXPathes(String html, String url, String[] xpathes, ResultJudger judger) throws Exception {
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
	public static SingleUrlTask newSingleTask(Crawler crawler, String url, Map<String, Object>param) {
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
	public static String getRealXPathByXPathObj(String xpath) {
		return "[" + xpath + "]";
	}
	public static String getRealXPathByXPathObj(String[] xpathes) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(xpathes[0]);
		for(int i=1; i<xpathes.length; i++) {
			sb.append(", \r\n");
			sb.append(xpathes[i]);
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	// 利用反射调用指定的class的methodName方法
	public static void parse(String className, String url, Map<String, Object> params) throws Exception {
		parse(className, url, params, PARSE_METHOD_NAME, IS_PARSE_METHOD_STATIC, PARSE_METHOD_PARAMTYPES);
	}
	public static void parse(String className, String url, Map<String, Object> params, String methodName, boolean isStaticMethod, Class[] methodParamTypes) throws Exception {
		Class clazz = Class.forName(className);
		Method method = clazz.getMethod(methodName, methodParamTypes);
		SingleUrlTask singleUrlTask = newSingleTask(HtmlCrawler.newInstance(), url, params);
		
		if(isStaticMethod) {
			method.invoke(null, singleUrlTask);
		} else {
			method.invoke(clazz.newInstance(), singleUrlTask);
		}
	}
	public static void parseAsync(final String className, final String url, final Map<String, Object> params) throws Exception {
		parseAsync(className, url, params, PARSE_METHOD_NAME, IS_PARSE_METHOD_STATIC, PARSE_METHOD_PARAMTYPES);
	}
	public static void parseAsync(final String className, final String url, final Map<String, Object> params, final String methodName, final boolean isStaticMethod, final Class[] methodParamTypes) throws Exception {
		threadPool.execute(new Runnable() {
			public void run() {
				try {
					parse(className, url, params, methodName, isStaticMethod, methodParamTypes);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	// 为nextStageParams添加category
	public static void addNameUrlSite(JSONObject category, JSONObject nextStageParams) {
		nextStageParams.put(Tools.NAME, category.getString("name"));
		nextStageParams.put(Tools.URL, category.getString("url"));
		nextStageParams.put(Tools.SITE, nextStageParams.getString("site") + "." +category.getString("name"));
		
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
	public static String replaceMultiSpacesAsOne(String str) {
		if(str == null) {
			return null;
		}
		
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<chars.length; i++) {
			if(spaces.contains(chars[i])) {
				sb.append(SPACE);
				int nextI = i+1;
				while((nextI < chars.length) && spaces.contains(chars[nextI])) nextI++ ;
				i = nextI - 1;
				continue ;
			}
			sb.append(chars[i]);
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

	// 去掉掉obj中所有的字符串类的值的相邻的多个空格
	// 思路 : 如果obj是空对象  则直接返回
	// 否则 遍历各个kv数据, 如果值为String  则去掉其多余的空格, 然后在更新obj中对应key的值
		// 否则如果 值为JSONObject, 递归
		// 否则如果 值为JSONArray, trimSpaces(JSONArray )
	public static void trimSpaces(JSONObject obj) {
		if(obj == null || obj.isNullObject() || obj.isEmpty()) {
			return ;
		}
		
		JSONArray names = obj.names();
		Iterator<String> it = names.iterator();
		while(it.hasNext()) {
			String key = it.next();
			Object val = obj.get(key );
			if(val instanceof String) {
				obj.put(key, replaceMultiSpacesAsOne((String) val));
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
	public static void trimSpaces(JSONArray arr) {
		if(arr == null || arr.isEmpty()) {
			return ;
		}
		
		for(int i=0; i<arr.size(); i++ ) {
			Object val = arr.get(i);
			if(val instanceof String) {
				arr.set(i, replaceMultiSpacesAsOne((String) val));
			} else if(val instanceof JSONObject) {
				trimSpaces((JSONObject) val);
			}
		}
	}
	
	// 确保arr中的每一个JSONObject都存在指定的key, 否则  则删除该条目
	// val.toString可以确保值为null的情形
	public static void removeIfNull(JSONArray arr, String key) {
		if(arr == null || arr.isEmpty()) {
			return ;
		}
		
		Iterator<Object> it = arr.iterator();
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
		if(obj == null || obj.isNullObject() || obj.isEmpty()) {
			return ;
		}
		
		JSONArray names = obj.names();
		Iterator<String> it = names.iterator();
		while(it.hasNext()) {
			String key = it.next();
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
		if(arr == null || arr.isEmpty()) {
			return ;
		}
		
		Iterator<Object> it = arr.iterator();
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
		if((spec == null) || (product == null) ) {
			return ;
		}
		
		Iterator<JSONObject> it = spec.iterator();
		while(it.hasNext()) {
			JSONObject val = it.next();
			String key = val.getString(name);
			if(getInSpec.containsKey(key) ) {
				product.put(getInSpec.get(key), val.get(value));
			}
		}
	}

	// 过滤掉不需要的字符
	public static Object filter(String str, Set<Character> needBeFiltered) {
		if((str == null) || (needBeFiltered == null) ) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder(str.length());
		for(int i=0; i<str.length(); i++) {
			if(! needBeFiltered.contains(str.charAt(i)) ) {
				sb.append(str.charAt(i));
			}
		}
		
		return replaceMultiSpacesAsOne(sb.toString() );
	}
	
	// 向sb中添加str
	public static void appendCRLF(StringBuilder sb, String str, boolean isClean) {
		if(isClean) {
			sb.setLength(0);
		}
		appendCRLF(sb, str);
	}
	public static void appendCRLF(StringBuilder sb, String str) {
		sb.append(str);
		sb.append(CRLF);
	}
	
	// 获取taskName
	public static String getTaskName(SingleUrlTask singleUrlTask) {
		return " crawl " + singleUrlTask.getParam().get(Tools.TASK) + " from " + singleUrlTask.getParam().get(Tools.SITE) + "　";
	}
	
	// 获取键值对类型的数据对, 添加到headers中
	public static void addHeaders(File configFile, Map<String, String> headers, String sep) throws IOException {
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
		StringBuilder sb = new StringBuilder(str.length() );
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if(ch == SLASH) {
				char nextCh = str.charAt(i + 1);
				if(nextCh == 'u') {
					boolean isUnicode = true;
					for(int j=0; j<4; j++) {
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
	public static void downloadFrom(String urlStr, String path) throws IOException {
		URL url = new URL(urlStr);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(new File(path));
		copy(is,  os);
		
		Log.log("download file \"" + path + "\" succcess ...");
	}
	
	// 将输入流中的数据 复制到输出流
	public static void copy(InputStream is, OutputStream os, boolean isCloseStream) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			int len = 0;
			byte[] buf = new byte[BUFF_SIZE];
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
	
	// shutdown 线程池
	public static void awaitShutdown(ThreadPoolExecutor threadPool, int checkInterval, boolean isLog) {
		awaitTasksEnd0(threadPool, checkInterval, true, isLog);
	}
	public static void awaitShutdown() {
		awaitShutdown(threadPool, CHECK_INTERVAL, false);
	}
	
    // 等待 线程池中任务结束 [并不关闭线程池]
    public static void awaitTasksEnd(ThreadPoolExecutor threadPool, int checkInterval, boolean isLog) {
    	awaitTasksEnd0(threadPool, checkInterval, false, isLog);
    }
    public static void awaitTasksEnd() {
    	awaitTasksEnd(threadPool, CHECK_INTERVAL, false);
    }
    private static void awaitTasksEnd0(ThreadPoolExecutor threadPool, int checkInterval, boolean isShutdown, boolean isLog) {
        while (! threadPool.isShutdown() ) {
        	int taskInQueue = threadPool.getQueue().size();
        	int activeTaskCount = threadPool.getActiveCount();
            if((taskInQueue == 0) && (activeTaskCount == 0) ) {
            	if(isShutdown) {
            		threadPool.shutdown();
            	}
                break ;
            } else {
            	if(isLog ) {
            		Log.log("task in queue : " + taskInQueue + ", active task count : " + activeTaskCount + ", at : " + new Date().toString() + " !");
            	}
                Tools.sleep(checkInterval);
            }
        }
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
	// 打印任务的日志信息
	public static void logBeforeTask(SingleUrlTask singleUrlTask, boolean debugEnable) {
		if(debugEnable ) {
			StringBuilder sb = new StringBuilder();
		    Tools.appendCRLF(sb, "URL : " + singleUrlTask.getUrl() );
		    Tools.appendCRLF(sb, "--------------------- [" + Tools.getTaskName(singleUrlTask) + "start ...] --------------------------");
		    Log.log(sb.toString() );
		}
	}
	public static void logAfterTask(SingleUrlTask singleUrlTask, String fetchedResult, String spent, boolean debugEnable) {
		if(debugEnable ) {
			StringBuilder sb = new StringBuilder();
		    Tools.appendCRLF(sb, "fetched result : " + fetchedResult);
		    Tools.appendCRLF(sb, "--------------------- [crawl" + Tools.getTaskName(singleUrlTask) + "end ...] --------------------------");
		    Tools.appendCRLF(sb, "spent " + spent + " ms ...");
		    Log.log(sb.toString() );
		}
	}
	public static void logErrorMsg(SingleUrlTask singleUrlTask, Exception e) {
		Log.err(e.getClass().getName() + " while fetch : " + Tools.getTaskName(singleUrlTask) + ", url : " + singleUrlTask.getUrl() );
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
	
	
	
	// ------------ 待续 --------------------

}
