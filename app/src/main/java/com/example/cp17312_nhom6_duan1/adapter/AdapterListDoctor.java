package com.example.cp17312_nhom6_duan1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cp17312_nhom6_duan1.OrderDoctorActivity;
import com.example.cp17312_nhom6_duan1.R;
import com.example.cp17312_nhom6_duan1.adapter.ViewHolder.ListDoctorViewHolder;
import com.example.cp17312_nhom6_duan1.dao.AccountDAO;
import com.example.cp17312_nhom6_duan1.dao.ServicesDAO;
import com.example.cp17312_nhom6_duan1.dto.AccountDTO;
import com.example.cp17312_nhom6_duan1.dto.DoctorDTO;
import com.example.cp17312_nhom6_duan1.dto.ServicesDTO;

import java.util.ArrayList;

public class AdapterListDoctor extends RecyclerView.Adapter<ListDoctorViewHolder> {
    private ArrayList<DoctorDTO> listDtoDoctor = new ArrayList<>();
    private Context context;

    public AdapterListDoctor(ArrayList<DoctorDTO> listDtoDoctor, Context context) {
        this.listDtoDoctor = listDtoDoctor;
        this.context = context;
    }

    @NonNull
    @Override
    public ListDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlistdoctor,parent,false);
        return new ListDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDoctorViewHolder holder, int position) {
        DoctorDTO doctorDTO = listDtoDoctor.get(position);

        AccountDAO accountDAO = new AccountDAO(context);
        ServicesDAO servicesDAO = new ServicesDAO(context);

        AccountDTO accountDTO = accountDAO.getDtoAccount(doctorDTO.getUser_id());
        holder.tvNameDoctor.setText(accountDTO.getFullName());

        ServicesDTO servicesDTO = servicesDAO.getDtoServiceByIdByService(doctorDTO.getService_id());

        holder.tvNameService.setText(servicesDTO.getServicesName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDoctorActivity.class);
                intent.putExtra("idDoctor",doctorDTO.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDtoDoctor.size();
    }
}
