/*
 * Copyright (C) 2013 DivineCorporation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openbase.planetsudo.game.strategy;

import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;


/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class EFPI extends AbstractStrategy {

	public EFPI() {
	}
	public EFPI(AgentInterface a) {
		super(a);
	}

	/**
	 * Gib hier an wie viele Agenten dein Team besitzen soll.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 7;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(0, "Just Go") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() { agent.go();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(10000, "dodgewall") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() { agent.turnRandom();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9698, "gotomarker") {
			@ Override
			protected boolean constraint() {
				return agent.isAlive();
			}
			@ Override
			protected void action() { agent.goToMarker();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9716, "fightmothership") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() { agent.fightWithAdversaryMothership();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9699, "krieg") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent()&& !agent.seeMarker();
			}
			@ Override
			protected void action() { agent.fightWithAdversaryAgent();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9715, "setmarker") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && !agent.seeMarker();
			}
			@ Override
			protected void action() { agent.deployMarker();
				
			}
		});
		//-------------------------------------------->
		createRule(new Rule(999999999, "s") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon();
			}
			@ Override
			protected void action() { agent.goToMothership();
				
			}
		});
		
	}
}
