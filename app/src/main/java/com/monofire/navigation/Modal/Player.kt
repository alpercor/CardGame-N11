package com.monofire.navigation.Modal


open class Player {
    var id: String? =null
    var mail: String? =null
    var password: String? =null
    var topScore:Int?=0
    var highScore:Int?=0
    var imgUrl: String? =null


    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Player.class)
    }

    constructor(id: String?,mail: String?,password:String?,topScore:Int?,highScore:Int?) {
        this.id =id
        this.mail = mail
        this.password=password
        this.topScore=topScore
        this.highScore=highScore
    }
    constructor(id: String?,mail: String?,password: String?,topScore: Int?,highScore: Int?,imgUrl:String = "")
            :this(id,mail,password,topScore,highScore){
        this.imgUrl=imgUrl
    }

}