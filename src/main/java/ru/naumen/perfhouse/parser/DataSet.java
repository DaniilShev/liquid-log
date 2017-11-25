package ru.naumen.perfhouse.parser;

import ru.naumen.perfhouse.parser.data.*;

/**
 * Created by doki on 22.10.16.
 */
public class DataSet
{
    private ActionDoneData actionsDoneData;
    private ErrorData errorsData;
    private GCData gcData;
    private TopData cpuData;

    public DataSet()
    {
        actionsDoneData = new ActionDoneData();
        errorsData = new ErrorData();
        gcData = new GCData();
        cpuData = new TopData();
    }

    public ActionDoneData getActionsDone()
    {
        return actionsDoneData;
    }

    public ErrorData getErrors()
    {
        return errorsData;
    }

    public GCData getGc()
    {
        return gcData;
    }

    public TopData getCpuData()
    {
        return cpuData;
    }
}
