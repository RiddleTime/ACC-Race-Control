/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.app.livetiming.timing.tablemodels;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static racecontrol.client.extension.statistics.CarProperties.BEST_SECTOR_ONE;
import static racecontrol.client.extension.statistics.CarProperties.BEST_SECTOR_THREE;
import static racecontrol.client.extension.statistics.CarProperties.BEST_SECTOR_TWO;
import static racecontrol.client.extension.statistics.CarProperties.CURRENT_SECTOR_ONE;
import static racecontrol.client.extension.statistics.CarProperties.CURRENT_SECTOR_THREE;
import static racecontrol.client.extension.statistics.CarProperties.CURRENT_SECTOR_TWO;
import static racecontrol.client.extension.statistics.CarProperties.SESSION_BEST_SECTOR_ONE;
import static racecontrol.client.extension.statistics.CarProperties.SESSION_BEST_SECTOR_THREE;
import static racecontrol.client.extension.statistics.CarProperties.SESSION_BEST_SECTOR_TWO;
import racecontrol.client.extension.statistics.CarStatistics;
import racecontrol.gui.LookAndFeel;
import static racecontrol.gui.LookAndFeel.COLOR_PURPLE;
import static racecontrol.gui.LookAndFeel.COLOR_RACE;
import static racecontrol.gui.LookAndFeel.COLOR_WHITE;
import racecontrol.gui.lpui.LPTable;
import racecontrol.gui.lpui.LPTableColumn;
import racecontrol.utility.TimeUtils;

/**
 *
 * @author Leonard
 */
public class QualifyingCurrentTableModel
        extends QualifyingBestTableModel {

    @Override
    public LPTableColumn[] getColumns() {
        return new LPTableColumn[]{
            positionColumn,
            nameColumn,
            pitColumn,
            carNumberColumn,
            new LPTableColumn("Lap")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> lapTimeRenderer(applet, context)),
            new LPTableColumn("Delta")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> deltaRenderer(applet, context)),
            new LPTableColumn("Best")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> bestLapRenderer(applet, context)),
            new LPTableColumn("Gap")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> gapRenderer(applet, context)),
            new LPTableColumn("S1")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> currentSectorOneRenderer(applet, context)),
            new LPTableColumn("S2")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> currentSectorTwoRenderer(applet, context)),
            new LPTableColumn("S3")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> currentSectorThreeRenderer(applet, context)),
            new LPTableColumn("Laps")
            .setMaxWidth(100)
            .setCellRenderer((applet, context) -> lapsRenderer(applet, context))
        };
    }

    @Override
    public String getName() {
        return "Qualifying Current";
    }

    protected void currentSectorOneRenderer(PApplet applet, LPTable.RenderContext context) {
        CarStatistics stats = (CarStatistics) context.object;
        int splitTime = stats.get(CURRENT_SECTOR_ONE);
        int bestSplitTime = stats.get(BEST_SECTOR_ONE);
        int sessionBestSplitTime = stats.get(SESSION_BEST_SECTOR_ONE);

        if (splitTime > 0) {
            String text = TimeUtils.asSeconds(splitTime);
            if (splitTime > 999999) {
                text = "999.999";
            }
            applet.fill(COLOR_WHITE);
            if (splitTime <= bestSplitTime) {
                applet.fill(COLOR_RACE);
            }
            if (splitTime <= sessionBestSplitTime) {
                applet.fill(COLOR_PURPLE);
            }
            applet.textAlign(CENTER, CENTER);
            applet.textFont(LookAndFeel.fontRegular());
            applet.text(text, context.width / 2, context.height / 2);
        }
    }

    protected void currentSectorTwoRenderer(PApplet applet, LPTable.RenderContext context) {
        CarStatistics stats = (CarStatistics) context.object;
        int splitTime = stats.get(CURRENT_SECTOR_TWO);
        int bestSplitTime = stats.get(BEST_SECTOR_TWO);
        int sessionBestSplitTime = stats.get(SESSION_BEST_SECTOR_TWO);

        if (splitTime > 0) {
            String text = TimeUtils.asSeconds(splitTime);
            if (splitTime > 999999) {
                text = "999.999";
            }
            applet.fill(COLOR_WHITE);
            if (splitTime <= bestSplitTime) {
                applet.fill(COLOR_RACE);
            }
            if (splitTime <= sessionBestSplitTime) {
                applet.fill(COLOR_PURPLE);
            }
            applet.textAlign(CENTER, CENTER);
            applet.textFont(LookAndFeel.fontRegular());
            applet.text(text, context.width / 2, context.height / 2);
        }
    }

    protected void currentSectorThreeRenderer(PApplet applet, LPTable.RenderContext context) {
        CarStatistics stats = (CarStatistics) context.object;
        int splitTime = stats.get(CURRENT_SECTOR_THREE);
        int bestSplitTime = stats.get(BEST_SECTOR_THREE);
        int sessionBestSplitTime = stats.get(SESSION_BEST_SECTOR_THREE);

        if (splitTime > 0) {
            String text = TimeUtils.asSeconds(splitTime);
            if (splitTime > 999999) {
                text = "999.999";
            }
            applet.fill(COLOR_WHITE);
            if (splitTime <= bestSplitTime) {
                applet.fill(COLOR_RACE);
            }
            if (splitTime <= sessionBestSplitTime) {
                applet.fill(COLOR_PURPLE);
            }
            applet.textAlign(CENTER, CENTER);
            applet.textFont(LookAndFeel.fontRegular());
            applet.text(text, context.width / 2, context.height / 2);
        }
    }
}
