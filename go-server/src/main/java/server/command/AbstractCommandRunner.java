package server.command;

/**
 * @since: 2023/4/11.
 * @Author: LiuXinjie
 */
public abstract class AbstractCommandRunner implements CommandRunner {

    private final static String COMMAND_SEPARATOR = ":";

    @Override
    public String execute(String command) {
        String[] commandArray = command.split(COMMAND_SEPARATOR);

        if (!command().equals(commandArray[0])) {
            return "unknown command";
        }
        return doExecute(commandArray[1]);
    }

    protected abstract String doExecute(String commandContent);

    protected abstract String command();
}
