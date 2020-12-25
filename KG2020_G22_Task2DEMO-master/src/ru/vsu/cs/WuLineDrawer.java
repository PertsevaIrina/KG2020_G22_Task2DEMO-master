package ru.vsu.cs;

import java.awt.*;

public class WuLineDrawer implements LineDrawer{
    private PixelDrawer pd;


    public WuLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        Color c = Color.BLACK;

        int sX = (x2 - x1 >= 0 ? 1 : -1);
        int sY = (y2 - y1 >= 0 ? 1 : -1);

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int x = x1;
        int y = y1;
        int e = 0;
        int gradient;
        Color c1, c2;

        if (dx >= dy) {

            for(int i = 0; i <= dx; i++){
                if (dx == 0) {
                    gradient = 255;   //белый
                } else {
                    gradient = (255 * e) / (2 * dx);
                }

                c1 = setColor( 255 - Math.abs(gradient), c);
                c2 = setColor( Math.abs(gradient), c);
                pd.colorPixel(x, y, c1);

                if (gradient > 0) {
                    pd.colorPixel(x, y + sY, c2);
                } else {
                    pd.colorPixel(x, y - sY, c2);
                }

                e =  e + 2 * dy;
                if (e > dx) {
                    y = y + sY;
                    e = e - 2 * dx;
                } else if (e < -dx) {
                    y = y - sY;
                }
                x = x + sX;

            }
        } else {

            for(int i = 0; i <= dy; i++){
                if (dy == 0) {
                    gradient = 255;
                } else {
                    gradient = (255 * e) / (2 * dy);
                }

                c1 = setColor(255 - Math.abs(gradient), c);
                c2 = setColor(Math.abs(gradient), c);
                pd.colorPixel(x, y, c1);

                if (gradient > 0) {
                    pd.colorPixel(x + sX, y, c2);
                } else {
                    pd.colorPixel(x - sX, y, c2);
                }

                e = e + 2 * dx;
                if (e > dy) {
                    x = x + sX;
                    e = e - 2 * dy;
                } else if (e < -dy) {
                    x = x - sX;
                }
                y = y + sY;
            }
        }

    }

    private Color setColor(int t, Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), t);
    }


}


