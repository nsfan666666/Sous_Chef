package com.souschef.sork.sous_chef;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeChooser extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_chooser);

        // TODO Add some test recipes
        recipeList = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spaghetti);
        Recipe recipe1 = new Recipe("Köttfärsås och spaghetti", 2, "4-6", "30 min", bitmap);
        recipeList.add(recipe1);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.biff);
        Recipe recipe2 = new Recipe("Köttfärsås och spaghetti", 2, "4-6", "30 min", bitmap2);
        recipeList.add(recipe2);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.soppa);
        Recipe recipe3 = new Recipe("Köttfärsås och spaghetti", 2, "4-6", "30 min", bitmap3);
        recipeList.add(recipe3);

        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.ryggbiff);
        Recipe recipe4 = new Recipe("Köttfärsås och spaghetti", 2, "4-6", "30 min", bitmap4);
        recipeList.add(recipe4);

        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.hamburgare);
        Recipe recipe5 = new Recipe("Köttfärsås och spaghetti", 2, "4-6", "30 min", bitmap5);
        recipeList.add(recipe5);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_chooser, menu);
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
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe_chooser, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

            ImageView cover = (ImageView) rootView.findViewById(R.id.cover);
            if(sectionNumber < recipeList.size() && sectionNumber >= 0) {
                Recipe recipe = recipeList.get(sectionNumber);
                if(recipe != null) {
                    cover.setImageBitmap(recipe.cover);
                }
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }
    }
}
