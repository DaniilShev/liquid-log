package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.factories.SdngParserFactory;
import ru.naumen.perfhouse.parser.packers.SdngStoragePacker;
import ru.naumen.perfhouse.parser.sets.SdngDataSet;

public class SdngStoragePackerTest {
    private InfluxDAO mockedInfluxDao;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void init() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);
        ParserFactory parserFactory = new SdngParserFactory();
        StoragePacker storagePacker = new SdngStoragePacker(mockedInfluxDao);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);

        storage = new Storage(mockedInfluxDao);
        storage.init(parserFactory, storagePacker, "test", false);
    }

    @Test
    public void mustStore() {
        //when
        SdngDataSet firstSet = (SdngDataSet)storage.get(1);
        firstSet.getData().getX().getTimes().add(10);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao).storeActionsFromLog(batchPoints, "test",
                1, firstSet.getData().getX(), firstSet.getData().getY());
    }

    @Test
    public void mustNotStore() {
        //when
        storage.get(1);
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao, Mockito.never()).storeActionsFromLog(
                Mockito.any(),Mockito.any(), Mockito.anyLong(), Mockito.any(),
                Mockito.any());
    }
}
