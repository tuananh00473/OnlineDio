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
import com.qsoft.ondio.lazzyload.ProfileAvatarLoader;
import com.qsoft.ondio.lazzyload.ProfileCoverImageLoader;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.util.Constants;
import org.json.JSONException;

/**
 * User: AnhNT
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

    private static final int MALE = 1;
    private static final int FEMALE = 2;
    private static int gender;
    private static int countryId;
    private static final int REQUEST_CODE_CAMERA_TAKE_PICTURE = 999;
    private static final int REQUEST_CODE_RESULT_LOAD_IMAGE = 888;
    private static final int AVATAR_CODE = 0;
    private static final int COVER_IMAGE_CODE = 1;
    private static int code;
    private static String userId;
    private static String authToken;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Cursor c = getActivity().managedQuery(Constants.CONTENT_URI_USER, null, null, null, "_ID");
        if (null != c && c.moveToFirst())
        {
            userId = c.getString(c.getColumnIndex(Constants.USER_USER_ID));
            authToken = c.getString(c.getColumnIndex(Constants.USER_ACCESS_TOKEN));
        }
    }

    @Override
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

        SyncFormServer();
        loadProfileFromDB();
    }

    private void SyncFormServer()
    {
        try
        {
            Profile profile = Constants.sServerAuthenticate.getProfile(userId, authToken);
            saveProfileToDB(profile);
            Log.d(TAG, profile.getDisplay_name());
        }
        catch (JSONException e)
        {
        }
    }

    private void loadProfileFromDB()
    {
        Cursor c = getActivity().managedQuery(Constants.CONTENT_URI_PROFILE, null, null, null, "displayName");
        if (null != c && c.moveToFirst())
        {
            etProfileName.setText(c.getString(c.getColumnIndex(Constants.PROFILE_DISPLAY_NAME)));
            etFullName.setText(c.getString(c.getColumnIndex(Constants.PROFILE_FULL_NAME)));
            etPhoneNo.setText(c.getString(c.getColumnIndex(Constants.PROFILE_PHONE)));
            etBirthday.setText(c.getString(c.getColumnIndex(Constants.PROFILE_BIRTHDAY)));
            setGender(c.getInt(c.getColumnIndex(Constants.PROFILE_GENDER)));
            etCountry.setText(getCountryName(c));
            etDescription.setText(c.getString(c.getColumnIndex(Constants.PROFILE_DESCRIPTION)));
            new ProfileAvatarLoader(getActivity(), this).DisplayImage(c.getString(c.getColumnIndex(Constants.PROFILE_AVATAR)), ivAvatar);
            new ProfileCoverImageLoader(getActivity(), this).DisplayImage(c.getString(c.getColumnIndex(Constants.PROFILE_COVER_IMAGE)), rlCoverImage);
        }
    }

    private String getCountryName(Cursor c)
    {
        String[] countries = getResources().getStringArray(R.array.countries);
        return countries[c.getInt(c.getColumnIndex(Constants.PROFILE_COUNTRY))];
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
            profile.setId(Integer.parseInt(userId));
            profile.setDisplay_name(etProfileName.getText().toString());
            profile.setFull_name(etFullName.getText().toString());
            profile.setPhone(etPhoneNo.getText().toString());
            profile.setBirthday(etBirthday.getText().toString());
            profile.setGender(gender);
            profile.setCountry_id(getCountryCode());
            profile.setDescription(etDescription.getText().toString());

            Profile profileUpdated = Constants.sServerAuthenticate.updateProfile(profile, authToken);
            if (null != profileUpdated)
            {
                saveProfileToDB(profileUpdated);
                loadProfileFromDB();
                MyDialog.showMessageDialog(getActivity(), "Success", "Profile updated!");
            }
            else
            {

                MyDialog.showMessageDialog(getActivity(), "Failure", "Update failure, try again later!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String getCountryCode()
    {
        String[] countries_code = getResources().getStringArray(R.array.countries_code);
        return countries_code[countryId];
    }

    private void saveProfileToDB(Profile profile)
    {
        Cursor c = getActivity().managedQuery(Constants.CONTENT_URI_PROFILE, null, null, null, "_ID");
        if (c.moveToFirst())
        {
            // get profile lan dau thi server tra ve full link cua image avatar va image cover
            // sau khi update profile thi server chi tra ve ten cua image
            // the nen phai lam them cai doan nay :( ==> rat cu chuoi!
            String linkAvatar = c.getString(c.getColumnIndex(Constants.PROFILE_AVATAR));
            if (null != linkAvatar && null != profile.getAvatar() && linkAvatar.contains(profile.getAvatar()))
            {
                profile.setAvatar(linkAvatar);
            }
            String linkCoverImage = c.getString(c.getColumnIndex(Constants.PROFILE_COVER_IMAGE));
            if (null != linkCoverImage && null != profile.getCover_image() && linkCoverImage.contains(profile.getCover_image()))
            {
                profile.setCover_image(linkCoverImage);
            }

            ContentValues values = profile.getContentValues();
            values.put(Constants.USER_USER_ID, userId);
            getActivity().getContentResolver().update(Constants.CONTENT_URI_PROFILE, values, null, null);
        }
        else
        {
            ContentValues values = profile.getContentValues();
            getActivity().getContentResolver().insert(Constants.CONTENT_URI_PROFILE, values);
        }
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
                this.gender = MALE;
                btMale.setBackgroundResource(R.drawable.profile_male);
                btFemale.setBackgroundResource(R.drawable.profile_female_visible);
                break;
            case FEMALE:
                this.gender = FEMALE;
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
                countryId = adapterView.getSelectedItemPosition();
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