package com.example.animalswiki;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalswiki.entities.Cat;
import com.example.animalswiki.entities.CatImage;
import com.example.animalswiki.net.ApiFactory;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Cat>> breeds = new MutableLiveData<>();
    private final MutableLiveData<List<CatImage>> catImageList = new MutableLiveData<>();

    public LiveData<List<CatImage>> getCatImageList() {
        return catImageList;
    }
    public LiveData<List<Cat>> getBreeds() {
        return breeds;
    }

    public void getCatInfo() {
        Disposable disposable = ApiFactory.getApiService().allCats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Cat>>() {
                    @Override
                    public void accept(List<Cat> cats) throws Throwable {
                        breeds.setValue(cats);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);

    }
    public void getCatImage(String id){
        Disposable disposable = ApiFactory.getApiService().catImage(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CatImage>>() {
                    @Override
                    public void accept(List<CatImage> imageList) throws Throwable {
                        catImageList.setValue(imageList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}


