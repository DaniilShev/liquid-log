package ru.naumen.perfhouse.plugins.sdng.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.plugins.sdng.ActionDoneConstants;
import ru.naumen.perfhouse.plugins.sdng.ResponseTimesConstants;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.concurrent.TimeUnit;

@Service
@ParsingMode(name="sdng")
public class SdngStoragePacker implements StoragePacker<SdngData> {
    private ActionDoneConstants actionDoneConstants;
    private ResponseTimesConstants responseTimesConstants;

    @Autowired
    public SdngStoragePacker(ActionDoneConstants actionDoneConstants,
                             ResponseTimesConstants responseTimesConstants) {
        this.actionDoneConstants = actionDoneConstants;
        this.responseTimesConstants = responseTimesConstants;
    }

    public void store(BatchPoints points, String currentDb, long currentKey,
                      SdngData currentSet, boolean printLog) {

        ActionDoneData dones = currentSet.getActionDone();
        dones.calculate();

        ErrorData erros = currentSet.getErrors();

        if (printLog)
        {
            if (points.getPoints().size() == 0) {
                System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
            }

            System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n",
                    currentKey, dones.getCount(), dones.getMin(),
                    dones.getMean(), dones.getStddev(),
                    dones.getPercent50(), dones.getPercent95(),
                    dones.getPercent99(), dones.getPercent999(),
                    dones.getMax(), erros.getErrorCount()));
        }

        if (!dones.isNan())
        {
            Point point = Point.measurement(Constants.MEASUREMENT_NAME)
                    .time(currentKey, TimeUnit.MILLISECONDS)
                    .addField(responseTimesConstants.COUNT, dones.getCount())
                    .addField("min", dones.getMin())
                    .addField(responseTimesConstants.MEAN, dones.getMean())
                    .addField(responseTimesConstants.STDDEV, dones.getStddev())
                    .addField(responseTimesConstants.PERCENTILE50, dones.getPercent50())
                    .addField(responseTimesConstants.PERCENTILE95, dones.getPercent95())
                    .addField(responseTimesConstants.PERCENTILE99, dones.getPercent99())
                    .addField(responseTimesConstants.PERCENTILE999, dones.getPercent999())
                    .addField(responseTimesConstants.MAX, dones.getMax())
                    .addField(responseTimesConstants.ERRORS, erros.getErrorCount())
                    .addField(actionDoneConstants.ADD_ACTIONS, dones.getAddObjectActions())
                    .addField(actionDoneConstants.EDIT_ACTIONS, dones.getEditObjectsActions())
                    .addField(actionDoneConstants.GET_CATALOGS_ACTIONS, dones.getCatalogsActions())
                    .addField(actionDoneConstants.LIST_ACTIONS, dones.getListActions())
                    .addField(actionDoneConstants.COMMENT_ACTIONS, dones.getCommentActions())
                    .addField(actionDoneConstants.GET_FORM_ACTIONS, dones.getFormActions())
                    .addField(actionDoneConstants.GET_DT_OBJECT_ACTIONS, dones.getDtObjectActions())
                    .addField(actionDoneConstants.SEARCH_ACTIONS, dones.getSearchActions())
                    .build();

            points.getPoints().add(point);
        }
    }
}
