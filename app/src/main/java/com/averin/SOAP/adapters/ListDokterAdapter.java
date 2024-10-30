package com.averin.SOAP.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.averin.SOAP.R;
import com.averin.SOAP.activities.ListDokterActivity;
import com.averin.SOAP.models.Dokter;
import com.averin.SOAP.utilities.TransactionData;
import com.averin.SOAP.utilities.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ListDokterAdapter extends RecyclerView.Adapter<ListDokterAdapter.ListDokterViewHolder> {
    private Context context;
    private ArrayList<Dokter> dokters;

    TransactionData trans;
    Utility utils = new Utility();

    public ListDokterAdapter(Context context, ArrayList<Dokter> dokters) {
        this.context = context;
        this.dokters = dokters;
    }

    @NonNull
    @Override
    public ListDokterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_dokter, viewGroup, false);
        return new ListDokterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDokterViewHolder holder, int i) {
        trans = new TransactionData(context);
        holder.txNamaDr.setText(dokters.get(i).getNamaDokter());
        holder.txJadwalJam.setText(utils.FormatHour(dokters.get(i).getJamMulai()) + " s/d " + utils.FormatHour(dokters.get(i).getJamSelesai()));

        Glide.with(context).load(trans.getRSBaseUrl() + dokters.get(i).getFotoDokter()).thumbnail(0.5f).signature(new ObjectKey("imgDr@"+dokters.get(i).getNamaDokter())).transition(withCrossFade()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgDr);
    }

    @Override
    public int getItemCount() {
        return dokters.size();
    }

    public class ListDokterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDr;
        TextView txNamaDr, txJadwalJam;

        public ListDokterViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDr = itemView.findViewById(R.id.imgDr);
            txNamaDr = itemView.findViewById(R.id.txNamaDr);
            txJadwalJam = itemView.findViewById(R.id.txJadwalJam);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongCLick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ListAntrianAdapter.ClickListener clickListener;

        public RecyclerTouchListener(final Context context, final RecyclerView recyclerView, final ListAntrianAdapter.ClickListener clickListener) {
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
