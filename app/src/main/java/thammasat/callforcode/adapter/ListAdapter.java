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

import java.util.List;

import thammasat.callforcode.R;
import thammasat.callforcode.manager.GlideApp;
import thammasat.callforcode.manager.OnItemClick;
import thammasat.callforcode.model.Disaster;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<Disaster> disasterList;
    private Typeface bold, regular, light;

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
        holder.tvCountry.setText(disasterList.get(position).getCountry());
        holder.tvTitle.setText(disasterList.get(position).getTitle());
        holder.tvTag.setText(disasterList.get(position).getDisaster());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(disasterList.get(position));
            }
        });

        if (holder.getItemViewType() == 1) {
            GlideApp.with(context)
                    .load(disasterList.get(position).getUrl_thumb())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade()) //Optional
                    .into(holder.ivCover);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (disasterList.get(position).getUrl_thumb() == null)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return disasterList != null ? disasterList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView ivCover;
        TextView tvTitle, tvCountry, tvDuration, tvDistance, tvTag;

        public ViewHolder(View itemView, int type) {
            super(itemView);

            bold = Typeface.createFromAsset(context.getAssets(), "fonts/Bold.ttf");
            regular = Typeface.createFromAsset(context.getAssets(), "fonts/Regular.ttf");
            light = Typeface.createFromAsset(context.getAssets(), "fonts/Light.ttf");

            cardView = (CardView) itemView.findViewById(R.id.cardView);
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
