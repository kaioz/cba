package com.cocosw.commonbank.rest;

import com.cocosw.commonbank.rest.model.AccountInfo;
import com.cocosw.framework.network.Network;

/**
 * Class that communicate with the remote server
 *
 * Project: CommonBank
 * Created by LiaoKai(soarcn) on 2015/5/5.
 */
public class Server extends Network {

    private final static String HOST = "https://gist.githubusercontent.com/soarcn/";

    /**
     * Fetch account detail info from server
     * @return Account detail info
     */
    public static AccountInfo getAccountInfo() {
        return request(HOST+"75b0242b1fc272021962/raw/f87e0d85321e34a3dbf2493dae3a8748f05c66d1/json",AccountInfo.class);
    }
}
