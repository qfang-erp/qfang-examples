package com.qfang.examples.httpserver.simple;

import com.qfang.examples.httpserver.Request;
import com.qfang.examples.httpserver.common.MethodType;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * TODO
 *
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public class SimpleRequest implements Request {

    private final String requestSourceStr;

    private String url;  // index.html?a=b
    private String uri;  // index.html
    private MethodType methodType;  // GET,POST,PUT,DELETE
    private final Map<String, Object> params = new HashMap<>();

    public SimpleRequest(String requestSourceStr) {
        this.requestSourceStr = requestSourceStr;
        this.decode();
    }

    public void decode() {
        try (LineNumberReader reader = new LineNumberReader(new StringReader(requestSourceStr))) {
            String lineInput;
            boolean firstLine = true;
            while ((lineInput = reader.readLine()) != null) {
                if (firstLine) {
                    this.url = lineInput.substring(lineInput.indexOf("/") + 1, lineInput.lastIndexOf(' '));
                    this.methodType = MethodType.valueOf(lineInput.substring(0, lineInput.indexOf(' ')));
                    firstLine = false;
                }

                if (lineInput.isEmpty())
                    break;
            }

            if (this.methodType == MethodType.POST) {

            } else if (this.methodType == MethodType.GET) {
                parseUriAndParams();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void parseUriAndParams() {
        if (StringUtils.isEmpty(url))
            return;

        String[] urls = url.split("\\?");
        this.uri = urls[0];
        if (urls.length > 1) {
            Map<String, String> params = Stream.of(urls[1].split("&"))
                    .map(p -> p.split("="))
                    .collect(toMap(x -> x[0], x -> x[1]));
            this.params.putAll(params);
        }
    }

    @Override
    public MethodType getMethodType() {
        return this.methodType;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getUri() {
        return this.uri;
    }

    @Override
    public Map<String, Object> getRequestParams() {
        return params;
    }

}
