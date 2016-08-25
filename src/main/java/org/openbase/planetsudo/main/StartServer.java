/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.main;

import org.openbase.jps.core.JPService;
import org.openbase.jps.preset.JPDebugMode;
import org.openbase.jps.preset.JPPrefix;
import org.openbase.planetsudo.main.command.SetBuildTargetDirectory;
import org.openbase.planetsudo.jp.SetServerPort;
import org.openbase.planetsudo.main.command.SetStrategyModuleDirectory;
import org.openbase.planetsudo.jp.SetStrategyPathCommand;
import org.openbase.planetsudo.main.command.SetStrategyServerSourceDirectory;
import org.openbase.planetsudo.jp.SetTeamPathCommand;
import org.openbase.planetsudo.net.PlanetSudoServer;
import org.openbase.planetsudo.tools.SenactController;
import org.openbase.util.logging.Logger;
import java.io.File;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
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
