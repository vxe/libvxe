cmake_minimum_required(VERSION 3.5)
project (libvxe)

# set(EMACS_PATH "/Applications/Emacs.app/Contents/MacOS/Emacs")
# execute_process(COMMAND "opt/tangle-project" OUTPUT_VARIABLE FOO)


set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY lib)
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ".")
set (CMAKE_SHARED_LIBRARY_SUFFIX ".so")
set (CMAKE_EXPORT_COMPILE_COMMANDS true)

include_directories(include)



set(CMAKE_RUNTIME_OUTPUT_DIRECTORY bin)
add_library(libvxe SHARED src/hello-emacs.c)
