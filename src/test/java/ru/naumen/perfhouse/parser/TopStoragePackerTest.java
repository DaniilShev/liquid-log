package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.factories.TopParserFactory;
import ru.naumen.perfhouse.parser.packers.TopStoragePacker;
import ru.naumen.perfhouse.parser.data.TopData;

public class TopStoragePackerTest {
    private InfluxDAO mockedInfluxDao;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void init() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);
        ParserFactory parserFactory = new TopParserFactory();
        StoragePacker storagePacker = new TopStoragePacker(mockedInfluxDao);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);

        storage = new Storage(mockedInfluxDao);
        storage.init(parserFactory, storagePacker, "test", false);
    }

    @Test
    public void mustStore() {
        //when
        TopData firstSet = (TopData)storage.get(1);
        firstSet.addCpu(1.5);
        firstSet.addLa(3.2);
        firstSet.addMem(2);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao)
                .storeTop(batchPoints, "test", 1, firstSet);
    }

    @Test
    public void mustNotStore() {
        //when
        storage.get(1);
        storage.close();

        //then
        Mockito.verify(mockedInfluxDao, Mockito.never()).storeTop(
                Mockito.any(),Mockito.any(), Mockito.anyLong(), Mockito.any());
    }
}
