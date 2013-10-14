package com.qsoft.ondio.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * User: anhnt
 * Date: 10/14/13
 * Time: 2:55 PM
 */
public class MyDialog
{
    public static void showMessageDialog(Context context, String tittle, String message)
    {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
        alertDialog2.setTitle(tittle);
        alertDialog2.setMessage(message);
        alertDialog2.setPositiveButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //doOK
                    }
                });
        alertDialog2.show();
    }
}
