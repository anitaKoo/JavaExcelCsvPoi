import java.io.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ShellExecutor {

    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }
    }


    protected boolean runSH(String script, List<String> scriptArgs, String directory ) throws IOException, InterruptedException {

        String delimiter = " ";
        String arguments = String.join(delimiter, scriptArgs);

        ProcessBuilder builder = new ProcessBuilder();
        if(isWindows)
            builder.command("cmd.exe", script, arguments);
        else
            builder.command("sh", script, arguments);
        builder.directory(new File(directory));
        Process process = builder.start();
        StreamGobbler inGobbler =
                new StreamGobbler(process.getInputStream(), System.out::println);
        StreamGobbler errorGobbler =
                new StreamGobbler(process.getErrorStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(inGobbler);
        Executors.newSingleThreadExecutor().submit(errorGobbler);
        int exitCode = process.waitFor();
        process.destroy();

        return (exitCode == 0);
    }



}
