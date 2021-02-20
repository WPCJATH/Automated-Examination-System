package polyu.hk.comp2411.gp35.Model;

import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is built the System to access the database
 *         so that the system can read,write,insert,query,update the data of database
 *         Once the system start, the connection is open
 *         Only if the system shut down, connection will be switched off
 */
public class Connection {
    static String HostName;
    static String Port;
    static String ConnectionName;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL1 = "jdbc:mysql://";
    static final String DB_URL2 = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static  String USER;
    static  String PASS;

    static java.sql.Connection conn = null;
    //static Statement stmt = null;

    static String DB_URL;
    static int fileNum = 0;

    public Connection(String HostName, String Port, String ConnectionName, String user, String password){
        Connection.HostName = HostName;
        Connection.Port = Port;
        Connection.ConnectionName = ConnectionName;
        Connection.USER = user;
        Connection.PASS = password;
    }

    public Connection(){
        HostName = "127.0.0.1";
        Port = "3305";
        ConnectionName = "AESData";
        USER = "root";
        PASS = "123456";
    }

    /**
     * code of connecting MySQL and read data: https://www.runoob.com/java/java-mysql-connect.html
     * Modified
     */
    public static void makeConnection() throws Exception{

        DB_URL = DB_URL1 + HostName + ':' + Port + '/' + ConnectionName + DB_URL2;
        try {
            // Register JDBC drive
            Class.forName(JDBC_DRIVER);
        }catch (Exception se){
            System.out.println("JDBC Driver has a problem, please switch to source code: package Model class Connection and modify it.");
            se.printStackTrace();// deal with the JDBC error.
        }

        try{
            // open the url
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //stmt = conn.createStatement();
        }catch (Exception se){
            throw new Exception(se);// deal with the default database info error.
        }

    }



    /**get data with possibilities of muti-rows and muti-columns*/
    public static ArrayList<String[]> getMoreData(String ms, int ColumnCount){
        ArrayList<String[]> data = new ArrayList<>();
        boolean flag = false;
        try {// execution query
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ms);
            //System.out.println();

            // expand the ResultSet
            while (rs.next()) {
                // search
                flag = true;
                String[] Column = new String[ColumnCount];
                for (int i=0;i<ColumnCount;i++){
                    Column[i] = rs.getString(i);
                }
                data.add(Column);

            }
            // close after completed
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.getStackTrace();
        }
        return (!flag)? null:data;
    }

    /**get data with possibilities of muti-column and 1 row*/
    public static String[] getData(String ms, int ColumnCount){
        String[] data = new String[ColumnCount];
        boolean flag = false;
        try {// execution query
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ms);
            //System.out.println();

            int i =0;
            // expand the ResultSet
            if (rs.next()) {
                // search
                flag = true;
                while (i<ColumnCount) {
                    data[i] = rs.getString(i + 1);
                    i++;
                }
            }
            // close after completed
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.printStackTrace();
        }
        return (!flag)? null:data;
    }

    /**get data with only 1 row and muti-column*/
    public static String[] getData(String ms){
        ArrayList<String> items = new ArrayList<>();
        try {
            // execution query
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ms);
            // close after completed

            while (rs.next()) {
                items.add(rs.getString(1));
            }
            // close after completed
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.getStackTrace();
        }
        return (items.isEmpty())? null:items.toArray(new String[0]);
    }

    /**Get the count of rows*/
    public static int getCount(String from){
        int count = 0;
        try {// execution query
            String sql;
            sql = "select count(*) as totalCount "+from;
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //System.out.println();

            // expand the ResultSet
            if (rs.next()) {
                // search
                count = rs.getInt(1);
            }
            // close after completed
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.getStackTrace();
        }
        return count;
    }


    //code of reading pics from Database: https://blog.csdn.net/Jymman/article/details/90724974
    // Modified
    public static String getPic(String where){
        String path = "\\src\\TempFile"+fileNum+".jpg";
        boolean flag = false;
        try {// execution query
            String sql;
            sql = "select `graph` from `answer`"+where;
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // expand the ResultSet
            if (rs.next()) {
                // search
                flag = true;
                Blob photo=rs.getBlob(0);

                InputStream in = photo.getBinaryStream();
                FileOutputStream out = new FileOutputStream(path);

                byte[] buffer = new byte[1024*1024];// 1M
                int len;
                while((len = in.read(buffer))!=-1) {//write pic in path
                    out.write(buffer,0,len); }

                fileNum++;
            }
            // close after completed
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException | IOException se){
            se.getStackTrace();
        }
        return (!flag)? null:path;
    }

    //code of reading audios from Database: https://blog.csdn.net/Jymman/article/details/90724974
    // Modified
    public static String getAudio(String where){
        String path = "\\src\\TempFile"+fileNum+".mp3";
        boolean flag = false;
        try {// execution query
            String sql;
            sql = "select `audio` from `answer`"+where;
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // expand the ResultSet
            if (rs.next()) {
                // search
                flag = true;
                Blob audio=rs.getBlob(0);//读取数据库中图片的Blob流

                InputStream in = audio.getBinaryStream();
                FileOutputStream out = new FileOutputStream(path);

                byte[] buffer = new byte[1024*1024];// 1M
                int len;
                while((len = in.read(buffer))!=-1) {//write pic in path
                    out.write(buffer,0,len); }

                fileNum++;
            }
            // close after completed
            rs.close();
            stmt.close();
            conn.close();
        }catch (SQLException | IOException se){
            se.getStackTrace();
        }
        return (!flag)? null:path;
    }

    public static void updateData(String ms){
        try {// execution query
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(ms);
            stmt.close();
            conn.close();
        }catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
