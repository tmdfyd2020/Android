package org.techtown.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<Movie> items = new ArrayList<Movie>();

    static class ViewHolder extends RecyclerView.ViewHolder {  // ViewHolder : ListView에서의 매번 findViewById() 낭비를 해결하기 위해 사용
        TextView textView;  // 영화 이름
        TextView textView2;  // 관객 수

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Movie item) {
            textView.setText(item.movieNm);  // movieNm = 영화 이름
            textView2.setText(item.audiCnt + " 명");  // audiCnt = 관객 수
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {  // 뷰홀더가 만들어지는 시점
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, viewGroup, false);
        // ☆ Inflation : XML 레이아웃에 정의된 내용이 메모리에 로딩된 후, 객체화되는 과정 ☆
        // 이 객체의 inflate() 함수를 호출하면서 XML 레이아웃 파일을 파라미터로 전달하면 인플레이션이 진행되면서 이 소스 파일에 저장된다.
        return new ViewHolder(itemView);  // Movie 객체를 찾아 뷰홀더에 객체를 설정한다.
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Movie item) {
        items.add(item);
    }

    public void setItems(ArrayList<Movie> items) {
        this.items = items;
    }

    public Movie getItem(int position) {
        return items.get(position);
    }

}
