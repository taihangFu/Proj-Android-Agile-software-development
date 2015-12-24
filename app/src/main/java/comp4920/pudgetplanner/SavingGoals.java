package comp4920.pudgetplanner;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;


/**
 * Created by michellephan on 30/09/2015.
 */
public class SavingGoals extends AppCompatActivity {
    private Toolbar toolbar;

    private ViewPager pager;
    private SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saving_goals);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation_drawer drawer = (navigation_drawer)
                getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setCustomTabView(R.layout.custom_tab_vertical, R.id.tabTxt);
        tabs.setDistributeEvenly(true);

        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorDivider));
        tabs.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        //THIS CHANGES THE TAB BAR HIGHLIGHT COLOUR
        /**tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
                                       @Override
                                       public int getIndicatorColor(int position) {
                                           return getResources().getColor(R.color.colorDivider);
                                       }
                                   }); **/


                tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class MyPagerAdapter extends FragmentStatePagerAdapter {
        int icons[] = {R.drawable.savings, R.drawable.month_icon, R.drawable.contribution_icon};
        String[] tabs = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            //MyFragment myFragment = MyFragment.getInstance(position);
           // return myFragment;
            if (position == 0) {
                Tab11 tab1 = new Tab11();
                return tab1;
            }
            if (position == 1) {
                Tab22 tab2 = new Tab22();
                return tab2;
            }
            if (position == 2) {
                Tab33 tab3 = new Tab33();
                return tab3;

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,72,72);

            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // super.onBackPressed();
            ParseUser currentUser = ParseUser.getCurrentUser();
            currentUser.logOut();
            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

   /** public static class MyFragment extends Fragment {
        private TextView textView;

        public static MyFragment getInstance(int position) {

            MyFragment myFragment = new MyFragment();
            Bundle a = new Bundle();
            a.putInt("position", position);
            myFragment.setArguments(a);
            return myFragment;

        }

        // @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            textView = (TextView) layout.findViewById(R.id.position);

            Bundle bundle = getArguments();
            if (bundle != null) {
                textView.setText("The page Selected" + bundle.getInt("position"));
            }
            return layout;
        }
    } **/


