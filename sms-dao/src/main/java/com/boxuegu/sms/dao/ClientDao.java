package com.boxuegu.sms.dao;

import com.boxuegu.sms.domain.ClientDO;
import com.boxuegu.sms.utils.Page;

import java.util.List;

/**
 * 接入方 Dao
 *
 * @author leonzhangxf 20180906
 */
public interface ClientDao {


    ClientDO saveClient(ClientDO clientDO);


    void deleteClient(Integer id);


    void updateClient(ClientDO clientDO);


    Page<ClientDO> clients(String code, String name, Integer status, Integer currentPage, Integer pageSize);


    ClientDO client(Integer id);


    List<ClientDO> clientWithinDeletedByCode(String code);
}
