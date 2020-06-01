package com.example.healthy_v_7.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by paolotormon on 27 May 2020
 */
public class DetachableResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    public DetachableResultReceiver(Handler handler) {
        super(handler);
    }

    public void clearReceiver() {
        mReceiver = null;
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}