package com.boxuegu.sms.service.impl;

import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.dao.ChannelTemplateDao;
import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.ChannelConfigService;
import com.boxuegu.sms.service.ChannelTemplateService;
import com.boxuegu.sms.service.TemplateService;
import com.boxuegu.sms.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 渠道模板 Service
 *
 * @author leonzhangxf 20180904
 */
@Service
public class ChannelTemplateServiceImpl implements ChannelTemplateService {

    private ChannelTemplateDao channelTemplateDao;

    private ChannelConfigService channelConfigService;

    private TemplateService templateService;

    @Autowired
    public void setChannelTemplateDao(ChannelTemplateDao channelTemplateDao) {
        this.channelTemplateDao = channelTemplateDao;
    }

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }

    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    @Transactional
    public void saveTemplate(ChannelTemplateDTO channelTemplateDTO) {
        if (null == channelTemplateDTO || null == channelTemplateDTO.getChannelConfig()
                || null == channelTemplateDTO.getChannelConfig().getId())
            return;

        ChannelTemplateDO channelTemplateDO = ChannelTemplateDTO.convertToChannelTemplateDO(channelTemplateDTO);
        if (null == channelTemplateDO) return;

        channelTemplateDao.saveTemplate(channelTemplateDO);
    }


    @Override
    @Transactional
    public void deleteTemplate(Integer id) {
        if (null == id) return;

        //1.删除渠道模板
        ChannelTemplateDO channelTemplateDO = channelTemplateDao.template(id);
        if (null == channelTemplateDO) return;

        channelTemplateDao.deleteTemplate(channelTemplateDO.getId());

        //2.删除渠道模板，需要禁用和其关联的短信服务模板。
        templateService.updateTemplateStatusByChannelTemplateId(channelTemplateDO.getId(), CommonStatus.DISABLE.getStatus());
    }


    @Override
    @Transactional
    public void updateTemplate(ChannelTemplateDTO channelTemplateDTO) {
        if (null == channelTemplateDTO || null == channelTemplateDTO.getId()
                || null == channelTemplateDTO.getChannelConfig()
                || null == channelTemplateDTO.getChannelConfig().getId()
                || !StringUtils.hasText(channelTemplateDTO.getName())
                || !StringUtils.hasText(channelTemplateDTO.getCode())
                || !StringUtils.hasText(channelTemplateDTO.getContent()))
            return;

        //1.更新渠道模板
        ChannelTemplateDO channelTemplateDO = ChannelTemplateDTO.convertToChannelTemplateDO(channelTemplateDTO);
        if (null == channelTemplateDO) return;

        channelTemplateDao.updateTemplate(channelTemplateDO);

        //2.禁用渠道模板，需要禁用和其关联的短信服务模板。
        templateService.updateTemplateStatusByChannelTemplateId(channelTemplateDO.getId(), CommonStatus.DISABLE.getStatus());
    }


    @Override
    @Transactional
    public void updateTemplateStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus) {
        if (null == channelConfigId || !CommonStatus.inStatus(targetStatus)) return;

        channelTemplateDao.updateTemplateStatusByChannelConfigId(channelConfigId, targetStatus);

        if (targetStatus.equals(CommonStatus.DISABLE.getStatus())) {
            List<ChannelTemplateDO> channelTemplateDOList = channelTemplateDao.templates(channelConfigId);
            if (!CollectionUtils.isEmpty(channelTemplateDOList)) {
                List<Integer> channelTemplateDOIdList = channelTemplateDOList.stream()
                        .map(ChannelTemplateDO::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(channelTemplateDOIdList)) {
                    templateService.updateTemplateStatusByChannelTemplateIdList(channelTemplateDOIdList, CommonStatus.DISABLE.getStatus());
                }
            }
        }
    }


    @Override
    @Transactional
    public Page<ChannelTemplateDTO> templates(Integer channelConfigId, String name, String code, Integer status,
                                              Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ChannelTemplateDO> channelTemplateDOPage = channelTemplateDao
                .templates(channelConfigId, name, code, status, currentPage, pageSize);

        List<ChannelTemplateDTO> channelTemplateDTOList = new ArrayList<>();
        if (null == channelTemplateDOPage || CollectionUtils.isEmpty(channelTemplateDOPage.getItems()))
            return new Page<>(channelTemplateDTOList, 0, pageSize, currentPage);

        for (ChannelTemplateDO channelTemplateDO : channelTemplateDOPage.getItems()) {
            ChannelConfigDTO channelConfigDTO = channelConfigService.configWithinDeleted(channelTemplateDO.getChnlConfigId());
            ChannelTemplateDTO channelTemplateDTO = ChannelTemplateDTO.convertChannelTemplateDO(channelTemplateDO, channelConfigDTO);
            channelTemplateDTOList.add(channelTemplateDTO);
        }

        return new Page<>(channelTemplateDTOList, channelTemplateDOPage.getTotalCount(), pageSize, currentPage);
    }

    @Override
    @Transactional
    public List<ChannelTemplateDTO> templates() {
        List<ChannelTemplateDO> channelTemplateDOList = channelTemplateDao.templates();
        if (CollectionUtils.isEmpty(channelTemplateDOList)) return null;

        List<ChannelTemplateDTO> list = new ArrayList<>();
        for (ChannelTemplateDO channelTemplateDO : channelTemplateDOList) {
            ChannelConfigDTO channelConfigDTO = channelConfigService.configWithinDeleted(channelTemplateDO.getChnlConfigId());
            ChannelTemplateDTO channelTemplateDTO = ChannelTemplateDTO.convertChannelTemplateDO(channelTemplateDO, channelConfigDTO);
            list.add(channelTemplateDTO);
        }
        return list;
    }

    @Override
    @Transactional
    public ChannelTemplateDTO template(Integer id) {
        if (null == id) return null;
        ChannelTemplateDO channelTemplateDO = channelTemplateDao.template(id);
        if (null == channelTemplateDO) return null;
        ChannelConfigDTO channelConfigDTO = channelConfigService.config(channelTemplateDO.getChnlConfigId());
        return ChannelTemplateDTO.convertChannelTemplateDO(channelTemplateDO, channelConfigDTO);
    }
}
