package thammasat.callforcode.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import thammasat.callforcode.R;
import thammasat.callforcode.manager.GlideApp;
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.model.Disaster;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<Disaster> disasterList;
    private Typeface bold, regular, light;
    private Date now = new Date();
    private double latitude = 0, longitude = 0;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnItemClick onItemClick;

    public ListAdapter(Context context, List<Disaster> disasterList) {
        this.context = context;
        this.disasterList = disasterList;
        try {
            latitude = (double) InternalStorage.readObject(context, "latitude");
            longitude = (double) InternalStorage.readObject(context, "longitude");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        holder.tvCountry.setText(disasterList.get(position).getTime());
        holder.tvTitle.setText(disasterList.get(position).getTitle());
        holder.tvTag.setText(disasterList.get(position).getSeverity());
        holder.tvDuration.setText(getDateDiff(date, now, TimeUnit.DAYS) + " days ago | ");
        holder.tvDistance.setText(distance(latitude, longitude, disasterList.get(position).getLoc().getCoordinates().get(0), disasterList.get(position).getLoc().getCoordinates().get(1), "K") + " km away");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(disasterList.get(position));
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

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static final double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    private static final double rad2deg(double rad)
    {
        return (rad * 180 / Math.PI);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CircleImageView civProfile;
        ImageView ivCover;
        TextView tvTitle, tvCountry, tvDuration, tvDistance, tvTag;

        public ViewHolder(View itemView, int type) {
            super(itemView);

            bold = Typeface.createFromAsset(context.getAssets(), "fonts/Bold.ttf");
            regular = Typeface.createFromAsset(context.getAssets(), "fonts/Regular.ttf");
            light = Typeface.createFromAsset(context.getAssets(), "fonts/Light.ttf");

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            civProfile = (CircleImageView) itemView.findViewById(R.id.civProfile);
            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
            tvCountry = (TextView) itemView.findViewById(R.id.tvCountry);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);

            tvCountry.setTypeface(light);
            tvDistance.setTypeface(light);
            tvDuration.setTypeface(light);
            tvTitle.setTypeface(regular);
            tvTag.setTypeface(light);
        }
    }
}
