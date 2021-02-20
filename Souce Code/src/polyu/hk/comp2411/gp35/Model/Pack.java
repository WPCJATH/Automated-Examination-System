package polyu.hk.comp2411.gp35.Model;

public class Pack {
    public static String pack(String ms){
        return "'"+ms+"'";
    }

    public static String pack(int ms){
        return "'"+ms+"'";
    }

    public static String pack(String[] mss){
        if (mss.length == 0) return "";
        StringBuilder sb = new StringBuilder(Pack.pack(mss[0]));
        for (int i=1;i<mss.length;i++){
            sb.append(",");
            sb.append(Pack.pack(mss[i]));
        }
        return sb.toString();
    }

    public static String pack2(String ms) {return "`"+ms+"`";}
}
