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
package org.openbase.planetsudo.game.strategy;

import org.openbase.planetsudo.level.levelobjects.AgentInterface;
import org.openbase.planetsudo.level.levelobjects.Resource;

/**
 *
 * @author <a href="mailto:DivineThreepwood@gmail.com">Divine Threepwood</a>
 */
public class MybattleStategy1 extends AbstractStrategy {

    public MybattleStategy1() {
    }

    public MybattleStategy1(AgentInterface a) {
        super(a);
    }

    /**
     * Gib hier an wie viele Agenten dein Team besitzen soll.
     *
     * @return Anzahl der Agenten
     */
    @Override
    protected int loadAgentCount() {
        return 3;
    }

    @Override
    protected void loadRules() {
        //-------------------------------------------->
        createRule(new Rule(2, "Just Go") {
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
        createRule(new Rule(1000000, "Wand ausweichen") {
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
        createRule(new Rule(950000, "Resource abgeben") {
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
        createRule(new Rule(850000, "Resource sehen") {
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
        createRule(new Rule(900000, "Resource wegbringen") {
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
        createRule(new Rule(851000, "Resource aufnehmen") {
            @ Override
            protected boolean constraint() {
                return agent.isTouchingResource() && !agent.isCarringResource();
            }

            @ Override
            protected void action() {
                agent.pickupResource();

            }
        });
        //-------------------------------------------->
        createRule(new Rule(980000, "Agenten aufladen") {
            @ Override
            protected boolean constraint() {
                return agent.getFuelInPercent() <= 30;
            }

            @ Override
            protected void action() {
                agent.goToMothership();
                

            }
        });
        //-------------------------------------------->
        createRule(new Rule(980000, "Agenten aufladen 2") {
            @ Override
            protected boolean constraint() {
                return agent.getFuelInPercent() <= 30 && agent.isAtMothership();
            }

            @ Override
            protected void action() {
                agent.orderFuel(200);
                

            }
        });
        //-------------------------------------------->
        createRule(new Rule(980001, "Agent wird angegriffen") {
            @ Override
            protected boolean constraint() {
                return agent.isUnderAttack();
            }

            @ Override
            protected void action() {
                agent.fightWithAdversaryAgent();

            }
        });
        //-------------------------------------------->
        createRule(new Rule(700000, "Flucht") {
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
        createRule(new Rule(993000, "Zu Hilfe kommen") {
            @ Override
            protected boolean constraint() {
                return agent.seeLostTeamAgent();
            }

            @ Override
            protected void action() {
                agent.spendTeamAgentFuel(40);

            }
        });
        //-------------------------------------------->
        createRule(new Rule(982000, "Allein Hilfe rufen") {
            @ Override
            protected boolean constraint() {
                return agent.isDisabled();
            }

            @ Override
            protected void action() {
                agent.orderSupport();

            }
        });
        
        //-------------------------------------------->
        createRule(new Rule(970000, "Mutterschiffschutz") {
            @ Override
            protected boolean constraint() {
                return mothership.isDamaged()&& !agent.isAtMothership();
            }

            @ Override
            protected void action() {
                agent.goToMothership();

            }
        });
        //-------------------------------------------->
        createRule(new Rule(960000, "Mutterschiffrepair") {
            @ Override
            protected boolean constraint() {
                return mothership.isBurning()&& agent.isAtMothership();
            }

            @ Override
            protected void action() {
                agent.repairMothership();

            }
        });
        //-------------------------------------------->
        createRule(new Rule(991000, "Atttacke!!!") {
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
        createRule(new Rule(200, "Vernichtung!") {
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
        createRule(new Rule(999999, "Ende") {
            @ Override
            protected boolean constraint() {
                return agent.isGameOverSoon();
            }

            @ Override
            protected void action() {
                agent.goToMothership();

            }
        });
     
    

    }
}
