package com.bordingvista.promotion.promotion.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bordingvista.promotion.promotion.R;
import com.bordingvista.promotion.promotion.adapters.PromotionRecyclerViewAdapter;
import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.models.Promotion;
import com.bordingvista.promotion.promotion.presenters.PromotionListPresenter;
import com.bordingvista.promotion.promotion.services.PromotionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imran.zahid on 1/5/2016.
 */
public class PromotionListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PromotionRecyclerViewAdapter promotionRecyclerViewAdapter;
    private PromotionService service;
    private PromotionListPresenter presenter;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPromotions);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        toolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.action_promotion);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<Promotion> list = new ArrayList<Promotion>();
        promotionRecyclerViewAdapter = new PromotionRecyclerViewAdapter(list, this);
        recyclerView.setAdapter(promotionRecyclerViewAdapter);
        service = new PromotionService();
        presenter = new PromotionListPresenter(this, service);
        presenter.loadPromotions();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PromotionListActivity.this, PromotionCreationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayError(){
        Toast.makeText(getApplicationContext(), R.string.error_server, Toast.LENGTH_LONG).show();
    }

    public void displayPromotions(List<Promotion> promotions) {
        promotionRecyclerViewAdapter.updateData(promotions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadPromotions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_product) {
            Intent intent = new Intent(PromotionListActivity.this, ProductListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
