package com.qsoft.ondio.util;

import java.io.*;

public class StreamUtils
{

    /**
     * A helper method to convert an InputStream into a String
     *
     * @param inputStream
     * @return the String or a blank string if the IS was null
     * @throws java.io.IOException
     */
    public static String convertToString(InputStream inputStream) throws IOException
    {
        if (inputStream != null)
        {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try
            {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 1024);
                int n;
                while ((n = reader.read(buffer)) != -1)
                {
                    writer.write(buffer, 0, n);
                }
            }
            finally
            {
                inputStream.close();
            }
            return writer.toString();
        }
        else
        {
            return "";
        }
    }
}