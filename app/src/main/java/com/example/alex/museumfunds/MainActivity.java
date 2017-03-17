package com.example.alex.museumfunds;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        registerForContextMenu(fab);
        fab.setOnClickListener((View view) -> openContextMenu(fab));
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
