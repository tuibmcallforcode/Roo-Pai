package thammasat.callforcode.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import thammasat.callforcode.R;
import thammasat.callforcode.fragment.InfoFragment;
import thammasat.callforcode.model.Disaster;

public class InfoActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout llTitle;
    private AppBarLayout appBar;
    private CircleImageView circleImageView;
    private TextView toolbarTitle, title, description;
    private Disaster disaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initInstance();
    }

    private void initInstance() {
        disaster = (Disaster) getIntent().getSerializableExtra("disaster");

        llTitle = (LinearLayout) findViewById(R.id.info_linearlayout_title);
        appBar = (AppBarLayout) findViewById(R.id.info_appbar);
        circleImageView = (CircleImageView) findViewById(R.id.info_circleImageView); 
        toolbarTitle = (TextView) findViewById(R.id.info_textview_toolbarTitle);
        title = (TextView) findViewById(R.id.info_textview_title);
        description = (TextView) findViewById(R.id.info_textview_description);

        setTypeface();
        setCircleImageView();
        setTextView();

        appBar.addOnOffsetChangedListener(this);
        startAlphaAnimation(toolbarTitle, 0, View.INVISIBLE);

        InfoFragment infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("disaster", disaster);
        infoFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, infoFragment, "infoFragment");
        transaction.commit();
    }

    private void setTextView() {
        title.setTypeface(bold);
        toolbarTitle.setTypeface(regular);
        description.setTypeface(regular);

        title.setText(disaster.getSeverity());
        toolbarTitle.setText(disaster.getSeverity());
        description.setText(disaster.getTitle());
    }

    private void setCircleImageView() {
        switch (disaster.getSeverity().toLowerCase()) {
            case "cold wave":
                circleImageView.setImageResource(R.drawable.white_cold_wave);
                break;
            case "drought":
                circleImageView.setImageResource(R.drawable.white_drought);
                break;
            case "earthquake":
                circleImageView.setImageResource(R.drawable.white_earth_quake);
                break;
            case "epidemic":
                circleImageView.setImageResource(R.drawable.white_epidemic);
                break;
            case "extratropical cyclone":
                circleImageView.setImageResource(R.drawable.white_extratropical_cyclone);
                break;
            case "fire":
                circleImageView.setImageResource(R.drawable.white_fire);
                break;
            case "wild fire":
                circleImageView.setImageResource(R.drawable.white_fire);
                break;
            case "flash flood":
                circleImageView.setImageResource(R.drawable.white_flash_flood);
                break;
            case "flood":
                circleImageView.setImageResource(R.drawable.white_flood);
                break;
            case "heat wave":
                circleImageView.setImageResource(R.drawable.white_heat_wave);
                break;
            case "insect infestation":
                circleImageView.setImageResource(R.drawable.white_insect_infestation);
                break;
            case "land slide":
                circleImageView.setImageResource(R.drawable.white_land_slide);
                break;
            case "mud slide":
                circleImageView.setImageResource(R.drawable.white_mud_slide);
                break;
            case "severe local storm":
                circleImageView.setImageResource(R.drawable.white_severe_local_storm);
                break;
            case "snow avalanche":
                circleImageView.setImageResource(R.drawable.white_snow_avalanche);
                break;
            case "storm surge":
                circleImageView.setImageResource(R.drawable.white_storm_surge);
                break;
            case "technological disaster":
                circleImageView.setImageResource(R.drawable.white_technological_disaster);
                break;
            case "tropical cyclone":
                circleImageView.setImageResource(R.drawable.white_tropical_cyclone);
                break;
            case "tsunami":
                circleImageView.setImageResource(R.drawable.white_tsunami);
                break;
            case "volcano":
                circleImageView.setImageResource(R.drawable.white_volcano);
                break;
            default:
                circleImageView.setImageResource(R.drawable.microphone);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(toolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(llTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(llTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
