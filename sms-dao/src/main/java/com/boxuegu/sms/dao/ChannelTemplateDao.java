package com.boxuegu.sms.dao;

import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.utils.Page;

/**
 * 渠道模板 Dao
 *
 * @author leonzhangxf 20180904
 */
public interface ChannelTemplateDao {

    Page<ChannelTemplateDO> channelTemplates(Integer channelConfigId, String name, String code, Integer status, Integer currentPage, Integer pageSize);
}
