package org.techtown.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>
                            implements OnPersonItemClickListener {
    ArrayList<Person> items = new ArrayList<Person>();
    // 어댑터 클래스가 새로 정의한 리스너 인터페이스 구현하도록 하기

    OnPersonItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);

        return new ViewHolder(itemView, this);  // 리스너인 this를 추가한다.
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Person item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Person item) {
        items.add(item);
    }

    public void setItems(ArrayList<Person> items) {
        this.items = items;
    }

    public Person getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Person item) {
        items.set(position, item);
    }

    /* 외부에서 리스너를 설정할 수 있도록 메서드 추가하기 */
    public void setOnItemClickListener(OnPersonItemClickListener listener) {
        // *** listener라는 이름의 변수를 하나 선언하고 setOnItemClickListener() 메서드를 추가하여 이 메서드가 호출되었을 때
        // 다시 외부에서 설정된 메서드가 호출되도록 만들 수 있다.
        this.listener = listener;
    }

    @Override  // 인터페이스에서 정의한 onItemClick() 메서드를 추가
    // 이 메서드는 뷰홀더 클래스 안에서 뷰가 클릭되었을 때 호출되는 메서드이다.
    public void onItemClick(ViewHolder holder, View view, int position) {
        // 이 어댑터 클래스 안에서가 아니라 밖에서 이벤트 처리를 하는 것이 일반적이므로 ***
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView, final OnPersonItemClickListener listener) {  // 뷰홀더 객체의 생성자가 호출될 때 리스너 객체가 파라미터로 전달
                                                                                      // 이 리스터 객체는 어댑터 밖에서 설정할 것이며 뷰홀더까지 전달된다.
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {  // 아이템 뷰에 OnClickListener 설정하기
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();  // 이 뷰홀더에 표시할 아이템이 어댑터에서 몇 번째인지 정보를 반환한다.
                                                          // 즉, 아이템의 인덱스 정보를 반환한다.

                    /* 아이템 뷰 클릭 시 미리 정의한 다른 리스터의 메서드 호출하기 */
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getMobile());
        }

    }

}
