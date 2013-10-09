/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.Agent;
import de.dc.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class LevelTwo extends AbstractStrategy {

	public LevelTwo() {
	}
	
	public LevelTwo(Agent a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 5;
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
				agent.goStraightAhead();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(20, "Search Resources") {
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
		createRule(new Rule(30, "PickUp Resource") {
			@ Override
			protected boolean constraint() {
				return agent.touchResource() && agent.touchResourceType() != Resource.ResourceType.Mine;
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(40, "Save Resource") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(60, "HelpLostAgent") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendFuelTeamAgent(300);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(90, "FightAgainstMothership") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
				agent.orderSupport();
			}
		});
		
		//-------------------------------------------->
		createRule(new Rule(100, "TurnToAdversaryAgent") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.turnLeft(60);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(110, "SaveMothership") {
			@ Override
			protected boolean constraint() {
				return agent.getMothership().isDamaged();
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(120, "RepaireMothership") {
			@ Override
			protected boolean constraint() {
				return agent.getMothership().isDamaged() && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(190, "FightAgainstAgent") {
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
		createRule(new Rule(200, "GoBackToMothership") {
			@ Override
			protected boolean constraint() {
				return agent.getFuel() < 300;
			}
			@ Override
			protected void action() {
				agent.moveOneStepInTheMothershipDirection();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(400, "OrderFuel") {
			@ Override
			protected boolean constraint() {
				return (agent.getFuel() < 300) && (agent.isAtMothership());
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(500, "OrderFuelDuringFight") {
			@ Override
			protected boolean constraint() {
				return (agent.getFuel() < 100) && (agent.seeAdversaryAgent()  || agent.isUnderAttack()) && agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderFuel(10);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(550, "Pass Resource") {
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
		createRule(new Rule(1000, "AvoidWall") {
			@ Override
			protected boolean constraint() {
				return agent.collisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRandom();
			}
		});
		//-------------------------------------------->
	}
}
