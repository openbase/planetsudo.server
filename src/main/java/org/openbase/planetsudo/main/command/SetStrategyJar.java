/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.main.command;

import org.openbase.jps.core.JPService;
import org.openbase.jps.preset.AbstractJPFile;
import org.openbase.jps.tools.FileHandler;
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
