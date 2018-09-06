package com.boxuegu.sms.service;

import com.boxuegu.sms.domain.dto.ChannelSignatureDTO;
import com.boxuegu.sms.utils.Page;

/**
 * 渠道签名 Service
 *
 * @author leonzhangxf 20180905
 */
public interface ChannelSignatureService {

    void saveSignature(ChannelSignatureDTO channelSignatureDTO);


    void deleteSignature(Integer id);


    void updateSignature(ChannelSignatureDTO channelSignatureDTO);


    void updateSignatureStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus);


    Page<ChannelSignatureDTO> signatures(Integer channelConfigId, String signature, Integer status, Integer currentPage, Integer pageSize);

}
