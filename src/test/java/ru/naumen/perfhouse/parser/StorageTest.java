package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.sets.SdngDataSet;

public class StorageTest {
    private InfluxDAO mockedInfluxDao;
    private ParserFactory mockedParserFactory;
    private DataPacker mockedDataPacker;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void init() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);
        mockedParserFactory = Mockito.mock(ParserFactory.class);
        mockedDataPacker = Mockito.mock(DataPacker.class);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);
        Mockito.when(mockedParserFactory.getDataSet())
                .thenReturn(new SdngDataSet());

        storage = new Storage(mockedInfluxDao);
        storage.init(mockedParserFactory, mockedDataPacker, "test", false);
    }

    @Test
    public void mustReturnStoredSet() {
        //when
        DataSet firstSet = storage.get(1);
        DataSet firstSetCopy = storage.get(1);

        //then
        Mockito.verify(mockedParserFactory).getDataSet();
    }

    @Test
    public void mustReturnNewSet() {
        //given
        storage.init(mockedParserFactory, mockedDataPacker, "test", true);

        //when
        DataSet firstSet = storage.get(1);
        DataSet secondSet = storage.get(2);

        //then
        Mockito.verify(mockedParserFactory, Mockito.times(2)).getDataSet();
    }

    @Test
    public void mustSave() {
        //when
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao).writeBatch(batchPoints);
    }

    @Test
    public void mustStore() {
        //when
        DataSet firstSet = storage.get(1);
        storage.get(2);

        //then
        Mockito.verify(mockedDataPacker).store(batchPoints, "test", 1, firstSet, false);
    }

    @Test
    public void mustSaveFirstSet() {
        //when
        DataSet firstSet = storage.get(1);
        storage.close();

        //then
        Mockito.verify(mockedDataPacker).store(batchPoints, "test", 1, firstSet, false);
    }

    @Test
    public void mustSaveLastSet() {
        //when
        storage.get(1);
        DataSet secondSet = storage.get(2);
        storage.close();

        //then
        Mockito.verify(mockedDataPacker).store(batchPoints, "test", 2, secondSet, false);
    }
}
