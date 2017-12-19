package ru.naumen.perfhouse.plugins.sdng;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import ru.naumen.perfhouse.statdata.PluginConstants;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

@Component
public class ActionDoneConstants implements PluginConstants {
    public final String ADD_ACTIONS = "addActions";
    public final String EDIT_ACTIONS = "editActions";
    public final String GET_CATALOGS_ACTIONS = "getCatalogsAction";
    public final String LIST_ACTIONS = "listActions";
    public final String COMMENT_ACTIONS = "commentActions";
    public final String GET_FORM_ACTIONS = "getFormActions";
    public final String GET_DT_OBJECT_ACTIONS = "getDtObjectActions";
    public final String SEARCH_ACTIONS = "searchActions";
    public final String ACTIONS_COUNT = "count";

    public List<String> getProps() {
        return Lists.newArrayList(TIME, ADD_ACTIONS, EDIT_ACTIONS, GET_CATALOGS_ACTIONS, LIST_ACTIONS, COMMENT_ACTIONS, ACTIONS_COUNT,
                GET_FORM_ACTIONS, GET_DT_OBJECT_ACTIONS, SEARCH_ACTIONS);
    }
}
