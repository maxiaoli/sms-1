package com.boxuegu.sms;

import cn.itcast.bxg.common.util.bean.APIResponse;
import com.boxuegu.sms.configuration.CorsConfiguration;
import com.boxuegu.sms.configuration.SwaggerConfiguration;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

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
        principal(userPrincipal);

        return APIResponse.newOKResponse("index");
    }

    @GetMapping("/api/user")
    public APIResponse<String> user(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        principal(userPrincipal);

        return APIResponse.newOKResponse("user");
    }

    @GetMapping("/api/admin")
    public APIResponse<String> admin(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        principal(userPrincipal);

        return APIResponse.newOKResponse("admin");
    }

    private void principal(Principal userPrincipal) {
        KeycloakPrincipal keycloakPrincipal;
        if (null != userPrincipal && userPrincipal instanceof KeycloakPrincipal) {
            keycloakPrincipal = (KeycloakPrincipal) userPrincipal;
            AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();

            Set<String> roles = accessToken.getRealmAccess().getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                for (String role : roles) {
                    System.out.println("role: " + role);
                }
            }
            System.out.println(accessToken.getPreferredUsername());
        }
    }
}
