package com.teamcarl.prototype;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;
import java.sql.ResultSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

//import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Random;

/**
 * Created by Tank Residents on 12/10/2016.
 */

public class User implements Serializable {

        public String UserID;
        public String Permissions;
        public String Password;
        public String FirstName;
        public String LastName;
        public String BrandNumber;
        public String BrandLink;
        public String BrandName;
        public String Email;
        public boolean isAnonymous;

        public User(String UserID, Context context)
        {
            this.UserID = UserID;
            setUserInfo(UserID, context);
        }
        private void setUserInfo(String UserID, Context context)
        {
            DataAccess db = new DataAccess();
            InternalDataAccess ida = new InternalDataAccess();

            try {
                String query = "\n" +
                        "SELECT ISNULL(Permissions,''), ISNULL(Email,''), StayAnonymous, ISNULL(FirstName,''), ISNULL(LastName,''), ISNULL(b.BrandId,'')\n" +
                        ", ISNULL(b.BrandName,'')\n" +
                        ", ISNULL(b.BrandLink,'')\n" +
                        "FROM USERS u\n" +
                        "JOIN BRAND b ON u.brandNumber = b.brandId\n" +
                        "WHERE userid = '" + UserID + "'";
                ResultSet result = db.getDataTable(query);
                if(result.next()) {
                    Permissions = result.getString(1);
                    Email = result.getString(2);
                    isAnonymous = result.getBoolean(3);
                    FirstName = result.getString(4);
                    LastName = result.getString(5);
                    BrandNumber = result.getString(6);
                    BrandName = result.getString(7);
                    BrandLink = result.getString(8);
                }
            }
            catch(Exception e)
            {
                Log.e("error in setUserInfo", e.getMessage());
            }
            if(BrandNumber == null || BrandNumber.length() == 0)
                BrandNumber = ida.getSharedPreferencesKeyValue(context, "brand_number");
            if(BrandName == null || BrandName.length() == 0)
                BrandName = ida.getSharedPreferencesKeyValue(context, "brand_name");
            if(BrandLink == null || BrandLink.length() == 0)
                BrandLink = ida.getSharedPreferencesKeyValue(context, "brand_link");
        }
}