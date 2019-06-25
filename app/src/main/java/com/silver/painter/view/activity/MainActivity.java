package com.silver.painter.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.silver.painter.R;
import com.silver.painter.databinding.ActivityMainBinding;
import com.silver.painter.viewmodel.PaintViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PaintViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(PaintViewModel.class);
        viewModel.setPencilMode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pencil:
                viewModel.setPencilMode();
                return true;
            case R.id.action_eraser:
                viewModel.setEraserMode();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
