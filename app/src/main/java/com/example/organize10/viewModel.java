package com.example.organize10;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class viewModel  extends ViewModel {
        private MutableLiveData<List<todo>> mTodo;
        private TodoRepo repo;


        public LiveData<List<todo>> getTodo(){
            return mTodo;
        }

        public void init(){
            if(mTodo != null){
                return;
            }
            repo = TodoRepo.getInstance();
            mTodo = repo.getTodos();
        }


}
