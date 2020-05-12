package com.kenshoo.integration.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created By: raga
 * Date: 10/05/2020
 * Time: 19:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Integration {

    @Id
    @GeneratedValue
    private int id;
    private String ks_id;
    private String data;

    public Integration(String ks_id, String data) {
        this.ks_id = ks_id;
        this.data = data;
    }


}
