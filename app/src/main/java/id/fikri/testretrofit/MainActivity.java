package id.fikri.testretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_respond = (TextView)findViewById(R.id.tv_respond);
        tv_result_api = (TextView)findViewById(R.id.tv_result_api);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-7bb04d-signandlogin.apiary-mock.com/users/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UserAPI user_api = retrofit.create(UserAPI.class);

        // implement interface for get all user
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                //this extract data from retrofit with for() loop
                for(Users.UserItem user : response.body().getUsers()) {
                    tv_result_api.append(
                            "Id = " + String.valueOf(user.getId()) + System.getProperty("line.separator") +
                            "Email = " + user.getEmail() + System.getProperty("line.separator") +
                            "Password = " + user.getPassword() + System.getProperty("line.separator")
                    );
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_respond.setText(String.valueOf(t));
            }
        });
    }
}
