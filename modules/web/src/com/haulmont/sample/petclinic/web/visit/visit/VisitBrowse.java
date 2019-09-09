package com.haulmont.sample.petclinic.web.visit.visit;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import com.haulmont.sample.petclinic.entity.visit.Visit;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@UiController("petclinic_Visit.browse")
@UiDescriptor("visit-browse.xml")
@LookupComponent("visitsTable")
@LoadDataBeforeShow
public class VisitBrowse extends StandardLookup<Visit> {

    @Inject
    protected ReportGuiManager reportGuiManager;

    @Inject
    protected DataManager dataManager;

    @Inject
    protected TimeSource timeSource;

    @Subscribe("visitsTable.lastMonthReport")
    protected void onVisitsTableLastMonthReport(Action.ActionPerformedEvent event) {

        TimeRange visitDateTimeRange =
                MonthYearValue
                        .fromDate(today())
                        .minusMonths(1);

        reportGuiManager.printReport(
                loadReportByCode("visits-by-period"),
                ParamsMap.of(
                        "visitDateRangeStart", visitDateTimeRange.getStart(),
                        "visitDateRangeEnd", visitDateTimeRange.getEnd()
                        )
        );

    }

    private LocalDate today() {
        return timeSource.now().toLocalDate();
    }

    private Report loadReportByCode(String reportCode) {
        return dataManager.load(Report.class)
                .query("select e from report$Report e where e.code = :reportCode")
                .parameter("reportCode", reportCode)
                .one();
    }

    @Subscribe("visitsTable.lastQuarterReport")
    protected void onVisitsTableLastQuarterReport(Action.ActionPerformedEvent event) {

        TimeRange visitDateTimeRange =
                QuarterYearValue
                        .fromDate(today())
                        .minusQuarters(1);

        reportGuiManager.printReport(
                loadReportByCode("visits-by-period"),
                ParamsMap.of(
                        "visitDateRangeStart", visitDateTimeRange.getStart(),
                        "visitDateRangeEnd", visitDateTimeRange.getEnd()
                )
        );
    }

}