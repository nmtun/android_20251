import kotlin.math.*

// Lớp phân số
class Fraction(var numerator: Int, var denominator: Int) {
    // Tìm ước chung lớn nhất của tử số và mẫu số
    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    // In phân số dưới dạng tử/mẫu
    fun printFraction() {
        print("$numerator/$denominator" + " ")
    }

    // Tối giản phân số
    fun simplifyFraction() {
        val ucln = gcd(abs(numerator), abs(denominator))
        numerator /= ucln
        denominator /= ucln
        if (denominator < 0) {
            numerator *= -1
            denominator *= -1
        }
    }

    // So sánh 2 phân số
    fun compareFraction(ps: Fraction): Int {
        val a = this.numerator * ps.denominator
        val b = ps.numerator * this.denominator
        return when {
            a < b -> -1
            a == b -> 0
            else -> 1
        }
    }

    // Cộng 2 phân số
    fun fractionPlus(ps: Fraction): Fraction {
        val newNumerator = this.numerator * ps.denominator + ps.numerator * this.denominator
        val newDenominator = this.denominator * ps.denominator
        val sum = Fraction(newNumerator, newDenominator)
        sum.simplifyFraction()
        return sum
    }
}

// Lấy phân số từ bàn phím
fun getFractionFromInput(): Fraction {
    while (true) {
        print("Enter fraction (a/b): ")
        val input = readln().trim()
        val parts = input.split("/")
        if (parts.size == 2) {
            try {
                val numerator  = parts[0].toInt()
                val denominator  = parts[1].toInt()
                if (denominator  != 0) return Fraction(numerator , denominator )
                println("Denominator must not be zero. Please try again!")
            } catch (e: NumberFormatException) {
                println("Invalid format. Please enter a valid integer!")
            }
        } else {
            println("Invalid format. Please enter in a/b format (e.g. 3/4)")
        }
    }
}


fun main() {
    print("Enter number of fractions: ")
    val n = readln().toInt()
    val arr = Array(n) { getFractionFromInput() }

    println("\nArray of fractions:")
    for (ps in arr) {
        ps.printFraction()
    }

    println("\nSimplified fractions:")
    for (ps in arr) {
        ps.simplifyFraction()
        ps.printFraction()
    }

    println("\nSum of fractions:")
    var sum = arr[0]
    for (i in 1 until n) {
        sum = sum.fractionPlus(arr[i])
    }
    sum.printFraction()

    println("\nLargest fraction:")
    var max = arr[0]
    for (i in 1 until n) {
        if (arr[i].compareFraction(max) == 1) {
            max = arr[i]
        }
    }
    max.printFraction()

    println("\nSorted fractions in descending order:")
    arr.sortWith { a, b -> b.compareFraction(a) }
    for (ps in arr) ps.printFraction()
}
