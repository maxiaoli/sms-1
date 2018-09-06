package com.boxuegu.sms.api;

import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.domain.dto.ChannelSignatureDTO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.domain.dto.ClientDTO;
import com.boxuegu.sms.domain.dto.TemplateDTO;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.enumeration.TemplateUsage;
import com.boxuegu.sms.service.ChannelSignatureService;
import com.boxuegu.sms.service.ChannelTemplateService;
import com.boxuegu.sms.service.ClientService;
import com.boxuegu.sms.service.TemplateService;
import com.boxuegu.sms.utils.Page;
import com.boxuegu.sms.utils.TemplateIdUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务模板管理接口列表
 *
 * @author leonzhangxf 20180906
 */
@Api(tags = {"服务模板管理"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/service")
public class TemplateAPI {

    private TemplateService templateService;

    private ClientService clientService;

    private ChannelSignatureService channelSignatureService;

    private ChannelTemplateService channelTemplateService;

    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setChannelSignatureService(ChannelSignatureService channelSignatureService) {
        this.channelSignatureService = channelSignatureService;
    }

    @Autowired
    public void setChannelTemplateService(ChannelTemplateService channelTemplateService) {
        this.channelTemplateService = channelTemplateService;
    }


    // TODO
    @ApiOperation(value = "保存服务模板", notes = "新增时，接入方、渠道签名和渠道模板只需要传递对应的ID即可")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/template")
    public ResponseEntity<String> saveTemplate(@ModelAttribute TemplateDTO templateDTO) {
        ResponseEntity<String> res = validateTemplate(templateDTO, true);
        if (res != null) return res;

        return ResponseEntity.ok("OK");
    }


    //TODO
    @ApiOperation(value = "删除服务模板")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/template/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable("id") Integer id) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定删除参数");

        return ResponseEntity.ok("OK");
    }


    //TODO
    @ApiOperation(value = "修改服务模板", notes = "更新时，接入方、渠道签名和渠道模板只需要传递对应的ID即可")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/template/{id}")
    public ResponseEntity<String> updateTemplate(@PathVariable("id") Integer id,
                                                 @ModelAttribute TemplateDTO templateDTO) {
        if (null == id) return ResponseEntity.badRequest().body("缺少指定更新参数");
        templateDTO.setId(id);

        ResponseEntity<String> res = validateTemplate(templateDTO, false);
        if (res != null) return res;

        return ResponseEntity.ok("OK");
    }


    //TODO
    @ApiOperation(value = "服务模板分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "接入方ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "channelSignatureId", value = "渠道签名ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "channelTemplateId", value = "渠道模板ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "templateId", value = "短信服务模板ID", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "usage", value = "用途：1-验证，2-通知，3-推广", dataTypeClass = TemplateUsage.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class, paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path", required = true),
    })
    @GetMapping("/templates/{currentPage}/{pageSize}")
    public ResponseEntity<Page<TemplateDTO>> templates(
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) Integer channelSignatureId,
            @RequestParam(required = false) Integer channelTemplateId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String templateId,
            @RequestParam(required = false) TemplateUsage usage,
            @RequestParam(required = false) Integer status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        return ResponseEntity.ok(null);
    }


    @ApiOperation(value = "生成短信服务模板ID")
    @GetMapping("/template/template-id")
    public ResponseEntity<String> generateTemplateId() {
        return ResponseEntity.ok(TemplateIdUtils.generateTemplateId());
    }


    @ApiOperation(value = "接入方列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ClientDTO.class, responseContainer = "List")
    })
    @GetMapping("/template/service/clients")
    public ResponseEntity<List<ClientDTO>> serviceClients() {
        return ResponseEntity.ok(clientService.clients());
    }


    @ApiOperation(value = "渠道签名列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelSignatureDTO.class, responseContainer = "List")
    })
    @GetMapping("/template/channel/signatures")
    public ResponseEntity<List<ChannelSignatureDTO>> channelSignatures() {
        return ResponseEntity.ok(channelSignatureService.signatures());
    }


    @ApiOperation(value = "渠道模板列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelTemplateDTO.class, responseContainer = "List")
    })
    @GetMapping("/template/channel/templates")
    public ResponseEntity<List<ChannelTemplateDTO>> channelTemplates() {
        return ResponseEntity.ok(channelTemplateService.templates());
    }


    /**
     * 对服务模板的新增、更新操作进行参数校验
     *
     * @param templateDTO 短信服务模板实体
     * @param creation    是否是新增操作校验
     * @return API响应信息，如果校验通过则返回null
     */
    private ResponseEntity<String> validateTemplate(TemplateDTO templateDTO, boolean creation) {
        if (null == templateDTO) return ResponseEntity.badRequest().body("参数不能为空！");
        if (null == templateDTO.getClient() || null == templateDTO.getClient().getId())
            return ResponseEntity.badRequest().body("模板关联的接入方不能为空！");
        if (null == templateDTO.getChannelSignature() || null == templateDTO.getChannelSignature().getId())
            return ResponseEntity.badRequest().body("模板关联的渠道签名不能为空！");
        if (null == templateDTO.getChannelTemplate() || null == templateDTO.getChannelTemplate().getId())
            return ResponseEntity.badRequest().body("模板关联的渠道模板不能为空！");
        if (!StringUtils.hasText(templateDTO.getName()))
            return ResponseEntity.badRequest().body("模板名称不能为空！");
        if (!StringUtils.hasText(templateDTO.getTemplateId()))
            return ResponseEntity.badRequest().body("短信服务模板ID不能为空！");
        if (null == templateDTO.getUsage())
            return ResponseEntity.badRequest().body("模板用途不能为空！");
        if (!CommonStatus.inStatus(templateDTO.getStatus()))
            return ResponseEntity.badRequest().body("接入方状态不能为空！");

        //短信服务模板ID全局唯一（包含删除）
        List<TemplateDTO> existTemplateDTOList = templateService.templatesWithinDeletedByTemplateId(templateDTO.getTemplateId());
        if (creation) {
            if (!CollectionUtils.isEmpty(existTemplateDTOList))
                return ResponseEntity.badRequest().body("短信服务模板ID已存在！短信服务模板ID全局唯一，不能重复！");
        } else {
            if (!CollectionUtils.isEmpty(existTemplateDTOList) && existTemplateDTOList.size() > 1)
                return ResponseEntity.badRequest().body("短信服务模板ID已存在！短信服务模板ID全局唯一，不能重复！");
        }

        return null;
    }
}
