package com.leyili.utils;

public class GenerateID {

   public static Long generateEmployeeID(String username) {
        Long hashCode = (long) username.hashCode();
        return Math.abs(hashCode);
    }
}
