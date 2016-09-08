package org.openbase.planetsudo.game.strategy;

/*-
 * #%L
 * PlanetSudo GameEngine
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
import org.openbase.planetsudo.level.levelobjects.Resource.ResourceType;
import org.openbase.planetsudo.level.levelobjects.Tower;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class DivineStrategy extends AbstractStrategy {

<<<<<<< HEAD
    public DivineStrategy() {
    }

    public DivineStrategy(AgentInterface a) {
        super(a);
    }

    /**
     * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
     *
     * @return Anzahl der Agenten
     */
    @Override
    protected int loadAgentCount() {
        return 999;
    }

    @Override
    protected void loadSwatTeams() {
        createSwat(ALPHA, 1);
    }

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
        createRule(new Rule("Discover") {
            @Override
            protected boolean constraint() {
                return agent.isCommander();
            }

            @Override
            protected void action() {
                agent.goRight(4);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("See Resources") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && agent.seeResource();
            }

            @Override
            protected void action() {
                agent.goToResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp 1P Resource") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && agent.isTouchingResource(ResourceType.Normal);
            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Go to Marker") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && mothership.isMarkerDeployed();
            }

            @Override
            protected void action() {
                agent.goToMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Search") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && agent.seeMarker();
            }

            @Override
            protected void action() {
                mothership.clearMarker();
                agent.searchResources();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && (agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(ResourceType.ExtraMothershipFuel));
            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp and Place") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && agent.isTouchingResource(ResourceType.ExtremPoint);
            }

            @Override
            protected void action() {
                if (!mothership.isMarkerDeployed()) {
                    agent.deployMarker();
                }
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp and Place") {
            @Override
            protected boolean constraint() {
                return agent.isCommander() && (agent.isTouchingResource(ResourceType.DoublePoints) || agent.isTouchingResource(ResourceType.ExtraMothershipFuel)) && !mothership.isMarkerDeployed() && !agent.seeMarker();
            }

            @Override
            protected void action() {
                agent.deployMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp 5P and Place") {
            @Override
            protected boolean constraint() {
                return !agent.isCommander() && agent.isTouchingResource(ResourceType.ExtremPoint);
            }

            @Override
            protected void action() {
                agent.deployMarker();
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp 5P and Place", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource(ResourceType.ExtremPoint) && !agent.seeMarker();
            }

            @Override
            protected void action() {
                agent.deployMarker();

            }
        });
        //-------------------------------------------->
        createRule(new Rule("Follow Wall", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource(ResourceType.ExtremPoint) && agent.hasTower();
            }

            @Override
            protected void action() {
                agent.deployTower(Tower.TowerType.ObservationTower);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Ignore") {
            @Override
            protected boolean constraint() {
                return agent.isTouchingResource(ResourceType.Mine) || agent.isTouchingResource(ResourceType.ExtraAgentFuel);
            }

            @Override
            protected void action() {
                agent.go();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Secure") {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon() && !agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Mark Resource") {
            @Override
            protected boolean constraint() {
                return agent.isCarringResource() && !mothership.isMarkerDeployed() && agent.seeResource();
            }

            @Override
            protected void action() {
                agent.deployMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Saved") {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon() && agent.isAtMothership();
            }

            @Override
            protected void action() {
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Order Fuel") {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership() && agent.isGameOverSoon() && mothership.hasFuel() && agent.getFuelInPercent() < 100;
            }

            @Override
            protected void action() {
                agent.orderFuel(100);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Support Agent") {
            @Override
            protected boolean constraint() {
                return mothership.needSomeoneSupport() && !agent.isSupportOrdered();
            }

            @Override
            protected void action() {
                agent.goToSupportAgent();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Save Resource") {
            @Override
            protected boolean constraint() {
                return agent.isCarringResource() && !agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("HelpLostAgent") {
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
        createRule(new Rule("FightAgainstMothership") {
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
        createRule(new Rule("FightAgainstMothership & Order Support") {
            @Override
            protected boolean constraint() {
                return !agent.isSupportOrdered() && agent.seeAdversaryMothership();
            }

            @Override
            protected void action() {
                agent.fightWithAdversaryMothership();
                agent.orderSupport();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("TurnToAdversaryAgent") {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack();
            }

            @Override
            protected void action() {
                agent.turnLeft(60);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("SaveMothership") {
            @Override
            protected boolean constraint() {
                return mothership.isDamaged() && !agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("RepaireMothership") {
            @Override
            protected boolean constraint() {
                return mothership.isDamaged() && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.repairMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("FightAgainstAgent") {
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
        createRule(new Rule("PlaceMine") {
            @Override
            protected boolean constraint() {
                return agent.hasMine() && agent.isUnderAttack();
            }

            @Override
            protected void action() {
                agent.deployMine();
                agent.goLeft(180);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("GoBackToMothership") {
            @Override
            protected boolean constraint() {
                return agent.getFuel() < 300 && !agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("OrderFuel") {
            @Override
            protected boolean constraint() {
                return mothership.hasFuel() && (agent.getFuelInPercent() < 90) && (agent.isAtMothership());
            }

            @Override
            protected void action() {
                agent.orderFuel(100);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("OrderFuelDuringFight") {
            @Override
            protected boolean constraint() {
                return mothership.hasFuel() && (agent.getFuel() < 100) && agent.isUnderAttack() && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.orderFuel(5);
                agent.goLeft(10);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("OrderFuelDuringFight") {
            @Override
            protected boolean constraint() {
                return mothership.hasFuel() && (agent.getFuel() < 100) && agent.seeAdversaryAgent() && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.orderFuel(5);
                agent.fightWithAdversaryAgent();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Pass Resource") {
            @Override
            protected boolean constraint() {
                return agent.isCarringResource() && agent.isAtMothership();
            }

            @Override
            protected void action() {
                agent.deliverResourceToMothership();
                agent.turnAround();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("CallForHelp") {
            @Override
            protected boolean constraint() {
                return (!agent.isSupportOrdered()) && ((agent.getFuel() < 5) || agent.isUnderAttack());
            }

            @Override
            protected void action() {
                agent.orderSupport();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("PickUp Fuel") {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 50 && agent.isTouchingResource(ResourceType.ExtraAgentFuel);
            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Follow Wall", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnLeft(4);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Follow Wall", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRight(4);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("AvoidWall", NOT_ALPHA, NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRandom(150);
            }
        });
		//-------------------------------------------->
		createRule(new Rule("Cancel Support") {
			@ Override
			protected boolean constraint() {
				return agent.isSupportOrdered() && !agent.seeAdversaryMothership() && agent.getFuel() > 10 && !agent.isUnderAttack() && !agent.seeAdversaryAgent();
			}

			@ Override
			protected void action() {
				agent.cancelSupport();

			}
		});
		//-------------------------------------------->
    }
=======
	public DivineStrategy() {
	}

	public DivineStrategy(AgentInterface a) {
		super(a);
	}

	/**
	 * Hier wird angegeben wie viele Agenten dem Team zur Verfügung stehen sollen.
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
>>>>>>> d848399dbc6663e330380778895e2265a9eb25ca

}
