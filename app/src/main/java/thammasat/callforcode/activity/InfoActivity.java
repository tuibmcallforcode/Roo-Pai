package thammasat.callforcode.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import thammasat.callforcode.R;
import thammasat.callforcode.fragment.InfoFragment;
import thammasat.callforcode.model.Disaster;

public class InfoActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private AppBarLayout appbar;
    private ImageView coverImage;
    private LinearLayout linearlayoutTitle;
    private Disaster disaster;
    private long time;
    private int distance;
    private TextView tvTitle, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_info);

        initInstance();
    }

    private void initInstance() {
        disaster = (Disaster) getIntent().getSerializableExtra("disaster");
        time = getIntent().getLongExtra("time", 0);
        distance = getIntent().getIntExtra("distance", 0);

        appbar = (AppBarLayout)findViewById( R.id.appbar );
        coverImage = (ImageView)findViewById( R.id.imageview_placeholder );
        linearlayoutTitle = (LinearLayout)findViewById( R.id.linearlayout_title );
        tvTitle = (TextView) findViewById(R.id.info_textview_title);
        tvDate = (TextView) findViewById(R.id.info_textview_date);

        setTypeface();
        tvTitle.setTypeface(light);
        tvDate.setTypeface(bold);

        String[] text = disaster.getTitle().split(" - ");
        tvTitle.setText(text[0]);
        tvDate.setText(text[1]);

        appbar.addOnOffsetChangedListener(this);

        coverImage.setImageResource(R.drawable.background);

        InfoFragment infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("disaster", disaster);
        bundle.putLong("time", time);
        bundle.putInt("distance", distance);
        infoFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, infoFragment, "infoFragment");
        transaction.commit();
    }



    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
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
