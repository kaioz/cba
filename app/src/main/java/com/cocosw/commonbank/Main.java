package com.cocosw.commonbank;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cocosw.commonbank.fragments.Transactions;
import com.cocosw.framework.core.SinglePaneActivity;


public class Main extends SinglePaneActivity {

    @Override
    protected void init(Bundle saveBundle) throws Exception {
        super.init(saveBundle);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected Fragment onCreatePane() {
        return Fragment.instantiate(this,Transactions.class.getName());
    }
}
