package com.xianyu.xianyuopenapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xianyu.xianyuopenapiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.xianyu.xianyuopenapiclientsdk.utils.SignUtils.genSign;

/**
 * 调用接口的客户端（项目内部）
 * 发出HTTP请求调用模拟的接口
 *
 * @author happyxianfish
 */
public class XianYuOpenApiClient {
    private String accessKey;
    private String secretKey;

    private static final String GATEWAY_HOST = "http://localhost:8090";

    public XianYuOpenApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/url", paramMap);
        System.out.println(result);
        return result;
    }

    /**
     * 实现API签名认证
     * 存ak、sk请求头
     * 请求头是一组k-v的结构
     * 而map也是一组k-v的结构
     * 所以在设置请求头时，可以传入map来设置请求头
     *
     * @return
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        //实现标准的API签名认证需6个参数，其中密钥参数一定不能直接发送
        hashMap.put("accessKey", accessKey);
//        hashMap.put("secretKey",secretKey);
        //body：用户请求参数
        hashMap.put("body", body);
        //生成100以内的随机数（偷懒模拟下）
        hashMap.put("nonce", RandomUtil.randomNumbers(5));
        //时间戳.（偷懒模拟下）（String.valueOf转字符串）  System.currentTimeMillis() / 1000 ???
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        //签名。使用签名生成算法生成签名
        hashMap.put("sign", genSign(body, secretKey));
        return hashMap;
    }

    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        //todo 这里发送http请求时，请求参数user不支持中文，会乱码
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/json")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
