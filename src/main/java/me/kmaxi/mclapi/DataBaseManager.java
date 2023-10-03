package me.kmaxi.mclapi;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static me.kmaxi.mclapi.ApiKey.*;

public class DataBaseManager {



    private Connection connection;
    public static DataBaseManager instance;

    public DataBaseManager() {
        instance = this;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOSTNAME + ":" + DB_PORT + "/" + DB_NAME, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) this.connect();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return this.connection;
    }


    public static DataBaseManager getInstance() {
        if (instance == null) new DataBaseManager();
        return instance;
    }

}
