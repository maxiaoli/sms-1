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

    List<ChannelConfigParamsDTO> configParams(Integer channelConfigId);

    void deleteConfigParams(Integer channelConfigId);

    void saveConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);

    void updateConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);

    void deleteConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList);
}
