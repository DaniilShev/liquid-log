package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAOInterface;

public class StorageTest {
    private InfluxDAOInterface mockedInfluxDao;
    private BatchPoints batchPoint;

    @Before
    public void initInfluxDAO() {
        mockedInfluxDao = Mockito.mock(InfluxDAOInterface.class);

        batchPoint = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoint);
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
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", true);

        //when
        DataSet firstSet = storage.get(1);
        DataSet secondSet = storage.get(2);

        //then
        Assert.assertNotEquals(firstSet, secondSet);
    }

    @Test
    public void mustSave() {
        //given
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", false);

        //when
        storage.save();

        //then
        Mockito.verify(mockedInfluxDao, Mockito.times(1))
                .writeBatch(batchPoint);
    }

    @Test
    public void mustStoreGc() {
        //given
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", false);

        //when
        DataSet firstSet = storage.get(1);
        firstSet.getGc().addValue(1.5);
        DataSet secondSet = storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao, Mockito.times(1))
                .storeGc(batchPoint,"test", 1, firstSet.getGc());
    }

    @Test
    public void mustStoreTop() {
        //given
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", false);

        //when
        DataSet firstSet = storage.get(1);
        firstSet.getCpuData().addCpu(1.5);
        firstSet.getCpuData().addLa(3.2);
        firstSet.getCpuData().addMem(2);
        DataSet secondSet = storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao, Mockito.times(1))
                .storeTop(batchPoint, "test", 1, firstSet.getCpuData());
    }

    @Test
    public void mustStoreActions() {
        //given
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", false);

        //when
        DataSet firstSet = storage.get(1);
        firstSet.getActionsDone().parseLine("Done(10): AddObjectAction");
        DataSet secondSet = storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao, Mockito.times(1))
                .storeActionsFromLog(batchPoint, "test", 1,
                        firstSet.getActionsDone(), firstSet.getErrors());
    }
}