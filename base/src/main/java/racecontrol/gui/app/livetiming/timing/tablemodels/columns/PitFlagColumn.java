/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.app.livetiming.timing.tablemodels.columns;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import racecontrol.client.model.Car;
import racecontrol.gui.LookAndFeel;
import static racecontrol.gui.LookAndFeel.COLOR_BLACK;
import static racecontrol.gui.LookAndFeel.COLOR_WHITE;
import static racecontrol.gui.LookAndFeel.COLOR_YELLOW;
import static racecontrol.gui.LookAndFeel.LINE_HEIGHT;
import static racecontrol.gui.LookAndFeel.TEXT_SIZE;
import racecontrol.gui.lpui.table.LPTable;
import racecontrol.gui.lpui.table.LPTableColumn;

/**
 *
 * @author Leonard
 */
public class PitFlagColumn
        extends LPTableColumn {

    public PitFlagColumn() {
        super("");
        setMaxWidth((int) (LINE_HEIGHT * 0.4f));
        setMinWidth((int) (LINE_HEIGHT * 0.4f));
        setPriority(1000);
        setCellRenderer(this::pitRenderer);
    }

    protected void pitRenderer(PApplet applet, LPTable.RenderContext context) {
        if (!(context.object instanceof Car)) {
            return;
        }
        Car car = (Car) context.object;
        if (car.isCheckeredFlag) {
            applet.fill(COLOR_WHITE);
            applet.rect(1, 1, context.width - 2, context.height - 2);
            float w = (context.width - 2) / 2;
            float h = (context.height - 2) / 6;
            applet.fill(COLOR_BLACK);
            for (int i = 0; i < 6; i++) {
                applet.rect(1 + w * (i % 2), 1 + h * i, w, h);
            }
        } else if (car.isInPit()) {
            applet.noStroke();
            applet.fill(COLOR_WHITE);
            applet.rect(1, 1, context.width - 2, context.height - 2);
            applet.fill(0);
            applet.textAlign(CENTER, CENTER);
            applet.textSize(TEXT_SIZE * 0.6f);
            applet.text("P", context.width / 2f, context.height / 2f);
            applet.textFont(LookAndFeel.fontMedium());
            applet.textSize(LookAndFeel.TEXT_SIZE);
        } else if (car.isYellowFlag) {
            applet.fill(COLOR_YELLOW);
            applet.rect(1, 1, context.width - 2, context.height - 2);
        }
    }

}
