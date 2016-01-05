package com.bordingvista.promotion.promotion.services;

import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.models.Promotion;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by imran.zahid on 1/4/2016.
 */
public class PromotionService {
    private static final String PROMOTION_SERVER_URL = "http://192.168.43.195:8084/com.bordingvista.promotion/rest/service/";
    private PromotionApi promotionApi;

    public PromotionService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(PROMOTION_SERVER_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        promotionApi = restAdapter.create(PromotionApi.class);
    }

    public PromotionApi getApi() {
        return promotionApi;
    }

    public interface PromotionApi {

        @GET("/products")
        public Observable<List<Product>>
        getProducts();

        @GET("/promotions")
        public Observable<List<Promotion>>
        getPromotions();

        @POST("/addproduct")
        public  Observable<Product>
        addProduct(@Body Product product);

        @POST("/addpromotion")
        public Observable<Promotion>
        addPromotion(@Body Promotion promotion);
    }
}
