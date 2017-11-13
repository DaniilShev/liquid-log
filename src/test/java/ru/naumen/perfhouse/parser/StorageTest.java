package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;

public class StorageTest {
    private InfluxDAO mockedInfluxDao;
    private BatchPoints batchPoint;

    @Before
    public void initInfluxDAO() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);

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
        Mockito.verify(mockedInfluxDao).writeBatch(batchPoint);
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
        Mockito.verify(mockedInfluxDao)
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
        Mockito.verify(mockedInfluxDao)
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
        Mockito.verify(mockedInfluxDao).storeActionsFromLog(batchPoint, "test", 1,
                        firstSet.getActionsDone(), firstSet.getErrors());
    }

    @Test
    public void mustSaveLastSet() {
        //given
        Storage storage = new Storage(mockedInfluxDao);
        storage.init("test", false);

        //when
        DataSet firstSet = storage.get(1);
        firstSet.getActionsDone().parseLine("Done(10): AddObjectAction");
        DataSet secondSet = storage.get(2);
        secondSet.getActionsDone().parseLine("Done(5): GetCatalogsAction");
        storage.save();

        //then
        Mockito.verify(mockedInfluxDao).storeActionsFromLog(batchPoint, "test", 1,
                        firstSet.getActionsDone(), firstSet.getErrors());
        Mockito.verify(mockedInfluxDao).storeActionsFromLog(batchPoint, "test", 2,
                        secondSet.getActionsDone(), secondSet.getErrors());
    }
}
