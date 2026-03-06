(define (problem npuzzle-default)
(:domain npuzzle)
(:objects t1 t2 t3 - tile
        p1 p2 p3 p4 - position)
(:init
        (blankAt p1) (positioned t3 p2)
        (positioned t2 p3) (positioned t1 p4)
        ; constraints
        (neighbor p1 p2) (neighbor p1 p3)
        (neighbor p2 p4)
        (neighbor p3 p4)
        )
(:goal (and
        (positioned t1 p1) (positioned t2 p2)
        (positioned t3 p3) ;(blankAt p4)
        ))
)