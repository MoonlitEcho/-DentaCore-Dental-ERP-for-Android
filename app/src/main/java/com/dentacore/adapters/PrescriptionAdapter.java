package com.dentacore.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dentacore.R;
import com.dentacore.models.Prescription;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.VH> {
    public interface OnClick { void onClick(Prescription p); }
    private List<Prescription> list = new ArrayList<>();
    private final OnClick listener;
    public PrescriptionAdapter(OnClick l){ this.listener=l; }
    public void setPrescriptions(List<Prescription> l){ this.list=l; notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t){
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_prescription,p,false)); }
    @Override public void onBindViewHolder(@NonNull VH h, int i){
        Prescription p=list.get(i);
        h.tvDiagnosis.setText(p.diagnosis!=null&&!p.diagnosis.isEmpty()?p.diagnosis:"No diagnosis");
        h.tvDate.setText(new SimpleDateFormat("dd MMM yyyy",Locale.getDefault()).format(new Date(p.createdAt)));
        h.tvAmount.setText("Rs."+String.format("%.0f",p.billAmount));
        h.tvProcedure.setText(p.procedureType!=null?p.procedureType:"General");
        int medCount = p.medications!=null?p.medications.size():0;
        h.tvMedCount.setText(medCount+" med"+(medCount!=1?"s":""));
        h.itemView.setOnClickListener(v->listener.onClick(p));
    }
    @Override public int getItemCount(){ return list.size(); }
    static class VH extends RecyclerView.ViewHolder {
        TextView tvDiagnosis,tvDate,tvAmount,tvProcedure,tvMedCount;
        VH(View v){ super(v); tvDiagnosis=v.findViewById(R.id.tvDiagnosis); tvDate=v.findViewById(R.id.tvDate); tvAmount=v.findViewById(R.id.tvAmount); tvProcedure=v.findViewById(R.id.tvProcedure); tvMedCount=v.findViewById(R.id.tvMedCount); }
    }
}
