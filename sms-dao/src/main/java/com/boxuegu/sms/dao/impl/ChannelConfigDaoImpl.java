package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.dao.ChannelConfigDao;
import com.boxuegu.sms.dao.mapper.ChannelConfigMapper;
import com.boxuegu.sms.domain.ChannelConfigDO;
import com.boxuegu.sms.domain.ChannelConfigDOCriteria;
import com.boxuegu.sms.utils.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 渠道配置 Dao 实现
 *
 * @author leonzhangxf 20180903
 */
@Repository
public class ChannelConfigDaoImpl implements ChannelConfigDao {

    private ChannelConfigMapper channelConfigMapper;

    @Autowired
    public void setChannelConfigMapper(ChannelConfigMapper channelConfigMapper) {
        this.channelConfigMapper = channelConfigMapper;
    }

    @Override
    public Page<ChannelConfigDO> channelConfigs(String name, Integer type, Integer currentPage, Integer pageSize) {
        ChannelConfigDOCriteria criterion = new ChannelConfigDOCriteria();
        ChannelConfigDOCriteria.Criteria criteria = criterion.createCriteria().andDeleteFlagEqualTo(0);
        if (StringUtils.hasText(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (null != type) {
            criteria.andTypeEqualTo(type);
        }
        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ChannelConfigDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ChannelConfigDO> channelConfigDOList = channelConfigMapper.selectByExample(criterion);

        return new Page<>(channelConfigDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }

    @Override
    public ChannelConfigDO channelConfig(Integer id) {
        if (null == id) return null;
        ChannelConfigDO channelConfigDO = channelConfigMapper.selectByPrimaryKey(id);
        return channelConfigDO.getDeleteFlag() == 1 ? null : channelConfigDO;
    }

    @Override
    public void deleteChannelConfig(Integer id) {
        if (null == id) return;
        ChannelConfigDO channelConfigDO = new ChannelConfigDO();
        channelConfigDO.setId(id);
        channelConfigDO.setStatus(0);//禁用
        channelConfigDO.setDeleteFlag(1);//删除
        channelConfigMapper.updateByPrimaryKeySelective(channelConfigDO);
    }

    @Override
    public ChannelConfigDO saveChannelConfig(ChannelConfigDO channelConfigDO) {
        if (null == channelConfigDO) return null;
        int result = channelConfigMapper.insertSelective(channelConfigDO);
        if (result <= 0) return null;
        return channelConfigDO;
    }

    @Override
    public void updateChannelConfig(ChannelConfigDO channelConfigDO) {
        if (null == channelConfigDO) return;
        channelConfigMapper.updateByPrimaryKeySelective(channelConfigDO);
    }
}
