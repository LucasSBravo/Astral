package com.main;

import javax.swing.*;
import java.awt.*;

public class ShadowLabel extends JLabel {
    private Color shadowColor = Color.BLACK;
    private int shadowOffsetX = 2;
    private int shadowOffsetY = 2;

    public ShadowLabel(String text) {
        super(text);
        setFont(new Font("Serif", Font.BOLD, 48));
        setForeground(Color.WHITE);
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(false);
    }

    @Override
protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setFont(getFont());

    FontMetrics fm = g2.getFontMetrics();
    int textWidth = fm.stringWidth(getText());
    int textHeight = fm.getAscent();

    // Calcula posição X para centralizar o texto
    int x = (getWidth() - textWidth) / 2;
    // Calcula posição Y para centralizar verticalmente o texto
    int y = (getHeight() + textHeight) / 2 - shadowOffsetY;

    // Desenha sombra deslocada
    g2.setColor(shadowColor);
    g2.drawString(getText(), x + shadowOffsetX, y + shadowOffsetY);

    // Desenha texto principal
    g2.setColor(getForeground());
    g2.drawString(getText(), x, y);

    g2.dispose();
}
}
