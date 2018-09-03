package com.boxuegu.sms;

import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigDetailDTO;
import com.boxuegu.sms.enumeration.ChannelConfigType;
import com.boxuegu.sms.utils.Page;

/**
 * 渠道配置Service
 *
 * @author leonzhangxf 20180903
 */
public interface ChannelConfigService {

    void deleteChannelConfig(Integer id);

    void saveChannelConfig(ChannelConfigDetailDTO channelConfigDetailDTO);

    void updateChannelConfig(ChannelConfigDetailDTO channelConfigDetailDTO);

    Page<ChannelConfigDTO> channelConfigs(String name, ChannelConfigType channelConfigType, Integer currentPage, Integer pageSize);

    ChannelConfigDetailDTO channelConfigDetail(Integer id);

}
