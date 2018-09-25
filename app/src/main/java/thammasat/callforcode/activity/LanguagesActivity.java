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
        binding.tvPortuguese.setTypeface(regular);
        binding.tvFinnish.setTypeface(regular);
        binding.tvRussian.setTypeface(regular);
        binding.tvFrench.setTypeface(regular);
        binding.tvItalian.setTypeface(regular);
        binding.tvChinese.setTypeface(regular);
        binding.tvChinese.setTypeface(regular);
        binding.tvArabic.setTypeface(regular);
        binding.tvNorwegian.setTypeface(regular);
        binding.tvJapanese.setTypeface(regular);
        binding.tvPolish.setTypeface(regular);
        binding.tvCzech.setTypeface(regular);

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
                case "pt":
                    binding.llPortuguese.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("pt");
                    break;
                case "fi":
                    binding.llFinnish.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("fi");
                    break;
                case "ru":
                    binding.llRussian.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("ru");
                    break;
                case "fr":
                    binding.llFrench.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("fr");
                    break;
                case "it":
                    binding.llItalian.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("it");
                    break;
                case "zh":
                    binding.llChinese.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("zh");
                    break;
                case "cs":
                    binding.llCzech.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("cs");
                    break;
                case "ar":
                    binding.llArabic.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("ar");
                    break;
                case "nb":
                    binding.llNorwegian.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("nb");
                    break;
                case "ja":
                    binding.llJapanese.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("ja");
                    break;
                case "pl":
                    binding.llPolish.setBackgroundResource(R.drawable.background_selection);
                    setLanguages("pl");
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
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
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
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
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
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
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
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
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
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
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
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("tr");
            }
        });

        binding.llPortuguese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(R.drawable.background_selection);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("pt");
            }
        });

        binding.llFinnish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(R.drawable.background_selection);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("fi");
            }
        });

        binding.llFinnish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(R.drawable.background_selection);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("fi");
            }
        });

        binding.llRussian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(R.drawable.background_selection);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("ru");
            }
        });

        binding.llFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(R.drawable.background_selection);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("fr");
            }
        });

        binding.llItalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(R.drawable.background_selection);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("it");
            }
        });

        binding.llChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(R.drawable.background_selection);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("zh");
            }
        });

        binding.llCzech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(R.drawable.background_selection);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("cs");
            }
        });

        binding.llArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(R.drawable.background_selection);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("ar");
            }
        });

        binding.llNorwegian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(R.drawable.background_selection);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("nb");
            }
        });

        binding.llJapanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(R.drawable.background_selection);
                binding.llPortuguese.setBackgroundResource(0);
                setLanguages("ja");
            }
        });

        binding.llPolish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llEnglish.setBackgroundResource(0);
                binding.llSpanish.setBackgroundResource(0);
                binding.llDutch.setBackgroundResource(0);
                binding.llDenmark.setBackgroundResource(0);
                binding.llIndia.setBackgroundResource(0);
                binding.llTurkey.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(0);
                binding.llFinnish.setBackgroundResource(0);
                binding.llRussian.setBackgroundResource(0);
                binding.llFrench.setBackgroundResource(0);
                binding.llItalian.setBackgroundResource(0);
                binding.llChinese.setBackgroundResource(0);
                binding.llCzech.setBackgroundResource(0);
                binding.llArabic.setBackgroundResource(0);
                binding.llNorwegian.setBackgroundResource(0);
                binding.llJapanese.setBackgroundResource(0);
                binding.llPortuguese.setBackgroundResource(R.drawable.background_selection);
                setLanguages("pl");
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
