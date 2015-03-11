/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.net;

import de.citec.jps.core.JPService;
import de.dc.planetsudo.game.LevelReciver;
import de.dc.planetsudo.game.TeamData;
import de.dc.planetsudo.main.command.SetServerPort;
import de.dc.util.logging.Logger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.logging.Level;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class PlanetSudoServer implements Runnable {

	private ServerSocket serverSocked;

	public PlanetSudoServer() {
		setupAndRunConnection();
	}

	private void setupAndRunConnection() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		Logger.info(this, "Setup connection...");

		while (true) {
			while (true) {
				try {
					serverSocked = new ServerSocket(JPService.getProperty(SetServerPort.class).getValue());
					break;
				} catch (IOException ex) {
					Logger.error(this, "Could not bind Port! Try again in 10 Sec..", ex);
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException ex) {
					java.util.logging.Logger.getLogger(LevelReciver.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			Logger.info(this, "Server online.");

			try {
				while (true) {
					Logger.info(this, "Wait for client...");
					new PlanetSudoClientHandler(serverSocked.accept());
				}
			} catch (IOException ex) {
				Logger.warn(this, "Binding lost!", ex);
			} finally {
				if (serverSocked != null) {
					try {
						serverSocked.close();
					} catch (IOException ex) {
						Logger.debug(this, "Connection broken!", ex);
					}
				}
			}
			Logger.info(this, "Rebind port...");
		}
	}

	private void reciveTeam(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
//		File strategyFileSend = (File) in.readObject();
//		Logger.info(this, "####### Incomming class name is:" + strategyFileSend.getName());
		TeamData teamData = (TeamData) in.readObject();
		Logger.info(this, "Got Team" + teamData.getName());




//		int res = JOptionPane.showConfirmDialog(MainGUI.getInstance(),
//				"Jemand möchte mit dir seine KI tauschen. Stimmst du dem zu?",
//				"Team empfangen",
//				JOptionPane.YES_NO_OPTION);
//		Logger.debug(this, "Res is" + res);
//		if (res == JOptionPane.NO_OPTION) {
//			out.writeObject(new Boolean(false));
//			return;
//		} else {
//			out.writeObject(new Boolean(true));
//		}
//		Team defaultTeamTmp = MainGUI.getInstance().getConfigurationPanel().getDefaultTeam();
//		out.writeObject(new Boolean(defaultTeamTmp == null));
//		if (defaultTeamTmp == null) {
//			JOptionPane.showMessageDialog(MainGUI.getInstance(),
//					"Du hast kein DefaultTeam ausgesucht! Breche Transfer ab!",
//					"Cancel",
//					JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//
//
//		URL strategyURL = defaultTeamTmp.getStrategy().getResource(defaultTeamTmp.getStrategy().getSimpleName() + ".class");
//		try {
//			File strategyFile = new File(strategyURL.toURI());
//			out.writeObject(strategyFile);
//		} catch (URISyntaxException ex) {
//			java.util.logging.Logger.getLogger(AINetworkTransferMenu.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		try {
//			Logger.info(this, "########### ClassLocation:" + strategyURL.toURI().toString());
//		} catch (URISyntaxException ex) {
//			java.util.logging.Logger.getLogger(AINetworkTransferMenu.class.getName()).log(Level.SEVERE, null, ex);
//		}
//
//
//
//		out.writeObject(defaultTeamTmp);


		//MainGUI.getInstance().getConfigurationPanel().updateTeamList();
	}
}