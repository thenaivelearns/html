package com.igw.market.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ClassName: WorkDateInfoRequest
 * Description： TODO
 * Author: zhubengang
 * Date: Created in 2021/9/14 10:47
 * Version: 1.0.0
 */
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class WorkDateInfoRequest {
    private String marketCode;
    private String queryDate;
    private String expression;
    private String outFormat;

    public WorkDateInfoRequest() {
    }

    public String getMarketCode() {
        return this.marketCode;
    }

    public String getQueryDate() {
        return this.queryDate;
    }

    public String getExpression() {
        return this.expression;
    }

    public String getOutFormat() {
        return this.outFormat;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setOutFormat(String outFormat) {
        this.outFormat = outFormat;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WorkDateInfoRequest)) {
            return false;
        } else {
            WorkDateInfoRequest other = (WorkDateInfoRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$marketCode = this.getMarketCode();
                    Object other$marketCode = other.getMarketCode();
                    if (this$marketCode == null) {
                        if (other$marketCode == null) {
                            break label59;
                        }
                    } else if (this$marketCode.equals(other$marketCode)) {
                        break label59;
                    }

                    return false;
                }

                Object this$queryDate = this.getQueryDate();
                Object other$queryDate = other.getQueryDate();
                if (this$queryDate == null) {
                    if (other$queryDate != null) {
                        return false;
                    }
                } else if (!this$queryDate.equals(other$queryDate)) {
                    return false;
                }

                Object this$expression = this.getExpression();
                Object other$expression = other.getExpression();
                if (this$expression == null) {
                    if (other$expression != null) {
                        return false;
                    }
                } else if (!this$expression.equals(other$expression)) {
                    return false;
                }

                Object this$outFormat = this.getOutFormat();
                Object other$outFormat = other.getOutFormat();
                if (this$outFormat == null) {
                    if (other$outFormat != null) {
                        return false;
                    }
                } else if (!this$outFormat.equals(other$outFormat)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof WorkDateInfoRequest;
    }

   /* public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $marketCode = this.getMarketCode();
        int result = result * 59 + ($marketCode == null ? 43 : $marketCode.hashCode());
        Object $queryDate = this.getQueryDate();
        result = result * 59 + ($queryDate == null ? 43 : $queryDate.hashCode());
        Object $expression = this.getExpression();
        result = result * 59 + ($expression == null ? 43 : $expression.hashCode());
        Object $outFormat = this.getOutFormat();
        result = result * 59 + ($outFormat == null ? 43 : $outFormat.hashCode());
        return result;
    }*/

    public String toString() {
        return "WorkDateInfoRequest(marketCode=" + this.getMarketCode() + ", queryDate=" + this.getQueryDate() + ", expression=" + this.getExpression() + ", outFormat=" + this.getOutFormat() + ")";
    }
}
