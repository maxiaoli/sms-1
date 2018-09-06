package com.boxuegu.sms.service.impl;

import com.boxuegu.sms.dao.TemplateDao;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    private TemplateDao templateDao;

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
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
}
