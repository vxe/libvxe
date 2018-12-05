package libvxe

import (
	"glycerine/zygomys"
	// "github.com/junegunn/fzf"
    // "encoding/json"
    "log"
    "net/http"
    "github.com/gorilla/mux"
)

// nolint
var (
	version = "0.0.1_SNAPSHOT"
	commit  = "snapshot"
)

// Version will get a version of the package libvxe
func Version() string {
	return version
}


func Launch() string {
    router := mux.NewRouter()
    log.Fatal(http.ListenAndServe(":8000", router))
	return "hi"
}


func Fzfthing() string {
    return "hi"
}
