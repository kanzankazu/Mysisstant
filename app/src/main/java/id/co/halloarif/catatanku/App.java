package id.co.halloarif.catatanku;

import android.content.Context;
import android.support.multidex.MultiDex;

import app.beelabs.com.codebase.base.BaseApp;
import app.beelabs.com.codebase.di.component.AppComponent;
import app.beelabs.com.codebase.di.component.DaggerAppComponent;
import id.co.halloarif.catatanku.support.util.DeviceDetailUtil;
import id.co.halloarif.catatanku.support.util.FontUtil;

public class App extends BaseApp {

    private static Context context;
    private App instance;

    public static Context getContext() {
        return context;
    }

    public static AppComponent getAppComponent() {
        if (context == null) return null;
        return getComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (DeviceDetailUtil.isKitkatBelow()) {
            MultiDex.install(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        setupBuilder(DaggerAppComponent.builder(), this);
        FontUtil.overrideFont(getApplicationContext(), "DEFAULT", "fonts/Nunito-Regular.ttf");
        FontUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Nunito-SemiBold.ttf");
        FontUtil.overrideFont(getApplicationContext(), "MONOSPACE", "fonts/Nunito-Light.ttf");

        //MobileAds.initialize(this, getString(R.string.ad_unit_app_id));

        /*Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("awewe.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(realmConfig);*/
    }

}
