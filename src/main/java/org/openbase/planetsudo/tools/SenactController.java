/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.tools;

import org.openbase.util.data.Color;
import org.openbase.util.exceptions.CouldNotPerformException;
import org.openbase.util.logging.Logger;
import org.openbase.util.senact.SenactClientConnection;
import org.openbase.util.senact.SenactInstanceInterface;
import org.openbase.util.senact.SenactServerService;
import org.openbase.util.senact.commands.BuzzerCommand;
import org.openbase.util.senact.commands.BuzzerCommand.Sound;
import org.openbase.util.senact.commands.MotionDetectorCommand;
import org.openbase.util.senact.commands.MotionDetectorCommand.MotionState;
import org.openbase.util.senact.commands.RGBLightCommand;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class SenactController implements SenactInstanceInterface {

	private Color S_GREEN = new Color(0, 255, 0);
	private Color S_BLUE = new Color(0, 0, 255);
	private Color S_RED = new Color(255, 0, 0);

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
				playSound(Sound.Confirm);
				break;
			case Error:
				setColor(S_RED);
				playSound(Sound.S1);
				break;
		}
	}



	private SenactController() {
		senactServerService = new SenactServerService(this);
	}

	public void setSenactClientConnection(SenactClientConnection senactClientConnection) {
		this.senactClientConnection = senactClientConnection;
		playSound(Sound.Monkey);
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
