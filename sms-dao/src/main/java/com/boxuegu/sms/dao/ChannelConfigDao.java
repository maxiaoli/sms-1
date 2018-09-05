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

    Page<ChannelConfigDO> channelConfigs(String name, Integer type, Integer currentPage, Integer pageSize);

    List<ChannelConfigDO> channelConfigs(String name, Integer type);

    ChannelConfigDO channelConfig(Integer id);

    ChannelConfigDO channelConfigWithinDeleted(Integer id);

    void deleteChannelConfig(Integer id);

    ChannelConfigDO saveChannelConfig(ChannelConfigDO channelConfigDO);

    void updateChannelConfig(ChannelConfigDO channelConfigDO);

}
