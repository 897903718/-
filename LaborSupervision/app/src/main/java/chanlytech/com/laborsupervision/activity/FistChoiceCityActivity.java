package chanlytech.com.laborsupervision.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.module.ChoiceCityModule;

/**
 * 首次进入选择地区
 * */
public class FistChoiceCityActivity extends BaseActivity<ChoiceCityModule> {



    @Override
    public int setContentView() {
        return R.layout.activity_fist_choice_city;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public ChoiceCityModule initModule() {
        return new ChoiceCityModule(this);
    }
}
