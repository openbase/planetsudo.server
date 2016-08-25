/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.main.command;

import org.openbase.jps.core.JPService;
import org.openbase.jps.preset.AbstractJPDirectory;
import org.openbase.jps.tools.FileHandler;
import java.io.File;


/**
 *
 * @author divine
 */
public class SetBuildTargetDirectory extends AbstractJPDirectory {
	public final static String[] COMMAND_IDENTIFIERS = {"--buildTarget"};
	public final static String[] ARGUMENT_IDENTIFIERS = {"PATH"};

	public SetBuildTargetDirectory() {
		super(COMMAND_IDENTIFIERS, ARGUMENT_IDENTIFIERS, FileHandler.ExistenceHandling.Must, FileHandler.AutoMode.On);
	}

	@Override
	public String getDescription() {
		return "Set the build target directory.";
	}

	@Override
	protected File getPropertyDefaultValue() {
		return new File(JPService.getProperty(SetStrategyModuleDirectory.class).getValue().getAbsolutePath()+"/target");
	}
}
