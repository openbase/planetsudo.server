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
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class Blitzkrieg extends AbstractStrategy {

	public Blitzkrieg() {
	}
	public Blitzkrieg(AgentInterface a) {
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
		createRule(new Rule(10000, "Avoid Wall") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom(270);
                                
			}
                  
		});
                //-------------------------------------------->      
		//-------------------------------------------->
		createRule(new Rule(10, "Go") {
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
		createRule(new Rule(20, "Get Resource") {
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
		//-------------------------------------------->
		createRule(new Rule(20, "Take Resource") {
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
		createRule(new Rule(30, "Carrying") {
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
		createRule(new Rule(40, "Release") {
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
		
		//-------------------------------------------->
		createRule(new Rule(300, "Get Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 80;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                
			}
                  
		});
                //-------------------------------------------->      
		//-------------------------------------------->
		createRule(new Rule(301, "Get Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() <= 80;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
                                
			}
                  
		});
                //-------------------------------------------->      
		
		
		createRule(new Rule(100, "Spend Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(350);
                                
			}
                  
		});
                //-------------------------------------------->      
		
                //-------------------------------------------->      
		
		
		createRule(new Rule(110, "Fire in the Hole") {
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
		
		
		
		
		createRule(new Rule(80, "Fire in the Hole") {
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
		
		
		createRule(new Rule(150, "Repair Home") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                
			}
                  
		});
                //-------------------------------------------->      
		
		
		createRule(new Rule(150, "Repair Home") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
                                
			}
                  
		});
                //-------------------------------------------->      
                //-------------------------------------------->      
		
		
		createRule(new Rule(300, "Repair Home") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander()&& mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                
			}
                  
		});
                //-------------------------------------------->      
		
		
		createRule(new Rule(300, "Repair Home") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander()&& mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
                                
			}
                  
		});
                //-------------------------------------------->      
		
                //-------------------------------------------->      
		
		
		createRule(new Rule(310, "Surrender") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack()&& agent.getFuelInPercent() <= 40;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
                                
			}
                  
		});
                //-------------------------------------------->      
		createRule(new Rule(320, "Mine") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack()&& agent.getFuelInPercent() <= 75;
			}
			@ Override
			protected void action() {
				agent.deployMine();
                                
			}
                  
		});
                //-------------------------------------------->      
		createRule(new Rule(320, "Mine") {
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
		
		
	
                      
		
		
	
		
	}
}
