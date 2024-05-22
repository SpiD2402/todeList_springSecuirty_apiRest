package com.example.todelist.response;

import java.util.Optional;

public class ResponseDatos {

    public boolean success;
    public int code;
    public String message;

    public Optional data;


    public ResponseDatos(boolean success, int code, String message, Optional data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDatos() {

    }

    public ResponseDatos error( int code, String message, Optional data) {
            return  new ResponseDatos(false,code,message,data);
    }
    public ResponseDatos succes( int code, String message, Optional data) {
        return  new ResponseDatos(true,code,message,data);
    }
}
