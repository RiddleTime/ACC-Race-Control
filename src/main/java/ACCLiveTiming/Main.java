/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCLiveTiming;

import ACCLiveTiming.visualisation.Visualisation;
import ACCLiveTiming.client.BasicAccBroadcastingClient;
import ACCLiveTiming.extensions.debug.DebugExtension;
import ACCLiveTiming.extensions.incidents.IncidentExtension;
import ACCLiveTiming.extensions.laptimes.LapTimeExtension;
import ACCLiveTiming.extensions.livetiming.LiveTimingExtension;
import ACCLiveTiming.extensions.logging.LoggingExtension;
import ACCLiveTiming.utility.SpreadSheetService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import processing.core.PApplet;

/**
 *
 * @author Leonard
 */
public class Main {

    private static Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //set logging file.
        LogManager logManager = LogManager.getLogManager();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String logPath = System.getProperty("user.dir") + "/log/" + dateFormat.format(new Date()) + ".log";
            Properties prop = new Properties();
            prop.load(Main.class.getResourceAsStream("/logging.properties"));
            prop.put("java.util.logging.FileHandler.pattern", logPath);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            prop.store(out, "");
            logManager.readConfiguration(new ByteArrayInputStream(out.toByteArray()));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "An error happened while setting up the logger.", e);
        }

        startApp();
    }

    public static void startApp() {
        LOG.info("Starting");

        ConnectionDialog dialog = new ConnectionDialog();
        dialog.setVisible(true);
        if (!dialog.exitWithConnect()) {
            return;
        }

        //eable spreadSheet service.
        if (dialog.isSheetsAPIEnabled()) {
            SpreadSheetService.setEnable(dialog.isSheetsAPIEnabled());
            SpreadSheetService.setSpreadsheetURL(dialog.getSpreadsheetURL());

            if (!SpreadSheetService.isEnabled()) {
                JOptionPane.showMessageDialog(null, "Error enabling the Spreadsheet API");
            }
        }

        BasicAccBroadcastingClient client;
        try {
            client = new BasicAccBroadcastingClient(dialog.getDisplayName(),
                    dialog.getConnectionPassword(),
                    dialog.getCommandPassword(),
                    dialog.getUpdateInterval(),
                    dialog.getHostAddress(),
                    dialog.getPort());
        } catch (SocketException e) {
            LOG.log(Level.SEVERE, "Error while creating the broadcasting client.", e);
            return;
        }

        client.registerExtension(new LiveTimingExtension());
        client.registerExtension(new IncidentExtension());
        client.registerExtension(new LapTimeExtension());
        client.registerExtension(new LoggingExtension());
        client.registerExtension(new DebugExtension());

        try {
            client.sendRegisterRequest();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error while sending register request", e);
            return;
        }

        Visualisation v = new Visualisation(client, dialog.getUpdateInterval());
        String[] a = {"MAIN"};
        PApplet.runSketch(a, v);
    }

}
