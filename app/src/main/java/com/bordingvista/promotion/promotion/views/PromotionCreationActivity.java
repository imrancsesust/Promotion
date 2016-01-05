package com.bordingvista.promotion.promotion.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bordingvista.promotion.promotion.R;
import com.bordingvista.promotion.promotion.Util.Constants;
import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.models.Promotion;
import com.bordingvista.promotion.promotion.presenters.PromotionCreationPresenter;
import com.bordingvista.promotion.promotion.services.PromotionService;
import com.google.gson.Gson;

import java.util.List;

import static android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by imran.zahid on 1/5/2016.
 */
public class PromotionCreationActivity extends AppCompatActivity {
    private Spinner productSpinner;
    private PromotionCreationPresenter presenter;
    private PromotionService service;
    private List<Product> products;
    private Product selectedProduct;
    private TextInputLayout inputLayoutDescription;
    private TextInputLayout inputLayoutDiscount;
    private EditText inputDescription;
    private EditText inputDiscount;
    private Button buttonSave;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_creation);
        productSpinner = (Spinner) findViewById(R.id.spinner_product);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_description);
        inputLayoutDiscount = (TextInputLayout) findViewById(R.id.input_layout_discount);
        inputDescription = (EditText) findViewById(R.id.input_description);
        inputDiscount = (EditText) findViewById(R.id.input_discount);
        buttonSave = (Button) findViewById(R.id.button_save);
        toolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.add_promotion);
        service = new PromotionService();
        presenter = new PromotionCreationPresenter(this, service);
        presenter.loadProducts();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePromotion();
            }
        });
    }

    public void showConfirmation(boolean success) {
        if(success) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.success))
                    .setMessage(R.string.promotion_saved_success)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).show();
        } else
        {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.failed))
                    .setMessage(R.string.promotion_saved_failure)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            }).show();
        }
    }

    private void savePromotion() {
        if (!validateDescription()) {
            return;
        }

        if (!validateDiscount()) {
            return;
        }
        Promotion promotion = new Promotion();
        promotion.setDescription(inputDescription.getText().toString());
        promotion.setDiscount(Double.parseDouble(inputDiscount.getText().toString()));
        promotion.setProduct(selectedProduct);
        presenter.addPromotion(promotion);
    }

    private boolean validateDescription() {
        if (inputDescription.getText().toString().trim().isEmpty()) {
            inputLayoutDescription.setError(getString(R.string.err_msg_description));
            requestFocus(inputDescription);
            return false;
        } else {
            inputLayoutDescription.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDiscount() {
        if (inputDiscount.getText().toString().trim().isEmpty()) {
            inputLayoutDiscount.setError(getString(R.string.err_msg_discount));
            requestFocus(inputDiscount);
            return false;
        } else {
            inputLayoutDiscount.setErrorEnabled(false);
        }
        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void initialiseProductSpinner(final List<Product> products) {
        this.products = products;
        ArrayAdapter<Product> spinnerDataAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_spinner_item, products) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setText(products.get(position).getDescription());
                return v;
            }

            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                ((TextView) v).setGravity(Gravity.CENTER);
                ((TextView) v).setText(products.get(position).getDescription());

                return v;
            }
        };

        spinnerDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(spinnerDataAdapter);
        productSpinner.setOnItemSelectedListener(productItemSelectedListener);
    }

    OnItemSelectedListener productItemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int position, long arg3) {
            selectedProduct = products.get(position);
            Gson gson = new Gson();
            Log.d(Constants.LOG_PROMOTION, "Selected Product: " + gson.toJson(selectedProduct));
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadProducts();
    }
}
