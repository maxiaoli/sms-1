package com.boxuegu.sms.service;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
public interface TemplateService {


    void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus);


    void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureIdList, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus);
}
