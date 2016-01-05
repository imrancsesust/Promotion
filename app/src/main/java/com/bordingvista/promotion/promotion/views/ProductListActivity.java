package com.bordingvista.promotion.promotion.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bordingvista.promotion.promotion.R;
import com.bordingvista.promotion.promotion.adapters.ProductRecyclerViewAdapter;
import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.presenters.ProductListPresenter;
import com.bordingvista.promotion.promotion.services.PromotionService;

import java.util.ArrayList;
import java.util.List;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;


public class ProductListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter productRVAdapter;
    private PromotionService service;
    private ProductListPresenter presenter;
    private FloatingActionButton fab;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProducts);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        toolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.action_product);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<Product> products = new ArrayList();
        productRVAdapter = new ProductRecyclerViewAdapter(products, this);
        recyclerView.setAdapter(productRVAdapter);
        service = new PromotionService();
        presenter = new ProductListPresenter(this, service);
        presenter.loadProducts();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, ProductCreationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayError(){
        Toast.makeText(getApplicationContext(),R.string.error_server, Toast.LENGTH_LONG).show();
    }

    public void displayProducts(List<Product> products) {
        productRVAdapter.updateData(products);
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

        if (id == R.id.action_promotion) {
            Intent intent = new Intent(ProductListActivity.this, PromotionListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadProducts();
    }
}
