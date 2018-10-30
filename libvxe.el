;;; libvxe.el --- a new era of polyglot elisp

;; Copyright (C) 2018 Vijay Edwin

;; Author: Vijay Edwin <vedwin@vijays-mbp.localhost>
;; Version: 0.1

;; This file is not part of GNU Emacs.

;; This program is free software: you can redistribute it and/or modify
;; it under the terms of the GNU General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or
;; (at your option) any later version.

;; This program is distributed in the hope that it will be useful,
;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;; GNU General Public License for more details.

;; You should have received a copy of the GNU General Public License
;; along with this program.  If not, see <http://www.gnu.org/licenses/>.

;;; Commentary:

;; a new era of polyglot elisp

;;; Code:

(defun lv:grep (stdin regex)
  (interactive)
  (-filter
   (lambda (output)
     (s-matches? regex output))
   stdin))


(defun lv:awk (stdin index)
  (interactive)
  (-map
   (lambda (lines)
     (nth (- index 1) (s-split " " lines)))
   stdin))

(defun lv:sed (stdin regex replacement)
  (interactive)
  (-map (lambda (lines)
          (s-replace regex replacement lines))
        stdin))


(provide 'libvxe)

;;; libvxe.el ends here
