package ru.job4j.bot;

import ru.job4j.sort.FileLine;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Answer command.
 *
 * @author Alexander Yakovlev (sanyakovlev@yandex.ru)
 * @since 03.01.2019
 */
public class Answer implements Command {
    /**
     * Silence mode[on=true, off=false].
     */
    private final AtomicBoolean silence;
    private final List<FileLine> answers;
    private final PrintWriter writer;

    public Answer(final AtomicBoolean silence, final List<FileLine> answers,
                  final PrintWriter writer) {
        this.silence = silence;
        this.answers = answers;
        this.writer = writer;
    }

    /**
     * Отправит рандомную фразу, если не включен режим тишины.
     */
    @Override
    public void execute() {
        if (!this.silence.get()) {
            final String answer = this.answers
                    .get((int) (Math.random() * answers.size()))
                    .line();
            this.writer.println(answer);
        }
    }
}
