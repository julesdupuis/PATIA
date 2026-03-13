(define (problem pursuit-evasion-default)
(:domain pursuit-evasion)
(:objects p1 p2 - pursuer
        n1 n2 n3 n4 n5 - node)
(:init
        (positioned p1 n1) (positioned p2 n1)
        (captured n1)
        ; constraints
        (neighbor n1 n2)
        (neighbor n2 n3) (neighbor n2 n4) (neighbor n2 n5)
        (neighbor n4 n5)
        )
(:goal (and
        (captured n1)
        (captured n2)
        (captured n3)
        (captured n4)
        (captured n5)
        ))
)