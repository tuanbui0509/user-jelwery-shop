package com.example.de_tai_di_dong.model;

import android.content.Intent;

public class ResultLogin{
   private String message;
    private int result;

    public ResultLogin(String message, int result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}