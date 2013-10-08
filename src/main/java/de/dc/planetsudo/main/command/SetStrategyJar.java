/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.main.command;

import de.unibi.agai.clparser.CLParser;
import de.unibi.agai.clparser.command.AbstractCLFile;
import de.unibi.agai.tools.FileHandler;
import java.io.File;


/**
 *
 * @author divine
 */
public class SetStrategyJar extends AbstractCLFile {
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
	protected File getCommandDefaultValue() {
		return new File(CLParser.getAttribute(SetBuildTargetDirectory.class).getValue().getAbsolutePath()+"/strategy-2.0.0-SNAPSHOT.jar");
	}
}
