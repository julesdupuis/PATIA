; npuzzle

(define (domain npuzzle)
	(:requirements :strips :typing :disjunctive-preconditions)
	(:types tile position)
	(:predicates
		(blankAt ?x - position)
		(positioned ?x - tile ?y - position)
		(neighbor ?x - position ?y - position)
		)

	(:action movetile
		:parameters (?x - tile ?y - position ?z - position)
		:precondition
		(and (blankAt ?z) (positioned ?x ?y)
			(or (neighbor ?y ?z) (neighbor ?z ?y)))
		:effect
		(and
			(not (positioned ?x ?y))
			(positioned ?x ?z)
			(not (blankAt ?z))
			(blankAt ?y)
			))
)
