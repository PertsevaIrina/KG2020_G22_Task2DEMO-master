package ru.vsu.cs;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.MyFactoryImplementation;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.testing.TestArcs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel implements MouseMotionListener {
    private Point2D position = new Point(0, 0);
    public DrawPanel() throws Exception {
        this.addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.createGraphics();

        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.setColor(new Color(204, 99, 200));

        PixelDrawer pd = new GraphicsPixelDrawer(gr);
        LineDrawer ld = new WuLineDrawer(pd);

        try {
            drawAll(ld); //метод рисования снежинки
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void drawSnowFlake(LineDrawer ld, int x, int y, int r, int n) {
        double da = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double a = da * i;
            double dx = r * Math.cos(a);
            double dy = r * Math.sin(a);
            ld.drawLine(x, y, x + (int) dx, y + (int) dy);
        }
    }

    private void drawAll(LineDrawer ld) throws Exception {
        drawSnowFlake(ld, getWidth() / 2, getHeight() / 2, 150, 64);
        ld.drawLine(getWidth() / 2, getHeight() / 2, (int) position.getX(), (int) position.getY());

        TestArcs.startTest(new MyFactoryImplementation(), TestArcs.IMG_DIFF, TestArcs.TEST_ARC, true, "./KG2020_G22_Task2DEMO-master\\");
//        TestArcs.startTest(new MyFactoryImplementation(), TestArcs.IMG_DIFF, TestArcs.TEST_PIE, true, "./KG2020_G22_Task2DEMO-master\\");
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        position = new Point (e.getX(), e.getY());
        repaint();
    }
}
