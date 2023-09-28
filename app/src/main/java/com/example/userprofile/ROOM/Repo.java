package com.example.userprofile.ROOM;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Display;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repo {
    private Dao dao;
    private LiveData<List<Model>> allUsers;

    public Repo(Application application) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(application);
        dao = databaseHelper.Dao();
        allUsers = dao.getAllUsers();
    }

    public void insert(Model model) {
        new InsertUserAsyncTask(dao).execute(model);
    }

    public void update(Model model) {
        new UpdateUserAsyncTask(dao).execute(model);
    }

    public void delete(Model model) {
        new DeleteUserAsyncTask(dao).execute(model);
    }

    public void deleteAllUsers() {
        new DeleteAllUsersAsyncTask(dao).execute();
    }

    public LiveData<List<Model>> getAllUsers() {
        return allUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<Model, Void, Void> {
        private Dao dao;

        private InsertUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Model... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<Model, Void, Void> {
        private Dao dao;

        private UpdateUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Model... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<Model, Void, Void> {
        private Dao dao;

        private DeleteUserAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Model... models) {
            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllUsersAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllUsersAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
    public LiveData<Model> getUserById(String userId) {
        return dao.getUserById(userId);
    }


}
