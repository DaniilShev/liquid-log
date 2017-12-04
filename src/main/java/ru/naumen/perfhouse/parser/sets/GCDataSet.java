package ru.naumen.perfhouse.parser.sets;

import ru.naumen.perfhouse.parser.DataSet;
import ru.naumen.perfhouse.parser.data.GCData;

public class GCDataSet implements DataSet
{
    private GCData gcData;

    public GCDataSet() {
        gcData = new GCData();
    }

    @Override
    public GCData getData() {
        return gcData;
    }
}
