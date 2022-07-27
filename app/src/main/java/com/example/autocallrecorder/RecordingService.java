package com.example.autocallrecorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.Date;

public class RecordingService extends Service {
    private MediaRecorder rec;
    private boolean recordStarted;
    private File file;
    String path = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Date date = new Date();
        CharSequence format = android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", date);
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.i("aunu", "saveLayout: "+format.toString());
        File file = new File(root+"/Download");
        rec = new MediaRecorder();
        rec.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        rec.setOutputFile(file.getAbsoluteFile() + "/Readymotive" + format + "rec.3gp");
        rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);

                if (TelephonyManager.CALL_STATE_IDLE == state && rec == null) {
                    rec.stop();
                    rec.reset();
                    rec.release();
                    recordStarted = false;
                    stopSelf();
                } else if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                    try {
                        rec.prepare();
                        rec.start();
                    } catch (Exception e) {
                        Log.i("aunu", "onCallStateChanged: " + e);
                    }
                    recordStarted = true;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

        return START_STICKY;
    }
}
