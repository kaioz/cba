package com.cocosw.commonbank;

import com.cocosw.commonbank.rest.Server;
import com.cocosw.commonbank.rest.model.AccountInfo;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Project: CommonBank
 * Created by LiaoKai(soarcn) on 2015/5/10.
 */
public class ServerTest {

    @Before
    public void setupContext() {
        Server.setGSON(new GsonBuilder().setDateFormat(
                "dd/MM/yyyy").create());
    }

    @Test
    public void assertGetAccountInfo() {
        AccountInfo accountInfo = Server.getAccountInfo();
        Assert.assertNotNull(accountInfo.account);
        Assert.assertEquals(accountInfo.account.accountName, "Complete Access");
        Assert.assertEquals(accountInfo.transactions.size(),13);
        Assert.assertEquals(accountInfo.pending.size(),2);
        Assert.assertEquals(accountInfo.atms.size(),2);
    }
}