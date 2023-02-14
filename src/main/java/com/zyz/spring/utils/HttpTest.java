package com.zyz.spring.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.*;
import org.json.CDL;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.alibaba.fastjson.util.IOUtils.DIGITS;

public class HttpTest {
    public static final String APP_SECRET = "CAE2677FDB4389744233E6B1A5AEC5E7";

    @Test
    public void test_HttpUtil() throws IOException {
        HttpUtils httpUtils = new HttpUtils();
        net.sf.json.JSONObject jsonObject = httpUtils.sendGet("http://127.0.0.1:8080/demo/hello");
        System.out.println("dada");
        System.out.println("hhh");
    }

    @Test
    public void test_okHttp() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"dataflowName\": \"wx_qcloud_wx_ordersrv_msa\"\n}");
        Request request = new Request.Builder()
                .url("http://openapi.zhiyan.oa.com/log/v2/search/fields")
                .method("POST", body)
                .addHeader("token", "1aff28fb878dda84b82fae5538242d8b")
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "x-client-ssid=1e11630a:018a4f3d2bb0:0f7a0d; x_host_key_access=be5202e768843dc3ef4d806b250615a1b7f79698_s")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray data = (JSONArray) jsonObject.get("data");
        System.out.println("lalalala");
    }

    @Test
    public void test_okHttp_shuazhang() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String content = getString(); // 包装请求body
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://open.wecard.qq.com/cgi-bin/pay/inner-provider/palm/wechat-analysis")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = Objects.requireNonNull(response.body()).string();
        extracted(responseBody); // 处理接口返回数据
    }

    private void extracted(String responseBody) throws IOException {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray data = (JSONArray) jsonObject.get("data");
        FileUtils.writeStringToFile(new File("/Users/yidazyz/Downloads/zyzyida/zyz.xlsx"), CDL.toString(data),"UTF-8",true);
        System.out.println("成功生成CSV文件！");
    }

    private String getString() {
        String org_id = "2000100004";
        String type = "device";
        String nonce = "12321";
        String start_date = "20230701";
        String end_date = "20230730";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String app_key = "BD42E31CD5A67D5B";
        Map<String, String> map = new HashMap<>();
        map.put("org_id", org_id);
        map.put("type", type);
        map.put("nonce", nonce);
        map.put("start_date", start_date);
        map.put("end_date", end_date);
        map.put("timestamp", timestamp);
        map.put("app_key", app_key);
        map.put("signature", calSign(map));
        return JSON.toJSONString(map);
    }

    /**
     * 计算签名
     *
     * @param map
     * @return
     */
    public static String calSign(Map<String, String> map) {
        String str = "";
        Collection<String> keyset = map.keySet();
        List<String> list = new ArrayList<String>(keyset);

        //对key键值按字典升序排序
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            str += list.get(i) + "=" + map.get(list.get(i)) + "&";
        }

        str += "key=" + APP_SECRET;
        System.out.println(str);
        return md5Encode(str).toUpperCase();
    }

    /**
     * MD5
     *
     * @param srcString
     * @return
     */
    public final static String md5Encode(String srcString) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = srcString.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(strTemp);
            byte[] mdByte = messageDigest.digest();
            int j = mdByte.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = mdByte[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String md5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        try {
            msgDigest.update(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("System doesn't support your EncodingException.");

        }

        byte[] bytes = msgDigest.digest();
        return new String(encodeHex(bytes));
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
