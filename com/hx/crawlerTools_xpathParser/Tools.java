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
import java.nio.ByteBuffer;
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
import com.hx.util.Log;

// ������
public class Tools {
	
	// ����
	public static String EMPTY_STR = "";
	public static String NULL = "null";
	public static final Character SLASH = '\\';
	public static final Character INV_SLASH = '/';
	private static final Character DOT = '.';
	private static final Character SPACE = ' ';
	private static final Character TAB = '\t';
	private static final Character CR = '\r';
	private static final Character LF = '\n';
	private static final Character QUESTION = '?';
	public static final String CRLF = "\r\n";
	public static Random ran = new Random();
	public static String DEFAULT_CHARSET = "utf-8";
	
	// ҵ����س���
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
	
	// http��س���
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
	
	// parse ��س���
	public static final String PARSE_METHOD_NAME = "parse";
	public static final boolean IS_PARSE_METHOD_STATIC = true;
	public static final Class[] PARSE_METHOD_PARAMTYPES = new Class[]{ScriptParameter.class };
	
	// Ĭ�ϵ�������ñ���
	private static String DEFAULT_TMP_NAME = "tmp";
	private static String DEFAULT_TMP_DIR = "C:\\Users\\970655147\\Desktop\\tmp";
	private static int DEFAULT_BUFF_SIZE = 2048;
	private static String DEFAULT_SUFFIX = ".html";
	private static int DEFAULT_CHECK_INTERVAL = 3 * 1000;
	private static int DEFAULT_N_THREADS = 10;
	
	// �̳߳����
	public static int CHECK_INTERVAL = DEFAULT_CHECK_INTERVAL;
	public static int N_THREADS = DEFAULT_N_THREADS;
	public static ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(N_THREADS);
	
	// ��ʱ�ļ����
	public static String TMP_NAME = DEFAULT_TMP_NAME;
	public static String TMP_DIR = DEFAULT_TMP_DIR;
	public static AtomicInteger TMP_IDX = new AtomicInteger();
	public static int BUFF_SIZE = DEFAULT_BUFF_SIZE;
	public static String SUFFIX = DEFAULT_SUFFIX;
	
	// ��׺���
	public static String HTML = ".html";
	public static String JAVA = ".java";
	public static String TXT = ".txt";
	public static String PNG = ".png";
	public static String JPEG = ".jpeg";
	public static String JS = ".js";
	
	// �ļ���������ܳ��ֵ���������
	static Set<Character> mayBeFileNameSeps = new HashSet<>();
	static {
		mayBeFileNameSeps.add(QUESTION);
	}
	
	// ����ַ���Ϊһ���ַ���, ������Ϊ���ַ���
	static Set<String> emptyStrCondition = new HashSet<>();
	static {
		emptyStrCondition.add(EMPTY_STR);
	}
	
