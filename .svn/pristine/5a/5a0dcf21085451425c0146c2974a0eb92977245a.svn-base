package com.averin.SOAP.adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.averin.SOAP.R;
import com.averin.SOAP.models.Poliklinik;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import java.util.ArrayList;

public class ListPoliAdapter extends RecyclerView.Adapter<ListPoliAdapter.PoliViewHolder> {
    Context context;
    ArrayList<Poliklinik> polikliniks;

    TransactionData trans;

    public ListPoliAdapter(Context context, ArrayList<Poliklinik> polikliniks) {
        this.context = context;
        this.polikliniks = polikliniks;
    }

    public ArrayList<Poliklinik> getPolikliniks() {
        return polikliniks;
    }

    public void setPolikliniks(ArrayList<Poliklinik> polikliniks) {
        this.polikliniks = polikliniks;
    }

    @NonNull
    @Override
    public PoliViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_poli, viewGroup, false);
        return new ListPoliAdapter.PoliViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliViewHolder holder, int position) {
        trans = new TransactionData(context);
        holder.txNamaPoli.setText(polikliniks.get(position).getNamaPoli());

        Glide.with(context).load(trans.getRSimgUrl() + polikliniks.get(position).getFotoPoli()).thumbnail(0.5f).transition(withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ico_poli);
    }

    @Override
    public int getItemCount() {
        return polikliniks.size();
    }

    public class PoliViewHolder extends RecyclerView.ViewHolder {
        TextView txNamaPoli;
        ImageView ico_poli;

        public PoliViewHolder(@NonNull View itemView) {
            super(itemView);
            txNamaPoli = itemView.findViewById(R.id.txNamaPoli);
            ico_poli = itemView.findViewById(R.id.ico_poli);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongCLick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ListPoliAdapter.ClickListener clickListener;

        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final ListPoliAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongCLick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(motionEvent)) {
                clickListener.onClick(child, recyclerView.getChildLayoutPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }
}
