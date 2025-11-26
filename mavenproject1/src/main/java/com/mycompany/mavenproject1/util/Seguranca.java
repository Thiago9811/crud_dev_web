/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1.util;

import java.security.MessageDigest;
import java.math.BigInteger;
/**
 *
 * @author Thiago
 */
public class Seguranca {
    public static String hashMD5(String senha) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(), 0, senha.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (Exception e) {
            return senha;
        }
    }
}
