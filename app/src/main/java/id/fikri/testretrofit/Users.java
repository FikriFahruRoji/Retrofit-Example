package id.fikri.testretrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lowercase on 15/12/16.
 */

public class Users {
    @SerializedName("users")
    public List<UserItem> users;
    public List<UserItem> getUsers() { return users; }
    public void setUsers(List<UserItem> users) { this.users = users; }
    public Users(List<UserItem> users) { this.users = users; }

    public class UserItem {
        private int id;
        private String email;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
