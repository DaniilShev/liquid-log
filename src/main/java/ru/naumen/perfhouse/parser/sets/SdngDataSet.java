package ru.naumen.perfhouse.parser.sets;

import ru.naumen.perfhouse.parser.DataSet;
import ru.naumen.perfhouse.parser.Tuple;
import ru.naumen.perfhouse.parser.data.ActionDoneData;
import ru.naumen.perfhouse.parser.data.ErrorData;

public class SdngDataSet implements DataSet {
    private Tuple<ActionDoneData, ErrorData> sdngData;

    public SdngDataSet() {
        sdngData = new Tuple<>(new ActionDoneData(), new ErrorData());
    }

    @Override
    public Tuple<ActionDoneData, ErrorData> getData() {
        return sdngData;
    }
}