package com.boxuegu.sms.service.impl;

import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.dao.ChannelConfigDao;
import com.boxuegu.sms.domain.ChannelConfigDO;
import com.boxuegu.sms.domain.ChannelConfigParamsDO;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigDetailDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigParamsDTO;
import com.boxuegu.sms.enumeration.ChannelConfigParam;
import com.boxuegu.sms.enumeration.ChannelConfigType;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.service.*;
import com.boxuegu.sms.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 渠道配置Service 实现
 *
 * @author leonzhangxf 201890903
 */
@Service
public class ChannelConfigServiceImpl implements ChannelConfigService {

    private ChannelConfigDao channelConfigDao;

    private ChannelConfigParamsService channelConfigParamsService;

    private ChannelTemplateService channelTemplateService;

    private ChannelSignatureService channelSignatureService;

    @Autowired
    public void setChannelConfigDao(ChannelConfigDao channelConfigDao) {
        this.channelConfigDao = channelConfigDao;
    }

    @Autowired
    public void setChannelConfigParamsService(ChannelConfigParamsService channelConfigParamsService) {
        this.channelConfigParamsService = channelConfigParamsService;
    }

    @Autowired
    public void setChannelTemplateService(ChannelTemplateService channelTemplateService) {
        this.channelTemplateService = channelTemplateService;
    }

    @Autowired
    public void setChannelSignatureService(ChannelSignatureService channelSignatureService) {
        this.channelSignatureService = channelSignatureService;
    }


    @Override
    @Transactional
    public void deleteChannelConfig(Integer id) {
        if (null == id) return;

        //1.删除渠道配置
        ChannelConfigDO channelConfigDO = channelConfigDao.channelConfig(id);
        if (null == channelConfigDO) return;
        channelConfigDao.deleteChannelConfig(channelConfigDO.getId());

        //2.删除渠道配置参数
        channelConfigParamsService.deleteChannelConfigParams(channelConfigDO.getId());

        //3.删除渠道配置，需要同时禁用其下对应的所有的渠道模板、渠道签名、短信服务模板（通过渠道模板、以及渠道签名的禁用来级联禁用）
        //禁用所属的渠道模板
        channelTemplateService.updateTemplateStatusByChannelConfigId(channelConfigDO.getId(), CommonStatus.DISABLE.getStatus());
        //禁用所属的渠道签名
        channelSignatureService.updateSignatureStatusByChannelConfigId(channelConfigDO.getId(), CommonStatus.DISABLE.getStatus());
    }


    @Override
    @Transactional
    public void saveChannelConfig(ChannelConfigDetailDTO channelConfigDetailDTO) {
        if (null == channelConfigDetailDTO) return;
        //1.保存主配置
        ChannelConfigDTO channelConfigDTO = channelConfigDetailDTO.getConfig();
        if (null == channelConfigDTO) return;

        ChannelConfigDO channelConfigDO = ChannelConfigDTO.convertToChannelConfigDO(channelConfigDTO);
        if (null == channelConfigDO) return;
        channelConfigDao.saveChannelConfig(channelConfigDO);
        if (null == channelConfigDO.getId()) return;

        //2.保存参数配置
        List<ChannelConfigParamsDTO> params = channelConfigDetailDTO.getParams();
        if (CollectionUtils.isEmpty(params)) return;
        List<ChannelConfigParamsDO> channelConfigParamsDOList =
                ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(params, channelConfigDO.getId());
        channelConfigParamsService.saveChannelConfigParamsBatch(channelConfigParamsDOList);
    }


    @Override
    @Transactional
    public void updateChannelConfig(ChannelConfigDetailDTO channelConfigDetailDTO) {
        if (null == channelConfigDetailDTO) return;

        //1.更新主配置
        ChannelConfigDTO channelConfigDTO = channelConfigDetailDTO.getConfig();
        if (null == channelConfigDTO) return;

        ChannelConfigDO existChannelConfigDO = channelConfigDao.channelConfig(channelConfigDTO.getId());
        if (null == existChannelConfigDO) return;
        ChannelConfigDO channelConfigDO = ChannelConfigDTO.convertToChannelConfigDO(channelConfigDTO);
        if (null == channelConfigDO) return;

        if (!compareValuableValue(existChannelConfigDO, channelConfigDO)) {
            channelConfigDao.updateChannelConfig(channelConfigDO);
        }

        //2.更新参数配置
        List<ChannelConfigParamsDTO> params = channelConfigDetailDTO.getParams();
        if (CollectionUtils.isEmpty(params)) return;

        List<ChannelConfigParamsDTO> existParams = channelConfigParamsService.channelConfigParams(channelConfigDO.getId());
        if (CollectionUtils.isEmpty(existParams)) {
            //之前没有，全部是新增的
            List<ChannelConfigParamsDO> channelConfigParamsDOList =
                    ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(params, channelConfigDO.getId());
            channelConfigParamsService.saveChannelConfigParamsBatch(channelConfigParamsDOList);
        } else {
            //之前有，分成新增、要更新的、以及要删除的 分开处理
            Map<ChannelConfigParam, String> existMap = existParams.stream()
                    .collect(Collectors.toMap(ChannelConfigParamsDTO::getKey, ChannelConfigParamsDTO::getValue));
            List<ChannelConfigParamsDTO> newParams = new ArrayList<>();
            List<ChannelConfigParamsDTO> differentParams = new ArrayList<>();

            for (ChannelConfigParamsDTO channelConfigParamsDTO : params) {
                String existValue = existMap.remove(channelConfigParamsDTO.getKey());
                if (!StringUtils.hasText(existValue)) {
                    newParams.add(channelConfigParamsDTO);
                } else if (!existValue.equals(channelConfigParamsDTO.getValue())) {
                    differentParams.add(channelConfigParamsDTO);
                }
            }

            List<ChannelConfigParamsDTO> deleteParams = new ArrayList<>();
            if (!CollectionUtils.isEmpty(existMap)) {
                for (Map.Entry<ChannelConfigParam, String> entry : existMap.entrySet()) {
                    deleteParams.add(new ChannelConfigParamsDTO(entry.getKey(), entry.getValue()));
                }
            }

            //Dispose
            if (!CollectionUtils.isEmpty(newParams)) {
                List<ChannelConfigParamsDO> newParamsDOList =
                        ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(newParams, channelConfigDO.getId());
                channelConfigParamsService.saveChannelConfigParamsBatch(newParamsDOList);
            }

            if (!CollectionUtils.isEmpty(differentParams)) {
                List<ChannelConfigParamsDO> differentParamsDOList =
                        ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(differentParams, channelConfigDO.getId());
                channelConfigParamsService.updateChannelConfigParamsBatch(differentParamsDOList);
            }

            if (!CollectionUtils.isEmpty(deleteParams)) {
                List<ChannelConfigParamsDO> deleteParamsDOList =
                        ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(deleteParams, channelConfigDO.getId());
                channelConfigParamsService.deleteChannelConfigParamsBatch(deleteParamsDOList);
            }
        }

        //3.禁用渠道配置，需要同时禁用其下对应的所有的渠道模板、渠道签名、短信服务模板（通过渠道模板、以及渠道签名的禁用来级联禁用）
        //禁用所属的渠道模板
        channelTemplateService.updateTemplateStatusByChannelConfigId(channelConfigDTO.getId(), CommonStatus.DISABLE.getStatus());
        //禁用所属的渠道签名
        channelSignatureService.updateSignatureStatusByChannelConfigId(channelConfigDO.getId(), CommonStatus.DISABLE.getStatus());
    }


