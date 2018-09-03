package com.boxuegu.sms.api;

import com.boxuegu.sms.ChannelConfigService;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigDetailDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigParamsDTO;
import com.boxuegu.sms.enumeration.ChannelConfigParam;
import com.boxuegu.sms.enumeration.ChannelConfigType;
import com.boxuegu.sms.utils.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 渠道（账号）配置管理接口列表
 *
 * @author leonzhangxf 20180903
 */
@Api(tags = {"渠道配置管理"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/channel")
public class ChannelConfigAPI {

    private ChannelConfigService channelConfigService;

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }

    @ApiOperation(value = "保存渠道配置", tags = {"渠道配置管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/config")
    public ResponseEntity<String> saveConfig(@ModelAttribute ChannelConfigDetailDTO channelConfigDetailDTO) {
        ResponseEntity<String> res = validateChannelConfigParams(channelConfigDetailDTO);
        if (res != null) return res;

        channelConfigService.saveChannelConfig(channelConfigDetailDTO);
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "删除渠道配置", tags = {"渠道配置管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/config/{id}")
    public ResponseEntity<String> deleteConfig(@PathVariable("id") Integer id) {
        channelConfigService.deleteChannelConfig(id);
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "修改渠道配置", tags = {"渠道配置管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/config/{id}")
    public ResponseEntity<String> updateConfig(@PathVariable("id") Integer id,
                                               @ModelAttribute ChannelConfigDetailDTO channelConfigDetailDTO) {
        ResponseEntity<String> res = validateChannelConfigParams(channelConfigDetailDTO);
        if (res != null) return res;

        channelConfigDetailDTO.getConfig().setId(id);
        channelConfigService.updateChannelConfig(channelConfigDetailDTO);
        return ResponseEntity.ok("OK");
    }

    @CrossOrigin
    @ApiOperation(value = "渠道配置分页列表", tags = {"渠道配置管理"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "渠道账号配置类型", dataTypeClass = ChannelConfigType.class, paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path", required = true),
    })
    @GetMapping("/configs/{currentPage}/{pageSize}")
    public ResponseEntity<Page<ChannelConfigDTO>> configs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ChannelConfigType type,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        Page<ChannelConfigDTO> channelConfigDTOPage = channelConfigService.channelConfigs(name, type, currentPage, pageSize);

        return ResponseEntity.ok(channelConfigDTOPage);
    }

    @ApiOperation(value = "渠道配置详情", tags = {"渠道配置管理"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigDetailDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "渠道配置ID", dataTypeClass = Integer.class, paramType = "path"),
    })
    @GetMapping("/config/detail/{id}")
    public ResponseEntity<ChannelConfigDetailDTO> configDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(channelConfigService.channelConfigDetail(id));
    }

    /**
     * 对新增，或更新的入口参数进行校验
     *
     * @param channelConfigDetailDTO 渠道配置详情
     * @return API响应信息，如果校验通过则返回null
     */
    private ResponseEntity<String> validateChannelConfigParams(ChannelConfigDetailDTO channelConfigDetailDTO) {
        if (null == channelConfigDetailDTO) return ResponseEntity.badRequest().body("参数不能为空！");

        ChannelConfigDTO channelConfigDTO = channelConfigDetailDTO.getConfig();
        if (null == channelConfigDTO) return ResponseEntity.badRequest().body("渠道配置不能为空！");

        if (!StringUtils.hasText(channelConfigDTO.getName()))
            return ResponseEntity.badRequest().body("渠道配置名称参数不能为空！");
        if (null == channelConfigDTO.getType())
            return ResponseEntity.badRequest().body("渠道配置类型参数不能为空！");

        // 启用检查，对应渠道参数必须配置完全
        if (channelConfigDTO.getStatus().equals(1)) {
            switch (channelConfigDTO.getType()) {
                case ALIYUN: {
                    List<ChannelConfigParamsDTO> params = channelConfigDetailDTO.getParams();
                    if (CollectionUtils.isEmpty(params))
                        return ResponseEntity.badRequest().body("启用前需要配置完全对应渠道所需要的参数！");

                    //参数类型不完全匹配，或去重后参数个数不匹配都不算参数配置完全。
                    if (!params.stream().allMatch((channelConfigParams) -> ChannelConfigParam
                            .getChannelConfigParams(ChannelConfigType.ALIYUN).contains(channelConfigParams.getKey()))
                            ||
                            Long.valueOf(params.stream().distinct().count()).intValue() != ChannelConfigParam
                                    .getChannelConfigParams(ChannelConfigType.ALIYUN).size()) {
                        return ResponseEntity.badRequest().body("启用前需要配置完全对应渠道所需要的参数！");
                    }

                    break;
                }
                default: {
                    return ResponseEntity.badRequest().body("不支持的渠道配置类型！");
                }
            }
        }

        return null;
    }
}
