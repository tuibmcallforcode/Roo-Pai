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
import java.util.List;

import thammasat.callforcode.R;
import thammasat.callforcode.databinding.ActivitySettingsBinding;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.model.DisasterMap;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;
    private int selectedRadiusValue;
    private Integer[] severity;
    private String url, languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        setTypeface();
        initInstance();
        eventListenerBinding();
    }

    private void eventListenerBinding() {
        binding.llSeverity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    severity = (Integer[]) InternalStorage.readObject(SettingsActivity.this, "severity");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new MaterialDialog.Builder(SettingsActivity.this)
                        .typeface("Bold.ttf", "Regular.ttf")
                        .backgroundColor(Color.parseColor("#454F63"))
                        .titleColor(Color.parseColor("#FFFFFF"))
                        .negativeColor(Color.parseColor("#FFFFFF"))
                        .positiveColor(Color.parseColor("#FFFFFF"))
                        .contentColor(Color.parseColor("#FFFFFF"))
                        .widgetColor(Color.parseColor("#FFFFFF"))
                        .title(R.string.severity)
                        .items(R.array.severity)
                        .itemsCallbackMultiChoice(severity, new MaterialDialog.ListCallbackMultiChoice() {
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                binding.tvSeveritySelection.setText(which.length + " Events");
                                try {
                                    InternalStorage.writeObject(SettingsActivity.this, "severity", which);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            }
                        })
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.confirm)
                        .show();
            }
        });

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

                final MaterialDialog nearby = new MaterialDialog.Builder(SettingsActivity.this)
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
                        if (charSequence.toString().equals("")) {
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

        binding.llLanguages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    languages = (String) InternalStorage.readObject(SettingsActivity.this, "languages");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new MaterialDialog.Builder(SettingsActivity.this)
                        .typeface("Bold.ttf", "Regular.ttf")
                        .backgroundColor(Color.parseColor("#454F63"))
                        .titleColor(Color.parseColor("#FFFFFF"))
                        .negativeColor(Color.parseColor("#FFFFFF"))
                        .positiveColor(Color.parseColor("#FFFFFF"))
                        .contentColor(Color.parseColor("#FFFFFF"))
                        .widgetColor(Color.parseColor("#FFFFFF"))
                        .title(R.string.languages)
                        .items(R.array.languages)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                binding.tvLanguagesSelection.setText(text.toString());
                                try {
                                    InternalStorage.writeObject(SettingsActivity.this, "languages", text.toString());
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                                return false;
                            }
                        })
                        .negativeText(R.string.cancel)
                        .show();
            }
        });

        binding.llUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    url = (String) InternalStorage.readObject(SettingsActivity.this, "url");
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } catch (ClassNotFoundException e) {
                    Log.e(TAG, e.getMessage());
                }

                final TextView etUrl;

                final MaterialDialog nearby = new MaterialDialog.Builder(SettingsActivity.this)
                        .typeface("Bold.ttf", "Regular.ttf")
                        .backgroundColor(Color.parseColor("#454F63"))
                        .titleColor(Color.parseColor("#FFFFFF"))
                        .negativeColor(Color.parseColor("#FFFFFF"))
                        .positiveColor(Color.parseColor("#FFFFFF"))
                        .contentColor(Color.parseColor("#FFFFFF"))
                        .widgetColor(Color.parseColor("#FFFFFF"))
                        .title(R.string.service_url)
                        .customView(R.layout.url_dialog, false)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.confirm)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                binding.tvUrlSelection.setText(url + "");
                                try {
                                    InternalStorage.writeObject(SettingsActivity.this, "url", url);
                                } catch (IOException e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        })
                        .build();

                etUrl = (EditText) nearby.findViewById(R.id.etUrl);
                etUrl.setText(url);
                etUrl.setTypeface(regular);
                etUrl.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        url = charSequence.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                nearby.show();
            }
        });
    }

    private void initInstance() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.tvGeneral.setTypeface(bold);
        binding.tvUnit.setTypeface(bold);
        binding.tvRadius.setTypeface(regular);
        binding.tvRadiusSelection.setTypeface(light);
        binding.tvCategories.setTypeface(bold);
        binding.tvSeverity.setTypeface(regular);
        binding.tvSeveritySelection.setTypeface(light);
        binding.tvLanguages.setTypeface(regular);
        binding.tvLanguagesSelection.setTypeface(light);
        binding.tvService.setTypeface(bold);
        binding.tvUrl.setTypeface(regular);
        binding.tvUrlSelection.setTypeface(light);

        try {
            severity = (Integer[]) InternalStorage.readObject(SettingsActivity.this, "severity");
            binding.tvSeveritySelection.setText(severity.length + " Events");
            selectedRadiusValue = (int) InternalStorage.readObject(SettingsActivity.this, "selectedRadiusValue");
            binding.tvRadiusSelection.setText(selectedRadiusValue + "");
            url = (String) InternalStorage.readObject(SettingsActivity.this, "url");
            binding.tvUrlSelection.setText(url);
            languages = (String) InternalStorage.readObject(SettingsActivity.this, "languages");
            binding.tvLanguagesSelection.setText(languages);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
