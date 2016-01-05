package com.bordingvista.promotion.promotion.presenters;

import android.util.Log;

import com.bordingvista.promotion.promotion.Util.Constants;
import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.models.Promotion;
import com.bordingvista.promotion.promotion.services.PromotionService;
import com.bordingvista.promotion.promotion.views.ProductCreationActivity;
import com.google.gson.Gson;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by imran.zahid on 1/4/2016.
 */
public class ProductCreationPresenter {
    private ProductCreationActivity view;
    private PromotionService service;

    public ProductCreationPresenter(ProductCreationActivity view, PromotionService service) {
        this.view = view;
        this.service = service;
    }

    public void addProduct(Product product){
        service.getApi()
                .addProduct(product)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Product>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       e.printStackTrace();
                        view.showConfirmation(false);
                    }

                    @Override
                    public void onNext(Product product) {
                        Log.d(Constants.LOG_PROMOTION, "Id:" + product.getId() + "Description:" +product.getDescription());
                        view.showConfirmation((product!=null)? true : false);
                    }
                });
    }

}
