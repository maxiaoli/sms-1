package com.boxuegu.sms.api;

import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelSignatureDTO;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.ChannelConfigService;
import com.boxuegu.sms.service.ChannelSignatureService;
import com.boxuegu.sms.utils.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
     * 启用渠道签名，需要其所属的渠道配置已经启用
     */
    @ApiOperation(value = "保存渠道签名", notes = "渠道配置只需要上传其ID即可。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/signature")
    public ResponseEntity<String> saveSignature(@ModelAttribute ChannelSignatureDTO channelSignatureDTO) {
        ResponseEntity<String> res = validateSignature(channelSignatureDTO);
        if (res != null) return res;

        //在渠道配置删除后，会禁用渠道签名
        //这时，去新增渠道签名（不启用）而不修改关联的渠道配置，则不会允许其进行修改。
        ChannelConfigDTO channelConfigDTO = channelConfigService.channelConfig(channelSignatureDTO.getChannelConfig().getId());
        if (null == channelConfigDTO)
            return ResponseEntity.badRequest().body("所关联的渠道配置不存在或已被删除！");

        //在渠道配置禁用后，会禁用渠道签名
        //这时，去启用渠道签名，不会允许其启用，需要先启用对应的渠道配置
        if (null == channelConfigDTO.getStatus()) channelConfigDTO.setStatus(CommonStatus.DISABLE.getStatus());
        if (channelSignatureDTO.getStatus().equals(CommonStatus.ENABLE.getStatus())
                && channelConfigDTO.getStatus().equals(CommonStatus.DISABLE.getStatus()))
            return ResponseEntity.badRequest().body("启用渠道签名需要先启用对应的渠道配置！");

        channelSignatureService.saveSignature(channelSignatureDTO);
        return ResponseEntity.ok("OK");
    }


    /**
     * TODO
     * 删除渠道签名，需要禁用和其关联的短信服务模板
     */
    @ApiOperation(value = "删除渠道签名")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/signature/{id}")
    public ResponseEntity<String> deleteSignature(@PathVariable("id") Integer id) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定删除参数");
        channelSignatureService.deleteSignature(id);
        return ResponseEntity.ok("OK");
    }


    /**
     * TODO
     * 启用渠道签名，需要其所属的渠道配置已经启用
     * 禁用渠道签名，需要禁用和其关联的短信服务模板
     */
    @ApiOperation(value = "修改渠道签名", notes = "渠道配置只需要上传其ID即可。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/signature/{id}")
    public ResponseEntity<String> updateSignature(@PathVariable("id") Integer id,
                                                  @ModelAttribute ChannelSignatureDTO channelSignatureDTO) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定更新参数");

        channelSignatureDTO.setId(id);

        //在渠道配置删除后，会禁用渠道签名
        //这时，去修改渠道签名（不启用）而不修改关联的渠道配置，则不会允许其进行修改。
        ChannelConfigDTO channelConfigDTO = channelConfigService.channelConfig(channelSignatureDTO.getChannelConfig().getId());
        if (null == channelConfigDTO)
            return ResponseEntity.badRequest().body("所关联的渠道配置不存在或已被删除！");

        //在渠道配置禁用后，会禁用渠道签名
        //这时，去启用渠道签名，不会允许其启用，需要先启用对应的渠道配置
        if (null == channelConfigDTO.getStatus()) channelConfigDTO.setStatus(CommonStatus.DISABLE.getStatus());
        if (channelSignatureDTO.getStatus().equals(CommonStatus.ENABLE.getStatus())
                && channelConfigDTO.getStatus().equals(CommonStatus.DISABLE.getStatus()))
            return ResponseEntity.badRequest().body("启用渠道签名需要先启用对应的渠道配置！");

        channelSignatureService.updateSignature(channelSignatureDTO);
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "渠道签名分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "渠道配置ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "签名", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class, paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path", required = true),
    })
    @GetMapping("/signatures/{currentPage}/{pageSize}")
    public ResponseEntity<Page<ChannelSignatureDTO>> signatures(
            @RequestParam(value = "configId", required = false) Integer channelConfigId,
            @RequestParam(required = false) String signature,
            @RequestParam(required = false) Integer status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        Page<ChannelSignatureDTO> page = channelSignatureService.signatures(channelConfigId, signature, status, currentPage, pageSize);
        return ResponseEntity.ok(page);
    }


    @ApiOperation(value = "渠道配置列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigDTO.class, responseContainer = "List")
    })
    @GetMapping("/signature/configs")
    public ResponseEntity<List<ChannelConfigDTO>> channelConfigs() {
        return ResponseEntity.ok(channelConfigService.channelConfigs());
    }


    private ResponseEntity<String> validateSignature(ChannelSignatureDTO channelSignatureDTO) {
        if (null == channelSignatureDTO) return ResponseEntity.badRequest().body("参数不能为空！");
        if (!StringUtils.hasText(channelSignatureDTO.getSignature()))
            return ResponseEntity.badRequest().body("渠道签名的签名不能为空！");
        if (!CommonStatus.inStatus(channelSignatureDTO.getStatus()))
            return ResponseEntity.badRequest().body("渠道签名状态不能为空！");
        if (null == channelSignatureDTO.getChannelConfig() || null == channelSignatureDTO.getChannelConfig().getId())
            return ResponseEntity.badRequest().body("渠道签名所要关联的渠道配置不能为空！");
        return null;
    }
}
