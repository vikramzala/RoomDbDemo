package com.example.roomdbdemo.db;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataListViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<List<DataEntity>> datas;

    public DataListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new DataRepository(application);
    }

    public LiveData<List<DataEntity>> getDatas() {
        if (datas == null) {
            datas = mRepository.getAllDatas();
        }
        return datas;
    }

    public DataEntity getData(int id) throws ExecutionException, InterruptedException {
        return mRepository.getData(id);
    }

    public void insertData(DataEntity data) {
        Log.e("insert", "" + data.firstName + "==" + data.birthDate);
        mRepository.insertData(data);
    }

    public void updateById(String firstName, String lastName, String mobileNo, String birthDate, int id) {
        mRepository.updateById(firstName, lastName, mobileNo, birthDate, id);
    }

    public void updateData(DataEntity data) {
        mRepository.updateData(data);
    }

    public void deleteData(DataEntity data) {
        mRepository.deleteData(data);
    }


}
