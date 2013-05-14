package com.johnathanmarksmith.hellospring.bean;

/**
 * Date:   4/25/13 / 9:50 AM
 * Author: Johnathan Mark Smith
 * Email:  john@johnathanmarksmith.com
 * <p/>
 * Comments:
 * <p/>
 * This is just a very small bean for showing this example
 *
 */


public class HelloWorld {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}