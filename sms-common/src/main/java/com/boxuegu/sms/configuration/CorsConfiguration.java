package com.boxuegu.sms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The server cross origin support configuration.
 *
 * @author leonzhangxf 20180808
 */
@ConfigurationProperties("spring.cors")
public class CorsConfiguration implements WebMvcConfigurer {

    /**
     * 支持的关于访问域名配置
     * <p>
     * 默认支持所有域名跨域访问
     */
    private List<String> allowOriginList = new ArrayList<>(Collections.singletonList("*"));

    /**
     * 默认支持所有头跨域访问
     */
    private List<String> allowedOriginHeaders = new ArrayList<>(Collections.singletonList("*"));

    /**
     * 默认支持四种请求方式
     */
    private List<String> allowedOriginMethods = new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    /**
     * 添加跨域配置
     * <p>
     * Configure cross origin requests processing.
     *
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/**");
        corsRegistration.allowedOrigins(allowOriginList.toArray(new String[]{}))
                .allowedHeaders(allowedOriginHeaders.toArray(new String[]{}))
                .allowedMethods(allowedOriginMethods.toArray(new String[]{}));
    }

    public List<String> getAllowOriginList() {
        return allowOriginList;
    }

    public void setAllowOriginList(List<String> allowOriginList) {
        this.allowOriginList = allowOriginList;
    }

    public List<String> getAllowedOriginHeaders() {
        return allowedOriginHeaders;
    }

    public void setAllowedOriginHeaders(List<String> allowedOriginHeaders) {
        this.allowedOriginHeaders = allowedOriginHeaders;
    }

    public List<String> getAllowedOriginMethods() {
        return allowedOriginMethods;
    }

    public void setAllowedOriginMethods(List<String> allowedOriginMethods) {
        this.allowedOriginMethods = allowedOriginMethods;
    }
}
