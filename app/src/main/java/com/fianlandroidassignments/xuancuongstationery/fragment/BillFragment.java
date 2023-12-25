package com.fianlandroidassignments.xuancuongstationery.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fianlandroidassignments.xuancuongstationery.R;
import com.fianlandroidassignments.xuancuongstationery.activity.MainActivity;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayCategoryAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.ArrayProviderAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.RecyclerViewImportBillAdapter;
import com.fianlandroidassignments.xuancuongstationery.adapter.RecyclerViewSoldBillAdapter;
import com.fianlandroidassignments.xuancuongstationery.database.DatabaseHelper;
import com.fianlandroidassignments.xuancuongstationery.dto.ImportBillDTO;
import com.fianlandroidassignments.xuancuongstationery.dto.SoldBillDTO;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String[] arrBillTypes = {"Import Bill", "Sold Bill"};
    AutoCompleteTextView autoCompleteTextViewBillTypes;
    ArrayAdapter<String> adapterBillTypes;
    List<ImportBillDTO> importBillDTOS;
    List<SoldBillDTO> soldBillDTOS;

    RelativeLayout relativeLayoutImportBill;
    RelativeLayout relativeLayoutSoldBill;
    DatabaseHelper databaseHelper;
    RecyclerViewImportBillAdapter recyclerViewImportBillAdapter;
    RecyclerViewSoldBillAdapter recyclerViewSoldBillAdapter;

    RecyclerView recyclerViewImportBill;
    RecyclerView recyclerViewSoldBill;


    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance(String param1, String param2) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        autoCompleteTextViewBillTypes = requireActivity().findViewById(R.id.autoCompleteBillTypes);
        relativeLayoutImportBill = requireActivity().findViewById(R.id.formImportBill);
        relativeLayoutSoldBill = requireActivity().findViewById(R.id.formSoldBill);
        databaseHelper = new DatabaseHelper(requireContext());
        importBillDTOS = databaseHelper.selectAllImportBill();
        soldBillDTOS = databaseHelper.selectAllSoldBill();
        recyclerViewImportBillAdapter = new RecyclerViewImportBillAdapter(requireContext(), importBillDTOS);
        recyclerViewSoldBillAdapter = new RecyclerViewSoldBillAdapter(requireContext(), soldBillDTOS);
        recyclerViewImportBill = requireActivity().findViewById(R.id.recyclerViewImportBillList);
        recyclerViewSoldBill = requireActivity().findViewById(R.id.recyclerViewSoldBillList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_bill, container, false);
        adapterBillTypes = new ArrayAdapter<>(requireContext(), R.layout.list_item_product_exists, arrBillTypes);
        autoCompleteTextViewBillTypes = inflatedView.findViewById(R.id.autoCompleteBillTypes);

        relativeLayoutImportBill = inflatedView.findViewById(R.id.formImportBill);
        relativeLayoutSoldBill = inflatedView.findViewById(R.id.formSoldBill);

        recyclerViewSoldBill = inflatedView.findViewById(R.id.recyclerViewSoldBillList);
        recyclerViewSoldBill.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewSoldBill.setAdapter(recyclerViewSoldBillAdapter);

        recyclerViewImportBill = inflatedView.findViewById(R.id.recyclerViewImportBillList);
        recyclerViewImportBill.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewImportBill.setAdapter(recyclerViewImportBillAdapter);
        autoCompleteTextViewBillTypes.setAdapter(adapterBillTypes);
        autoCompleteTextViewBillTypes.setDropDownBackgroundResource(R.color.white);


        autoCompleteTextViewBillTypes.setOnItemClickListener((parent, view, position, id) -> {
            if ("Import Bill".equals(adapterBillTypes.getItem(position))) {
                relativeLayoutImportBill.setVisibility(View.VISIBLE);
                relativeLayoutSoldBill.setVisibility(View.INVISIBLE);
            } else if ("Sold Bill".equals(adapterBillTypes.getItem(position))) {
                relativeLayoutSoldBill.setVisibility(View.VISIBLE);
                relativeLayoutImportBill.setVisibility(View.INVISIBLE);
            }
        });
        return inflatedView;
    }
}