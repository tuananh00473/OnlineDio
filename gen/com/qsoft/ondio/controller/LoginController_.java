//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package com.qsoft.ondio.controller;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.qsoft.ondio.restservice.AccountShared_;
import com.qsoft.ondio.restservice.Interceptor_;
import com.qsoft.ondio.restservice.MyRestService_;

public final class LoginController_
        extends LoginController
{

    private Context context_;

    private LoginController_(Context context)
    {
        context_ = context;
        init_();
    }

    public void afterSetContentView_()
    {
        if (!(context_ instanceof Activity))
        {
            return;
        }
        ((Interceptor_) interceptor).afterSetContentView_();
        ((AccountShared_) accountShared).afterSetContentView_();
    }

    /**
     * You should check that context is an activity before calling this method
     */
    public View findViewById(int id)
    {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_()
    {
        if (context_ instanceof Activity)
        {
            Activity activity = ((Activity) context_);
        }
        accountManager = ((AccountManager) context_.getSystemService(Context.ACCOUNT_SERVICE));
        services = new MyRestService_();
        interceptor = Interceptor_.getInstance_(context_);
        accountShared = AccountShared_.getInstance_(context_);
        init();
    }

    public static LoginController_ getInstance_(Context context)
    {
        return new LoginController_(context);
    }

    public void rebind(Context context)
    {
        context_ = context;
        init_();
    }

}
