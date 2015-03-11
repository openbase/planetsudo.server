/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.AgentInterface;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author divine
 */
public class DivineStrategy extends AbstractStrategy {

	public DivineStrategy() {
	}

	public DivineStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 *
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
				agent.go();

			}
		});
		//-------------------------------------------->
		createRule(new Rule(1000, "Discover") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander();
			}

			@ Override
			protected void action() {
				agent.goRight(4);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(2000, "See Resources") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.seeResource();
			}

			@ Override
			protected void action() {
				agent.goToResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(3000, "PickUp 1P Resource") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.isTouchingResource(ResourceType.Normal);
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(4000, "Go to Marker") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && mothership.isMarkerDeployed();
			}

			@ Override
			protected void action() {
				agent.goToMarker();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(5000, "Search") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.seeMarker();
			}

			@ Override
			protected void action() {
				mothership.clearMarker();
				agent.searchResources();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(6000, "PickUp") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && (agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(ResourceType.ExtraMothershipFuel));
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(6250, "PickUp and Place") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.isTouchingResource(ResourceType.ExtremPoint);
			}

			@ Override
			protected void action() {
				if (!mothership.isMarkerDeployed()) {
					agent.deployMarker();
				}
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(6500, "PickUp and Place") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && (agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(ResourceType.ExtraMothershipFuel)) && !mothership.isMarkerDeployed() && !agent.seeMarker();
			}

			@ Override
			protected void action() {
				agent.deployMarker();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(7000, "PickUp 5P and Place") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.isTouchingResource(ResourceType.ExtremPoint);
			}

			@ Override
			protected void action() {
				agent.deployMarker();
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(7500, "PickUp 5P and Place") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && agent.isTouchingResource(ResourceType.ExtremPoint) && !agent.seeMarker();
			}

			@ Override
			protected void action() {
				agent.deployMarker();

			}
		});
		//-------------------------------------------->
		createRule(new Rule(8000, "Ignore") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource(ResourceType.Mine) || agent.isTouchingResource(ResourceType.ExtraAgentFuel);
			}

			@ Override
			protected void action() {
				agent.go();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9000, "Secure") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon() && !agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(9500, "Mark Resource") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && !mothership.isMarkerDeployed() && agent.seeResource();
			}

			@ Override
			protected void action() {
				agent.deployMarker();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(10000, "Saved") {
			@ Override
			protected boolean constraint() {
				return agent.isGameOverSoon() && agent.isAtMothership();
			}

			@ Override
			protected void action() {
			}
		});
		//-------------------------------------------->
		createRule(new Rule(11000, "Order Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isGameOverSoon() && mothership.hasFuel() && agent.getFuelInPercent() < 100;
			}

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(12000, "Support Agent") {
			@ Override
			protected boolean constraint() {
				return mothership.needSomeoneSupport() && !agent.isSupportOrdered();
			}

			@ Override
			protected void action() {
				agent.goToSupportAgent();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(13000, "Save Resource") {
			@ Override
			protected boolean constraint() {
				return agent.isCarringResource() && !agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(14000, "HelpLostAgent") {
			@ Override
			protected boolean constraint() {
				return agent.seeLostTeamAgent();
			}

			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(300);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(15000, "FightAgainstMothership") {
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
		createRule(new Rule(16000, "FightAgainstMothership & Order Support") {
			@ Override
			protected boolean constraint() {
				return !agent.isSupportOrdered() && agent.seeAdversaryMothership();
			}

			@ Override
			protected void action() {
				agent.fightWithAdversaryMothership();
				agent.orderSupport();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(17000, "TurnToAdversaryAgent") {
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
		createRule(new Rule(18000, "SaveMothership") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && !agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(19000, "RepaireMothership") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(20000, "FightAgainstAgent") {
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
		createRule(new Rule(21000, "PlaceMine") {
			@ Override
			protected boolean constraint() {
				return agent.hasMine() && agent.isUnderAttack();
			}

			@ Override
			protected void action() {
				agent.deployMine();
				agent.goLeft(180);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(22000, "GoBackToMothership") {
			@ Override
			protected boolean constraint() {
				return agent.getFuel() < 300 && !agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(23000, "OrderFuel") {
			@ Override
			protected boolean constraint() {
				return mothership.hasFuel() && (agent.getFuelInPercent() < 90) && (agent.isAtMothership());
			}

			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(24000, "OrderFuelDuringFight") {
			@ Override
			protected boolean constraint() {
				return mothership.hasFuel() && (agent.getFuel() < 100) && agent.isUnderAttack() && agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.orderFuel(5);
				agent.goLeft(10);
			}
		});
		//-------------------------------------------->
		createRule(new Rule(25000, "OrderFuelDuringFight") {
			@ Override
			protected boolean constraint() {
				return mothership.hasFuel() && (agent.getFuel() < 100) && agent.seeAdversaryAgent() && agent.isAtMothership();
			}

			@ Override
			protected void action() {
				agent.orderFuel(5);
				agent.fightWithAdversaryAgent();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(26000, "Pass Resource") {
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
		createRule(new Rule(27000, "CallForHelp") {
			@ Override
			protected boolean constraint() {
				return (!agent.isSupportOrdered()) && ((agent.getFuel() < 5) || agent.isUnderAttack());
			}

			@ Override
			protected void action() {
				agent.orderSupport();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(29000, "PickUp Fuel") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() < 50 && agent.isTouchingResource(ResourceType.ExtraAgentFuel);
			}

			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		createRule(new Rule(30000, "Follow Wall") {
			@ Override
			protected boolean constraint() {
				return agent.isCommander() && agent.isCollisionDetected();
			}

			@ Override
			protected void action() {
				agent.turnLeft(4);

			}
		});
		//-------------------------------------------->
		createRule(new Rule(31000, "AvoidWall") {
			@ Override
			protected boolean constraint() {
				return !agent.isCommander() && agent.isCollisionDetected();
			}

			@ Override
			protected void action() {
				agent.turnRandom(150);

			}
		});
//		//-------------------------------------------->
//		createRule(new Rule(32000, "Cancel Support") {
//			@ Override
//			protected boolean constraint() {
//				return agent.isSupportOrdered() && !agent.seeAdversaryMothership() && agent.getFuel() > 10 && !agent.isUnderAttack() && !agent.seeAdversaryAgent();
//			}
//
//			@ Override
//			protected void action() {
//				agent.cancelSupport();
//
//			}
//		});
//		//-------------------------------------------->
	}
}
