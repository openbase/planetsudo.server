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
import de.dc.planetsudo.level.levelobjects.BurningMothershipEffect;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;


/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class Bomber extends AbstractStrategy {

	public Bomber() {
	}
	public Bomber(AgentInterface a) {
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
		createRule(new Rule(10000, "wand") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnLeft(135);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(900, "sehen") {
			@ Override
			protected boolean constraint() {
				return agent.seeResource()&& !agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.goToResource();
			}
		});

		//-------------------------------------------->


		createRule(new Rule(800, "abgeben") {
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


		createRule(new Rule(700, "geh zum mutterschiff") {
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
		createRule(new Rule(950, "sammel") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource()&& !agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(0, "Just Go") {
			@ Override
			protected boolean constraint() {
				return true ;
			}
			@ Override
			protected void action() {
				agent.go();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(980, "tanken gehen") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<30;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(985, "tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent()<30 && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(1600, "agent kämpfen") {
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
		createRule(new Rule(1500, "mutter kämpfen") {
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
		createRule(new Rule(2050, "reparien") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged()|| mothership.isBurning() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}
                        });
		//-------------------------------------------->                        		
		createRule(new Rule(1400, "notwehr kämpfen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		});
                //--------------------------------------------> 
		createRule(new Rule(1800, "reagieren") {
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
		createRule(new Rule(1900, "hilfe") {
			@ Override
			protected boolean constraint() {
				return mothership.needSomeoneSupport();
			}
			@ Override
			protected void action() {
				agent.goToSupportAgent();
			}
		});                       		
                //--------------------------------------------> 
		createRule(new Rule(2000, "medic") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(200);
			}
		});                       		
		createRule(new Rule(2100, "finale") {
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

