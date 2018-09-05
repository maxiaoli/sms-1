package com.boxuegu.sms.impl;

import com.boxuegu.sms.ChannelConfigService;
import com.boxuegu.sms.ChannelTemplateService;
import com.boxuegu.sms.dao.ChannelTemplateDao;
import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道模板 Service
 *
 * @author leonzhangxf 20180904
 */
@Service
public class ChannelTemplateServiceImpl implements ChannelTemplateService {

    private ChannelTemplateDao channelTemplateDao;

    private ChannelConfigService channelConfigService;

    @Autowired
    public void setChannelTemplateDao(ChannelTemplateDao channelTemplateDao) {
        this.channelTemplateDao = channelTemplateDao;
    }

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }

    @Override
    public Page<ChannelTemplateDTO> channelTemplates(Integer channelConfigId, String name, String code, Integer status,
                                                     Integer currentPage, Integer pageSize) {

        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        Page<ChannelTemplateDO> channelTemplateDOPage = channelTemplateDao
                .channelTemplates(channelConfigId, name, code, status, currentPage, pageSize);

        List<ChannelTemplateDTO> channelTemplateDTOList  = new ArrayList<>();
        if (null == channelTemplateDOPage || CollectionUtils.isEmpty(channelTemplateDOPage.getItems()))
            return new Page<>(channelTemplateDTOList, 0, pageSize, currentPage);

        for (ChannelTemplateDO channelTemplateDO : channelTemplateDOPage.getItems()) {
            ChannelConfigDTO channelConfigDTO = channelConfigService.channelConfig(channelTemplateDO.getChnlConfigId());
            ChannelTemplateDTO channelTemplateDTO = ChannelTemplateDTO.convertChannelTemplateDO(channelTemplateDO, channelConfigDTO);
            channelTemplateDTOList.add(channelTemplateDTO);
        }

        return new Page<>(channelTemplateDTOList, channelTemplateDOPage.getTotalCount(), pageSize, currentPage);
    }
}
