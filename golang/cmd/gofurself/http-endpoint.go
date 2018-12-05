package main

import (
	"github.com/gorilla/mux"
	"fmt"
	"log"
	"net/http"
	"github.com/vxe/libvxe/pkg"
	"encoding/json"
	// "github.com/glycerine/zygomys"
)

type Person struct{
	ID string `json:"id:,omitempty"`
	FirstName string `json:"firstname:omitempty"`
	
}

var people []Person



func CreatePeople() {
	people = append(people, Person{ID: "pizza", FirstName: "pie"})
	people = append(people, Person{ID: "wtf", FirstName: "dude"})
}


func GetPeople (w http.ResponseWriter, r *http.Request){
    json.NewEncoder(w).Encode(people)
}

func GetPerson (w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	for _, item := range people {
		if item.ID == params["id"]{
			json.NewEncoder(w).Encode(item)
			return
		}		
	}
	json.NewEncoder(w).Encode(&Person{})
}


func main() {
	CreatePeople()
	router := mux.NewRouter()
	router.HandleFunc("/people", GetPeople).Methods("GET")
    // router.HandleFunc("/people/{id}", GetPerson).Methods("GET")
    // router.HandleFunc("/people/{id}", CreatePerson).Methods("POST")
    // router.HandleFunc("/people/{id}", DeletePerson).Methods("DELETE")
	log.Fatal(http.ListenAndServe(":8000", router)) //
	fmt.Printf("A version of the Package %s is %s\n", "libvxe", libvxe.Version())	
	// libvxe.Launch()

}







