package org.techtown.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable {  // Parcelable 인터페이스를 구현하는 SimpleData 클래스
    int code;
    String message;

    public SimpleData(int code, String message) {  // SimpleData 생성자 1
        this.code = code;
        this.message = message;
    }

    // Parcel 객체에서 읽기
    public SimpleData(Parcel src) {  // SimpleData 생성자 2
        code = src.readInt();
        message = src.readString();
    }

    // CREATOR 상수 정의
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {  // CREATOR 객체는 상수로 정의되어야 한다.
        public SimpleData createFromParcel(Parcel in) {  // SimpleData 객체를 만들 때 Parcel 안에 들어가 있는 것을 가지고 만든다.
            return new SimpleData(in);  // 새로운 객체가 만들어지는 코드가 들어가므로 new SimpleData()와 같이 SimpleData 객체를 만드는 부분을 볼 수 있다.
        }

        public SimpleData[] newArray(int size) {
            return new SimpleData[size];  // SimpleData 생성자를 호출해 Parcel 객체에서 읽기
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { // SimpleData 객체 안에 들어 있는 데이터를 Parcel 객체로 만드는 역할
        dest.writeInt(code);
        dest.writeString(message);
    }
}
