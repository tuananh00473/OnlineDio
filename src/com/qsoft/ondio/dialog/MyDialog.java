package com.qsoft.ondio.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import com.qsoft.ondio.R;

/**
 * User: anhnt
 * Date: 10/14/13
 * Time: 2:55 PM
 */
public class MyDialog
{
    private static final int CAMERA_REQUEST = 999;
    private static final int RESULT_LOAD_IMAGE = 888;
    private static Activity activity;
    private static Dialog dialog;

    public static void showMessageDialog(Activity act, String tittle, String message)
    {
        activity = act;
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(activity);
        alertDialog2.setTitle(tittle);
        alertDialog2.setMessage(message);
        alertDialog2.setPositiveButton("OK", onClickListener);
        alertDialog2.show();
    }

    private static final DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            // do some thing.
        }
    };

    public static void showSetImageDialog(Activity act, String tittle)
    {
        activity = act;
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_setimage);
        dialog.setTitle(tittle);

        Button btTakePicture = (Button) dialog.findViewById(R.id.dialog_btTakePicture);
        Button btChoosePicture = (Button) dialog.findViewById(R.id.dialog_btChoosePicture);
        Button btCancel = (Button) dialog.findViewById(R.id.dialog_btCancel);

        btTakePicture.setOnClickListener(onViewClickListener);
        btChoosePicture.setOnClickListener(onViewClickListener);
        btCancel.setOnClickListener(onViewClickListener);

        dialog.show();
    }

    private final static View.OnClickListener onViewClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.dialog_btTakePicture:
                    takePicture(activity);
                    dialog.dismiss();
                    break;
                case R.id.dialog_btChoosePicture:
                    loadPicture(activity);
                    dialog.dismiss();
                    break;
                case R.id.dialog_btCancel:
                    dialog.dismiss();
                    break;
            }
        }
    };

    private static void loadPicture(Activity activity)
    {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private static void takePicture(Activity activity)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
}
