package com.example.cp17312_nhom6_duan1.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cp17312_nhom6_duan1.R;
import com.example.cp17312_nhom6_duan1.adapter.SpinneRoomsAdapter;
import com.example.cp17312_nhom6_duan1.adapter.SpinnerServiceAdapter;
import com.example.cp17312_nhom6_duan1.adapter.SpinnerTimeWorkAdapter;
import com.example.cp17312_nhom6_duan1.dao.AccountDAO;
import com.example.cp17312_nhom6_duan1.dao.DoctorDAO;
import com.example.cp17312_nhom6_duan1.dao.RoomsDAO;
import com.example.cp17312_nhom6_duan1.dao.ServicesDAO;
import com.example.cp17312_nhom6_duan1.dao.TimeWorkDAO;
import com.example.cp17312_nhom6_duan1.dto.AccountDTO;
import com.example.cp17312_nhom6_duan1.dto.DoctorDTO;
import com.example.cp17312_nhom6_duan1.dto.RoomsDTO;
import com.example.cp17312_nhom6_duan1.dto.ServicesDTO;
import com.example.cp17312_nhom6_duan1.dto.TimeWorkDTO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddDoctorActivity extends AppCompatActivity {
    private Spinner spRooms, spServices, spTimeWork;
    private SpinneRoomsAdapter spinneRoomsAdapter;
    private SpinnerServiceAdapter spinnerServiceAdapter;
    private SpinnerTimeWorkAdapter spinnerTimeWorkAdapter;
    private Button btnSaveDoctor;
    private TextInputLayout edNameDoctor,edPhoneDoctor,edNameAccount,edPassWordDoctor,edDes,edBirthdayDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        init();

        RoomsDAO roomsDAO = new RoomsDAO(this);
        ServicesDAO servicesDAO = new ServicesDAO(this);
        TimeWorkDAO timeWorkDAO = new TimeWorkDAO(this);
        AccountDAO accountDAO = new AccountDAO(this);
        DoctorDAO doctorDAO = new DoctorDAO(this);

        ArrayList<RoomsDTO> listDtoRooms = roomsDAO.selectAll();
        spinneRoomsAdapter = new SpinneRoomsAdapter(listDtoRooms, this);
        spRooms.setAdapter(spinneRoomsAdapter);

        ArrayList<ServicesDTO> listDtoService = servicesDAO.selectAll();
        spinnerServiceAdapter = new SpinnerServiceAdapter(listDtoService, this);
        spServices.setAdapter(spinnerServiceAdapter);

        ArrayList<TimeWorkDTO> listDtoTimeWork = timeWorkDAO.getAll();
        spinnerTimeWorkAdapter = new SpinnerTimeWorkAdapter(listDtoTimeWork,this);
        spTimeWork.setAdapter(spinnerTimeWorkAdapter);

        btnSaveDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setUserName(edNameAccount.getEditText().getText().toString());
                accountDTO.setPassWord(edPassWordDoctor.getEditText().getText().toString());
                accountDTO.setPhoneNumber(edPhoneDoctor.getEditText().getText().toString());
                accountDTO.setFullName(edNameDoctor.getEditText().getText().toString());
                accountDTO.setRole("Doctor");
                accountDTO.setImg(null);

                long res = accountDAO.insertAccount(accountDTO);

                AccountDTO accountDTO1 = accountDAO.getTopDtoAccount();
                DoctorDTO doctorDTO = new DoctorDTO();
                doctorDTO.setUser_id(accountDTO1.getId());
                doctorDTO.setBirthday(formatDate(edBirthdayDoctor.getEditText().getText().toString()));

                ServicesDTO servicesDTO = (ServicesDTO) spServices.getSelectedItem();
                doctorDTO.setService_id(servicesDTO.getServicesId());

                RoomsDTO roomsDTO = (RoomsDTO) spRooms.getSelectedItem();
                doctorDTO.setRoom_id(roomsDTO.getId());

                doctorDTO.setDescription(edDes.getEditText().getText().toString());

                TimeWorkDTO timeWorkDTO  = (TimeWorkDTO) spTimeWork.getSelectedItem();
                doctorDTO.setTimework_id(timeWorkDTO.getId());

                long res1 = doctorDAO.insertRow(doctorDTO);
                if(res1>0){
                    Toast.makeText(AddDoctorActivity.this, "Thêm bác sĩ thành công", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(AddDoctorActivity.this, "Thêm bác sĩ không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void init() {
        spRooms = findViewById(R.id.spRooms);
        spServices = findViewById(R.id.spServices);
        spTimeWork = findViewById(R.id.spTimeWork);
        btnSaveDoctor = findViewById(R.id.btnSaveDoctor);
        edNameDoctor = findViewById(R.id.edNameDoctor);
        edPhoneDoctor = findViewById(R.id.edPhoneDoctor);
        edNameAccount = findViewById(R.id.edNameAccount);
        edPassWordDoctor = findViewById(R.id.edPassWordDoctor);
        edDes = findViewById(R.id.edDes);
        edBirthdayDoctor = findViewById(R.id.edBirthdayDoctor);
    }
    public String formatDate(String a) {
        String newDate ="";
        Date objdate2 = new Date(System.currentTimeMillis());
        DateFormat dateFormat2 = new DateFormat();
        String dates2 =a;
        SimpleDateFormat Format2 = new SimpleDateFormat("dd/mm/yyyy");
        try {
            Date obj = Format2.parse(dates2);
            newDate = (String) dateFormat2.format("yyyy/mm/dd", obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }
}