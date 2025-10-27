package com.example.inmobiliaria.ui.inmueble;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgregarViewModel extends AndroidViewModel {

    private MutableLiveData<Uri> mUri = new MutableLiveData<>();


    public AgregarViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMUri(){
        return mUri;
    }

    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == Activity.RESULT_OK){
            Uri uri = result.getData().getData();
            Log.d(
                    "uri",
                    uri.toString()
            );
            mUri.postValue(uri);
        }
    }


}