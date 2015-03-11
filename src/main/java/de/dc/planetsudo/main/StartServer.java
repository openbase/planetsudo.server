/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.main;

import de.citec.jps.core.JPService;
import de.citec.jps.preset.JPDebugMode;
import de.citec.jps.preset.JPPrefix;
import de.dc.planetsudo.main.command.SetBuildTargetDirectory;
import de.dc.planetsudo.main.command.SetServerPort;
import de.dc.planetsudo.main.command.SetStrategyModuleDirectory;
import de.dc.planetsudo.main.command.SetStrategyPathCommand;
import de.dc.planetsudo.main.command.SetStrategyServerSourceDirectory;
import de.dc.planetsudo.main.command.SetTeamPathCommand;
import de.dc.planetsudo.net.PlanetSudoServer;
import de.dc.planetsudo.tools.SenactController;
import de.dc.util.logging.Logger;
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
		JPService.setApplicationName("PlanetSudoServer");
		JPService.registerProperty(JPPrefix.class, new File("."));
		JPService.registerProperty(SetTeamPathCommand.class);
		JPService.registerProperty(SetStrategyPathCommand.class);
		JPService.registerProperty(JPDebugMode.class);
		JPService.registerProperty(SetStrategyModuleDirectory.class);
		JPService.registerProperty(SetBuildTargetDirectory.class);
		JPService.registerProperty(SetStrategyServerSourceDirectory.class);
		JPService.registerProperty(SetServerPort.class);
		Logger.setDebugMode(false);
		JPService.parseAndExitOnError(args);
		SenactController.getInstance();
		new PlanetSudoServer();
	}
}
