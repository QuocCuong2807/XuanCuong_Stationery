package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.CategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.Category;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ListView listViewCategory;
    List<Category> categories;
    MaterialToolbar toolbar;
    FloatingActionButton addNewCateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        references();
        displayListView();
        manipulateToolbar();

        //set onclick event to add new category button
        addNewCateBtn.setOnClickListener(view -> {
            Toast.makeText(CategoryActivity.this, "Chức năng thêm mới chưa được phát triển", Toast.LENGTH_LONG).show();
        });
    }

    private void manipulateToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void displayListView() {
        categories = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categories);

        categories.add(new Category(1, "Book", R.drawable.book, 50));
        categories.add(new Category(2, "Pen", R.drawable.pen, 29));
        categories.add(new Category(3, "Ruler", R.drawable.ruler, 11));
        categories.add(new Category(4, "Eraser", R.drawable.eraser, 6));
        categories.add(new Category(5, "Office Tools", R.drawable.office_tool, 46));


        listViewCategory.setAdapter(categoryAdapter);
    }

    private void references() {
        toolbar = findViewById(R.id.customTopCateBar);
        listViewCategory = findViewById(R.id.listViewCate);
        addNewCateBtn = findViewById(R.id.addNewCateButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.sellStack) {
            Toast.makeText(CategoryActivity.this, "Chuc nang chua hoan thanh", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

}