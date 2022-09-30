package com.example.festivalcarpet.model;

public interface OnApiResponse {

    void onResponseListener(Object object);
    void onFailureListener(Throwable throwable);
}