	// ��̬��, ��ʼ�� �����õ�����
	// tmpName, tmpDir, buffSize, suffix, checkInterval, nThreads
	// emptyCondition, mayBeFileNameSeps
	static {
		boolean isException = false;
		Properties props = new Properties();
		try {
//			InputStream config = new FileInputStream(new File("./src/config.conf") );
			// ǰ��Ϊtrue, ����Ϊfalse
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
				mayBeFileNameSeps.add(sep.charAt(0) );
			}
		}
		
	}
	
	// ��ȡ�ļ�������
	public static String getContent(String path) throws IOException {
		return getContent(new File(path));
	}
	public static String getContent(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		return sb.toString();
	}
	
	// ��ȡ�ļ������е���, �洢��һ�������List, �ļ�����, ���ô˷���
	public static List<String> getContentWithList(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)) );
		List<String> lines = new LinkedList<>();
		
		String line = null;
		while((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		
		return lines;
	}
	
	// ��ȡinputStream�е��ַ���
	public static String getContent(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder(is.available() );
		BufferedReader br = new BufferedReader(new InputStreamReader(is) );

		String line = null;
		while((line = br.readLine()) != null) {
			sb.append(line );
		}
		
		return sb.toString();
	}

	// ƥ��siteUrl��regex
	static Pattern siteUrlPattern = Pattern.compile("^(\\w{3,5}://\\w+(\\.\\w+)+?/)(.*)");
	
	// ��ȡվ�����ҳurl
	// http://www.baidu.com/tieba/java/page01.jsp  =>  http://www.baidu.com/
	public static String getSiteUrl(String url) {
		Matcher matcher = siteUrlPattern.matcher(url);
		if(matcher.matches()) {
			return matcher.group(1);
		}
		return null;
	}
	
	// ������/ ��Ե�urlת��Ϊ���Ե�url
	// ת�� /path & ./path
	public static String transformUrl(String url, String res) {
		if(res.startsWith("/") ) {
			return getSiteUrl(url) + removeIfStartsWith(res, "/");
		} else if (res.startsWith("./") ) {
			return url.substring(0, url.lastIndexOf("/")+1 ) + Tools.removeIfStartsWith(res, "./");
		} else {
			return res;
		}
	}

	// ����������ַ�����startsWith, ���Ƴ�startsWith
	public static String removeIfStartsWith(String str, String startsWith) {
		if(str.startsWith(startsWith) ) {
			return str.substring(startsWith.length() );
		}
		
		return str;
	}
	
	// ����������ַ�����endsWith, ���Ƴ�endsWith
	public static String removeIfEndsWith(String str, String endsWith) {
		if(str.endsWith(endsWith) ) {
			return str.substring(0, str.length() - endsWith.length());
		}
		
		return str;
	}
	
	// �ж�str01 ��str02�Ƿ���ͬ[���Դ�Сд]
	public static boolean equalsIgnoreCase(String str01, String str02) {
		return str01.toUpperCase().equals(str02.toUpperCase() );
	}
	
	// ͨ��cookies��ȡcookie���ַ�����ʾ
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
	
	
	// ---------------��ʱ�ļ����---------------
	// ��ȡ��ʱ·������һ��·��[�����ļ�·��]
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
	
	// ��ȡ��ʱ�ļ�����һ������[�����ļ�����]
	private static String getNextTmpName() {
		return TMP_NAME + (TMP_IDX.getAndIncrement() ) + SUFFIX;
	}
	private static String getNextTmpName(String suffix) {
		return TMP_NAME + (TMP_IDX.getAndIncrement() ) + suffix;
	}
	
	
	// ��html�ַ������浽ָ�����ļ���
	public static void save(String html, String nextTmpName) throws IOException {
		save(html, new File(nextTmpName) );
	}
	public static void save(String html, File nextTmpFile) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nextTmpFile) );
		bos.write(html.getBytes(DEFAULT_CHARSET) );
		bos.close();
		
		Log.log("save content to \" " + nextTmpFile.getAbsolutePath() + " \" success ...");
	}	
	public static void append(String html, String nextTmpName) throws IOException {
		append(html, new File(nextTmpName) );
	}
	public static void append(String html, File nextTmpFile) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nextTmpFile, true) );
		bos.write(html.getBytes(DEFAULT_CHARSET) );
		bos.close();
		
		Log.log("append content to \" " + nextTmpFile.getAbsolutePath() + " \" success ...");
	}
	

	// ----------------- ҵ�񷽷� -----------------------
	
	// ���е����ֵ�Character
	static Set<Character> nums = new HashSet<>();
	static {
		for(char i='0'; i<='9'; i++) {
			nums.add(i);
		}
	}
	
	// ����۸�, Ҳ�������ڴ�����ȡ�ַ����е�BigDecimal�����
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
	
	// ����ҳ��, Ҳ�������ڴ�����ȡ�ַ����е����������
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
	
	// �ж��ַ����Ƿ�Ϊ��[null, "", "null"]
	public static boolean isEmpty(String str) {
		return (str == null) || emptyStrCondition.contains(str.trim());
	}
	
	// ��ȡstr����start ��end֮����ַ���
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
	
	// ��ȡ�����֮����ĵ�
	public static void getPreparedDoc(String url, String file) throws Exception {
		String html = HtmlCrawler.newInstance().getPage(url).getContent();
		getPreparedDoc(url, html, file);
	}
	public static void getPreparedDoc(String url, String html, String file) throws Exception {
		Tools.save(StringEscapeUtils.unescapeHtml(Tools.normalize(html) ), file);
	}
	
	// ͨ��xpath ��ȡ���
	public static JSONArray getResultByXPath(String html, String url, String xpath) throws Exception {
		return com.hx.crawlerTools_xpathParser.Parser.parse(Tools.normalize(html), url, xpath);
	}
	
	// ��ʽ��html [ȥ��, ��� ���淶�ı�ǩ]
	// ˼· : ʹ��tagSoup�淶��xml
		// ע�� : ��Ҫ�ض���ContentHandler�����
		// ��Ҫȥ��xml����, ��Ҫȥ��html��ǩ��xmlns������
	public static String normalize(String html) throws Exception {
//		StringReader xmlReader = new StringReader("");
		StringReader sr = new StringReader(html);
		Parser parser = new Parser();		//ʵ����Parse
		
//		// �����ʽ��֮���xml
		XMLWriter writer = new XMLWriter();	//ʵ����XMLWriter����SAX���ݴ�����
		parser.setContentHandler(writer);	//�������ݴ�����
		StringWriter strWriter = new StringWriter();
		writer.setOutput(strWriter);
		
		parser.parse(new InputSource(sr));	//����
		
//		Scanner scan = new PYXScanner();
//		scan.scan(xmlReader, parser);	//ͨ��xmlReader��ȡ������Ľ��
		
//		char[] buff = new char[1024];
//		StringBuilder sb = new StringBuilder();
//		while(xmlReader.read(buff) != -1) {
//		    sb.append(buff);		//��ӡ������Ľṹ���õ�HTML�ĵ�
//		} 
		
		// ��ȡ�ṹ���õ�html, ��ȥ��xml����
		String res = strWriter.toString();
		res = res.substring(res.indexOf("\n") + 1);
		
		// ������Ϊ�������xmlns������, �������ҵ�xPath��ȡ���������⡣����, ����˵ ֻ��ͨ��/*[@XX='XXX'] ����ȡ����
		// ���Ҷ�ȡ�������� ����һ��xmlns������...						--2015.07.31
//		String html = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><body><strong><span id=\"ctl01_ContentPlaceHolder1_lblPriceTitle\">ProductPricing</span>:</strong></body></html>";
		// ���� �����res����ͳһ�Ĵ���, ȥ��html��ǩ��xmlns������
		String needReplace = "xmlns=\"http://www.w3.org/1999/xhtml\"";
		return res.replace(needReplace, Tools.EMPTY_STR);
	}
	
	// ����һ��ScriptParameter
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
	
	// ͨ��xpath��ȡ��ʵ��xpath
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
	
	// ���÷������ָ����class��methodName����
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
	
	// ΪnextStageParams���category
	public static void addNameUrlSite(JSONObject category, JSONObject nextStageParams) {
		nextStageParams.put(Tools.NAME, category.getString("name"));
		nextStageParams.put(Tools.URL, category.getString("url"));
		nextStageParams.put(Tools.SITE, nextStageParams.getString("site") + "." +category.getString("name"));
		
	}
	
	// ͨ����Ʒ����Ŀ, �Լ�ÿһҳ��ʾ�Ĳ�Ʒ����Ŀ, ����ҳ��
	public static int calcPageNums(int productNum, int numPerPage) {
		return ((productNum-1) / numPerPage) + 1;
	}
	
	// �ո����ַ�
	static Set<Character> spaces = new HashSet<>();
	static {
		spaces.add(SPACE);
		spaces.add(TAB);
		spaces.add(CR);
		spaces.add(LF);
	}
	
	// ���ַ����Ķ�������Ŀո�ת��Ϊһ���ո�
	// ˼· : ���strΪnull  ֱ�ӷ���null
	// ��str�ж�����ڵĿո��滻Ϊһ���ո�[SPACE]
	// ���������ַ�������Ϊ1 ���Ҹ��ַ�Ϊ�ո�, ��ֱ�ӷ��ؿ��ַ���
	// ����  ȥ��ǰ��Ŀո�, ����֮������ַ���
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

	// ȥ����obj�����е��ַ������ֵ�����ڵĶ���ո�
	// ˼· : ���obj�ǿն���  ��ֱ�ӷ���
	// ���� ��������kv����, ���ֵΪString  ��ȥ�������Ŀո�, Ȼ���ڸ���obj�ж�Ӧkey��ֵ
		// ������� ֵΪJSONObject, �ݹ�
		// ������� ֵΪJSONArray, trimSpaces(JSONArray )
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

	// ȥ����arr�����е��ַ������ֵ�����ڵĶ���ո�
	// ˼· : ���arr�ǿն���  ��ֱ�ӷ���
	// ���� ������������, ���ֵΪString  ��ȥ�������Ŀո�, Ȼ���ڸ���obj�ж�Ӧkey��ֵ
		// ������� ֵΪJSONObject, trimSpaces(JSONObject )
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
	
	// ȷ��arr�е�ÿһ��JSONObject������ָ����key, ����  ��ɾ������Ŀ
	// val.toString����ȷ��ֵΪnull������
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
	
	// ȥ����obj�����е��ַ������ֵ�����ڵĶ���ո�
	// ˼· : ���obj�ǿն���  ��ֱ�ӷ���
	// ���� ��������kv����, ���ֵΪString  ���ֻΪ��  ��ȥ����ǰkv��
		// ������� ֵΪJSONObject, �ݹ�,  ����÷���֮��, valΪ��, ���Ƴ�val��Ӧ����Ŀ
		// ������� ֵΪJSONArray, removeIfNull(JSONArray ),  ����÷���֮��, valΪ��, ���Ƴ�val��Ӧ����Ŀ
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
				// ��ֹ "{'price' : null } "������
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
	
	// ȥ����arr�����е��ַ������ֵ�����ڵĶ���ո�
	// ˼· : ���arr�ǿն���  ��ֱ�ӷ���
	// ���� ������������, ���ֵΪString  ��ȥ�������Ŀո�, Ȼ���ڸ���obj�ж�Ӧkey��ֵ
		// ������� ֵΪJSONObject, removeIfNull(JSONObject ), ����÷���֮��, valΪ��, ���Ƴ�val��Ӧ����Ŀ
	// ע�� : ��Ϊ�����JSONArray������ɾ������, ��������ʹ����Iterator
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
				// ��ֹ "{'price' : null } "������
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
	
	// ��spec�л�ȡ��Ҫ������
	// ע�� : ����ȷ��spec��ÿһ������ΪJSONObject, nameΪspec����������Ҫ����ֵ, valueΪspec����������Ҫ��ȡ��ֵ, getInSpec��Ż�ȡ���ݵļ���(key[srcԴ����] -> key[dstĿ�����])ӳ��
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

	// ���˵�����Ҫ���ַ�
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
	
	// ��sb�����str
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
	
	// ��ȡtaskName
	public static String getTaskName(SingleUrlTask singleUrlTask) {
		return " crawl " + singleUrlTask.getParam().get(Tools.TASK) + " from " + singleUrlTask.getParam().get(Tools.SITE) + "��";
	}
	
	// ��ȡ��ֵ�����͵����ݶ�, ��ӵ�headers��
	public static void addHeaders(File configFile, Map<String, String> headers, String sep) throws IOException {
		List<String> lines = Tools.getContentWithList(configFile);
		for(String line : lines) {
			int idx = line.indexOf(sep);
			if(idx > 0) {
				headers.put(line.substring(0, idx), line.substring(idx + 1));
			}
		}
	}
	
	// ���뺬��unicode�ַ������ַ���
	// ����һ���ַ���, Ѱ�ҳ�ƥ��"\\uxxxx"���ַ���, Ȼ�������Ϊ�ַ�[unicode -> char]
	// �����������ַ���������
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
					
					// ���"\\u"֮����ĸ��ַ����Ա�ʾΪʮ�����Ƶ�����, �������, ������i, continue
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
	
	// �жϸ������ַ��Ƿ�ɱ�ʾʮ������[0-9, a-f, A-F]
	public static boolean isHexChar(char ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
	}
	
	// ��ָ����url��������ͼƬ  ���浽ָ����·������ [Ҳ���������������Ķ���������]
	public static void downloadFrom(String urlStr, String path) throws IOException {
		URL url = new URL(urlStr);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(new File(path));
		copy(is,  os);
		is.close();
		os.close();
		
		Log.log("download file \"" + path + "\" succcess ...");
	}
	
	// ���������е����� ���Ƶ������
	public static void copy(InputStream is, OutputStream os) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		
		int len = 0;
		byte[] buf = new byte[BUFF_SIZE];
		while((len = bis.read(buf)) != -1) {
			bos.write(buf, 0, len);
		}
		bos.close();
		bis.close();
	}

	// վ����ҳģʽ
	static Pattern siteUrlPat = Pattern.compile("(\\w{2,5}://\\w+(\\.\\w+)+/).*");
	
	// ��ȡ����url��վ����ҳ
	public static String getSiteForUrl(String url) {
		Matcher matcher = siteUrlPat.matcher(url);
		
		if(matcher.matches() ) {
			return matcher.group(1);
		}
		return url;
	}
	
	// ��ȡָ��·�����ļ����ļ�, ͨ��sep�ָ���ļ���     ��ȡ�ļ���
	// ����? ��λ��, ��Ϊ�˷�ֹһ�����
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
	
	// ��ȡ�ļ�������Ŀ��ܳ��ֵķ��ϵ����������
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
	
	// awaitTermination �̳߳�
//	public static void awaitTermination(long timout, TimeUnit unit) {
//		try {
//			threadPool.awaitTermination(timout, unit);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	// shutdown �̳߳�
	public static void awaitShutdown() {
		while (! threadPool.isShutdown() ) {
			int acitveTaskCount = threadPool.getActiveCount();
			
			if(acitveTaskCount == 0) {
				threadPool.shutdown();
			} else {
				Tools.sleep(CHECK_INTERVAL);
			}
		}
	}
	
	// �õ�ǰ�߳���Ϣms
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// ��ӡ�������־��Ϣ
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
	
	
	
}
