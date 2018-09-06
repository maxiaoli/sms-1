package com.boxuegu.sms.service.impl;


import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.dao.ChannelSignatureDao;
import com.boxuegu.sms.domain.ChannelSignatureDO;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelSignatureDTO;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.ChannelConfigService;
import com.boxuegu.sms.service.ChannelSignatureService;
import com.boxuegu.sms.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道签名 Service
 *
 * @author leonzhangxf 20180905
 */
@Service
public class ChannelSignatureServiceImpl implements ChannelSignatureService {

    private ChannelSignatureDao channelSignatureDao;

    private ChannelConfigService channelConfigService;

    @Autowired
    public void setChannelSignatureDao(ChannelSignatureDao channelSignatureDao) {
        this.channelSignatureDao = channelSignatureDao;
    }

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }


    @Override
    @Transactional
    public void saveSignature(ChannelSignatureDTO channelSignatureDTO) {
        if (null == channelSignatureDTO || null == channelSignatureDTO.getChannelConfig()
                || null == channelSignatureDTO.getChannelConfig().getId())
            return;

        ChannelSignatureDO channelSignatureDO = ChannelSignatureDTO.convertToChannelSignatureDO(channelSignatureDTO);
        if (null == channelSignatureDO) return;

        channelSignatureDao.saveSignature(channelSignatureDO);
    }


    @Override
    @Transactional
    public void deleteSignature(Integer id) {
        if (null == id) return;

        //1.删除渠道签名
        ChannelSignatureDO channelSignatureDO = channelSignatureDao.signature(id);
        if (null == channelSignatureDO) return;

        channelSignatureDao.deleteSignature(channelSignatureDO.getId());

        //2.删除渠道签名，需要禁用和其关联的短信服务模板
    }


    @Override
    @Transactional
    public void updateSignature(ChannelSignatureDTO channelSignatureDTO) {
        if (null == channelSignatureDTO || null == channelSignatureDTO.getId()
                || null == channelSignatureDTO.getChannelConfig()
                || null == channelSignatureDTO.getChannelConfig().getId()
                || !StringUtils.hasText(channelSignatureDTO.getSignature()))
            return;

        //1.禁用渠道签名
        ChannelSignatureDO channelSignatureDO = ChannelSignatureDTO.convertToChannelSignatureDO(channelSignatureDTO);
        if (null == channelSignatureDO) return;

        channelSignatureDao.updateSignature(channelSignatureDO);

        //2.禁用渠道签名，需要禁用和其关联的短信服务模板
    }


    @Override
    @Transactional
    public void updateSignatureStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus) {
        if (null == channelConfigId || !CommonStatus.inStatus(targetStatus)) return;

        channelSignatureDao.updateSignatureStatusByChannelConfigId(channelConfigId, targetStatus);
    }


    @Override
    public Page<ChannelSignatureDTO> signatures(Integer channelConfigId, String signature, Integer status,
                                                Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ChannelSignatureDO> channelSignatureDOPage = channelSignatureDao
                .signatures(channelConfigId, signature, status, currentPage, pageSize);

        List<ChannelSignatureDTO> channelSignatureDTOList = new ArrayList<>();
        if (null == channelSignatureDOPage || CollectionUtils.isEmpty(channelSignatureDOPage.getItems()))
            return new Page<>(channelSignatureDTOList, 0, pageSize, currentPage);

        for (ChannelSignatureDO channelSignatureDO : channelSignatureDOPage.getItems()) {
            ChannelConfigDTO channelConfigDTO = channelConfigService.channelConfigWithinDeleted(channelSignatureDO.getChnlConfigId());
            ChannelSignatureDTO channelSignatureDTO = ChannelSignatureDTO.convertChannelSignatureDO(channelSignatureDO, channelConfigDTO);
            channelSignatureDTOList.add(channelSignatureDTO);
        }

        return new Page<>(channelSignatureDTOList, channelSignatureDOPage.getTotalCount(), pageSize, currentPage);
    }
}
