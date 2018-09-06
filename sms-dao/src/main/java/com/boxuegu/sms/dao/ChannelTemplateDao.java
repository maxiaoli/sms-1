package com.boxuegu.sms.dao;

import com.boxuegu.sms.domain.ChannelTemplateDO;
import com.boxuegu.sms.utils.Page;

import java.util.List;

/**
 * 渠道模板 Dao
 *
 * @author leonzhangxf 20180904
 */
public interface ChannelTemplateDao {


    ChannelTemplateDO saveTemplate(ChannelTemplateDO channelTemplateDO);


    void deleteTemplate(Integer id);


    void updateTemplate(ChannelTemplateDO channelTemplateDO);


    void updateTemplateStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus);


    Page<ChannelTemplateDO> templates(Integer channelConfigId, String name, String code, Integer status, Integer currentPage, Integer pageSize);


    List<ChannelTemplateDO> templates(Integer channelConfigId);


    List<ChannelTemplateDO> templates();


    ChannelTemplateDO template(Integer id);

}
