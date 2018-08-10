package thammasat.callforcode.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import thammasat.callforcode.R;
import thammasat.callforcode.databinding.ActivityFabBinding;
import thammasat.callforcode.fragment.MicrophoneFragment;

public class FabActivity extends BaseActivity {

    private ActivityFabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fab);

        if (checkRecordAudioPermission()) {
            MicrophoneFragment microphoneFragment = new MicrophoneFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, microphoneFragment);
            transaction.commit();
        } else {
            requestRecordAudioPermission();
        }

    }
}
