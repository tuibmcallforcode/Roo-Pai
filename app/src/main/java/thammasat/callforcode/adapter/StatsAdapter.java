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
import thammasat.callforcode.manager.InternalStorage;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.manager.Singleton;
import thammasat.callforcode.model.Disaster;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    private Context context;
    private List<Disaster> disasterList = new ArrayList<>();
    private Typeface bold, regular, light;
    private Date now = new Date();

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnItemClick onItemClick;

    public StatsAdapter(Context context, List<Disaster> disasterList) {
        this.context = context;
        this.disasterList = disasterList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_list, parent, false);
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
        holder.tvTitle.setText(disasterList.get(position).getTitle());
        holder.tvDate.setText(time + " days ago");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(disasterList.get(position), time, 0);
            }
        });
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
        TextView tvTitle, tvDate;

        public ViewHolder(View itemView, int type) {
            super(itemView);

            bold = Typeface.createFromAsset(context.getAssets(), "fonts/Bold.ttf");
            regular = Typeface.createFromAsset(context.getAssets(), "fonts/Regular.ttf");
            light = Typeface.createFromAsset(context.getAssets(), "fonts/Light.ttf");

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTitle.setTypeface(regular);
            tvDate.setTypeface(light);
        }
    }
}
