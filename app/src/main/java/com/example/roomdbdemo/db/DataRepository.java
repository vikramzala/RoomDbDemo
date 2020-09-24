package com.example.roomdbdemo.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataRepository {

    private DataDao mDataDao;
    private LiveData<List<DataEntity>> mAllDatas;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDataDao = db.noteDao();
        mAllDatas = mDataDao.getAllDatas();
    }

    public LiveData<List<DataEntity>> getAllDatas() {
        return mAllDatas;
    }

    public DataEntity getData(int dataId) throws ExecutionException, InterruptedException {
        return new getDataAsync(mDataDao).execute(dataId).get();
    }

    public void insertData(DataEntity data) {
        new insertDatasAsync(mDataDao).execute(data);
    }

    public void updateData(DataEntity data) {
        new updateDatasAsync(mDataDao).execute(data);
    }

    public void updateById(String firstName, String lastName, String mobileNo, String birthDate, int id) {
        mDataDao.updateById(firstName, lastName, mobileNo, birthDate, id);
    }

    public void deleteData(DataEntity data) {
        new deleteDatasAsync(mDataDao).execute(data);
    }

    public void deletDatas() {
        new deleteAllDatasAsync(mDataDao).execute();
    }


    private static class getDataAsync extends AsyncTask<Integer, Void, DataEntity> {

        private DataDao mDataDaoAsync;

        getDataAsync(DataDao animalDao) {
            mDataDaoAsync = animalDao;
        }

        @Override
        protected DataEntity doInBackground(Integer... ids) {
            return mDataDaoAsync.getDataById(ids[0]);
        }
    }

    private static class insertDatasAsync extends AsyncTask<DataEntity, Void, Long> {

        private DataDao mDataDaoAsync;

        insertDatasAsync(DataDao dataDao) {
            mDataDaoAsync = dataDao;
        }

        @Override
        protected Long doInBackground(DataEntity... notes) {
            long id = mDataDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateDatasAsync extends AsyncTask<DataEntity, Void, Void> {

        private DataDao mDataDaoAsync;

        updateDatasAsync(DataDao noteDao) {
            mDataDaoAsync = noteDao;
        }

        @Override
        protected Void doInBackground(DataEntity... notes) {
            mDataDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteDatasAsync extends AsyncTask<DataEntity, Void, Void> {

        private DataDao mDataDaoAsync;

        deleteDatasAsync(DataDao dataDao) {
            mDataDaoAsync = dataDao;
        }

        @Override
        protected Void doInBackground(DataEntity... notes) {
            mDataDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllDatasAsync extends AsyncTask<DataEntity, Void, Void> {

        private DataDao mDataDaoAsync;

        deleteAllDatasAsync(DataDao dataDao) {
            mDataDaoAsync = dataDao;
        }

        @Override
        protected Void doInBackground(DataEntity... datas) {
            mDataDaoAsync.deleteAll();
            return null;
        }
    }

}
