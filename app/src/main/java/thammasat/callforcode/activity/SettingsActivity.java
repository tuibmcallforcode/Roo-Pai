package thammasat.callforcode.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;

import thammasat.callforcode.R;
import thammasat.callforcode.databinding.ActivitySettingsBinding;
import thammasat.callforcode.manager.InternalStorage;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;
    private int selectedUnitIndex = 0, selectedRadiusValue = 10000;
    private String selectedUnitValue = "km";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        setTypeface();
        initInstance();
        eventListenerBinding();
    }

    private void eventListenerBinding() {
        binding.llRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectedRadiusValue = (int) InternalStorage.readObject(SettingsActivity.this, "selectedRadiusValue");
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.e(TAG, e.getMessage());
                }

                final TextView etNearby;

                final MaterialDialog nearby = new MaterialDialog.Builder( SettingsActivity.this)
                        .typeface("Bold.ttf", "Regular.ttf")
                        .backgroundColor(Color.parseColor("#454F63"))
                        .titleColor(Color.parseColor("#FFFFFF"))
                        .negativeColor(Color.parseColor("#FFFFFF"))
                        .positiveColor(Color.parseColor("#FFFFFF"))
                        .contentColor(Color.parseColor("#FFFFFF"))
                        .widgetColor(Color.parseColor("#FFFFFF"))
                        .title(R.string.nearby_radius)
                        .customView(R.layout.nearby_dialog, false)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.confirm)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                binding.tvRadiusSelection.setText(selectedRadiusValue + "");
                                try {
                                    InternalStorage.writeObject(SettingsActivity.this, "selectedRadiusValue", selectedRadiusValue);
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        })
                        .build();

                etNearby = (EditText) nearby.findViewById(R.id.etNearby);
                etNearby.setText(selectedRadiusValue + "");
                etNearby.setTypeface(regular);
                etNearby.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(charSequence.toString().equals("")) {
                            selectedRadiusValue = 0;
                        } else {
                            selectedRadiusValue = Integer.parseInt(charSequence.toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                nearby.show();
            }
        });

        binding.llUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectedUnitIndex = (int) InternalStorage.readObject(SettingsActivity.this, "selectedUnitIndex");
                    selectedUnitValue = (String) InternalStorage.readObject(SettingsActivity.this, "selectedUnitValue");
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.e(TAG, e.getMessage());
                }
                new MaterialDialog.Builder( SettingsActivity.this)
                        .typeface("Bold.ttf", "Regular.ttf")
                        .backgroundColor(Color.parseColor("#454F63"))
                        .titleColor(Color.parseColor("#FFFFFF"))
                        .negativeColor(Color.parseColor("#FFFFFF"))
                        .positiveColor(Color.parseColor("#FFFFFF"))
                        .contentColor(Color.parseColor("#FFFFFF"))
                        .widgetColor(Color.parseColor("#FFFFFF"))
                        .title(R.string.unit_of_distance)
                        .items(R.array.unit)
                        .itemsCallbackSingleChoice(selectedUnitIndex, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                binding.tvUnitSelection.setText(text.toString());
                                try {
                                    InternalStorage.writeObject(SettingsActivity.this, "selectedUnitIndex", which);
                                    InternalStorage.writeObject(SettingsActivity.this, "selectedUnitValue", text);
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                                return false;
                            }
                        })
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.confirm)
                        .show();

            }
        });
    }

    private void initInstance() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.tvGeneral.setTypeface(bold);
        binding.tvUnit.setTypeface(regular);
        binding.tvRadius.setTypeface(regular);
        binding.tvUnitSelection.setTypeface(light);
        binding.tvRadiusSelection.setTypeface(light);

        try {
            selectedRadiusValue = (int) InternalStorage.readObject(SettingsActivity.this, "selectedRadiusValue");
            selectedUnitValue = (String) InternalStorage.readObject(SettingsActivity.this, "selectedUnitValue");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        binding.tvUnitSelection.setText(selectedUnitValue + "");
        binding.tvRadiusSelection.setText(selectedRadiusValue + "");
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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
