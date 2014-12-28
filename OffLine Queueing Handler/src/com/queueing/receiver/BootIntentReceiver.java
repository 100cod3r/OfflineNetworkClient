package com.queueing.receiver;

import com.queueing.service.QueueHandlerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, QueueHandlerService.class);
        context.startService(startServiceIntent);
    }
}