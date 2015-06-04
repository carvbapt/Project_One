package com.android.exemplo.projectone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;

import com.android.exemplo.projectone.agenda.agendaFragment;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AgendaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        agenda();
    }

    public void agenda (){
        ViewPager pager = (ViewPager) findViewById(R.id.viewPagerAgenda);
        pager.setAdapter(new MyPagerAdapterSubZones(getSupportFragmentManager()));
    }

    /**
     * Save current states of the Caldroid here
     */
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        // TODO Auto-generated method stub
//        super.onSaveInstanceState(outState);
//
//        if (caldroidFragment != null) {
//            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
//        }
//
//        if (dialogCaldroidFragment != null) {
//            dialogCaldroidFragment.saveStatesToKey(outState,
//                    "DIALOG_CALDROID_SAVED_STATE");
//        }
//    }



    private class MyPagerAdapterSubZones extends FragmentPagerAdapter {

        public MyPagerAdapterSubZones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return agendaFragment.newInstance();
//                case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
//                case 2: return ThirdFragment.newInstance("ThirdFragment, Instance 1");
//                case 3: return ThirdFragment.newInstance("ThirdFragment, Instance 2");
//                case 4: return ThirdFragment.newInstance("ThirdFragment, Instance 3");
                default:
//                    if local == nr faz para essa zona
//                    if ((local==4)||(local==5)) // If Arm left or Arm righ
//                        return AlarmSubZonFragment_Braco.newInstance(local, text_localBody);
//                    else {
//                        if ((local == 0) || (local == 1)) // If left abdomen or right abdomen
//                            return AlarmSubZonFragment_Abdomen.newInstance(local, text_localBody);
//                        else{
//                            if ((local == 2) || (local == 3)) // If left coxa or right coxa
//                                return AlarmSubZonFragment_Coxa.newInstance(local, text_localBody);
//                            else{
//                                if ((local == 6) || (local == 7)) // If left nadega or right nadega
//                                    return AlarmSubZonFragment_Nadega.newInstance(local, text_localBody);
//                                else
                    return null;
//                            }
//                        }
//
//                    }
            }
        }

        @Override
        public int getCount() {
//            if (local!=(-1))
//                return 2;
//            else
            return 1;
        }
    }

}
