package com.topcoder.poseidon.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.topcoder.poseidon.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NFCPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("WeakerAccess")
public class NFCPaymentFragment extends Fragment {

    public NFCPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardDetailsFragment.
     */
    static NFCPaymentFragment newInstance() {
        return new NFCPaymentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nfc_payment, container, false);
    }
}
