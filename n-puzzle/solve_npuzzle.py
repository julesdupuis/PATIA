
from npuzzle import (Solution,
                     State,
                     Move,
                     UP,
                     DOWN,
                     LEFT,
                     RIGHT,
                     create_goal,
                     get_children,
                     is_goal,
                     is_solution,
                     load_puzzle,
                     to_string,
                     make_move)
from node import Node
from typing import Literal, List
import argparse
import math
import time

BFS = 'bfs'
DFS = 'dfs'
ASTAR = 'astar'
IDDFS = 'iddfs'

def solve_bfs(open : List[Node]) -> Solution:
    '''Solve the puzzle using the BFS algorithm'''

    dimension = int(math.sqrt(len(open[0].state)))
    goal = create_goal(dimension)
    while(len(open)>0):
        # get oldest element from the list
        current = open.pop(0)
        if(is_goal(current.state, goal)):
            return current.get_path()
        for move in [UP, DOWN, LEFT, RIGHT]:
            new_state = make_move(current.state, move, dimension)
            if(new_state != None):
                open.append(Node(new_state, move, parent=current))
    return []

def oppositeMove(move : Move) -> Move:
    if(move == UP):
        return DOWN
    elif(move == DOWN):
        return  UP
    elif(move == LEFT):
        return RIGHT
    elif(move == RIGHT):
        return LEFT
    else:
        return

def solve_dfs(open : List[Node]) -> Solution:
    '''Solve the puzzle using the DFS algorithm'''

    dimension = int(math.sqrt(len(open[0].state)))
    goal = create_goal(dimension)
    while(len(open)>0):
        # get newest element from the list
        current = open.pop()
        if(is_goal(current.state, goal)):
            return current.get_path()
        # else:
        #     path = current.get_path()
        #     print(path, ' : ', len(path))
        for move in [UP, DOWN, LEFT, RIGHT]:
            if(move == oppositeMove(current.move)):
                continue
            new_state = make_move(current.state, move, dimension)
            if(new_state != None):
                open.append(Node(new_state, move, parent=current))
                # print('state:')
                # print(to_string(new_state))
    return []

def solve_astar(open : List[Node]) -> Solution:
    '''Solve the puzzle using the A* algorithm'''

    # Todo: implement A* algorithm
    pass

def heuristic(current_state : State, goal_state : State) -> int:
    '''Calculate the Manhattan distance of the puzzle'''

    # Todo: implement the heuristic function
    pass

def depth_limited_search(node: Node, limit: int, goal_state: State, moves: List[Move], dimension: int) -> Solution | None:
    '''Perform a depth-limited search'''

    # Todo: implement depth-limited search
    pass

def solve_iddfs(root: Node, max_depth: int) -> Solution:
    '''Solve the puzzle using the Iterative Deepening Depth-First Search algorithm'''

    # Todo: implement IDDFS algorithm
    pass

def main():
    parser = argparse.ArgumentParser(description='Load an n-puzzle and solve it.')
    parser.add_argument('filename', type=str, help='File name of the puzzle')
    parser.add_argument('-a', '--algo', type=str, choices=['bfs', 'dfs', 'astar', 'iddfs'], required=True, help='Algorithm to solve the puzzle')
    parser.add_argument('-v', '--verbose', action='store_true', help='Increase output verbosity')
    parser.add_argument('-d', '--max_depth', type=int, default=100, help='Maximum depth for IDDFS')

    args = parser.parse_args()

    puzzle = load_puzzle(args.filename)

    if args.verbose:
        print('Puzzle:\n')
        print(to_string(puzzle))

    if not is_goal(puzzle, create_goal(int(math.sqrt(len(puzzle))))):

        root = Node(state = puzzle, move = None)
        open = [root]

        if args.algo == BFS:
            print('BFS\n')
            start_time = time.time()
            solution = solve_bfs(open)
            duration = time.time() - start_time
            if solution:
                print('Solution:', solution)
                print('Valid solution:', is_solution(puzzle, solution))
                print('Duration:', duration)
            else:
                print('No solution')
        elif args.algo == DFS:
            print('DFS\n')
            start_time = time.time()
            solution = solve_dfs(open)
            duration = time.time() - start_time
            if solution:
                print('Solution:', solution)
                print('Valid solution:', is_solution(puzzle, solution))
                print('Duration:', duration)
            else:
                print('No solution')
        elif args.algo == ASTAR:
            print('A*')
            start_time = time.time()
            solution = solve_astar(open)
            duration = time.time() - start_time
            if solution:
                print('Solution:', solution)
                print('Valid solution:', is_solution(puzzle, solution))
                print('Duration:', duration)
        elif args.algo == IDDFS:
            print('IDDFS')
            start_time = time.time()
            solution = solve_iddfs(root, args.max_depth)
            duration = time.time() - start_time
            if solution:
                print('Solution:', solution)
                print('Valid solution:', is_solution(puzzle, solution))
                print('Duration:', duration)
            else:
                print('No solution')
    else:
        print('Puzzle is already solved')

if __name__ == '__main__':
    main()