package ru.naumen.perfhouse.plugins.sdng;

import com.google.common.collect.Lists;

import java.util.List;

import static ru.naumen.perfhouse.statdata.Constants.TIME;

public class ActionDoneConstants {
    private ActionDoneConstants() {

    }

    public static final String ADD_ACTIONS = "addActions";
    public static final String EDIT_ACTIONS = "editActions";
    public static final String GET_CATALOGS_ACTIONS = "getCatalogsAction";
    public static final String LIST_ACTIONS = "listActions";
    public static final String COMMENT_ACTIONS = "commentActions";
    public static final String GET_FORM_ACTIONS = "getFormActions";
    public static final String GET_DT_OBJECT_ACTIONS = "getDtObjectActions";
    public static final String SEARCH_ACTIONS = "searchActions";
    public static final String ACTIONS_COUNT = "count";

    static List<String> getProps() {
        return Lists.newArrayList(TIME, ADD_ACTIONS, EDIT_ACTIONS, GET_CATALOGS_ACTIONS, LIST_ACTIONS, COMMENT_ACTIONS, ACTIONS_COUNT,
                GET_FORM_ACTIONS, GET_DT_OBJECT_ACTIONS, SEARCH_ACTIONS);
    }
}
