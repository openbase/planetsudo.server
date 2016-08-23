/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.citec.jps.core.JPService;
import de.citec.jps.preset.AbstractJPFile;
import de.citec.jps.tools.FileHandler;
import java.io.File;


/**
 *
 * @author divine
 */
public class SetStrategyJar extends AbstractJPFile {
	public final static String[] COMMAND_IDENTIFIERS = {"--buildTarget"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PATH"};

	public SetStrategyJar() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.Off);
	}

	@Override
	public String getDescription() {
		return "Set the strategy jar.";
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(JPService.getProperty(SetBuildTargetDirectory.class).getValue().getAbsolutePath()+"/strategy-2.0.0-SNAPSHOT.jar");
	}
}
