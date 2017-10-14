package skyray.waol2000;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;

import skyray.waol2000.Core.MainStatusManage;
import skyray.waol2000.Core.MeasureStatus;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.Utils.FullScreenController;

public class ActivityMaintain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(this);
        setContentView(R.layout.activity_maintain);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardOne) {
                    msg = "标定1 进行中，强制退出？";
                } else if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardTwo) {
                    msg = "标定2 进行中，强制退出？";
                }

                if (!msg.equals("")) {
                    AlertDialogManager.showDialog(ActivityMaintain.this, msg, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                FragmentMaintain_HandCalibrate ff = (FragmentMaintain_HandCalibrate) ActivityMaintain.this.fragments.get(MaintainType.HandCalibrate);
                                if (ff != null) {
                                    ff.stopCalibrate();
                                }
                                ActivityMaintain.this.finish();
                            } catch (Exception ex) {
                            }
                        }
                    });
                } else {
                    ActivityMaintain.this.finish();
                }
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
     * 仪表维护子菜单类型
     */
    enum MaintainType {
        HandControl(0), StepOperation(1), HandCalibrate(2), StandardCheck(3), ConnectionOut(4);

        private int _value;

        private MaintainType(int value) {
            _value = value;
        }

        public int value() {
            return _value;
        }

        public static MaintainType valueOf(int value) {
            switch (value) {
                case 0:
                    return HandControl;
                case 1:
                    return StepOperation;
                case 2:
                    return HandCalibrate;
                case 3:
                    return StandardCheck;
                case 4:
                    return ConnectionOut;
                default:
                    return null;
            }
        }
    }

    HashMap<MaintainType, FragmentBase> fragments = new HashMap<>();

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MaintainType maintainType = MaintainType.valueOf(position);
            if (!ActivityMaintain.this.fragments.containsKey(maintainType)) {
                FragmentBase fragment = null;
                switch (maintainType) {
                    case HandControl:
                        fragment = new FragmentMaintain_HandControl();
                        break;
                    case StepOperation:
                        fragment = new FragmentMaintain_StepOperation();
                        break;
                    case HandCalibrate:
                        fragment = new FragmentMaintain_HandCalibrate();
                        break;
                    case StandardCheck:
                        fragment = new FragmentMaintain_StandardCheck();
                        break;
                    case ConnectionOut:
                        fragment = new FragmentMaintain_ConnectionOut();
                        break;
                    default:
                        break;
                }
                ActivityMaintain.this.fragments.put(maintainType, fragment);
            }
            return ActivityMaintain.this.fragments.get(maintainType);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            MaintainType maintainType = MaintainType.valueOf(position);
            switch (maintainType) {
                case HandControl:
                    return getResources().getString(R.string.e_HandControl);
                case StepOperation:
                    return getResources().getString(R.string.e_StepOperation);
                case HandCalibrate:
                    return getResources().getString(R.string.e_HandCalibrate);
                case StandardCheck:
                    return getResources().getString(R.string.e_StandardCheck);
                case ConnectionOut:
                    return getResources().getString(R.string.e_ConnectionOut);
            }
            return null;
        }

    }
}
