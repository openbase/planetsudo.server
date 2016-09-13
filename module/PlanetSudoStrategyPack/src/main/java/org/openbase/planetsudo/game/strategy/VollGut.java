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
public class VollGut extends AbstractStrategy {

    public VollGut() {
    }

    public VollGut(AgentInterface a) {
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
        return 2;
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
        createRule(new Rule("Just Go", ALL) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go() ; agent.turnLeft(1) ;
            }
        });createRule(new Rule("Just Go", COMMANDER) {
            @Override
            protected boolean constraint() {
                return true;
            }

            @Override
            protected void action() {
                agent.go() ; agent.turnRight(1) ;
            }
        });
        //-------------------------------------------->
        createRule(new Rule("Kollision", ALL)
         {
            @Override
            protected boolean constraint() {
                return agent.isCollisionDetected();
            }

            @Override
            protected void action() {
             agent.turnRight (30);
         
            }
        });        
        createRule (new Rule ("Kollision", COMMANDER)   
        { @Override 
        protected boolean constraint () {
            return agent.isCollisionDetected() ; 
        }
        
                @Override
            protected void action() {
                agent.turnLeft (30);
                  }
        });
        
        createRule (new Rule ("Tanken", ALL)   
        { @Override 
        protected boolean constraint () {
            return  agent.isAtMothership() && agent.getFuelInPercent() <80 ; 
        }
        
                @Override
            protected void action() {
                agent.orderFuel(100);}
        });
        
        
        
        
        createRule (new Rule ("Tanken", ALL)   
        { @Override 
        protected boolean constraint () {
            return agent.getFuelInPercent() <20 && !agent.isAtMothership() ; 
        }
        
                @Override
            protected void action() {
                agent.goToMothership();}
        });
       
      
         
         createRule (new Rule ("Heal", ALL)   
        { @Override 
        protected boolean constraint () {
            return agent.seeLostTeamAgent(); 
        }
        
                @Override
            protected void action() {
                agent.spendTeamAgentFuel(agent.getFuelInPercent()/ 2);}
        });
        
        
       
        
        createRule (new Rule ("Attack Mothership", ALL)   
        { @Override 
        protected boolean constraint () {
            return agent.seeAdversaryMothership() ; 
        }
        
                @Override
            protected void action() {
                agent.fightWithAdversaryMothership();
}
        });
        
    
        
      createRule (new Rule ("Fight", ALL)   
        { @Override 
        protected boolean constraint () {
            return agent.seeAdversaryAgent() ; 
        }
        
                @Override
            protected void action() {
                agent.fightWithAdversaryAgent();
}
        });
        
     
      
    
   
            
      
      
      createRule(new Rule("End of Game", ALL) {
            @Override
            protected boolean constraint() {
                return agent.isGameOverSoon();
            }

            @Override
            protected void action() {
                agent.goToMothership();
            }
        });

       
    }
}
