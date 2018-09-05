package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.dao.ChannelTemplateDao;
import com.boxuegu.sms.dao.mapper.ChannelTemplateMapper;
import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.domain.ChannelTemplateDOCriteria;
import com.boxuegu.sms.utils.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 渠道模板 Dao
 *
 * @author leonzhangxf 20180904
 */
@Repository
public class ChannelTemplateDaoImpl implements ChannelTemplateDao {

    private ChannelTemplateMapper channelTemplateMapper;

    @Autowired
    public void setChannelTemplateMapper(ChannelTemplateMapper channelTemplateMapper) {
        this.channelTemplateMapper = channelTemplateMapper;
    }

    @Override
    public ChannelTemplateDO saveTemplate(ChannelTemplateDO channelTemplateDO) {
        if (null == channelTemplateDO || null == channelTemplateDO.getChnlConfigId()
                || !StringUtils.hasText(channelTemplateDO.getName())
                || !StringUtils.hasText(channelTemplateDO.getCode())
                || !StringUtils.hasText(channelTemplateDO.getContent()))
            return null;

        if (null == channelTemplateDO.getStatus())
            channelTemplateDO.setStatus(0);

        int result = channelTemplateMapper.insertSelective(channelTemplateDO);
        if (result <= 0) return null;
        return channelTemplateDO;
    }

    @Override
    public void deleteTemplate(Integer id) {
        if (null == id) return;

        ChannelTemplateDO channelTemplateDO = new ChannelTemplateDO();
        channelTemplateDO.setId(id);
        channelTemplateDO.setStatus(0);
        channelTemplateDO.setDeleteFlag(1);
        channelTemplateMapper.updateByPrimaryKeySelective(channelTemplateDO);
    }

    @Override
    public void updateTemplate(ChannelTemplateDO channelTemplateDO) {
        if (null == channelTemplateDO || null == channelTemplateDO.getId()
                || null == channelTemplateDO.getChnlConfigId()
                || !StringUtils.hasText(channelTemplateDO.getName())
                || !StringUtils.hasText(channelTemplateDO.getCode())
                || !StringUtils.hasText(channelTemplateDO.getContent()))
            return;

        if (null == channelTemplateDO.getStatus())
            channelTemplateDO.setStatus(0);

        channelTemplateMapper.updateByPrimaryKeySelective(channelTemplateDO);
    }

    @Override
    public Page<ChannelTemplateDO> channelTemplates(Integer channelConfigId, String name, String code, Integer status, Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        ChannelTemplateDOCriteria criterion = new ChannelTemplateDOCriteria();

        ChannelTemplateDOCriteria.Criteria criteria = criterion.createCriteria().andDeleteFlagEqualTo(0);

        if (null != channelConfigId) criteria.andChnlConfigIdEqualTo(channelConfigId);
        if (StringUtils.hasText(name)) criteria.andNameLike("%" + name + "%");
        if (StringUtils.hasText(code)) criteria.andCodeLike("%" + code + "%");
        if (null != status && (status == 1 || status == 2)) criteria.andStatusEqualTo(status);

        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ChannelTemplateDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ChannelTemplateDO> channelTemplateDOList = channelTemplateMapper.selectByExample(criterion);

        if (CollectionUtils.isEmpty(channelTemplateDOList))
            return new Page<>(channelTemplateDOList, 0, pageSize, currentPage);

        return new Page<>(channelTemplateDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }

    @Override
    public ChannelTemplateDO channelTemplate(Integer id) {
        if (null == id) return null;

        ChannelTemplateDO channelTemplateDO = channelTemplateMapper.selectByPrimaryKey(id);
        return null == channelTemplateDO || channelTemplateDO.getDeleteFlag().equals(1) ? null : channelTemplateDO;
    }
}
