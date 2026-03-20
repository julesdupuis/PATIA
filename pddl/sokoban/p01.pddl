(define (problem sokoban-default)
(:domain sokoban)
(:objects
                p31 p41
                p32 p42
        p13 p23 p33 p43
        p14     p34 p44
        p15 p25 p35 p45
            p26 p36 p46 - position)
(:init
        ; horizontal
        (neighbor p31 p41)
        (neighbor p32 p42)
        (neighbor p13 p23) (neighbor p23 p33) (neighbor p33 p43)
        (neighbor p34 p44)
        (neighbor p15 p25) (neighbor p25 p35) (neighbor p35 p45)
        (neighbor p26 p36) (neighbor p36 p46)
        (aligned p13 p23 p33) (aligned p23 p33 p43)
        (aligned p15 p25 p35) (aligned p25 p35 p45)
        (aligned p26 p36 p46)
        ; vertical
        (neighbor p13 p14) (neighbor p14 p15)
        (neighbor p25 p26)
        (neighbor p31 p32) (neighbor p32 p33) (neighbor p33 p34) (neighbor p34 p35) (neighbor p35 p36)
        (neighbor p41 p42) (neighbor p42 p43) (neighbor p43 p44) (neighbor p44 p45) (neighbor p45 p46)
        (aligned p13 p14 p15)
        (aligned p31 p32 p33) (aligned p32 p33 p34) (aligned p33 p34 p35) (aligned p34 p35 p36)
        (aligned p41 p42 p43) (aligned p42 p43 p44) (aligned p43 p44 p45) (aligned p44 p45 p46)
        ; other
        (playeron p34)
        (isbox p25) (isbox p35) (isbox p33)
                                  (notbox p31) (notbox p41)
                                  (notbox p32) (notbox p42)
        (notbox p13) (notbox p23)              (notbox p43)
        (notbox p14)              (notbox p34) (notbox p44)
        (notbox p15)                           (notbox p45)
                     (notbox p26) (notbox p36) (notbox p46)
        )
(:goal (and
        (isbox p42) (isbox p35) (isbox p33)
        ))
)

; level 1
; "  ####"
; "  #  #"
; "### .#"
; "#  * #"
; "# #@ #"
; "# $* #"
; "##   #"
; " #####"

; # wall
; @ player
; $ box
; . goal
; * box on goal
; + player on goal
