package com.kenshoo.integration.repository;

import com.kenshoo.integration.beans.Integration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By: raga
 * Date: 10/05/2020
 * Time: 20:05
 */
@Repository
public interface IntegrationDao extends CrudRepository {

    /**
     * ​@return​ number of affected rows (0 or 1)
     */
    int insert(String ksId, String data);

    /**
     * ​@return​ number of affected rows (0 or 1)
     */

    int update(int id, String ksId, String data);


    /**
     * ​@return​ number of affected rows
     */

    int updateKsId(String oldKsId, String newksId);


    Integration fetchById(int id);


    List<Integration> fetchByKsId(String ksId);


    List<Integration> fetchAll();

}
