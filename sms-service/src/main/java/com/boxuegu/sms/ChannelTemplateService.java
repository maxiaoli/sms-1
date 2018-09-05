package com.boxuegu.sms;

import com.boxuegu.sms.domain.dto.ChannelTemplateDTO;
import com.boxuegu.sms.utils.Page;

/**
 * 渠道模板 Service
 *
 * @author leonzhangxf 20180904
 */
public interface ChannelTemplateService {

    void saveTemplate(ChannelTemplateDTO channelTemplateDTO);


    void deleteTemplate(Integer id);


    void updateTemplate(ChannelTemplateDTO channelTemplateDTO);


    Page<ChannelTemplateDTO> channelTemplates(Integer channelConfigId, String name, String code, Integer status,
                                              Integer currentPage, Integer pageSize);

}
