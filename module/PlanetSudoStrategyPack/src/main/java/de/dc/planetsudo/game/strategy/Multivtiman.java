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
import de.dc.planetsudo.level.levelobjects.Resource.ResourceType;


/**
 *
 * @author Divine <DivineThreepwood@gmail.com>
 */
public class Multivtiman extends AbstractStrategy {

	public Multivtiman() {
	}
	public Multivtiman(AgentInterface a) {
		super(a);
	}

	/**
	 * Gib hier an wie viele Agenten dein Team besitzen soll.
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
				agent.go() ;
			}
		}) ;
              //-------------------------------------------->
          createRule(new Rule(100, "Got some" ) {
              
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
            createRule (new Rule(200, "Dats good") {      	
           @ Override 
                          protected boolean constraint() {
                                return         agent.isTouchingResource() &&
				              !agent.isTouchingResource(ResourceType.Mine);
			} ;
			@ Override
			protected void action() {
			agent.pickupResource();
                 
                       
			}
		});
        //-------------------------------------------->
            createRule (new Rule(300, "Got it") {      	
           @ Override 
                          protected boolean constraint() {
           
				return agent.isCarringResource();
			} ;
			@ Override
			protected void action() {
			agent.goToMothership();
                 
                       
			}
		});
            //-------------------------------------------->
             createRule (new Rule(350, "Coming Home") {      	
           @ Override 
                          protected boolean constraint() {
           
				return agent.isCarringResource() && agent.isAtMothership();
			} ;
			@ Override
			protected void action() {
			agent.deliverResourceToMothership();
                 
                       
			}
		});
             //-------------------------------------------->
            
          
             createRule (new Rule(1000000001, "Tanken") {      	
           @ Override              
                          protected boolean constraint() {
           
				return agent.getFuelInPercent() <= 30 ;
			} ;
			@ Override
			protected void action() {
			agent.goToMothership() ;        
			}
		});
         //-------------------------------------------->
             createRule (new Rule(2000010000, "Dont Crash") {      	
           @ Override 
                          protected boolean constraint() {
           
				return agent.isCollisionDetected();
			} ;
			@ Override
			protected void action() {
			agent.turnRandom();
                 
                       
			}
});
              //-------------------------------------------->
createRule (new Rule(1000000002, "Tanken2") {      	
           @ Override              
                          protected boolean constraint() {
           
				return agent.getFuelInPercent() <= 30  && agent.isAtMothership();
			} ;
			@ Override
			protected void action() {
			agent.orderFuel(100) ;        
			}
		});
        
        
        //-------------------------------------------->
createRule (new Rule(1000010000, "Dont Crash") {      	
           @ Override 
                          protected boolean constraint() {
           
				return agent.isCollisionDetected();
			} ;
			@ Override
			protected void action() {
			agent.turnLeft(87) ;                      
			}

           
            //-------------------------------------------->
});

        
        createRule (new Rule(2001100000, "Game Over") {
            @ Override
                     protected boolean constraint() {
                         return agent.isGameOverSoon();
            };
            @ Override
             protected void action() {
                agent.goToMothership();
            }
        }) ;

        
        
   //----------------------------------------------------------->
         createRule (new Rule(2011111111, "Last Stand") {
            @ Override
                     protected boolean constraint() {
                         return agent.isGameOverSoon() && agent.isAtMothership() && agent.seeAdversaryAgent() ;
            };
            @ Override
             protected void action() {
                agent.fightWithAdversaryAgent();
            }
        }) ;

         //----------------------------------------------------------->
         createRule (new Rule(2111111111, "Last Stand- Commander") {
            @ Override
                     protected boolean constraint() {
                         return agent.isGameOverSoon() && agent.isAtMothership() && agent.isCommander();
            };
            @ Override
             protected void action() {
                agent.repairMothership();
            }
        }) ;
        //----------------------------------------------------------->
       createRule (new Rule(1999999997, "Zum Angriff") {
            @ Override
                     protected boolean constraint() {
                         return agent.seeAdversaryAgent();
            };
            @ Override
             protected void action() {
                agent.fightWithAdversaryAgent();
                
            }
        }) ;
       //-----------------------------------------------------------> 
       createRule (new Rule(1999999996, "Hilfe!") {
            @ Override
                     protected boolean constraint() {
                         return agent.isFighting();
            };
            @ Override
             protected void action() {
                agent.orderSupport();
            }
        }) ;
       //----------------------------------------------------------->
        createRule (new Rule(1999999995, "Hilfe!") {
            @ Override
                     protected boolean constraint() {
                         return mothership.needSomeoneSupport();
            };
            @ Override
             protected void action() {
                agent.goToSupportAgent();
            }
      
        }) ;
        //----------------------------------------------------------->
        createRule (new Rule(1999999994, "Heal") {
            @ Override
                     protected boolean constraint() {
                         return mothership.needSomeoneSupport();
            };
            @ Override
             protected void action() {
                agent.spendTeamAgentFuel(50);
          
            }
       }) ;
        //----------------------------------------------------------->
         createRule (new Rule(1999999993, "Heal") {
            @ Override
                     protected boolean constraint() {
                         return agent.seeLostTeamAgent();
            };
            @ Override
             protected void action() {
                
                agent.spendTeamAgentFuel(50);
          
            }
       }) ;
        }}

        
