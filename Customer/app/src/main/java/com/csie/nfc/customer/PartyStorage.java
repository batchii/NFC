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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Utility class for persisting account numbers to disk.
 *
 * <p>The default SharedPreferences instance is used as the backing storage. Values are cached
 * in memory for performance.
 *
 * <p>This class is thread-safe.
 */
public class PartyStorage {
    private static final String PREF_PARTY_SIZE = "party_size";
    private static final String DEFAULT_PARTY_SIZE = "1";
    private static final String TAG = "PartySize";
    private static String sParty = null;
    private static final Object sPartyLock = new Object();

    public static void SetParty(Context c, String s) {
        synchronized(sPartyLock) {
            Log.i(TAG, "Setting account number: " + s);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_PARTY_SIZE, s).commit();
            sParty = s;
        }
    }

    public static String GetParty(Context c) {
        synchronized (sPartyLock) {
            if (sParty == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String account = prefs.getString(PREF_PARTY_SIZE, DEFAULT_PARTY_SIZE);

                sParty = account;
            }
            return sParty;
        }
    }


}
