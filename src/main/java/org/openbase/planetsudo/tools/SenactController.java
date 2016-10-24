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
import org.openbase.bco.senact.api.SenactClientConnection;
import org.openbase.bco.senact.api.SenactInstance;
import org.openbase.bco.senact.api.SenactServerService;
import org.openbase.bco.senact.api.commands.BuzzerCommand;
import org.openbase.bco.senact.api.commands.BuzzerCommand.Sound;
import org.openbase.bco.senact.api.commands.ByeCommand;
import org.openbase.bco.senact.api.commands.MotionDetectorCommand;
import org.openbase.bco.senact.api.commands.MotionDetectorCommand.MotionState;
import org.openbase.bco.senact.api.commands.RGBLightCommand;
import org.openbase.bco.senact.api.data.Color;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.schedule.Timeout;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class SenactController implements SenactInstance {

    private static final Color S_BLACK = new Color(0, 0, 0);
    private static final Color S_GREEN = new Color(0, 255, 0);
    private static final Color S_BLUE = new Color(0, 0, 255);
    private static final Color S_RED = new Color(255, 0, 0);

    public enum ServerMode {
        Compile,
        Error,
        NewPackage;
    }

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SenactController.class);

    private final SenactServerService senactServerService;
    private SenactClientConnection senactClientConnection;
    private int lightIntensity = 0;
    private MotionState motionState = MotionState.Unknown;
    private final Timeout ledTimeout;

    private static SenactController instance;

    public synchronized static SenactController getInstance() {
        if (instance == null) {
            instance = new SenactController();
        }
        return instance;
    }

    public void setServerMode(final ServerMode mode) {
        switch (mode) {
            case NewPackage:
                setColor(S_GREEN);
                playSound(Sound.Monkey);
                break;
            case Compile:
                setColor(S_BLUE);
                playSound(Sound.Confirm);
                break;
            case Error:
                setColor(S_RED);
                playSound(Sound.S1);
                break;
        }
    }

    private SenactController() {
        this.senactServerService = new SenactServerService(this);
        this.ledTimeout = new Timeout(900000) {
            @Override
            public void expired() throws InterruptedException {
                switchLedOff();
            }
        };
        ledTimeout.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                switchLedOff();
                disconect();
            }
        });
    }
    
    public void switchLedOff() {
        setColor(S_BLACK);
    }

    @Override
    public void setSenactClientConnection(SenactClientConnection senactClientConnection) {
        this.senactClientConnection = senactClientConnection;
        playSound(Sound.S1);
//        Thread.sleep(50);
        playSound(Sound.S2);
//        Thread.sleep(05);
        playSound(Sound.S3);
//        Thread.sleep(50);
    }

    @Override
    public void setMotionState(MotionDetectorCommand.MotionState motionState) {
        this.motionState = motionState;
    }

    @Override
    public void setLightIntensity(int intensity) {
        this.lightIntensity = intensity;
    }

    public void setColor(final Color color) {
        try {
            if (senactClientConnection == null) {
                throw new CouldNotPerformException("Senact not conneced!");
            }
            senactClientConnection.sendCommand(new RGBLightCommand(color));

            if (!color.equals(S_BLACK)) {
                ledTimeout.restart();
            }
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not control senact!", ex), LOGGER);
        }
    }

    public void disconect() {
        try {
            if (senactClientConnection == null) {
                throw new CouldNotPerformException("Senact not conneced!");
            }
            senactClientConnection.sendCommand(new ByeCommand());
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not control senact!", ex), LOGGER);
        }
    }
    
    public void playSound(final Sound sound) {
        try {
            if (senactClientConnection == null) {
                throw new CouldNotPerformException("Senact not conneced!");
            }
            senactClientConnection.sendCommand(new BuzzerCommand(sound));
        } catch (CouldNotPerformException ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Could not control senact!", ex), LOGGER);
        }
    }
}
