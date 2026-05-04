(define (domain move2)
   (:predicates (room ?r)
		(at-robby ?r)
        (next ?ra ?rb))

   (:action move
       :parameters  (?from ?to)
       :precondition (and  (room ?from) (room ?to) (at-robby ?from) (next ?from ?to))
       :effect (and  (at-robby ?to)
		     (not (at-robby ?from)))))