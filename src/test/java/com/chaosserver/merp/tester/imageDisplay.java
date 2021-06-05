package com.chaosserver.merp.tester;

import java.net.URL;

import com.chaosserver.merp.data.FileNameGetter;

public class imageDisplay {
    public imageDisplay() {
        String file = "merp/data/image/merplogo.gif";
        ClassLoader classLoader = file.getClass().getClassLoader();
        if(classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        if(classLoader == null) {
            System.out.println("ClassLoad is null, Something weird happened");
        }
        URL resourceURL = classLoader.getSystemResource(file);
        System.out.println("Resource: " + resourceURL);

    }

    public static void main (String args[]) {
        imageDisplay my = new imageDisplay();
    }

}