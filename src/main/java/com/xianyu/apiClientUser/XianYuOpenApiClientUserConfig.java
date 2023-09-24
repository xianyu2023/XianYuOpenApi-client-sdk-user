package com.xianyu.apiClientUser;


import com.xianyu.apiClientUser.client.XianYuOpenApiClientUser;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author happyxianfish
 */
@Configuration
@ConfigurationProperties("xianyu.client.user")
@ComponentScan
@Data
public class XianYuOpenApiClientUserConfig {
    private String accessKey;
    private String secretKey;

    /**
     * 获取SDK客户端
     * @return
     */
    @Bean
    public XianYuOpenApiClientUser getXianYuOpenApiClientUser() {
        return new XianYuOpenApiClientUser(accessKey,secretKey);
    }
}
