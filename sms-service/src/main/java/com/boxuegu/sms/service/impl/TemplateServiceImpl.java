package com.boxuegu.sms.service.impl;

import com.boxuegu.sms.dao.TemplateDao;
import com.boxuegu.sms.domain.TemplateDO;
import com.boxuegu.sms.domain.dto.ChannelSignatureDTO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.domain.dto.ClientDTO;
import com.boxuegu.sms.domain.dto.TemplateDTO;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.ChannelSignatureService;
import com.boxuegu.sms.service.ChannelTemplateService;
import com.boxuegu.sms.service.ClientService;
import com.boxuegu.sms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    private TemplateDao templateDao;

    private ClientService clientService;

    private ChannelSignatureService channelSignatureService;

    private ChannelTemplateService channelTemplateService;

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
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

    @Override
    @Transactional
    public void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus) {
        if (null == channelSignatureId || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelSignatureId(channelSignatureId, targetStatus);
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureDOIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelSignatureDOIdList) || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelSignatureIdList(channelSignatureDOIdList, targetStatus);
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus) {
        if (null == channelTemplateId || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelTemplateId(channelTemplateId, targetStatus);
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelTemplateIdList) || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelTemplateIdList(channelTemplateIdList, targetStatus);
    }

    @Override
    public List<TemplateDTO> templatesWithinDeletedByTemplateId(String templateId) {
        if (!StringUtils.hasText(templateId)) return null;

        List<TemplateDO> templateDOList = templateDao.templatesWithinDeletedByTemplateId(templateId);
        if (CollectionUtils.isEmpty(templateDOList)) return null;

        List<TemplateDTO> list = new ArrayList<>();
        for (TemplateDO templateDO : templateDOList) {
            ClientDTO clientDTO = clientService.client(templateDO.getClientId());
            ChannelSignatureDTO channelSignatureDTO = channelSignatureService.signature(templateDO.getChnlSignatureId());
            ChannelTemplateDTO channelTemplateDTO = channelTemplateService.template(templateDO.getChnlTemplateId());
            TemplateDTO templateDTO = TemplateDTO.convertTemplateDO(templateDO, clientDTO, channelSignatureDTO, channelTemplateDTO);
            if (null != templateDTO) list.add(templateDTO);
        }
        return list;
    }
}
