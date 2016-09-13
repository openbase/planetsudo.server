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
import org.openbase.planetsudo.game.SwatTeam;
import static org.openbase.planetsudo.game.SwatTeam.*;
import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class TeamKuno extends AbstractStrategy {

    public TeamKuno() {
    }

    public TeamKuno(AgentInterface a) {
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
        return 4;
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
        createSwat(ALPHA, 1, 2);
        createSwat(DELTA, 3);
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
        createRule(new Rule("sieht Recource", ALL) {
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
        createRule(new Rule("geht zu bestimmte Recourcen", ALL) {
            @Override
            protected boolean constraint() {
                return agent.seeMarker() && mothership.getFuelInPercent() < 30;
            }

            @Override
            protected void action() {
                agent.goToMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("sieht bestimmt Recource", ALL) {
            @Override
            protected boolean constraint() {
                return agent.seeResource(Resource.ResourceType.ExtraMothershipFuel) && (mothership.getFuelInPercent() < 30);
            }

            @Override
            protected void action() {
                agent.goToResource(Resource.ResourceType.ExtraMothershipFuel);
                agent.deployMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("sammle Recource", ALL) {
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
        createRule(new Rule("sammle bestimmte Recource", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource(Resource.ResourceType.ExtraMothershipFuel) && mothership.getFuelInPercent() < 30 ;
            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("geh zum Mutterschiff", ALL) {
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
        createRule(new Rule("abladen", ALL) {
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
        createRule(new Rule("angreifen", ALPHA) {
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
        createRule(new Rule("beschützen", ALL) {
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
        createRule(new Rule("helfen", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isSupportOrdered() && agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryAgent();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Tanken", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.needSomeoneSupport();
            }

            @Override
            protected void action() {
                agent.goToSupportAgent();
                agent.spendTeamAgentFuel(70);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("laden", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuel() < 5;
            }

            @Override
            protected void action() {
                agent.orderSupport();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("hilfe", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack();
            }

            @Override
            protected void action() {
                agent.orderSupport();
                agent.releaseResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("wenig energie", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuel() < 300;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("laden", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.getFuel() < 300;
            }

            @Override
            protected void action() {
                agent.orderFuel(100);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("hilfe", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon();
            }

            @Override
            protected void action() {
                agent.goToMothership();
                agent.deliverResourceToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("hilfe", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isDisabled()&& agent.isGameOverSoon();
            }

            @Override
            protected void action() {
                agent.orderSupport();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("hilfe", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.needSomeoneSupport()&& agent.isGameOverSoon();
            }

            @Override
            protected void action() {
                agent.goToSupportAgent();
                agent.spendTeamAgentFuel(30);
                            }
        });
        //-------------------------------------------->
        createRule(new Rule("Mutterschiff beschützen", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.isDamaged();
            }

            @Override
            protected void action() {
                agent.goToMothership();
                agent.seeAdversaryAgent();
                agent.fightWithAdversaryAgent();

            }
        });
        //-------------------------------------------->
        createRule(new Rule("Mutterschiff reparieren", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.isMaxDamaged();
            }

            @Override
            protected void action() {
                agent.repairMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("verhindere Crach", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRandom();
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
