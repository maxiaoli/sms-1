package com.boxuegu.sms.impl;

import com.boxuegu.sms.ChannelConfigParamsService;
import com.boxuegu.sms.ChannelConfigService;
import com.boxuegu.sms.dao.ChannelConfigDao;
import com.boxuegu.sms.domain.ChannelConfigDO;
import com.boxuegu.sms.domain.ChannelConfigParamsDO;
import com.boxuegu.sms.domain.dto.ChannelConfigDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigDetailDTO;
import com.boxuegu.sms.domain.dto.ChannelConfigParamsDTO;
import com.boxuegu.sms.enumeration.ChannelConfigParam;
import com.boxuegu.sms.enumeration.ChannelConfigType;
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
 */
@Service
public class ChannelConfigServiceImpl implements ChannelConfigService {

    private ChannelConfigDao channelConfigDao;

    private ChannelConfigParamsService channelConfigParamsService;

    @Autowired
    public void setChannelConfigDao(ChannelConfigDao channelConfigDao) {
        this.channelConfigDao = channelConfigDao;
    }

    @Autowired
    public void setChannelConfigParamsService(ChannelConfigParamsService channelConfigParamsService) {
        this.channelConfigParamsService = channelConfigParamsService;
    }

    @Override
    @Transactional
    public void deleteChannelConfig(Integer id) {
        if (null == id) return;
        ChannelConfigDO channelConfigDO = channelConfigDao.channelConfig(id);
        if (null == channelConfigDO) return;
        channelConfigDao.deleteChannelConfig(channelConfigDO.getId());

        channelConfigParamsService.deleteChannelConfigParams(channelConfigDO.getId());
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

    private boolean compareValuableValue(ChannelConfigDO existChannelConfigDO, ChannelConfigDO channelConfigDO) {
        if (existChannelConfigDO == channelConfigDO) return true;

        return existChannelConfigDO.getName().equals(channelConfigDO.getName())
                && existChannelConfigDO.getDesc().equals(channelConfigDO.getDesc())
                && existChannelConfigDO.getType().equals(channelConfigDO.getType())
                && existChannelConfigDO.getStatus().equals(channelConfigDO.getStatus());
    }

    @Override
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

    }

    @Override
    public Page<ChannelConfigDTO> channelConfigs(String name, ChannelConfigType channelConfigType,
                                                 Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? 1 : currentPage;
        pageSize = null == pageSize ? 10 : pageSize;

        Integer type = null;
        if (null != channelConfigType) {
            type = channelConfigType.getType();
        }

        Page<ChannelConfigDO> channelConfigDOPage = channelConfigDao.channelConfigs(name, type, currentPage, pageSize);

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
    @Transactional
    public ChannelConfigDetailDTO channelConfigDetail(Integer id) {
        if (null == id) return null;

        ChannelConfigDetailDTO channelConfigDetailDTO = new ChannelConfigDetailDTO();

        ChannelConfigDO channelConfigDO = channelConfigDao.channelConfig(id);
        if (null == channelConfigDO) return null;
        ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
        channelConfigDetailDTO.setConfig(channelConfigDTO);

        List<ChannelConfigParamsDTO> channelConfigParamsDTOList = channelConfigParamsService.channelConfigParams(id);
        channelConfigDetailDTO.setParams(channelConfigParamsDTOList);
        return channelConfigDetailDTO;
    }
}
