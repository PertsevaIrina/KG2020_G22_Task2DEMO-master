package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BresenhamPieDrawer implements PieDrawer {
    private ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd;

    public BresenhamPieDrawer(ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawPie(int x, int y, int width, int height, double startAngle, double arcAngle, Color c) {
        int a = width / 2;
        int b = height / 2;
        int x0 = x + a;
        int y0 = y + b;
        int sX = (int) (a * Math.cos(startAngle));
        int sY = (int) (b * Math.sin(startAngle));
        int eX = (int) (a * Math.cos(startAngle + arcAngle));
        int eY = (int) (b * Math.sin(startAngle + arcAngle));
        drawLine(x0, y0, x0 + sX, y0 - sY, c);
        drawLine(x0, y0, x0 + eX, y0 - eY, c);
        draw(x0, y0, a, b, startAngle, arcAngle, c);
    }

    private double roundAngle(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private void drawLines(int x0, int y0, double startAngle, double endAngle, Color c) {
        int[] start = list.get(0);
        int[] end = list.get(0);
        for (int[] a : list) {
            if (Math.abs(startAngle - getAngle(a[0], a[1])) < Math.abs(startAngle - getAngle(start[0], start[1]))) {
                start = a;
            }
            if (Math.abs(endAngle - getAngle(a[0], a[1])) < Math.abs(endAngle - getAngle(end[0], end[1]))) {
                end = a;
            }
        }
        drawLine(x0, y0, x0 + start[0], y0 + start[1], c);
        drawLine(x0, y0, x0 + end[0], y0 + end[1], c);
    }

    private void draw(int x0, int y0, int rX, int rY, double startAngle, double arcAngle, Color c) {
        int twoA = 2 * rX * rX;
        int twoB = 2 * rY * rY;
        int x = rX;
        int y = 0;
        int dx = rY * rY * (1 - 2 * rX);
        int dy = rX * rX;
        int error = 0;
        int stopX = twoB * rX;
        int stopY = 0;
        while (stopX > stopY) {
            drawPixels(x0, y0, x, y, startAngle, arcAngle, c);
            y++;
            stopY += twoA;
            error += dy;
            dy += twoA;
            if ((2 * error + dx) >= 0) {
                x--;
                stopX -= twoB;
                error += dx;
                dx += twoB;
            }
        }
        x = 0;
        y = rY;
        dx = rY * rY;
        dy = rX * rX * (1 - 2 * rY);
        error = 0;
        stopX = 0;
        stopY = twoA * rY;
        while (stopX <= stopY) {
            drawPixels(x0, y0, x, y, startAngle, arcAngle, c);
            x++;
            stopX += twoB;
            error += dx;
            dx += twoB;
            if ((2 * error + dy) >= 0) {
                y--;
                stopY -= twoA;
                error += dy;
                dy += twoA;
            }
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2, Color c) {
        int x = x1;
        int y = y1;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int xs = x1 < x2 ? 1 : -1;
        int ys = y1 < y2 ? 1 : -1;
        int swap = 0;
        int step;
        if ((step = dx) < dy) {
            swap++;
            step = dy;
            dy = dx;
            dx = step;
        }
        int s = (2 * dy) - dx;
        pd.setPixel(x, y, c);
        while (step > 0) {
            step--;
            if (s >= 0) {
                if (swap != 0) {
                    x += xs;
                } else {
                    y += ys;
                }
                s -= (2 * dx);
            }
            if (swap != 0) {
                y += ys;
            } else {
                x += xs;
            }
            s += (2 * dy);
            pd.setPixel(x, y, c);
        }
    }

    private List<int[]> list = new ArrayList<>();

    private void drawPixels(int x, int y, int dx, int dy, double startAngle, double arcAngle, Color c) {
        double angle = getAngleInFirstQuad(dx, dy);
        if (inAngle(angle, startAngle, arcAngle)) {
            pd.setPixel(x + dx, y - dy, c);
            list.add(new int[]{dx, -dy});
        }
        if (inAngle(Math.PI - angle, startAngle, arcAngle)) {
            pd.setPixel(x - dx, y - dy, c);
            list.add(new int[]{-dx, -dy});
        }
        if (inAngle(Math.PI + angle, startAngle, arcAngle)) {
            pd.setPixel(x - dx, y + dy, c);
            list.add(new int[]{-dx, dy});
        }
        if (inAngle(2 * Math.PI - angle, startAngle, arcAngle)) {
            pd.setPixel(x + dx, y + dy, c);
            list.add(new int[]{dx, dy});
        }
    }

    private boolean inAngle(double angle, double startAngle, double arcAngle) {
        double a = Math.min(startAngle, startAngle + arcAngle);
        double b = Math.max(startAngle, startAngle + arcAngle);
        double ang = angle;
        while (ang <= b) {
            if (ang >= a && ang <= b) {
                return true;
            }
            ang += Math.PI * 2;
        }
        ang = angle;
        while (ang >= a) {
            if (ang >= a && ang <= b) {
                return true;
            }
            ang -= Math.PI * 2;
        }
        return false;
    }

    private double getAngleInFirstQuad(int x, int y) {
        if (x == 0) {
            return Math.PI / 2;
        }
        double tan = ((double) y / x);
        return Math.atan(tan);
    }

    private double getAngle(int x, int y) {
        double angle = getAngleInFirstQuad(x, y);
        if (x >= 0) {
            if (y < 0) {
                return angle;
            } else {
                return 2 * Math.PI - angle;
            }
        } else {
            if (y < 0) {
                return Math.PI - angle;
            } else {
                return Math.PI + angle;
            }
        }
    }

    private int round(double num) {
        if (num < 0 && num % 1 >= -0.5) {
            num--;
        } else if (num > 0 && num % 1 >= 0.5) {
            num++;
        }
        return (int) num;
    }
}