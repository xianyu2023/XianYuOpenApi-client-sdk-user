package com.xianyu.xianyuopenapiclientsdk;

import com.xianyu.xianyuopenapiclientsdk.client.XianYuOpenApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author happyxianfish
 */
@Configuration
@ConfigurationProperties("xianyu.client")
@ComponentScan
@Data
public class XianYuOpenApiClientConfig {
    private String accessKey;
    private String secretKey;

    /**
     * 获取SDK客户端
     * @return
     */
    @Bean
    public XianYuOpenApiClient getXianYuOpenApiClient() {
        return new XianYuOpenApiClient(accessKey,secretKey);
    }
}
