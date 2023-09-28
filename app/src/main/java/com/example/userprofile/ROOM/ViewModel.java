package com.example.userprofile.ROOM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private Repo repo;
    private LiveData<List<Model>> allUsers;
    private MutableLiveData<Integer> selectedItemId = new MutableLiveData<>();

    public ViewModel(@NonNull Application application) {
        super(application);
        repo = new Repo(application);
        allUsers = repo.getAllUsers();
    }
    public void insert(Model model) {
        repo.insert(model);
    }
    public void update(Model model) {
        repo.update(model);
    }
    public void delete(Model model) {
        repo.delete(model);
    }
    public void deleteAllUsers() {
        repo.deleteAllUsers();
    }
    public LiveData<List<Model>> getAllUsers() {
        return allUsers;
    }
//    public LiveData<Model> getUserById(String userId) {
//        return repo.getUserById(userId);
//    }
//    public void setSelectedItemId(int itemId) {
//        selectedItemId.setValue(itemId);
//    }
//
//    public LiveData<Integer> getSelectedItemId() {
//        return selectedItemId;
//    }


}
