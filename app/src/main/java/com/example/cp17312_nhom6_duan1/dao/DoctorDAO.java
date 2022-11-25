package com.example.cp17312_nhom6_duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cp17312_nhom6_duan1.database.MyDbHelper;
import com.example.cp17312_nhom6_duan1.dto.AllDTO;
import com.example.cp17312_nhom6_duan1.dto.DoctorDTO;

import java.util.ArrayList;

public class DoctorDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public DoctorDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertRow(DoctorDTO doctorDTO) {
        ContentValues val = new ContentValues();
        val.put(DoctorDTO.colIdUser, doctorDTO.getUser_id());
        val.put(DoctorDTO.colBirthday, doctorDTO.getBirthday());
        val.put(DoctorDTO.colService_id, doctorDTO.getService_id());
        val.put(DoctorDTO.colRoom_id, doctorDTO.getRoom_id());
        val.put(DoctorDTO.colDescription, doctorDTO.getDescription());
        val.put(DoctorDTO.colTimework_id, doctorDTO.getTimework_id());

        long res = db.insert(DoctorDTO.nameTable, null, val);
        return res;
    }

    public int updateRow(DoctorDTO doctorDTO) {
        ContentValues val = new ContentValues();
        val.put(DoctorDTO.colIdUser, doctorDTO.getUser_id());
        val.put(DoctorDTO.colBirthday, doctorDTO.getBirthday());
        val.put(DoctorDTO.colService_id, doctorDTO.getService_id());
        val.put(DoctorDTO.colRoom_id, doctorDTO.getRoom_id());
        val.put(DoctorDTO.colDescription, doctorDTO.getDescription());
        val.put(DoctorDTO.colTimework_id, doctorDTO.getTimework_id());

        String[] check = new String[]{doctorDTO.getId() + ""};

        int res = db.update(DoctorDTO.nameTable, val, "id = ?", check);
        return res;
    }

    public ArrayList<DoctorDTO> selectAll() {
        ArrayList<DoctorDTO> listDtoDoctor = new ArrayList<>();
        Cursor cs = db.query(DoctorDTO.nameTable, null, null, null, null, null, null);
        if (cs.moveToFirst()) {
            while (!cs.isAfterLast()) {
                DoctorDTO doctorDTO = new DoctorDTO();
                doctorDTO.setId(cs.getInt(0));
                doctorDTO.setUser_id(cs.getInt(1));
                doctorDTO.setBirthday(cs.getString(2));
                doctorDTO.setService_id(cs.getInt(3));
                doctorDTO.setRoom_id(cs.getInt(4));
                doctorDTO.setDescription(cs.getString(5));
                doctorDTO.setTimework_id(cs.getInt(6));

                listDtoDoctor.add(doctorDTO);
                cs.moveToNext();
            }
        }
        return listDtoDoctor;
    }
    public DoctorDTO getDtoDoctorByIdDoctor(int idDoctor){
        DoctorDTO doctorDTO = new DoctorDTO();
        String where = "id = ?";
        String[] whereArgs = {idDoctor+""};
        Cursor cs = db.query(DoctorDTO.nameTable,null,where,whereArgs,null,null,null);
        if(cs.moveToFirst()){
            doctorDTO.setId(cs.getInt(0));
            doctorDTO.setUser_id(cs.getInt(1));
            doctorDTO.setBirthday(cs.getString(2));
            doctorDTO.setService_id(cs.getInt(3));
            doctorDTO.setRoom_id(cs.getInt(4));
            doctorDTO.setDescription(cs.getString(5));
            doctorDTO.setTimework_id(cs.getInt(6));
        }
        return doctorDTO;
    }
    public ArrayList<AllDTO> CalendarDoctor(int idDoctor){
        ArrayList<AllDTO> list = new ArrayList<>();
        String select ="select tbAccount.fullName, tbFile.birthday, tbOrderDoctor.start_date, tbOrderDoctor.start_time from  tbFile join tbOrderDoctor  on tbOrderDoctor.file_id = tbFile.id join tbDoctor on tbOrderDoctor.doctor_id = tbDoctor.id join tbAccount on tbFile.user_id =  tbAccount.id where tbOrderDoctor.doctor_id = "+idDoctor +" and tbAccount.role='User' order by tbOrderDoctor.start_date , tbOrderDoctor.start_time ASC";
        Cursor cursor = db.rawQuery(select, null);
        if(cursor!=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                AllDTO obj = new AllDTO();
                obj.setFullameUser(cursor.getString(0));
                obj.setBirthdayUser(cursor.getString(1));
                obj.setStartDate(cursor.getString(2));
                obj.setStartTime(cursor.getString(3));
                list.add(obj);
                cursor.moveToNext();
            }
        }
        return  list;
    }
    public ArrayList<AllDTO> CalendarDoctorByDateNow(int idDoctor){
        ArrayList<AllDTO> list = new ArrayList<>();
        String select ="select tbAccount.fullName, tbFile.birthday, tbOrderDoctor.start_date, tbOrderDoctor.start_time from  tbFile join tbOrderDoctor  on tbOrderDoctor.file_id = tbFile.id join tbDoctor on tbOrderDoctor.doctor_id = tbDoctor.id join tbAccount on tbFile.user_id =  tbAccount.id where tbOrderDoctor.doctor_id = "+idDoctor +" and tbAccount.role='User' and tbOrderDoctor.start_date = date('now') order by tbOrderDoctor.start_date , tbOrderDoctor.start_time ASC";
        Cursor cursor = db.rawQuery(select, null);
        if(cursor!=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                AllDTO obj = new AllDTO();
                obj.setFullameUser(cursor.getString(0));
                obj.setBirthdayUser(cursor.getString(1));
                obj.setStartDate(cursor.getString(2));
                obj.setStartTime(cursor.getString(3));
                list.add(obj);
                cursor.moveToNext();
            }
        }
        return  list;
    }

    public int getIdDoctorByIdUser(int idUser){
        int id=0;
        String select ="select tbDoctor.id from tbDoctor join tbAccount on tbDoctor.user_id = tbAccount.id where tbDoctor.user_id = "+idUser;
        Cursor cursor = db.rawQuery(select, null);
        if(cursor!=null){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }
    public ArrayList<AllDTO> getDocotrByService(int service){
        ArrayList<AllDTO> list = new ArrayList<>();
        String select = "select  tbAccount.fullName, tbDoctor.birthday,tbServices.name, tbAccount.role from tbDoctor join tbAccount on  tbDoctor.user_id =tbAccount.id join tbServices on tbDoctor.service_id =tbServices.id   where  tbAccount.role='Doctor' and tbServices.id = "+service;
        Cursor cursor = db.rawQuery(select, null);
        if(cursor!=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                AllDTO obj = new AllDTO();
                obj.setFullameUser(cursor.getString(0));
                obj.setBirthday(cursor.getString(1));
                obj.setServicesName(cursor.getString(2));
                list.add(obj);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
    public ArrayList<AllDTO> getDocotrByServiceAll(){
        ArrayList<AllDTO> list = new ArrayList<>();
        String select = "select  tbAccount.fullName, tbDoctor.birthday,tbServices.name, tbAccount.role from tbDoctor join tbAccount on  tbDoctor.user_id =tbAccount.id join tbServices on tbDoctor.service_id =tbServices.id   where  tbAccount.role='Doctor'";
        Cursor cursor = db.rawQuery(select, null);
        if(cursor!=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                AllDTO obj = new AllDTO();
                obj.setFullameUser(cursor.getString(0));
                obj.setBirthday(cursor.getString(1));
                obj.setServicesName(cursor.getString(2));
                list.add(obj);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}