package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.dao.ChannelConfigParamsDao;
import com.boxuegu.sms.dao.mapper.ChannelConfigParamsMapper;
import com.boxuegu.sms.domain.ChannelConfigParamsDO;
import com.boxuegu.sms.domain.ChannelConfigParamsDOCriteria;
import com.boxuegu.sms.enumeration.DeleteFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 渠道账号配置参数 DAO
 *
 * @author leonzhangxf 20180903
 */
@Repository
public class ChannelConfigParamsDaoImpl implements ChannelConfigParamsDao {

    private ChannelConfigParamsMapper channelConfigParamsMapper;

    @Autowired
    public void setChannelConfigParamsMapper(ChannelConfigParamsMapper channelConfigParamsMapper) {
        this.channelConfigParamsMapper = channelConfigParamsMapper;
    }

    @Override
    public List<ChannelConfigParamsDO> channelConfigParams(Integer channelConfigId) {
        if (null == channelConfigId) return null;
        ChannelConfigParamsDOCriteria criteria = new ChannelConfigParamsDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlConfigIdEqualTo(channelConfigId);
        return channelConfigParamsMapper.selectByExample(criteria);
    }

    @Override
    public void deleteChannelConfigParams(Integer channelConfigId) {
        if (null == channelConfigId) return;
        ChannelConfigParamsDOCriteria criteria = new ChannelConfigParamsDOCriteria();
        criteria.createCriteria().andChnlConfigIdEqualTo(channelConfigId)
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());//删除未被删除的

        ChannelConfigParamsDO channelConfigParamsDO = new ChannelConfigParamsDO();
        channelConfigParamsDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());//删除
        channelConfigParamsMapper.updateByExampleSelective(channelConfigParamsDO, criteria);
    }

    @Override
    public void saveChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        for (ChannelConfigParamsDO channelConfigParamsDO : channelConfigParamsDOList) {
            channelConfigParamsMapper.insertSelective(channelConfigParamsDO);
        }
    }

    @Override
    public void updateChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        for (ChannelConfigParamsDO channelConfigParamsDO : channelConfigParamsDOList) {
            ChannelConfigParamsDOCriteria criteria = new ChannelConfigParamsDOCriteria();
            criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                    .andChnlConfigIdEqualTo(channelConfigParamsDO.getChnlConfigId())
                    .andKeyEqualTo(channelConfigParamsDO.getKey());//更新未被删除的指定渠道账号ID和KEY的值
            channelConfigParamsMapper.updateByExampleSelective(channelConfigParamsDO, criteria);
        }
    }

    @Override
    public void deleteChannelConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        for (ChannelConfigParamsDO channelConfigParamsDO : channelConfigParamsDOList) {
            channelConfigParamsDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());

            ChannelConfigParamsDOCriteria criteria = new ChannelConfigParamsDOCriteria();
            criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                    .andChnlConfigIdEqualTo(channelConfigParamsDO.getChnlConfigId())
                    .andKeyEqualTo(channelConfigParamsDO.getKey());//删除未被删除的指定渠道账号ID和KEY的值
            channelConfigParamsMapper.updateByExampleSelective(channelConfigParamsDO, criteria);
        }
    }
}
