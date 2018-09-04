package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.dao.ChannelTemplateDao;
import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.utils.Page;
import org.springframework.stereotype.Repository;

/**
 * 渠道模板 Dao
 *
 * @author leonzhangxf 20180904
 */
@Repository
public class ChannelTemplateDaoImpl implements ChannelTemplateDao {

    @Override
    public Page<ChannelTemplateDO> channelTemplates(Integer channelConfigId, String name, String code, Integer status, Integer currentPage, Integer pageSize) {
        return null;
    }
}
