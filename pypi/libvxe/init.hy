(import time)
(import hyrepl)

;; (import time
;;   [HyREPL.server :as repl]
;;   [HyREPL.middleware.eval :as repl-mw])


(setv (. hyrepl.middleware.eval eval-module) (globals))
;; (defmain [&rest args]
;;   (let [[s (repl.start-server)]]
;;     (print (.format "Listening on {}" (. (second s) server-address)))
;;     (while True
;;       (time.sleep 1))))


;; (setv (. repl-mw eval-module) (globals))
;; (setv s (repl.start-server))
;; (print (.format "Listening on {}" (. (second s) server-address)))


;; (defmain [&rest args]
;;       (while True
;;         (time.sleep 1)))
