package thammasat.callforcode.activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.Locale;

import thammasat.callforcode.databinding.ActivityLanguagesBinding;

import thammasat.callforcode.R;
import thammasat.callforcode.manager.InternalStorage;

public class LanguagesActivity extends BaseActivity {

    private ActivityLanguagesBinding binding;
    private String languages;
    private boolean settings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_languages);

        settings = getIntent().getBooleanExtra("settings", false);

        setTypeface();
        setAnimation();
        initInstance();
        eventListenerBinding();
    }

    private void initInstance() {
        binding.tvSelectLanguages.setTypeface(bold);
        binding.tvEnglish.setTypeface(regular);
        binding.tvSpanish.setTypeface(regular);
        binding.tvDutch.setTypeface(regular);
        binding.btnConfirm.setTypeface(bold);
        binding.tvDenmark.setTypeface(regular);
        binding.tvIndia.setTypeface(regular);
        binding.tvTurkey.setTypeface(regular);

        try {
            languages = (String) InternalStorage.readObject(LanguagesActivity.this, "languages");
            switch (languages) {
                case "en":
                    binding.llEnglish.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("en");
                    break;
                case "es":
                    binding.llSpanish.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("es");
                    break;
                case "nl":
                    binding.llDutch.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("nl");
                    break;
                case "tr":
                    binding.llTurkey.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("tr");
                    break;
                case "hi":
                    binding.llIndia.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("hi");
                    break;
                case "da":
                    binding.llDenmark.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("da");
                    break;
            }
            if (!settings)
                goToActivity(WelcomeActivity.class, R.anim.enter_from_right, R.anim.exit_to_left, true);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void eventListenerBinding() {
        binding.llEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(R.drawable.background_selection);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                setLanguages("en");
            }
        });

        binding.llSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(R.drawable.background_selection);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                setLanguages("es");
            }
        });

        binding.llDutch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(R.drawable.background_selection);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                setLanguages("nl");
            }
        });
        binding.llDenmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(R.drawable.background_selection);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                setLanguages("da");
            }
        });
        binding.llIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(R.drawable.background_selection);
                binding.llTurkey.setBackgroundResource(0);
                setLanguages("hi");
            }
        });
        binding.llTurkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(R.drawable.background_selection);
                setLanguages("tr");
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(WelcomeActivity.class, R.anim.enter_from_right, R.anim.exit_to_left, true);
            }
        });
    }

    private void setLanguages(String languages) {
        try {
            InternalStorage.writeObject(LanguagesActivity.this, "languages", languages);
            String languageToLoad = languages;
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
