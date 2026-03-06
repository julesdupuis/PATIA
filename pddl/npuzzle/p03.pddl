(define (problem npuzzle-default)
(:domain npuzzle)
(:objects
        t1 t2 t3 t4
        t5 t6 t7 t8
        t9 t10 t11 t12
        t13 t14 t15
        - tile
        p1 p2 p3 p4
        p5 p6 p7 p8
        p9 p10 p11 p12
        p13 p14 p15 p16
        - position)
(:init
        (positioned t13 p1) (positioned t7 p2) (positioned t9 p3) (positioned t11 p4)
        (blankAt p5) (positioned t1 p6) (positioned t2 p7) (positioned t15 p8)
        (positioned t3 p9) (positioned t14 p10) (positioned t12 p11) (positioned t5 p12)
        (positioned t8 p13) (positioned t4 p14) (positioned t6 p15) (positioned t10 p16)
        ; constraints
        (neighbor p1 p2) (neighbor p1 p5)
        (neighbor p2 p3) (neighbor p2 p6)
        (neighbor p3 p4) (neighbor p3 p7)
        (neighbor p4 p8)
        (neighbor p5 p6) (neighbor p5 p9)
        (neighbor p6 p7) (neighbor p6 p10)
        (neighbor p7 p8) (neighbor p7 p11)
        (neighbor p8 p12)
        (neighbor p9 p10) (neighbor p9 p13)
        (neighbor p10 p11) (neighbor p10 p14)
        (neighbor p11 p12) (neighbor p11 p15)
        (neighbor p12 p16)
        (neighbor p13 p14)
        (neighbor p14 p15)
        (neighbor p15 p16)
        )
(:goal (and
        (positioned t1 p1) (positioned t2 p2) (positioned t3 p3) (positioned t4 p4)
        (positioned t5 p5) (positioned t6 p6) (positioned t7 p7) (positioned t8 p8)
        (positioned t9 p9) (positioned t10 p10) (positioned t11 p11) (positioned t12 p12)
        (positioned t13 p13) (positioned t14 p14) (positioned t15 p15) ;(blankAt p16)
        ))
)