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
package de.dc.planetsudo.game.strategy;

import de.dc.planetsudo.level.levelobjects.AgentInterface;
import de.dc.planetsudo.level.levelobjects.Resource;
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;

/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class Searchandcollect extends AbstractStrategy {

    public Searchandcollect() {
    }

    public Searchandcollect(AgentInterface a) {
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
        createRule(new Rule(2600, "Support2") {
            @ Override
            protected boolean constraint() {
                return agent.isUnderAttack();
            }

            @ Override
            protected void action() {
                agent.goToSupportAgent();



            }
        });
        //-------------------------------------------->
        createRule(new Rule(2000, "Wiederbelebung") {
            @ Override
            protected boolean constraint() {
                return agent.seeLostTeamAgent();
            }

            @ Override
            protected void action() {
                agent.spendTeamAgentFuel(120);
            }
        });
        //-------------------------------------------->
        createRule(new Rule(1599, "Hilfe") {
            @ Override
            protected boolean constraint() {
                return mothership.isDamaged() || mothership.isBurning();
            }

            @ Override
            protected void action() {
                agent.repairMothership();
            }
        });

        //-------------------------------------------->
        createRule(new Rule(1995, "Mine") {
            @ Override
            protected boolean constraint() {
                return agent.isCommander() && agent.hasMine() && agent.isTouchingResource(Resource.ResourceType.ExtremPoint);
            }

            @ Override
            protected void action() {
                agent.deployMine();
            }
        });
        //-------------------------------------------->
        createRule(new Rule(1999, "Commander") {
            @ Override
            protected boolean constraint() {
                return agent.isCommander() && agent.isTouchingResource(Resource.ResourceType.Mine) && agent.isTouchingResource();
            }

            @ Override
            protected void action() {
                agent.go();
            }
        });

        //-------------------------------------------->
        createRule(new Rule(800, "Support") {
            @ Override
            protected boolean constraint() {
                return agent.isCommander() && agent.getFuelInPercent() <= 15;
            }

            @ Override
            protected void action() {
                agent.spendTeamAgentFuel(50);
            }
        });




        //-------------------------------------------->
        createRule(new Rule(900, "Suche R") {
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
        createRule(new Rule(904, "Pick up") {
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
        createRule(new Rule(905, "Mine2") {
            @ Override
            protected boolean constraint() {
                return agent.isTouchingResource(ResourceType.Mine);
            }

            @ Override
            protected void action() {
                agent.turnAround();
                agent.go();
            }
        });
        //-------------------------------------------->
        createRule(new Rule(910, "Mothership") {
            @ Override
            protected boolean constraint() {
                return agent.isCarringResource();
            }

            @ Override
            protected void action() {
                agent.goToMothership();
            }
        });//-------------------------------------------->
        createRule(new Rule(915, "Give M") {
            @ Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.isCarringResource();
            }

            @ Override
            protected void action() {
                agent.deliverResourceToMothership();
            }
        });//-------------------------------------------->
        createRule(new Rule(998, "Hab durst") {
            @ Override
            protected boolean constraint() {
                return agent.getFuelInPercent() <= 30;
            }

            @ Override
            protected void action() {
                agent.goToMothership();
            }
        });//-------------------------------------------->
        createRule(new Rule(999, "Tanken") {
            @ Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.getFuelInPercent() <= 75;
            }

            @ Override
            protected void action() {
                agent.orderFuel(80);
            }
        });//-------------------------------------------->
        createRule(new Rule(1997, "Attack") {
            @ Override
            protected boolean constraint() {
                return agent.seeAdversaryAgent()|| agent.isUnderAttack();
            }

            @ Override
            protected void action() {
                agent.fightWithAdversaryAgent();
            }
        });//-------------------------------------------->
        createRule(new Rule(1998, "Attack") {
            @ Override
            protected boolean constraint() {
                return agent.seeAdversaryMothership();
            }

            @ Override
            protected void action() {
                agent.fightWithAdversaryMothership();
            }
        });//-------------------------------------------->
        createRule(new Rule(2500, "Ausweichen") {
            @ Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @ Override
            protected void action() {
                agent.turnRight(105);
            }
        });
    }
}
