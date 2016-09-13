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
public class Eistee extends AbstractStrategy {

    public Eistee() {
    }

    public Eistee(AgentInterface a) {
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
        return 3;
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
        createSwat(GOLF, 0);
        createSwat(ALPHA, 1);
        
        
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
        createRule(new Rule("gehen zurueck", ALL, NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return !agent.isAtMothership() && agent.getFuelInPercent() < 15;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("gehen vor", ALL, NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed() && agent.isAtMothership() && agent.getFuelInPercent() > 15;
            }

            @Override
            protected void action() {
                agent.goToMarker();
            }
        });
        
        //-------------------------------------------->
        createRule(new Rule("tanken2", ALL, NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.getFuelInPercent() < 15 && mothership.hasFuel();
            }

            @Override
            protected void action() {
                int winkel = agent.getAngle();
                int berechneter_winkel = winkel + 0;
                agent.turnLeft(berechneter_winkel);
                agent.orderFuel(98);
            }
        });
       //-------------------------------------------->
        createRule(new Rule("gehen vor wenn platz", ALL, NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed() && !agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.goToMarker();
            }
        });
        //-------------------------------------------->
       createRule(new Rule("gehen vor wenn kein platz", ALL, NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed() && agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnAround();
            }
        });
        //--------------------------------------------> 
        
        
        
        
        
        
        
        
        createRule(new Rule("Just Go", COMMANDER) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.deployMarker();
                agent.go();
                agent.turnLeft(1);
            }
        });
        
        
        createRule(new Rule("tanken2", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.getFuelInPercent() < 20 && mothership.hasFuel();
            }

            @Override
            protected void action() {
                agent.orderFuel(98);
            }
        });
        //-------------------------------------------->
        
        createRule(new Rule("auftanken connnnander", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.getFuelInPercent() <90 ;
            }

            @Override
            protected void action() {
                agent.orderFuel(96);
            }
        });
        //-------------------------------------------->
        
        createRule(new Rule("tanken fahren normal", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 20 ;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        createRule(new Rule("tanken fahren mit angriff", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 25 && agent.isUnderAttack() && agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.goToMothership();
                
            }
        });
        
        
        //-------------------------------------------->
        createRule(new Rule("fight agent", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() || agent.isFighting() || agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                
                agent.fightWithAdversaryAgent();
                
            }
        });
        //-------------------------------------------->
        createRule(new Rule("fight mutter", COMMANDER) {
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
        
        
        //-------------------------------------------->
        createRule(new Rule("Achtung mauer", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRight(17);
                agent.turnRandom(20);
            }
        });
        createRule(new Rule("fight", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() || agent.isFighting() || agent.seeAdversaryAgent();
            }
            @Override
            protected void action() {
                agent.fightWithAdversaryAgent();
                agent.orderSupport();
            }
        });
        //-------------------------------------------->
                    //ENDE
        //-------------------------------------------->
        createRule(new Rule("mama helfen", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.isDamaged();
            }
            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("mama nach Hause", ALL) {
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
