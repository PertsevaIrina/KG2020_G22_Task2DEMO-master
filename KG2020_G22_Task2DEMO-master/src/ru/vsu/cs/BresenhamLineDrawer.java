package ru.vsu.cs;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {
    private PixelDrawer pd;


    public BresenhamLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }


    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        int x = x1, y = y1;
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
        int sx = (x2 - x1 >= 0 ? 1 : -1);
        int sy = (y2 - y1 >= 0 ? 1 : -1);
        boolean flag = false;
        if (dy > dx) {
            int z = dx;
            dx = dy;
            dy = z;
            flag = true;
        }
        int e = 2 * dy - dx;

        for (int k = 1; k <= dx; k++) {
            pd.colorPixel(x, y, new Color(153, 204, 204));
            while (e >= 0) {
                if (flag) {
                    x += sx;
                } else {
                    y += sy;
                }
                e = e - 2 * dx;
            }
            if (flag) {
                y += sy;
            } else {
                x += sx;
            }
            e = e + 2 * dy;
        }
        pd.colorPixel(x, y, new Color(153, 204, 204));
    }
}
