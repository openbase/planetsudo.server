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

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.AgentInterface;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;


/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class Grimmy extends AbstractStrategy {

	public Grimmy() {
	}
	public Grimmy(AgentInterface a) {
		super(a);
	}

	/**
	 * Gib hier an wie viele Agenten dein Team besitzen soll.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 1;
	}

	@Override
	protected void loadRules() {
		createRule(new Rule(0, "Just Go") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.go();

			}
		});
		createRule(new Rule(1, "right") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.goRight(3);

			}
		});
		createRule(new Rule(100000, "wall") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnLeft(10);

			}
		});
		createRule(new Rule(900000, "war") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();

			}
		});
		createRule(new Rule(8500, "GMS") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();

			}
		});

		createRule(new Rule(7000, "") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.goToMothership();

			}
		});
		createRule(new Rule(2000000000, "gb") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon();
			}
			@ Override
			protected void action() {
				agent.goToMothership();

			}
		});
		createRule(new Rule(2000, "gb") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.repairMothership();

			}
		});
		createRule(new Rule(7800000, "gb") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <10;
			}
			@ Override
			protected void action() {
				agent.goToMothership();

			}
		});
		createRule(new Rule(7600, "gb") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuel() <10 ;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);

			}
		});

	}
}
