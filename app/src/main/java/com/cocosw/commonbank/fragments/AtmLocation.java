package com.cocosw.commonbank.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cocosw.accessory.views.ViewUtils;
import com.cocosw.commonbank.R;
import com.cocosw.commonbank.rest.model.AccountInfo;
import com.cocosw.framework.core.Presenter;
import com.cocosw.framework.core.SinglePaneActivity;
import com.cocosw.framework.log.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Shows a map of the Atm
 *
 * Project: CommonBank
 * Created by Kai Liao (soarcn) on 2015/5/5.
 */
public class AtmLocation extends SupportMapFragment {

    private static final String ATM = "_atm";
    private GoogleMap mMap;
    // Initial camera zoom
    private static final float CAMERA_ZOOM = 17f;
    private AccountInfo.Atm atm;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setTitle(R.string.find_us);
            getActionBar().setIcon(R.drawable.icon_welcome_logo);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapView = super.onCreateView(inflater, container, savedInstanceState);
        mMap = getMap();
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setIndoorEnabled(false);
            mMap.setBuildingsEnabled(false);
            atm = (AccountInfo.Atm) getArguments().getSerializable(ATM);
            Log.d(atm);
            setUpMap(mapView);
        }

        return mapView;
    }


    private void setUpMap(final View mapView) {
        final LatLng latLng = new LatLng(atm.location.lat, atm.location.lng);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 1f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_atm_commbank)));

        if (mapView != null && mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
                    ViewUtils.removeGlobalOnLayoutListener(mapView, this);
                    getActivity().getWindow().setBackgroundDrawable(null);
                }
            });
        }
    }

    static void launch(Fragment fragment, AccountInfo.Atm atm) {
        Intent extra = new Intent().putExtra(ATM, atm);
        new Presenter(fragment).container(SinglePaneActivity.class)
                .target(AtmLocation.class).extra(extra).open();
    }

    private ActionBar getActionBar() {
        try {
            return ((ActionBarActivity) getActivity()).getSupportActionBar();
        } catch (ClassCastException e) {
            return null;
        }
    }
}
