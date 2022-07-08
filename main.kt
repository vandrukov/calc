package calculator
import java.math.BigInteger
fun main() {
    val map: MutableMap<String, BigInteger> = mutableMapOf()

    do {


        var entry = readLine()!!
        var listB = entry.split(" ").toMutableList()
        if (entry.contains(Regex(".*\\(.*\\).*"))) {
            listB = entry.replace(Regex("\\("), "\\( ").replace(Regex("\\)"), "\\ )").split(" ").toMutableList()
        }

        when {
            entry == "/help" -> {
                println("The program calculates numbers")
                continue
            }
            entry == "/exit" -> {
                println("Bye!")
                return
            }


            entry.contains(Regex("\\(.*\\)")) -> {

                val mapSign = mutableMapOf<String, Int>("+" to 1, "-" to 1, "*" to 2, "/" to 2, "^" to 3)
                val list = listB.toMutableList()
                val postfix = mutableListOf<String>()
                val stack = mutableListOf<String>()
                for (i in 0 until list.size) {
                    // if a nuber or leter -> add it to prefix
                    if (list[i].contains(Regex("\\w"))) {
                        postfix.add(list[i])

                        // if a left parenthesis -> add it to stack
                    } else if (list[i] == "(") {
                        stack.add(list[i])


                        // if a right parenthesis -> add all operators from stack till the left parenthesis to postfix, remove parenthesis and operators from the stack
                    } else if (list[i] == ")") {

                        for (j in stack.size - 1 downTo 0) {
                            if (stack[j] != "(") {
                                postfix.add(stack[j])
                                stack.removeAt(j)
                            } else if (stack.isEmpty()) {
                                break

                            } else {
                                stack.removeAt(j)

                                break
                            }
                        }

                        // the last operator
                    } else if (i == list.size - 1) {

                        for (k in stack.size - 1 downTo 0) {
                            postfix.add(stack[k])
                            stack.removeAt(k)
                        }
                    } else {
                        if (stack.isEmpty()) {
                            stack.add(list[i])

                            // if an operator -> compare with all operators till the left paranthesis in stack, all higher or equel priority add to postfix

                        } else {
                            for (m in stack.size - 1 downTo 0) {

                                if (stack[m] == ")" || stack[m] == "(") {
                                    stack.add(list[i])
                                    break

                                } else {
                                    if (mapSign[list[i]]!! <= mapSign[stack[m]]!!) {
                                        postfix.add(stack[m])
                                        stack.removeAt(m)


                                    }
                                    stack.add(list[i])

                                    break




                                }


                            }

                        }
                    }



                    if (i == list.size - 1) {
                        for (k in stack.size - 1 downTo 0) {
                            postfix.add(stack[k])
                            stack.removeAt(k)

                        }
                        var numero = BigInteger("0")
                        do {

                            for (i in 0 until postfix.size) {

                                when (postfix[i]) {
                                    "+" -> {
                                        if (postfix[i - 2].contains(Regex("\\d+")) && postfix[i - 1].contains(Regex("\\d+"))) {
                                            numero = (postfix[i - 2].toBigInteger() + postfix[i - 1].toBigInteger())
                                        } else if (postfix[i - 2].contains(Regex("\\d+"))) {
                                            numero = (postfix[i - 2].toBigInteger() + BigInteger("${map[postfix[i - 1]]}"))

                                        } else if (postfix[i - 1].contains(Regex("\\d+"))) {
                                            numero = BigInteger("${map[postfix[i - 2]]}") + postfix[i - 1].toBigInteger()

                                            // } else if (!map.containsKey(map[postfix[i-2]]) && !postfix[i-1].contains(Regex("\\d+")) || !map.containsKey(map[postfix[i-1]]) && !postfix[i-1].contains(Regex("\\d+"))  ) {
                                            //     println("Unknown variable")
                                            //     break
                                        } else {
                                            numero = BigInteger("${map[postfix[i - 2]]}") + BigInteger("${map[postfix[i - 1]]}")
                                        }

                                        postfix[i - 2] = numero.toString()
                                        postfix.removeAt(i)

                                        postfix.removeAt(i - 1)

                                        break

                                    }
                                    "-" -> {
                                        if (postfix[i - 2].contains(Regex("\\d+")) && postfix[i - 1].contains(Regex("\\d+"))) {
                                            numero = (postfix[i - 2].toBigInteger() - postfix[i - 1].toBigInteger())
                                        } else if (postfix[i - 2].contains(Regex("\\d+"))) {

                                            numero = (postfix[i - 2]!!.toBigInteger() - BigInteger("${map[postfix[i - 1]]}"))

                                        } else if (postfix[i - 1].contains(Regex("\\d+"))) {
                                            numero = BigInteger("${map[postfix[i - 2]]}") - postfix[i - 1].toBigInteger()

                                            // } else if (!map.containsKey(map[postfix[i-2]]) && !postfix[i-1].contains(Regex("\\d+")) || !map.containsKey(map[postfix[i-1]]) && !postfix[i-1].contains(Regex("\\d+"))  ) {
                                            //     println("Unknown variable")
                                            //     break
                                        } else {
                                            numero = BigInteger("${map[postfix[i - 2]]!!}") - BigInteger("${map[postfix[i - 1]]}")
                                        }
                                        postfix[i - 2] = numero.toString()
                                        postfix.removeAt(i)

                                        postfix.removeAt(i - 1)


                                        break
                                    }
                                    "/" -> {
                                        if (postfix[i - 2].contains(Regex("\\d+")) && postfix[i - 1].contains(Regex("\\d+"))) {
                                            numero = (postfix[i - 2].toBigInteger() / postfix[i - 1].toBigInteger())
                                        } else if (postfix[i - 2].contains(Regex("\\d+"))) {
                                            numero = (postfix[i - 2].toBigInteger() / BigInteger("${map[postfix[i - 1]]}"))

                                        } else if (postfix[i - 1].contains(Regex("\\d+"))) {
                                            numero = BigInteger("${map[postfix[i - 2]]}") / postfix[i - 1].toBigInteger()

                                            // } else if (!map.containsKey(map[postfix[i-2]]) && !postfix[i-1].contains(Regex("\\d+")) || !map.containsKey(map[postfix[i-1]]) && !postfix[i-1].contains(Regex("\\d+"))  ) {
                                            //     println("Unknown variable")
                                            //     break
                                        } else {
                                            numero = BigInteger("${map[postfix[i - 2]]}") / BigInteger("${map[postfix[i - 1]]}")
                                        }
                                        postfix[i - 2] = numero.toString()
                                        postfix.removeAt(i)

                                        postfix.removeAt(i - 1)

                                        break
                                    }
                                    "*" -> {
                                        if (postfix[i - 2].contains(Regex("\\d+")) && postfix[i - 1].contains(Regex("\\d+"))) {

                                            numero = (postfix[i - 2].toBigInteger() * postfix[i - 1].toBigInteger())
                                        } else if (postfix[i - 2].contains(Regex("\\d+")) && postfix[i - 1].contains(Regex("[a-zA-z]+"))) {

                                            numero = (postfix[i - 2].toBigInteger() * BigInteger("${map[postfix[i - 1]]}"))

                                        } else if (postfix[i - 2].contains(Regex("[a-zA-Z]+")) && postfix[i - 1].contains(Regex("\\d+"))) {

                                            numero = BigInteger("${map[postfix[i - 2]]}") * postfix[i - 1].toBigInteger()

                                            // } else if (!map.containsKey(map[postfix[i-2]]) && !postfix[i-1].contains(Regex("\\d+")) || !map.containsKey(map[postfix[i-1]]) && !postfix[i-1].contains(Regex("\\d+"))  ) {
                                            //     println("Unknown variable")
                                            //     break
                                        } else {
                                            numero = BigInteger("${map[postfix[i - 2]]}") * BigInteger("${map[postfix[i - 1]]}")

                                        }
                                        postfix[i - 2] = numero.toString()
                                        postfix.removeAt(i)

                                        postfix.removeAt(i - 1)

                                        break
                                    }
                                    "^" -> {
                                        println("to be done")
                                        break
                                    }
                                    else -> {
                                        continue
                                    }
                                }
                            }
                        } while (postfix.size != 1)
                        println(numero)
                    }


                }
            }







            entry.contains(Regex(".*\\(.* | .*\\).*")) -> {
                println("Invalid expression")

            }
            entry.contains(Regex(".*\\*{2,}.* | .*\\/{2,}.*")) -> {
                println("Invalid expression")

            }




            entry.contains(Regex("\\=.+\\=")) -> {
                println("Invalid assignment")

            }
            entry.contains(Regex("\\d+[a-z]+\\s*=[a-z]+\\d+\\s*=")) -> {
                println("Invalid identifier")
            }

            entry.contains(Regex("[^\\x00-\\x7F]+\\s*\\=")) -> {
                println("Invalid identifier")



            }

            entry.contains(Regex("[a-z]*\\s*=\\s*\\d+[a-z]+|[a-z]+\\d+")) -> {
                println("Invalid assignment")

            }

            entry.contains(Regex("\\d+[a-z]+|[a-z]+\\d+\\s*=\\s*\\d*")) -> {
                println("Invalid identifier")

            }
            entry.contains(Regex("[a-zA-Z]+\\s*\\=\\s*\\-?\\d+")) -> {
                val (s, n) = entry.replace(" ", "").split("=")

                map.put(s.trim(), n.toBigInteger())
            }



            entry.contains(Regex("[a-z]+\\s*\\=\\s*[a-z]")) -> {
                val (l, k) = entry.replace(" ", "").split("=")
                if (map.containsKey(l) && map.containsKey(k)) {
                    map[l] = map[k]!!.toInt().toBigInteger()

                } else if (!map.containsKey(l) && map.containsKey(k)){
                    map.put(l, map[k]!!.toInt().toBigInteger())
                } else {
                    println("Unknown variable")
                }
            }
            entry.contains(Regex("[a-z]+\\s*\\=\\s*\\w{2,}")) -> {
                println("Invalid assignment")

            }

            entry.contains(Regex("\\w+\\s*[\\+\\-\\*\\/]\\s*\\w+")) -> {

                val list = entry.split(" ").toMutableList()


                var num = BigInteger("0")
                for(j in 0 until list.size) {

                    when {

                        j == 0 -> {
                            if (list[j].contains(Regex("\\d+"))) {
                                num = list[j].toBigInteger()
                            } else {
                                num = BigInteger("${map[list[j]]}")
                                continue
                            }
                        }


                        //
                        list[j].contains("+") -> {
                            if (list[j+1].contains(Regex("\\d+"))) {
                                num += (list[j+1]).toBigInteger()
                            } else {
                                num += BigInteger("${map[list[j+1]]}")
                                continue
                            }
                        }
                        //
                        list[j].contains("-") && !list[j].contains(Regex("\\d")) -> {
                            var n = 0
                            for (q in 0 until list[j].length) {
                                if (list[j][q] == '-')n++
                            }
                            if (n % 2 == 0) {

                                if (list[j+1].contains(Regex("\\d+"))) {
                                    num += (list[j+1]).toBigInteger()
                                } else {
                                    num += BigInteger("${map[list[j+1]]}")
                                    continue
                                }


                            } else {
                                if (list[j+1].contains(Regex("\\d+"))) {
                                    num -= (list[j+1]).toBigInteger()
                                } else {
                                    num -= BigInteger("${map[list[j+1]]}")
                                    continue
                                }

                            }

                        }

                        list[j] == "*" -> {
                            if (list[j+1].contains(Regex("\\d+"))) {
                                num *= (list[j+1]).toBigInteger()
                            } else {
                                num *= BigInteger("${map[list[j+1]]}")
                                continue
                            }
                        }
                        list[j] == "/" -> {
                            if (list[j+1].contains(Regex("\\d+"))) {
                                num /= (list[j+1]).toBigInteger()
                            } else {
                                num /= BigInteger("${map[list[j+1]]}")
                                continue
                            }
                        } else -> {continue}
                    }
                }
                println(num)
            }

            entry.contains(Regex("\\d+\\s*[\\+\\-\\*\\/]+\\s*\\d+")) -> {
                val listA = entry.split(" ").toMutableList()
                var num = BigInteger("0")
                for(k in 0 until listA.size) {

                    when  {

                        k == 0  -> {
                            num = listA[0]!!.toBigInteger()
                            continue
                        }
                        listA[k].contains("+") -> {
                            num += (listA[k+1]).toBigInteger()
                            continue
                        }


                        listA[k].contains("-") && !listA[k].contains(Regex("\\d")) -> {
                            var n = 0
                            for (q in 0 until listA[k].length) {
                                if (listA[k][q] == '-')n++
                            }
                            if (n % 2 == 0) {

                                if (listA[k+1].contains(Regex("\\d+"))) {
                                    num += (listA[k+1]).toBigInteger()
                                } else {
                                    num += BigInteger("${map[listA[k+1]]}")
                                    continue
                                }


                            } else {
                                if (listA[k+1].contains(Regex("\\d+"))) {
                                    num -= (listA[k+1]).toBigInteger()
                                } else {
                                    num -= BigInteger("${map[listA[k+1]]}")
                                    continue
                                }

                            }
                        }
                        listA[k] == "*" -> {

                            num *= (listA[k+1]).toBigInteger()

                            continue

                        }
                        listA[k] == "/" -> {

                            num /= (listA[k+1]).toBigInteger()

                            continue

                        }

                        else -> {continue}

                    }

                }
                println(num)

            }

            entry.contains(Regex("\\w+")) -> {

                if (map.containsKey(entry.trim())) {
                    println(map[entry.trim()])
                    continue
                } else {
                    println("Unknown variable")

                }
            }

        }
    } while (entry != "/exit")
}
