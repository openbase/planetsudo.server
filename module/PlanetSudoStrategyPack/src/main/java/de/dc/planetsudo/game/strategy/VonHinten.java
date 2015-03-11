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
public class VonHinten extends AbstractStrategy {

	public VonHinten() {
	}
	public VonHinten(AgentInterface a) {
		super(a);
	}

	/**
	 * Gib hier an wie viele Agenten dein Team besitzen soll.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 4;
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
				agent.turnLeft(111);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(0, "Just go...") {
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
		createRule(new Rule(1000, "See and Go") {
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
		createRule(new Rule(2000, "PickUp") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource()&&!agent.isTouchingResource(ResourceType.Mine);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
                        }
		});
		//-------------------------------------------->
		createRule(new Rule(3000, "BacktoMothership") {
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
		createRule(new Rule(4000, "DeliverResource") {
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
		createRule(new Rule(5000, "Full") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership()&& agent.getFuelInPercent() <=45;
                        }
			@ Override
			protected void action() {
				agent.orderFuel(80);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9998, "Helping Friend") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(150);
                                
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9999, "Commanderback") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(2501, "Angreifen") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryAgent() ;                                      
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryAgent();
			}
		//-------------------------------------------->
                });        
		createRule(new Rule(2500, "Angreife2") {
			@ Override
			protected boolean constraint() {
				return  agent.seeAdversaryMothership();                                    
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
			}
		//-------------------------------------------->
                });
		createRule(new Rule(500, "Hilfe") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.orderSupport();
			}
		//-------------------------------------------->
//                });
//		createRule(new Rule(501, "Hilfe 2") {
//			@ Override
//			protected boolean constraint() {
//				return agent.isSupportOrdered();
//			}
//			@ Override
//			protected void action() {
//			          agent.goToSupportAgent();
//			}
                //-------------------------------------------->
                });
		createRule(new Rule(4001, "Mine legen") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && agent.isTouchingResource(ResourceType.ExtremPoint);
			}
			@ Override
			protected void action() {
				agent.deployMine();
			}
		});
	}
}
