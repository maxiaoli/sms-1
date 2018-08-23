package com.boxuegu.sms;

import cn.itcast.bxg.common.util.bean.APIResponse;
import com.boxuegu.sms.configuration.CorsConfiguration;
import com.boxuegu.sms.configuration.SwaggerConfiguration;
import org.keycloak.KeycloakPrincipal;
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

        KeycloakPrincipal keycloakPrincipal = null;
        if (null != userPrincipal && userPrincipal instanceof KeycloakPrincipal) {
            keycloakPrincipal = (KeycloakPrincipal)userPrincipal;
            System.out.println(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());
        }
        return APIResponse.newOKResponse("index");
    }

    @GetMapping("/api/user")
    public APIResponse<String> user(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();

        KeycloakPrincipal keycloakPrincipal = null;
        if (null != userPrincipal && userPrincipal instanceof KeycloakPrincipal) {
            keycloakPrincipal = (KeycloakPrincipal)userPrincipal;
            System.out.println(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());
        }
        return APIResponse.newOKResponse("user");
    }

    @GetMapping("/api/admin")
    public APIResponse<String> admin(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();

        KeycloakPrincipal keycloakPrincipal = null;
        if (null != userPrincipal && userPrincipal instanceof KeycloakPrincipal) {
            keycloakPrincipal = (KeycloakPrincipal)userPrincipal;
            System.out.println(keycloakPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());
        }
        return APIResponse.newOKResponse("admin");
    }
}
