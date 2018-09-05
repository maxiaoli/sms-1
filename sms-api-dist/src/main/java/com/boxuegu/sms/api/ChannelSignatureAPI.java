package com.boxuegu.sms.api;

import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelSignatureDTO;
import com.boxuegu.sms.service.ChannelConfigService;
import com.boxuegu.sms.service.ChannelSignatureService;
import com.boxuegu.sms.utils.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道签名管理接口列表
 *
 * @author leonzhangxf 20180905
 */
@Api(tags = {"渠道签名管理"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/channel")
public class ChannelSignatureAPI {

    private ChannelSignatureService channelSignatureService;

    private ChannelConfigService channelConfigService;

    @Autowired
    public void setChannelSignatureService(ChannelSignatureService channelSignatureService) {
        this.channelSignatureService = channelSignatureService;
    }

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }


    /**
     * TODO
     * 启用渠道签名，需要其所属的渠道配置已经启用
     */
    @ApiOperation(value = "保存渠道签名", tags = {"渠道签名管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/signature")
    public ResponseEntity<String> saveTemplate(@ModelAttribute ChannelSignatureDTO channelSignatureDTO) {

        //在渠道配置删除后，会禁用渠道签名
        //这时，去新增渠道签名（不启用）而不修改关联的渠道配置，则不会允许其进行修改。
        ChannelConfigDTO channelConfigDTO = channelConfigService.channelConfig(channelSignatureDTO.getChannelConfig().getId());
        if (null == channelConfigDTO)
            return ResponseEntity.badRequest().body("所关联的渠道配置不存在或已被删除！");

        //在渠道配置禁用后，会禁用渠道签名
        //这时，去启用渠道签名，不会允许其启用，需要先启用对应的渠道配置
        if (null == channelConfigDTO.getStatus()) channelConfigDTO.setStatus(0);
        if (channelSignatureDTO.getStatus().equals(1) && channelConfigDTO.getStatus().equals(0))
            return ResponseEntity.badRequest().body("启用渠道签名需要先启用对应的渠道配置！");

        return ResponseEntity.ok("OK");
    }


    /**
     * TODO
     * 删除渠道签名，需要禁用和其关联的短信服务模板
     */
    @ApiOperation(value = "删除渠道签名", tags = {"渠道签名管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/signature/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("id") Integer id) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定删除参数");
        return ResponseEntity.ok("OK");
    }


    /**
     * TODO
     * 启用渠道签名，需要其所属的渠道配置已经启用
     * 禁用渠道签名，需要禁用和其关联的短信服务模板
     */
    @ApiOperation(value = "修改渠道签名", tags = {"渠道签名管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/signature/{id}")
    public ResponseEntity<String> updateTemplate(@PathVariable("id") Integer id,
                                                 @ModelAttribute ChannelSignatureDTO channelSignatureDTO) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定更新参数");

        return ResponseEntity.ok("OK");
    }

    //TODO
    @ApiOperation(value = "渠道签名分页列表", tags = {"渠道签名管理"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "渠道配置ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "签名", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path", required = true),
    })
    @GetMapping("/signatures/{currentPage}/{pageSize}")
    public ResponseEntity<Page<ChannelSignatureDTO>> templates(
            @RequestParam(value = "configId", required = false) Integer channelConfigId,
            @RequestParam(required = false) String signature,
            @RequestParam(required = false) Integer status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        return ResponseEntity.ok(null);
    }


    @ApiOperation(value = "渠道配置列表", tags = {"渠道签名管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigDTO.class, responseContainer = "List")
    })
    @GetMapping("/signature/configs")
    public ResponseEntity<List<ChannelConfigDTO>> channelConfigs() {
        return ResponseEntity.ok(channelConfigService.channelConfigs());
    }
}
