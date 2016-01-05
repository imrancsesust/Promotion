package com.bordingvista.promotion.promotion.presenters;

import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.services.PromotionService;
import com.bordingvista.promotion.promotion.views.ProductListActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by imran.zahid on 1/4/2016.
 */
public class ProductListPresenter {
    ProductListActivity view;
    PromotionService service;

    public ProductListPresenter(ProductListActivity view, PromotionService service) {
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
                        view.displayError();
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        view.displayProducts(products);
                    }
                });
    }
}
