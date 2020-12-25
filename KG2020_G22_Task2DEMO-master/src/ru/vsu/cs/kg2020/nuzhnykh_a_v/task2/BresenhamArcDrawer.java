package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2;

import java.awt.*;

public class BresenhamArcDrawer implements ArcDrawer {
    private ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd;

    public BresenhamArcDrawer(ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawArc(int x, int y, int width, int height, double startAngle, double arcAngle, Color c) {
        int a = width / 2;
        int b = height / 2;
        int x0 = x + width / 2;
        int y0 = y + height / 2;
        draw(x0, y0, a, b, startAngle, arcAngle, c);
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
            if ((2 * error + dx) > 0) {
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
            if ((2 * error + dy) > 0) {
                y--;
                stopY -= twoA;
                error += dy;
                dy += twoA;
            }
        }
    }

    private void drawPixels(int x, int y, int dx, int dy, double startAngle, double arcAngle, Color c) {
        double angle = getAngle(dx, dy);
        if (inAngle(angle, startAngle, arcAngle)) {
            pd.setPixel(x + dx, y - dy, c);
        }
        if (inAngle(Math.PI - angle, startAngle, arcAngle)) {
            pd.setPixel(x - dx, y - dy, c);
        }
        if (inAngle(Math.PI + angle, startAngle, arcAngle)) {
            pd.setPixel(x - dx, y + dy, c);
        }
        if (inAngle(2 * Math.PI - angle, startAngle, arcAngle)) {
            pd.setPixel(x + dx, y + dy, c);
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

    private double getAngle(int x, int y) {
        if (x == 0) {
            return Math.PI / 2;
        }
        double tan = ((double) y / x);
        return Math.atan(tan);
    }
}

//
//public class BresenhamArcDrawer implements ArcDrawer {
//    private ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd;
//
//    public BresenhamArcDrawer(ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd) {
//        this.pd = pd;
//    }
//
//    public void drawArc(int x0, int y0, int width, int height, int startAngle, int sweepAngle, Color color) {
//        if (sweepAngle == 0) {
//            return;
//        }
//        while (startAngle <= -180) {
//            startAngle += 360;
//        }
//        while (startAngle >= 180) {
//            startAngle -= 360;
//        }
//        if (startAngle + sweepAngle >= 180) {
//            if (sweepAngle >= 360) {
//                startAngle = -180;
//                sweepAngle = 360;
//            } else {
//                drawArc(x0, y0, width, height, -180, sweepAngle - 180 + startAngle, color);
//                sweepAngle = 180 - startAngle;
//            }
//        }
//        int y = height;
//        int x = 0;
//        final int bSquared = height * height;
//        final int aSquared = width * width;
//        final int doubleASquared = aSquared * 2;
//        final int quadrupleASquared = aSquared * 4;
//        final int quadrupleBSquared = bSquared * 4;
//        final int doubleBSquared = bSquared * 2;
//        double mod = (double) width / (double) height;
//        int delta = doubleASquared * ((y - 1) * y) + aSquared + doubleBSquared * (1 - aSquared);
//        while (aSquared * y >= bSquared * x) {
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x + x0, y + y0, mod)) {
//                getPixelDrawer().setPixel(x + x0, y + y0, color);
//            }
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x + x0, y0 - y, mod)) {
//                getPixelDrawer().setPixel(x + x0, y0 - y, color);
//            }
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x0 - x, y + y0, mod)) {
//                getPixelDrawer().setPixel(x0 - x, y + y0, color);
//            }
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x0 - x, y0 - y, mod)) {
//                getPixelDrawer().setPixel(x0 - x, y0 - y, color);
//            }
//
//            if (delta >= 0) {
//                y--;
//                delta -= quadrupleASquared * (y);
//            }
//            delta += doubleBSquared * (3 + x * 2);
//            x++;
//        }
//        delta = doubleBSquared * (x + 1) * x + doubleASquared * (y * (y - 2) + 1) + (1 - doubleASquared) * bSquared;
//        while (y + 1 > 0) {
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x + x0, y + y0, mod)) {
//                getPixelDrawer().setPixel(x + x0, y + y0, color);
//            }
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x + x0, y0 - y, mod)) {
//                getPixelDrawer().setPixel(x + x0, y0 - y, color);
//            }
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x0 - x, y + y0, mod)) {
//                getPixelDrawer().setPixel(x0 - x, y + y0, color);
//            }
//            if (isValidAngle(startAngle, sweepAngle, x0, y0, x0 - x, y0 - y, mod)) {
//                getPixelDrawer().setPixel(x0 - x, y0 - y, color);
//            }
//            if (delta <= 0) {
//                x++;
//                delta += quadrupleBSquared * x;
//            }
//            y--;
//            delta += doubleASquared * (1 - y * 2);
//        }
//    }
//
//    private int getAngle(int centerX, int centerY, int pointX, int pointY, double mod) {
//        int x = pointX - centerX;
//        int y = pointY - centerY;
//        y *= mod;
//
//        int a = (int) (Math.atan2(x, y) * 180 / Math.PI) - 90;
//
//        if ((x < 0) && (y < 0)) {
//            a += 360;
//        }
//
//        return a;
//    }
//
//    private boolean isValidAngle(int angle, int startAngle, int sweepAngle) {
//        return angle >= startAngle && angle <= startAngle + sweepAngle;
//    }
//
//    private boolean isValidAngle(int startAngle, int sweepAngle, int centerX, int centerY, int pointX, int pointY, double mod) {
//        return isValidAngle(getAngle(centerX, centerY, pointX, pointY, mod), startAngle, sweepAngle);
//    }
//
//    public PixelDrawer getPixelDrawer() {
//        return pd;
//    }
//
//    public void setPixelDrawer(PixelDrawer pixelDrawer) {
//        this.pd = pixelDrawer;
//    }
//
//    @Override
//    public void drawArc(int x, int y, int width, int height, double startAngle, double arcAngle, Color c) {
//        height /= 2;
//        width /= 2;
//        startAngle *= 180 / Math.PI+1;
//        arcAngle *= 180 / Math.PI + 1;
//        drawArc(x + width, y + height, width, height, (int) startAngle, (int) arcAngle, c);
//    }
//
//
//}

