package com.boxuegu.sms.api;

import com.boxuegu.sms.ChannelConfigService;
import com.boxuegu.sms.ChannelTemplateService;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.utils.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道模板管理接口列表
 *
 * @author leonzhangxf 20180904
 */
@Api(tags = {"渠道模板管理"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/channel")
public class ChannelTemplateAPI {

    private ChannelTemplateService channelTemplateService;

    private ChannelConfigService channelConfigService;

    @Autowired
    public void setChannelTemplateService(ChannelTemplateService channelTemplateService) {
        this.channelTemplateService = channelTemplateService;
    }

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }

    //TODO
    @ApiOperation(value = "保存渠道模板", tags = {"渠道模板管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/template")
    public ResponseEntity<String> saveTemplate(@ModelAttribute ChannelTemplateDTO channelTemplateDTO) {

        return ResponseEntity.ok("OK");
    }


    //TODO
    @ApiOperation(value = "删除渠道模板", tags = {"渠道模板管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/template/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("id") Integer id) {
        return ResponseEntity.ok("OK");
    }


    //TODO
    @ApiOperation(value = "修改渠道模板", tags = {"渠道模板管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/template/{id}")
    public ResponseEntity<String> updateTemplate(@PathVariable("id") Integer id,
                                               @ModelAttribute ChannelTemplateDTO channelTemplateDTO) {
        return ResponseEntity.ok("OK");
    }


    @ApiOperation(value = "渠道模板分页列表", tags = {"渠道模板管理"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "渠道配置ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "渠道短信模板code", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path", required = true),
    })
    @GetMapping("/templates/{currentPage}/{pageSize}")
    public ResponseEntity<Page<ChannelTemplateDTO>> templates(
            @RequestParam(value = "configId", required = false) Integer channelConfigId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Integer status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        Page<ChannelTemplateDTO> page = channelTemplateService.channelTemplates(channelConfigId, name, code, status, currentPage, pageSize);
        return ResponseEntity.ok(page);
    }


    @ApiOperation(value = "渠道配置列表", tags = {"渠道模板管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigDTO.class, responseContainer = "List")
    })
    @GetMapping("/template/configs")
    public ResponseEntity<List<ChannelConfigDTO>> channelConfigs() {
        return ResponseEntity.ok(channelConfigService.channelConfigs());
    }
}
