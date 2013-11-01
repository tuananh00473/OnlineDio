package com.qsoft.ondio.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.util.Common;

/**
 * User: anhnt
 * Date: 10/16/13
 * Time: 8:56 AM
 */
public class ProfileFragment extends Fragment
{
    private static final String TAG = "ProfileFragment";

    private EditText etProfileName;
    private EditText etFullName;
    private EditText etPhoneNo;
    private EditText etBirthday;
    private Button btMale;
    private Button btFemale;
    private EditText etCountry;
    private Spinner spCountry;
    private EditText etDescription;
    private ImageView ivAvatar;
    private RelativeLayout rlCoverImage;
    private ScrollView scroll;
    private Button btSave;
    private Button btMenu;

    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private static int gender;
    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    private static final int AVATAR_CODE = 0;
    private static final int COVER_IMAGE_CODE = 1;
    private static int code;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.profile, null);
        setUpUI(view);
        setUpListenerController();
        return view;
    }

    private void setUpListenerController()
    {
        etBirthday.setOnClickListener(onClickListener);
        etCountry.setOnClickListener(onClickListener);
        btMale.setOnClickListener(onClickListener);
        btFemale.setOnClickListener(onClickListener);
        ivAvatar.setOnClickListener(onClickListener);
        rlCoverImage.setOnClickListener(onClickListener);
        etDescription.setOnClickListener(onClickListener);
        btSave.setOnClickListener(onClickListener);
        btMenu.setOnClickListener(onClickListener);
    }

    private void setUpUI(View view)
    {
        etProfileName = (EditText) view.findViewById(R.id.profile_etProfileName);
        etFullName = (EditText) view.findViewById(R.id.profile_etFullName);
        etPhoneNo = (EditText) view.findViewById(R.id.profile_etPhoneNo);
        etBirthday = (EditText) view.findViewById(R.id.profile_etBirthday);
        btMale = (Button) view.findViewById(R.id.profile_btMale);
        btFemale = (Button) view.findViewById(R.id.profile_btFemale);
        etCountry = (EditText) view.findViewById(R.id.profile_etCountry);
        spCountry = (Spinner) view.findViewById(R.id.profile_spCountry);
        etDescription = (EditText) view.findViewById(R.id.profile_etDescription);
        ivAvatar = (ImageView) view.findViewById(R.id.profile_ivAvatar);
        rlCoverImage = (RelativeLayout) view.findViewById(R.id.profile_rlCoverImage);
        scroll = (ScrollView) view.findViewById(R.id.profile_svScroll);
        btSave = (Button) view.findViewById(R.id.profile_btSave);
        btMenu = (Button) view.findViewById(R.id.profile_btMenu);

        loadProfileFromDB();
    }

    private void loadProfileFromDB()
    {
        Cursor c = getActivity().managedQuery(Common.CONTENT_URI_PROFILE, null, null, null, "displayName");
        if (c.moveToFirst())
        {
            etProfileName.setText(c.getString(c.getColumnIndex(Common.PROFILE_DISPLAY_NAME)));
            etFullName.setText(c.getString(c.getColumnIndex(Common.PROFILE_FULL_NAME)));
            etPhoneNo.setText(c.getString(c.getColumnIndex(Common.PROFILE_PHONE)));
            etBirthday.setText(c.getString(c.getColumnIndex(Common.PROFILE_BIRTHDAY)));
            if ("male".equals(c.getString(c.getColumnIndex(Common.PROFILE_GENDER))))
            {
                setGender(MALE);
            }
            else
            {
                setGender(FEMALE);
            }
            etCountry.setText(c.getString(c.getColumnIndex(Common.PROFILE_COUNTRY)));
            etDescription.setText(c.getString(c.getColumnIndex(Common.PROFILE_DESCRIPTION)));
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.profile_ivAvatar:
                    setAvatar();
                    break;
                case R.id.profile_rlCoverImage:
                    setCoverImage();
                    break;
                case R.id.profile_etBirthday:
                    setBirthday();
                    break;
                case R.id.profile_etCountry:
                    setCountry();
                    break;
                case R.id.profile_btMale:
                    setGender(MALE);
                    break;
                case R.id.profile_btFemale:
                    setGender(FEMALE);
                    break;
                case R.id.profile_etDescription:
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    break;
                case R.id.profile_btSave:
                    doSave();
                    break;
                case R.id.profile_btMenu:
                    showMenu();
                    break;
            }
        }
    };

    private void setBirthday()
    {
        MyDialog.showDatePickerDialog(getActivity(), etBirthday);
    }

    private void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    private void doSave()
    {
        try
        {
            Profile profile = new Profile();
            profile.setId(getUserId());
            profile.setDisplayName(etProfileName.getText().toString());
            profile.setFullName(etFullName.getText().toString());
            profile.setPhoneNo(etPhoneNo.getText().toString());
            profile.setBirthday(etBirthday.getText().toString());
            profile.setGender(gender);
            profile.setCountry(etCountry.getText().toString());
            profile.setDescription(etDescription.getText().toString());

            saveToDB(profile);

//            jsonResult result = Common.sServerAuthenticate.updateProfile(profile);
//            if ("success".equals(result))
//            {
//                saveToDB(profile);
            MyDialog.showMessageDialog(getActivity(), "Success", "Profile updated!");
//            }
//            else
//            {
//                MyDialog.showMessageDialog(getActivity(), "Failure", "Form invalid!");
//            }
            loadProfileFromDB();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void saveToDB(Profile profile)
    {
        ContentValues values = profile.getContentValues();

        Uri uri = getActivity().getContentResolver().insert(Common.CONTENT_URI_PROFILE, values);
        Toast.makeText(getActivity().getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }

    private Long getUserId()
    {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        long userId = sharedPreferences.getLong(Common.KEY, -1l);
//        if (-1l != userId)
//        {
//            return userId;
//        }
        return 573l;
    }

    private void setCoverImage()
    {
        code = COVER_IMAGE_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_coverimage));
    }

    private void setAvatar()
    {
        code = AVATAR_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_avatar));
    }

    private void setGender(int gender)
    {
        switch (gender)
        {
            case MALE:
                ProfileFragment.gender = MALE;
                btMale.setBackgroundResource(R.drawable.profile_male);
                btFemale.setBackgroundResource(R.drawable.profile_female_visible);
                break;
            case FEMALE:
                ProfileFragment.gender = FEMALE;
                btFemale.setBackgroundResource(R.drawable.profile_female);
                btMale.setBackgroundResource(R.drawable.profile_male_visible);
                break;
        }
    }

    private void setCountry()
    {
        spCountry.performClick();
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                showCountry(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }

    private void showCountry(String address)
    {
        etCountry.setText(address);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "onActivityResult");
        if (resultCode == getActivity().RESULT_OK && null != data)
        {
            if (requestCode == REQUEST_CODE_CAMERA_TAKE_PICTURE)
            {
                setImageFromCamera(data);
            }
            if (requestCode == REQUEST_CODE_RESULT_LOAD_IMAGE)
            {
                setImageFromAlbum(data);
            }
        }
    }

    private void setImageFromAlbum(Intent data)
    {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        Bitmap photo = BitmapFactory.decodeFile(picturePath);
        switch (code)
        {
            case AVATAR_CODE:
                makeMaskImage(ivAvatar, photo);
                break;
            case COVER_IMAGE_CODE:
                Drawable cover = new BitmapDrawable(photo);
                rlCoverImage.setBackgroundDrawable(cover);
                break;
        }
    }

    private void setImageFromCamera(Intent data)
    {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        switch (code)
        {
            case AVATAR_CODE:
                makeMaskImage(ivAvatar, photo);
                break;
            case COVER_IMAGE_CODE:
                Drawable cover = new BitmapDrawable(photo);
                rlCoverImage.setBackgroundDrawable(cover);
                break;
        }
        Log.d("CameraDemo", "Pic saved");
    }

    public void makeMaskImage(ImageView mImageView, Bitmap photoBitmap)
    {
        Bitmap mask = BitmapFactory.decodeResource(getResources(), R.drawable.profile_mask);

        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap photoBitmapScale = Bitmap.createScaledBitmap(photoBitmap, mask.getWidth(), mask.getHeight(), false);

        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawBitmap(photoBitmapScale, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);
        mImageView.setImageBitmap(result);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImageView.setBackgroundResource(R.drawable.profile_frame);
    }
}