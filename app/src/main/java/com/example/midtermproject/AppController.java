package com.example.midtermproject;
import android.app.Application;
public class AppController extends Application {
    private static AppController mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        UserManager.getInstance(this);
        CartManager.getInstance(this);
        WishlistManager.getInstance(this);
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
