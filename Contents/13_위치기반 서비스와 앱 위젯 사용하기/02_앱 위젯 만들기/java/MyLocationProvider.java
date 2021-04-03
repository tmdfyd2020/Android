package org.techtown.location.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

public class MyLocationProvider extends AppWidgetProvider {  // AppWidgetProvider 클래스를 상속하여 새로운 앱 위젯 제공자 클래스 정의

	public static double ycoord = 0.0D;
	public static double xcoord = 0.0D;


	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {  // onUpdate() 메서드 다시 정의
		// 앱 위젯으로 표현되는 텍스트 뷰를 눌렀을 때 실행할 인텐트를 지정하고 GPS 위치 확인을 위해 새로 정의한 서비스를 시작한다.
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Log.d("MyLocationProvider", "onUpdate() called : " + ycoord + ", " + xcoord);

		final int size = appWidgetIds.length;

        for (int i = 0; i < size; i++) {
            int appWidgetId = appWidgetIds[i];

            //String uri = "geo:"+ ycoord + "," + xcoord + "?z=10";
            //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));

			// 텍스트뷰를 눌렀을 때 내 위치를 이용해 지도를 보여줄 수 있는 가장 간단한 방법은 "geo:"로 시작하는 URI 객체를 만들어 인텐트로 지도를 띄워주는 것이다.
			// geo:<latitude>, <longitude>?z=<zoomLevel>
			// "z" 파라미터의 값은 지도가 나타날 때 사용되는 확대/축소 수준을 지정한다.

            // 지도를 띄우기 위한 URI 문자열 생성
            String uriBegin = "geo:" + ycoord + "," + xcoord;
            String query = ycoord + "," + xcoord + "(" + "내위치" + ")";
            String encodedQuery = Uri.encode(query);
            String uriString = uriBegin + "?q=" + encodedQuery + "&z=15";
            Uri uri = Uri.parse(uriString);
            
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);  // 지도를 띄우기 위한 인텐트 객체 생성
            
            // ○ 텍스트뷰를 눌렀을 때 내 위치 좌표를 이용해 지도를 띄워주기 위해 설정하는 인텐트는 미리 설정되어야 하므로 PendingIntent 객체로 만들어 설정한다.
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);  // 지도를 띄우기 위한 펜딩 인텐트 객체 생성

			// ○ 이 객체는 RemoteViews 객체의 setOnClickPendingIntent() 메서드를 이용하여 설정한다.
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mylocation);
            views.setOnClickPendingIntent(R.id.txtInfo, pendingIntent);  // 뷰를 눌렀을 때 실행할 펜딩 인텐트 객체 지정

			// ○ 그런 다음 앱 위젯 매니저 객체의 updateAppWidget() 메서드를 호출하여 위젯을 업데이트하면 텍스트뷰의 클릭 이벤트를 처리하기 위한 인텐트가 설정된다.
            appWidgetManager.updateAppWidget(appWidgetId, views);  // 앱 위젯 매니저 객체의 updateAppWidget() 메서드 호출
        }

        context.startService(new Intent(context,GPSLocationService.class));  // 내 위치 확인을 위해 정의한 서비스를 실행(GPS 위치 확인을 위한 서비스 시작)
	}


	public static class GPSLocationService extends Service {
		public static final String TAG = "GPSLocationService";

		private LocationManager manager = null;

		private LocationListener listener = new LocationListener() {

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

			public void onLocationChanged(Location location) {
				Log.d(TAG, "onLocationChanged() called.");

				updateCoordinates(location.getLatitude(), location.getLongitude());  // Go to down !!
				// 위치 정보가 확인되면 updateCoordinates() 메서드 호출

				stopSelf();
			}
		};

		public IBinder onBind(Intent intent) {
			return null;
		}

		public void onCreate() {
			super.onCreate();

			Log.d(TAG, "onCreate() called.");

			manager = (LocationManager) getSystemService(LOCATION_SERVICE);  // 서비스가 생성될 때 위치 관리자 객체 참조

		}

		public int onStartCommand(Intent intent, int flags, int startId) {  // 서비스가 시작하는 onStartCommand()
			startListening();  // Go to down !!
			// 서비스가 시작할 때 startListening() 메서드 호출

			return super.onStartCommand(intent, flags, startId);
		}

		public void onDestroy() {
			stopListening();

			Log.d(TAG, "onDestroy() called.");

			super.onDestroy();
		}

		private void startListening() {
			Log.d(TAG, "startListening() called.");

			final Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);

			final String bestProvider = manager.getBestProvider(criteria, true);

			try {
				if (bestProvider != null && bestProvider.length() > 0) {
					manager.requestLocationUpdates(bestProvider, 500, 10, listener);  // 위치 관리자에 위치 정보 요청
				} else {
					final List<String> providers = manager.getProviders(true);

					for (final String provider : providers) {
						manager.requestLocationUpdates(provider, 500, 10, listener);
					}
				}
			} catch(SecurityException e) {
				e.printStackTrace();
			}
		}

		private void stopListening() {
			try {
				if (manager != null && listener != null) {
					manager.removeUpdates(listener);
				}

				manager = null;
			} catch (final Exception ex) {

			}
		}

		private void updateCoordinates(double latitude, double longitude) {
			Geocoder coder = new Geocoder(this);
			List<Address> addresses = null;
			String info = "";

			Log.d(TAG, "updateCoordinates() called.");

			try {
				addresses = coder.getFromLocation(latitude, longitude, 2);

				if (null != addresses && addresses.size() > 0) {
					int addressCount = addresses.get(0).getMaxAddressLineIndex();

					if (-1 != addressCount) {
						for (int index = 0; index <= addressCount; ++index) {
							info += addresses.get(0).getAddressLine(index);

							if (index < addressCount)
								info += ", ";
						}
					} else {
						info += addresses.get(0).getFeatureName() + ", "
								+ addresses.get(0).getSubAdminArea() + ", "
								+ addresses.get(0).getAdminArea();
					}
				}

				Log.d(TAG, "Address : " + addresses.get(0).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			coder = null;
			addresses = null;

			if (info.length() <= 0) {  // 위치 좌표와 주소 정보를 포함하는 문자열 생성
				info = "[내 위치] " + latitude + ", " + longitude
						+ "\n터치하면 지도로 볼 수 있습니다.";
			} else {
				info += ("\n" + "[내 위치] " + latitude + ", " + longitude + ")");
				info += "\n터치하면 지도로 볼 수 있습니다.";
			}

			RemoteViews views = new RemoteViews(getPackageName(), R.layout.mylocation);  // RemoteViews 객체 생성한 후 텍스트뷰의 텍스트 설정

			views.setTextViewText(R.id.txtInfo, info);

			ComponentName thisWidget = new ComponentName(this, MyLocationProvider.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, views);  // 위젯 업데이트

			xcoord = longitude;
			ycoord = latitude;
			Log.d(TAG, "coordinates : " + latitude + ", " + longitude);

		}
	}

}
