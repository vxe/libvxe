// +build sample

package main

import (
	"fmt"
	"github.com/vxe/libvxe"
)

func main() {
	fmt.Printf("A version of the Package %s is %s\n", "libvxe", libvxe.Version())
	libvxe.Launch()
}
