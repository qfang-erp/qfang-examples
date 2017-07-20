package com.qfang.examples.httpserver.simple;

import com.qfang.examples.httpserver.Response;
import com.qfang.examples.httpserver.common.Constants;
import com.qfang.examples.httpserver.server.SimpleHttpServer;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.qfang.examples.httpserver.common.Constants.SPLIT;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public class SimpleResponse implements Response {
	
	private String uri;
	private Map<String, Object> params = new HashMap<>();

	public SimpleResponse(String uri, Map<String, Object> params) {
		this.uri = uri;
		this.params = params;
	}

	@Override
	public byte[] toBytes() throws IOException {
		if(StringUtils.isEmpty(uri))
			throw new IOException("找不到对应的 uri 路径");

		String header;
		byte[] data;
		if(uri.endsWith(".png")) {
			data = getResourceAsBytes(uri);
			header = buildResponseHeader("image/jpeg", data.length);
		} else if(uri.endsWith(".msp")) {
			String body = getResourceAsString(uri);
			data = parseDynamicScripts(body).getBytes(Charsets.UTF_8);
			header = buildResponseHeader("text/html; charset=utf-8", data.length);
		} else {
			String body = getResourceAsString(uri);
			data = body.getBytes(Charsets.UTF_8);
			header = buildResponseHeader("text/html; charset=utf-8", data.length);
		}

		byte[] headerBytes = header.getBytes();
		byte[] responseBytes = new byte[headerBytes.length + data.length];
		System.arraycopy(headerBytes, 0, responseBytes, 0, headerBytes.length);
		System.arraycopy(data, 0, responseBytes, headerBytes.length, data.length);
		return responseBytes;
	}

	private String parseDynamicScripts(String html) {
		Pattern pattern = Pattern.compile("(.*)<%([\\s\\S]*?)%>(.*)");
		Matcher matcher = pattern.matcher(html);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String script = matcher.group(2);
			String result = executeScript(script);
			matcher.appendReplacement(sb, result);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String executeScript(String script) {
		Binding binding = new Binding();
		this.params.forEach((String key, Object value) -> {
			binding.setVariable(key, value.toString());
		});

		GroovyShell shell = new GroovyShell(binding);
		return shell.evaluate(script).toString();
	}

	protected String buildResponseHeader(String contentType, int contentLength) {
		StringBuilder responseHeader = new StringBuilder();
		SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		responseHeader.append("HTTP/1.1 200 OK").append(SPLIT)  // 状态行
			.append("Server: Tengine/2.1.1").append(SPLIT)   // 响应头部
			.append("Date: ").append(gmtFrmt.format(new Date())).append(SPLIT)
			.append("Content-Type: ").append(contentType).append(SPLIT)
			.append("Connection: close").append(SPLIT)  // 是否复用链接(keep-alive / close)
			.append("Content-Length: ").append(contentLength).append(SPLIT)
			.append(SPLIT);
		return responseHeader.toString();
	}
	
	protected static String getResourceAsString(String requestPage) throws IOException {
		StringBuilder html = new StringBuilder();
		try(InputStream is = SimpleHttpServer.class.getClassLoader().getResourceAsStream(requestPage)) {
			if(is != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
					reader.lines().map(line -> line.trim()).forEach(line -> html.append(line).append(Constants.SPLIT));
				}
			} else {
				html.append("404! Page not found.");
			}
		}
		return html.toString();
	}
	
	protected byte[] getResourceAsBytes(String requestPage) throws IOException {
		try(InputStream is = SimpleHttpServer.class.getClassLoader().getResourceAsStream(requestPage)) {
			if(is == null)
				return new byte[0];
			
			byte[] bs = new byte[is.available()];
			is.read(bs);
			return bs;
		}
	}

	public static void main(String[] args) throws IOException {
		String html = getResourceAsString("test.msp");
		Map<String, Object> params = new HashMap<>();
		params.put("userName", "zhangsan");
		SimpleResponse response = new SimpleResponse("test.msp", params);
		System.out.println(response.parseDynamicScripts(html));
	}

}
