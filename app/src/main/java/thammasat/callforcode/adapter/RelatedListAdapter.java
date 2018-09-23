package thammasat.callforcode.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import thammasat.callforcode.R;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.model.Disaster;

public class RelatedListAdapter extends RecyclerView.Adapter<RelatedListAdapter.ViewHolder> {

    private Context context;
    private List<Disaster> disasterList;
    private Typeface bold, regular, light;
    private Date now = new Date();
    private String dayAgo;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnItemClick onItemClick;

    public RelatedListAdapter(Context context, List<Disaster> disasterList) {
        dayAgo = context.getResources().getString(R.string.days_ago);
        this.context = context;
        this.disasterList = disasterList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_list, parent, false);
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
        holder.tvDescription.setText(disasterList.get(position).getDescription());
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(disasterList.get(position), time, 0);
            }
        });
        switch (disasterList.get(position).getSeverity().toLowerCase()) {
            case "cold wave":
                holder.civProfile.setImageResource(R.drawable.white_cold_wave);
                break;
            case "drought":
                holder.civProfile.setImageResource(R.drawable.white_drought);
                break;
            case "earthquake":
                holder.civProfile.setImageResource(R.drawable.white_earth_quake);
                break;
            case "epidemic":
                holder.civProfile.setImageResource(R.drawable.white_epidemic);
                break;
            case "extratropical cyclone":
                holder.civProfile.setImageResource(R.drawable.white_extratropical_cyclone);
                break;
            case "fire":
                holder.civProfile.setImageResource(R.drawable.white_fire);
                break;
            case "wild fire":
                holder.civProfile.setImageResource(R.drawable.white_fire);
                break;
            case "flash flood":
                holder.civProfile.setImageResource(R.drawable.white_flash_flood);
                break;
            case "flood":
                holder.civProfile.setImageResource(R.drawable.white_flood);
                break;
            case "heat wave":
                holder.civProfile.setImageResource(R.drawable.white_heat_wave);
                break;
            case "insect infestation":
                holder.civProfile.setImageResource(R.drawable.white_insect_infestation);
                break;
            case "land slide":
                holder.civProfile.setImageResource(R.drawable.white_land_slide);
                break;
            case "mud slide":
                holder.civProfile.setImageResource(R.drawable.white_mud_slide);
                break;
            case "severe local storm":
                holder.civProfile.setImageResource(R.drawable.white_severe_local_storm);
                break;
            case "snow avalanche":
                holder.civProfile.setImageResource(R.drawable.white_snow_avalanche);
                break;
            case "storm surge":
                holder.civProfile.setImageResource(R.drawable.white_storm_surge);
                break;
            case "technological disaster":
                holder.civProfile.setImageResource(R.drawable.white_technological_disaster);
                break;
            case "tropical cyclone":
                holder.civProfile.setImageResource(R.drawable.white_tropical_cyclone);
                break;
            case "tsunami":
                holder.civProfile.setImageResource(R.drawable.white_tsunami);
                break;
            case "volcano":
                holder.civProfile.setImageResource(R.drawable.white_volcano);
                break;
            default:
                holder.civProfile.setImageResource(R.drawable.microphone);
        }

//        if (holder.getItemViewType() == 1) {
//            GlideApp.with(context)
//                    .load(disasterList.get(position).getUrl_thumb())
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .fitCenter()
//                    .transition(DrawableTransitionOptions.withCrossFade()) //Optional
//                    .into(holder.ivCover);
//        }
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

        FrameLayout frameLayout;
        CircleImageView civProfile;
        ImageView ivCover;
        TextView tvTitle, tvDuration, tvDistance, tvTag, tvDescription;

        public ViewHolder(View itemView, int type) {
            super(itemView);

            bold = Typeface.createFromAsset(context.getAssets(), "fonts/Bold.ttf");
            regular = Typeface.createFromAsset(context.getAssets(), "fonts/Regular.ttf");
            light = Typeface.createFromAsset(context.getAssets(), "fonts/Light.ttf");

            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout);
            civProfile = (CircleImageView) itemView.findViewById(R.id.civProfile);
            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            tvDistance.setTypeface(light);
            tvDuration.setTypeface(light);
            tvTitle.setTypeface(regular);
            tvTag.setTypeface(light);
            tvDescription.setTypeface(light);
        }
    }
}
