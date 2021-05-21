package com.singalovski_kafiteria_2017.kafiteria;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Stack;

public class ChooseFoodActivity extends AppCompatActivity
{


    final static int NUM_OF_TOSAFOT=6;
    int[] ids_image_button_bread = new int[6];
    int[] ids_image_button_main = new int[6];// mana ikarit
    int[] ids_image_button_tosafot = new int[NUM_OF_TOSAFOT];
    Stack<String> st_tavlinim;
    Stack<String> st_drinks;
    ImageButton last_btn_hit_bread=null;
    ImageButton last_btn_hit_main=null;
    ImageButton last_btn_hit_tosafot=null;
    int kamut_bread_allowed=0;
    int kamut_main_allowed=0;
    int kamut_tosafot_allowed=0;
    boolean exhibited=false;
    String hazmana="";
    String hazmana_tavlinim="";
    String hazmana_drinks="";
    String bread="";
    String main="";
    String tosafot="";
    int filter =Color.argb(150, 255, 0, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        //set up notitle
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_choose_food);
        get_all_bread_ids();
        get_all_main_ids();
        get_all_tosafot_ids();
        st_tavlinim = new Stack<String>();
        st_drinks = new Stack<String>();
        kamut_bread_allowed=1;
        kamut_main_allowed=1;
        kamut_tosafot_allowed=NUM_OF_TOSAFOT;






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_food, menu);
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


    public  void get_all_bread_ids()
    {
        LinearLayout LL = (LinearLayout)findViewById(R.id.bread);
        final int childcount = LL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            View v = LL.getChildAt(i);
            // Do something with v.
            if (v instanceof ImageButton)
                ids_image_button_bread[i]=v.getId();
        }
    }

    public  void get_all_main_ids()
    {
        LinearLayout LL = (LinearLayout)findViewById(R.id.main_meal);
        final int childcount = LL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            View v = LL.getChildAt(i);
            // Do something with v.
            if (v instanceof ImageButton)
                ids_image_button_main[i]=v.getId();
        }
    }

    public  void get_all_tosafot_ids()
    {
        LinearLayout LL = (LinearLayout)findViewById(R.id.tosafot);
        final int childcount = LL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            View v = LL.getChildAt(i);
            // Do something with v.
            if (v instanceof ImageButton)
                ids_image_button_tosafot[i]=v.getId();
        }
    }

    public void zor_hazmana()
    {
        hazmana="";

        if (bread !="")
            hazmana=  bread ;

        if (main !="")
        {
            if (hazmana != "")
                hazmana = hazmana+ " , " +main;
            else
                hazmana=main;
        }

        if (tosafot !="")
        {
            if (hazmana != "")
                hazmana = hazmana+ " , " +tosafot;
            else
                hazmana=tosafot;
        }



        TextView textView = (TextView) findViewById(R.id.my_hazmana);

        if (hazmana!=null)
            textView.setText( "ההזמנה שלך :"+ " :" + hazmana);
        else
            textView.setText("ההזמנה שלך "+" :");


    }


    public void zor_hazmana_tavlinim_drinks()
    {
        if (!st_tavlinim.isEmpty())
            hazmana_tavlinim=st_tavlinim.toString();
        else
            hazmana_tavlinim="";



        if (!st_drinks.isEmpty())
            hazmana_drinks=st_drinks.toString();
        else
            hazmana_drinks="";

        String tavlinim_drinks=hazmana_tavlinim+hazmana_drinks;

        TextView textView = (TextView) findViewById(R.id.my_tavlinim_drink);

        if (tavlinim_drinks!=null)
            textView.setText("תבלינים ושתייה :" + tavlinim_drinks);
        else
            textView.setText("תבלינים ושתייה :");

    }

    public void hitBread (View v)
    {
        boolean jelly_bean_and_over=false;
        ImageButton btn_hit;
        ImageButton btn;
        ColorFilter cf=null;



        int idb=v.getId();
        bread=(String)v.getTag();
        btn_hit=(ImageButton)findViewById(idb);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN )
        {
            cf= btn_hit.getColorFilter();
            jelly_bean_and_over=true;
        }
        else
        {
            jelly_bean_and_over=false;
            if (!exhibited ) {
                Toast.makeText(this, "old cellulare phone. year 2012 or less", Toast.LENGTH_LONG).show();
                Toast.makeText(this , "recomended to upgrade the phone", Toast.LENGTH_LONG).show();
                exhibited=true;
            }
        }


        for (int i=0 ; i<ids_image_button_bread.length; i++)
        {
            btn=(ImageButton)findViewById(ids_image_button_bread[i]);
            btn.clearColorFilter();
        }

        btn=(ImageButton)findViewById(idb);

        if (jelly_bean_and_over)
        {
            if (cf==null)
              btn.setColorFilter(filter);
            else
              bread="";
        }
        else
        {
            if (last_btn_hit_bread == null) {
                btn.setColorFilter(filter);
                last_btn_hit_bread=btn;
            }
            else
            {
                if (last_btn_hit_bread==btn_hit)
                {
                    last_btn_hit_bread=null;
                }
                else
                {
                    btn.setColorFilter(filter);
                    last_btn_hit_bread=btn;
                }
            }
            if (last_btn_hit_bread == null)
                bread="";
        }
         zor_hazmana();
    }


    public void hitMain (View v)
    {
        boolean jelly_bean_and_over=false;
        ImageButton btn_hit;
        ImageButton btn;
        ColorFilter cf=null;



        int idb=v.getId();
        main=(String)v.getTag();
        btn_hit=(ImageButton)findViewById(idb);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN )
        {
            cf= btn_hit.getColorFilter();
            jelly_bean_and_over=true;
        }
        else
        {
            jelly_bean_and_over=false;
            if (!exhibited ) {
                Toast.makeText(this, "old cellulare phone. year 2012 or less", Toast.LENGTH_LONG).show();
                Toast.makeText(this , "recomended to upgrade the phone", Toast.LENGTH_LONG).show();
                exhibited=true;
            }
        }


        for (int i=0 ; i<ids_image_button_main.length; i++)
        {
            btn=(ImageButton)findViewById(ids_image_button_main[i]);
            btn.clearColorFilter();
        }

        btn=(ImageButton)findViewById(idb);

        if (jelly_bean_and_over)
        {
            if (cf==null)
                btn.setColorFilter(filter);
            else
                main="";
        }
        else
        {
            if (last_btn_hit_main == null) {
                btn.setColorFilter(filter);
                last_btn_hit_main=btn;
            }
            else
            {
                if (last_btn_hit_main==btn_hit)
                {
                    last_btn_hit_main=null;
                }
                else
                {
                    btn.setColorFilter(filter);
                    last_btn_hit_main=btn;
                }
            }
            if (last_btn_hit_main == null)
                main="";
        }
        zor_hazmana();
    }


    public void hitTosafot (View v)
    {
        boolean jelly_bean_and_over=false;
        ImageButton btn_hit;
        ImageButton btn;
        ColorFilter cf=null;
        String tmp_tosafot;



        int idb=v.getId();
        tmp_tosafot=((String)v.getTag()).trim();
        btn_hit=(ImageButton)findViewById(idb);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN )
        {
            cf= btn_hit.getColorFilter();

            jelly_bean_and_over=true;
        }
        else
        {
            jelly_bean_and_over=false;
            if (!exhibited ) {
                Toast.makeText(this, "old cellulare phone. year 2012 or less", Toast.LENGTH_LONG).show();
                Toast.makeText(this , "recomended to upgrade the phone", Toast.LENGTH_LONG).show();
                exhibited=true;
            }
        }


        for (int i=0 ; i<ids_image_button_tosafot.length; i++)
        {
            btn=(ImageButton)findViewById(ids_image_button_tosafot[i]);
            if (btn == btn_hit)
            {

                if (jelly_bean_and_over)
                {
                    if (cf == null) {
                        btn_hit.setColorFilter(filter);
                        tosafot = tosafot + " " + tmp_tosafot;
                    } else {
                        btn.clearColorFilter();
                        tosafot = tosafot.replace(tmp_tosafot, "");
                    }
                    tosafot=tosafot.trim();
                }
                else
                {

                }
            }
        }//for

        zor_hazmana();
    }

    public void tavlinim (View v)
    {
        CheckBox chbx_hit;
        CheckBox btn;
        String tmp_tavlinim;
        int idb=v.getId();
        tmp_tavlinim=((String)v.getTag()).trim();
        chbx_hit=(CheckBox)findViewById(idb);


        if (s_in_st(st_tavlinim, tmp_tavlinim))
            remove_s_from_st(st_tavlinim, tmp_tavlinim);
        else
            st_tavlinim.push(tmp_tavlinim) ;

        zor_hazmana_tavlinim_drinks();
    }

    public void drinks (View v)
    {
        CheckBox chbx_hit;
        CheckBox btn;
        String tmp_drink;
        int idb=v.getId();
        tmp_drink=((String)v.getTag()).trim();
        chbx_hit=(CheckBox)findViewById(idb);


        if (s_in_st(st_drinks, tmp_drink))
            remove_s_from_st(st_drinks, tmp_drink);
        else
            st_drinks.push(tmp_drink) ;

        zor_hazmana_tavlinim_drinks();
    }


    public void sndmsg1 (View v)
    {
        String phoneNo="";
        String msg="";
        if (hazmana != null)
        {
            //TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            //String mPhoneNumber = tMgr.getLine1Number();
            //msg= "ההזמנה ממספר סלולארי :"+"77777"+"\n";

            msg=msg+ "מנה עיקרית:"+hazmana;
            if (hazmana_tavlinim !=null)
            {
                msg=msg+ "\n"+"תבלינים :"+hazmana_tavlinim;
            }

            if (hazmana_drinks !=null)
            {
                msg=msg+ "\n"+"שתייה :"+hazmana_drinks;
            }

            //byte[] data =LongMsg.convertUnicode2GSM( msg);
            //String s = new String(data);
            //sendSMS(phoneNo, s);
            sendSMS(phoneNo, msg);
           // <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
        }
        else
        {
            Toast.makeText(getApplicationContext(), " חובה הזמנה מנה עיקרית: לחם ואחת המנות העיקריות",
                    Toast.LENGTH_LONG).show();

        }


    }

    public void sndmsg (View v)
    {


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);


        TelephonyManager telMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String myphone;
        try
        {
            myphone=telMgr.getLine1Number();
        }
        catch (Exception e)
        {
            myphone="uknown ";
        }



        if (myphone.equals("uknown "))
        {
              Toast.makeText(getApplicationContext(), "can not get phone number . look sim", Toast.LENGTH_SHORT).show();
        }
        else
        {
              Toast.makeText(getApplicationContext(), "Mobile Number : "+ myphone, Toast.LENGTH_SHORT).show();
        }


        String phone=myphone;

        EditText edit_text   = (EditText)findViewById(R.id.phone);
        String phoneNo=edit_text.getText().toString();



        String msg="";
        if (hazmana != null)
        {
            msg=phone+"\n"+hazmana;
            if (hazmana_tavlinim !=null)
            {
                msg=msg+ "\n"+hazmana_tavlinim;
            }

            if (hazmana_drinks !=null)
            {
                msg=msg+ "\n"+"שתייה :"+hazmana_drinks;
            }

            sendSMS(phoneNo, msg);
        }
        else
        {
            Toast.makeText(getApplicationContext(), " חובה הזמנה מנה עיקרית: לחם ואחת המנות העיקריות",
                    Toast.LENGTH_LONG).show();

        }


    }


    // http://www.wrankl.de/JavaPC/SMSTools.html for long message
    // for different phones look
    // http://stackoverflow.com/questions/1959522/which-class-should-we-use-for-sending-sms-text-messages
    public void sendSMS(String phoneNo, String msg)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(),"ההזמה נשלחה"+msg.length()+ " to:"+phoneNo,
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }







    // see if value s in stack. st kept.
    public static boolean s_in_st(Stack<String> st , String s)
    {
        boolean b=false;
        Stack<String> tmp=new Stack<String> ();

        while (!st.isEmpty())
        {
            String y=st.pop();
            if (y.equalsIgnoreCase(s))
                b= true;
            tmp.push(y);
        }

        while (!tmp.isEmpty())
            st.push(tmp.pop());
        return b;
    }


    // remove value s from stack. st is kept just without value s
    public static void remove_s_from_st(Stack<String> st , String s)
    {

        Stack<String> tmp_st=new Stack<String>();

        while (!st.isEmpty())
        {
            if (! (st.peek().equalsIgnoreCase(s)))
                tmp_st.push(st.pop());
            else
                st.pop();
        }

        while (!tmp_st.isEmpty())
            st.push(tmp_st.pop());

    }
}
