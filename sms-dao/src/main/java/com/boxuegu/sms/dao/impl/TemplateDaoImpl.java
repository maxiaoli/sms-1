package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.dao.TemplateDao;
import com.boxuegu.sms.dao.mapper.TemplateMapper;
import com.boxuegu.sms.domain.TemplateDO;
import com.boxuegu.sms.domain.TemplateDOCriteria;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.enumeration.DeleteFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
@Repository
public class TemplateDaoImpl implements TemplateDao {

    private TemplateMapper templateMapper;

    @Autowired
    public void setTemplateMapper(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    @Override
    public void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus) {
        if (null == channelSignatureId || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlSignatureIdEqualTo(channelSignatureId);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureDOIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelSignatureDOIdList) || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlSignatureIdIn(channelSignatureDOIdList);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus) {
        if (null == channelTemplateId || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlTemplateIdEqualTo(channelTemplateId);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelTemplateIdList) || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlTemplateIdIn(channelTemplateIdList);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    //TODO
    @Override
    public List<TemplateDO> templatesWithinDeletedByTemplateId(String templateId) {
        return null;
    }
}
