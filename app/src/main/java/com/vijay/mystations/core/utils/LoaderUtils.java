package com.vijay.mystations.core.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.vijay.mystations.R;

public class LoaderUtils {
    private static ProgressDialog proDialog = null;

    public static void startLoadingPleaseWait(Context context) {
        if (context != null) {
            if (proDialog == null)
                proDialog = ProgressDialog.show(context, null, context.getString(R.string.please_Wait));
            proDialog.setCancelable(true);
        }

    }


    public static void stopLoading() {
        if (proDialog != null)
            proDialog.dismiss();
        proDialog = null;
    }


}
