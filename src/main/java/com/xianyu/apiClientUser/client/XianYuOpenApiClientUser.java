package com.xianyu.apiClientUser.client;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import java.util.HashMap;
import java.util.Map;

import static com.xianyu.apiClientUser.utils.SignUtils.genSign;

/**
 * 调用接口的客户端（项目内部）
 * 发出HTTP请求调用模拟的接口
 *
 * @author happyxianfish
 */
public class XianYuOpenApiClientUser {
    private String accessKey;
    private String secretKey;

    private static final String API_BACKEND_HOST = "http://localhost:7529";

    public XianYuOpenApiClientUser(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    /**
     * API签名认证
     * 存ak、sign、body请求头
     * 请求头是一组k-v的结构，而map也是一组k-v的结构
     * 所以在设置请求头时，可以传入map来设置请求头
     * @return
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> hashMap = new HashMap<>();
        //实现标准的API签名认证需6个参数，其中密钥参数一定不能直接发送
        hashMap.put("accessKey", accessKey);
//        hashMap.put("secretKey",secretKey);
        //签名。使用签名生成算法生成签名
        hashMap.put("sign", genSign(body, secretKey));
//        //body：用户请求参数
//        hashMap.put("body", body);
        return hashMap;
    }

    /**
     *
     * @param apiID 想调用的接口id
     * @param userParameter 用户请求参数
     * @return
     */
//    public String getApiService(Long apiID,String userParameter) {
//        //Hutool发送http请求给后端，经后端发请求给网关去调用接口资源
//        String id = JSONUtil.toJsonStr(apiID);
//        String json = JSONUtil.toJsonStr(userParameter);
//        //todo 这里发送http请求时，请求参数user不支持中文，会乱码
//        HttpResponse httpResponse = HttpRequest.post(API_BACKEND_HOST)
//                .addHeaders(getHeaderMap(json))
//                .body(id)
//                .execute();
//        System.out.println(httpResponse.getStatus());
//        String result = httpResponse.body();
//        System.out.println(result);
//        return result;
//    }

    /**
     * 向API开放平台的invokeOpenApi方法发送POST请求【id、userRequestParams、ak、sign】
     * @param id 想调用的接口id
     * @param userRequestParams 用户请求参数
     * @return
     */
    public String getApiService(Long id,String userRequestParams) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("userRequestParams", userRequestParams);
        String jsonStr = JSONUtil.toJsonStr(paramMap);
        HttpResponse httpResponse = HttpRequest.post(API_BACKEND_HOST + "/api/openApi/invoke")
                .addHeaders(getHeaderMap(userRequestParams))
                .body(jsonStr)
                .execute();
        String result = httpResponse.body();
        System.out.println(httpResponse.getStatus());
        System.out.println(result);
        return result;
    }
}
