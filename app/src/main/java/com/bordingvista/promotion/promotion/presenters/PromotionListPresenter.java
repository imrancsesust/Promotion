package com.bordingvista.promotion.promotion.presenters;

import com.bordingvista.promotion.promotion.models.Promotion;
import com.bordingvista.promotion.promotion.services.PromotionService;
import com.bordingvista.promotion.promotion.views.PromotionListActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by imran.zahid on 1/5/2016.
 */
public class PromotionListPresenter {
    private PromotionListActivity view;
    private PromotionService service;

    public PromotionListPresenter(PromotionListActivity view, PromotionService service) {
        this.view = view;
        this.service = service;
    }

    public void loadPromotions() {
        service.getApi()
                .getPromotions()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Promotion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayError();
                    }

                    @Override
                    public void onNext(List<Promotion> promotions) {
                        view.displayPromotions(promotions);
                    }
                });
    }
}
