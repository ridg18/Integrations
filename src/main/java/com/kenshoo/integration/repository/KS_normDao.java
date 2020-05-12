package com.kenshoo.integration.repository;

import com.kenshoo.integration.beans.KS_Normalization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created By: raga
 * Date: 10/05/2020
 * Time: 23:33
 */
public interface KS_normDao extends CrudRepository<KS_Normalization, Long> {

    @Query("From KS_Normalization ks WHERE ks.ks_id=:ksId OR ks.normalized_id=:ksId ")
    List<KS_Normalization> fetchByKSId(String ksId);
}
