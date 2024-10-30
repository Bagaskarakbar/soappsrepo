package com.averin.SOAP.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.averin.SOAP.R;
import com.averin.SOAP.models.RumahSakit;
import com.averin.SOAP.utilities.Utility;
import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

public class ListRsAdapter extends RecyclerView.Adapter<ListRsAdapter.ListRsAdapterViewHolder> {

    Context context;
    ArrayList<RumahSakit> rumahSakits;

    public ListRsAdapter(Context context, ArrayList<RumahSakit> rumahSakits) {
        this.context = context;
        this.rumahSakits = rumahSakits;
    }

    public void setRumahSakits(ArrayList<RumahSakit> rumahSakits) {
        this.rumahSakits = rumahSakits;
    }

    @NonNull
    @Override
    public ListRsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_rs, viewGroup, false);
        return new ListRsAdapterViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRsAdapterViewHolder holder, int position) {
        holder.txNamaRs.setText(rumahSakits.get(position).getNamaRs());
        holder.txSpesialisasiRs.setText("Rumah Sakit " + rumahSakits.get(position).getSpesialisasiRs());

        int isbpjs = rumahSakits.get(position).getBpjs();
        if (isbpjs == 1) {
            holder.layoutBPJS.setVisibility(View.VISIBLE);
        } else {
            holder.layoutBPJS.setVisibility(View.INVISIBLE);
        }

        //byte[] decodedImgRs = Base64.decode(rumahSakits.get(position).getFotoRs(), Base64.DEFAULT);
        Glide.with(context).load(rumahSakits.get(position).getFotoRs()).thumbnail(0.5f).signature(new ObjectKey("imgrs@"+rumahSakits.get(position).getNamaRs())).transition(withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgRS);
    }

    @Override
    public int getItemCount() {
        return rumahSakits.size();
    }

    class ListRsAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView txNamaRs, txSpesialisasiRs, txValueJarak;
        ImageView imgRS;
        LinearLayout layoutBPJS;
        CardView cvItemRow;

        public ListRsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txNamaRs = itemView.findViewById(R.id.txNamaRs);
            txSpesialisasiRs = itemView.findViewById(R.id.txSpesialisasiRs);
            txValueJarak = itemView.findViewById(R.id.txValueJarak);
            imgRS = itemView.findViewById(R.id.imgRS);
            layoutBPJS = itemView.findViewById(R.id.layoutBPJS);
            cvItemRow = itemView.findViewById(R.id.cvItemRow);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongCLick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ListRsAdapter.ClickListener clickListener;

        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final ListRsAdapter.ClickListener clickListener) {
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
