package com.topcoder.poseidon.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.topcoder.poseidon.MainActivity;
import com.topcoder.poseidon.R;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A placeholder fragment containing a simple view.
 */
@SuppressWarnings("WeakerAccess")
public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    /**
     * Permission request code
     */
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    /**
     * the scanner view
     */
    private ZXingScannerView scannerView;

    /**
     * The host activity
     */
    private Activity activity;

    /**
     * Did we request permission since launch?
     */
    private boolean didRequestPermission = false;

    static ScannerFragment newInstance() {
        return new ScannerFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        scannerView = new ZXingScannerView(activity);
        setupFormats();
        return scannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasPermissions()) {
            startCamera();
        } else if (!didRequestPermission) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            didRequestPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            }
        }
    }

    @Override
    public void onPause() {
        stopCamera();
        super.onPause();
    }

    /**
     * Check if the app has necessary permissions to use camera
     * @return true if the app has the permissions
     */
    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Set formats which the scanners view should support
     */
    private void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(formats);
    }

    /**
     * Start camera and scan QRCode
     */
    private void startCamera() {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
        scannerView.setAutoFocus(true);
    }

    /**
     * Stop camera
     */
    private void stopCamera() {
        scannerView.stopCamera();
    }

    /**
     * Resume the camera preview
     */
    private void resumeCameraPreview() {
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void handleResult(Result rawResult) {
        new AlertDialog.Builder(activity)
            .setCancelable(false)
            .setTitle(R.string.scan_result_title)
            .setMessage(rawResult.getText())
            .setNegativeButton(R.string.scan_result_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position) {
                    resumeCameraPreview();
                    dialogInterface.cancel();
                    ((MainActivity) activity).setCurrentTab(SectionsPagerAdapter.Companion.getHOME_TAB_INDEX());
                }
            })
            .setPositiveButton(R.string.scan_result_confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position) {
                    dialogInterface.dismiss();
                    new AlertDialog.Builder(activity)
                        .setCancelable(false)
                        .setMessage(R.string.successfully_paid_msg)
                        .setPositiveButton(R.string.successfully_paid_close, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                resumeCameraPreview();
                                dialogInterface.dismiss();
                                ((MainActivity) activity).setCurrentTab(SectionsPagerAdapter.Companion.getHOME_TAB_INDEX());
                            }
                        })
                        .show();
                }
            })
            .show();
    }
}