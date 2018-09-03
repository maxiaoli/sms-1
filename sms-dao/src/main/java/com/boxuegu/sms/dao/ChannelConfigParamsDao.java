package com.boxuegu.sms.dao;

import com.boxuegu.sms.domain.ChannelConfigParamsDO;

import java.util.List;

/**
 * 渠道账号配置参数 DAO
 *
 * @author leonzhangxf 20180903
 */
public interface ChannelConfigParamsDao {

    List<ChannelConfigParamsDO> channelConfigParams(Integer channelConfigId);

    void deleteChannelConfigParams(Integer channelConfigId);

    void saveChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);

    void updateChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);

    void deleteChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);
}
