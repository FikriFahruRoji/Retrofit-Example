package id.fikri.testretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tv_respond, tv_result_api;
    Button boom;

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://private-7bb04d-signandlogin.apiary-mock.com/users/").addConverterFactory(GsonConverterFactory.create(gson)).build();
    UserAPI user_api = retrofit.create(UserAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_respond = (TextView)findViewById(R.id.tv_respond);
        tv_result_api = (TextView)findViewById(R.id.tv_result_api);
        boom = (Button)findViewById(R.id.boom);
        boom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              getUser("1");
                getUsers();
            }
        });
    }

    public void getUsers(){
        // implement interface for get all users
        Call<Users> calls = user_api.getUsers();
        calls.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                tv_result_api.setText("");
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                //this extract data from retrofit with for() loop
                for(Users.UserItem user : response.body().getUsers()) {
                    tv_result_api.append( String.valueOf(user.getId()) + "\n" + user.getEmail() + "\n" + user.getPassword() + "\n");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_respond.setText(String.valueOf(t));
            }
        });
    }

    public void getUser(String id) {
        Call<User> call = user_api.getUser(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                tv_result_api.setText(String.valueOf(response.body().getId() + "\n" + response.body().getEmail()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                tv_respond.setText(String.valueOf(t));
            }
        });
    }
}
