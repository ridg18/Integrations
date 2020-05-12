package com.kenshoo.integration.service;

import com.google.common.collect.Lists;
import com.kenshoo.integration.beans.Integration;
import com.kenshoo.integration.repository.IntegrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created By: raga
 * Date: 10/05/2020
 * Time: 19:59
 */
@Service
public class IntegrationService implements Serializable {

    @Autowired
    private IntegrationDao integrationDao;

    @Autowired
    KsNormalizerClient ksNormalizerClient;


    /**
     * Inserts data into the integrations table
     * ​@param​ ksId a ks id, might be not normalized
     * ​@param​ data
     */
    public int insertIntegration(String ksId, String data) throws IOException {
        return integrationDao.insert(ksNormalizerClient.normalize(ksId), data);

    }


    /**
     * Returns all integrations having the provided ks id
     * ​@param​ ksId a ks id, might be not normalized
     * ​@return​ list of all the integrations having the provided ks id
     */
    public List<Integration> fetchIntegrationsByKsId(String ksId) {
        return integrationDao.fetchByKsId(ksId);
    }


    /**
     * Updates all rows in integrations table with normalized ks id
     * ​@return​ number of affected rows
     * <p>
     * <p>
     * solution 1: is to run on all Integration table and send each row to the normalize method and update the ksId
     * <p>
     * solution 2: is to create table with ks_Id and integration_Id and all the Integration Id that appears in the
     * integration table and not in this table should be normalized
     */
    public int migrate() {
        AtomicInteger count = new AtomicInteger();
        List<Integration> integrations = Lists.newArrayList(integrationDao.fetchAll());
        if(CollectionUtils.isEmpty(integrations)){
            return 0;
        }
        integrations.stream().forEach(integration -> {
            try {
                count.getAndAdd(integrationDao.update(integration.getId(),
                        ksNormalizerClient.normalize(integration.getKs_id()),
                        integration.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return count.get();
    }
}
