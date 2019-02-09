(require 'clomacs)


(clomacs-defun hello-world
               libvxe.emacs/hello-world
               :lib-name "libvxe"
               :namespace libvxe.emacs
               :doc "test.")


(provide 'libvxe)


