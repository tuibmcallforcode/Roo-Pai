package thammasat.callforcode.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import thammasat.callforcode.R;
import thammasat.callforcode.databinding.ActivitySettingsBinding;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        setTypeface();
        initInstance();
    }

    private void initInstance() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.tvGeneral.setTypeface(bold);
        binding.tvUnit.setTypeface(regular);
        binding.tvRadius.setTypeface(regular);
        binding.tvUnitSelection.setTypeface(light);
        binding.tvRadiusSelection.setTypeface(light);

        binding.tvUnitSelection.setText("km");
        binding.tvRadiusSelection.setText("1000");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
