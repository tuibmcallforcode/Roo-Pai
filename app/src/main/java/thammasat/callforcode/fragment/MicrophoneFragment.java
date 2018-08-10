package thammasat.callforcode.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import thammasat.callforcode.databinding.FragmentMicrophoneBinding;

import thammasat.callforcode.R;
import thammasat.callforcode.manager.WebSocketClient;

import static com.facebook.FacebookSdk.getCacheDir;

public class MicrophoneFragment extends BaseFragment {

    private static final String LOG_TAG = "AudioRecordTest";
    public static String sRecordedFileName;
    private FragmentMicrophoneBinding binding;

    private MediaRecorder mRecorder;
    private WebSocketClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_microphone, container, false);
        View view = binding.getRoot();
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sRecordedFileName = getCacheDir().getAbsolutePath() + "/audiorecordtest.3gp";

        binding.btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnMic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(LOG_TAG, "onTouch: " + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecording();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecording();
                    send();
                }
                return true;
            }
        });

        client = new WebSocketClient(getContext());
        client.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    private void startRecording() {
        String[] perms = {Manifest.permission.RECORD_AUDIO};

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(sRecordedFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void send() {
        client.sendAudio();
    }
}
