package com.company.shidoris.butler.model;

import java.util.List;

/**
 * Created by Erik on 17/11/2017.
 */

public class UserData {

    private Request currentRequest;
    private List<String> requestIds;


    public UserData() {
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }
}
