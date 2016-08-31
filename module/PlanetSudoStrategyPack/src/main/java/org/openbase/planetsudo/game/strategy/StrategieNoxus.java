/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.game.strategy;

import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author divine
 */
public class StrategieNoxus extends AbstractStrategy {

	public StrategieNoxus() {
	}
	public StrategieNoxus(AgentInterface a) {
		super(a);
	}

	/**
	 * Wie viele Agenten sollen erstellt werde wird hier angegeben.
	 * @return Anzahl der Agenten
	 */
	@Override
	protected int loadAgentCount() {
		return 2;
	}

	@Override
	protected void loadRules() {
		//-------------------------------------------->
		createRule(new Rule(1000, "Kollision!") {
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
		//-------------------------------------------->
		createRule(new Rule(870, "Warte...") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 6 && agent.isAtMothership() && !mothership.hasFuel() && !agent.isUnderAttack() && !mothership.isDamaged() && !agent.isCarringResource();
			}
			@ Override
			protected void action() {
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(860, "Auftanken...") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 7 && agent.isAtMothership() && mothership.hasFuel();
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(850, "Wenig Energie...") {
			@ Override
			protected boolean constraint() {
				return agent.getFuelInPercent() <= 7 && agent.hasFuel() && !agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.goToMothership();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(720, "Repariere Mutterschiff...") {
			@ Override
			protected boolean constraint() {
				return mothership.isDamaged() && agent.isAtMothership() && !agent.isUnderAttack();
			}
			@ Override
			protected void action() {
				agent.repairMothership();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(800, "Mutterschiff helfen...") {
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
		//-------------------------------------------->
		createRule(new Rule(750, "Bekämpfe Agent...") {
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
		createRule(new Rule(740, "Suche Feind...") {
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
		//-------------------------------------------->
		createRule(new Rule(610, "Mutterschiff verminen...") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership() && agent.hasMine();
			}
			@ Override
			protected void action() {
				agent.deployMine();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(600, "Bekämpfe Mutterschiff...") {
			@ Override
			protected boolean constraint() {
				return agent.seeAdversaryMothership();
			}
			@ Override
			protected void action() {
				agent.deployMarker();
				agent.fightWithAdversaryMothership();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(350, "Notkanal schließen.") {
			@ Override
			protected boolean constraint() {
				return agent.isSupportOrdered() && agent.getFuelInPercent() >=7;
			}
			@ Override
			protected void action() {
				agent.cancelSupport();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(340, "Gestrandet...") {
			@ Override
			protected boolean constraint() {
				return !agent.hasFuel() && !agent.isAtMothership();
			}
			@ Override
			protected void action() {
				agent.orderSupport();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(310, "Energie Spenden...") {
			@ Override
			protected boolean constraint() {
				return mothership.needSomeoneSupport() && agent.seeLostTeamAgent();
			}
			@ Override
			protected void action() {
				agent.spendTeamAgentFuel(7);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(300, "Agenten Helfen...") {
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
		//-------------------------------------------->
		createRule(new Rule(250, "Resource abliefern!") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.isCarringResource();
			}
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
				agent.turnAround();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(230, "Berge Resource...") {
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
		createRule(new Rule(220, "Mine!") {
			@ Override
			protected boolean constraint() {
				return (agent.isTouchingResource(Resource.ResourceType.Mine) || agent.isTouchingResource(Resource.ResourceType.ExtremPoint));
			}
			@ Override
			protected void action() {
				agent.go();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(210, "Resource aufheben!") {
			@ Override
			protected boolean constraint() {
				return agent.isTouchingResource() && !agent.isTouchingResource(Resource.ResourceType.Mine) && !agent.isTouchingResource(Resource.ResourceType.ExtremPoint);
			}
			@ Override
			protected void action() {
				agent.pickupResource();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(200, "Resource gesichtet...") {
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
		createRule(new Rule(30, "Zwischentanken...") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && mothership.hasFuel() && !mothership.isBurning() && !mothership.isDamaged() && !agent.isUnderAttack() && agent.getFuelInPercent()<=95;
			}
			@ Override
			protected void action() {
				agent.orderFuel(100);
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(20, "Marker entfernen.") {
			@ Override
			protected boolean constraint() {
				return agent.seeMarker();
			}
			@ Override
			protected void action() {
				mothership.clearMarker();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(10, "Bewege zu Marker...") {
			@ Override
			protected boolean constraint() {
				return mothership.isMarkerDeployed();
			}
			@ Override
			protected void action() {
				agent.goToMarker();
			}
		});
		//-------------------------------------------->
		//-------------------------------------------->
		createRule(new Rule(0, "Bewegung...") {
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
	}
}
