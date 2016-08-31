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
public class Giani extends AbstractStrategy {

	public Giani() {
	}
	public Giani(AgentInterface a) {
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
		createRule(new Rule(10000, "vor einer wand") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom(180);
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9900, "mine") {
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
		createRule(new Rule(9800, "umdrehen") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}  
		});
		
		//-------------------------------------------->
		createRule(new Rule(9980, "mutterschiff heilen") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && mothership.isDamaged();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9981, "mutterschiff heilen") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && mothership.isDamaged() &&agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}  
		});
                //-------------------------------------------->
		createRule(new Rule(9757, "Helf Agent") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(1000);
			}  
		});
		//-----------
		//-------------------------------------------->
		createRule(new Rule(9756, "Sehe Lost Team Agent") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent() && mothership.needSomeoneSupport();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(300);
                                agent.cancelSupport();
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9754, "Brauche Support") {
			@ Override
			protected boolean constraint() {
				return !agent.isSupportOrdered() && agent.getFuelInPercent() <= 20;
			}
			@ Override
			protected void action() {
				agent.orderSupport();
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9755, "Helfe Agenten") {
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
		createRule(new Rule(9799, "tanken") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 20 && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9798, "tanken ") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 20;
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9750, "abgabe") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(9500, "zum mutter schiff") {
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
		createRule(new Rule(8000, "ressourcen") {
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
		createRule(new Rule(9000, "aufnehmen") {
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
		createRule(new Rule(9001, "aufnehmen") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(ResourceType.ExtraMothershipFuel);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}  
		});
		//-------------------------------------------->
		createRule(new Rule(8002, "mutterschiff fight") {
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
		createRule(new Rule(8900, "angriff") {
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
		createRule(new Rule(9999, "verteidigung") {
			@ Override
			protected boolean constraint() {
				return mothership.isBurning();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}  
		});
		
	}
}
