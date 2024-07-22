package com.example.animalswiki;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.animalswiki.entities.Cat;
import com.example.animalswiki.entities.CatImage;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textViewDescription;
    private TextView textViewCountry;
    private TextView textViewWeight;
    private TextView textViewLifespan;
    private TextView textViewTemperament;
    private RatingBar ratingAffection;
    private RatingBar ratingBarChild;
    private RatingBar ratingAffectionDog;
    private RatingBar ratingBarEnergy;
    private ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;

    private Spinner spinner;
    private List<Cat> breeds = new ArrayList<>();
    private final ArrayList<String> breedsName = new ArrayList<>();
    private MainViewModel viewModel;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getCatInfo();
        viewModel.getBreeds().observe(this, new Observer<List<Cat>>() {
            @Override
            public void onChanged(List<Cat> cats) {
                breeds = cats;
                for (Cat name : breeds) {
                    breedsName.add(name.getName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            MainActivity.this,
                            android.R.layout.simple_spinner_item,
                            breedsName
                    );
                    adapter.setDropDownViewResource(
                            android.R.layout.select_dialog_singlechoice)
                    ;
                    spinner.setAdapter(adapter);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(breeds.get(position).getName());
                textViewDescription.setText(breeds.get(position).getDescription());
                textViewTemperament.setText(String.format("\"%s\"", breeds.get(position).getTemperament()));
                textViewCountry.setText(String.format("Country: %s", breeds.get(position).getOrigin()));
                textViewWeight.setText(String.format(" %s kg.", breeds.get(position).getWeight().getMetric()));
                textViewLifespan.setText(String.format("Lifespan: %s years", breeds.get(position).getLifeSpan()));
                ratingAffection.setRating(breeds.get(position).getAffection());
                ratingAffectionDog.setRating(breeds.get(position).getDog());
                ratingBarEnergy.setRating(breeds.get(position).getEnergy());
                ratingBarChild.setRating(breeds.get(position).getChild());
                viewModel.getCatImage(breeds.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewModel.getCatImageList().observe(this, new Observer<List<CatImage>>() {
            @Override
            public void onChanged(List<CatImage> imageList) {
                mViewPagerAdapter = new ViewPagerAdapter(MainActivity.this, imageList);
                mViewPager.setAdapter(mViewPagerAdapter);
                tabLayout.setupWithViewPager(mViewPager, true);
            }
        });
    }

    private void initViews() {
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewWeight = findViewById(R.id.textViewWeight);
        textViewLifespan = findViewById(R.id.textViewLifespan);
        textViewTemperament = findViewById(R.id.textViewTemperament);
        ratingAffection = findViewById(R.id.ratingAffection);
        ratingBarChild = findViewById(R.id.ratingBarChild);
        ratingAffectionDog = findViewById(R.id.ratingAffectionDog);
        ratingBarEnergy = findViewById(R.id.ratingBarEnergy);
        mViewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
    }
}
