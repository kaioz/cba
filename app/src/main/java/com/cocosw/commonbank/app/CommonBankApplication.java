package com.cocosw.commonbank.app;

import com.cocosw.commonbank.rest.Server;
import com.cocosw.framework.app.CocoApp;
import com.google.gson.GsonBuilder;

import timber.log.Timber;

/**
 * Project: CommonBank
 * Created by LiaoKai(soarcn) on 2015/5/6.
 */
public class CommonBankApplication extends CocoApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Server.setGSON(new GsonBuilder().setDateFormat(
                "dd/MM/yyyy").create());
    }

    @Override
    protected Runnable config() {
        return new CommonBankConfig(this);
    }
}
