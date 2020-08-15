package algorithm.lc

object PalindromeNumber {

  def isPalindrome(x: Int): Boolean = {
    if (x < 0 || (x % 10 == 0 && x != 0)) return false

    var reservedNumber = 0
    var n = x

    while (n > reservedNumber) {
      reservedNumber = (reservedNumber * 10) + n % 10
      n /= 10
    }

    (n == reservedNumber) || (reservedNumber / 10) == n
  }

  def main(args: Array[String]): Unit = {
    println(isPalindrome(123))
    println(isPalindrome(10))
    println(isPalindrome(101))
    println(isPalindrome(1221))
  }
}
