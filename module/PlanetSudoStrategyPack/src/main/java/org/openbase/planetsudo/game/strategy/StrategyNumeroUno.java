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
import org.openbase.planetsudo.level.levelobjects.Mothership;
import org.openbase.planetsudo.level.levelobjects.Resource;
import org.openbase.planetsudo.level.levelobjects.Tower;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class StrategyNumeroUno extends AbstractStrategy {

    public StrategyNumeroUno() {
    }

    public StrategyNumeroUno(AgentInterface a) {
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
        return 1;
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
        createRule(new Rule("Just Go", ALL,NOT_COMMANDER) {
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
        createRule(new Rule("Just Go", COMMANDER) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go();
                agent.turnLeft(2);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Just Go", COMMANDER) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed();
            }

            @Override
            protected void action() {
                agent.goToMarker();
                agent.turnLeft(30);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("collect", DELTA,ECHO) {
            @Override
            protected boolean constraint() {
                return (agent.seeResource(Resource.ResourceType.Normal) || agent.seeResource(Resource.ResourceType.DoublePoints) || agent.seeResource(Resource.ResourceType.ExtremPoint) && !agent.seeResource(Resource.ResourceType.Mine));
            }

            @Override
            protected void action() {
                agent.goToResource();
            }
        });        
        //-------------------------------------------->
        createRule(new Rule("collectRessource", DELTA,ECHO) {
            @Override
            protected boolean constraint() {
                return (agent.isTouchingResource(Resource.ResourceType.Normal)||agent.isTouchingResource(Resource.ResourceType.DoublePoints)||agent.isTouchingResource(Resource.ResourceType.ExtremPoint)) && !agent.isTouchingResource(Resource.ResourceType.Mine);
            }

            @Override
            protected void action() {
                agent.pickupResource();
               }
        });    
        
        //-------------------------------------------->
        createRule(new Rule("collect", ECHO,CHARLIE) {
            @Override
            protected boolean constraint() {
                return (agent.seeResource(Resource.ResourceType.ExtraMothershipFuel));
            }

            @Override
            protected void action() {
                agent.goToResource();
            }
        });        
        //-------------------------------------------->
        createRule(new Rule("collectRessource", ECHO,CHARLIE) {
            @Override
            protected boolean constraint() {
                return (agent.isTouchingResource(Resource.ResourceType.ExtraMothershipFuel));
            }

            @Override
            protected void action() {
                agent.pickupResource();
            }
        });    
        //-------------------------------------------->
        createRule(new Rule("Genozid", DELTA, CHARLIE,ECHO) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed() && !agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.goToMarker();
                agent.go();
            }
        }); 
        //-------------------------------------------->
        createRule(new Rule("Genozid", DELTA, CHARLIE,ECHO) {
            @Override
            protected boolean constraint() {
                return mothership.isMarkerDeployed() && agent.seeResource();
            }

            @Override
            protected void action() {
                agent.goToResource();
            }
        }); 
        //-------------------------------------------->
        createRule(new Rule("ressourceMothership", CHARLIE,ECHO,DELTA) {
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
        createRule(new Rule("collectRessource", DELTA,ECHO,CHARLIE) {
            @Override
            protected boolean constraint() {
                return agent.isCarringResource(Resource.ResourceType.ExtremPoint);
            }

            @Override
            protected void action() {
                mothership.clearMarker();
                agent.deployMarker();
                agent.goToMothership();
               }
        });    
         
        
         //-------------------------------------------->
        createRule(new Rule("Tower", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.erectTower(Tower.TowerType.DefenceTower);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("deployMarker", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryMothership() && !mothership.isMarkerDeployed();
            }

            @Override
            protected void action() {
                agent.deployMarker();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Attack", ALPHA,COMMANDER,ECHO,CHARLIE) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryMothership() && !agent.isUnderAttack();
            }

            @Override
            protected void action() {           
                agent.fightWithAdversaryMothership();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Attack", ALPHA,COMMANDER,ECHO,CHARLIE) {
            @Override
            protected boolean constraint() {
                return agent.seeAdversaryAgent();
            }

            @Override
            protected void action() {
                agent.deployMine();
                agent.fightWithAdversaryAgent();
            }
        });
        
        //-------------------------------------------->
        createRule(new Rule("orderSupport_Attack", CHARLIE,DELTA,ECHO) {
            @Override
            protected boolean constraint() {
                return agent.isUnderAttack() ;
            }

            @Override
            protected void action() {
                agent.deployMine();
                agent.orderSupport();
                agent.fightWithAdversaryAgent();
            }
        });
        //--------------------------------------->
        createRule(new Rule("fuel", ALL) {
            @Override
            protected boolean constraint() {
                return !agent.isUnderAttack() && agent.seeResource(Resource.ResourceType.ExtraAgentFuel);
            }

            @Override
            protected void action() {
                agent.goToResource();
                agent.pickupResource();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("fuel", ALL) {
            @Override
            protected boolean constraint() {
                return agent.getFuelInPercent() < 25;
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });
        
        
         //-------------------------------------------->
        createRule(new Rule("giveRessource", DELTA,ECHO,CHARLIE) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership()&& agent.isCarringResource();
            }

            @Override
            protected void action() {
                agent.deliverResourceToMothership();         
            }
        });      
        //-------------------------------------------->
        createRule(new Rule("Support", ALL) {
            @Override
            protected boolean constraint() {
                return mothership.needSomeoneSupport();
            }

            @Override
            protected void action() {
                agent.goToSupportAgent();
                agent.spendTeamAgentFuel(agent.getFuelInPercent()/2);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("getFuel", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isAtMothership()&& agent.getFuelInPercent() <25 && mothership.hasFuel();
            }

            @Override
            protected void action() {
                agent.orderFuel(90);
            }
        }); 
        //-------------------------------------------->
        createRule(new Rule("Mothership repair", ECHO) {
            @Override
            protected boolean constraint() {
                return mothership.isDamaged();
            }

            @Override
            protected void action() {
                agent.goToMothership();
                agent.repairMothership();
            }
        }); 
        //-------------------------------------------->
        createRule(new Rule("Mothership is under attack", CHARLIE,ECHO) {
            @Override
            protected boolean constraint() {
                return mothership.isDamaged();
            }

            @Override
            protected void action() {
                agent.goToMothership();
                agent.fightWithAdversaryAgent();
            }
        }); 
        
        //-------------------------------------------->
        createRule(new Rule("out of Fuel", ALL) {
            @Override
            protected boolean constraint() {
                return !agent.hasFuel();
            }

            @Override
            protected void action() {
                agent.orderSupport();
            }
        }); 
        //-------------------------------------------->
        createRule(new Rule("retreat", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        }); 
           
        //-------------------------------------------->
        createRule(new Rule("avoidColission", ALL , NOT_ALPHA,NOT_COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRandom();
            }
        });
        //-------------------------------------------->
        createRule(new Rule("avoidColission", ALPHA) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnLeft(13);
            }
        });
        //-------------------------------------------->
        createRule(new Rule("avoidColission", COMMANDER) {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
                agent.turnRight(5);
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

    private void createSwat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
