package org.techtown.fragment2;

/*
화면에서 선택된 버튼에 따라 다른 프래그먼트의 이미지를 바꿔주려면 액티비티 쪽으로 데이터를 전달해야 하므로
액티비티에 onImageSelected() 메서드를 정의한 후 그 메서드를 호출하도록 한다.
그런데 매번 액티비티마다 다른 이름의 메서드를 만들면 프래그먼트가 올라간 액티비티가 다른 액티비티로 변경되었을 때
해당 액티비티가 무엇인지 매번 확인해야 하는 번거로움이 있다.
이 때문에 인터페이스를 하나 정의한 후 액티비티가 이 인터페이스를 구현하도록 하는 것이 좋다.
여기에서는 ImageSelectionCallback 인터페이스를 정의했으며 onImageSelected() 메서드는 이 인터페이스 안에 정의했다.
 */

public interface ImageSelectionCallback {

    public void onImageSelected(int position); // 몇 번째 이미지가 선택되었는지

}
