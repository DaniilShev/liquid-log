package ru.naumen.perfhouse.parser.sets;

import ru.naumen.perfhouse.parser.DataSet;
import ru.naumen.perfhouse.parser.data.TopData;

public class TopDataSet implements DataSet
{
    private TopData topData;

    public TopDataSet() {
        topData = new TopData();
    }

    @Override
    public TopData getData() {
        return topData;
    }
}
