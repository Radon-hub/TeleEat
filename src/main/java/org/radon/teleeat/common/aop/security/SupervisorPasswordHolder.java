package org.radon.teleeat.common.aop.security;

public final class SupervisorPasswordHolder {

    private static String password;

    private SupervisorPasswordHolder() {}

    public static void setPassword(String pwd) {
        password = pwd;
    }

    public static String getPassword() {
        return password;
    }

    public static void clearPassword() {
        password = null;
    }

}
