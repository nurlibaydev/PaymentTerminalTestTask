package kaa.nurlibaydev.paymentterminaltesttask.presentation.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import kaa.nurlibaydev.paymentterminaltesttask.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}