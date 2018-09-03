package com.boxuegu.sms;

import com.boxuegu.sms.configuration.CorsConfiguration;
import com.boxuegu.sms.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SMS 内部API服务
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({
        SwaggerConfiguration.class,
        CorsConfiguration.class,
        RootExceptionHandler.class
})
public class SMSAPIDistApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMSAPIDistApplication.class, args);
    }
}
