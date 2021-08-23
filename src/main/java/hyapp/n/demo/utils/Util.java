package hyapp.n.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author Wang Zhen
 * @date 2020/7/24 10:38 上午
 */
@Component
@Slf4j
public class Util {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * token过期时间，单位为ms
     */
    private static long expireTime = 10 * 60 * 1000;

    /**
     * redis过期时间，比token过期短1min，注意单位为s
     */
    private static long redisExpireTime = 9 * 60;

    /**
     * 生效请求jwt凭证
     * @param appId  开发者ID
     * @param secret  开发者密钥
     * @param extId  小程序uuid
     * @param profileId  主播unionId（通过hyext.request接口请求到后台的Header里获取，详情可以看：http://dev.huya.com/docs#/sdk/hyExt.request）
     * @return
     */
    public static String getApiextJwtString(String appId, String secret, String extId, String profileId){
        //获取时间戳（毫秒）
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = System.currentTimeMillis() + expireTime;  //超时时间:通常设置10分钟有效，即exp=iat+600，注意不少于当前时间且不超过当前时间60分钟
        Date iat = new Date(currentTimeMillis);
        Date exp = new Date(expireTimeMillis);

        try {

            Map<String, Object> header = new HashMap<String, Object>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");

            //生成JWT凭证
            Algorithm algorithm = Algorithm.HMAC256(secret);   //开发者密钥
            String sToken = JWT.create()
                    .withHeader(header)                    //JWT声明
                    .withIssuedAt(iat)                     //jwt凭证生成时间
                    .withExpiresAt(exp)                    //jwt凭证超时时间
                    .withClaim("appId", appId)             //开发者ID
                    .withClaim("extId", extId)             //小程序ID
                    .withClaim("creator", "DEV")           //创建者（token生成方：SYS平台，DEV开发者）
                    .withClaim("profileId", profileId)             //开发者ID
                    .sign(algorithm);

            return sToken;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * POST请求
     * @param url
     * @param headerMap
     * @param paramMap
     * @return
     */
    public static String sendPost(String url, Map<String, Object> headerMap, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(3000)
                .build();
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json");
        // 封装header参数
        if (null != headerMap && headerMap.size() > 0) {
            Set<Map.Entry<String, Object>> entrySet = headerMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                httpPost.addHeader(mapEntry.getKey(), mapEntry.getValue().toString());
            }
        }

        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            String bodyJson = JSONObject.toJSONString(paramMap);
            // 为httpPost设置封装好的请求参数，使用json字符串方式传参
            httpPost.setEntity(new StringEntity(bodyJson, "UTF-8"));
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public String postEventAndMessageByProfileId(String profileId, String event, String message) {
        String appId = "vb14674090674c43";      //小程序开发者ID（成为开发者后，https://ext.huya.com可查）
        String extId = "pphevxqc";              //小程序ID（创建小程序后，https://ext.huya.com可查）
        String secret = "f0234d9ace3d1cdcc5a35c7e676465b0";     //小程序开发者密钥（成为开发者后，https://ext.huya.com可查）

        //设置header参数
        Map<String, Object> headerMap = new HashMap<String, Object>();
        StringBuilder token = new StringBuilder();
        if(!redisUtil.hasKey(profileId)) {
            token.append(getApiextJwtString(appId, secret, extId, profileId));
            redisUtil.set(profileId, token, redisExpireTime);
        } else {
            token.append(redisUtil.get(profileId));
        }
        headerMap.put("authorization", token.toString());

        //设置body参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("profileId", profileId);
        paramMap.put("event", event);
        paramMap.put("message", message);

        String url = "https://apiext.huya.com/message/deliverByProfileId?appId="+appId+"&extId=" + extId;
        String resString = sendPost(url, headerMap, paramMap);
        return resString;
    }
}
