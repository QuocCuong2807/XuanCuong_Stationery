package com.fianlandroidassignments.xuancuongstationery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.adapter.CategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.dto.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ListView listViewCategory;
    List<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        references();
        displayListView();

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
        listViewCategory = findViewById(R.id.listViewCate);
    }
}