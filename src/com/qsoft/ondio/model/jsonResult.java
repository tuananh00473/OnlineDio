package com.qsoft.ondio.model;

/**
 * User: anhnt
 * Date: 11/1/13
 * Time: 10:04 AM
 */
public class JsonResult
{
    int code;
    String status;
    Object data;
    String message;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
