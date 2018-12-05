package main


import (
    "log"
    "net/http"
    "github.com/gorilla/mux"
)

// nolint
var (
	version = "0.0.1-SNAPSHOT"
	commit  = "snapshot"
)

// Version will get a version of the package golang
func Version() string {
	return version
}




// our main function
func main() {
    router := mux.NewRouter()
    log.Fatal(http.ListenAndServe(":8000", router))
}
