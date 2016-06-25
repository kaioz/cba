package com.cocosw.commonbank.app;

import com.cocosw.commonbank.BuildConfig;
import com.cocosw.framework.debug.DebugActivityLifeCycle;
import com.cocosw.framework.debug.DebugFragmentLiftCycle;
import com.cocosw.framework.debug.DebugUtils;
import com.cocosw.framework.debug.NotificationLog;
import com.cocosw.framework.debug.ViewServerActiviyCycle;
import com.readystatesoftware.notificationlog.Log;

import timber.log.Timber;

/**
 * Project: CommonBank
 * Created by LiaoKai(soarcn) on 2015/5/8.
 */
class CommonBankConfig implements Runnable {

    private final CommonBankApplication app;

    public CommonBankConfig(CommonBankApplication commonBankApplication) {
        this.app = commonBankApplication;
    }

    @Override
    public void run() {
        if (DebugUtils.isViewServerNeeded(app))
            app.registerActivityLifecycle(new ViewServerActiviyCycle());
        app.registerActivityLifecycle(new DebugActivityLifeCycle());
        app.registerFragmentLifecycle(new DebugFragmentLiftCycle());
        DebugUtils.setupStrictMode();
        Timber.plant(new Timber.DebugTree());
        if (BuildConfig.notificationLog)
            Timber.plant(new NotificationLog());
        Log.initialize(app, app.getApplicationInfo().icon);
    }
}
