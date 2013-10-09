/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main;

import de.dc.planetsudo.main.command.SetBuildTargetDirectory;
import de.dc.planetsudo.main.command.SetLevelPathCommand;
import de.dc.planetsudo.main.command.SetServerPort;
import de.dc.planetsudo.main.command.SetStrategyModuleDirectory;
import de.dc.planetsudo.main.command.SetStrategyPathCommand;
import de.dc.planetsudo.main.command.SetStrategyServerSourceDirectory;
import de.dc.planetsudo.main.command.SetTeamPathCommand;
import de.dc.planetsudo.net.PlanetSudoServer;
import de.dc.planetsudo.tools.SenactController;
import de.dc.util.logging.Logger;
import de.dc.util.view.Set2dDebug;
import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.command.DebugModeFlag;
import de.unibi.agai.clparser.command.SetPrefix;
import java.io.File;

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
		CLParser.registerCommand(SetPrefix.class, new File("."));
		CLParser.registerCommand(SetTeamPathCommand.class);
		CLParser.registerCommand(SetStrategyPathCommand.class);
		CLParser.registerCommand(DebugModeFlag.class);
		CLParser.registerCommand(SetStrategyModuleDirectory.class);
		CLParser.registerCommand(SetBuildTargetDirectory.class);
		CLParser.registerCommand(SetStrategyServerSourceDirectory.class);
		CLParser.registerCommand(SetServerPort.class);
		Logger.setDebugMode(false);
		CLParser.analyseAndExitOnError(args);
		SenactController.getInstance();
		new PlanetSudoServer();
	}
}
