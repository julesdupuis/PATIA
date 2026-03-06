(define (problem npuzzle-default)
(:domain npuzzle)
(:objects t1 t2 t3 t4 t5 t6 t7 t8 - tile
        p1 p2 p3 p4 p5 p6 p7 p8 p9 - position)
(:init
        (positioned t3 p1) (positioned t7 p2) (positioned t5 p3)
        (blankAt p4) (positioned t1 p5) (positioned t2 p6)
        (positioned t8 p7) (positioned t4 p8) (positioned t6 p9)
        ; constraints
        (neighbor p1 p2) (neighbor p1 p4)
        (neighbor p2 p3) (neighbor p2 p5)
        (neighbor p3 p6)
        (neighbor p4 p5) (neighbor p4 p7)
        (neighbor p5 p6) (neighbor p5 p8)
        (neighbor p6 p9)
        (neighbor p7 p8) (neighbor p8 p9)
        )
(:goal (and
        (positioned t1 p1) (positioned t2 p2) (positioned t3 p3)
        (positioned t4 p4) (positioned t5 p5) (positioned t6 p6)
        (positioned t7 p7) (positioned t8 p8) ;(blankAt p9)
        ))
)