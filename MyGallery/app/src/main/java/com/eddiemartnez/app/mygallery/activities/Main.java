package com.eddiemartnez.app.mygallery.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.eddiemartnez.app.mygallery.db.Models.Imagen;
import com.eddiemartnez.app.mygallery.util.ImageAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.eddiemartnez.app.mygallery.R;
import com.eddiemartnez.app.mygallery.fragments.FragmentRandomImage;
import com.eddiemartnez.app.mygallery.fragments.FragmentRandomImageRX;
import com.eddiemartnez.app.mygallery.subclases.ImageViewHolder;
import com.eddiemartnez.app.mygallery.subclases.RunImage;
import com.eddiemartnez.app.mygallery.util.Adapter;
import com.eddiemartnez.app.mygallery.util.Functions;
import com.eddiemartnez.app.mygallery.util.Adapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Main extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private Adapter mSectionsPagerAdapter;
    private static Context QuickContext;
    private static RecyclerView recyclerView;
    private static final CompositeDisposable disposables = new CompositeDisposable();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        QuickContext = this;
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new Adapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter.addFragment(new ImageFragment(), "Gallery 1");
        mSectionsPagerAdapter.addFragment(new FragmentRandomImage(), "Gallery 2");
        mSectionsPagerAdapter.addFragment(new FragmentRandomImageRX(), "Gallery 3");
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class ImageFragment extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
            //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(QuickContext);
             layoutManager.setFlexWrap(FlexWrap.WRAP);
             layoutManager.setAlignItems(AlignItems.BASELINE);
              layoutManager.setJustifyContent(JustifyContent.CENTER);
              recyclerView.setLayoutManager(layoutManager);
            setHasOptionsMenu(true);
            return rootView;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            setupRecyclerView();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

    }


    public static void setupRecyclerView(){

    }

    private static void cargarImagenes() {
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<RunImage>>() {
                    @Override public void onComplete() {

                    }

                    @Override public void onError(Throwable e) {

                    }

                    @Override public void onNext(List<RunImage> images) {
                        ImageAdapter adapter = new ImageAdapter(images, QuickContext);
                        recyclerView.setAdapter(adapter);
                    }
                }));
    }

    static Observable<List<RunImage>> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends List<RunImage>>>() {
            @Override public ObservableSource<? extends List<RunImage>> call() throws Exception {
                // Do some long running operation
                List<RunImage> images= new ArrayList<>();
                try{
                    List<Imagen> imagenes = SQLite.select().from(Imagen.class).queryList();
                    RunImage imageR;
                    for(Imagen image : imagenes){
                        imageR = new RunImage();
                        imageR.url = image.uri;
                        imageR.name = image.nombre;
                        imageR.author = image.autor;
                        images.add(imageR);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return Observable.just(images);
            }
        });
    }

}