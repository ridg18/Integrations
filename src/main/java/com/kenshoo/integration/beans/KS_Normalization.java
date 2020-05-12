package com.kenshoo.integration.beans;

import lombok.Data;

import javax.persistence.*;

/**
 * Created By: raga
 * Date: 10/05/2020
 * Time: 19:57
 */
@Data
@Entity
public class KS_Normalization {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "integration")
    private Integration integration;
    private String ks_id;
    private String normalized_id;

    public KS_Normalization(Integration integration, String ks_id, String normalized_id) {
        this.integration = integration;
        this.ks_id = ks_id;
        this.normalized_id = normalized_id;
    }
}
