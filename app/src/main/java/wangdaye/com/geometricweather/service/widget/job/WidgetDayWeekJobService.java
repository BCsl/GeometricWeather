package wangdaye.com.geometricweather.service.widget.job;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import wangdaye.com.geometricweather.R;
import wangdaye.com.geometricweather.basic.GeoJobService;
import wangdaye.com.geometricweather.data.entity.model.Location;
import wangdaye.com.geometricweather.data.entity.model.Weather;
import wangdaye.com.geometricweather.receiver.widget.WidgetDayWeekProvider;
import wangdaye.com.geometricweather.service.widget.alarm.WidgetDayWeekAlarmService;
import wangdaye.com.geometricweather.utils.helpter.DatabaseHelper;

/**
 * Widget day week job service.
 * */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WidgetDayWeekJobService extends GeoJobService {
    // data.
    public static final int SCHEDULE_CODE = 33;

    /** <br> life cycle. */

    @Override
    public Location readSettings() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.sp_widget_day_week_setting), Context.MODE_PRIVATE);
        String locationName = sharedPreferences.getString(
                getString(R.string.key_location), getString(R.string.local));
        return DatabaseHelper.getInstance(this).searchLocation(locationName);
    }

    @Override
    protected void doRefresh(Location location) {
        int[] widgetIds = AppWidgetManager.getInstance(this)
                .getAppWidgetIds(new ComponentName(this, WidgetDayWeekProvider.class));
        if (widgetIds != null && widgetIds.length != 0) {
            requestData(location);
        }
    }

    @Override
    protected void updateView(Context context, Weather weather) {
        refreshWidgetView(context, weather);
    }

    /** <br> widget. */

    public static void refreshWidgetView(Context context, Weather weather) {
        WidgetDayWeekAlarmService.refreshWidgetView(context, weather);
    }
}