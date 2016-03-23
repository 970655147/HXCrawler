/**
 * file name : Page.java
 * created at : 3:28:05 PM Jul 26, 2015
 * created by 970655147
 */

package com.hx.crawlerTools_crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.hx.crawler.util.Tools;

// Page
public class Page {

	// Response
	private String content;
	private HttpResponse httpResp;
	private Map<String, String> cookies = new HashMap<>();
	private String charset = Tools.DEFAULT_CHARSET;
	
	// ��ʼ��
	public Page() {
		super();
	}
	public Page(Response resp) {
		super();
		try {
			this.httpResp = resp.returnResponse();
			parseResponse(httpResp);
			
//			this.content = Tools.getContent(httpResp.getEntity().getContent() );
			this.content = EntityUtils.toString(httpResp.getEntity(), charset );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��Ӧͷ��cookie��Ԫ�ؾ�keys
	static Set<String> nonCookieKeys = new HashSet<>();
	static {
		nonCookieKeys.add(getStdForString("path") );
		nonCookieKeys.add(getStdForString("Max-Age") );
		nonCookieKeys.add(getStdForString("max-age") );
		nonCookieKeys.add(getStdForString("expires") );
		nonCookieKeys.add(getStdForString("domain") );
	}
	
	// ������Ӧ��Ϣ [cookie, charset�ȵ�]
	public void parseResponse(HttpResponse resp) {
		Header[] headers = resp.getAllHeaders();

		for(int i=0; i<headers.length; i++) {
			Header header = headers[i];
			String headerName = header.getName();
			if(Tools.equalsIgnoreCase(headerName, Tools.RESP_COOKIE_STR) ) {
				String headerValue = header.getValue();
				Set<String> nonCookieKeysTmp = nonCookieKeys;
				NameValuePair[] cookie = getCookie(headerValue, nonCookieKeysTmp);
				if(cookie != null) {
					for(NameValuePair nvp : cookie) {
						cookies.put(nvp.getName(), nvp.getValue() );
					}
				}
			} else if(Tools.equalsIgnoreCase(headerName, Tools.CONTENT_TYPE) ) {
				String charsetTmp = Tools.getStrInRangeWithStart(header.getValue(), "charset=");
				if(! Tools.isEmpty(charsetTmp) ) {
					charset = charsetTmp;
				}
			}
		}
		
	}
	
	// setter & getter
	public HttpResponse getResponse() {
		return httpResp;
	}
	public String getCharset() {
		return charset;
	}
	public Map<String, String> getCookies() {
		return cookies;
	}
	
	// ��ȡPage������
	public String getContent() {
			String srcHtml = content;
//			String srcHtml = new String(resp.returnContent().asString().getBytes("iso8859-1"), "gbk");
//			String dstHtml = Jsoup.parseBodyFragment(srcHtml).outerHtml();
			String dstHtml = srcHtml;
			return dstHtml;
	}
	
	// ��ȡ��Ӧͷ�е�"Set-Cookie" �ж�Ӧ��cookie
	// ��; �ָ�kv��, =�ָ�key ��value
	private NameValuePair[] getCookie(String value, Set<String>nonCookieKeys) {
		NameValuePair[] cookie = new BasicNameValuePair[0];
		int lastIdxSemicolon = 0, idxEqu = value.indexOf("="), idxSemicolon = value.indexOf(";");
		while((idxSemicolon > 0) && (idxEqu > 0) ) {
			String key = value.substring(lastIdxSemicolon, idxEqu);
			String val = value.substring(idxEqu+1, idxSemicolon);
			if(! nonCookieKeys.contains(getStdForString(key.trim())) ) {
				NameValuePair[] cookieTmp = new BasicNameValuePair[cookie.length + 1];
				System.arraycopy(cookie, 0, cookieTmp, 0, cookie.length);
				cookieTmp[cookieTmp.length-1] = new BasicNameValuePair(key, val);
				cookie = cookieTmp;
			}
			
			lastIdxSemicolon = idxSemicolon+1;
			idxEqu = value.indexOf("=", idxSemicolon);
			idxSemicolon = value.indexOf(";", idxSemicolon+1);
		}
		// �������һ��kv��  
		// ³�����ж� : Set-Cookie: __cfduid=d97fc04a3d79d4567d86b62582fbaa4bc1444897363; expires=Fri, 14-Oct-16 08:22:43 GMT; path=/; domain=.tom365.co; HttpOnly
		if(idxEqu > 0) {
			String key = value.substring(lastIdxSemicolon, idxEqu);
			String val = value.substring(idxEqu+1);
			if(! nonCookieKeys.contains(getStdForString(key.trim())) ) {
				NameValuePair[] cookieTmp = new BasicNameValuePair[cookie.length + 1];
				System.arraycopy(cookie, 0, cookieTmp, 0, cookie.length);
				cookieTmp[cookieTmp.length-1] = new BasicNameValuePair(key, val);
				cookie = cookieTmp;
			}
		}
		
		return cookie;
	}
	
	// ��ȡͳһ�Ĵ�д ����Сд
	private static String getStdForString(String str) {
		return str.toUpperCase();
	}
	
	
}
