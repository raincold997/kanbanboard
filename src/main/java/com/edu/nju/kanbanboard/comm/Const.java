package com.edu.nju.kanbanboard.comm;

import org.springframework.stereotype.Component;

@Component
public class Const {

    public static String LOGIN_SESSION_KEY = "kanban_user";

    public static String PASSWORD_KEY = "@#$%^&*()OPG#$%^&*(HG";

    public static String DES3_KEY = "9964DYByKL967c3308imytCB";

    public static int COOKIE_TIMEOUT= 30*24*60*60;
}
