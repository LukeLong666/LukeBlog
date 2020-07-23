package com.luke.luke_blog

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main() {
    val passwordEncoder = BCryptPasswordEncoder()
    val password = "123456"
    val encode = passwordEncoder.encode(password)
    println("password===>${password}")
    println("encode=====>${encode}")

    val encode1 = "\$2a\$10\$dHHUQstE92nZUJTpw2rZPeRZkWRGhuWLWqI0skipNrihoPdBNHzfq"
    val encode2 = "\$2a\$10\$SY/xpWp0qlHYxL9.v8oW0ullNGwrbCLRSJn7F0yRTS291R3y9a9Ay"
    val encode3 = "\$2a\$10\$A2IM.lgX4Rc7GAaLJlZuKeekm5ib7rJ9ZLybzmsZULuVdXrWUFqXe"

    //1提交密码

    //2与密文匹配

//    val origin = "1233456";
//    val matches = passwordEncoder.matches(origin, "\$2a\$10$"+"QvhlUnHW4r/ilFboonq0nOSgR3QAKFUUH56xb09ognb28QtiJIAyG")
//    println(matches)


    val rightPassword = "123456"
    val wrongPassword1 = "ajdawjdoaj"
    val wrongPassword2 = "1234566"
    val wrongPassword3 = "1223456"

    println("rightPassword,encode1===>"+passwordEncoder.matches(rightPassword,encode1))
    println("rightPassword,encode2===>"+passwordEncoder.matches(rightPassword,encode2))
    println("rightPassword,encode3===>"+passwordEncoder.matches(rightPassword,encode3))

    println("wrongPassword1,encode1===>"+passwordEncoder.matches(wrongPassword1,encode1))
    println("wrongPassword1,encode2===>"+passwordEncoder.matches(wrongPassword1,encode2))
    println("wrongPassword1,encode3===>"+passwordEncoder.matches(wrongPassword1,encode3))
    println("wrongPassword2,encode1===>"+passwordEncoder.matches(wrongPassword2,encode1))
    println("wrongPassword3,encode2===>"+passwordEncoder.matches(wrongPassword3,encode2))


}