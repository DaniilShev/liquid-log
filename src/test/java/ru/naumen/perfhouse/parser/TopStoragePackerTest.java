package ru.naumen.perfhouse.parser;

import org.influxdb.dto.BatchPoints;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.factories.TopParserFactory;
import ru.naumen.perfhouse.parser.packers.TopStoragePacker;
import ru.naumen.perfhouse.parser.sets.TopDataSet;

public class TopStoragePackerTest {
    private InfluxDAO mockedInfluxDao;
    private BatchPoints batchPoints;
    private Storage storage;

    @Before
    public void init() {
        mockedInfluxDao = Mockito.mock(InfluxDAO.class);
        ParserFactory parserFactory = new TopParserFactory();
        DataPacker dataPacker = new TopStoragePacker(mockedInfluxDao);

        batchPoints = BatchPoints.database("test").build();
        Mockito.when(mockedInfluxDao.startBatchPoints("test"))
                .thenReturn(batchPoints);

        storage = new Storage(mockedInfluxDao);
        storage.init(parserFactory, dataPacker, "test", false);
    }

    @Test
    public void mustStore() {
        //when
        TopDataSet firstSet = (TopDataSet)storage.get(1);
        firstSet.getData().addCpu(1.5);
        firstSet.getData().addLa(3.2);
        firstSet.getData().addMem(2);
        storage.get(2);

        //then
        Mockito.verify(mockedInfluxDao)
                .storeTop(batchPoints, "test", 1, firstSet.getData());
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
