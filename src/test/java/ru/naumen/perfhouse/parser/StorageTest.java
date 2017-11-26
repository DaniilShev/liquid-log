package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;

public class StorageTest {
    private InfluxDAO mockedInfluxDao;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void initInfluxDAO() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);

        storage = new Storage(mockedInfluxDao);
        storage.init("test", false);
    }

    @Test
    public void mustReturnStoredSet() {
        //given
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", false);

        //when
        DataSet firstSet = storage.get(1);
        DataSet firstSetCopy = storage.get(1);

        //then
        Assert.assertEquals(firstSet, firstSetCopy);
    }

    @Test
    public void mustReturnNewSet() {
        //given
        storage.init("test", true);

        //when
        DataSet firstSet = storage.get(1);
        DataSet secondSet = storage.get(2);

        //then
        Assert.assertNotEquals(firstSet, secondSet);
    }

    @Test
    public void mustSave() {
        //when
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao).writeBatch(batchPoints);
    }

    @Test
    public void mustStoreGc() {
        //when
        DataSet firstSet = storage.get(1);
        firstSet.getGc().addValue(1.5);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao)
                .storeGc(batchPoints, "test", 1, firstSet.getGc());
    }

    @Test
    public void mustStoreTop() {
        //when
        DataSet firstSet = storage.get(1);
        firstSet.getCpuData().addCpu(1.5);
        firstSet.getCpuData().addLa(3.2);
        firstSet.getCpuData().addMem(2);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao)
                .storeTop(batchPoints, "test", 1, firstSet.getCpuData());
    }

    @Test
    public void mustStoreActions() {
        //when
        DataSet firstSet = storage.get(1);
        firstSet.getActionsDone().getTimes().add(10);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao).storeActionsFromLog(batchPoints, "test",
                1, firstSet.getActionsDone(), firstSet.getErrors());
    }

    @Test
    public void mustNotStore() {
        //when
        storage.get(1);
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao, Mockito.never()).storeGc(
                Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(mockedInfluxDao, Mockito.never()).storeTop(
                Mockito.any(),Mockito.any(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(mockedInfluxDao, Mockito.never()).storeActionsFromLog(
                Mockito.any(),Mockito.any(), Mockito.anyLong(), Mockito.any(),
                Mockito.any());
    }

    @Test
    public void mustSaveFirstSet() {
        //when
        DataSet firstSet = storage.get(1);
        firstSet.getGc().addValue(1.5);
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao)
                .storeGc(batchPoints, "test", 1, firstSet.getGc());
    }

    @Test
    public void mustSaveLastSet() {
        //when
        DataSet firstSet = storage.get(1);
        firstSet.getGc().addValue(1.5);
        DataSet secondSet = storage.get(2);
        secondSet.getGc().addValue(3);
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao)
                .storeGc(batchPoints, "test", 1, firstSet.getGc());
        Mockito.verify(mockedInfluxDao)
                .storeGc(batchPoints, "test", 2, secondSet.getGc());
    }
}
