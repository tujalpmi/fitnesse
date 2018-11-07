/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.triggle.utils;

import java.text.SimpleDateFormat;

/**
 *
 * @author jose.alvarez
 */
public class ManageMessages {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final int iLevel = 1;
            
    public ManageMessages() {
    }

    public static void printMessage(int iLevel, String strMessage, String className) {
        if (ManageMessages.iLevel >= iLevel) {
            String strDate = sdf.format(new java.util.Date());
            System.out.println(className + ": " + strDate + " " + strMessage);
        }
    }

}
