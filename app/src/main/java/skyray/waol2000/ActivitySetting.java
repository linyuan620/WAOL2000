package skyray.waol2000;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;

import skyray.waol2000.Utils.FullScreenController;

public class ActivitySetting extends AppCompatActivity {

    private SettingPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mViewPager.setCurrentItem(item.getItemId() - R.id.setting_valve);
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(ActivitySetting.this);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NavigationView navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.navigation_setting_color);
        navigation.setItemTextColor(csl);
        navigation.getMenu().getItem(0).setChecked(true);

        mSectionsPagerAdapter = new SettingPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySetting.this.finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreenController.hideNavigationBar(this);
        }
    }

    /**
     * 设置子菜单类型
     */
    enum SettingType {
        valve(0), pump(1), injection(2), action(3), flow(4), calibrate(5), measure(6), connection(7), about(8);

        private int _value;

        private SettingType(int value) {
            _value = value;
        }

        public int value() {
            return _value;
        }

        public static SettingType valueOf(int value) {
            switch (value) {
                case 0:
                    return valve;
                case 1:
                    return pump;
                case 2:
                    return injection;
                case 3:
                    return action;
                case 4:
                    return flow;
                case 5:
                    return calibrate;
                case 6:
                    return measure;
                case 7:
                    return connection;
                case 8:
                    return about;
                default:
                    return null;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SettingPagerAdapter extends FragmentPagerAdapter {

        public SettingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        HashMap<SettingType, FragmentBase> fragments = new HashMap<>();

        @Override
        public Fragment getItem(int position) {
            SettingType maintainType = SettingType.valueOf(position);
            if (!fragments.containsKey(maintainType)) {
                FragmentBase fragment = null;
                switch (maintainType) {
                    case valve:
                        fragment = new FragmentSetting_Valve();
                        break;
                    case pump:
                        fragment = new FragmentSetting_Pump();
                        break;
                    case injection:
                        fragment = new FragmentSetting_InjectionInfo();
                        break;
                    case action:
                        fragment = new FragmentSetting_Action();
                        break;
                    case flow:
                        fragment = new FragmentSetting_Flow();
                        break;
                    case calibrate:
                        fragment = new FragmentSetting_Calibrate();
                        break;
                    case measure:
                        fragment = new FragmentSetting_Measure();
                        break;
                    case connection:
                        fragment = new FragmentSetting_Connection();
                        break;
                    case about:
                        fragment = new FragmentSetting_About();
                        break;
                    default:
                        break;
                }
                fragments.put(maintainType, fragment);
            }
            return fragments.get(maintainType);
        }

        @Override
        public int getCount() {
            return SettingType.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            SettingType maintainType = SettingType.valueOf(position);
//            switch (maintainType) {
//                case valve:
//                    return getResources().getString(R.string.e_HandControl);
//                case pump:
//                    return getResources().getString(R.string.e_StepOperation);
//                case measure:
//                    return getResources().getString(R.string.e_HandCalibrate);
//                case connection:
//                    return getResources().getString(R.string.e_StandardCheck);
//                case action:
//                    return getResources().getString(R.string.e_ConnectionOut);
//                case calibrate:
//                    return getResources().getString(R.string.e_HandCalibrate);
//                case flow:
//                    return getResources().getString(R.string.e_StandardCheck);
//                case about:
//                    return getResources().getString(R.string.e_ConnectionOut);
//            }
            return null;
        }

    }

}
