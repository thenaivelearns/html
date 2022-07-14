package com.igw.market.query.domain.dto;

import com.igw.restful.util.IgwPage;

public class IgwData<T> {
    private IgwPage page;
    private T body;

    public IgwData() {
    }

    public IgwPage getPage() {
        return this.page;
    }

    public T getBody() {
        return this.body;
    }

    public void setPage(IgwPage page) {
        this.page = page;
    }

    public void setBody(T body) {
        this.body = body;
    }


    public String toString() {
        return "IgwData(page=" + this.getPage() + ", body=" + this.getBody() + ")";
    }
}
