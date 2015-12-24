package comp4920.pudgetplanner;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class navigation_drawer extends Fragment {

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME="testpref";
    private ActionBarDrawerToggle mdrawerToggle;
    private DrawerLayout mdrawerLayout;
    public static final String KEY_USER_INPUT="user_input";
    private AAdapter adapter;


    //the user being aware of the existence of the drawer
    private boolean userInput;

    //coming back from rotation or start from first time
    private boolean fromSavedState;

    private View containView;
    private Context context;



    public navigation_drawer() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //user never opened the drawer

        userInput = Boolean.valueOf(readFromPref(getActivity(), KEY_USER_INPUT, "false"));
        if (savedInstanceState!=null) {
            fromSavedState = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new AAdapter(getActivity(), getData());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i;
                if (position == 0 ) {
                    i = new Intent(getActivity(), LoginSuccessful.class);
                    startActivity(i);
                }
                if (position == 1) {
                    i = new Intent(getActivity(), Account.class);
                    startActivity(i);
                }
                if (position == 2) {
                    i = new Intent(getActivity(), SavingGoals.class);
                    startActivity(i);
                }
                if (position == 3) {
                    i = new Intent(getActivity(), ChangePassword.class);
                    startActivity(i);
                }

                if (position == 4){
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    currentUser.logOut();
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("username","");
                    installation.saveInBackground();
                    i = new Intent(getActivity(), Login.class);
                    startActivity(i);
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return layout;
    }

    public static List<IconHolder>  getData() {
        List<IconHolder> data = new ArrayList();

        int[] icons = {R.drawable.home_icon, R.drawable.acc_icon2, R.drawable.cal_icon2, R.drawable.pw1, R.drawable.logout_icon};
        String[] titles = {"Home", "Account", "Saving Goals Calculator", "Change Password", "Log Out"};

        for (int i=0; i < titles.length && i < icons.length;i++) {
            IconHolder current = new IconHolder();
            current.eIcon = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragID, DrawerLayout drawerLayout, Toolbar toolbar) {
        containView = getActivity().findViewById(fragID);
        mdrawerLayout = drawerLayout;
        mdrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            //user just viewed
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                //saving the drawer was opened
                /**   if (!userInput) {
                 userInput = true;
                 savePref(getActivity(), KEY_USER_INPUT, userInput+" ");
                 } **/
                //redraw the menu
                // getActivity().invalidateOptionsMenu();

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // getActivity().invalidateOptionsMenu();
            }
        };
        //if user hasn't knew about the drawer

        //if (userInput && !fromSavedState) {
        //  mdrawerLayout.openDrawer(containView);
        mdrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawerToggle.syncState();
            }
        });
        //}

        mdrawerLayout.setDrawerListener(mdrawerToggle);

    }

    public static void savePref(Context context, String prefName, String prefValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }


    public static String readFromPref(Context context, String prefName, String initialValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefName, initialValue);

    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }


    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child!=null && clickListener!=null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(), e.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e))
            {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

