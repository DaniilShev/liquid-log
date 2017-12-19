package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.DataSet;
import ru.naumen.perfhouse.parser.interfaces.ParserFactory;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.plugins.sdng.parser.SdngData;

public class StorageTest {
    private InfluxDAO mockedInfluxDao;
    private ParserFactory mockedParserFactory;
    private StoragePacker mockedStoragePacker;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void init() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);
        mockedParserFactory = Mockito.mock(ParserFactory.class);
        mockedStoragePacker = Mockito.mock(StoragePacker.class);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);
        Mockito.when(mockedParserFactory.getDataSet())
                .thenReturn(new SdngData());

        storage = new Storage(mockedInfluxDao);
        storage.init(mockedParserFactory, mockedStoragePacker, "test", false);
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
        storage.init(mockedParserFactory, mockedStoragePacker, "test", true);

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
        Mockito.verify(mockedStoragePacker).store(batchPoints, "test", 1, firstSet, false);
    }

    @Test
    public void mustSaveFirstSet() {
        //when
        DataSet firstSet = storage.get(1);
        storage.close();

        //then
        Mockito.verify(mockedStoragePacker).store(batchPoints, "test", 1, firstSet, false);
    }

    @Test
    public void mustSaveLastSet() {
        //when
        storage.get(1);
        DataSet secondSet = storage.get(2);
        storage.close();

        //then
        Mockito.verify(mockedStoragePacker).store(batchPoints, "test", 2, secondSet, false);
    }
}
