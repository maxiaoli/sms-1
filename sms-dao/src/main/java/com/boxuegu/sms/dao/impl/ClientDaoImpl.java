package com.boxuegu.sms.dao.impl;

import com.boxuegu.sms.constant.SMSConstant;
import com.boxuegu.sms.dao.ClientDao;
import com.boxuegu.sms.dao.mapper.ClientMapper;
import com.boxuegu.sms.domain.ClientDO;
import com.boxuegu.sms.domain.ClientDOCriteria;
import com.boxuegu.sms.enumeration.CommonStatus;
import com.boxuegu.sms.enumeration.DeleteFlag;
import com.boxuegu.sms.utils.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 接入方 Dao
 *
 * @author leonzhangxf 20180906
 */
@Repository
public class ClientDaoImpl implements ClientDao {

    private ClientMapper clientMapper;

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDO saveClient(ClientDO clientDO) {
        if (null == clientDO || !StringUtils.hasText(clientDO.getCode())
                || !StringUtils.hasText(clientDO.getName())
                || !StringUtils.hasText(clientDO.getKey()))
            return null;

        if (!CommonStatus.inStatus(clientDO.getStatus())) clientDO.setStatus(CommonStatus.DISABLE.getStatus());

        clientMapper.insertSelective(clientDO);

        return clientDO;
    }

    @Override
    public void deleteClient(Integer id) {
        if (null == id) return;

        ClientDO clientDO = new ClientDO();
        clientDO.setId(id);
        clientDO.setStatus(CommonStatus.DISABLE.getStatus());
        clientDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());
        clientMapper.updateByPrimaryKeySelective(clientDO);
    }

    @Override
    public void updateClient(ClientDO clientDO) {
        if (null == clientDO || null == clientDO.getId()
                || !StringUtils.hasText(clientDO.getCode())
                || !StringUtils.hasText(clientDO.getName())
                || !StringUtils.hasText(clientDO.getKey()))
            return;

        if (!CommonStatus.inStatus(clientDO.getStatus())) clientDO.setStatus(CommonStatus.DISABLE.getStatus());

        clientMapper.updateByPrimaryKeySelective(clientDO);
    }

    @Override
    public Page<ClientDO> clients(String code, String name, Integer status, Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SMSConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SMSConstant.DEFAULT_PAGE_SIZE : pageSize;

        ClientDOCriteria criterion = new ClientDOCriteria();
        ClientDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());

        if (StringUtils.hasText(code)) criteria.andCodeLike("%" + code + "%");
        if (StringUtils.hasText(name)) criteria.andNameLike("%" + name + "%");
        if (CommonStatus.inStatus(status)) criteria.andStatusEqualTo(status);

        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ClientDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ClientDO> clientDOList = clientMapper.selectByExample(criterion);

        if (CollectionUtils.isEmpty(clientDOList))
            return new Page<>(clientDOList, 0, currentPage, pageSize);

        return new Page<>(clientDOList, Long.valueOf(page.getTotal()).intValue(), currentPage, pageSize);
    }

    @Override
    public ClientDO client(Integer id) {
        if (null == id) return null;

        ClientDO clientDO = clientMapper.selectByPrimaryKey(id);

        return null == clientDO || clientDO.getDeleteFlag().equals(DeleteFlag.DELETED.getDeleteFlag())
                ? null : clientDO;
    }

    @Override
    public List<ClientDO> clientWithinDeletedByCode(String code) {
        if (!StringUtils.hasText(code)) return null;

        ClientDOCriteria criteria = new ClientDOCriteria();
        criteria.createCriteria().andCodeEqualTo(code);

        List<ClientDO> clientDOList = clientMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(clientDOList)) return null;
        return clientDOList;
    }
}
