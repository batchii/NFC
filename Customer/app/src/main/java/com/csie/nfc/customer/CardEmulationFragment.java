/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.csie.nfc.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Generic UI for sample discovery.
 */
public class CardEmulationFragment extends Fragment {

    public static final String TAG = "CardEmulationFragment";

    /** Called when sample is created. Displays generic UI with welcome text. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        EditText account = (EditText) v.findViewById(R.id.card_name_field);
        TextView phoneNumber = (TextView) v.findViewById(R.id.card_number_field);
        EditText partySize = (EditText) v.findViewById(R.id.party_number_field);
        account.setText(AccountStorage.GetAccount(getActivity()));
        account.addTextChangedListener(new AccountUpdater());
        TelephonyManager phoneManager = (TelephonyManager)
                getActivity().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumberString = phoneManager.getLine1Number();
        phoneNumber.setText(phoneNumberString);
        partySize.setText(PartyStorage.GetParty(getActivity()));
        partySize.addTextChangedListener(new PartyUpdater());
        Log.i(TAG, "hi");
        return v;
    }


    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
            AccountStorage.SetAccount(getActivity(), account);
        }
    }

    private class PartyUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String party = s.toString();
            PartyStorage.SetParty(getActivity(), party);
        }
    }

}
