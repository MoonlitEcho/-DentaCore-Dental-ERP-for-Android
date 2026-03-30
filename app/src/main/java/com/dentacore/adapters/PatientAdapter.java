package com.dentacore.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dentacore.R;
import com.dentacore.models.Patient;
import java.util.List;
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.VH> {
    public interface OnClick { void onClick(Patient p); }
    private List<Patient> list;
    private final OnClick listener;
    public PatientAdapter(List<Patient> list, OnClick l){ this.list=list; this.listener=l; }
    public void setPatients(List<Patient> l){ this.list=l; notifyDataSetChanged(); }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int t){
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.item_patient,p,false)); }
    @Override public void onBindViewHolder(@NonNull VH h, int i){
        Patient p=list.get(i);
        h.tvName.setText(p.name);
        h.tvPhone.setText(p.phone);
        h.tvBalance.setText(p.getBalance());
        String initials = p.name.length()>=2 ? p.name.substring(0,2).toUpperCase() : p.name.toUpperCase();
        h.tvInitials.setText(initials);
        h.itemView.setOnClickListener(v->listener.onClick(p));
    }
    @Override public int getItemCount(){ return list==null?0:list.size(); }
    static class VH extends RecyclerView.ViewHolder {
        TextView tvName,tvPhone,tvBalance,tvInitials;
        VH(View v){ super(v); tvName=v.findViewById(R.id.tvPatientName); tvPhone=v.findViewById(R.id.tvPatientPhone); tvBalance=v.findViewById(R.id.tvPatientBalance); tvInitials=v.findViewById(R.id.tvInitials); }
    }
}
