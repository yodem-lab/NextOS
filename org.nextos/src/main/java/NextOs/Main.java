package NextOs;

import javax.swing.*;
import java.awt.*;

public class Main
{
    private static final Dimension FRAME_SIZE = new Dimension(800, 600);
    private static final String TITLE = "Next Os";
    void main() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(FRAME_SIZE);
            frame.setMinimumSize(FRAME_SIZE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}