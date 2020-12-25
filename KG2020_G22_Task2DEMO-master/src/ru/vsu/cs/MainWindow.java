package ru.vsu.cs;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private DrawPanel dp;

    public MainWindow() throws Exception {
        dp = new DrawPanel();
        this.add(dp);
    }
}
