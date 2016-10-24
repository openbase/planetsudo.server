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
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class Limoaffen extends AbstractStrategy {

    public Limoaffen() {
    }

    public Limoaffen(AgentInterface a) {
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
        return 5;
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
        createSwat(ALPHA,  1, 2,3);
        createSwat(BRAVO, 0, 4);
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
        //-------------------------------------------->
        createRule(new Rule("Just Go", ALL) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go();
            }
        });
     * Swat Teams müssen zuvor über die "loadSwatTeams" Methode erstellt werden.
     * Anschließend kann das "ALL" der Regel Definition durch den Swatnamen ersetzt werden.
     * Eine Regel kann mehreren, durch Komma getrennten, Swat Teams zugeteilt werden. Zudem können Swat Teams mit dem "NOT_" prefix von einer Regel ausgeschlossen werden.
     * 
     * z.B. "createRule(new Rule("Just Go", ALPHA, FOXTROT)" oder "createRule(new Rule("Just Go", ALL, NOT_FOXTROT)"
     */
    @Override
    protected void loadRules() {
        //-------------------------------------------->
        createRule(new Rule("[G]Just Go", ALL) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go();
            }
        });
             //-------------------------------------------->
        createRule(new Rule("[2]touch Resource Fuel", ALL) {
            @Override
            protected boolean constraint() {
            return agent.isTouchingResource(Resource.ResourceType.ExtraAgentFuel) && (agent.getFuelInPercent() < 30);

            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
                 });
        //-------------------------------------------->
        createRule(new Rule("[2]touch Resource except fuel", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource() && !agent.isTouchingResource(Resource.ResourceType.ExtraAgentFuel);
            }

            @Override
            protected void action() {
                agent.pickupResource();
                agent.goToMothership();
            }
            });
        //-------------------------------------------->
        createRule(new Rule("[G]bring Resource@mothership", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
});
        //-------------------------------------------->
        createRule(new Rule("[G]at Mothership", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.deliverResourceToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G]avoid Collision", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRandom(360);
            }
        });
      //-------------------------------------------->
        createRule(new Rule("[G]Refuel at base", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuel() < 50;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule(" [G]Agent Down", ALL) {
            @Override
            protected boolean constraint() {
                return !agent.hasFuel();}

            @Override
            protected void action() {
                agent.orderSupport();
            }
        });
        //-------------------------------------------->
        createRule(new Rule(" [G]Agent support send", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.needSomeoneSupport() && !agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.goToSupportAgent();
                agent.releaseResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[1]Agent support", BRAVO) {
            @Override
            protected boolean constraint() {
                return agent.seeLostTeamAgent();
            }

            @Override
            protected void action() {
                agent.spendTeamAgentFuel(300);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[1]Agent end support", BRAVO) {
            @Override
            protected boolean constraint() {
                return agent.isSupportOrdered() && (agent.hasFuel()|| !agent.isFighting());
            }

            @Override
            protected void action() {
                agent.cancelSupport();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[1]attackenemyAgent", ALL) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryAgent();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[1]attackenemybase", ALL) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryMothership();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G] Agent report attack", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack();
            }

            @Override
            protected void action() {
                agent.orderSupport();
            }
        });
//        //-------------------------------------------->
//        createRule(new Rule("[G] Agents follow 1", ALL) {
//            @Override
//            protected boolean constraint() {
//                return agent.isUnderAttack();
//            }
//
//            @Override
//            protected void action() {
//                agent.orderSupport();
//            }
//        });
        //-------------------------------------------->
        createRule(new Rule("[G]under Attack", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack();
            }

            @Override
            protected void action() {
                agent.turnAround();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G]marker deployed", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed();
            }

            @Override
            protected void action() {
                agent.goToMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G]clear marker", ALL) {
            @Override
            protected boolean constraint() {
                return agent.seeMarker();
            }

            @Override
            protected void action() {
                agent.goToResource();
                mothership.clearMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G]Refuel", ALL) {
            @Override
            protected boolean constraint() {
                return  agent.getFuel() <100;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G]Refuel", ALL) {
            @Override
            protected boolean constraint() {
                return  agent.isAtMothership() && agent.getFuel() <100;
            }

            @Override
            protected void action() {
                agent.orderFuel(60);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("[G]gameover", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        
       
        /**
         * Eine neue Regel erstellen
         * =========================
         * Hierzu markiere am besten die obere Regel von "//" bis "});" und drücke in Netbeans die Tastenkombination: (Strg + Shift + Down)
         * Die Regel sollte somit kopiert und als nächste Regel eingefügt werden. 
         * 
         * Nachträgliche Änderung von Prioritäten
         * ======================================
         * Während der Optimierung der Strategie, wird es immer mal wieder nötig sein die Prioritäten existiereder Regeln neu zu verteilen.
         * Realisiert werden kann dies durch die Änderung der Regelreihenfolgen. Soll die Priorität einer Regel verändert werden, 
         * so wird die zu verschiebene Regel markiert (von "//" bis "});") und in Netbeans über die Tastenkombination (Alt + Shift + (Up/Down)) verschoben.
         * 
         */
       
    }
}