package com.example.homepage;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class Voice_Recognition extends Service {
    private SpeechRecognizer speechRecognizer;
    private Intent recognizerIntent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate();
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        // Set up the intent for speech recognition
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // Set up the recognition listener
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("SpeechRecognition", "Ready for speech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("SpeechRecognition", "Beginning of speech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // You can log or monitor changes in voice volume here
            }

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {
                Log.d("SpeechRecognition", "End of speech");
            }

            @Override
            public void onError(int error) {
                Log.e("SpeechRecognition", "Error occurred: " + error);
                restartListening(); // Restart if an error occurs
            }

            @Override
            public void onResults(Bundle results) {
                // Get the recognized text
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String recognizedText = matches.get(0);
                    Log.d("SpeechRecognition", "Recognized Text: " + recognizedText);

                    // Here, you can handle the recognized text (e.g., send it to another part of the app)
                }
                restartListening(); // Restart to keep listening
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        // Start listening for speech
        startListening();
    }

    private void startListening() {
        speechRecognizer.startListening(recognizerIntent);
    }

    private void restartListening() {
        speechRecognizer.stopListening();
        startListening();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}



