package com.boxuegu.sms.impl;

import com.boxuegu.sms.ChannelTemplateService;
import com.boxuegu.sms.dao.ChannelTemplateDao;
import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 渠道模板 Service
 *
 * @author leonzhangxf 20180904
 */
@Service
public class ChannelTemplateServiceImpl implements ChannelTemplateService {

    private ChannelTemplateDao channelTemplateDao;

    @Autowired
    public void setChannelTemplateDao(ChannelTemplateDao channelTemplateDao) {
        this.channelTemplateDao = channelTemplateDao;
    }

    @Override
    public Page<ChannelTemplateDTO> channelTemplates(Integer channelConfigId, String name, String code, Integer status,
                                                     Integer currentPage, Integer pageSize) {

        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        Page<ChannelTemplateDO> channelTemplateDOPage = channelTemplateDao
                .channelTemplates(channelConfigId, name, code, status, currentPage, pageSize);


        return null;
    }
}
