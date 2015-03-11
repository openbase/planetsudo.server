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
public class FloschiStrategy extends AbstractStrategy {

	public FloschiStrategy() {
	}
	public FloschiStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Gib hier an wie viele Agenten dein Team besitzen soll.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 3;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(6000, "Verteidigen") {
			@ Override
			protected boolean constraint() {
				return mothership.isBurning() && !agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(5500, "Reparieren") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && !agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
                
		//-------------------------------------------->
		createRule(new Rule(5000, "Umkehren an Wand") {
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
		createRule(new Rule(4000, "Ressource übergeben") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
                                agent.turnAround();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(3105, "Ressource loswerden und Agent angreifen") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && (agent.getFuelInPercent() >= 30) && agent.isUnderAttack();
			}
			@ Override
			protected void action() {
                                agent.releaseResource();
                                agent.turnLeft(110);
				agent.fightWithAdversaryAgent(); 
			}
		});
		//-------------------------------------------->
		createRule(new Rule(3100, "Ressource bringen") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && (agent.getFuelInPercent() >= 30);
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(3000, "Ressource bringen") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && (agent.getFuelInPercent()<30) && mothership.hasFuel();
			}
			@ Override
			protected void action() {
				agent.releaseResource();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(2500, "Tanken") {
			@ Override
			protected boolean constraint() {
				return (agent.getFuelInPercent()<=30) && agent.isAtMothership() && mothership.hasFuel();
			}
			@ Override
			protected void action() {
				agent.orderFuel(70);
			}
		});
                //-------------------------------------------->
                
		createRule(new Rule(2490, "Tanken fahren") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<=30 && !agent.isTouchingResource(ResourceType.ExtraAgentFuel) && mothership.hasFuel();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
                //-------------------------------------------->
                
		createRule(new Rule(2480, "Treibstoff einsammeln") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(ResourceType.ExtraAgentFuel);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(2300, "Agent angreifen") {
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
		createRule(new Rule(2220, "Return to base") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                        }
		});
                //-------------------------------------------->
		createRule(new Rule(2210, "Mutterschiff markieren") {
			@ Override
			protected boolean constraint() {
				return false && agent.seeAdversaryMothership() && !agent.seeMarker();
			}
			@ Override
			protected void action() {
				agent.deployMarker();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(2205, "Mine an Mutterschiff legen") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && !agent.seeMarker();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
                                agent.deployMine();
                                agent.deployMarker();
			}
		});
                //-------------------------------------------->
		createRule(new Rule(2200, "Mutterschiff angreifen") {
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
		createRule(new Rule(2000, "Ressource einsammeln") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && !agent.isTouchingResource(ResourceType.Mine) && !agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "Ressource gesichtet") {
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
		createRule(new Rule(0, "Just go") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
				agent.go();
			}
		});
		
	}
}
