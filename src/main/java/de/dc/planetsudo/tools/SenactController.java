/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.tools;

import de.dc.util.exceptions.CouldNotPerformException;
import de.dc.util.logging.Logger;
import de.dc.util.senact.SenactClientConnection;
import de.dc.util.senact.SenactInstanceInterface;
import de.dc.util.senact.SenactServerService;
import de.dc.util.senact.commands.BuzzerCommand;
import de.dc.util.senact.commands.BuzzerCommand.Sound;
import de.dc.util.senact.commands.MotionDetectorCommand;
import de.dc.util.senact.commands.MotionDetectorCommand.MotionState;
import de.dc.util.senact.commands.RGBLightCommand;
import java.awt.Color;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class SenactController implements SenactInstanceInterface {

	private Color S_GREEN = new Color(0, 50, 0);
	private Color S_BLUE = new Color(0, 0, 100);
	private Color S_RED = new Color(100, 0, 0);

	public enum ServerMode {
		Compile,
		Error,
		NewPackage;
	}

	private SenactServerService senactServerService;
	private SenactClientConnection senactClientConnection;
	private int lightIntensity = 0;
	private MotionState motionState = MotionState.Unknown;


	private static SenactController instance;

	public synchronized static SenactController getInstance() {
		if(instance == null) {
			instance = new SenactController();
		}
		return instance;
	}

	public void setServerMode(final ServerMode mode) {
		switch(mode) {
			case NewPackage:
				setColor(S_BLUE);
				break;
			case Compile:
				setColor(S_GREEN);
				playSound(Sound.Monkey);
				break;
			case Error:
				setColor(S_RED);
				playSound(Sound.S3);
				break;
		}
	}

	private SenactController() {
		senactServerService = new SenactServerService(this);
	}

	public void setSenactClientConnection(SenactClientConnection senactClientConnection) {
		this.senactClientConnection = senactClientConnection;
	}

	@Override
	public void setMotionState(MotionDetectorCommand.MotionState motionState) {
		this.motionState = motionState;
	}

		@Override
	public void setLightIntensity(int intensity) {
		this.lightIntensity = intensity;
	}

	public void setColor(final Color color) {
		try {
			if(senactClientConnection == null) {
				throw new CouldNotPerformException("Senact not conneced!");
			}
			senactClientConnection.sendCommand(new RGBLightCommand(color));
		} catch (CouldNotPerformException ex) {
			Logger.warn(this, "Could not control senact!", ex);
		}
	}

	public void playSound(final Sound sound) {
		try {
			if(senactClientConnection == null) {
				throw new CouldNotPerformException("Senact not conneced!");
			}
			senactClientConnection.sendCommand(new BuzzerCommand(sound));
		} catch (CouldNotPerformException ex) {
			Logger.warn(this, "Could not control senact!", ex);
		}
	}
}
