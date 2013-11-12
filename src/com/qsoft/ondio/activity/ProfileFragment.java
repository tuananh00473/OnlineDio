package com.qsoft.ondio.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.ondio.R;
import com.qsoft.ondio.controller.DatabaseController;
import com.qsoft.ondio.dialog.MyDialog;
import com.qsoft.ondio.lazzyload.ProfileAvatarLoader;
import com.qsoft.ondio.lazzyload.ProfileCoverImageLoader;
import com.qsoft.ondio.model.Profile;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Constants;
import org.json.JSONException;

/**
 * User: AnhNT
 * Date: 10/16/13
 * Time: 8:56 AM
 */

@EFragment(R.layout.profile)
public class ProfileFragment extends Fragment
{
    private static final String TAG = "ProfileFragment";

    @ViewById(R.id.profile_etProfileName)
    EditText etProfileName;

    @ViewById(R.id.profile_etFullName)
    EditText etFullName;

    @ViewById(R.id.profile_etPhoneNo)
    EditText etPhoneNo;

    @ViewById(R.id.profile_etBirthday)
    EditText etBirthday;

    @ViewById(R.id.profile_btMale)
    Button btMale;

    @ViewById(R.id.profile_btFemale)
    Button btFemale;

    @ViewById(R.id.profile_etCountry)
    EditText etCountry;

    @ViewById(R.id.profile_spCountry)
    Spinner spCountry;

    @ViewById(R.id.profile_etDescription)
    EditText etDescription;

    @ViewById(R.id.profile_ivAvatar)
    ImageView ivAvatar;

    @ViewById(R.id.profile_rlCoverImage)
    RelativeLayout rlCoverImage;

    @ViewById(R.id.profile_svScroll)
    ScrollView scroll;

    @ViewById(R.id.profile_btSave)
    Button btSave;

    @ViewById(R.id.profile_btMenu)
    Button btMenu;

    @Bean
    DatabaseController databaseController;

    User user = null;

    private static final int MALE = 1;
    private static final int FEMALE = 2;
    private static int gender;

    private static final int AVATAR_CODE = 0;
    private static final int COVER_IMAGE_CODE = 1;
    private static int code;

    @AfterViews
    public void afterView()
    {
        user = databaseController.loadUserFromDB();

        setUpProfile(user);
        SyncFormServer(user);
    }

    private void SyncFormServer(User user)
    {
        try
        {
            Profile profile = Constants.sServerAuthenticate.getProfile(user.getUser_id(), user.getAccess_token());
            databaseController.saveProfileToDB(profile);
            setUpProfile(user);
            Log.d(TAG, profile.getDisplay_name());
        }
        catch (JSONException ignored)
        {
        }
    }

    private void setUpProfile(User user)
    {
        Profile profile = databaseController.loadProfileFromDB(user);
        if (null != profile)
        {
            etProfileName.setText(profile.getDisplay_name());
            etFullName.setText(profile.getFull_name());
            etPhoneNo.setText(profile.getPhone());
            etBirthday.setText(profile.getBirthday());
            setGender(profile.getGender());
            etCountry.setText(getCountryName(profile.getCountry_id()));
            etDescription.setText(profile.getDescription());
            new ProfileAvatarLoader(getActivity(), this).DisplayImage(profile.getAvatar(), ivAvatar);
            new ProfileCoverImageLoader(getActivity(), this).DisplayImage(profile.getCover_image(), rlCoverImage);
        }
    }

    private void setGender(int gender)
    {
        switch (gender)
        {
            case MALE:
                btMale.performClick();
                break;
            case FEMALE:
                btFemale.performClick();
                break;
        }
    }

    private String getCountryName(String countryCode)
    {
        String[] countries = getResources().getStringArray(R.array.countries);
        String[] countryCodes = getResources().getStringArray(R.array.countries_code);
        for (int i = 0; i < countryCodes.length; i++)
        {
            if (countryCode.equals(countryCodes[i]))
            {
                return countries[i];
            }
        }
        return null;
    }

    @Click(R.id.profile_etDescription)
    void setFullScroll()
    {
        scroll.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Click(R.id.profile_etBirthday)
    void setBirthday()
    {
        MyDialog.showDatePickerDialog(getActivity(), etBirthday);
    }

    @Click(R.id.profile_btMenu)
    void showMenu()
    {
        ((SlidebarActivity) getActivity()).setOpenListOption();
    }

    @Click(R.id.profile_btSave)
    void doSave()
    {
        try
        {
            Profile profile = new Profile();
            profile.setId(Integer.parseInt(user.getUser_id()));
            profile.setDisplay_name(etProfileName.getText().toString());
            profile.setFull_name(etFullName.getText().toString());
            profile.setPhone(etPhoneNo.getText().toString());
            profile.setBirthday(etBirthday.getText().toString());
            profile.setGender(gender);
            profile.setCountry_id(getCountryCode(etCountry.getText().toString()));
            profile.setDescription(etDescription.getText().toString());

            Profile profileUpdated = Constants.sServerAuthenticate.updateProfile(profile, user.getAccess_token());
            if (null != profileUpdated)
            {
                databaseController.saveProfileToDB(profileUpdated);
                setUpProfile(user);
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

    private String getCountryCode(String countryName)
    {
        String[] countryNames = getResources().getStringArray(R.array.countries);
        String[] countryCodes = getResources().getStringArray(R.array.countries_code);
        for (int i = 0; i < countryCodes.length; i++)
        {
            if (countryName.equals(countryNames[i]))
            {
                return countryCodes[i];
            }
        }
        return null;
    }

    @Click(R.id.profile_rlCoverImage)
    void setCoverImage()
    {
        code = COVER_IMAGE_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_coverimage));
    }

    @Click(R.id.profile_ivAvatar)
    void setAvatar()
    {
        code = AVATAR_CODE;
        MyDialog.showSetImageDialog(getActivity(), getString(R.string.dialog_tittle_avatar));
    }

    @Click(R.id.profile_btMale)
    void setGenderMale()
    {
        ProfileFragment.gender = MALE;
        btMale.setBackgroundResource(R.drawable.profile_male);
        btFemale.setBackgroundResource(R.drawable.profile_female_visible);
    }

    @Click(R.id.profile_btFemale)
    void setGenderFemale()
    {
        ProfileFragment.gender = FEMALE;
        btFemale.setBackgroundResource(R.drawable.profile_female);
        btMale.setBackgroundResource(R.drawable.profile_male_visible);
    }

    @Click(R.id.profile_etCountry)
    void setCountry()
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
        if (resultCode == Activity.RESULT_OK && null != data)
        {
            if (Constants.REQUEST_CODE_CAMERA_TAKE_PICTURE == requestCode)
            {
                setImageFromCamera(data);
            }
            if (Constants.REQUEST_CODE_RESULT_LOAD_IMAGE == requestCode)
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