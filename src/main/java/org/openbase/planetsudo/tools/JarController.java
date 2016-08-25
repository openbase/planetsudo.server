/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.tools;

import org.openbase.jps.core.JPService;
import org.openbase.planetsudo.main.command.SetStrategyJar;
import org.openbase.planetsudo.main.command.SetStrategyModuleDirectory;
import org.openbase.util.exceptions.CouldNotPerformException;
import org.openbase.util.logging.Logger;
import java.io.File;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public final class JarController {

	private final Object JAR_LOCK = new Object();
	private static JarController instance;
	private byte[] jarBytes;
	private final File jarFile;
	private final File projectDirectory;

	private JarController() {
		this.jarFile = JPService.getProperty(SetStrategyJar.class).getValue();
		this.projectDirectory = JPService.getProperty(SetStrategyModuleDirectory.class).getValue();
		this.jarBytes = null;
	}

	public static synchronized JarController getInstance() {
		if (instance == null) {
			instance = new JarController();
		}
		return instance;
	}

	public byte[] getJarAndBuild() throws CouldNotPerformException {
		boolean freshBuild = false;
		synchronized (JAR_LOCK) {
			if (!jarFile.exists() || jarBytes == null) {
				buildJar();
				freshBuild = true;
			}

			final byte[] jarBytesCopy = new byte[jarBytes.length];
			System.arraycopy(jarBytes, 0, jarBytesCopy, 0, jarBytes.length);

			if(!freshBuild) {
				buildJarLater();
			}
			return jarBytesCopy;
		}
	}

	private void buildJarLater() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					buildJar();
				} catch (CouldNotPerformException ex) {
					Logger.error(this, "Could not build jar later!");
				}
			}
		}, "JarBuilder").start();
	}

	private void buildJar() throws CouldNotPerformException {
		buildJar(false);
	}

	private void buildJar(final boolean clean) throws CouldNotPerformException {
		synchronized (JAR_LOCK) {
			SenactController.getInstance().setServerMode(SenactController.ServerMode.Compile);
			try {
				final CommandLine cmdLine = new CommandLine("mvn");
				if (clean) {
					cmdLine.addArgument("clean");
				}
				cmdLine.addArgument("package");
				final DefaultExecutor executor = new DefaultExecutor();
				final ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
				executor.setWorkingDirectory(projectDirectory);
				executor.setWatchdog(watchdog);
				executor.setExitValue(0);
				executor.getStreamHandler().start();
				final int result = executor.execute(cmdLine);

				if (result != 0) {
					if(clean) {
						throw new CouldNotPerformException("Could not package!");
					} else {
						buildJar(true);
						return;
					}
				}

				if(!jarFile.exists()) {
					throw new CouldNotPerformException("Resulting Jar["+jarFile+"] is missing");
				}

				jarBytes = FileUtils.readFileToByteArray(jarFile);
				SenactController.getInstance().setServerMode(SenactController.ServerMode.NewPackage);
			} catch (Exception ex) {
				SenactController.getInstance().setServerMode(SenactController.ServerMode.Error);
				throw new CouldNotPerformException("Could not build jar!", ex);
			}
		}
	}
}
