/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import Main.JugsState;
import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.ResultFunction;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author jcarlos
 * @author Renannr
 * @author Rodrigo Nascimento
 */
public class JugsFunctionFactory {
    
    private static ActionsFunction _actionsFunction = null;
    private static ResultFunction _resultFunction = null;

    public static ActionsFunction getActionsFunction() {
        if (null == _actionsFunction) {
                _actionsFunction = new JugsActionsFunction();
        }
        return _actionsFunction;
    }

    public static ResultFunction getResultFunction() {
        if (null == _resultFunction) {
                _resultFunction = new JugsResultFunction();
        }
        return _resultFunction;
    }

    private static class JugsActionsFunction implements ActionsFunction {
        @Override
        public Set<Action> actions(Object state) {
            JugsState board = (JugsState) state;

            Set<Action> actions = new LinkedHashSet<Action>();

            if (board.state[0] > 0) { // pote de 5L esta cheio
                actions.add(board.ESVAZIAR_5L);
            }
            if (board.state[1] > 0) { // pote de 3L esta cheio
                actions.add(board.ESVAZIAR_3L);
            }
            if(board.state[0] < 5){
                actions.add(board.COMPLETAR_5L);
            }
            if(board.state[1] < 3){
                actions.add(board.COMPLETAR_3L);
            }
            if (((board.state[0] + board.state[1]) >= 5) && (board.state[1] > 0)) {
                actions.add(board.DESPEJAR_3L_5L);//(4, y - (4 - x))
            }
            if ((board.state[0] + board.state[1]) >= 3 && board.state[1] > 0) {
                actions.add(board.DESPEJAR_5L_3L);//(x -(3-y),3)
            }
            if (((board.state[0] + board.state[1]) <= 5) && (board.state[1] > 0)) {
                actions.add(board.DESPEJAR_3L_5L);//(x+y,0)
                if(board.state[1] != 0){
                    actions.add(board.ESVAZIAR_3L);
                }
            }
            if (((board.state[0] + board.state[1]) <= 3) && (board.state[1] > 0)) {
                actions.add(board.DESPEJAR_5L_3L);//(0,x+y)
                if(board.state[0] != 0 ){
                    actions.add(board.ESVAZIAR_5L);
                }
            }

            return actions;
        }
    }

    private static class JugsResultFunction implements ResultFunction {
        @Override
        public Object result(Object s, Action a) {
            JugsState board = (JugsState) s;
            // x == 5
            // y == 3
            // regra nº 6
            if (board.ESVAZIAR_3L.equals(a)) {
                JugsState newBoard = new JugsState();
                newBoard.state[0] = board.state[0];
                newBoard.state[1] = 0; //esvaziar 3L
                return newBoard;
            // regra nº 5 (0, y)
            } else if (board.ESVAZIAR_5L.equals(a)) {
                JugsState newBoard = new JugsState();
                newBoard.state[0] = 0; //esvaziar 5L
                newBoard.state[1] = board.state[1];
                return newBoard;
            // regra nº 
            } else if (board.COMPLETAR_3L.equals(a)) {
                JugsState newBoard = new JugsState();
                newBoard.state[0] = board.state[0];
                newBoard.state[1] = 3; //completa 3l
                return newBoard;
            } else if (board.COMPLETAR_5L.equals(a)) {
                JugsState newBoard = new JugsState();
                newBoard.state[0] = 5; //completa 5l
                newBoard.state[1] = board.state[1];
                return newBoard;
                //começar a mecher aqui
            } else if (board.DESPEJAR_3L_5L.equals(a)) {
                /* precisamos rever esse despeja um em outro */
                JugsState newBoard = new JugsState();
                newBoard.state[0] = board.state[1];
                newBoard.state[1] = 0; //esvazia 3L
                return newBoard;
            } else if (board.DESPEJAR_5L_3L.equals(a)) {
                /* precisamos rever esse despeja um em outro */
                JugsState newBoard = new JugsState();
                newBoard.state[0] = 2;
                newBoard.state[1] = 3;
                return newBoard;
            } 

            // The Action is not understood or is a NoOp
            // the result will be the current state.
            return s;
        }
    }
}