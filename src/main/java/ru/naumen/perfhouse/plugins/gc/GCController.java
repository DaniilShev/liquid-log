package ru.naumen.perfhouse.plugins.gc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.naumen.perfhouse.controllers.HistoryController;
import ru.naumen.perfhouse.statdata.DataType;
import ru.naumen.perfhouse.tabs.HistoryTab;

import java.text.ParseException;

@Controller
@HistoryTab(name = "Garbage Collection", path = "/gc")
public class GCController extends HistoryController
{
    private static final String GC_VIEW = "gc_history";

    @RequestMapping(path = "/history/{client}/gc/{year}/{month}/{day}")
    public ModelAndView getByDay(@PathVariable("client") String client,
                                @PathVariable(name = "year", required = false) int year,
                                @PathVariable(name = "month", required = false) int month,
                                @PathVariable(name = "day", required = false) int day) throws ParseException
    {
        return getDataAndViewByDate(client, new GCDataType(), year, month, day, GC_VIEW);
    }

    @RequestMapping(path = "/history/{client}/gc/{year}/{month}")
    public ModelAndView getByMonth(@PathVariable("client") String client,
                                  @PathVariable(name = "year", required = false) int year,
                                  @PathVariable(name = "month", required = false) int month) throws ParseException
    {
        return getDataAndViewByDate(client, new GCDataType(), year, month, 0, GC_VIEW, true);
    }

    @RequestMapping(path = "/history/{client}/gc")
    public ModelAndView getLast(@PathVariable("client") String client,
                                  @RequestParam(name = "count", defaultValue = "864") int count) throws ParseException
    {
        return getDataAndView(client, new GCDataType(), count, GC_VIEW);

    }

    @RequestMapping(path = "/history/{client}/custom/gc")
    public ModelAndView getCustom(@PathVariable("client") String client, @RequestParam("from") String from,
                                 @RequestParam("to") String to, @RequestParam("maxResults") int count) throws ParseException
    {
        return getDataAndViewCustom(client, new GCDataType(), from, to, count, GC_VIEW);
    }
}
