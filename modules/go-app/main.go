package main


func fibonacci() func() int {
	var p = -1
	var n = 1
	return func() int {
		var c = p
		var d = n
		p = n
		n = c + d
		return c + d
	}
}

func main() {
	f = fibonacci()

	for i :=0; i < 10; i++ {
		println(f())
	}
}
