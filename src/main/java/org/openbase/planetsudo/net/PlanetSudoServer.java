/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.net;

/*-
 * #%L
 * PlanetSudo Server
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import org.openbase.jps.core.JPService;
import org.openbase.planetsudo.game.LevelReciver;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.planetsudo.jp.JPServerPort;
import org.openbase.planetsudo.tools.JarController;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class PlanetSudoServer implements Runnable {
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlanetSudoServer.class);
    
    private ServerSocket serverSocked;
    
    public PlanetSudoServer() {
        setupAndRunConnection();
        try {
            LOGGER.info("start initial build...");
            JarController.getInstance().getJarAndBuild();
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Initial build failed!", ex), LOGGER);
        }
    }
    
    private void setupAndRunConnection() {
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        LOGGER.info("Setup connection...");
        
        while (true) {
            while (true) {
                try {
                    serverSocked = new ServerSocket(JPService.getProperty(JPServerPort.class).getValue());
                    break;
                } catch (IOException | JPNotAvailableException ex) {
                    LOGGER.error("Could not bind Port! Try again in 10 Sec..", ex);
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(LevelReciver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            LOGGER.info("Server online.");
            
            try {
                while (true) {
                    LOGGER.info("Wait for client...");
                    new PlanetSudoClientHandler(serverSocked.accept());
                }
            } catch (IOException ex) {
                LOGGER.warn("Binding lost!", ex);
            } finally {
                if (serverSocked != null) {
                    try {
                        serverSocked.close();
                    } catch (IOException ex) {
                        LOGGER.debug("Connection broken!", ex);
                    }
                }
            }
            LOGGER.info("Rebind port...");
        }
    }
}
