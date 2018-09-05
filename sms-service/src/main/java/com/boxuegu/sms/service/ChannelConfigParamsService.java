package com.boxuegu.sms.service;

import com.boxuegu.sms.domain.ChannelConfigParamsDO;
import com.boxuegu.sms.domain.dto.ChannelConfigParamsDTO;

import java.util.List;

/**
 * 渠道账号配置参数 Service
 *
 * @author leonzhangxf 20180903
 */
public interface ChannelConfigParamsService {

    List<ChannelConfigParamsDTO> channelConfigParams(Integer channelConfigId);

    void deleteChannelConfigParams(Integer channelConfigId);

    void saveChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);

    void updateChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);

    void deleteChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);
}
