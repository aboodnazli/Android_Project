package com.example.midtermproject;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
public class OrderHistoryFragment extends Fragment {
    private ListView orderHistoryListView;
    private LinearLayout emptyOrdersLayout;
    public OrderHistoryFragment() {
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_history_fragment, container, false);
        orderHistoryListView = view.findViewById(R.id.orderHistoryListView);
        emptyOrdersLayout = view.findViewById(R.id.emptyOrdersLayout);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> orders = new ArrayList<>();
        if (orders.isEmpty()) {
            orderHistoryListView.setVisibility(View.GONE);
            emptyOrdersLayout.setVisibility(View.VISIBLE);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, orders);
            orderHistoryListView.setAdapter(adapter);
            orderHistoryListView.setVisibility(View.VISIBLE);
            emptyOrdersLayout.setVisibility(View.GONE);
        }
    }
}
