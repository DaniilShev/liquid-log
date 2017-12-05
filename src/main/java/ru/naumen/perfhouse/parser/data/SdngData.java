package ru.naumen.perfhouse.parser.data;

import ru.naumen.perfhouse.parser.DataSet;

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