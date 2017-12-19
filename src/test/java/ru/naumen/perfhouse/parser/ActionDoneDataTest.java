package ru.naumen.perfhouse.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.naumen.perfhouse.plugins.sdng.parser.ActionDoneDataParser;
import ru.naumen.perfhouse.plugins.sdng.parser.SdngData;

public class ActionDoneDataTest {
    private ActionDoneDataParser parser;
    private SdngData dataSet;

    @Before
    public void init() {
        parser = new ActionDoneDataParser();
        dataSet = new SdngData();
    }

    @Test
    public void mustParseAddAction() {
        //when
        parser.parseLine("Done(10): AddObjectAction", dataSet);

        //then
        Assert.assertEquals(1, dataSet.getActionDone().getAddObjectActions());
    }

    @Test
    public void mustParseFormActions() {
        //when
        parser.parseLine("Done(10): GetFormAction", dataSet);
        parser.parseLine("Done(1): GetAddFormContextDataAction", dataSet);

        //then
        Assert.assertEquals(2, dataSet.getActionDone().getFormActions());
    }

    @Test
    public void mustParseEditObject() {
        //when
        parser.parseLine("Done(10): EditObjectAction", dataSet);

        //then
        Assert.assertEquals(1, dataSet.getActionDone().getEditObjectsActions());
    }

    @Test
    public void mustParseGetCatalogs() {
        //when
        parser.parseLine("Done(10): GetCatalogsAction", dataSet);

        //then
        Assert.assertEquals(1, dataSet.getActionDone().getCatalogsActions());
    }

    @Test
    public void mustParseSearchObject(){
        //when
        parser.parseLine("Done(10): GetPossibleAgreementsChildsSearchAction", dataSet);
        parser.parseLine("Done(10): TreeSearchAction", dataSet);
        parser.parseLine("Done(10): GetSearchResultAction", dataSet);
        parser.parseLine("Done(10): GetSimpleSearchResultsAction", dataSet);
        parser.parseLine("Done(10): SimpleSearchAction", dataSet);
        parser.parseLine("Done(10): ExtendedSearchByStringAction", dataSet);
        parser.parseLine("Done(10): ExtendedSearchByFilterAction", dataSet);

        //then
        Assert.assertEquals(7, dataSet.getActionDone().getSearchActions());
    }

    @Test
    public void mustParseGetList(){
        //when:
        parser.parseLine("Done(10): GetDtObjectListAction", dataSet);
        parser.parseLine("Done(10): GetPossibleCaseListValueAction", dataSet);
        parser.parseLine("Done(10): GetPossibleAgreementsTreeListActions", dataSet);
        parser.parseLine("Done(10): GetCountForObjectListAction", dataSet);
        parser.parseLine("Done(10): GetDataForObjectListAction", dataSet);
        parser.parseLine("Done(10): GetPossibleAgreementsListActions", dataSet);
        parser.parseLine("Done(10): GetDtObjectForRelObjListAction", dataSet);

        //then:
        Assert.assertEquals(7, dataSet.getActionDone().getListActions());
    }

    @Test
    public void mustParseComment(){
        //when:
        parser.parseLine("Done(10): EditCommentAction", dataSet);
        parser.parseLine("Done(10): ChangeResponsibleWithAddCommentAction", dataSet);
        parser.parseLine("Done(10): ShowMoreCommentAttrsAction", dataSet);
        parser.parseLine("Done(10): CheckObjectsExceedsCommentsAmountAction", dataSet);
        parser.parseLine("Done(10): GetAddCommentPermissionAction", dataSet);
        parser.parseLine("Done(10): GetCommentDtObjectTemplateAction", dataSet);

        //then:
        Assert.assertEquals(6, dataSet.getActionDone().getCommentActions());
    }

    @Test
    public void mustParseDtObject(){
        //when:
        parser.parseLine("Done(10): GetVisibleDtObjectAction", dataSet);
        parser.parseLine("Done(10): GetDtObjectsAction", dataSet);
        parser.parseLine("Done(10): GetDtObjectTreeSelectionStateAction", dataSet);
        parser.parseLine("Done(10): AbstractGetDtObjectTemplateAction", dataSet);
        parser.parseLine("Done(10): GetDtObjectTemplateAction", dataSet);

        //then:
        Assert.assertEquals(5, dataSet.getActionDone().getDtObjectActions());
    }

}
