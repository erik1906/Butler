package com.company.shidoris.butler.model;

import java.util.List;

/**
 * Created by Erik on 17/11/2017.
 */

public class UserData {

    private String currentRequest;
    private List<String> requestIds;


    public UserData() {
    }

    public String getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(String currentRequest) {
        this.currentRequest = currentRequest;
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }
}
