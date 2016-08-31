/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.tools;

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
import org.openbase.planetsudo.jp.JPStrategyJar;
import org.openbase.planetsudo.jp.JPStrategyModuleDirectory;
import java.io.File;
import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FileUtils;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.slf4j.LoggerFactory;
import org.openbase.jul.exception.InstantiationException;
import org.openbase.jul.exception.NotAvailableException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.schedule.GlobalExecutionService;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public final class JarController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JarController.class);

    private final Object JAR_LOCK = new Object();
    private static JarController instance;
    private byte[] jarBytes;
    private final File jarFile;
    private final File projectDirectory;

    private JarController() throws InstantiationException {
        try {
            this.jarFile = JPService.getProperty(JPStrategyJar.class).getValue();
            this.projectDirectory = JPService.getProperty(JPStrategyModuleDirectory.class).getValue();
            this.jarBytes = null;
        } catch (JPNotAvailableException ex) {
            throw new InstantiationException(this, ex);
        }
    }

    public static synchronized JarController getInstance() throws NotAvailableException {
        if (instance == null) {
            try {
                instance = new JarController();
            } catch (InstantiationException ex) {
                throw new NotAvailableException("JarController", ex);
            }
        }
        return instance;
    }

    public byte[] getJarAndBuild() throws CouldNotPerformException {
        boolean freshBuild = false;
        synchronized (JAR_LOCK) {
            if (!jarFile.exists() || jarBytes == null) {
                buildJar();
                freshBuild = true;
            }

            final byte[] jarBytesCopy = new byte[jarBytes.length];
            System.arraycopy(jarBytes, 0, jarBytesCopy, 0, jarBytes.length);

            if (!freshBuild) {
                buildJarLater();
            }
            return jarBytesCopy;
        }
    }

    private void buildJarLater() {
        GlobalExecutionService.execute(() -> {
            try {
                buildJar();
            } catch (CouldNotPerformException ex) {
                ExceptionPrinter.printHistory(new CouldNotPerformException("Could not build jar later!", ex), LOGGER);
            }
        });
    }

    private void buildJar() throws CouldNotPerformException {
        buildJar(false);
    }

    private void buildJar(final boolean clean) throws CouldNotPerformException {
        synchronized (JAR_LOCK) {
            SenactController.getInstance().setServerMode(SenactController.ServerMode.Compile);
            try {
                final CommandLine cmdLine = new CommandLine("mvn");
                if (clean) {
                    cmdLine.addArgument("clean");
                }
                cmdLine.addArgument("package");
                final DefaultExecutor executor = new DefaultExecutor();
                final ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
                executor.setWorkingDirectory(projectDirectory);
                executor.setWatchdog(watchdog);
                executor.setExitValue(0);
                executor.getStreamHandler().start();
                final int result = executor.execute(cmdLine);

                if (result != 0) {
                    if (clean) {
                        throw new CouldNotPerformException("Could not package!");
                    } else {
                        buildJar(true);
                        return;
                    }
                }

                if (!jarFile.exists()) {
                    throw new CouldNotPerformException("Resulting Jar[" + jarFile + "] is missing");
                }

                jarBytes = FileUtils.readFileToByteArray(jarFile);
                SenactController.getInstance().setServerMode(SenactController.ServerMode.NewPackage);
            } catch (CouldNotPerformException | IOException ex) {
                SenactController.getInstance().setServerMode(SenactController.ServerMode.Error);
                throw new CouldNotPerformException("Could not build jar!", ex);
            }
        }
    }
}
