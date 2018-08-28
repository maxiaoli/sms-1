package com.boxuegu.sms;

import cn.itcast.bxg.common.util.bean.APIResponse;
import com.boxuegu.sms.configuration.CorsConfiguration;
import com.boxuegu.sms.configuration.SwaggerConfiguration;
import com.boxuegu.sms.dao.mapper.ClientMapper;
import com.boxuegu.sms.domain.ClientDO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@Import({
        SwaggerConfiguration.class,
        CorsConfiguration.class
})
@Api(tags = {"测试API"})
@RestController
public class SMSAPIDistApplication {

    public static void main(String[] args) {
        SpringApplication.run(SMSAPIDistApplication.class, args);
    }

    private ClientMapper clientMapper;

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @ApiOperation(value = "获取所有调用方")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "当前页", dataTypeClass = Integer.class, paramType = "path", required = true),
            @ApiImplicitParam(name = "size", value = "每页数", dataTypeClass = Integer.class, paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ClientDO.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal error message", response = String.class),
    })
    @GetMapping("/api/clients/{num}/{size}")
    public ResponseEntity<List<ClientDO>> clients(@PathVariable("num") Integer num,
                                                  @PathVariable("size") Integer size) {
        if (null != num || null != size) {
            if (null == num) num = 1;
            if (null == size) size = 2;
            PageHelper.startPage(num, size);
        }

        List<ClientDO> clientDOs = clientMapper.selectByExample(null);

        return ResponseEntity.ok(clientDOs);
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