    @Override
    public Page<ChannelConfigDTO> channelConfigs(String name, ChannelConfigType channelConfigType, Integer status,
                                                 Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        Integer type = null;
        if (null != channelConfigType) {
            type = channelConfigType.getType();
        }

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ChannelConfigDO> channelConfigDOPage = channelConfigDao.channelConfigs(name, type, status, currentPage, pageSize);

        List<ChannelConfigDTO> list = new ArrayList<>();
        if (null != channelConfigDOPage && !CollectionUtils.isEmpty(channelConfigDOPage.getItems())) {
            for (ChannelConfigDO channelConfigDO : channelConfigDOPage.getItems()) {
                ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
                list.add(channelConfigDTO);
            }
            return new Page<>(list, channelConfigDOPage.getTotalCount(), pageSize, currentPage);
        }
        return new Page<>(list, 0, pageSize, currentPage);
    }


    @Override
    public List<ChannelConfigDTO> channelConfigs() {
        return channelConfigs(null, null);
    }


    @Override
    public List<ChannelConfigDTO> channelConfigs(String name, ChannelConfigType channelConfigType) {
        Integer type = null;
        if (null != channelConfigType) {
            type = channelConfigType.getType();
        }

        List<ChannelConfigDO> channelConfigDOList = channelConfigDao.channelConfigs(name, type);
        List<ChannelConfigDTO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(channelConfigDOList)) return list;

        for (ChannelConfigDO channelConfigDO : channelConfigDOList) {
            ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
            list.add(channelConfigDTO);
        }
        return list;
    }


    @Override
    @Transactional
    public ChannelConfigDetailDTO channelConfigDetail(Integer id) {
        if (null == id) return null;

        ChannelConfigDetailDTO channelConfigDetailDTO = new ChannelConfigDetailDTO();

        ChannelConfigDTO channelConfigDTO = channelConfig(id);
        if (null == channelConfigDTO) return null;
        channelConfigDetailDTO.setConfig(channelConfigDTO);

        List<ChannelConfigParamsDTO> channelConfigParamsDTOList = channelConfigParamsService.channelConfigParams(id);
        channelConfigDetailDTO.setParams(channelConfigParamsDTOList);
        return channelConfigDetailDTO;
    }


    @Override
    public ChannelConfigDTO channelConfig(Integer id) {
        if (null == id) return null;
        ChannelConfigDO channelConfigDO = channelConfigDao.channelConfig(id);
        if (null == channelConfigDO) return null;
        return ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
    }

    @Override
    public ChannelConfigDTO channelConfigWithinDeleted(Integer id) {
        if (null == id) return null;
        ChannelConfigDO channelConfigDO = channelConfigDao.channelConfigWithinDeleted(id);
        if (null == channelConfigDO) return null;
        return ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
    }

    @Override
    public List<ChannelConfigDTO> channelConfigWithinDeletedByName(String name) {
        if (!StringUtils.hasText(name)) return null;

        List<ChannelConfigDO> channelConfigDOList = channelConfigDao.channelConfigWithinDeletedByName(name);
        if (CollectionUtils.isEmpty(channelConfigDOList)) return null;

        List<ChannelConfigDTO> list = new ArrayList<>();
        for (ChannelConfigDO channelConfigDO : channelConfigDOList) {
            ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
            if (null != channelConfigDTO) list.add(channelConfigDTO);
        }

        return list;
    }


    private boolean compareValuableValue(ChannelConfigDO existChannelConfigDO, ChannelConfigDO channelConfigDO) {
        if (existChannelConfigDO == channelConfigDO) return true;

        return existChannelConfigDO.getName().equals(channelConfigDO.getName())
                && existChannelConfigDO.getDesc().equals(channelConfigDO.getDesc())
                && existChannelConfigDO.getType().equals(channelConfigDO.getType())
                && existChannelConfigDO.getStatus().equals(channelConfigDO.getStatus());
    }
}
