;;;; libvxe.asd

(asdf:defsystem #:libvxe
  :description "Describe libvxe here"
  :author "Your Name <your.name@example.com>"
  :license "Specify license here"
  :depends-on (#:drakma
               #:cxml)
  :serial t
  :components ((:file "package")
               (:file "libvxe")))

