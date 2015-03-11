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
public class SoldierStategy extends AbstractStrategy {

	public SoldierStategy() {
	}
	public SoldierStategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Gib hier an wie viele Agenten dein Team besitzen soll.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 6;
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
			protected void action() {
				agent.go();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(10000, "Avoid Wall") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(100, "ResourceSee") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource();
			}
			@ Override
			protected void action() {
				agent.goToResource();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(200, "ResourceAufnehmen") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource();
			}
			@ Override
			protected void action() {
				agent.pickupResource();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(300, "ResourceTragen") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(400, "ResourceAbliefern") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership()&& agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(398, "MothershipBetanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 30;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(399, "MothershipBetanken1") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership()&& agent.getFuelInPercent() <= 80;
			}
			@ Override
			protected void action() {
				agent.orderFuel(90);
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(5000, "GegnerSee") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(600, "Verteidigen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(450, "mutterschiffattack") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "mutterschiffdefend") {
			@ Override
			protected boolean constraint() {
				return mothership.isBurning();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
		
                                    
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9999, "Ende") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
		
                                    
			}
		});
		
		
	}
}
