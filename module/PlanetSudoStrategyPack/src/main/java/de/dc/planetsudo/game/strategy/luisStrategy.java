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
public class luisStrategy extends AbstractStrategy {

	public luisStrategy() {
	}
	public luisStrategy(AgentInterface a) {
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
		createRule(new Rule(5500, "mienen legen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.deployMine();
                              
			}
		});		
		//-------------------------------------------->
		createRule(new Rule(9000, "repair") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
                              
			}
		});		
		//-------------------------------------------->
		createRule(new Rule(2500, "schiessen 1") {
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
		createRule(new Rule(8000, "hilfe") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(100);
                              
			}
		});		
		//-------------------------------------------->

		createRule(new Rule(5000, "tanken") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() <= 25;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
                              
			}
		});		
		//-------------------------------------------->
		createRule(new Rule(4000, "zurück") {
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

		createRule(new Rule(5100, "abgeben") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
                              
			}
		});		
		//-------------------------------------------->
		createRule(new Rule(4000, "zurück") {
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
		createRule(new Rule(3000, "touching") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && !agent.isTouchingResource(ResourceType.Mine);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
                              
			}
		});	
		
		//-------------------------------------------->
		createRule(new Rule(2000, "sehen") {
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
		createRule(new Rule(1000, "nichgegenwand") {
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
	
		
	}
}
