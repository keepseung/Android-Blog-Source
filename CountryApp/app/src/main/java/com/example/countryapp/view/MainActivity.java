package com.example.countryapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.countryapp.R;
import com.example.countryapp.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.countriesList)
    RecyclerView countriesList;

    @BindView(R.id.list_error)
    TextView listError;

    @BindView(R.id.loading_view)
    ProgressBar loadingView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    // 뷰모델은 뷰가 어디 뷰에 사용될 지 모른다.
    // 하지만 뷰는 어떤 뷰모델을 사용할지 알아야 한다.
    private ListViewModel viewModel;
    private CoutryListAdapter adapter = new CoutryListAdapter(new ArrayList<>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // viewModel 초기화는 ViewModelProviders를 통해서 한다.
        // 액티비티 destroy 되고 다시 create 되더라도 뷰모델에 있는 데이터를 보여주기 위함이다.
        // 액티비티가 다시 생성되더라도 이전에 생성한 뷰모델 인스턴스을 줄 수 있다.
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        // 국가 데이터를 가져온다.
        viewModel.refresh();

        // 리사이클러뷰에 어뎁터를 설정한다.
        countriesList.setLayoutManager(new LinearLayoutManager(this));
        countriesList.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> {
            // 리프레이 될 때마다 새로운 데이터를 가져온다.
            viewModel.refresh();
            refreshLayout.setRefreshing(false);
        });

        observerViewModel();
    }

    private void observerViewModel() {
        /**
         *  뷰(메인 화면)에 라이브 데이터를 붙인다.
         *  메인 화면에서 관찰할 데이터를 설정한다.
         *  라이브 데이터가 변경됐을 때 변경된 데이터를 가지고 UI를 변경한다.
         */
        //
        viewModel.countries.observe(this, countryModels -> {

            // 데이터 값이 변할 때마다 호출된다.
            if (countryModels != null){
                countriesList.setVisibility(View.VISIBLE);
                // 어뎁터가 리스트를 수정한다.
                adapter.updateCountires(countryModels);
            }
        });

        viewModel.countryLoadError.observe(this, isError -> {
            // 에러 메세지를 보여준다.
            if (isError != null){
                listError.setVisibility(isError? View.VISIBLE: View.GONE);
            }
        });

        viewModel.loading.observe(this, isLoading -> {
            if (isLoading!= null){
                // 로딩 중이라는 것을 보여준다.
                loadingView.setVisibility(isLoading?View.VISIBLE:View.GONE);
                // 로딩중일 때 에러 메세지, 국가 리스트는 안 보여준다.
                if (isLoading){
                    listError.setVisibility(View.GONE);
                    countriesList.setVisibility(View.GONE);
                }

            }
        });
    }
}