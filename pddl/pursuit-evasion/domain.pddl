; npuzzle

(define (domain pursuit-evasion)
	(:requirements :strips :typing :disjunctive-preconditions)
	(:types pursuer node)
	(:predicates
		(positioned ?x - pursuer ?y - node)
		(neighbor ?x - node ?y - node)
		(captured ?x - node)
		)

	(:action move
		:parameters (?x - pursuer ?y - node ?z - node)
		:precondition
		(and (captured ?y) (positioned ?x ?y)
			(or (neighbor ?y ?z) (neighbor ?z ?y)))
		:effect
		(and
			(not (positioned ?x ?y))
			(positioned ?x ?z)
			(captured ?z)
			))
)
