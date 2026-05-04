(define (problem move01)
   (:domain move)
   (:objects rooma roomb)
   (:init (room rooma)
          (room roomb)
          (at-robby rooma)
          )
   (:goal (and
               (at-robby roomb))))