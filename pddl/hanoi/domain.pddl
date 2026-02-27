; hanoi

(define (domain hanoi)
  (:requirements :strips :typing)
  (:types block stick)
  (:predicates (on ?x - block ?y - block)
	       (onstick ?x - block ?y - stick)
	       (clear ?x - block)
           (empty ?x - stick)
	       (handempty)
	       (holding ?x - block)
           (bigger ?x - block ?y - block)
	       )

  (:action pick-up
	     :parameters (?x - block ?y - stick)
	     :precondition (and (clear ?x) (handempty) (onstick ?x ?y))
	     :effect
	     (and (not (onstick ?x ?y))
		   (empty ?y)
		   (not (clear ?x))
		   (not (handempty))
		   (holding ?x)
           ))

  (:action put-down
	     :parameters (?x - block ?y - stick)
	     :precondition (and (holding ?x) (empty ?y))
	     :effect
	     (and (not (holding ?x))
		   (clear ?x)
		   (handempty)
		   (onstick ?x ?y)
           (not (empty ?y))
           ))

  (:action stack
	     :parameters (?x - block ?y - block)
	     :precondition (and (holding ?x) (clear ?y) (bigger ?y ?x))
	     :effect
	     (and (not (holding ?x))
		   (not (clear ?y))
		   (clear ?x)
		   (handempty)
		   (on ?x ?y)
           ))

  (:action unstack
	     :parameters (?x - block ?y - block)
	     :precondition (and (on ?x ?y) (clear ?x) (handempty))
	     :effect
	     (and (holding ?x)
		   (clear ?y)
		   (not (clear ?x))
		   (not (handempty))
		   (not (on ?x ?y))
           )))
