package ru.naumen.perfhouse.plugins.sdng.parser;

/**
 * Created by doki on 22.10.16.
 */
public class ErrorData
{
    private long warnCount = 0;
    private long errorCount = 0;
    private long fatalCount = 0;

    public void incWarnCount() {
        ++warnCount;
    }

    public void incErrorCount() {
        ++errorCount;
    }

    public void incFatalCount() {
        ++fatalCount;
    }

    public long getWarnCount()
    {
        return warnCount;
    }

    public long getErrorCount()
    {
        return errorCount;
    }

    public long getFatalCount()
    {
        return fatalCount;
    }
}
