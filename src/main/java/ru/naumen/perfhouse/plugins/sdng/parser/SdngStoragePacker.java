package ru.naumen.perfhouse.plugins.sdng.parser;

import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.parser.interfaces.StoragePacker;
import ru.naumen.perfhouse.parser.ParsingMode;
import ru.naumen.perfhouse.plugins.sdng.parser.ActionDoneData;
import ru.naumen.perfhouse.plugins.sdng.parser.ErrorData;
import ru.naumen.perfhouse.plugins.sdng.parser.SdngData;
import ru.naumen.perfhouse.statdata.Constants;

import java.util.concurrent.TimeUnit;

import static ru.naumen.perfhouse.plugins.sdng.ActionDoneConstants.*;
import static ru.naumen.perfhouse.plugins.sdng.ActionDoneConstants.SEARCH_ACTIONS;
import static ru.naumen.perfhouse.plugins.sdng.ResponseTimesConstants.*;
import static ru.naumen.perfhouse.plugins.sdng.ResponseTimesConstants.ERRORS;
import static ru.naumen.perfhouse.plugins.sdng.ResponseTimesConstants.MAX;

@Service
@ParsingMode(name="sdng")
public class SdngStoragePacker implements StoragePacker<SdngData> {
    private InfluxDAO influxDAO;

    @Autowired
    public SdngStoragePacker(InfluxDAO influxDAO) {
        this.influxDAO = influxDAO;
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
                    .addField(COUNT, dones.getCount())
                    .addField("min", dones.getMin())
                    .addField(MEAN, dones.getMean())
                    .addField(STDDEV, dones.getStddev())
                    .addField(PERCENTILE50, dones.getPercent50())
                    .addField(PERCENTILE95, dones.getPercent95())
                    .addField(PERCENTILE99, dones.getPercent99())
                    .addField(PERCENTILE999, dones.getPercent999())
                    .addField(MAX, dones.getMax())
                    .addField(ERRORS, erros.getErrorCount())
                    .addField(ADD_ACTIONS, dones.getAddObjectActions())
                    .addField(EDIT_ACTIONS, dones.getEditObjectsActions())
                    .addField(GET_CATALOGS_ACTIONS, dones.getCatalogsActions())
                    .addField(LIST_ACTIONS, dones.getListActions())
                    .addField(COMMENT_ACTIONS, dones.getCommentActions())
                    .addField(GET_FORM_ACTIONS, dones.getFormActions())
                    .addField(GET_DT_OBJECT_ACTIONS, dones.getDtObjectActions())
                    .addField(SEARCH_ACTIONS, dones.getSearchActions())
                    .build();

            points.getPoints().add(point);
        }
    }
}
