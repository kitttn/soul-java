package io.soulsdk.model.tasks;

import io.soulsdk.model.requests.ReportUserREQ;

/**
 * Created by Buiarov on 24/03/16.
 */
public class ReportTask extends GeneralTask{

    private String userId;
    private ReportUserREQ reportUserREQ;

    public ReportTask(String userId, ReportUserREQ reportUserREQ) {
        this.setReportUserREQ(reportUserREQ);
        this.setUserId(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ReportUserREQ getReportUserREQ() {
        return reportUserREQ;
    }

    public void setReportUserREQ(ReportUserREQ reportUserREQ) {
        this.reportUserREQ = reportUserREQ;
    }
}
