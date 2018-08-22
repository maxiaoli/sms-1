package com.boxuegu.sms;

import cn.itcast.bxg.common.util.bean.APIResponse;
import com.boxuegu.sms.configuration.CorsConfiguration;
import com.boxuegu.sms.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@SpringBootApplication
@Import({
        SwaggerConfiguration.class,
        CorsConfiguration.class
})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class
})
@RestController
public class SMSAPIDistApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMSAPIDistApplication.class, args);
    }

    @GetMapping("/api/index")
    public APIResponse<String> index(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        System.out.println(userPrincipal);
        return APIResponse.newOKResponse("index");
    }
}
