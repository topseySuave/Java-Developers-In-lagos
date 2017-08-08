package apps.gabrielmicah.githubdevs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    String url,image,name,profile_link,username,html_url;
    ImageView profilePics,img;
    TextView text_name;
    Button link;
    ProgressBar loading;
    AppBarLayout appbar;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);

        profilePics = (ImageView)findViewById(R.id.profile_pics);
        text_name = (TextView)findViewById(R.id.profile_name);
        link = (Button)findViewById(R.id.profile_link);
        loading = (ProgressBar)findViewById(R.id.progressBar);
        appbar = (AppBarLayout)findViewById(R.id.appbar);

        profilePics.setVisibility(View.GONE);

        text_name.setVisibility(View.GONE);
        link.setVisibility(View.GONE);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        url = intent.getStringExtra("profile_url");
        html_url = intent.getStringExtra("html_url");
        image = intent.getStringExtra("image");

        toolbar.setTitle(username);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("Response json","response: "+response.toString());
                try {
                    setProfile(response);
                } catch (JSONException e) {
                    Log.d("Response json","Error process the method... ");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });
        queue.add(jsObjRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag){
                    Snackbar.make(view, "Profile is still loading...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    try{
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT,"Github Devs");
                        String sAux = "\nCheck out this Awesome Developer @"+username+", "+html_url;
                        i.putExtra(Intent.EXTRA_TEXT,sAux);
                        startActivity(Intent.createChooser(i,"Choose one"));
                    }
                    catch (Exception e){
                        Snackbar.make(view, "Error sharing profile...", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setProfile(JSONObject resp) throws JSONException
    {
        flag = true;

        name = resp.getString("name");
        profile_link = resp.getString("html_url");

        profilePics.setVisibility(View.VISIBLE);

        //set image
        RoundedTransformation transfm = new RoundedTransformation(175, 4);
        Picasso.with(this)
                .load(image)
                .resize(350,350)
                .transform(transfm)
                .centerCrop()
                .into(profilePics);

        setBlur();

        //set Name
        text_name.setText(name);

        //set link
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(html_url));
                startActivity(i);
            }
        });

        //show Name and link button,remove loading bar
        text_name.setVisibility(View.VISIBLE);
        link.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    //blur AppBar Background image..
    private void setBlur()
    {
        img = new ImageView(this);
        Picasso.with(this)
                .load(image)
                .transform(new BlurTransform(this))
                .into(img, new Callback(){
                    public void onSuccess(){
                        appbar.setBackground(img.getDrawable());
                    }
                    public void onError(){}
                });

    }

}
