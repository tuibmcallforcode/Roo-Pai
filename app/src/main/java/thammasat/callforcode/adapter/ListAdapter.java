package thammasat.callforcode.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import thammasat.callforcode.R;
import thammasat.callforcode.activity.LanguagesActivity;
import thammasat.callforcode.activity.MainActivity;
import thammasat.callforcode.activity.SettingsActivity;
import thammasat.callforcode.activity.WelcomeActivity;
import thammasat.callforcode.manager.GlideApp;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.model.Disaster;
import thammasat.callforcode.model.Prepareness;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<Disaster> disasterList = new ArrayList<>();
    private Typeface bold, regular, light;
    private Date now = new Date();
    private double latitude = 0, longitude = 0;
    private int selectedRadiusValue;
    private List<Integer> distance = new ArrayList<>();
    private boolean nearby;
    private String[] severityList;
    private Integer[] severityFilter;
    private String dayAgo, kmAway;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnItemClick onItemClick;

    public ListAdapter(Context context, List<Disaster> disasterList, boolean nearby) {
        dayAgo = context.getResources().getString(R.string.days_ago);
        kmAway = context.getResources().getString(R.string.km_away);
        severityList = context.getResources().getStringArray(R.array.severity);
        try {
            latitude = (double) InternalStorage.readObject(context, "latitude");
            longitude = (double) InternalStorage.readObject(context, "longitude");
            severityFilter = (Integer[]) InternalStorage.readObject(context, "severity");
            selectedRadiusValue = (int) InternalStorage.readObject(context, "selectedRadiusValue");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.context = context;
        this.nearby = nearby;
        if (nearby) {
            for (int i = 0; i < disasterList.size(); i++) {
                for (int n = 0; n < severityFilter.length; n++) {
                    if (disasterList.get(i).getSeverity().toLowerCase().equals(severityList[severityFilter[n]].toLowerCase())) {
                        final int distance = (int) distance(latitude, longitude, disasterList.get(i).getLoc().getCoordinates().get(0), disasterList.get(i).getLoc().getCoordinates().get(1));
                        if (distance <= selectedRadiusValue) {
                            this.disasterList.add(disasterList.get(i));
                            this.distance.add(distance);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < disasterList.size(); i++) {
                for (int n = 0; n < severityFilter.length; n++) {
                    if (disasterList.get(i).getSeverity().toLowerCase().equals(severityList[severityFilter[n]].toLowerCase())) {
                        this.disasterList.add(disasterList.get(i));
                    }
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            return new ViewHolder(view, 0);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_item_list, parent, false);
            return new ViewHolder(view, 1);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = df.parse(disasterList.get(position).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final long time = getDateDiff(date, now, TimeUnit.DAYS);
        holder.tvTitle.setText(disasterList.get(position).getBriefBody());
        holder.tvTag.setText(disasterList.get(position).getSeverity());
        holder.tvDuration.setText(time + " " + dayAgo);
        if (nearby)
            holder.tvDistance.setText(" | " + distance.get(position) + " " + kmAway);
        holder.tvDescription.setText(disasterList.get(position).getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nearby)
                    onItemClick.onItemClick(disasterList.get(position), time, distance.get(position));
                else
                    onItemClick.onItemClick(disasterList.get(position), time, 0);
            }
        });
        holder.llPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (disasterList.get(position).getSeverity().toLowerCase()) {
                    case "flood":
                        showPrepareDialog("flood");
                        break;
                    case "earthquake":
                        showPrepareDialog("earthquake");
                        break;
                    case "tropical cyclone":
                        showPrepareDialog("tornado");
                        break;
                }
            }
        });
        holder.llTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (disasterList.get(position).getSeverity().toLowerCase()) {
                    case "flood":
                        showTipsDialog("flood");
                        break;
                    case "earthquake":
                        showTipsDialog("earthquake");
                        break;
                    case "tropical cyclone":
                        showTipsDialog("tornado");
                        break;
                }
            }
        });
        switch (disasterList.get(position).getSeverity().toLowerCase()) {
            case "cold wave":
                holder.civProfile.setImageResource(R.drawable.white_cold_wave);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "drought":
                holder.civProfile.setImageResource(R.drawable.white_drought);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "earthquake":
                holder.civProfile.setImageResource(R.drawable.white_earth_quake);
                holder.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "epidemic":
                holder.civProfile.setImageResource(R.drawable.white_epidemic);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "extratropical cyclone":
                holder.civProfile.setImageResource(R.drawable.white_extratropical_cyclone);
                holder.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "fire":
                holder.civProfile.setImageResource(R.drawable.white_fire);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "wild fire":
                holder.civProfile.setImageResource(R.drawable.white_fire);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "flash flood":
                holder.civProfile.setImageResource(R.drawable.white_flash_flood);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "flood":
                holder.civProfile.setImageResource(R.drawable.white_flood);
                holder.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "heat wave":
                holder.civProfile.setImageResource(R.drawable.white_heat_wave);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "insect infestation":
                holder.civProfile.setImageResource(R.drawable.white_insect_infestation);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "land slide":
                holder.civProfile.setImageResource(R.drawable.white_land_slide);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "mud slide":
                holder.civProfile.setImageResource(R.drawable.white_mud_slide);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "severe local storm":
                holder.civProfile.setImageResource(R.drawable.white_severe_local_storm);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "snow avalanche":
                holder.civProfile.setImageResource(R.drawable.white_snow_avalanche);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "storm surge":
                holder.civProfile.setImageResource(R.drawable.white_storm_surge);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "technological disaster":
                holder.civProfile.setImageResource(R.drawable.white_technological_disaster);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "tropical cyclone":
                holder.civProfile.setImageResource(R.drawable.white_tropical_cyclone);
                holder.llPrepareness.setVisibility(View.VISIBLE);
                break;
            case "tsunami":
                holder.civProfile.setImageResource(R.drawable.white_tsunami);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            case "volcano":
                holder.civProfile.setImageResource(R.drawable.white_volcano);
                holder.llPrepareness.setVisibility(View.GONE);
                break;
            default:
                holder.civProfile.setImageResource(R.drawable.white_technological_disaster);
                holder.llPrepareness.setVisibility(View.GONE);
        }
    }

    private void showTipsDialog(String category) {
        String tips = "";
        try {
            tips = (String) InternalStorage.readObject(context, category + "Tips");
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        final TextView tvTitle, tvDescription;
        ImageView ivClose;

        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .typeface("Bold.ttf", "Regular.ttf")
                .backgroundColor(Color.parseColor("#00B0FF"))
                .widgetColor(Color.parseColor("#00B0FF"))
                .customView(R.layout.tips_dialog, true)
                .build();

        tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(bold);
        tvDescription = (TextView) dialog.findViewById(R.id.tvDescription);
        tvDescription.setTypeface(regular);
        tvDescription.setText(Html.fromHtml(tips));
        ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showPrepareDialog(String category) {
        String tips = "";
        try {
            tips = (String) InternalStorage.readObject(context, category + "Prepare");
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        final TextView tvTitle, tvDescription;
        ImageView ivClose;

        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .typeface("Bold.ttf", "Regular.ttf")
                .backgroundColor(Color.parseColor("#F9A825"))
                .widgetColor(Color.parseColor("#F9A825"))
                .customView(R.layout.prepare_dialog, true)
                .build();

        tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(bold);
        tvDescription = (TextView) dialog.findViewById(R.id.tvDescription);
        tvDescription.setTypeface(regular);
        tvDescription.setText(Html.fromHtml(tips));
        ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return disasterList != null ? disasterList.size() : 0;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in km
        return d;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CircleImageView civProfile;
        ImageView ivCover, ivClose;
        LinearLayout llPrepareness, llPrepare, llTips;
        TextView tvTitle, tvDuration, tvDistance, tvTag, tvDescription, tvPrepare, tvTips;

        public ViewHolder(View itemView, int type) {
            super(itemView);

            bold = Typeface.createFromAsset(context.getAssets(), "fonts/Bold.ttf");
            regular = Typeface.createFromAsset(context.getAssets(), "fonts/Regular.ttf");
            light = Typeface.createFromAsset(context.getAssets(), "fonts/Light.ttf");

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            civProfile = (CircleImageView) itemView.findViewById(R.id.civProfile);
            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvPrepare = (TextView) itemView.findViewById(R.id.tvPrepare);
            tvPrepare.setTypeface(regular);
            tvTips = (TextView) itemView.findViewById(R.id.tvTips);
            tvTips.setTypeface(regular);
            llPrepare = (LinearLayout) itemView.findViewById(R.id.llPrepare);
            llTips = (LinearLayout) itemView.findViewById(R.id.llTips);
            llPrepareness = (LinearLayout) itemView.findViewById(R.id.llPrepareness);
            ivClose = (ImageView) itemView.findViewById(R.id.ivClose);

            tvDistance.setTypeface(light);
            tvDuration.setTypeface(light);
            tvTitle.setTypeface(regular);
            tvTag.setTypeface(light);
            tvDescription.setTypeface(light);
        }
    }
}
