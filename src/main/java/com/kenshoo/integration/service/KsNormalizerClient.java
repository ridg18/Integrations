package com.kenshoo.integration.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created By: raga
 * Date: 10/05/2020
 * Time: 23:36
 */
@Service
public interface KsNormalizerClient {

    String normalize(String ksId) throws IOException;
}
