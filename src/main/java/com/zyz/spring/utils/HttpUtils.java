package com.zyz.spring.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

/********
 * @author: yidazyzhou
 */
@Slf4j
@Component
public class HttpUtils {
    public long timeStamp = 0;

    public JSONObject sendGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("accept", "*/*");
        httpGet.setHeader("connection", "Keep-Alive");
        httpGet.setHeader("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return executeHttp(httpGet);
    }

    public JSONObject doDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("accept", "*/*");
        httpDelete.setHeader("connection", "Keep-Alive");
        httpDelete.setHeader("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return executeHttp(httpDelete);
    }

    public JSONObject doPutForm(String url, List<Map<String, Object>> params) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("accept", "*/*");
        httpPut.setHeader("connection", "Keep-Alive");
        httpPut.setHeader("Content-Type", "application/json");
        httpPut.setHeader("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        HttpEntity entity;
        try {
            JSONObject jsonObj = new JSONObject();
            if (params != null && params.size() > 0) {
                for (Map<String, Object> param : params) {
                    String name = param.get("name") == null ? "name" : param.get("name").toString();
                    String value = param.get("value") == null ? "" : param.get("value").toString();
                    jsonObj.put(name, value);
                }
            }
            entity = new StringEntity(jsonObj.toString());
            httpPut.setEntity(entity);
            return executeHttp(httpPut);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new JSONObject();
    }

    public JSONObject doPut(String url, String body) {
        HttpPut httpPut = new HttpPut(url);
        return sendHttp(httpPut, body);
    }

    public JSONObject doPost(String url, String body) {
        HttpPost httppost = new HttpPost(url);
        return sendHttp(httppost, body);
    }

    public JSONObject httpClientForm(String postUrl, List<Map<String, Object>> params) {
        HttpPost httppost = new HttpPost(postUrl);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (params != null && params.size() > 0) {

            for (Map<String, Object> param : params) {
                if (param.get("type") != null && "file".equals(param.get("type").toString())) {
                    FileBody fb = new FileBody((File) param.get("value"));
                    builder.addPart(param.get("name").toString(), fb);
                    builder.addBinaryBody(param.get("name").toString(), (File) param.get("value"));
                } else {
                    builder.addTextBody(param.get("name").toString(), param.get("value").toString());
                }
            }

        }
        httppost.setEntity(builder.build());
        return executeHttp(httppost);
    }

    private JSONObject sendHttp(HttpEntityEnclosingRequestBase httpRequestBase, String body) {
        HttpEntity entity;
        entity = new StringEntity(body, "UTF-8");
        httpRequestBase.setEntity(entity);
        return executeHttp(httpRequestBase);
    }

    private JSONObject executeHttp(HttpRequestBase httpRequestBase) {
        JSONObject jo = new JSONObject();
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpResponse httpResponse = httpclient.execute(httpRequestBase);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);
            HttpEntity httpEntity = httpResponse.getEntity();
            String resStr = getResult(httpEntity);
            jo.put("httpCode", statusCode);
            jo.put("result", resStr);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return jo;
    }

    private String getResult(HttpEntity httpEntity) throws IOException {
        StringBuilder resStr = new StringBuilder();
        if (httpEntity != null) {
            InputStream inStreams = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStreams));
            String line;
            while ((line = reader.readLine()) != null) {
                resStr.append(line);
            }

        }
        return resStr.toString();
    }

    public String decodeUnicode(final String dataStr) {
        int start = 0;
        int end;
        final StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr;
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(Character.toString(letter));
            start = end;
        }
        return buffer.toString();
    }
}
