import javax.swing.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class JTextAreaPrintStream {
    private JTextArea textArea;

    public JTextAreaPrintStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    public PrintStream getPrintStream() {
        return new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                textArea.append(String.valueOf((char) b));
            }
        }, true);
    }
}
