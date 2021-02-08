package org.techtown.fragment2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {

    ImageSelectionCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {  // 프래그먼트가 액티비티 위에 올라오는 시점에 호출된다.
                                                      // 따라서, 프래그먼트에서 해당 액티비티를 참조하고 싶다면
                                                      // onAttach() 메서드로 전달되는 파라미터를 참조하거나
                                                      // getActivity() 메서드를 호출하여 반환되는 객체를 참조할 수 있다.
        super.onAttach(context);

        if (context instanceof ImageSelectionCallback) {  // context가 ImageSelectionCallback 타입의 객체면
            callback = (ImageSelectionCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트와 관련되는 뷰 계층을 만들어서 리턴

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        // fragment_list.xml과 연결

        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onImageSelected(0);
                }
            }
        });

        Button button2 = rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onImageSelected(1);
                }
            }
        });

        Button button3 = rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onImageSelected(2);
                }
            }
        });

        return rootView;
    }
}
