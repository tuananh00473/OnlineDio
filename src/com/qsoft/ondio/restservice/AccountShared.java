package com.qsoft.ondio.restservice;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.ondio.util.Constants;

/**
 * User: anhnt
 * Date: 11/15/13
 * Time: 9:38 AM
 */
@EBean(scope = Scope.Singleton)
public class AccountShared
{
    @SystemService
    AccountManager accountManager;

    private Account account;

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public Account getAccount()
    {
        return account;
    }

    public String getUser_id()
    {
        if (account != null)
        {
            return accountManager.getUserData(account, Constants.USERDATA_USER_OBJ_ID);
        }
        else
        {
            return null;
        }
    }

    public String getPassword()
    {
        return accountManager.getPassword(account);
    }

    public void refreshTokenToSystem(String authToken)
    {
        accountManager.setAuthToken(account, Constants.AUTHTOKEN_TYPE_FULL_ACCESS, authToken);
    }
}
