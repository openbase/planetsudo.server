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
import de.dc.planetsudo.level.levelobjects.Mothership;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;


/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class Pumukl_Badass extends AbstractStrategy {

	public Pumukl_Badass() {
	}
	public Pumukl_Badass(AgentInterface a) {
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
		createRule(new Rule(8202, "ShipEmpty_AndRessAtThip") {
			@ Override
			protected boolean constraint() {
				return !mothership.hasFuel() && agent.isCarringResource() && agent.isAtMothership();			
                        }
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8201, "ShipEmpty_AndRess") {
			@ Override
			protected boolean constraint() {
				return !mothership.hasFuel() && agent.isCarringResource();			
                        }
			@ Override
			protected void action() {
				agent.goToMothership();
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8200, "ShipEmpty") {
			@ Override
			protected boolean constraint() {
				return !mothership.hasFuel()&& !agent.isCarringResource();			
                        }
			@ Override
			protected void action() {
				agent.go();
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8100, "Refuel") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 35;			
                        }
			@ Override
			protected void action() {
				agent.orderFuel (100);
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8000, "LowFuel") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 35;			
                        }
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(7110, "GiveRessShip") {
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
		createRule(new Rule(7100, "TakeRessHome") {
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
		createRule(new Rule(7000, "TakeRess") {
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
		createRule(new Rule(6000, "TurnToRess") {
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
	}
}
