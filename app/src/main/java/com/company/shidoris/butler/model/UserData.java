package com.company.shidoris.butler.model;

import java.util.List;

/**
 * Created by Erik on 17/11/2017.
 */

public class UserData {

    private String currenRequesr;
    private List<String> RequestIds;

    public UserData() {
    }

    public String getCurrenRequesr() {
        return currenRequesr;
    }

    public void setCurrenRequesr(String currenRequesr) {
        this.currenRequesr = currenRequesr;
    }

    public List<String> getRequestIds() {
        return RequestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        RequestIds = requestIds;
    }
}
