// +build sample

package main

import (
	"fmt"

	"github.com/vxe/golang"
)

func main() {
	fmt.Printf("A version of the Package %s is %s\n", "golang", golang.Version())
}
