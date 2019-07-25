package com.example.e_travel;

        import androidx.lifecycle.Observer;
        import androidx.lifecycle.ViewModelProviders;

        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.e_travel.adapter.CityContentAdapter;
        import com.example.e_travel.model.CityContent;
        import com.example.e_travel.response.CityContentResponse;
        import com.example.e_travel.viewmodel.CityContentViewModel;

        import java.util.ArrayList;

public class CityContentFragment extends Fragment {

    private static final String TAG = CityContentFragment.class.getSimpleName();

    private CityContentViewModel cityContentViewModel;

    private int cityId;
    private String table;

    RecyclerView recyclerView;
    CityContentAdapter cityContentAdapter;


    public static CityContentFragment newInstance(int cityId, int position) {

        Bundle args = new Bundle();
        args.putInt("cityId", cityId);
        String table = "";

        Log.v(TAG , "postiion "+position);

        switch (position) {
            case 0:
                table = "sightseeing";
                break;
            case 1:
                table = "restaurants";
                break;
            case 2:
                table = "hotels";
                break;
        }

        args.putString("table", table);
        CityContentFragment fragment = new CityContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityId = getArguments().getInt("cityId");
        table = getArguments().getString("table");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_city_content, container, false);

        generateCityContentRecyclerView();

        return recyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cityContentViewModel = ViewModelProviders.of(this).get(CityContentViewModel.class);
        cityContentViewModel.cityContentLiveData.observe(this, new Observer<CityContentResponse>() {
            @Override
            public void onChanged(CityContentResponse cityContentResponse) {
                if(cityContentResponse == null) return;
                if(cityContentResponse.getThrowable() !=null){
                    // TODO: GRESKA
                }
                Log.v(TAG, "on change resenje size za "+table+"= "+ cityContentResponse.getCityList().size());
               cityContentAdapter.updateCityContent(cityContentResponse.getCityList());
            }
        });
        cityContentViewModel.getCityContent(cityId, table);
    }

    private void generateCityContentRecyclerView(){
        cityContentAdapter = new CityContentAdapter(getContext(), new ArrayList<CityContent>(), new CityContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CityContent item) {
                PanoramaCommentsActivity.start(getContext(),item.getId(), item.getName(), item.getDescription(), item.getLat(), item.getLon());
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(cityContentAdapter);
    }

}
