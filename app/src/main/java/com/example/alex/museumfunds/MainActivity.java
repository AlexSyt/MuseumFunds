package com.example.alex.museumfunds;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        registerForContextMenu(fab);
        fab.setOnClickListener((View view) -> openContextMenu(fab));
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExhibitsFragment(), "Exhibits");
        adapter.addFragment(new ExhibitionsFragment(), "Exhibitions");
        viewPager.setAdapter(adapter);
    }

    private static final int CM_ADD_EXHIBIT_ID = 1;
    private static final int CM_ADD_EXHIBITION_ID = 2;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Add:");
        menu.add(0, CM_ADD_EXHIBIT_ID, 0, "Exhibit");
        menu.add(0, CM_ADD_EXHIBITION_ID, 0, "Exhibition");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CM_ADD_EXHIBIT_ID:
                startActivity(new Intent(this, AddExhibitActivity.class));
                return true;

            case CM_ADD_EXHIBITION_ID:
                startActivity(new Intent(this, AddExhibitionActivity.class));
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}

class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }
}
