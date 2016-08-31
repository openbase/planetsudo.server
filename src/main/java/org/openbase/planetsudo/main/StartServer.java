/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.main;

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
import org.openbase.jps.preset.JPDebugMode;
import org.openbase.jps.preset.JPPrefix;
import org.openbase.planetsudo.net.PlanetSudoServer;
import org.openbase.planetsudo.tools.SenactController;
import java.io.File;
import org.openbase.planetsudo.jp.JPBuildTargetDirectory;
import org.openbase.planetsudo.jp.JPServerPort;
import org.openbase.planetsudo.jp.JPStrategyModuleDirectory;
import org.openbase.planetsudo.jp.JPStrategyPath;
import org.openbase.planetsudo.jp.JPStrategyServerSourceDirectory;
import org.openbase.planetsudo.jp.JPTeamPath;

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
		JPService.registerProperty(JPTeamPath.class);
		JPService.registerProperty(JPStrategyPath.class);
		JPService.registerProperty(JPDebugMode.class);
		JPService.registerProperty(JPStrategyModuleDirectory.class);
		JPService.registerProperty(JPBuildTargetDirectory.class);
		JPService.registerProperty(JPStrategyServerSourceDirectory.class);
		JPService.registerProperty(JPServerPort.class);
		JPService.parseAndExitOnError(args);
		SenactController.getInstance();
		new PlanetSudoServer();
	}
}
