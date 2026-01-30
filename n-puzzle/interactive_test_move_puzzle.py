import npuzzle

def main():
    dimension = 3
    puzzle = npuzzle.create_goal(dimension)

    print('input q or CTRl-D to quit')
    print('input u to move up')
    print('input d to move down')
    print('input l to move left')
    print('input r to move right\n')

    print('state:')
    print(npuzzle.to_string(puzzle))
    while(True):
        try:
            user_input = input()
        except EOFError:
            return

        if(user_input == 'q'):
            return
        elif(user_input == 'u'):
            direction = npuzzle.UP
        elif(user_input == 'd'):
            direction = npuzzle.DOWN
        elif(user_input == 'l'):
            direction = npuzzle.LEFT
        elif(user_input == 'r'):
            direction = npuzzle.RIGHT
        else:
            pass
        new_puzzle = npuzzle.make_move(puzzle, direction, dimension)
        if(new_puzzle == None):
            print('impossible move')
        else:
            puzzle = new_puzzle
            print('state:')
            print(npuzzle.to_string(puzzle))

if __name__ == '__main__':
    main()