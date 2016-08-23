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
import de.dc.planetsudo.level.levelobjects.Resource;


/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class maxibStategy extends AbstractStrategy {

	public maxibStategy() {
	}
	public maxibStategy(AgentInterface a) {
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
		//-------------------------------------------->
		createRule(new Rule(0, "Just Go") {
			@ Override
			protected boolean constraint() {
				return true;
			}
			@ Override
			protected void action() {
                                agent.go();
                                agent.turnLeft(5);
                                
                                
			}
		});
		//-------------------------------------------->
		createRule(new Rule(20000, "wand ausweichen") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRight(5);
			}
		});
		
		//-------------------------------------------->
		createRule(new Rule(19810, "get fuel") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 80 && mothership.hasFuel();
			}
			@ Override
			protected void action() {
				agent.orderFuel(90);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(2, "fuelotw") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(Resource.ResourceType.ExtraAgentFuel);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
               
		//-------------------------------------------->
		createRule(new Rule(19800, "low fuel") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 40;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(19900, "mutterschiff verteidigen") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && !agent.isAtMothership() && !agent.isFighting();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                
			}
		});
		//-------------------------------------------->
		createRule(new Rule(14500, "mutterschiff verteidigen1") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && agent.isAtMothership() && !agent.isFighting();
			}
			@ Override
			protected void action() {
				agent.turnLeft(30);
                                agent.go();
                                agent.repairMothership();
                                
			}
		});
		
	
		
		
           
		//-------------------------------------------->
		createRule(new Rule(15000, "schiffe angreifen") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent() ;
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
                               
                                
                               
			}
		});
	
                
		
		//-------------------------------------------->
		createRule(new Rule(14000, "mutterschiff angreifen") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && !agent.seeAdversaryAgent();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
                             
			}
		});
		//-------------------------------------------->
		createRule(new Rule(14100, "mutterschiff marker") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && !agent.seeMarker() ;
			}
			@ Override
			protected void action() {
				agent.deployMarker();
                             
			}
		});
                
		//-------------------------------------------->
		createRule(new Rule(1, "marker finden") {
			@ Override
			protected boolean constraint() {
				return mothership.isMarkerDeployed() && !agent.seeMarker();
			}
			@ Override
			protected void action() {
				agent.goToMarker();
                             
			}
		});
                
		
		
		
		
		//-------------------------------------------->
		createRule(new Rule(19999, "end") {
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
