package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo Artificial Intelligence
 * %%
 * Copyright (C) 2009 - 2018 openbase.org
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
public class HammerStrategy extends AbstractStrategy {

    public HammerStrategy() {
    }

    public HammerStrategy(AgentInterface a) {
        super(a);
    }

    /**
     * Über diese Methode kannst du angeben wie viele Agenten dein Team besitzen
     * soll.
     * ===============================================================================
     * Bedenke, dass dein Muterschiff die Aktionspunkte (APs) gleichmäßig auf
     * deine Agenten verteilt. Somit ist ein Team mit vielen Agenten unheimlich
     * effizient beim Resourcen sammeln und beim Planeten erforschen, allerdings
     * stehen einem einzelnen Agenten weniger Aktionspunkte für den Kampf mit
     * feindlichen Agenten zur Verfügung wodurch große Teams im Kampf schwächer
     * sind.
     *
     * @return Anzahl der Agenten
     */
    @Override
    protected int loadAgentCount() {
        return 20;
    }

    /**
     * Hier kannst du SwatTeams aus mehreren Agenten bilden.
     * ===================================================== Die Agenten werden
     * hierbei über ihre IDs hinzugefügt. Sind beispielsweise 4 Agenten in der
     * Strategie angegeben, so sind diese über die IDs 0 - 3 referenzierbar
     * wobei Agent 0 immer für den Kommander steht. Bitte beachte somit, dass
     * die Agenten ID nicht größer als N - 1 sein kann sofern N für die maximale
     * Anzahl von Agenten steht. Ein Agent darf durchaus in mehrere Swat Teams
     * eingeteilt werden.
     *
     * ACHTUNG: Die default Gruppen ALL und COMMANDER können anhand dieser
     * Methode nicht modifiziert werden!
     */
    @Override
    protected void loadSwatTeams() {
        // createSwat(ALPHA, 1, 6, 8);
        // createSwat(DELTA, 2, 6, 7);
    }

    /**
     * Hier kannst du die Regeln für deine Agenten definieren.
     *
     * Prioritäten festlegen ===================== Die Reihenfolge der
     * Registrierung bestimmt hierbei die Priorität der Regeln. So besitzt die
     * erste Registrierte Regeln die geringste und die zuletzt registrierte
     * Regel die höchste Priorität.
     *
     * Swat Teams ========== Swat Teams müssen zuvor über die "loadSwatTeams"
     * Methode erstellt werden. Anschließend kann das "ALL" der Regel Definition
     * durch den Swatnamen ersetzt werden. Eine Regel kann mehreren, durch Komma
     * getrennten, Swat Teams zugeteilt werden. Zudem können Swat Teams mit dem
     * "NOT_" prefix von einer Regel ausgeschlossen werden.
     *
     * z.B. "createRule(new Rule("Just Go", ALPHA, FOXTROT)" oder
     * "createRule(new Rule("Just Go", ALL, NOT_FOXTROT)"
     */
    @Override
    protected void loadRules() {
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
        //-------------------------------------------->
        createRule(new Rule("ressource gesichtet", ALL) {
            @Override
            protected boolean constraint() {
                return agent.seeResource();
            }

            @Override
            protected void action() {
                agent.goToResource();
            }
        });

        //-------------------------------------------->
        createRule(new Rule("ressource einsammeln", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource();
            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
        });

        //-------------------------------------------->
        createRule(new Rule("Trage ressource", ALL) {
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
        createRule(new Rule("Ressouce zum Mutterschiff", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isCarringResource() && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.deliverResourceToMothership();
            }
        });
        
       
        //-------------------------------------------->
        createRule(new Rule("Nachtanken erforderlich", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() <= 8;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Nachtanken gehen", ALL) {
            @Override
            protected boolean constraint() {
                   return agent.getFuelInPercent() <= 8 && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.orderFuel(100);
            }
        });

        //-------------------------------------------->
        createRule(new Rule("turn arround", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnAround();
                agent.turnRandom(150);
            }
        });
        /**
         * Eine neue Regel erstellen ========================= Hierzu markiere
         * am besten die obere Regel von "//" bis "});" und drücke in Netbeans
         * die Tastenkombination: (Strg + Shift + Down) Die Regel sollte somit
         * kopiert und als nächste Regel eingefügt werden.
         *
         * Nachträgliche Änderung von Prioritäten
         * ====================================== Während der Optimierung der
         * Strategie, wird es immer mal wieder nötig sein die Prioritäten
         * existiereder Regeln neu zu verteilen. Realisiert werden kann dies
         * durch die Änderung der Regelreihenfolgen. Soll die Priorität einer
         * Regel verändert werden, so wird die zu verschiebene Regel markiert
         * (von "//" bis "});") und in Netbeans über die Tastenkombination (Alt
         * + Shift + (Up/Down)) verschoben.
         *
         */

    }
}
