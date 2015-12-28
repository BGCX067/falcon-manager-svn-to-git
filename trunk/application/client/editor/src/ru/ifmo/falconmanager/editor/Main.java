package ru.ifmo.falconmanager.editor;

import ru.ifmo.falconmanager.database.DatabaseSnapshot;
import ru.ifmo.falconmanager.editor.gui.MainForm;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 14:34:29
 */
public class Main {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Locale.setDefault(Locale.US);
        SwingUtilities.invokeLater(
            new Runnable() {
                public void run() {
                    JFrame frame = new JFrame("Falcon manager. (c) Dmitry Paraschenko & Fedor Tsarev. 2008");
                    MainForm form = new MainForm();
                    frame.setContentPane(form.$$$getRootComponent$$$());
                    frame.pack();
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - 30 - frame.getHeight()) / 2);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    DatabaseSnapshot.getInstance().reloadAll();
                }
            }
        );
    }
}
