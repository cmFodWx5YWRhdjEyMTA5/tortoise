package com.android.nobug.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by seonjonghun on 2016. 11. 17..
 */

public class TextToSpeechManager {

    private static TextToSpeechManager uniqueInstance = new TextToSpeechManager();

    protected TextToSpeech textToSpeech;
    protected String serviceCode;
    protected Context mContext;

    //  =======================================================================================

    public static TextToSpeechManager getInstance() {
        return uniqueInstance;
    }

    public void setup(Context context) {
        mContext = context;

        try {
            textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    Locale locale = Locale.ENGLISH;
                    try {
                        textToSpeech.setLanguage(locale);
                        textToSpeech.setPitch(1f);
                        textToSpeech.setSpeechRate(1f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if( textToSpeech != null ) {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopTTS() {
        try {
            textToSpeech.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playText(String item) {
        if( item == null || "".equals(item) )
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(item);
        } else {
            ttsUnder20(item);
        }
    }

    public void setUtteranceProgressListener(UtteranceProgressListener utteranceProgressListener) {
        try {
            textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  ======================================================================================

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
            int speak = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        try {
            String utteranceId=this.hashCode() + "";
            int speak = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
