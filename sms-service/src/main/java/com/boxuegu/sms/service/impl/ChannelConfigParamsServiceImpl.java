package com.boxuegu.sms.service.impl;

import com.boxuegu.sms.service.ChannelConfigParamsService;
import com.boxuegu.sms.dao.ChannelConfigParamsDao;
import com.boxuegu.sms.domain.ChannelConfigParamsDO;
import com.boxuegu.sms.domain.dto.ChannelConfigParamsDTO;
import com.boxuegu.sms.enumeration.ChannelConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道账号配置参数 Service
 *
 * @author leonzhangxf 20180903
 */
@Service
public class ChannelConfigParamsServiceImpl implements ChannelConfigParamsService {

    private ChannelConfigParamsDao channelConfigParamsDao;

    @Autowired
    public void setChannelConfigParamsDao(ChannelConfigParamsDao channelConfigParamsDao) {
        this.channelConfigParamsDao = channelConfigParamsDao;
    }

    @Override
    public List<ChannelConfigParamsDTO> channelConfigParams(Integer channelConfigId) {

        List<ChannelConfigParamsDO> channelConfigParamsDOList = channelConfigParamsDao.channelConfigParams(channelConfigId);
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return null;

        List<ChannelConfigParamsDTO> list = new ArrayList<>();
        for (ChannelConfigParamsDO channelConfigParamDO : channelConfigParamsDOList) {
            ChannelConfigParam key = ChannelConfigParam.getChannelConfigParam(channelConfigParamDO.getKey());
            if (null == key) continue;

            ChannelConfigParamsDTO channelConfigParamsDTO = new ChannelConfigParamsDTO();
            channelConfigParamsDTO.setKey(key);
            channelConfigParamsDTO.setValue(channelConfigParamDO.getValue());
            list.add(channelConfigParamsDTO);
        }
        return list;
    }

    @Override
    @Transactional
    public void deleteChannelConfigParams(Integer channelConfigId) {
        if (null == channelConfigId) return;
        channelConfigParamsDao.deleteChannelConfigParams(channelConfigId);
    }

    @Override
    @Transactional
    public void saveChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        channelConfigParamsDao.saveChannelConfigParamsBatch(channelConfigParamsDOList);
    }

    @Override
    @Transactional
    public void updateChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        channelConfigParamsDao.updateChannelConfigParamsBatch(channelConfigParamsDOList);
    }

    @Override
    @Transactional
    public void deleteChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        channelConfigParamsDao.deleteChannelConfigParamsBatch(channelConfigParamsDOList);
    }
}
