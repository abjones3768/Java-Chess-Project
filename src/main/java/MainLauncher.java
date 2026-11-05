public class MainLauncher {
    public static void main(String[] args) {
        if (args.length == 0) chess.main(args);
        else
        {
            backendTest test = new backendTest();
            test.main();
        }
    }
}