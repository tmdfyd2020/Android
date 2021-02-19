<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#ff5555ff"
        android:orientation="vertical"> <!--
        첫 번째 레이아웃 : 바탕 레이아웃 -->

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Base Area"
            android:textColor="#ffffffff" />

    </LinearLayout>

    <LinearLayout
        android:id="@+id/page"
        android:layout_width="200dp"
        android:layout_height="match_parent"
        android:layout_gravity="right"
        android:background="#ffffff66"
        android:orientation="vertical"
        android:visibility="gone"> <!--
        두 번째 레이아웃 : 슬라이딩으로 보일 레이아웃
        ○ visibility="gone" : 사용자가 원하는 시점에 보여야 하므로 처음에는 모이지 않도록 한다. -->

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Area #1"
            android:textColor="#ff000000" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="Area #2"
            android:textColor="#ff000000" />

    </LinearLayout>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="right|center_vertical"
        android:background="#00000000"
        android:orientation="vertical"> <!--
        세 번째 레이아웃 : 버튼이 들어 있는 레이아웃
        ○ 세 번째 뷰의 경우에는 배경이 투명한 색깔이기 때문에 첫 번째 뷰 위에 버튼이 하나 있는 형태로 보이게 된다. -->

        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Open" /> <!--
            [Open/Close] 버튼 -->

    </LinearLayout>

</FrameLayout>
