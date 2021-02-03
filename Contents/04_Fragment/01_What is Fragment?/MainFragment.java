package org.techtown.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment_main.xml을 메모리에 올리면서 fragment에 연결해준다.
        // LayoutInflater 객체가 전달되므로 이 객체의 inflate() 메서드를 바로 호출할 수 있다.

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        // para 1 : XML 레이아웃 파일 : R.layout.fragement_main
        // para 2 : XML 레이아웃이 설정될 뷰 그룹 객체 : 프래그먼트의 가장 상위 레이아웃
        // inflate() 메서드를 호출하면 인플레이션이 진행되고 그 결과로 ViewGroup 객체가 반환된다.

        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(1); // 메인 액티비티에 새로 추가할 메서드로 프래그먼트 매니저를 이용해 프래그먼트를 전환하는 메서드(직접 구현하자!)
                // 프래그먼트가 액티비티를 본떠 만들었고 액티비티 관리를 시스템에서 하는 것처럼 프래그먼트 관리를 액티비티가 하기 때문에 액티비티에서 프래그먼트를
                // 전환하도록 만들어야 하기 때문이다.
                // 즉, 프래그먼트를 직접 띄우는 것이 아니라 액티비티를 통해 띄워야 한다.
            }
        });
        return rootView;
    }
}
