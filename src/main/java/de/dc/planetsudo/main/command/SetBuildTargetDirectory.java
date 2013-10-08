/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.command.AbstractCLDirectory;
import de.unibi.agai.tools.FileHandler;
import java.io.File;


/**
 *
 * @author divine
 */
public class SetBuildTargetDirectory extends AbstractCLDirectory {
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
	protected File getCommandDefaultValue() {
		return new File(CLParser.getAttribute(SetStrategyModuleDirectory.class).getValue().getAbsolutePath()+"/target");
	}
}
