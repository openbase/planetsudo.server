package org.openbase.planetsudo.net;

/*-
 * #%L
 * PlanetSudo Server
 * %%
 * Copyright (C) 2009 - 2019 openbase.org
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
import org.openbase.planetsudo.game.Team;
import org.openbase.planetsudo.game.TeamData;
import org.openbase.planetsudo.jp.JPStrategyServerSourceDirectory;
import org.openbase.planetsudo.tools.JarController;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openbase.jps.exception.JPNotAvailableException;
import org.openbase.jul.exception.CouldNotPerformException;
import org.openbase.jul.exception.printer.ExceptionPrinter;
import org.openbase.jul.schedule.GlobalCachedExecutorService;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class PlanetSudoClientHandler implements Runnable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PlanetSudoClientHandler.class);

    private final Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    protected PlanetSudoClientHandler(final Socket socket) {
        this.socket = socket;
        LOGGER.info("Connecting to Client[" + socket.getInetAddress() + "]");
        GlobalCachedExecutorService.execute(this);
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            LOGGER.info("Connection established!");

            downloadDefaultTeam();
            downloadDefaultStrategy();
            uploadTeams();
            uploadStrategies();
            LOGGER.info("Sync finished.");
        } catch (Exception ex) {
            ExceptionPrinter.printHistory(new CouldNotPerformException("Connection error!", ex), LOGGER);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }

                if (socket != null) {
                    socket.close();
                }
                LOGGER.info("Connection closed.");
            } catch (Exception ex) {
                LOGGER.error("Connection Lost");
            }
        }
    }

    private void downloadDefaultTeam() throws IOException, ClassNotFoundException, CouldNotPerformException {
        LOGGER.info("Download default team!");
        final TeamData defaultTeam = (TeamData) in.readObject();
        Team.save(defaultTeam);
    }

    private void uploadTeams() throws CouldNotPerformException, IOException {
        LOGGER.info("Upload teams...");
        List<TeamData> teams = Team.loadAll();
        out.writeInt(teams.size());
        for (TeamData teamData : teams) {
            out.writeObject(teamData);
        }
    }

    private void downloadDefaultStrategy() throws CouldNotPerformException {
        try {
            LOGGER.info("Download default strategy!");
            final String sourceFileName = in.readUTF();
            final File sourceFile = new File(JPService.getProperty(JPStrategyServerSourceDirectory.class).getValue(), sourceFileName);
            final int fileByteLenght = in.readInt();
            final byte[] fileBytes = new byte[fileByteLenght];
            IOUtils.readFully(in, fileBytes);
            FileUtils.writeByteArrayToFile(sourceFile, fileBytes);
        } catch (JPNotAvailableException | IOException ex) {
            throw new CouldNotPerformException("COuld not download default strategy!", ex);
        }
    }

    private void uploadStrategies() throws CouldNotPerformException, IOException {
        LOGGER.info("Upload strategies...");
        final byte[] jarBytes = JarController.getInstance().getJarAndBuild();
        out.writeInt(jarBytes.length);
        IOUtils.write(jarBytes, out);
        out.flush();
    }
}
