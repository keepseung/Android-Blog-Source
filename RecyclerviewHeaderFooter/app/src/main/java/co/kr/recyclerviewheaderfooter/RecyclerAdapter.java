package co.kr.recyclerviewheaderfooter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 헤더, 아이템, 푸터를 구분하기 위한 상수
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    Context context;
    ArrayList<Person> listData;

    public RecyclerAdapter(Context context, ArrayList<Person> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder holder;
        View view;
        // 헤더, 푸터, 아이템이 맞는 레이아웃을 뷰 홀더로 여김
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            holder = new HeaderViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            holder = new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new ItemViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 뷰 홀더가 해당 클래스의 객체인지 확인
        // 속한 객차라면 맞는 데이터 보여줌
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        } else {
            // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.textView.setText(listData.get(position - 1).getName());
        }
    }

    @Override
    public int getItemCount() {
        return listData.size()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == listData.size() + 1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View headerView) {
            super(headerView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View footerView) {
            super(footerView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ItemViewHolder(View itemViewHolder) {
            super(itemViewHolder);
            textView = itemViewHolder.findViewById(R.id.textView);
        }
    }


}
