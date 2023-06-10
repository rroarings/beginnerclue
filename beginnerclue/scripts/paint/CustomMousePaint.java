package scripts.paint;

import org.tribot.script.sdk.painting.MousePaint;

import java.awt.*;
import java.awt.geom.Arc2D;

public class CustomMousePaint implements MousePaint {
    Color primary = Color.red;
    Color secondary = Color.orange;
    int size = 24;
    private long angle;
  
    @Override
    public void paintMouse(Graphics g, Point mousePos, Point dragPos) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(primary);
        g2.drawLine(mousePos.x -3, mousePos.y -3, mousePos.x+2, mousePos.y+2);
        g2.drawLine(mousePos.x-3, mousePos.y+2, mousePos.x+2, mousePos.y-3);
        g2.setColor(secondary);
        g2.rotate(Math.toRadians(angle+=12), mousePos.x, mousePos.y);
        g2.draw(new Arc2D.Double(mousePos.x-12, mousePos.y-12, size, size, 330, 180, Arc2D.OPEN));
        g2.draw(new Arc2D.Double(mousePos.x-12, mousePos.y-12, size, size, 151, 60, Arc2D.OPEN));
    }

}