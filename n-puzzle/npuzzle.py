from typing import List, Literal, Tuple, get_args
from random import choice
import math

'''
N-Puzzle
'''

UP = 'up'
DOWN = 'down'
LEFT = 'left'
RIGHT = 'right'

Move = Literal['up', 'down', 'left', 'right']
Solution = List[Move]
State = List[int]

# Convert the state to a string
def to_string(current_state : State) -> str:
    '''String representation of the puzzle'''

    dimension = int(math.sqrt(len(current_state)))

    t = ''
    for i in range(0, dimension):
        for j in range(0, dimension):
            t += str(current_state[i * dimension + j]) + '\t'
        t += '\n'
    return t

# Randomly shuffle a puzzle of dimension n
def shuffle(state : State) -> State:
    '''Generate a random n-puzzle'''
    moves = list(get_args(Move))
    random_move = choice(moves)
    new_state = make_move(state, random_move, int(math.sqrt(len(state))))
    while new_state is None:
        random_move = choice(moves)
        new_state = make_move(state, random_move, int(math.sqrt(len(state))))
    return new_state

def create_goal(dimension : int) -> State:
    '''Create the goal state of the puzzle'''
    return [i for i in range(0, dimension * dimension)]


def is_goal(puzzle : State, goal : State) -> bool:
    '''Check if the puzzle is the goal state'''
    return (puzzle == goal)

def get_children(puzzle : State, moves : List[Move], dimension : int) -> List[Tuple[State, Move]]:
    '''Get the children of the puzzle'''
    children = []
    for move in moves:
        new_state = make_move(puzzle, move, dimension)
        if new_state is not None:
            children.append((new_state, move))
    return children

def play_puzzle(puzzle : State, moves : Solution) -> State:
    '''Play the puzzle'''
    dimension = int(math.sqrt(len(puzzle)))
    for move in moves:
        puzzle = make_move(puzzle, move, dimension)
    return puzzle

def is_plan(puzzle : State, moves : Solution) -> bool:
    '''Check if the plan contains no illegal moves'''
    if play_puzzle(puzzle, moves) is not None:
        return True
    else:
        return False

def is_solution(puzzle : State, moves : Solution) -> bool:
    '''Check if the plan solves the puzzle'''
    if is_goal(play_puzzle(puzzle, moves), create_goal(int(math.sqrt(len(puzzle))))):
        return True
    else:
        return False

def make_move(current_state : State, direction : Move, dimension : int) -> State | None:
    '''Move the blank tile in the puzzle'''

    if current_state:

        new_state = current_state.copy()
        # blank tile is 0
        blank_index = current_state.index(0)

        if(direction == UP):
            if(math.floor(blank_index/dimension) == dimension-1):
                # no up move if blank is in the bottom row
                return None
            other_tile_index = blank_index + dimension

        elif(direction == DOWN):
            if(math.floor(blank_index/dimension) == 0):
                # no down move if blank is in the top row
                return None
            other_tile_index = blank_index - dimension

        elif(direction == LEFT):
            if(blank_index%dimension == dimension-1):
                # no left move if blank is in the left column
                return None
            other_tile_index = blank_index + 1

        elif(direction == RIGHT):
            if(blank_index%dimension == 0):
                # no right move if blank is in the right column
                return None
            other_tile_index = blank_index - 1

        else:
            return None

        other_tile = current_state[other_tile_index]
        new_state[blank_index] = other_tile
        new_state[other_tile_index] = 0

        return new_state
    else:
        return None

def save_puzzle(puzzle : State, filename : str) -> None:
    '''Save the puzzle to a file'''
    with open(filename, 'w') as file:
        for tile in puzzle:
            file.write(f"{tile} ")

def load_puzzle(filename : str) -> State:
    '''Load the puzzle from a file'''
    with open(filename, 'r') as file:
        content = file.read()
        puzzle = [int(tile) for tile in content.split()]
    return puzzle