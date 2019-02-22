package ru.job4j.find;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Path;

/**
 * FindFileApp.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 22.02.2019
 */
public final class FindFileApp {
    private final String[] args;

    public FindFileApp(final String[] args) {
        this.args = args;
    }

    public static void main(String[] args) throws ParseException, IOException {
        new FindFileApp(args).start();
    }

    /**
     * Запускает консольное приложение.
     *
     * @throws ParseException если отсутствуют требуемые опции.
     * @throws IOException    если возникло исключение во время поиска,
     *                        либо записи в конечный файл.
     */
    public final void start() throws ParseException, IOException {
        final Options options = new Options();
        options.addOption(Option
                .builder("h")
                .longOpt("help")
                .desc("print this help message to the output stream")
                .build()
        );
        options.addOption(Option
                .builder("d")
                .argName("Directory")
                .desc("main search directory")
                .hasArg()
                .required()
                .build()
        );
        options.addOption(Option
                .builder("n")
                .desc("search string")
                .hasArg()
                .required()
                .build()
        );
        final OptionGroup group = new OptionGroup()
                .addOption(Option
                        .builder("m")
                        .desc("mask search")
                        .build()
                )
                .addOption(Option
                        .builder("f")
                        .desc("full compare")
                        .build()
                ).addOption(Option
                        .builder("r")
                        .desc("regular expression")
                        .build()
                );
        group.setRequired(true);
        options.addOptionGroup(group);
        options.addOption(Option
                .builder("o")
                .desc("target file path")
                .hasArg()
                .required()
                .build()
        );
        HelpFormatter formatter = new HelpFormatter();
        if (this.args.length == 0) {
            formatter.printHelp("find", options, true);
        } else if ("help".equals(this.args[0])
                || "-h".equals(this.args[0])
                || "--help".equals(this.args[0])) {
            formatter.printHelp("find", options, true);
        } else {
            final CommandLine cmdLine
                    = new DefaultParser().parse(options, this.args);
            new FileInFileTree(
                    Path.of(cmdLine.getOptionValue("d")),
                    new PathFilters().pathFilter(
                            group.getSelected(),
                            cmdLine.getOptionValue("n")
                    )
            ).save(Path.of(cmdLine.getOptionValue("o")));
        }
    }
}
