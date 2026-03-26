; sokoban

(define (domain sokoban)
	(:requirements :strips :typing :disjunctive-preconditions)
	(:types position)
	(:predicates
		(neighbor ?x - position ?y - position)
		(aligned ?x - position ?y - position ?z - position)
		(isbox ?x - position)
		(notbox ?x - position)
		(playeron ?x - position)
		)

	(:action move
		:parameters (?from - position ?to - position)
		:precondition
		(and (or (neighbor ?from ?to) (neighbor ?to ?from))
			(playeron ?from)
			(notbox ?to))
		:effect
		(and
			(not (playeron ?from))
			(playeron ?to)
			))

	(:action movebox
		:parameters (?from - position ?box - position ?behind - position)
		:precondition
		(and (or (aligned ?from ?box ?behind) (aligned ?behind ?box ?from))
			(playeron ?from)
			(isbox ?box)
			(notbox ?behind))
		:effect
		(and
			(not (playeron ?from))
			(playeron ?box)
			(not (isbox ?box))
			(isbox ?behind)
			(not (notbox ?behind))
			(notbox ?box)
			))
)
