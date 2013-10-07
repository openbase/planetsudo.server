/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main;

import de.dc.planetsudo.main.command.SetLevelPathCommand;
import de.dc.planetsudo.main.command.SetServerPort;
import de.dc.planetsudo.main.command.SetStrategyPathCommand;
import de.dc.planetsudo.main.command.SetTeamPathCommand;
import de.dc.planetsudo.net.PlanetSudoServer;
import de.dc.util.logging.Logger;
import de.dc.util.view.Set2dDebug;
import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.command.DebugModeFlag;
import de.unibi.agai.clparser.command.SetPrefix;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class StartServer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		CLParser.setProgramName("PlanetSudoServer");
		CLParser.registerCommand(SetPrefix.class);
		CLParser.registerCommand(SetTeamPathCommand.class);
		CLParser.registerCommand(SetStrategyPathCommand.class);
		CLParser.registerCommand(DebugModeFlag.class);
		CLParser.registerCommand(Set2dDebug.class);
		CLParser.registerCommand(SetLevelPathCommand.class);
		CLParser.registerCommand(SetServerPort.class);
		Logger.setDebugMode(false);
		CLParser.analyseAndExitOnError(args);

		new PlanetSudoServer();
	}
}
