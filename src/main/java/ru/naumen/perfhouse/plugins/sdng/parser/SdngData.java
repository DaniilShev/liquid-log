package ru.naumen.perfhouse.plugins.sdng.parser;

import ru.naumen.perfhouse.parser.interfaces.DataSet;
import ru.naumen.perfhouse.plugins.sdng.parser.ActionDoneData;
import ru.naumen.perfhouse.plugins.sdng.parser.ErrorData;

public class SdngData implements DataSet {
    private ActionDoneData actionDoneData;
    private ErrorData errorData;

    public SdngData() {
        actionDoneData = new ActionDoneData();
        errorData = new ErrorData();
    }

    public ActionDoneData getActionDone() {
        return actionDoneData;
    }

    public ErrorData getErrors() {
        return errorData;
    }
}