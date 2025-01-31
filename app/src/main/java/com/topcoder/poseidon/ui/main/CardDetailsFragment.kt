package com.topcoder.poseidon.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.topcoder.poseidon.R
import com.topcoder.poseidon.storage.CardStorage
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [CardDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtCardNumber.setText(CardStorage.getLastSavedCardNumber())

        view.findViewById<View>(R.id.btnSave).setOnClickListener {
            CardStorage.saveCardNumber(edtCardNumber.text.toString())
            tvCardStatus.visibility = View.VISIBLE

        }
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CardDetailsFragment.
         */
        internal fun newInstance(): CardDetailsFragment {
            return CardDetailsFragment()
        }
    }
}// Required empty public constructor
