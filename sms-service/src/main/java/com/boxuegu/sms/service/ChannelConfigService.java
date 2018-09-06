package com.boxuegu.sms.service;

import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigDetailDTO;
import com.boxuegu.sms.enumeration.ChannelConfigType;
import com.boxuegu.sms.utils.Page;

import java.util.List;

/**
 * 渠道配置Service
 *
 * @author leonzhangxf 20180903
 */
public interface ChannelConfigService {

    void deleteChannelConfig(Integer id);

    void saveChannelConfig(ChannelConfigDetailDTO channelConfigDetailDTO);

    void updateChannelConfig(ChannelConfigDetailDTO channelConfigDetailDTO);

    Page<ChannelConfigDTO> channelConfigs(String name, ChannelConfigType channelConfigType, Integer status, Integer currentPage, Integer pageSize);

    List<ChannelConfigDTO> channelConfigs();

    List<ChannelConfigDTO> channelConfigs(String name, ChannelConfigType channelConfigType);

    ChannelConfigDetailDTO channelConfigDetail(Integer id);

    ChannelConfigDTO channelConfig(Integer id);

    ChannelConfigDTO channelConfigWithinDeleted(Integer id);

    ChannelConfigDTO channelConfigWithinDeletedByName(String name);
}
