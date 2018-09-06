package com.boxuegu.sms.dao;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
public interface TemplateDao {


    void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus);


    void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureDOIdList, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus);
}
