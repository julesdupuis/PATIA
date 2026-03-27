import matplotlib.pyplot as plt
import numpy as np
import argparse
from node import Node
import math
import time
from solve_npuzzle import solve_bfs, solve_dfs, solve_astar, solve_iddfs
import npuzzle

BFS = 'bfs'
DFS = 'dfs'
ASTAR = 'astar'
IDDFS = 'iddfs'

def solve(algo, max_depth, puzzle):
    root = Node(state = puzzle, move = None)
    open = [root]

    if algo == BFS:
        start_time = time.time()
        solution = solve_bfs(open)
        duration = time.time() - start_time
        if solution and npuzzle.is_solution(puzzle, solution):
            return len(solution), duration

    elif algo == DFS:
        start_time = time.time()
        solution = solve_dfs(open)
        duration = time.time() - start_time
        if solution and npuzzle.is_solution(puzzle, solution):
            return len(solution), duration

    elif algo == ASTAR:
        start_time = time.time()
        solution = solve_astar(open)
        duration = time.time() - start_time
        if solution and npuzzle.is_solution(puzzle, solution):
            return len(solution), duration

    elif algo == IDDFS:
        start_time = time.time()
        solution = solve_iddfs(root, max_depth)
        duration = time.time() - start_time
        if solution and npuzzle.is_solution(puzzle, solution):
            return len(solution), duration
    return 0, 0.0


def main():
    parser = argparse.ArgumentParser(description='Generate a performance graph of the chosen algorithm')
    parser.add_argument('-s', '--size', type=int, help='Size of the puzzle (e.g., 3 for a 3x3 puzzle)', default=3)
    parser.add_argument('-m', '--moves', type=int, help='Maximum number of moves to solve the puzzle', default=10)
    # parser.add_argument('-a', '--algo', type=str, choices=['bfs', 'dfs', 'astar', 'iddfs'], required=True, help='Algorithm to solve the puzzle')
    parser.add_argument('-v', '--verbose', action='store_true', help='Increase output verbosity')
    parser.add_argument('-d', '--max_depth', type=int, default=100, help='Maximum depth for IDDFS')

    args = parser.parse_args()

    initPuzzle = npuzzle.create_goal(args.size)

    if args.verbose:
        print('Puzzle:\n')
        print(npuzzle.to_string(initPuzzle))

    maxMoves = args.moves

    # generate puzzles with correct number of moves
    puzzles = []
    puzzle = initPuzzle
    for index in range(0, maxMoves):
        numMoves = 0
        while(numMoves != index+1):
            puzzle = npuzzle.shuffle(puzzle)
            numMoves, duration = solve(ASTAR, 100, puzzle)
        puzzles.append(puzzle)

    # we skip DFS because it gives bad results
    # (with DFS the solution is not the shortest and numMoves will not be index+1)
    algos = [BFS, ASTAR, IDDFS]

    for algo in algos:
        steps = []
        times = []
        print(algo)

        for index in range(0, maxMoves):
            numMoves, duration = solve(algo, args.max_depth, puzzles[index])
            if(numMoves <= 0):
                continue
            steps.append(numMoves)
            print(f"{index+1} : {numMoves} | {duration}")
            times.append(duration)

        plt.plot(steps, times)

    axe : plt.Axes = plt.gca()
    axe.set_xlabel("Number of moves in solution")
    axe.set_ylabel("Time")
    axe.set_title(f"Performance comparison of algorithms for {args.size}-puzzle")
    plt.legend(algos)
    # plt.savefig("figure.png")
    plt.show()

if __name__ == '__main__':
    main()