/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.net;

import de.citec.jps.core.JPService;
import de.dc.planetsudo.game.Team;
import de.dc.planetsudo.game.TeamData;
import de.dc.planetsudo.main.command.SetStrategyServerSourceDirectory;
import de.dc.planetsudo.tools.JarController;
import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class PlanetSudoClientHandler implements Runnable {

	private final Socket socket;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;

	protected PlanetSudoClientHandler(final Socket socket) {
		this.socket = socket;
		Logger.info(this, "Connecting to Client[" + socket.getInetAddress() + "]");
		new Thread(this, "ClientConnectionHandler").start();
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			Logger.info(this, "Connection established!");

			downloadDefaultTeam();
			downloadDefaultStrategy();
			uploadTeams();
			uploadStrategies();
			Logger.info(this, "Sync finished.");
		} catch (Exception ex) {
			Logger.warn(this, "Connection error!", ex);
			ex.printStackTrace();
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
				Logger.info(this, "Connection closed.");
			} catch (Exception ex) {
				Logger.error(this, "Connection Lost");
			}
		}
	}		

	private void downloadDefaultTeam() throws IOException, ClassNotFoundException, CouldNotPerformException {
		Logger.info(this, "Download default team!");
		final TeamData defaultTeam = (TeamData) in.readObject();
		Team.save(defaultTeam);
	}

	private void uploadTeams() throws CouldNotPerformException, IOException {
		Logger.info(this, "Upload teams...");
		List<TeamData> teams = Team.loadAll();
		out.writeInt(teams.size());
		for(TeamData teamData : teams) {
			out.writeObject(teamData);
		}
	}

	private void downloadDefaultStrategy() throws IOException {
		Logger.info(this, "Download default strategy!");
		final String sourceFileName = in.readUTF();
		final File sourceFile = new File(JPService.getProperty(SetStrategyServerSourceDirectory.class).getValue(), sourceFileName);
		final int fileByteLenght = in.readInt();
		final byte[] fileBytes = new byte[fileByteLenght];
		IOUtils.readFully(in, fileBytes);
		FileUtils.writeByteArrayToFile(sourceFile, fileBytes);
	}

	private void uploadStrategies() throws CouldNotPerformException, IOException {
		Logger.info(this, "Upload strategies...");
		final byte[] jarBytes = JarController.getInstance().getJarAndBuild();
		out.writeInt(jarBytes.length);
		IOUtils.write(jarBytes, out);
		out.flush();
	}
}