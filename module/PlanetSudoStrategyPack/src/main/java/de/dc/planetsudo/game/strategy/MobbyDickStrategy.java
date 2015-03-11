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
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class MobbyDickStrategy extends AbstractStrategy {

	public MobbyDickStrategy() {
	}
	public MobbyDickStrategy(AgentInterface a) {
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
		//-------------------------------------------->
		createRule(new Rule(10000, "Vermeide Wand") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnLeft(28);
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(500, "Recourcen finden") {
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
		createRule(new Rule(600, "Aufsammeln") {
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
		//-------------------------------------------->
		createRule(new Rule(700, "Zum Mutterschiff") {
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
		//-------------------------------------------->
		createRule(new Rule(750, "Abliefern") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && 
                                        agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(800, "Tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <=70;
			}                                        

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(810, "Tanken 2.0") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <=69 &&
                                        agent.isAtMothership();
			}                                        

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(900, "Andere Auftanken") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent()&&
                                        agent.hasFuel();
			}                                        

			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(35);
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(2000, "Kämpfen") {
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
		//-------------------------------------------->
		createRule(new Rule(1200, "Kämpfen 2.0") {
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
		//-------------------------------------------->
		createRule(new Rule(1225, "Supporten") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
                                        
			}                                        

			@ Override
			protected void action() {
				agent.orderSupport();
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(1250, "Muttership reparieren") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged();
                                        
			}                                        

			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
                //-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(1500, "Rückzug zum Mutterschiff") {
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
		//-------------------------------------------->
		createRule(new Rule(1400, "Minen Entdecken") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(Resource.ResourceType.Mine);
                                        
			}                                        

			@ Override
			protected void action() {
				agent.goLeft(30);
			}
		});
                //-------------------------------------------->
	}
	
}
