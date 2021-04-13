/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.screen.extensions.logging;

import base.screen.extensions.AccClientExtension;
import base.ACCLiveTimingExtensionFactory;
import base.screen.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class LoggingExtensionFactory
        implements ACCLiveTimingExtensionFactory {

    @Override
    public String getName() {
        return "Logging extension";
    }

    @Override
    public AccClientExtension createExtension() {
        return new LoggingExtension();
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }

}
