package com.fianlandroidassignments.xuancuongstationery.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.activity.CategoryActivity;
import com.fianlandroidassignments.xuancuongstationery.activity.ImportActivity;
import com.fianlandroidassignments.xuancuongstationery.activity.ListProductActivity;
import com.fianlandroidassignments.xuancuongstationery.activity.ProviderActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private CardView cvProduct, cvImport, cvCategory, cvProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        cvProduct = requireActivity().findViewById(R.id.cvProduct);
        cvImport = requireActivity().findViewById(R.id.cvImport);
        cvCategory = requireActivity().findViewById(R.id.cvCategory);
        cvProvider = requireActivity().findViewById(R.id.cvProvider);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_home, container, false);
        CardView cvProduct = (CardView) inflatedView.findViewById(R.id.cvProduct);
        CardView cvImport = (CardView) inflatedView.findViewById(R.id.cvImport);
        CardView cvCategory = (CardView) inflatedView.findViewById(R.id.cvCategory);
        CardView cvProvider = (CardView) inflatedView.findViewById(R.id.cvProvider);

        cvProduct.setOnClickListener(this);
        cvImport.setOnClickListener(this);
        cvCategory.setOnClickListener(this);
        cvProvider.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onClick(View v) {
        Intent it;
        if (v.getId() == R.id.cvProduct) {
            it = new Intent(getContext(), ListProductActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.cvImport) {
            it = new Intent(getContext(), ImportActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.cvCategory) {
            it = new Intent(getContext(), CategoryActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.cvProvider) {
            it = new Intent(getContext(), ProviderActivity.class);
            startActivity(it);
        }
    }
}