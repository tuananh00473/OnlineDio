package com.qsoft.ondio.util;

import java.security.MessageDigest;

/**
 * User: AnhNT
 * Date: 10/30/13
 * Time: 8:53 AM
 */
public class StringConverter
{
    public String doConvert(String password) throws Exception
    {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
