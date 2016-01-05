package com.bordingvista.promotion.promotion.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.bordingvista.promotion.promotion.R;
import com.bordingvista.promotion.promotion.Util.Constants;
import com.bordingvista.promotion.promotion.models.Product;
import com.bordingvista.promotion.promotion.presenters.ProductCreationPresenter;
import com.bordingvista.promotion.promotion.services.PromotionService;

/**
 * Created by imran.zahid on 1/4/2016.
 */
public class ProductCreationActivity extends AppCompatActivity {
    private Button buttonSave;
    private EditText inputDescription;
    private ProductCreationPresenter presenter;
    private PromotionService service;
    private TextInputLayout inputLayoutDescription;
    private Toolbar toolBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_creation);
        buttonSave = (Button) findViewById(R.id.button_save);
        inputDescription = (EditText) findViewById(R.id.input_description);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_description);
        toolBar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(R.string.add_product);
        service = new PromotionService();
        presenter = new ProductCreationPresenter(this, service);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePromotion();
            }
        });
    }

    private void savePromotion() {
        if (!validateDescription()) {
            return;
        }
        Product product = new Product();
        product.setDescription(inputDescription.getText().toString());
        Log.d(Constants.LOG_PROMOTION, product.getDescription());
        presenter.addProduct(product);
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showConfirmation(boolean success) {
        if(success) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.success))
                    .setMessage(R.string.product_saved_success)
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
                    .setMessage(R.string.product_saved_failure)
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
}
