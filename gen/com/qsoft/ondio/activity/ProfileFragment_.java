//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.activity;

import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.qsoft.ondio.R.layout;
import com.qsoft.ondio.controller.DatabaseController_;
import com.qsoft.ondio.controller.ProfileController_;
import com.qsoft.ondio.restservice.AccountShared_;
import com.qsoft.ondio.restservice.Interceptor_;
import com.qsoft.ondio.restservice.MyRestService_;

public final class ProfileFragment_
        extends ProfileFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState)
    {
        accountManager = ((AccountManager) getActivity().getSystemService(Context.ACCOUNT_SERVICE));
        services = new MyRestService_();
        profileController = ProfileController_.getInstance_(getActivity());
        interceptor = Interceptor_.getInstance_(getActivity());
        databaseController = DatabaseController_.getInstance_(getActivity());
        accountShared = AccountShared_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_()
    {
        btSave = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btSave));
        spCountry = ((Spinner) findViewById(com.qsoft.ondio.R.id.profile_spCountry));
        btMenu = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btMenu));
        etFullName = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etFullName));
        etCountry = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etCountry));
        rlCoverImage = ((RelativeLayout) findViewById(com.qsoft.ondio.R.id.profile_rlCoverImage));
        etPhoneNo = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etPhoneNo));
        btFemale = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btFemale));
        etProfileName = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etProfileName));
        etBirthday = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etBirthday));
        btMale = ((Button) findViewById(com.qsoft.ondio.R.id.profile_btMale));
        scroll = ((ScrollView) findViewById(com.qsoft.ondio.R.id.profile_svScroll));
        etDescription = ((EditText) findViewById(com.qsoft.ondio.R.id.profile_etDescription));
        ivAvatar = ((ImageView) findViewById(com.qsoft.ondio.R.id.profile_ivAvatar));
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_btFemale);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setGenderFemale();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_btSave);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.doSave();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_rlCoverImage);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setCoverImage();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_btMenu);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.showMenu();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_etDescription);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setFullScroll();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_etCountry);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setCountry();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_btMale);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setGenderMale();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_ivAvatar);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setAvatar();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(com.qsoft.ondio.R.id.profile_etBirthday);
            if (view != null)
            {
                view.setOnClickListener(new OnClickListener()
                {


                    @Override
                    public void onClick(View view)
                    {
                        ProfileFragment_.this.setBirthday();
                    }

                }
                );
            }
        }
        ((ProfileController_) profileController).afterSetContentView_();
        ((Interceptor_) interceptor).afterSetContentView_();
        ((DatabaseController_) databaseController).afterSetContentView_();
        ((AccountShared_) accountShared).afterSetContentView_();
        afterView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null)
        {
            contentView_ = inflater.inflate(layout.profile, container, false);
        }
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id)
    {
        if (contentView_ == null)
        {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static ProfileFragment_.FragmentBuilder_ builder()
    {
        return new ProfileFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_
    {

        private Bundle args_;

        private FragmentBuilder_()
        {
            args_ = new Bundle();
        }

        public ProfileFragment build()
        {
            ProfileFragment_ fragment_ = new ProfileFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
