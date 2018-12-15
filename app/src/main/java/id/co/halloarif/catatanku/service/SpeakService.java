package id.co.halloarif.catatanku.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

public class SpeakService extends Service implements TextToSpeech.OnInitListener {

    public static TextToSpeech tts;
    private String string;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        tts = new TextToSpeech(this, this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        string = intent.getStringExtra("string");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d("SpeakService", "Language is not available.");
            } else {
                if (!TextUtils.isEmpty(string)) {
                    speak(string);
                } else {

                    speak("Error");
                }
            }
        } else {
            Log.d("SpeakService", "Could not initialize TextToSpeech.");
        }
    }

    private void speak(String string) {
        tts.speak(string, TextToSpeech.QUEUE_FLUSH, null);
    }
}
