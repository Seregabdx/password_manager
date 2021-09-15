package App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by HOME on 04.12.2020.
 */
public class Util {
    public static boolean isEmpty(String p1, String p2, String p3) {
        return (p1.equals("") || p2.equals("") || p3.equals("0"));
    }
    public static String makeFileName (String source, String extension) {
        int slen = source.length();
        int elen = extension.length();
        if (slen<=elen) {return source+extension;}
        char[] cmpr = new char[elen];
        source.getChars(slen-elen,slen,cmpr,0);
        String cmprS = new String(cmpr);
        if (extension.equals(cmprS)) {return source;}
        return (source+extension);
    }
    public static void connect(String psw) {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionURL = "jdbc:mysql://localhost:3306/pswbd?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pswdb?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", psw);

        } catch (Exception exc) {
            throw new RuntimeException("No database");
        }
    }
}
