package com.boxuegu.sms.api;

import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.ChannelConfigService;
import com.boxuegu.sms.service.ChannelTemplateService;
import com.boxuegu.sms.utils.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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


    /**
     * 启用渠道模板，需要其所属的渠道配置已经启用
     */
    @ApiOperation(value = "保存渠道模板", notes = "渠道配置只需要上传其ID即可。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/template")
    public ResponseEntity<String> saveTemplate(@ModelAttribute ChannelTemplateDTO channelTemplateDTO) {
        ResponseEntity<String> res = validateTemplate(channelTemplateDTO);
        if (res != null) return res;

        //在渠道配置删除后，会禁用渠道模板
        //这时，去修改渠道模板（不启用）而不修改关联的渠道配置，则不会允许其进行修改。
        ChannelConfigDTO channelConfigDTO = channelConfigService.config(channelTemplateDTO.getChannelConfig().getId());
        if (null == channelConfigDTO)
            return ResponseEntity.badRequest().body("所关联的渠道配置不存在或已被删除！");

        //在渠道配置禁用后，会禁用渠道模板
        //这时，去启用渠道模板，不会允许其启用，需要先启用对应的渠道配置
        if (null == channelConfigDTO.getStatus()) channelConfigDTO.setStatus(CommonStatus.DISABLE.getStatus());
        if (channelTemplateDTO.getStatus().equals(CommonStatus.ENABLE.getStatus())
                && channelConfigDTO.getStatus().equals(CommonStatus.DISABLE.getStatus()))
            return ResponseEntity.badRequest().body("启用渠道模板需要先启用对应的渠道配置！");

        channelTemplateService.saveTemplate(channelTemplateDTO);
        return ResponseEntity.ok("OK");
    }


    /**
     * 删除渠道模板，需要禁用和其关联的短信服务模板
     */
    @ApiOperation(value = "删除渠道模板")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/template/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("id") Integer id) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定删除参数");
        channelTemplateService.deleteTemplate(id);
        return ResponseEntity.ok("OK");
    }


    /**
     * 启用渠道模板，需要其所属的渠道配置已经启用
     * 禁用渠道模板，需要禁用和其关联的短信服务模板
     */
    @ApiOperation(value = "修改渠道模板", notes = "渠道配置只需要上传其ID即可。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/template/{id}")
    public ResponseEntity<String> updateTemplate(@PathVariable("id") Integer id,
                                                 @ModelAttribute ChannelTemplateDTO channelTemplateDTO) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定更新参数!");
        ResponseEntity<String> res = validateTemplate(channelTemplateDTO);
        if (res != null) return res;

        channelTemplateDTO.setId(id);

        //在渠道配置删除后，会禁用渠道模板
        //这时，去修改渠道模板（不启用）而不修改关联的渠道配置，则不会允许其进行修改。
        ChannelConfigDTO channelConfigDTO = channelConfigService.config(channelTemplateDTO.getChannelConfig().getId());
        if (null == channelConfigDTO)
            return ResponseEntity.badRequest().body("所关联的渠道配置不存在或已被删除！");

        //在渠道配置禁用后，会禁用渠道模板
        //这时，去启用渠道模板，不会允许其启用，需要先启用对应的渠道配置
        if (null == channelConfigDTO.getStatus()) channelConfigDTO.setStatus(CommonStatus.DISABLE.getStatus());
        if (channelTemplateDTO.getStatus().equals(CommonStatus.ENABLE.getStatus())
                && channelConfigDTO.getStatus().equals(CommonStatus.DISABLE.getStatus()))
            return ResponseEntity.badRequest().body("启用渠道模板需要先启用对应的渠道配置！");

        channelTemplateService.updateTemplate(channelTemplateDTO);
        return ResponseEntity.ok("OK");
    }


    @ApiOperation(value = "渠道模板分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "渠道配置ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "code", value = "渠道短信模板code", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class, paramType = "query", allowableValues = "0,1"),
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
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ChannelTemplateDTO> page = channelTemplateService.templates(channelConfigId, name, code, status,
                currentPage, pageSize);
        return ResponseEntity.ok(page);
    }


    @ApiOperation(value = "渠道配置列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigDTO.class, responseContainer = "List")
    })
    @GetMapping("/template/configs")
    public ResponseEntity<List<ChannelConfigDTO>> channelConfigs() {
        return ResponseEntity.ok(channelConfigService.configs());
    }


    private ResponseEntity<String> validateTemplate(ChannelTemplateDTO channelTemplateDTO) {
        if (null == channelTemplateDTO) return ResponseEntity.badRequest().body("参数不能为空！");
        if (!StringUtils.hasText(channelTemplateDTO.getName()))
            return ResponseEntity.badRequest().body("渠道模板名称不能为空！");
        if (!StringUtils.hasText(channelTemplateDTO.getCode()))
            return ResponseEntity.badRequest().body("渠道模板Code不能为空！");
        if (!StringUtils.hasText(channelTemplateDTO.getContent()))
            return ResponseEntity.badRequest().body("渠道模板内容不能为空！");
        if (!CommonStatus.inStatus(channelTemplateDTO.getStatus()))
            return ResponseEntity.badRequest().body("渠道模板状态不能为空！");
        if (null == channelTemplateDTO.getChannelConfig() || null == channelTemplateDTO.getChannelConfig().getId())
            return ResponseEntity.badRequest().body("渠道模板所要关联的渠道配置不能为空！");
        return null;
    }
}
