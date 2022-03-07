package com.fxyublib.android.FxAppTangPoetry.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TextToSpeechUtils {
    private static final String TAG = "TextToSpeechUtils";
    private TextToSpeech mSpeech = null;

    public TextToSpeechUtils() {
    }

    public void init(Context c) {
        mSpeech = new TextToSpeech(c, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mSpeech.setLanguage(Locale.CHINESE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "TTS暂时不支持这种语音的朗读！");
                    }
                }
            }
        });
        //mSpeech.setSpeechRate(SharedData.voice_speed);
        //mSpeech.setPitch(SharedData.voice_pitch);
    }

    public void close(){
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
            mSpeech = null;
        }
    }

    public void stop(){
        if(mSpeech != null) {
            mSpeech.stop();
        }
    }

    public void speak(String msg) {
        if(mSpeech != null) {
            mSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public boolean isSpeaking(){
        if(mSpeech != null) {
            return mSpeech.isSpeaking();
        }
        else {
            return false;
        }
    }
}
