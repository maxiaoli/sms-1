package com.boxuegu.sms.dao;

import com.boxuegu.sms.domain.ChannelConfigDO;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.utils.Page;

import java.util.List;

/**
 * 渠道配置 Dao
 *
 * @author leonzhangxf 20180903
 */
public interface ChannelConfigDao {


    ChannelConfigDO saveChannelConfig(ChannelConfigDO channelConfigDO);


    void deleteChannelConfig(Integer id);


    void updateChannelConfig(ChannelConfigDO channelConfigDO);


    Page<ChannelConfigDO> channelConfigs(String name, Integer type, Integer status, Integer currentPage, Integer pageSize);


    List<ChannelConfigDO> channelConfigs(String name, Integer type);


    ChannelConfigDO channelConfig(Integer id);


    ChannelConfigDO channelConfigWithinDeleted(Integer id);


    List<ChannelConfigDO> channelConfigWithinDeletedByName(String name);
}
