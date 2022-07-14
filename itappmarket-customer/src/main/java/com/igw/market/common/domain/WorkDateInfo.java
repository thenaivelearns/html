package com.igw.market.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ClassName: WorkDateInfo
 * Description：
 * Author: zhubengang
 * Date: Created in 2021/9/14 10:38
 * Version: 1.0.0
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class WorkDateInfo {
    private String queryDate;
    private String workFlag;

    public WorkDateInfo() {
    }

    public String getQueryDate() {
        return this.queryDate;
    }

    public String getWorkFlag() {
        return this.workFlag;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public void setWorkFlag(String workFlag) {
        this.workFlag = workFlag;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WorkDateInfo)) {
            return false;
        } else {
            WorkDateInfo other = (WorkDateInfo)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$queryDate = this.getQueryDate();
                Object other$queryDate = other.getQueryDate();
                if (this$queryDate == null) {
                    if (other$queryDate != null) {
                        return false;
                    }
                } else if (!this$queryDate.equals(other$queryDate)) {
                    return false;
                }

                Object this$workFlag = this.getWorkFlag();
                Object other$workFlag = other.getWorkFlag();
                if (this$workFlag == null) {
                    if (other$workFlag != null) {
                        return false;
                    }
                } else if (!this$workFlag.equals(other$workFlag)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof WorkDateInfo;
    }

    /*public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $queryDate = this.getQueryDate();
        int result = result * 59 + ($queryDate == null ? 43 : $queryDate.hashCode());
        Object $workFlag = this.getWorkFlag();
        result = result * 59 + ($workFlag == null ? 43 : $workFlag.hashCode());
        return result;
    }*/

    public String toString() {
        return "WorkDateInfo(queryDate=" + this.getQueryDate() + ", workFlag=" + this.getWorkFlag() + ")";
    }
}
