package org.techtown.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    ArrayList<Person> items = new ArrayList<Person>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        // 리스트 형태로 보일 때 각각의 아이템은 뷰로 만들어지며 각각의 아이템을 위한 뷰는 뷰홀더에 담아두게 된다.
        // 뷰홀더 역할을 하는 클래스를 PersonAdapter 클래스 안에 넣어두는 것이다.

        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView) {  // 뷰홀더 생성자로 전달되는 뷰 객체 참조하기
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getMobile());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {  // 뷰홀더 객체가 새로 만들어질 때 자동으로 호출된다.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
          // 인플레이션을 진행하기 위해서는 Context 객체가 필요한데 파라미터로 전달되는 뷰그룹 객체의 getContext() 메서드를 이용하면 Context 객체를 참조할 수 있다.
        View itemView = inflater.inflate(R.layout.person_item, viewGroup, false);
          // onCreateViewHolder 메서드의 파라미터로 전달되는 뷰그룹 객체는 각 아이템을 위한 뷰그룹 객체이므로 XML 레이아웃을 인플레이션하여 뷰그룹 객체에 설정한다.
        return new ViewHolder(itemView);  // 뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환하기
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {  // 뷰홀더 객체가 재사용될 때 호출된다.
        /*
        ○ 뷰홀더의 재사용이란 무엇일까?
          아이템이 1000개라고 하더라도 이 아이템을 위해 1000개의 뷰 객체가 만들어지지 않는다. 메모리를 효율적으로 사용하려면 뷰홀더에
          뷰 객체를 넣어두고 사용자가 스크롤하여 보이지 않게 된 뷰 객체를 새로 보일 쪽에 재사용하는 것이 효율적이기 때문이다.
         */
        // 재활용할 수 있는 뷰홀더 객체를 파라미터로 전달하기 때문에 그 뷰홀더에 현재 아이템에 맞는 데이터만 설정한다.
        Person item = items.get(position);
        // 데이터는 Person 객체로 만드는데 여러 아이템을 이 Adapter에서 관리해야 하기 때문에 클래스 안에 ArrayList 자료형으로 된 items라는 변수를 하나 만들어준다.
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {  // Adapter에서 관리하는 아이템의 개수를 반환한다.
        // ArrayList의 size() 메서드를 호출하여 전체 아이템이 몇 개인지 확인한 후 그 값을 반환한다.
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

}
