package com.cocosw.commonbank.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.BaseAdapter;

import com.cocosw.accessory.utils.DateUtils;
import com.cocosw.accessory.utils.ListUtils;
import com.cocosw.accessory.views.textview.StyledText;
import com.cocosw.adapter.SectionAdapter;
import com.cocosw.adapter.ViewUpdater;
import com.cocosw.commonbank.R;
import com.cocosw.commonbank.rest.Server;
import com.cocosw.commonbank.rest.model.AccountInfo;
import com.cocosw.framework.core.ListFragment;
import com.cocosw.framework.log.Log;
import com.cocosw.framework.view.adapter.CocoAdapter;
import com.cocosw.framework.view.adapter.TypeListAdapter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Shows a transaction list
 * <p/>
 * Project: CommonBank
 * Created by LiaoKai(soarcn) on 2015/5/5.
 */
@SuppressWarnings("NullableProblems")
public class Transactions extends ListFragment<AccountInfo.Transaction> {

    private static final String RAWDATA = "_transitionRawData";
    private AccountInfo rawAccountInfo;
    private DateFormat format;

    @Override
    protected CocoAdapter<AccountInfo.Transaction> createAdapter(List<AccountInfo.Transaction> list) throws Exception {
        return new TransitionAdapter(context, list);
    }

    @Override
    protected void init(View view, Bundle bundle) throws Exception {
        if (rawAccountInfo != null) {
            setupAccountHeader(rawAccountInfo.account);
        }
        format = new SimpleDateFormat("dd MMM yyyy", new Locale("en", "AU"));
    }

    private class TransactionSectionizer implements SectionAdapter.Sectionizer<AccountInfo.Transaction, Date> {

        @Override
        public Date getSectionTitleForItem(AccountInfo.Transaction instance) {
            return instance.effectiveDate;
        }

        @Override
        public void update(int i, ViewUpdater viewUpdater, Date date) {
            viewUpdater.setText(0, format.format(date));
            viewUpdater.setText(1, DateUtils.getRelativeDate(date));
        }
    }

    @Override
    protected BaseAdapter wrapperAdapter(BaseAdapter adapter) {
        return new SectionAdapter<>(context, adapter, R.layout.list_section_transaction, new int[]{R.id.section_title, R.id.section_ago}, new TransactionSectionizer());
    }

    @Override
    protected void onItemClick(AccountInfo.Transaction transaction, int i, long l, View view) {
        if (transaction.atmId > 0) {
            AccountInfo.Atm atm = findAtm(transaction.atmId);
            if (atm != null) {
                if (validAtmLocation(atm)) {
                    AtmLocation.launch(this, atm);
                } else {
                    Log.e("Invalid Atm location " + atm.location);
                }
            } else
                Log.e("Invalid Atm id " + transaction.atmId);
        }
    }

    /**
     * Check if Atm object has valid location
     *
     * @param atm the Atm object for checking
     * @return Atm location is valid or not
     */
    private boolean validAtmLocation(AccountInfo.Atm atm) {
        return atm != null && atm.location != null;
    }

    /**
     * Get Atm object with atm ID
     *
     * @param atmId ID of atm
     * @return atm Object with atmId
     */
    private AccountInfo.Atm findAtm(int atmId) {
        for (AccountInfo.Atm atm : rawAccountInfo.atms) {
            if (atm.id == atmId)
                return atm;
        }
        return null;
    }

    @Override
    public void onLoaderDone(List<AccountInfo.Transaction> items) {
        setupAccountHeader(rawAccountInfo.account);
    }

    /**
     * Set up account info header
     *
     * @param account Account info to be shown in header
     */
    private void setupAccountHeader(AccountInfo.Account account) {
        if (account == null) {
            getHeaderAdapter().clearHeaders();
            return;
        }
        setHeader(R.layout.list_item_transactions_header);
        q.recycle(getHeaderView());
        q.id(R.id.account_name).text(account.accountName);
        q.id(R.id.account_number).text(account.accountNumber);
        q.id(R.id.account_balance).text(number2Cash(account.balance));
        q.id(R.id.available_funds).text(number2Cash(account.available));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        save(RAWDATA, rawAccountInfo);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        rawAccountInfo = load(RAWDATA);
        super.onAttach(activity);
    }

    /**
     * Format number to currency string
     *
     * @param amount amount of money
     * @return formatted string
     */
    private String number2Cash(float amount) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "AU")).format(amount);
    }

    @Override
    public List<AccountInfo.Transaction> pendingData(Bundle bundle) throws Exception {
        checkNetwork();
        rawAccountInfo = Server.getAccountInfo();
        //Convert accountInfo to Transaction list
        List<AccountInfo.Transaction> output = new ArrayList<>();
        if (!ListUtils.isEmpty(rawAccountInfo.transactions))
            output.addAll(rawAccountInfo.transactions);
        if (!ListUtils.isEmpty(rawAccountInfo.pending))
            for (AccountInfo.Transaction transaction : rawAccountInfo.pending) {
                transaction.pending = true;
                output.add(transaction);
            }
        Collections.sort(output, new Comparator<AccountInfo.Transaction>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public int compare(AccountInfo.Transaction lhs, AccountInfo.Transaction rhs) {
                return rhs.effectiveDate.compareTo(lhs.effectiveDate);
            }
        });

        return output;
    }

    class TransitionAdapter extends TypeListAdapter<AccountInfo.Transaction> {

        public TransitionAdapter(Context context, List<AccountInfo.Transaction> items) {
            super(context, R.layout.list_item_transactions_item, items);
        }

        @Override
        protected int[] getChildViewIds() {
            return new int[]{R.id.description, R.id.amount};
        }

        @Override
        protected void update(int i, AccountInfo.Transaction transaction) {
            if (transaction.pending)
                setText(0, new StyledText(context).bold(R.string.pending).append(" ").append(Html.fromHtml(transaction.description)));
            else
                setText(0, Html.fromHtml(transaction.description));
            setText(1, number2Cash(transaction.amount));
        }
    }
}
