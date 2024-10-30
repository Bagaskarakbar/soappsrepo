package com.averin.SOAP.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.averin.SOAP.R;
import com.averin.SOAP.models.Antrian;
import com.averin.SOAP.utilities.Utility;

import java.util.ArrayList;

public class ListAntrianAdapter extends RecyclerView.Adapter<ListAntrianAdapter.AntrianViewHolder> {

    private Context context;
    private ArrayList<Antrian> antrians;
    Utility util = new Utility();

    public ListAntrianAdapter(Context context, ArrayList<Antrian> antrians) {
        this.context = context;
        this.antrians = antrians;
    }

    public ArrayList<Antrian> getAntrians() {
        return antrians;
    }

    public void setAntrians(ArrayList<Antrian> antrians) {
        this.antrians = antrians;
    }

    @NonNull
    @Override
    public AntrianViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_row_antrian, viewGroup, false);
        return new AntrianViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AntrianViewHolder antrianViewHolder, int i) {
        antrianViewHolder.txNoAntri.setText(String.valueOf(i+1) + ".");
        antrianViewHolder.txJamMulai.setText(util.FormatHour(antrians.get(i).getJamMulai()) + " - ");
        antrianViewHolder.txJamAkhir.setText(util.FormatHour(antrians.get(i).getJamSelesai()));

        Boolean statusEntri = antrians.get(i).getStatusEntri();
        Boolean passed = antrians.get(i).getPassed();
        if (passed || statusEntri){
            antrianViewHolder.rlAntrian.setBackgroundResource(R.drawable.colored_rounded_button_disabled);
            antrianViewHolder.cvItemRow.setClickable(false);
        } else {
            antrianViewHolder.rlAntrian.setBackgroundResource(R.drawable.colored_rounded_button);
            antrianViewHolder.cvItemRow.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return antrians.size();
    }

    public class AntrianViewHolder extends  RecyclerView.ViewHolder {

        CardView cvItemRow;
        RelativeLayout rlAntrian;
        TextView txJamMulai, txJamAkhir, txNoAntri;

        public AntrianViewHolder(@NonNull View itemView) {
            super(itemView);

            rlAntrian = itemView.findViewById(R.id.rlAntrian);
            cvItemRow = itemView.findViewById(R.id.cvItemRow);
            txJamMulai = itemView.findViewById(R.id.txJamMulai);
            txJamAkhir = itemView.findViewById(R.id.txJamAkhir);
            txNoAntri = itemView.findViewById(R.id.txNoAntri);
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
