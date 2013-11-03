package com.qsoft.ondio.accountmanager;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.qsoft.ondio.activity.LoginActivity;
import com.qsoft.ondio.model.User;
import com.qsoft.ondio.util.Common;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 19/03/13
 * Time: 18:58
 */
public class OnlineAuthenticator extends AbstractAccountAuthenticator
{

    private String TAG = "OnlineAuthenticator";
    private final Context mContext;

    public OnlineAuthenticator(Context context)
    {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException
    {
        Log.d(TAG, " ==> addAccount");

        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(Common.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(Common.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(Common.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {
        Log.d(TAG, " ==> getAuthToken");

        if (!authTokenType.equals(Common.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(Common.AUTHTOKEN_TYPE_FULL_ACCESS))
        {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);
        String userId = null;

        Log.d(TAG, " ==> peekAuthToken returned - " + authToken);

        if (TextUtils.isEmpty(authToken))
        {
            final String password = am.getPassword(account);
            if (password != null)
            {
                try
                {
                    Log.d("udinic", TAG + "> re-authenticating with the existing password");
                    User user = Common.sServerAuthenticate.login(account.name, password, authTokenType);
                    if (user != null)
                    {
                        authToken = user.getAccess_token();
                        userId = user.getUser_id();
                        saveUserIdToSharedPreferences(Common.KEY, Long.parseLong(userId));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken))
        {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(Common.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(Common.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    private void saveUserIdToSharedPreferences(String key, Long value)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    @Override
    public String getAuthTokenLabel(String authTokenType)
    {
        if (Common.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
        {
            return Common.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        }
        else if (Common.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
        {
            return Common.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        }
        else
        {
            return authTokenType + " (Label)";
        }
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException
    {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType)
    {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException
    {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException
    {
        return null;
    }
}