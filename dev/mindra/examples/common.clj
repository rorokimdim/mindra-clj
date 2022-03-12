(ns mindra.examples.common)

(defn line-pattern [x y n delta]
  (when (>= n 2)
    (let [n' (/ n 3.0)
          n'' (/ n' 3.0)
          x' (+ x (* 2 n'))
          y' (+ y delta)]
      (concat
       [[
         [x y]
         [(+ x n) y]

         [x y']
         [(+ x n') y']

         [x' y']
         [(+ x' n'') y']]]
       (line-pattern x y' n' delta)
       (line-pattern (+ x (* 2 n')) y' n' delta)))))

(defn circle-pattern [x y r]
  (when (>= r 2)
    (let [sr (/ r 2.0)]
      (concat
       [[x y r]]
       (circle-pattern (+ x sr) y sr)
       (circle-pattern (- x sr) y sr)
       (circle-pattern x (+ y sr) sr)
       (circle-pattern x (- y sr) sr)))))

(defn hilbert-pattern [x y xi xj yi yj n]
  (if (<= n 0)
    [[(+ x (/ (+ xi yi) 2.0))
      (+ y (/ (+ xj yj) 2.0))]]
    (concat
     (hilbert-pattern x
                      y
                      (/ yi 2.0)
                      (/ yj 2.0)
                      (/ xi 2.0)
                      (/ xj 2.0)
                      (dec n))

     (hilbert-pattern (+ x (/ xi 2.0))
                      (+ y (/ xj 2.0))
                      (/ xi 2.0)
                      (/ xj 2.0)
                      (/ yi 2.0)
                      (/ yj 2.0)
                      (dec n))

     (hilbert-pattern (+ x (/ xi 2.0) (/ yi 2.0))
                      (+ y (/ xj 2.0) (/ yj 2.0))
                      (/ xi 2.0)
                      (/ xj 2.0)
                      (/ yi 2.0)
                      (/ yj 2.0)
                      (dec n))

     (hilbert-pattern (+ x (/ xi 2.0) yi)
                      (+ y (/ xj 2.0) yj)
                      (/ (- yi) 2.0)
                      (/ (- yj) 2.0)
                      (- (/ xi 2.0))
                      (- (/ xj 2.0))
                      (dec n)))))

(defn square-pattern [x y n]
  (when (>= n 2)
    (let [n' (/ n 2.0)]
      (concat
       [[x y n]]
       (square-pattern (- x n') (- y n') n')
       (square-pattern (+ x n') (- y n') n')
       (square-pattern (- x n') (+ y n') n')
       (square-pattern (+ x n') (+ y n') n')))))

(defn tree-pattern [x y n dx dy]
  (when (>= n 2)
    (let [n' (* n 0.60)
          p0 [x y]
          p1 [x (+ y n)]
          p2 [(- x dx) (+ y n dy)]
          p3 [(+ x dx) (+ y n dy)]]
      (concat
       [[p0 p1 p2 p3]]
       (tree-pattern (first p2) (second p2) n' (- dx 10) (- dy 10))
       (tree-pattern (first p3) (second p3) n' (- dx 10) (- dy 10))))))
