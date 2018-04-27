package com.souschef.sork.sous_chef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

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

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecipeChooserActivity extends AppCompatActivity {

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
    private static ViewPager mViewPager;

    private static List<Recipe> recipeList;

    private static Speaker speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_chooser);

        recipeList = Recipe.getSampleRecipes(getResources());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Must create new speaker here otherwise we risk using a speaker belonging to the previous recipe chooser activity
        speaker = new Speaker(this);
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
        public static final String RECIPE_LITE = "recipeLite";
        public static final String INSTRUCTIONS = "instructions";
        public static final String INGREDIENTS = "ingredients";

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
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe_chooser, container, false);

            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER) - 1;

            if(sectionNumber == 0 && recipeList.size() > 1 ) {
                TextView slideIndicator = (TextView) rootView.findViewById(R.id.slideIndicator);
                slideIndicator.setText("               >");
            } else if(sectionNumber + 1 == recipeList.size()) {
                TextView slideIndicator = (TextView) rootView.findViewById(R.id.slideIndicator);
                slideIndicator.setText("<               ");
            }

            ImageView cover = (ImageView) rootView.findViewById(R.id.cover);
            if(sectionNumber < recipeList.size() && sectionNumber >= 0) {
                final Recipe recipe = recipeList.get(sectionNumber);
                if(recipe != null) {
                    cover.setImageBitmap(recipe.cover);

                    if(sectionNumber != 0) {
                        // Force user to the first recipe
                        cover.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String message = "You should totally try out Spaghetti Bolognese instead!";
                                speaker.readText(message);
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                mViewPager.setCurrentItem(0);
                            }
                        });
                    } else {
                        // Show the instructions for the first recipe
                        cover.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Start cooking activity
                                Intent intent = new Intent(getContext(), CookingActivity.class);
                                Bundle args = new Bundle();
                                ArrayList<String> instructions = (ArrayList<String>) recipe.instructions;
                                args.putSerializable(RECIPE_LITE, recipe.getLite());
                                intent.putExtras(args);
                                startActivity(intent);
                            }
                        });
                    }


                    TextView recipeName = (TextView) rootView.findViewById(R.id.recipe);
                    recipeName.setText(recipe.name);

                    //TextView leftContent = (TextView) rootView.findViewById(R.id.left);
                    //String leftContentText = "Difficulty: " + recipe.rating + "/5, " + recipe.time;
                    //leftContent.setText(leftContentText);

                    RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
                    ratingBar.setNumStars(recipe.rating);

                    TextView rightContent = (TextView) rootView.findViewById(R.id.right);
                    String rightContentText = recipe.time + " | " + recipe.portions + " portions";
                    rightContent.setText(rightContentText);
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
            // Determines how many pages will be shown
            return recipeList.size();
        }
    }
}
