package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo Artificial Intelligence
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import static org.openbase.planetsudo.game.SwatTeam.*;
import org.openbase.planetsudo.level.levelobjects.AgentInterface;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class gruenmuckel extends AbstractStrategy {

    public gruenmuckel() {
    }

    public gruenmuckel(AgentInterface a) {
        super(a);
    }

    /**
     * Über diese Methode kannst du angeben wie viele Agenten dein Team besitzen soll.
     * ===============================================================================
     * Bedenke, dass dein Muterschiff die Aktionspunkte (APs) gleichmäßig auf deine Agenten verteilt.
     * Somit ist ein Team mit vielen Agenten unheimlich effizient beim Resourcen sammeln und beim Planeten erforschen,
     * allerdings stehen einem einzelnen Agenten weniger Aktionspunkte für den Kampf mit feindlichen Agenten zur Verfügung wodurch große Teams im Kampf schwächer sind.
     *
     * @return Anzahl der Agenten
     */
    @Override
    protected int loadAgentCount() {
        return 6;
    }

    /**
     * Hier kannst du SwatTeams aus mehreren Agenten bilden.
     * =====================================================
     * Die Agenten werden hierbei über ihre IDs hinzugefügt. Sind beispielsweise 4 Agenten in der Strategie angegeben,
     * so sind diese über die IDs 0 - 3 referenzierbar wobei Agent 0 immer für den Kommander steht.
     * Bitte beachte somit, dass die Agenten ID nicht größer als N - 1 sein kann sofern N für die maximale Anzahl von Agenten steht.
     * Ein Agent darf durchaus in mehrere Swat Teams eingeteilt werden.
     *
     * ACHTUNG: Die default Gruppen ALL und COMMANDER können anhand dieser Methode nicht modifiziert werden!
     */
    @Override
    protected void loadSwatTeams() {
        // createSwat(ALPHA, 1, 6, 8);
        // createSwat(DELTA, 2, 6, 7);
    }

    /**
     * Hier kannst du die Regeln für deine Agenten definieren.
     *
     * Prioritäten festlegen
     * =====================
     * Die Reihenfolge der Registrierung bestimmt hierbei die Priorität der Regeln.
     * So besitzt die erste Registrierte Regeln die geringste und die zuletzt registrierte Regel die höchste Priorität.
     *
     * Swat Teams
     * ==========
     * Swat Teams müssen zuvor über die "loadSwatTeams" Methode erstellt werden.
     * Anschließend kann das "ALL" der Regel Definition durch den Swatnamen ersetzt werden.
     * Eine Regel kann mehreren, durch Komma getrennten, Swat Teams zugeteilt werden. Zudem können Swat Teams mit dem "NOT_" prefix von einer Regel ausgeschlossen werden.
     * 
     * z.B. "createRule(new Rule("Just Go", ALPHA, FOXTROT)" oder "createRule(new Rule("Just Go", ALL, NOT_FOXTROT)"
     */
    @Override
    protected void loadRules() {
        //-------------------------------------------->
//		createRule(new Rule(10000, "Avoid Wall") {
//			@ Override
//			protected boolean constraint() {
//				return agent.isCollisionDetected();
//			}
//			@ Override
//			protected void action() {
//				agent.turnRandom();
//			}
//		});
        //-------------------------------------------->
		createRule(new Rule(10000, "Avoid Wall2") {
			@ Override
			protected boolean constraint() {
				return agent.isCollisionDetected();
			}
			@ Override
			protected void action() {
				agent.turnRight(9);
                                agent.turnRandom(4);
			}
		});
//		//-------------------------------------------->
//		createRule(new Rule(8204, "ShipEmpty_AndRessAtThip") {
//			@ Override
//			protected boolean constraint() {
//				return !mothership.hasFuel() && agent.isCarringResource() && agent.isAtMothership();			
//                        }
//			@ Override
//			protected void action() {
//				agent.deliverResourceToMothership();
//			}
//                     });   
		//-------------------------------------------->
		createRule(new Rule(8205, "Rette_sich_wer_kann") {
			@ Override
			protected boolean constraint () {
				return agent.isGameOverSoon();			
                        }
			@ Override
			protected void action() {
				agent.goToMothership();
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8204, "Schmeiss_Weg_den_Scheiss") {
			@ Override
			protected boolean constraint() {
				return agent.isUnderAttack();			
                        }
			@ Override
			protected void action() {
				agent.releaseResource();
                                agent.goToMothership();
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8203, "ShipEmpty_AndRessAtThip") {
			@ Override
			protected boolean constraint() {
				return !mothership.hasFuel() && agent.isCarringResource() && agent.isAtMothership();			
                        }
			@ Override
			protected void action() {
				agent.deliverResourceToMothership();
                                agent.turnAround();
			}
                     });   
		//-------------------------------------------->
		createRule(new Rule(8202, "ShipEmpty_AndRess") {
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
		createRule(new Rule(8201, "ShipEmpty_AndHome") {
			@ Override
			protected boolean constraint() {
				return !mothership.hasFuel()&& !agent.isCarringResource() && agent.getFuelInPercent()< 35 ;			
                        }
			@ Override
			protected void action() {
				agent.goToMothership();    // vorher nur agent.go
			}
                     });   
//		//-------------------------------------------->
//		createRule(new Rule(8200, "ShipEmpty") {
//			@ Override
//			protected boolean constraint() {
//				return !mothership.hasFuel()&& agent.isCarringResource();			
//                        }
//			@ Override
//			protected void action() {
//				agent.goToMothership();    // vorher nur agent.go
//			}
//                     });   
		//-------------------------------------------->
		createRule(new Rule(8150, "StayAtHomeSavePoints") {
			@ Override
			protected boolean constraint() {
				return agent.isAtMothership() && agent.getFuelInPercent() < 25 && !mothership.hasFuel();			
                        }
			@ Override
			protected void action() {
				
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
		createRule(new Rule("Just Go", ALL) {
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
 