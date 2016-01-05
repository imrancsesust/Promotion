package com.bordingvista.promotion.promotion.presenters;

import android.util.Log;

import com.bordingvista.promotion.promotion.Util.Constants;
import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.models.Promotion;
import com.bordingvista.promotion.promotion.services.PromotionService;
import com.bordingvista.promotion.promotion.views.PromotionCreationActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by imran.zahid on 1/5/2016.
 */
public class PromotionCreationPresenter {
    private PromotionCreationActivity view;
    private PromotionService service;

    public PromotionCreationPresenter(PromotionCreationActivity view, PromotionService service) {
        this.view = view;
        this.service = service;
    }

    public void loadProducts(){
        service.getApi()
                .getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Product> products) {
                        view.initialiseProductSpinner(products);
                    }
                });
    }

        public void addPromotion(Promotion promotion) {
            service.getApi()
                    .addPromotion(promotion)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Promotion>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showConfirmation(false);
                        }

                        @Override
                        public void onNext(Promotion promotion) {
                            Log.d(Constants.LOG_PROMOTION, "Id:" + promotion.getId() + " Description:" + promotion.getDescription());
                            view.showConfirmation((promotion!=null)? true:false);
                        }
                    });
        }


}
