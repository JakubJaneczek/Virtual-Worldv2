package POJava;

public class Log {
    private static String log = "";

    public static void AddComment(String comment) {
        log += comment + "\n";
    }

    public static String getLog() {
        return log;
    }

    public static void ClearLogs() {
        log = "";
    }
}
