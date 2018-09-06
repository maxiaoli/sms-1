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


    ChannelConfigDO saveConfig(ChannelConfigDO channelConfigDO);


    void deleteConfig(Integer id);


    void updateConfig(ChannelConfigDO channelConfigDO);


    Page<ChannelConfigDO> configs(String name, Integer type, Integer status, Integer currentPage, Integer pageSize);


    List<ChannelConfigDO> configs(String name, Integer type);


    ChannelConfigDO config(Integer id);


    ChannelConfigDO configWithinDeleted(Integer id);


    List<ChannelConfigDO> configsWithinDeletedByName(String name);
}
