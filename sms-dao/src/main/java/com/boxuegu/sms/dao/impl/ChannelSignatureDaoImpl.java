package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.dao.ChannelSignatureDao;
import com.boxuegu.sms.dao.mapper.ChannelSignatureMapper;
import com.boxuegu.sms.domain.ChannelSignatureDO;
import com.boxuegu.sms.domain.ChannelSignatureDOCriteria;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.enumeration.DeleteFlag;
import com.boxuegu.sms.utils.Page;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 渠道签名 Dao
 *
 * @author leonzhangxf 20180906
 */
@Repository
public class ChannelSignatureDaoImpl implements ChannelSignatureDao {

    private ChannelSignatureMapper channelSignatureMapper;

    @Autowired
    public void setChannelSignatureMapper(ChannelSignatureMapper channelSignatureMapper) {
        this.channelSignatureMapper = channelSignatureMapper;
    }


    @Override
    public ChannelSignatureDO saveSignature(ChannelSignatureDO channelSignatureDO) {
        if (null == channelSignatureDO || null == channelSignatureDO.getChnlConfigId()
                || null == channelSignatureDO.getSignature())
            return null;

        if (!CommonStatus.inStatus(channelSignatureDO.getStatus()))
            channelSignatureDO.setStatus(CommonStatus.DISABLE.getStatus());

        int result = channelSignatureMapper.insertSelective(channelSignatureDO);
        if (result <= 0) return null;
        return channelSignatureDO;
    }

    @Override
    public void deleteSignature(Integer id) {
        if (null == id) return;

        ChannelSignatureDO channelSignatureDO = new ChannelSignatureDO();
        channelSignatureDO.setId(id);
        channelSignatureDO.setStatus(CommonStatus.DISABLE.getStatus());
        channelSignatureDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());
        channelSignatureMapper.updateByPrimaryKeySelective(channelSignatureDO);
    }

    @Override
    public void updateSignature(ChannelSignatureDO channelSignatureDO) {
        if (null == channelSignatureDO || null == channelSignatureDO.getId()
                || null == channelSignatureDO.getChnlConfigId()
                || !StringUtils.hasText(channelSignatureDO.getSignature()))
            return;

        if (!CommonStatus.inStatus(channelSignatureDO.getStatus()))
            channelSignatureDO.setStatus(CommonStatus.DISABLE.getStatus());

        channelSignatureMapper.updateByPrimaryKeySelective(channelSignatureDO);
    }

    @Override
    public void updateSignatureStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus) {
        if (null == channelConfigId || !CommonStatus.inStatus(targetStatus)) return;

        ChannelSignatureDOCriteria criteria = new ChannelSignatureDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlConfigIdEqualTo(channelConfigId);

        ChannelSignatureDO channelSignatureDO = new ChannelSignatureDO();
        channelSignatureDO.setStatus(targetStatus);

        channelSignatureMapper.updateByExampleSelective(channelSignatureDO, criteria);
    }

    @Override
    public Page<ChannelSignatureDO> signatures(Integer channelConfigId, String signature, Integer status,
                                               Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        ChannelSignatureDOCriteria criterion = new ChannelSignatureDOCriteria();

        ChannelSignatureDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());

        if (null != channelConfigId) criteria.andChnlConfigIdEqualTo(channelConfigId);
        if (StringUtils.hasText(signature)) criteria.andSignatureLike("%" + signature + "%");
        if (CommonStatus.inStatus(status)) criteria.andStatusEqualTo(status);

        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ChannelSignatureDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ChannelSignatureDO> channelSignatureDOList = channelSignatureMapper.selectByExample(criterion);

        if (CollectionUtils.isEmpty(channelSignatureDOList))
            return new Page<>(channelSignatureDOList, 0, pageSize, currentPage);

        return new Page<>(channelSignatureDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }

    @Override
    public ChannelSignatureDO signature(Integer id) {
        if (null == id) return null;

        ChannelSignatureDO channelSignatureDO = channelSignatureMapper.selectByPrimaryKey(id);

        return null == channelSignatureDO
                || channelSignatureDO.getDeleteFlag().equals(DeleteFlag.DELETED.getDeleteFlag())
                ? null : channelSignatureDO;
    }
}
