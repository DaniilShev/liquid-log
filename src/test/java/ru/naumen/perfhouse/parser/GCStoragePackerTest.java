package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.factories.GCParserFactory;
import ru.naumen.perfhouse.parser.packers.GCStoragePacker;
import ru.naumen.perfhouse.parser.data.GCData;

public class GCStoragePackerTest {
    private InfluxDAO mockedInfluxDao;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void init() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);
        ParserFactory parserFactory = new GCParserFactory();
        StoragePacker storagePacker = new GCStoragePacker(mockedInfluxDao);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);

        storage = new Storage(mockedInfluxDao);
        storage.init(parserFactory, storagePacker, "test", false);
    }

    @Test
    public void mustStore() {
        //when
        GCData firstSet = (GCData)storage.get(1);
        firstSet.addValue(1.5);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao)
                .storeGc(batchPoints, "test", 1, firstSet);
    }

    @Test
    public void mustNotStore() {
        //when
        storage.get(1);
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao, Mockito.never()).storeGc(
                Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any());
    }
}
