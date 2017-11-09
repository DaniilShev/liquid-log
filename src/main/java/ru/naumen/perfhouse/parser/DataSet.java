package ru.naumen.perfhouse.parser;

/**
 * Created by doki on 22.10.16.
 */
public class DataSet
{
    private ActionDoneParser actionsDone;
    private ErrorParser errors;
    private GCData gcData;
    private TopData cpuData;

    public DataSet()
    {
        actionsDone = new ActionDoneParser();
        errors = new ErrorParser();
        gcData = new GCData();
        cpuData = new TopData();
    }

    public ActionDoneParser getActionsDone()
    {
        return actionsDone;
    }

    public ErrorParser getErrors()
    {
        return errors;
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
