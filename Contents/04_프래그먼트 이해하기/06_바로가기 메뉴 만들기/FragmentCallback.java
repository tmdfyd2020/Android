package org.techtown.drawer;

import android.os.Bundle;

public interface FragmentCallback {

    public void onFragmentSelected(int position, Bundle bundle);
    // 어떤 프래그먼트를 보여줄지 선택하는 함수를 포함하고 있는 인터페이스

}
