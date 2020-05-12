package com.kenshoo.integration.service;

import com.kenshoo.integration.beans.Integration;
import com.kenshoo.integration.repository.IntegrationDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created By: raga
 * Date: 11/05/2020
 * Time: 11:29
 */
@ExtendWith(MockitoExtension.class)
class IntegrationServiceTest {

    @Mock
    private IntegrationDao integrationDao;

    @Mock
    private KsNormalizerClient ksNormalizerClient;

    @InjectMocks
    IntegrationService integrationService = new IntegrationService();

    @Test
    void insertIntegration() throws IOException {
        when(integrationDao.insert("test", "testData")).thenReturn(1);
        int created = integrationService.insertIntegration("test", "testData");

        assertThat(created).isOne();
    }

    @Test
    void insertWrongIntegration() throws IOException {
        when(integrationDao.insert(null, "testData")).thenReturn(0);
        int created = integrationService.insertIntegration(null, "testData");

        assertThat(created).isZero();
    }

    @Test
    void fetchIntegrationsByKsId() {
        List<Integration> fetchedList = new ArrayList<>();
        fetchedList.add(new Integration("1", "test1"));
        fetchedList.add(new Integration("1", "test2"));
        fetchedList.add(new Integration("1", "test3"));

        when(integrationDao.fetchByKsId("1")).thenReturn(fetchedList);
        List<Integration> fetched = integrationService.fetchIntegrationsByKsId("1");

        assertThat(fetched).isSameAs(fetchedList);
    }

    @Test
    void migrate() throws IOException {

        List<Integration> fetchedList = new ArrayList<>();
        fetchedList.add(new Integration(0, "1", "test1"));
        fetchedList.add(new Integration(1, "2", "test2"));
        fetchedList.add(new Integration(2, "3", "test3"));

        when(integrationDao.fetchAll()).thenReturn(fetchedList);

        when(ksNormalizerClient.normalize("1")).thenReturn("1");
        when(integrationDao.update(fetchedList.get(0).getId(), fetchedList.get(0).getKs_id(),
                fetchedList.get(0).getData())).thenReturn(0);

        when(ksNormalizerClient.normalize("2")).thenReturn("4");
        when(integrationDao.update(fetchedList.get(1).getId(), "4",
                fetchedList.get(1).getData())).thenReturn(1);

        when(ksNormalizerClient.normalize("3")).thenReturn("3");
        when(integrationDao.update(fetchedList.get(2).getId(), fetchedList.get(2).getKs_id(),
                fetchedList.get(2).getData())).thenReturn(0);


        int numberOfMigratedRows = integrationService.migrate();

        assertThat(numberOfMigratedRows).isOne();


    }
}