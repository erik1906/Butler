package com.company.shidoris.butler.model;

import java.util.Map;

/**
 * Created by Erik on 17/11/2017.
 */

public class RequestsData {

    private Map<String, Request> requestsdata;

    public RequestsData() {
    }

    public Map<String, Request> getRequestsdata() {
        return requestsdata;
    }

    public void setRequestsdata(Map<String, Request> requestsdata) {
        this.requestsdata = requestsdata;
    }
}
