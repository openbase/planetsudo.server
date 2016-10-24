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
public class AbDurchDieHecke extends AbstractStrategy {

    public AbDurchDieHecke() {
    }

    public AbDurchDieHecke(AgentInterface a) {
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
        createSwat(ALPHA, 2, 3, 4);
        createSwat(DELTA, 0);
        createSwat(KILO, 1);
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
        createRule(new Rule("Just Go", ALPHA) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go();
                agent.turnRight(2);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Just Go", DELTA) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go();
                agent.turnLeft(3);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Just Go", KILO) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go();
                agent.turnRight(3);
            }
        });
                //-------------------------------------------->
        createRule(new Rule("ResourcenHolen", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.seeResource() && !agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.goToResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Einsammeln", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource() && !agent.isTouchingResource(Resource.ResourceType.Mine);
            }

            @Override
            protected void action() {
                agent.pickupResource();
                
            }
        });
        //-------------------------------------------->
        createRule(new Rule("EinsammelnDickeFische", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource(Resource.ResourceType.ExtremPoint);
            }

            @Override
            protected void action() {
                agent.pickupResource();
                agent.deployMarker();
                
            }
        });
        //-------------------------------------------->
        createRule(new Rule("ZurueckZumSchiff", ALPHA) {
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
        createRule(new Rule("Fight", DELTA) {
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
        createRule(new Rule("FightCarryer", DELTA) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryAgent()&& agent.seeResource();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryAgent();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("FightMothership", DELTA) {
            @Override
            protected boolean constraint() {
                return !agent.seeAdversaryAgent()&& agent.seeAdversaryMothership();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryMothership();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("DUELL", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() && agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryAgent();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("DUELL2", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() && !agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.turnAround();
                agent.fightWithAdversaryAgent();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("DUELL3", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() && agent.getFuelInPercent() < 25;
            }

            @Override
            protected void action() {
                agent.goToMothership();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("DUELL4", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() && agent.getFuelInPercent() < 25 && agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.releaseResource();
                agent.goToMothership();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("HILFE!", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isDisabled();
            }

            @Override
            protected void action() {
                agent.orderSupport();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Helfen", DELTA) {
            @Override
            protected boolean constraint() {
                return mothership.needSomeoneSupport();
            }

            @Override
            protected void action() {
                agent.goToSupportAgent();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Helfen2", DELTA) {
            @Override
            protected boolean constraint() {
                return agent.seeLostTeamAgent();
            }

            @Override
            protected void action() {
                agent.spendTeamAgentFuel(agent.getFuelInPercent()/2);
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Tanken", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && (agent.getFuelInPercent() < 30);
            }

            @Override
            protected void action() {
                agent.orderFuel(60);
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Tanken2", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && (agent.getFuelInPercent() < 15);
            }

            @Override
            protected void action() {
                agent.orderFuel(75);
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("DickeFischeholen", ALPHA) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed() && !agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.goToMarker();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("SpottenDickeFische", DELTA, KILO) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource(Resource.ResourceType.ExtremPoint) && !mothership.isMarkerDeployed();
            }

            @Override
            protected void action() {
                agent.deployMarker();              
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("MarkerWirtschaft", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.seeMarker() && !agent.isCarringResource();
            }

            @Override
            protected void action() {
                mothership.clearMarker();
                agent.searchResources();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Abliefern", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.deliverResourceToMothership();
                agent.turnAround();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Not-Tanken", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 15 && !agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.goToMothership();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Not-Tanken2", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 15 && agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.releaseResource();
                agent.goToMothership();
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Not-Tanken-Final", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 15 && agent.isAtMothership(); 
            }

            @Override
            protected void action() {
                agent.orderFuel(75);
                
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("KeineKollision", DELTA) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRight(23);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("KeineKollision", KILO) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnLeft(23);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("KeineKollisionMehr", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnAround();
                agent.turnRandom(90);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Baldvorbei", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon() ;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Baldvorbei2", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon() && agent.isCarringResource() && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.deliverResourceToMothership();
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
